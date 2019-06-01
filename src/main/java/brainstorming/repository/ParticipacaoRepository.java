package brainstorming.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import brainstorming.model.Participacao;
import brainstorming.model.ParticipacaoID;

public interface ParticipacaoRepository extends JpaRepository<Participacao, ParticipacaoID> {

}
