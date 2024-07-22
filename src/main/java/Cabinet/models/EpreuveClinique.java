package Cabinet.models;

import java.io.Serial;
import java.util.List;
import java.util.Map;

public class EpreuveClinique implements java.io.Serializable{
    @Serial
    private static final long serialVersionUID = 1L;

    private List<String> observations;
    private Map<Test, CompteRendu> tests;

    public EpreuveClinique(List<String> observations, Map<Test, CompteRendu> tests) {
        this.observations = observations;
        this.tests = tests;
    }

    public List<String> getObservations() {
        return observations;
    }

    public Map<Test, CompteRendu> getTests() {
        return tests;
    }

    public void ajouterObservation(String observation) {
        observations.add(observation);
    }

    public void ajouterTest(Test test, CompteRendu compteRendu) {
        tests.put(test, compteRendu);
    }
}
