package Cabinet.models;

import java.io.Serial;

public abstract class CompteRendu implements java.io.Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private Test test;
    private String conclusion;

    public CompteRendu(Test test, String conclusion) {
        this.test = test;
        this.conclusion = conclusion;
    }

    public abstract double calculerScore();

    public String getConclusion() {
        return conclusion;
    }

    public void setConclusion(String conclusion) {
        this.conclusion = conclusion;
    }

    public int getNumeroTest(){
        return test.getNumero();
    }
}
