package Lesson06;


import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketException;
import java.util.Scanner;
import java.util.function.Consumer;

public class Client {

    private static Network network = new Network();

    public static void main(String[] args) {

        network.connect();
        Thread outStream = new Thread(() -> network.startMessageOutStream());
        Thread inStream = new Thread(() -> startMessageInStream());
        outStream.start();
        inStream.start();
    }

    private static void startMessageInStream(){
        network.waitMessage(new Consumer<String>() {
            @Override
            public void accept(String s) {
                System.out.println(s);
            }
        });
    }
}