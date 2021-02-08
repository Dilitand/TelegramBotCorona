package bot.botApi;

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
    private Map opros;
    private Contact contact = new Contact();
    private String lastAnswer = "";
    private String lastQuestion = "";
    private Map<String,String> answers = new LinkedHashMap();

    public Bot() {
    }

    public Bot(@Qualifier("MyEnvironment")Environment environment) {
        this.environment = environment;
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
                    break;
                case "/help":
                    //execute(sendMessage.setText(message.getText() + "how can i help"));
                    sendMsg(message, "Введите город (иностранные на английском)");
                    break;
                case "/settings":
                    sendMsg(message, "Что будем настраивать");
                    break;
                case "/restart":
                case "/Пройти опрос заново":
                    sendMsg(message,"Рестарт опросника");
                    break;
                case "12345":
                    sendMsg(message,"12345");
                    break;
                //weahter example
                /*
                default:
                    try {
                        sendMsg(message, WeatherRequest.getWeather(message.getText(), new SimpleModelWeather()));
                    } catch (IOException e) {
                        sendMsg(message,"Город не найден");
                    }
                */
            }
        } else if (message.getContact() != null) {
            lastQuestion = "Первый вопрос!";
            lastAnswer = message.getText();
            sendMsg(message, lastQuestion);
        } else if (lastQuestion.equalsIgnoreCase("Первый вопрос!")) {
            lastAnswer = message.getText();
            answers.put(lastQuestion,lastAnswer);
            lastQuestion = "Второй вопрос!";
            sendMsg(message, lastQuestion);
        } else if (lastQuestion.equalsIgnoreCase("Второй вопрос!")) {
            lastAnswer = message.getText();
            answers.put(lastQuestion,lastAnswer);
            lastQuestion = "Третий вопрос!";
            sendMsg(message, lastQuestion);
        } else if (lastQuestion.equalsIgnoreCase("Третий вопрос!")) {
            lastAnswer = message.getText();
            answers.put(lastQuestion,lastAnswer);
            System.out.println(answers.values().toString());
            SendMessage sendMessage = new SendMessage();
            sendMessage.enableMarkdown(true);
            sendMessage.setChatId(message.getChatId().toString());
            sendMessage.setReplyToMessageId(message.getMessageId());
            sendMessage.setText("Спасибо за прохождение опроса");
            try {
                setButtons(sendMessage, message);
                execute(sendMessage);
            } catch (Exception e) {
                e.printStackTrace();
            }
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
            setButtons(sendMessage,message);
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
        /*
        * depricated
        try {
            sendMessage(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
        * */
    }


    public void setButtons(SendMessage sendMessage, Message message){
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
        } else if (sendMessage.getText().equalsIgnoreCase("Первый вопрос!")) {
            System.out.println("1 вопрос");
            KeyboardRow keyRow2 = new KeyboardRow();
            keyRow1.add(new KeyboardButton("1"));
            keyRow1.add(new KeyboardButton("2"));
            keyRow2.add(new KeyboardButton("/Пройти опрос заново"));
            keyboardRows.add(keyRow2);
        } else if (sendMessage.getText().equalsIgnoreCase("Второй вопрос!")) {
            System.out.println("2 вопрос");
            KeyboardRow keyRow2 = new KeyboardRow();
            keyRow1.add(new KeyboardButton("3"));
            keyRow1.add(new KeyboardButton("4"));
            keyRow2.add(new KeyboardButton("/Пройти опрос заново"));
            keyboardRows.add(keyRow2);
        } else if (sendMessage.getText().equalsIgnoreCase("Третий вопрос!")) {
            System.out.println("3 вопрос");
            KeyboardRow keyRow2 = new KeyboardRow();
            keyRow1.add(new KeyboardButton("5"));
            keyRow1.add(new KeyboardButton("6"));
            keyRow2.add(new KeyboardButton("/Пройти опрос заново"));
            keyboardRows.add(keyRow2);
        } else {
            keyRow1.add(new KeyboardButton("/Пройти опрос").setRequestContact(true));
    }

        keyboardRows.add(keyRow1);
        replyKeyboardMarkup.setKeyboard(keyboardRows);
    }

}
