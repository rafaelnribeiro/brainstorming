package brainstorming.model.estrutura;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "quadro")
public class Quadro extends No{
	
	@ManyToOne
	@JoinColumn(name = "id_divisor")
	private Divisor divisor;
	
}
