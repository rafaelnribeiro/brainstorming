package brainstorming.model.estrutura;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "storyboard")
public class Storyboard extends Estrutura{
	private static final long serialVersionUID = 1L;
	
	@OneToOne
	private Step first;

	public Step getFirst() {
		return first;
	}

	public void setFirst(Step first) {
		this.first = first;
	}
}
