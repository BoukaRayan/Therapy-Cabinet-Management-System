package Cabinet.models;

import java.io.Serial;
import java.io.Serializable;

public class Exercice implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private String consigne;
    private String materiel;
    private int numero;
    private static int cptExercices = 0;

    public Exercice(String consigne, String materiel){
        this.consigne = consigne;
        this.materiel = materiel;
        this.numero = ++cptExercices;
    }

    public String getConsigne() {
        return consigne;
    }

    public void setConsigne(String consigne) {
        this.consigne = consigne;
    }

    public String getMateriel() {
        return materiel;
    }

    public void setMateriel(String materiel) {
        this.materiel = materiel;
    }

    public int getNumero() {
        return numero;
    }
}

