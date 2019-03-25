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
@Table(name = "Voto")
public class Voto implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer id;
	
	@Column(name = "comentario")
	String comentario;
	
	@Column(name = "tipo")
	boolean tipo;
	
	@ManyToOne
	@JoinColumn(name = "id_usuario")
	User votante;
	
	@ManyToOne
	@JoinColumn(name = "id_ideia")
	Ideia ideia;
	
	
	
	
	
	
	
	
	
}
