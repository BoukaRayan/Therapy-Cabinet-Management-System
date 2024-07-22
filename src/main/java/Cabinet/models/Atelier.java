package Cabinet.models;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

public class Atelier extends RendezVous implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private String thematique;
    private List<String> numerosDossiersParticipants;

    // Constructeur
    public Atelier(LocalDateTime dateHeure, String thematique, List<String> numerosDossiersParticipants) {
        super(dateHeure, 60); // durée de 60 minutes pour les ateliers
        this.thematique = thematique;
        this.numerosDossiersParticipants = numerosDossiersParticipants;
    }

    // Getters et setters
    public String getThematique() {
        return thematique;
    }

    public void setThematique(String thematique) {
        this.thematique = thematique;
    }

    public List<String> getParticipants() {
        return numerosDossiersParticipants;
    }

    public void setParticipants(List<String> numerosDossiersParticipants) {
        this.numerosDossiersParticipants = numerosDossiersParticipants;
    }

    public String getType() {
        return "Atelier";
    }

}
