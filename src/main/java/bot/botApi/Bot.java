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

    private int currentPosition = 0;
    private boolean isAlive = false;
    private String lastAnswer = "";
    private String lastQuestion = "";


    public Bot() {
    }

    public Bot(@Qualifier("MyEnvironment") Environment environment, @Qualifier("QuestionV1") Questions questions) {
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
        if (message != null && message.hasText() && !lastQuestion.contains(" вопрос. ")) {
            lastAnswer = update.getMessage().toString();
            switch (message.getText()) {
                case "/start":
                    sendMsg(message, "Нажмите пройти опрос для начала", null);
                    currentPosition = 0;
                    break;
                case "/restart":
                case "/Пройти опрос заново":
                    sendMsg(message, "Рестарт опросника", null);
                    currentPosition = 0;
                    break;
            }
        } else if (message.getContact() != null || lastQuestion.contains(" вопрос. ")) {
            if (currentPosition == questions.getQuestions().size() - 1) {
                System.out.println("end");
            } else {
                lastQuestion = questions.getQuestions().get(currentPosition)[0];
                sendMsg(message, questions.getQuestions().get(currentPosition)[0], questions.getQuestions().get(currentPosition));
                System.out.println(message.getText());
                System.out.println(lastQuestion);
                questions.getAnswers().put(lastQuestion, lastAnswer);
                currentPosition++;
            }
        }
    }

    @Override
    public String getBotUsername() {
        return environment.getProperty("bot.username");
    }

    //Перенести в сервис
    private void sendMsg(Message message, String text, String[] questions) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setReplyToMessageId(message.getMessageId());
        sendMessage.setText(text);
        try {
            setButtons(sendMessage, message, questions);
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void setButtons(SendMessage sendMessage, Message message, String[] questions) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);

        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        List<KeyboardRow> keyboardRows = new ArrayList<>();
        KeyboardRow keyRow1 = new KeyboardRow();
        KeyboardRow keyRow2 = new KeyboardRow();

        if (message.getText() != null
                && (message.getText().equalsIgnoreCase("/start")
                || message.getText().equalsIgnoreCase("/restart")
                || message.getText().equalsIgnoreCase("/Пройти опрос заново")
                || message.getText().equalsIgnoreCase("/shareNumber"))) {
            keyRow1.add(new KeyboardButton("/Пройти опрос").setRequestContact(true));
        } else if (questions != null) {
            for (int i = 1; i < questions.length; i++) {
                keyRow1.add(new KeyboardButton(questions[i]));
            }
            keyRow2.add(new KeyboardButton("/Пройти опрос заново"));
        } else {
            keyRow1.add(new KeyboardButton("/Пройти опрос").setRequestContact(true));
        }
        keyboardRows.add(keyRow1);
        keyboardRows.add(keyRow2);
        replyKeyboardMarkup.setKeyboard(keyboardRows);
    }

}
