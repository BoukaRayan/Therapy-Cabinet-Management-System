package Cabinet.models;

import java.io.Serial;
import java.io.Serializable;

public class CompteOrthophoniste implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private String nom;
    private String prenom;
    private String adresse;
    private String telephone;
    private String email;
    private String motDePasse;

    public CompteOrthophoniste(String nom, String prenom, String adresse, String telephone, String email, String motDePasse) {
        this.nom = nom;
        this.prenom = prenom;
        this.adresse = adresse;
        this.telephone = telephone;
        this.email = email;
        this.motDePasse = motDePasse;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public boolean authentifier(String email, String motDePasse) {
        return (this.email.equals(email)) && (this.motDePasse.equals(motDePasse));
    }
}