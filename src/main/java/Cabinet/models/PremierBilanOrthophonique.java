package Cabinet.models;

import java.io.Serial;
import java.util.List;

public class PremierBilanOrthophonique extends BilanOrthophonique implements java.io.Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private Anamnese anamnese;
    public PremierBilanOrthophonique(List<Trouble> diagnostic, String projetTherapeutique, Anamnese anamnese) {
        super(diagnostic, projetTherapeutique);
        this.anamnese = anamnese;
    }
}
