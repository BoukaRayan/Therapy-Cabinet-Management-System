package Cabinet.models;

import Cabinet.models.enums.CategorieTrouble;

import java.io.Serial;

public class Trouble implements java.io.Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private String nom;
    private CategorieTrouble categorie;
    private static int cptTroubles = 0;
    private int numero;

    public Trouble(String nom, CategorieTrouble categorie) {
        this.numero = ++cptTroubles;
        this.nom = nom;
        this.categorie = categorie;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public CategorieTrouble getCategorie() {
        return categorie;
    }

    public void setCategorie(CategorieTrouble categorie) {
        this.categorie = categorie;
    }

    public int getNumero() {
        return numero;
    }
}