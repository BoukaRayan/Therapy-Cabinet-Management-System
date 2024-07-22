package Cabinet.models;

import java.io.Serial;
import java.time.LocalDate;
import java.io.Serializable;

public class Adulte extends Patient implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private String diplome;
    private String profession;

    public Adulte(String nom, String prenom, LocalDate dateNais, String lieuNais, String adresse, String numeroTel, String diplome, String profession){
        super(nom, prenom, dateNais, lieuNais, adresse, numeroTel);
        this.diplome = diplome;
        this.profession = profession;
    }

    public String getDiplome() {
        return diplome;
    }

    public void setDiplome(String diplome) {
        this.diplome = diplome;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getProfession() {
        return profession;
    }
}