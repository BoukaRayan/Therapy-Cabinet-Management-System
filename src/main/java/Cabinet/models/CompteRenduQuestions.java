package Cabinet.models;

import java.io.Serial;
import java.util.Map;

public class CompteRenduQuestions extends CompteRendu implements java.io.Serializable{
    @Serial
    private static final long serialVersionUID = 1L;
    private Map<Question, Integer> scoresQuestions;

    public CompteRenduQuestions(Test test, String conclusion, Map<Question, Integer> scoresQuestions) {
        super(test, conclusion);
        this.scoresQuestions = scoresQuestions;
    }

    public double calculerScore(){
        int score = 0;
        int cpt = 0;
        for (Integer s : scoresQuestions.values()){
            cpt++;
            score += s;
        }
        return (double) score / cpt;
    }
}
