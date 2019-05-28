package brainstorming.model.estrutura;


import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "step")
public class Step extends No {
	
	@OneToOne(mappedBy = "right")
	private Step left;
	@OneToOne(mappedBy = "left")
	private Step right;
	@OneToOne(mappedBy = "first")
	private Storyboard storyboard;
	
}
