package Cabinet.models;
import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Anamnese implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private static int cptAnamnese = 0;
    private int numero;

    private String nom;
    private List<QLAnamnese> questions;

    public Anamnese(String nom) {
        this.questions  = new ArrayList<>();
        this.numero = ++cptAnamnese;
        this.nom = nom;

    }

    public List<QLAnamnese> getQuestions() {
        return questions;
    }

    public int getNumero() {
        return numero;
    }

    public void setQuestions(List<QLAnamnese> questions) {
        this.questions  = questions ;
    }

    public String getNom() {
        return nom;
    }

    // Ajout, modification et suppression des questions

    public void ajouterQuestion(QLAnamnese question) {
        questions.add(question);
    }

    public void supprimerQuestion(QLAnamnese question) {
        questions.remove(question);
    }

    public void modifierQuestion(QLAnamnese question, QLAnamnese newQuestion) {
        questions.set(questions.indexOf(question), newQuestion);
    }

    public void setNom(String text) {
        this.nom = text;
    }
}