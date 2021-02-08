package bot.config;


import bot.botApi.Bot;
import bot.models.coronamodels.Questions;
import bot.models.coronamodels.QuestionsV1;
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
@ComponentScan(basePackages = {"bot","bot.models.coronamodels"})
@PropertySource("classpath:bot.properties")
public class MyConfiguration {

    @Autowired
    Environment env;
    @Autowired
    Questions questions;

    @Bean(name = "MyEnvironment")
    public Environment getEnvironment(){
        return env;
    }

    @Bean(name = "QuestionV1")
    public Questions getQuestions(){
        return new QuestionsV1();
    }

    @PostConstruct
    public void botInit(){
        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            telegramBotsApi.registerBot(new Bot(env,questions));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
