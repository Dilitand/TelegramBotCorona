package bot.models.coronamodels;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

@Component
public abstract class Questions {
    ArrayList<String[]> questions = new ArrayList<>();
    LinkedHashMap<String,String> answers = new LinkedHashMap<>();

    public ArrayList<String[]> getQuestions() {
        return questions;
    }

    public LinkedHashMap<String, String> getAnswers() {
        return answers;
    }
}
