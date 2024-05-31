import java.io.*;
import java.net.*;

public class InitialTCPServer {
    public static void main(String args[]) {
        Integer port = 1444;
        try {
            if (args.length >= 2 && args[0].equals("-port"))
                port = Integer.parseInt(args[1]);

            System.out.println("Listen to Port: " + port);
            ServerSocket welcomeSocket = new ServerSocket(port);

            while (true) {
                System.out.println("Waiting for Client to connect...");
                Socket connectionSocket = welcomeSocket.accept();

                BufferedReader inFromClient = new BufferedReader(
                        new InputStreamReader(connectionSocket.getInputStream()));

                String line;
                String firstLine = inFromClient.readLine() + "\n";
                String request = firstLine;

                while (!(line = inFromClient.readLine()).isEmpty()) {
                    request += line + "\n";
                }

//                String output = request.toUpperCase();
                String output = request;
                OutputStream os = connectionSocket.getOutputStream();
                os.write(output.getBytes("UTF-8"));

                connectionSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


