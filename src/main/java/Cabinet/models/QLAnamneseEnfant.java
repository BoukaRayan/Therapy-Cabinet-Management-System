package Cabinet.models;
import Cabinet.models.enums.CategorieQLAnamneseEnfant;

import java.io.Serial;
import java.io.Serializable;

public class QLAnamneseEnfant extends QLAnamnese implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private CategorieQLAnamneseEnfant categorie;

    public QLAnamneseEnfant(String enonce, CategorieQLAnamneseEnfant categorie) {
        super(enonce);
        this.categorie = categorie;
    }

    @Override
    public Enum getCategorie() {
        return categorie;
    }

    public CategorieQLAnamneseEnfant getCategorieEnfant() {
        return categorie;
    }

    public void setCategorieEnfant(CategorieQLAnamneseEnfant categorie) {
        this.categorie = categorie;
    }
}