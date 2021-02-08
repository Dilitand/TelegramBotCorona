package bot.models.coronamodels;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class QuestionsV1 extends Questions {

    @PostConstruct
    public void fillQuestion(){
        questions.put("1 вопрос. Температура тела?",new String[]{"до 36","36-37","37-38","выше 38"});
        questions.put("2 вопрос. Одышка?",new String[]{"да,нет"});
        questions.put("3 вопрос. Затрудненное дыхание?",new String[]{"да,нет"});
        questions.put("4 вопрос. Боли в груди?",new String[]{"да,нет"});
        questions.put("5 вопрос. Слабость?",new String[]{"да,нет"});
        questions.put("6 вопрос. Потеря обоняния?",new String[]{"да,нет"});
        questions.put("7 вопрос. Потеря вкуса?",new String[]{"да,нет"});
    }
}
