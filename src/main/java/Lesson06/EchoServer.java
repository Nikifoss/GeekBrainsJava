package Lesson06;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class EchoServer {

    public static final int PORT = 8189;

    private static Scanner scannerServer = new Scanner(System.in);

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Сервер начал работу. Ожидаются новые подключения. ");
            Socket clientSocket = serverSocket.accept();
            System.out.println("Клиент подключился. ");

            DataInputStream input = new DataInputStream(clientSocket.getInputStream());
            DataOutputStream output = new DataOutputStream(clientSocket.getOutputStream());

            Thread outStream = new Thread(() -> {
                try {
                    outStreamMessage(output);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            Thread inStream = new Thread(() -> {
                try {
                    processClientConnectionAndListen(input);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            outStream.start();
            inStream.start();


        } catch (IOException e) {
            System.err.println("Ошибка при подключении к порту: " + PORT);
            e.printStackTrace();
        }
    }
    private static void processClientConnectionAndListen(DataInputStream input) throws IOException {
        while (true){
            try {
                String message = input.readUTF();
                System.out.println("Получено сообщение " + message);
                if (message.equals("/end")){
                    break;
                }
            } catch (IOException e){
                System.out.println("Соединение было закрыто");
                break;
            }
        }
    }
    private static void outStreamMessage(DataOutputStream output) throws IOException{
        while (true){
        output.writeUTF(scannerServer.nextLine());
        }
    }

}
