package brainstorming.model.estrutura;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "mindmapping")
public class Mindmapping {

	@OneToOne(mappedBy = "mindmapping")
	private Conceito conceito;
	
}
