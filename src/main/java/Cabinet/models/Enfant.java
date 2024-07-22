package Cabinet.models;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

public class Enfant extends Patient implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private String classeEtudes;

    public Enfant(String nom, String prenom, LocalDate dateNais, String lieuNais, String adresse, String numeroTel, String classeEtudes){
        super(nom, prenom, dateNais, lieuNais, adresse, numeroTel);
        this.classeEtudes = classeEtudes;
    }

    public String getClasseEtudes() {
        return classeEtudes;
    }

    public void setClasseEtudes(String classeEtudes) {
        this.classeEtudes = classeEtudes;
    }
}

