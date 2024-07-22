package Cabinet.models;


import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

public class Consultation extends RendezVous implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String nom;
    private String prénom;
    private int age;

    // Constructeurs, getters et setters

    public Consultation(LocalDateTime date, String nom, String prénom, int age) {
        super(date, (age < 18) ? 150 : 90); // 150 minutes for children, 90 for adults
        this.nom = nom;
        this.prénom = prénom;
        this.age = age;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prénom;
    }

    public void setPrenom(String prénom) {
        this.prénom = prénom;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getType() {
        return "Consultation";
    }
}
