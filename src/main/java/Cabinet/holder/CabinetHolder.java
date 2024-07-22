package Cabinet.holder;

import Cabinet.models.CabinetOrthophonique;
import Cabinet.models.CompteOrthophoniste;

public final class CabinetHolder {
    private final static CabinetHolder INSTANCE = new CabinetHolder();
    private CabinetOrthophonique cabinet;
    private CompteOrthophoniste utilisateur;


    public static CabinetHolder getInstance() {
        return INSTANCE;
    }

    public CabinetOrthophonique getCabinetOrthophonique() { return this.cabinet; }

    public void setCabinetOrthophonique(CabinetOrthophonique cabinet) { this.cabinet = cabinet; }

    public CompteOrthophoniste getCompteOrthophoniste() { return this.utilisateur; }

    public void setCompteOrthophoniste(CompteOrthophoniste utilisateur){ this.utilisateur = utilisateur; }
}
