package src.zad1.ProxyServerModules;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class ProxyServerHandler implements Runnable{
    private final Socket socket;
    private final ProxyServer proxyServer;

    public ProxyServerHandler(Socket socket, ProxyServer proxyServer) {
        this.socket = socket;
        this.proxyServer = proxyServer;
    }

    @Override
    public void run() {
        try {
            BufferedReader bufferedReaderClient = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
            String[] request = bufferedReaderClient.readLine().split(",");


            Socket languageServerSocket = proxyServer.getLanguageServer(request[1]);

            if(languageServerSocket != null){
                PrintWriter printWriterLanguageServer = new PrintWriter(languageServerSocket.getOutputStream(),true);
                printWriterLanguageServer.println(request[0] + "," + socket.getLocalAddress().getHostAddress() + "," + request[2]);
            }else {
                Socket clientSocket = new Socket(socket.getLocalAddress().getHostAddress(), Integer.parseInt(request[2]));
                PrintWriter printWriter = new PrintWriter(clientSocket.getOutputStream(),true);
                printWriter.println("No dictionary for such a language");
            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
