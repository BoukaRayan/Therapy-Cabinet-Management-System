package Cabinet.models;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BilanOrthophonique implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    protected LocalDate date;
    protected List<EpreuveClinique> epreuvesCliniques;
    protected List<Trouble> diagnostic;
    protected String projetTherapeutique;

    public BilanOrthophonique(List<Trouble> diagnostic, String projetTherapeutique) {
        this.date = LocalDate.now();
        this.diagnostic = diagnostic;
        this.projetTherapeutique = projetTherapeutique;
        this.epreuvesCliniques = new ArrayList<>();
    }

    public String getProjetTherapeutique() {
        return projetTherapeutique;
    }

    public void setProjetTherapeutique(String projetTherapeutique) {
        this.projetTherapeutique = projetTherapeutique;
    }

    public List<Trouble> getDiagnostic() {
        return diagnostic;
    }

    public void setDiagnostic(List<Trouble> diagnostic) {
        this.diagnostic = diagnostic;
    }

    public LocalDate getDate() {
        return date;
    }

    public List<EpreuveClinique> getEpreuvesCliniques() {
        return epreuvesCliniques;
    }

    public void ajouterEpreuveClinique(EpreuveClinique epreuveClinique) {
        epreuvesCliniques.add(epreuveClinique);
    }

    public void supprimerEpreuveClinique(EpreuveClinique epreuveClinique) {
        epreuvesCliniques.remove(epreuveClinique);
    }
}
