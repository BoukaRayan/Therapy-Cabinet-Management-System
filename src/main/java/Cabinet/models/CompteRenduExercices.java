package Cabinet.models;

import java.io.Serial;
import java.util.Map;

public class CompteRenduExercices extends CompteRendu implements java.io.Serializable{
    @Serial
    private static final long serialVersionUID = 1L;
    private Map<Exercice, Integer> scoresExercies;

    public CompteRenduExercices(Test test, String conclusion, Map<Exercice, Integer> scoresExercies) {
        super(test, conclusion);
        this.scoresExercies = scoresExercies;
    }

    public double calculerScore(){
        int score = 0;
        int cpt = 0;
        for (Integer s : scoresExercies.values()){
            cpt++;
            score += s;
        }
         return (double) score / cpt;
    }
}
