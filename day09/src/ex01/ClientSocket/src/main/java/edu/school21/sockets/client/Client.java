package edu.school21.sockets.client;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class Client {
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private BufferedReader reader;
    private MessageGetter messageGetter;
    private MessageWriter messageWriter;
    private boolean isMessagingOver;

    public void connect(int port) {
        try {
            socket = new Socket(InetAddress.getLocalHost(), port);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
            reader = new BufferedReader(new InputStreamReader(System.in));

            String messageFromServer;
            String command;
            while (true) {
                messageFromServer = in.readLine();
                System.out.println(messageFromServer);
                System.out.print("-> ");
                command = reader.readLine();
                out.println(command);
                if (command.equalsIgnoreCase("signup")) {
                    signUp();
                    break;
                } else if (command.equalsIgnoreCase("signin")) {
                    if (signIn()) {
                        messageGetter = new MessageGetter(in);
                        messageWriter = new MessageWriter();
                        startMessaging();
                        if (isMessagingOver) {
                            break;
                        }
                    } else {
                        break;
                    }
                } else if (command.equalsIgnoreCase("exit")) {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void signUp() throws IOException {
        System.out.print(in.readLine() + "\n-> ");
        String name = reader.readLine();
        out.println(name);
        System.out.print(in.readLine() + "\n-> ");
        String password = reader.readLine();
        out.println(password);
        System.out.println(in.readLine());
    }

    private boolean signIn() throws IOException {
        System.out.print(in.readLine() + "\n-> ");
        String name = reader.readLine();
        out.println(name);
        System.out.print(in.readLine() + "\n-> ");
        String password = reader.readLine();
        out.println(password);
        String response = in.readLine();
        System.out.println(response);
        return response.equalsIgnoreCase("Start messaging");
    }

    private void startMessaging() {
        isMessagingOver = false;
        messageGetter.start();
        messageWriter.start();
        try {
            messageWriter.join();
            messageGetter.interrupt();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            isMessagingOver = true;
        }
    }

    private void disconnect() throws IOException {
        System.out.print("Disconnecting from the Server...");
        if (messageGetter != null && messageGetter.isAlive()) {
            messageGetter.interrupt();
        }
        if (messageWriter != null && messageWriter.isAlive()) {
            messageWriter.interrupt();
        }

        if (socket != null && !socket.isClosed()) {
            socket.close();
        }
        if (in != null) {
            in.close();
        }
        if (out != null) {
            out.close();
        }

        System.out.println(" Done!");
    }

    private class MessageGetter extends Thread {
        private BufferedReader in;

        MessageGetter(BufferedReader in) {
            this.in = in;
        }

        @Override
        public void run() {
            try {
                String str;
                while (!isInterrupted() && (str = in.readLine()) != null) {
                    System.out.println(str);
                }
            } catch (IOException e) {
                if (!socket.isClosed()) {
                    e.printStackTrace();
                }
            } finally {
                try {
                    disconnect();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private class MessageWriter extends Thread {
        @Override
        public void run() {
            while (!isInterrupted()) {
                try {
                    String message = reader.readLine();
                    if (message.equalsIgnoreCase("exit")) {
                        out.println(message);
                        messageGetter.interrupt();
                        break;
                    } else {
                        out.println(message);
                    }
                } catch (IOException e) {
                    if (!socket.isClosed()) {
                        e.printStackTrace();
                    }
                    break;
                }
            }
        }
    }

}
