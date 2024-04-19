package src.zad1.LanguageServerModules;

import src.zad1.ProxyServerModules.ProxyServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toMap;

public class LanguageServer implements Runnable{
    private final ExecutorService executorService;
    private String language;
    private Map<String, String> translations;

    public LanguageServer(Path dataPath) {
        executorService = Executors.newCachedThreadPool();
        try {
            Stream<String> stream = Files.lines(dataPath);
            language = dataPath.getFileName().toString().replaceFirst("[.][^.]+$", "");
            translations = stream.collect(toMap(l -> l.split(" ")[0], l -> l.split(" ")[1]));
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            Socket socket = new Socket(ProxyServer.getHost(),ProxyServer.listenerPort);

            PrintWriter printWriter = new PrintWriter(socket.getOutputStream(),true);
            printWriter.println(language);

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));


            while(!Thread.currentThread().isInterrupted()){
                String request = bufferedReader.readLine();
                executorService.submit(new LanguageServerHandler(request,translations));
            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

    }
}
