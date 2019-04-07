package brainstorming.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import brainstorming.model.Solicitacao;
import brainstorming.model.Sugestao;

public interface SolicitacaoRepository extends JpaRepository<Solicitacao, Integer>{

	@Query("SELECT sol FROM Sessao ses INNER JOIN ses.ideias i INNER JOIN i.solicitacoes sol WHERE ses.id = ?1")
	List<Solicitacao> findBySessao(Integer id_sessao);
	
	boolean existsBySugestao(Sugestao sugestao);
}


