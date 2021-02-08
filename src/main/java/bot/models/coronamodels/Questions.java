package bot.models.coronamodels;

import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;

@Component
public abstract class Questions {
    Map<String, String[]> questions = new LinkedHashMap();

    public Map<String, String[]> getQuestions() {
        return questions;
    }
}
