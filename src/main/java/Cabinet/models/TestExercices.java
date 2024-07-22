package Cabinet.models;

import java.io.Serial;
import java.util.ArrayList;
import java.util.List;

public class TestExercices extends Test implements java.io.Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private List<Exercice> exercices;

    public TestExercices(String nom, String capacite) {
        super(nom, capacite);
        exercices = new ArrayList<Exercice>();
    }

    public boolean ajouterExercice(Exercice e){
        return exercices.add(e);
    }

    public boolean supprimerExercice(Exercice e){
        return exercices.remove(e);
    }

    public List<Exercice> getExercices() {
        return exercices;
    }
}
