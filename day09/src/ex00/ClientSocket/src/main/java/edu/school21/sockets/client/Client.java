package edu.school21.sockets.client;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class Client {
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private BufferedReader reader;

    public void connect(int port) {
        try {
            try {
                socket = new Socket(InetAddress.getLocalHost(), port);
                in = new BufferedReader(new InputStreamReader(
                        socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);
                reader = new BufferedReader(new InputStreamReader(System.in));
                String messageFromServer;
                while (true) {
                    messageFromServer = in.readLine();
                    System.out.println(messageFromServer);
                    if (messageFromServer.equals("Successful!")
                            || messageFromServer.equals("Registration failed :(")) {
                        break;
                    }
                    System.out.print("-> ");
                    out.println(reader.readLine());
                }
            } finally {
                System.out.print("Disconnecting from the Server...");
                socket.close();
                in.close();
                out.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
