package bot.models.coronamodels;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class QuestionsV1 extends Questions {

    @PostConstruct
    public void fillQuestion(){

        questions.add(new String[]{"1 вопрос. Температура тела?","до 36","36-37","37-38","выше 38"});
        questions.add(new String[]{"2 вопрос. Одышка?","да,нет"});
        questions.add(new String[]{"3 вопрос. Затрудненное дыхание?","да,нет"});
        questions.add(new String[]{"4 вопрос. Боли в груди?","да,нет"});
        questions.add(new String[]{"5 вопрос. Слабость?","да,нет"});
        questions.add(new String[]{"6 вопрос. Потеря обоняния?","да,нет"});
        questions.add(new String[]{"7 вопрос. Потеря вкуса?","да,нет"});
    }
}
