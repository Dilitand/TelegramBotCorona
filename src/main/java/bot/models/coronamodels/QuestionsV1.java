package bot.models.coronamodels;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class QuestionsV1 extends Questions {

    @PostConstruct
    public void fillQuestion(){
        questions.put("Температура тела?",new String[]{"до 36","36-37","37-38","выше 38"});
        questions.put("Одышка?",new String[]{"да,нет"});
        questions.put("Затрудненное дыхание?",new String[]{"да,нет"});
        questions.put("Боли в груди?",new String[]{"да,нет"});
        questions.put("Слабость?",new String[]{"да,нет"});
        questions.put("Потеря обоняния?",new String[]{"да,нет"});
        questions.put("Потеря вкуса?",new String[]{"да,нет"});
    }
}
