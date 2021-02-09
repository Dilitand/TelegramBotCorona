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
    private List systemCom = Arrays.asList("/start", "/restart", "/Пройти опрос заново");

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
        if (message != null && message.hasText() && message.getText() != null && !isAlive && systemCom.contains(message.getText())) {
            lastAnswer = message.getText();
            switch (message.getText()) {
                case "/start":
                    resetAnswers();
                    sendMsg(message, "Нажмите пройти опрос для начала", null);
                    break;
                case "/restart":
                case "/Пройти опрос заново":
                    resetAnswers();
                    sendMsg(message, "Рестарт опросника", null);
                    break;
            }
        }
        //Если прилетел контакт то можно начинать
        else if ((message.getContact() != null || isAlive)) {
            //Проверка прерывания опросника
            if (systemCom.contains(message.getText()) && isAlive) {
                resetAnswers();
                sendMsg(message, "Рестарт опросника", null);
            }
            //Нужно доделать чтобы опросник проверял ответы
            else if (currentPosition == questions.getQuestions().size() && isAlive) {
                lastAnswer = message.getText();
                questions.getAnswers().put(questions.getQuestions().get(currentPosition - 1)[0], lastAnswer);
                resetAnswers();
                sendMsg(message, "Спасибо опрос завершен.\n" + questions.getAnswers().toString(), null);
                //sendMsg(message, "Рестарт опросника", null);
            } else {
                isAlive = true;
                //lastQuestion = questions.getQuestions().get(currentPosition)[0];
                //sendMsg(message, lastQuestion, questions.getQuestions().get(currentPosition));
                lastAnswer = message.getText();
                if (lastAnswer != null)  {
                    if (Arrays.asList(questions.getQuestions().get(currentPosition-1)).contains(lastAnswer)) {
                        questions.getAnswers().put(questions.getQuestions().get(currentPosition - 1)[0], lastAnswer);
                    } else
                    {
                        currentPosition--;
                    }
                }

                lastQuestion = questions.getQuestions().get(currentPosition)[0];
                sendMsg(message, lastQuestion, questions.getQuestions().get(currentPosition));
                currentPosition++;
            }
        }
    }

    private void resetAnswers(){
        currentPosition = 0;
        lastAnswer = "";
        lastQuestion = "";
        isAlive = false;
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
                || message.getText().equalsIgnoreCase("/Пройти опрос заново"))) {
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