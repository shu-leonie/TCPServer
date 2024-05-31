import java.io.*;
import java.net.*;

public class myTCPServer {
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

                // read all request lines and save the first line
                String line;
                String firstLine = inFromClient.readLine() + "\n";
                String request = firstLine;

                while (!(line = inFromClient.readLine()).isEmpty()) {
                    request += line + "\n";
                }

                // read filename from request
                String[] requestLine = firstLine.split(" ");
                String filename = requestLine[1];


//                String filename;
//                if (firstLine.contains("/")) {
//                    filename = firstLine.substring(firstLine.indexOf("/") + 1, firstLine.indexOf("HTTP"));
//                } else {
//                    filename = "index";
//                }

                if (filename.equals("/")) {
                    filename = "index";
                }

                String path = "/home/leonie/Dokumente/THU/Semester2/Rechnernetze/" + filename + ".html";

                // read html file
                BufferedReader htmlReader = new BufferedReader(new FileReader(path));
                String output = "";
                while ((line = htmlReader.readLine()) != null) {
                    output += line + "\n";
                }
                htmlReader.close();


                // send response
                OutputStream os = connectionSocket.getOutputStream();

//                os.write(output.getBytes("UTF-8"));

                os.write("HTTP/1.1 200 OK\r\n".getBytes());
                os.write(("ContentType: text/html\r\n").getBytes());
                os.write("\r\n".getBytes());
                os.write(output.getBytes());
                os.write("\r\n\r\n".getBytes());
                os.flush();

                connectionSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
