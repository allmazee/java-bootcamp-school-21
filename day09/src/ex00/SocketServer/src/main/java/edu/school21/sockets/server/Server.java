package edu.school21.sockets.server;

import edu.school21.sockets.repositories.UsersRepository;
import edu.school21.sockets.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

@Component
public class Server {
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private BufferedReader in;
    private PrintWriter out;
    private final UsersRepository usersRepository;
    private final UsersService usersService;

    @Autowired
    public Server(UsersRepository usersRepository, UsersService usersService) {
        this.usersRepository = usersRepository;
        this.usersService = usersService;
    }

    public void start(int port) {
        try {
            try {
                serverSocket = new ServerSocket(port);
                clientSocket = serverSocket.accept();
                System.out.println("Server is online");
                out = new PrintWriter(clientSocket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                out.println("Hello from Server!");
                String command;
                String name = "";
                String password = "";
                while (true) {
                    command = in.readLine();
                    if (command.equalsIgnoreCase("signUp")) {
                        break;
                    } else {
                        out.println("Wrong command. Example: signUp");
                    }
                }
                out.println("Enter username:");
                name = in.readLine();
                out.println("Enter password:");
                password = in.readLine();
                boolean isSignedUp = usersService.signUp(name, password);
                if (isSignedUp) {
                    out.println("Successful!");
                } else {
                    out.println("Registration failed :(");
                }
            } finally {
                System.out.println("Server is offline");
                serverSocket.close();
                clientSocket.close();
                in.close();
                out.close();
            }
        } catch (IOException ex) {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

}
