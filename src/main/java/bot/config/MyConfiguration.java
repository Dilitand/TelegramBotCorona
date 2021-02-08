package bot.config;


import bot.botApi.Bot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import javax.annotation.PostConstruct;


@org.springframework.context.annotation.Configuration
@ComponentScan(basePackages = {"bot"})
@PropertySource("classpath:bot.properties")
public class MyConfiguration {

    @Autowired
    Environment env;

    @Bean(name = "MyEnvironment")
    public Environment getEnvironment(){
        return env;
    }

    @PostConstruct
    public void botInit(){
        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            telegramBotsApi.registerBot(new Bot(env));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
