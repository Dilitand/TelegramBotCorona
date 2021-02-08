package bot;

import bot.botApi.Bot;
import bot.config.MyConfiguration;
import bot.models.openweathermodel.FullModelWeather;
import bot.parser.WeatherRequest;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MyConfiguration.class);
        Bot bot =  (Bot) context.getBean("TelegramBot");
    }
}
