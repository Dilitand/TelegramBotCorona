package bot.models.coronamodels;

import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;

@Component
public abstract class Questions {
    LinkedHashMap<String, String[]> questions = new LinkedHashMap();
    LinkedHashMap<String,String> answers = new LinkedHashMap<>();

    public LinkedHashMap<String, String[]> getQuestions() {
        return questions;
    }

    public LinkedHashMap<String, String> getAnswers() {
        return answers;
    }
}
