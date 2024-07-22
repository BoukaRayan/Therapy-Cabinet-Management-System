package Cabinet.models;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class FicheSuivi implements Serializable {
	@Serial
	private static final long serialVersionUID = 1L;
	private static int cptFiches;
	private int numero;
	private String nom;
	private Map<Objectif, Integer> objectifs;

	public FicheSuivi(String nom){
		numero = ++cptFiches;
		objectifs = new HashMap<Objectif, Integer>();
		this.nom = nom;
	}

	public void ajouterObjectif(Objectif obj, int note){
		objectifs.put(obj, note);
	}

	public void supprimerObjectif(Objectif obj){
		objectifs.remove(obj);
	}

	public Set<Entry<Objectif, Integer>> getObjectifs(){
		return objectifs.entrySet();
	}

	public int getNumero() {
		return numero;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getNom() {
		return nom;
	}
}
