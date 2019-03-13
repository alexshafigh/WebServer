package Server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class WebServer {
    private static ServerSocket serverSocket;
    private static Socket socket;
    private static InputStream input;

    public static void main(String[] args) throws IOException {

        serverSocket = new ServerSocket(8889 );
        socket = serverSocket.accept();
        input = new BufferedInputStream(socket.getInputStream());
        StringBuffer buffer = new StringBuffer();
        int read = 0;
        while ( (read = input.read()) != -1 ) {
            buffer.append((char) read);
            if ( buffer.indexOf("HTTP") != -1 )
                break;
        }
        String command = buffer.substring(0,3);
        String path = buffer.substring(5,buffer.indexOf("HTTP"));
        switch (command) {
            case "GET": {
                String fileAddress = "E:/web/"+path;
                File file = new File(fileAddress);
                DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
                PrintStream printStream = new PrintStream(socket.getOutputStream());
                printStream.println("HTTP/1.1 200 OK");
                 printStream.println("Server: Java HTTP Server from Alex Shafigh : 1.0");
                printStream.println("Content-Type: text/html");
                printStream.println();
                printStream.flush();
                InputStream stream  = new FileInputStream(file) ;
                int fis = 0;
                while ((fis = stream.read()) != -1 )
                    printStream.write(fis);
                printStream.flush();
                printStream.close();
                outputStream.close();
            }
            case "POST":{
            }
            case "PUT":{
            }
        }
        socket.close();
    }
}
