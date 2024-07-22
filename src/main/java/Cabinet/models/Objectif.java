package Cabinet.models;

import Cabinet.models.enums.TypeObjectif;

import java.io.Serial;
import java.io.Serializable;

public class Objectif implements Serializable {
	@Serial
	private static final long serialVersionUID = 1L;
	private String nom;
	private TypeObjectif type;

	public Objectif(String nom, TypeObjectif type){
		this.nom = nom;
		this.type = type;
	}

	public String getNom() {
		return nom;
	}

	public TypeObjectif getType() {
		return type;
	}
}
