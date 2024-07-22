package Cabinet.models;

import java.io.Serial;
import java.io.Serializable;

public abstract class Question implements Serializable {
	@Serial
	private static final long serialVersionUID = 1L;
	protected String enonce;
	protected int numero;
	private static int cptQuestions = 0;

	public Question(String enonce){
		this.enonce = enonce;
		this.numero = ++cptQuestions;
	}

	public String getEnonce() {
		return enonce;
	}

	public int getNumero() {
		return numero;
	}

	public void setEnonce(String enonce) {
		this.enonce = enonce;
	}
}
