package Cabinet.models;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CabinetOrthophonique implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private List<CompteOrthophoniste> comptesOrthophonistes;
    private List<DossierPatient> dossiersPatients;
    private List<Exercice> exercices;
    private List<Question> questions;
    private List<RendezVous> rdvs;
    private List<QLAnamnese > Qstanamnese;
    private List<Anamnese> anamneses;
    private List<Test> tests;
    private List<Trouble> troubles;

    public CabinetOrthophonique() {
        comptesOrthophonistes = new ArrayList<>();
        dossiersPatients = new ArrayList<>();
        exercices = new ArrayList<>();
        questions = new ArrayList<>();
        rdvs = new ArrayList<>();
        Qstanamnese = new ArrayList<>();
        tests = new ArrayList<>();
        anamneses = new ArrayList<>();
        troubles = new ArrayList<>();
    }

    public void ajouterCompte(CompteOrthophoniste compte) {
        comptesOrthophonistes.add(compte);
    }

    public List<CompteOrthophoniste> getComptesOrthophonistes() {
        return comptesOrthophonistes;
    }

    public void supprimerCompte(CompteOrthophoniste compte) {
        comptesOrthophonistes.remove(compte);
    }

    public CompteOrthophoniste authentifier(String email, String motDePasse) {
        for (CompteOrthophoniste compte : comptesOrthophonistes) {
            if (compte.authentifier(email, motDePasse)) return compte;
        }
        return null;
    }

    public boolean compteExiste(String email) {
        for (CompteOrthophoniste compte : comptesOrthophonistes) {
            if (compte.getEmail().equals(email)) return true;
        }
        return false;
    }

    public void ajouterDossierPatient(DossierPatient dossier) {
        dossiersPatients.add(dossier);
    }

    public void supprimerDossierPatient(DossierPatient dossier) {
        dossiersPatients.remove(dossier);
    }

    public List<DossierPatient> getDossiersPatients() {
        return dossiersPatients;
    }

    public List<Exercice> getExercices() {
        return exercices;
    }

    public void ajouterExercice(Exercice exercice) {
        exercices.add(exercice);
    }

    public void supprimerExercice(Exercice exercice) {
        exercices.remove(exercice);
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void ajouterQuestion(Question question) {
        questions.add(question);
    }

    public void supprimerQuestion(Question question) {
        questions.remove(question);
    }

    // Gestion des rendez-vous

    public void ajouterRendezVous(RendezVous rendezVous, int numeroDossier) {
        DossierPatient dossier = getDossierParNumero(numeroDossier);
        if (dossier != null) {
            dossier.ajouterRendezVous(rendezVous);
        }
    }

    public void modifierRendezVous(RendezVous oldRdv, RendezVous newRdv) {
        for (DossierPatient dossier : dossiersPatients) {
            dossier.modifierRendezVous(oldRdv, newRdv);
        }
    }

    public void supprimerRendezVous(RendezVous rendezVous) {
        for (DossierPatient dossier : dossiersPatients) {
            dossier.supprimerRendezVous(rendezVous);
        }
    }

    private DossierPatient getDossierParNumero(int numeroDossier) {
        for (DossierPatient dossier : dossiersPatients) {
            if (dossier.getNumeroDossier() == numeroDossier) {
                return dossier;
            }
        }
        return null;
    }

    public List<RendezVous> getRdv() {
        return rdvs;
    }

    public void addRdv(RendezVous rdv) {
        rdvs.add(rdv);
    }

    public void removeRdv(RendezVous rdv) {
        rdvs.remove(rdv);
    }

    public boolean dossierPatientExiste(int numeroDossier) {
        for (DossierPatient dossier : dossiersPatients) {
            if (dossier.getNumeroDossier() == numeroDossier) {
                return true;
            }
        }
        return false;
    }

    public boolean isRendezVousExistant(RendezVous newRendezVous) {
        for (RendezVous rendezVous : rdvs) {
            // Vérifier si un rendez-vous existe déjà avec la même date et heure de début
            if (rendezVous.getDate().isEqual(newRendezVous.getDate())) {
                return true;
            }
        }
        return false;
    }

    public void updateRdv(RendezVous oldRdv, RendezVous newRdv) {
        int index = rdvs.indexOf(oldRdv);
        if (index != -1) {
            rdvs.set(index, newRdv);
        }
    }
    // Gestion de l'anamnèse
    public List<QLAnamnese> getAnamneses() {
        return Qstanamnese;
    }

    public void ajouterQlAnamnese(QLAnamnese QlAnamnese) {
        this.Qstanamnese.add(QlAnamnese);
    }

    public void modifierQlAnamnese(QLAnamnese oldQlAnamnese, QLAnamnese newQlAnamnese) {
        int index = Qstanamnese.indexOf(oldQlAnamnese);
        if (index != -1) {
            Qstanamnese.set(index, newQlAnamnese);
        }
    }

    public void supprimerQlAnamnese(QLAnamnese qlAnamnese) {
        Qstanamnese.remove(qlAnamnese);
    }

    public List<QLAnamneseEnfant> getAnamnesesEnfant() {
        List<QLAnamneseEnfant> anamnesesEnfant = new ArrayList<>();
        for (QLAnamnese qlAnamnese : Qstanamnese) {
            if (qlAnamnese instanceof QLAnamneseEnfant) {
                anamnesesEnfant.add((QLAnamneseEnfant) qlAnamnese);
            }
        }
        return anamnesesEnfant;
    }

    public List<QLAnamneseAdulte> getAnamnesesAdulte() {
        List<QLAnamneseAdulte> anamnesesAdulte = new ArrayList<>();
        for (QLAnamnese qlAnamnese : Qstanamnese) {
            if (qlAnamnese instanceof QLAnamneseAdulte) {
                anamnesesAdulte.add((QLAnamneseAdulte) qlAnamnese);
            }
        }
        return anamnesesAdulte;
    }

    public List<TestExercices> getTestsExerices() {
        List<TestExercices> resultat = new ArrayList<>();
        for (Test test : tests) {
            if (test instanceof TestExercices) {
                resultat.add((TestExercices) test);
            }
        }
        return resultat;
    }

    public List<TestQuestions> getTestsQuestions() {
        List<TestQuestions> resultat = new ArrayList<>();
        for (Test test : tests) {
            if (test instanceof TestQuestions) {
                resultat.add((TestQuestions) test);
            }
        }
        return resultat;
    }

    public void ajouterTest(Test test) {
        tests.add(test);
    }

    public void supprimerTest(Test test) {
        tests.remove(test);
    }

    public Exercice getExerciceParNumero(int exoId) {
        for (Exercice exercice : exercices) {
            if (exercice.getNumero() == exoId) {
                return exercice;
            }
        }
        return null;
    }

    public Question getQuestionParNumero(int qstId) {
        for (Question question : questions) {
            if (question.getNumero() == qstId) {
                return question;
            }
        }
        return null;
    }

    public List<Anamnese> getAnamnese() {
        return anamneses;
    }

    public QLAnamnese getQuestionAnamneseParNumero(int qstId) {
        for (QLAnamnese question : Qstanamnese) {
            if (question.getNumero() == qstId) {
                return question;
            }
        }
        return null;
    }

    public void supprimerAnamnese(Anamnese anamnese) {
        anamneses.remove(anamnese);
    }

    public void ajouterAnamnese(Anamnese anamnese) {
        anamneses.add(anamnese);
    }

    public Test getTestParNumero(int testId) {
        for (Test test : tests) {
            if (test.getNumero() == testId) {
                return test;
            }
        }
        return null;
    }

    public List<Trouble> getTroubles() {
        return troubles;
    }

    public void ajouterTrouble(Trouble trouble) {
        troubles.add(trouble);
    }

    public void supprimerTrouble(Trouble trouble) {
        troubles.remove(trouble);
    }

    public void modifierTrouble(Trouble oldTrouble, Trouble newTrouble) {
        int index = troubles.indexOf(oldTrouble);
        if (index != -1) {
            troubles.set(index, newTrouble);
        }
    }

    public Trouble getTroubleParNumero(int troubleId) {
        for (Trouble trouble : troubles) {
            if (trouble.getNumero() == troubleId) {
                return trouble;
            }
        }
        return null;
    }

    public Anamnese getAnamneseParNumero(int anamneseId) {
        for (Anamnese anamnese : anamneses) {
            if (anamnese.getNumero() == anamneseId) {
                return anamnese;
            }
        }
        return null;
    }

    public List<RendezVous> getRdvParDossier(DossierPatient dossier) {
        return dossier.getRendezVous();
    }
}