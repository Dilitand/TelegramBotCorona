package bot.botApi;

import bot.models.coronamodels.Questions;
import bot.models.openweathermodel.FullModelWeather;
import bot.models.openweathermodel.SimpleModelWeather;
import bot.parser.WeatherRequest;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Contact;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.io.IOException;
import java.util.*;

@Component("TelegramBot")
public class Bot extends TelegramLongPollingBot {

    private Environment environment;
    private Questions questions;

    private boolean isAlive = false;
    private String lastAnswer = "";
    private String lastQuestion = "";


    public Bot() {
    }

    public Bot(@Qualifier("MyEnvironment")Environment environment, @Qualifier("QuestionV1") Questions questions) {
        this.environment = environment;
        this.questions = questions;
    }

    @Override
    public String getBotToken() {
        return environment.getProperty("bot.token");
    }

    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        if (message != null && message.hasText() && !lastQuestion.contains("вопрос!")){
            lastAnswer = update.getMessage().toString();
            switch (message.getText()) {
                case "/start":
                    sendMsg(message, "Нажмите пройти опрос для начала");
                    isAlive = false;
                    break;
                case "/restart":
                case "/Пройти опрос заново":
                    sendMsg(message,"Рестарт опросника");
                    isAlive = false;
                    break;
            }
        } else if (message.getContact() != null) {
            isAlive = true;
            Iterator<Map.Entry<String,String[]>> iterator = questions.getQuestions().entrySet().iterator();
            while (iterator.hasNext()){
                if (isAlive) {
                    Map.Entry<String, String[]> entry = iterator.next();
                    sendMsg(message, entry.getKey());
                    questions.getAnswers().put(lastQuestion,lastAnswer);
                } else {
                    break;
                }
            }
            System.out.println(questions.getAnswers()   );
         }
    }

    @Override
    public String getBotUsername() {
        return environment.getProperty("bot.username");
    }

    //Перенести в сервис
    private void sendMsg(Message message, String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setReplyToMessageId(message.getMessageId());
        sendMessage.setText(text);
        try {
            setButtons(sendMessage,message,null);
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void setButtons(SendMessage sendMessage, Message message, Map.Entry<String,String[]> entry){
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);

        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        List<KeyboardRow> keyboardRows = new ArrayList<>();
        KeyboardRow keyRow1 = new KeyboardRow();

        if (message.getText() != null
                && (message.getText().equalsIgnoreCase("/start")
                || message.getText().equalsIgnoreCase("/restart")
                || message.getText().equalsIgnoreCase("/Пройти опрос заново")
                || message.getText().equalsIgnoreCase("/shareNumber"))) {
            keyRow1.add(new KeyboardButton("/Пройти опрос").setRequestContact(true));
        } else if (entry != null) {
            KeyboardRow keyRow2 = new KeyboardRow();
            for (String value : entry.getValue()) {
                keyRow1.add(new KeyboardButton(value));
            }
            keyRow2.add(new KeyboardButton("/Пройти опрос заново"));
            keyboardRows.add(keyRow1);
            keyboardRows.add(keyRow2);
        } else {
            keyRow1.add(new KeyboardButton("/Пройти опрос").setRequestContact(true));
    }
        replyKeyboardMarkup.setKeyboard(keyboardRows);
    }

}
