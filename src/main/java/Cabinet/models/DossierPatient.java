package Cabinet.models;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DossierPatient implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private static int cptDossiers = 0;
    private int numeroDossier;
    private Patient infos;
    private List<FicheSuivi> fichesSuivi;
    private List<RendezVous> rdvs;
    private List<BilanOrthophonique> bos;

    public DossierPatient(Patient infos, List<RendezVous> rdvs){
        numeroDossier = ++cptDossiers;
        this.infos = infos;
        this.rdvs = rdvs;
        this.fichesSuivi = new ArrayList<>();
        this.bos = new ArrayList<>();
    }

    public int getNumeroDossier() {
        return numeroDossier;
    }

    public Patient getInfos() {
        return infos;
    }

    public void setInfos(Patient p) {
        this.infos = p;
    }

    public List<FicheSuivi> getFichesSuivi() { return fichesSuivi; }

    public void supprimerFicheSuivi(FicheSuivi fiche) {
        fichesSuivi.remove(fiche);
    }

    public void ajouterFicheSuivi(FicheSuivi ficheSuivi) {
        fichesSuivi.add(ficheSuivi);
    }

    public List<RendezVous> getRendezVous() {
        return rdvs;
    }

    public void ajouterRendezVous(RendezVous rdv) {
        rdvs.add(rdv);
    }

    public void modifierRendezVous(RendezVous rdv) {
        for (int i = 0; i < rdvs.size(); i++) {
            if (rdvs.get(i).getId() == rdv.getId()) {
                rdvs.set(i, rdv);
                return;
            }
        }
    }

    public void modifierRendezVous(RendezVous oldRdv, RendezVous newRdv) {
        int index = rdvs.indexOf(oldRdv);
        if (index != -1) {
            rdvs.set(index, newRdv);
        }
    }

    public void supprimerRendezVous(RendezVous rdv) {
        rdvs.remove(rdv);
    }

    public List<BilanOrthophonique> getBilansOrthophoniques() {
        return bos;
    }

    public void ajouterBilanOrthophonique(BilanOrthophonique bilanOrthophonique) {
        bos.add(bilanOrthophonique);
    }

    public void supprimerBilanOrthophonique(BilanOrthophonique bilanOrthophonique) {
        bos.remove(bilanOrthophonique);
    }
}
