package Cabinet.models;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

public class QCU extends Question implements Serializable {
	@Serial
	private static final long serialVersionUID = 1L;
	private List<String> propositions;

	public QCU(String enonce, List<String> propositions) {
		super(enonce);
		this.propositions = propositions;
	}

	public List<String> getPropositions() {
		return propositions;
	}

	public void setPropositions(List<String> props) {
		this.propositions = props;
	}
}
