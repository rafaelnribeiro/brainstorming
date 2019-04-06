package brainstorming.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import brainstorming.model.Solicitacao;

public interface SolicitacaoRepository extends JpaRepository<Solicitacao, Integer>{

	@Query("SELECT 	S "
		+ "FROM Solicitacao S "
		+ "JOIN Ideia I ON S.ideia = I.id "
		+ "JOIN Sessao B ON I.sessao = B.id "
		+ "WHERE B.id = ?1")
	List<Solicitacao> findBySessao(Integer id_sessao);
}
