import java.io.*;
import java.net.*;

public class myTCPClient {
    public static void main(String args[]) {
        BufferedReader inFromUser =
                new BufferedReader(
                        new InputStreamReader(System.in));

        Integer port = 1444;

        try {
            Socket clientSocket = new Socket("localhost", port);

            BufferedReader inFromServer =
                    new BufferedReader(
                            new InputStreamReader(clientSocket.getInputStream()));

            PrintWriter printToServer =
                    new PrintWriter(clientSocket.getOutputStream(), true);

            System.out.print("Type in text:\n");

            StringBuilder requestBuilder = new StringBuilder();
            String userinput;

            while ((userinput = inFromUser.readLine()) != null) {
                requestBuilder.append(userinput).append("\n");
            }

            String request = requestBuilder.toString();


//            request = "GET /infotext.html HTTP/1.1\nHost: www.example.net";

            printToServer.println(request);

            StringBuilder responseBuilder = new StringBuilder();
            String serverOutput;

            while ((serverOutput = inFromServer.readLine()) != null) {
                responseBuilder.append(serverOutput).append("\n");
            }

            String response = responseBuilder.toString();

            System.out.println("Server's response:\n" + response);
            clientSocket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
