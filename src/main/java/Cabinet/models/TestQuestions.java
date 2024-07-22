package Cabinet.models;

import java.io.Serial;
import java.util.ArrayList;
import java.util.List;

public class TestQuestions extends Test implements java.io.Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private List<Question> questions;

    public TestQuestions(String nom, String capacite) {
        super(nom, capacite);
        questions = new ArrayList<Question>();
    }

    public boolean ajouterQuestion(Question e){
        return questions.add(e);
    }

    public boolean supprimerQuestion(Question e){
        return questions.remove(e);
    }

    public List<Question> getQuestions() {
        return questions;
    }
}
