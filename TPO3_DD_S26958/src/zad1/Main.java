package src.zad1;

import src.zad1.LanguageServerModules.LanguageServer;
import src.zad1.ProxyServerModules.ProxyServer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        new Thread(new ProxyServer()).start();
        try {
            Stream<Path> stream = Files.list(Paths.get("src/zad1/Data"));
            List<Path> translations = stream.toList();
            for (Path p : translations){
                new Thread(new LanguageServer(p)).start();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        javax.swing.SwingUtilities.invokeLater(Ui::new);
    }
}
