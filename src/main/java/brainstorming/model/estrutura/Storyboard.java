package brainstorming.model.estrutura;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "storyboard")
public class Storyboard {
	
	@OneToOne(mappedBy = "storyboard")
	private Step first;
}
