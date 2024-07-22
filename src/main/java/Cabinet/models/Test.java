package Cabinet.models;

import java.io.Serial;

public abstract class Test implements java.io.Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    protected String nom;
    protected String capacite;
    protected int numero;
    private static int cptTests = 0;

    public Test(String nom, String capacite){
        this.numero = ++cptTests;
        this.nom = nom;
        this.capacite = capacite;
    }

    public String getNom() {
        return nom;
    }

    public String getCapacite() {
        return capacite;
    }

    public int getNumero() {
        return numero;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setCapacite(String capacite) {
        this.capacite = capacite;
    }
}
