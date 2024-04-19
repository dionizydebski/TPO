package zad1;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Currency;
import java.util.Locale;

/**
 *
 *  @author Dębski Dionizy S26958
 *
 */


public class Service {
    private String country;
    private Locale locale;
    public Service(String country) {
        this.country = country;
        for (Locale locale : Locale.getAvailableLocales())
            if (country.equals(locale.getDisplayCountry(Locale.ENGLISH)))
                this.locale = locale;
    }

    public String getWeather(String city) {
        String weather = null;
        try {
            URL url = new URL("http://api.openweathermap.org/data/2.5/weather?q="+city+","+locale.getCountry()+"&appid="+"e97d2008addb930a453384aa6849d1aa"+"&units=metric");
            URLConnection request = url.openConnection();
            request.connect();

            JsonParser jsonParser = new JsonParser();
            JsonElement jsonElement = jsonParser.parse(new InputStreamReader(request.getInputStream()));
            weather = jsonElement.toString();
        } catch (Exception e) {}
        return weather;
    }

    public Double getRateFor(String currency) {
        Double rate = null;
        try {
            URL base = new URL("https://v6.exchangerate-api.com/v6/651af457d44a179ab5abebe1/latest/"+currency);
            URLConnection request = base.openConnection();
            request.connect();

            JsonParser jsonParser = new JsonParser();
            JsonElement jsonElement = jsonParser.parse(new InputStreamReader(request.getInputStream()));
            
            Double selectedCurrency = jsonElement.getAsJsonObject().get("conversion_rates").getAsJsonObject().get(currency).getAsDouble();
            Double countryCurrency = jsonElement.getAsJsonObject().get("conversion_rates").getAsJsonObject().get(String.valueOf(Currency.getInstance(locale))).getAsDouble();
            rate = selectedCurrency/countryCurrency;
        } catch (Exception e) {}
        return rate;
    }

    public Double getNBPRate() {
        Double rate = null;
        try {
            URL base = new URL("http://api.nbp.pl/api/exchangerates/rates/a/"+Currency.getInstance(locale)+"/?format=json");
            URLConnection request = base.openConnection();
            request.connect();

            JsonParser jsonParser = new JsonParser();
            JsonElement jsonElement = jsonParser.parse(new InputStreamReader(request.getInputStream()));

            rate = jsonElement.getAsJsonObject().get("rates").getAsJsonArray().get(0).getAsJsonObject().get("mid").getAsDouble();

        } catch (Exception e) {}
        if(rate == null){
            try {
                URL base = new URL("http://api.nbp.pl/api/exchangerates/rates/b/"+Currency.getInstance(locale)+"/?format=json");
                URLConnection request = base.openConnection();
                request.connect();

                JsonParser jsonParser = new JsonParser();
                JsonElement jsonElement = jsonParser.parse(new InputStreamReader(request.getInputStream()));

                rate = jsonElement.getAsJsonObject().get("rates").getAsJsonArray().get(0).getAsJsonObject().get("mid").getAsDouble();

            } catch (Exception e) {}
        }
        if (rate == null)
            rate = 1.;

        return 1/rate;
    }

    public String getBetterWeather(String w){
        JsonParser jsonParser = new JsonParser();
        JsonElement jsonElement = jsonParser.parse(w);

        String weather = jsonElement.getAsJsonObject().get("weather").getAsJsonArray().get(0).getAsJsonObject().get("main").getAsString();
        return weather;
    }
}
