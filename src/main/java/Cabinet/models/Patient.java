package Cabinet.models;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

public abstract class Patient implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    protected String nom;
    protected String prenom;
    protected LocalDate dateNais;
    protected String lieuNais;
    protected String adresse;
    protected String numeroTel;

    public Patient(String nom, String prenom, LocalDate dateNais, String lieuNais, String adresse, String numeroTel){
        this.nom = nom;
        this.prenom = prenom;
        this.dateNais = dateNais;
        this.lieuNais = lieuNais;
        this.adresse = adresse;
        this.numeroTel = numeroTel;
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

    public LocalDate getDateNais() {
        return dateNais;
    }

    public void setDateNais(LocalDate dateNais) {
        this.dateNais = dateNais;
    }

    public String getLieuNais() {
        return lieuNais;
    }

    public void setLieuNais(String lieuNais) {
        this.lieuNais = lieuNais;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getNumeroTel() {
        return numeroTel;
    }

    public void setNumeroTel(String numeroTel) {
        this.numeroTel = numeroTel;
    }
}

