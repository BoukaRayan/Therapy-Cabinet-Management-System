package Cabinet.models;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.LocalTime;

public abstract class RendezVous implements Serializable {
	@Serial
	private static final long serialVersionUID = 1L;
	private static int cptRendezVous = 0;
	private int id;
    private LocalDateTime date;
    private  int duree;
    private String observation;
    //private BilanOrthophonique bo;

    public RendezVous(LocalDateTime date, int duree) {
		this.id = ++cptRendezVous;
        this.date = date;
        this.duree = duree;

    }

	public int getId() {
		return id;
	}

	public String getObservation() {
		return observation;
	}

	public void setObservation(String observation) {
		this.observation = observation;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}
	public int getDuree() {
		return duree;
	}

	public void setDuree(int duree) {
		this.duree = duree;
	}

	public abstract String getType();
}




