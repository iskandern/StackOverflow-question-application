package nasretdinovir.example.com.DataDefinition;

import java.util.ArrayList;
import java.util.List;

public class TestDataProvider {
    public static List<Question> getQuestions() {
        List<Question> questions = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            Owner owner = new Owner("image", String.valueOf(i));
            Question question = new Question("title", "body", owner, i, 2025282);
            questions.add(question);
        }
        return questions;
    }
    public static List<Answer> getAnswers() {
        List<Answer> answers = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            Answer answer = new Answer("very loooooooooooooooooooooooooon body" +
                    "looooooooooooooooooooooooooooooong" +
                    "loooooooooooooooooooooooooooooong", 300);
            answers.add(answer);
        }
        return answers;
    }
}
