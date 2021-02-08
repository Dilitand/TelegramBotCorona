package bot.parser;

import bot.models.openweathermodel.FullModelWeather;
import bot.models.openweathermodel.ModelWeather;
import bot.models.openweathermodel.SimpleModelWeather;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class WeatherRequest {

    public static String getWeather(String city, SimpleModelWeather model) throws IOException {
        URL url = new URL("http://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=e9606755833f95dbee697ae2cac50cda");

        Scanner scanner = new Scanner((InputStream) url.getContent());
        String resultString = "";
        while (scanner.hasNext()) {
            resultString += scanner.nextLine();
        }


        JSONObject object = new JSONObject(resultString);
        model.setName(object.getString("name"));

        JSONObject mainObject = object.getJSONObject("main");
        model.setTemp(mainObject.getDouble("temp"));
        model.setHumidity(mainObject.getDouble("humidity"));

        JSONArray getArray = object.getJSONArray("weather");
        for (int i = 0; i < getArray.length(); i++) {
            JSONObject object2 = getArray.getJSONObject(i);
            model.setIcon((String)object2.get("icon"));
            model.setMain((String)object2.get("main"));
        }
        //System.out.println(result.getWeather()[0].getDescription());
        return "Город: " + model.getName() + "\n" +
                "Температура: " + model.getTemp()  + "C" + "\n" +
                "Влажность: " + model.getHumidity() + "\n" +
                "http://openweathermap.org/img/w/" + model.getIcon() + ".png";

    }

    public static String getFullWeather(String city, FullModelWeather model) throws IOException {
        URL url = new URL("http://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=e9606755833f95dbee697ae2cac50cda");

        Scanner scanner = new Scanner((InputStream) url.getContent());
        String resultString = "";
        while (scanner.hasNext()) {
            resultString += scanner.nextLine();
        }

        FullModelWeather result = (FullModelWeather) JsonUtils.jsonToObject(resultString, FullModelWeather.class);
        return result.toString();
    }
}
