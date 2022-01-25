package Lesson06;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;
import java.net.SocketException;
import java.util.Scanner;
import java.util.function.Consumer;

public class Network {

    public static final String SERVER_HOST = "localhost";
    public static final int SERVER_PORT = 8189;
    public static final String CONNECTION_ERROR_MESSAGE = "Невозможно установить сетевое соединение";
    public static final String SEND_MESSAGE_ERROR = "Не удалось отправить сообщение на сервер";

    private int port;
    private String host;
    private Socket socket;
    private DataInputStream socketInput;
    private DataOutputStream socketOutput;
    private static Scanner scanner;

    public Network(int port, String host) {
        this.port = port;
        this.host = host;
    }

    public Network(){
        this(SERVER_PORT,SERVER_HOST);
    }

    public boolean connect(){
        try {
            this.socket = new Socket(this.host, this.port);
            this.socketInput = new DataInputStream(socket.getInputStream());
            this.socketOutput = new DataOutputStream(socket.getOutputStream());
            return true;
        } catch (IOException e) {
            System.err.println("Не удалось установить соединение");
            return false;
        }
    }

    public void sendMessage(String message) throws IOException {
        try {
            socketOutput.writeUTF(message);
        } catch (IOException e) {
            System.err.println("Не удалось отправить сообщение на сервер");
            throw e;
        }
    }

    public void waitMessage(Consumer<String> messageHandler){
        Thread thread = new Thread(() -> {
            while (true) {
                try {
                    String message = this.socketInput.readUTF();
                    messageHandler.accept(message);
                } catch (IOException e) {
                    e.printStackTrace();
                    System.err.println("Не удалось прочитать сообщение от сервера");
                    break;
                }
            }
        });
        thread.setDaemon(true);
        thread.start();
    }

    public void close(){
        try{
            this.socket.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void startMessageOutStream(){
        try {
            scanner = new Scanner(System.in);
            while (true){
                sendMessage(scanner.nextLine());
            }
        } catch (ConnectException e) {
            System.err.println(CONNECTION_ERROR_MESSAGE);
        } catch (SocketException e) {
            System.err.println(SEND_MESSAGE_ERROR);
        } catch (IOException e) {
            System.err.println("Error");;
        } finally {
            scanner.close();
        }
    }

}

