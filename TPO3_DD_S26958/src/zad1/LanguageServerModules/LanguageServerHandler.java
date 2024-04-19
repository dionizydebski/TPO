package src.zad1.LanguageServerModules;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Map;

public class LanguageServerHandler implements Runnable{
    private final String[] clientRequest;
    private final Map<String, String> translations;

    public LanguageServerHandler(String clientRequest, Map<String, String> translations) {
        this.clientRequest = clientRequest.split(",");
        this.translations = translations;
    }

    @Override
    public void run() {
        try {
            Socket socket = new Socket(clientRequest[1],Integer.parseInt(clientRequest[2]));
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream(),true);
            String response = translations.getOrDefault(clientRequest[0],"There's no translation");
            printWriter.println(response);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

    }
}
