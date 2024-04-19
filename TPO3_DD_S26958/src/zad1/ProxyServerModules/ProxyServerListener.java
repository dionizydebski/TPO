package src.zad1.ProxyServerModules;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class ProxyServerListener implements Runnable{
    private final int listenerPort;
    private final ProxyServer proxyServer;

    public ProxyServerListener(int listenerPort, ProxyServer proxyServer) {
        this.listenerPort = listenerPort;
        this.proxyServer = proxyServer;
    }

    @Override
    public void run() {
        try{
            ServerSocket serverSocket = new ServerSocket(listenerPort);
            while (!Thread.currentThread().isInterrupted()){
                Socket languageServerSocket = serverSocket.accept();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(languageServerSocket.getInputStream()));
                String language = bufferedReader.readLine();
                System.out.println(language);
                proxyServer.addLanguageServer(language,languageServerSocket);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
