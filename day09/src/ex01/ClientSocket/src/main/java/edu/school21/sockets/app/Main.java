package edu.school21.sockets.app;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import edu.school21.sockets.client.Client;

@Parameters(separators = "=")
public class Main {
    @Parameter(names = {"--server-port"})
    int port;
    public static void main(String[] args) {
        Main main = new Main();
        JCommander.newBuilder()
                .addObject(main)
                .build()
                .parse(args);
        Client client = new Client();
        client.connect(main.port);
    }
}
