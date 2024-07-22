package Cabinet.models;

import Cabinet.models.enums.CategorieQLAnamneseAdulte;
import Cabinet.models.enums.CategorieQLAnamneseEnfant;

import java.io.Serial;
import java.io.Serializable;


public abstract class QLAnamnese implements Serializable{
    @Serial
    private static final long serialVersionUID = 1L;
    private int numero;
    private static int cptQuestions = 0;
    private String enonce;

    public QLAnamnese(String enonce) {
        this.enonce = enonce;
        this.numero = ++cptQuestions;
    }

    public String getEnonce() {
        return enonce;
    }

    public void setEnonce(String enonce) {
        this.enonce = enonce;
    }

    public int getNumero() {
        return numero;
    }

    public abstract Enum getCategorie();

    public String getType() {
        if (this instanceof QLAnamneseEnfant) {
            return "Enfant";
        } else if (this instanceof QLAnamneseAdulte) {
            return "Adulte";
        }
        return "Inconnu";
    }


    public void setCategorie(Enum selectedCategorie) {
        if (this instanceof QLAnamneseEnfant) {
            ((QLAnamneseEnfant) this).setCategorieEnfant((CategorieQLAnamneseEnfant) selectedCategorie);
        } else if (this instanceof QLAnamneseAdulte) {
            ((QLAnamneseAdulte) this).setCategorieAdulte((CategorieQLAnamneseAdulte) selectedCategorie);
        }
    }
}