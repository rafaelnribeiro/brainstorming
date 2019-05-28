package brainstorming.model.estrutura;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;

import brainstorming.model.Ideia;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class No {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Integer id;
	
	@OneToMany(mappedBy = "no", cascade = CascadeType.ALL)
	private List<Ideia> ideias; 
	
	@Column(name = "nome")
	private String nome;
}
