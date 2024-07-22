package Cabinet.models;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

public class Suivi extends RendezVous implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private Integer numeroDossier;
    private boolean enPresentiel;

    // Constructeur complet
    public Suivi(LocalDateTime dateHeure, Integer numeroDossier, boolean enPresentiel) {
        super(dateHeure, 60); // durée de 60 minutes pour les suivis
        this.numeroDossier = numeroDossier;
        this.enPresentiel = enPresentiel;
    }

    // Getters et setters
    public Integer getNumeroDossier() {
        return numeroDossier;
    }

    public void setNumeroDossier(Integer numeroDossier) {
        this.numeroDossier = numeroDossier;
    }

    public boolean isEnPresentiel() {
        return enPresentiel;
    }

    public void setEnPresentiel(boolean enPresentiel) {
        this.enPresentiel = enPresentiel;
    }

    @Override
    public String getType() {
        return "Suivi";
    }
}
