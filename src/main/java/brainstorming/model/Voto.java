package brainstorming.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity 
@Table(name = "voto")
public class Voto implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "comentario")
	private String comentario;
	
	@Column(name = "tipo")
	private boolean tipo;
	
	@ManyToOne
	@JoinColumn(name = "id_usuario")
	private User votante;
	
	@ManyToOne
	@JoinColumn(name = "id_ideia")
	private Ideia ideia;
	
	
	
	
	
	
	
	
	
}
