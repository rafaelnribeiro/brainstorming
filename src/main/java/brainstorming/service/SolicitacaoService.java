package brainstorming.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import brainstorming.model.Solicitacao;
import brainstorming.repository.SolicitacaoRepository;


@Service
@Transactional(readOnly = true)
public class SolicitacaoService {

	@Autowired SolicitacaoRepository solicitacaoRepository;
	
	@Transactional(readOnly = false)
	public Solicitacao save(Solicitacao sol) {		
		return solicitacaoRepository.save(sol);
	}
	
	public Optional<Solicitacao> findOne(Integer id){
		return solicitacaoRepository.findById(id);
	}
	
	public List<Solicitacao> findBySessao(Integer id_sessao) {
		return solicitacaoRepository.findBySessao(id_sessao);
	}
}
