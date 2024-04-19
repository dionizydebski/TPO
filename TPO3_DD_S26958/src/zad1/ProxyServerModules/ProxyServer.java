package src.zad1.ProxyServerModules;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ProxyServer implements Runnable{
    public static int listenerPort = 12345;
    public static int handlerPort = 54321;
    private final ExecutorService executorService;
    private final Map<String, Socket> languageServers;
    private static InetAddress host;

    public ProxyServer() {
        this.languageServers = new HashMap<>();
        try {
            host = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        executorService = Executors.newCachedThreadPool();
    }

    @Override
    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(handlerPort);

            executorService.submit(new ProxyServerListener(listenerPort,this));
            while(!Thread.currentThread().isInterrupted()){
                Socket clientSocket = serverSocket.accept();
                executorService.submit(new ProxyServerHandler(clientSocket,this));
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public static InetAddress getHost() {
        return host;
    }
    public void addLanguageServer(String language, Socket socket){
        languageServers.put(language,socket);
    }

    public Socket getLanguageServer(String language) {
        return languageServers.get(language);
    }
}
