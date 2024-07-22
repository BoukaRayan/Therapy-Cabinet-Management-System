package Cabinet.models;
import Cabinet.models.enums.CategorieQLAnamneseAdulte;

import java.io.Serial;
import java.io.Serializable;

public class QLAnamneseAdulte extends QLAnamnese implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private CategorieQLAnamneseAdulte categorie;

    public QLAnamneseAdulte(String enonce, CategorieQLAnamneseAdulte categorie) {
        super(enonce);
        this.categorie = categorie;
    }

    @Override
    public Enum getCategorie() {
        return categorie;
    }

    public CategorieQLAnamneseAdulte getCategorieAdulte() {
        return categorie;
    }

    public void setCategorieAdulte(CategorieQLAnamneseAdulte categorie) {
        this.categorie = categorie;
    }
}