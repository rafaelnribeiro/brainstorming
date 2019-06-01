package brainstorming.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@IdClass(ParticipacaoID.class)
@Table(name = "participacao")
public class Participacao implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@ManyToOne
	@JoinColumn(name = "Id_Grupo")
	private Grupo grupo;
	
	@Id
	@ManyToOne
	@JoinColumn(name = "Id_Participante")
	private User participante;
	
	@Column(name = "pontos")
	private int pontos;

	public Grupo getGrupo() {
		return grupo;
	}

	public void setGrupo(Grupo grupo) {
		this.grupo = grupo;
	}

	public User getParticipante() {
		return participante;
	}

	public void setParticipante(User participante) {
		this.participante = participante;
	}

	public int getPontos() {
		return pontos;
	}

	public void setPontos(int pontos) {
		this.pontos = pontos;
	}
}
