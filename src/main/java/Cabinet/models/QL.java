package Cabinet.models;

import java.io.Serial;
import java.io.Serializable;

public class QL extends Question implements Serializable {
	@Serial
	private static final long serialVersionUID = 1L;

	public QL(String enonce) {
		super(enonce);
	}

}
