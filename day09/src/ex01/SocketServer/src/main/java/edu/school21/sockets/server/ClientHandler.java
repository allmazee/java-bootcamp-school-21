package edu.school21.sockets.server;

import edu.school21.sockets.models.Message;
import edu.school21.sockets.models.User;
import edu.school21.sockets.services.MessagesService;
import edu.school21.sockets.services.UsersService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.Optional;

public class ClientHandler implements Runnable {
    private final Socket clientSocket;
    private BufferedReader in;
    private PrintWriter out;
    private final UsersService usersService;
    private final MessagesService messagesService;
    private String clientName;
    private User user;

    public ClientHandler(Socket clientSocket, UsersService usersService, MessagesService messagesService) {
        this.clientSocket = clientSocket;
        this.usersService = usersService;
        this.messagesService = messagesService;
    }

    @Override
    public void run() {
        try {
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out.println("Hello from Server!");
            String command;
            do {
                command = in.readLine();
            } while (!readCommand(command));

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            System.out.println("Client disconnected");
            try {
                disconnect();
            } catch (IOException | NullPointerException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean readCommand(String command) throws IOException {
        boolean isValidCommand = true;
        if (command.equalsIgnoreCase("signup")) {
            signUp();
        } else if (command.equalsIgnoreCase("signin")) {
            signIn();
        } else if (command.equalsIgnoreCase("exit")) {
            disconnect();
        } else {
            out.println("Wrong command. Available commands: signUp, signIn, exit");
            isValidCommand = false;
        }
        return isValidCommand;
    }

    private void signUp() throws IOException {
        while (true) {
            out.println("Enter username:");
            String name = in.readLine();
            out.println("Enter password:");
            String password = in.readLine();
            boolean isSignedUp = usersService.signUp(name, password);
            if (isSignedUp) {
                out.println("Successful!");
                break;
            } else {
                out.println("A user with this name already exists");
                disconnect();
            }
        }
    }

    private void signIn() throws IOException {
        out.println("Enter username:");
        String name = in.readLine();
        out.println("Enter password:");
        String password = in.readLine();
        user = usersService.signIn(name, password);
        if (user != null) {
                clientName = name;
                out.println("Start messaging");
                startMessaging();
        } else {
            Optional<User> loggedUser = usersService.findByName(name);
            if (loggedUser.isPresent() && loggedUser.get().isLogged()) {
                out.println("The user is already logged in");
            } else {
                out.println("User name not found or incorrect password");
            }
        }
    }

    private void logOff() {
        usersService.logOff(user);
    }

    private void disconnect() throws IOException, NullPointerException {
        clientSocket.close();
        in.close();
        out.close();
        if (user.isLogged()) {
            logOff();
        }
    }

    private void startMessaging() throws IOException, NullPointerException {
        while (true) {
            String message = in.readLine();
            if (message == null || message.equalsIgnoreCase("exit")) {
                break;
            }
            messagesService.save(new Message(user, message, LocalDateTime.now()));
            for (ClientHandler handler : Server.clientList) {
                if (handler != this) {
                    handler.out.println(clientName + ": " + message);
                }
            }
        }
    }
}
