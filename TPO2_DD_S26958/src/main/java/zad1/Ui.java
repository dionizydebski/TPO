package zad1;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.util.Optional;

public class Ui extends Application {

    @Override
    public void start(Stage stage) {
        Service s = new Service("Poland");

        BorderPane root = new BorderPane();

        Label weather = new Label("Weather: " + s.getBetterWeather(s.getWeather("Warsaw")));
        Label currency = new Label("Currency rate: " + s.getRateFor("USD"));
        Label plnCurrency = new Label("PLN currency rate: " + s.getNBPRate());

        WebView browser = new WebView();
        WebEngine webEngine = browser.getEngine();
        String www  = "https://en.wikipedia.org/wiki/" + "Warsaw";
        webEngine.load(www);

        Button button = new Button("Change data");

        button.setOnAction(_ -> {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("New data");
            dialog.setHeaderText("Please insert new data: ");

            Optional<String> result = dialog.showAndWait();

            result.ifPresent(value ->{
                String[] arr = value.split(" ");
                Service tmp = new Service(arr[0]);
                weather.setText("Weather: " + tmp.getBetterWeather(tmp.getWeather(arr[1])));
                currency.setText("Currency rate: " + tmp.getRateFor(arr[2]));
                plnCurrency.setText("PLN currency rate: " + tmp.getNBPRate());
                webEngine.load("https://en.wikipedia.org/wiki/" + arr[1]);
            });
        });

        HBox topRoot = new HBox(weather,currency,plnCurrency,button);
        topRoot.setAlignment(Pos.CENTER);
        topRoot.setSpacing(100);

        root.setTop(topRoot);
        root.setCenter(browser);

        stage.setScene(new Scene(root,1080,720));
        stage.setTitle("TPO03");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}