package brainstorming.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import brainstorming.model.Solicitacao;
import brainstorming.repository.SolicitacaoRepository;
import brainstorming.util.exceptions.BusinessException;


@Service
@Transactional(readOnly = true)
public class SolicitacaoService {

	@Autowired SolicitacaoRepository solicitacaoRepository;
	
	@Transactional(readOnly = false)
	public Solicitacao save(Solicitacao sol) throws BusinessException {		
		if(solicitacaoRepository.existsBySugestao(sol.getSugestao())) {
			throw new BusinessException("Já existe uma solicitação para essa sugestão");
		}
		return solicitacaoRepository.save(sol);
	}
	
	public Optional<Solicitacao> findOne(Integer id){
		return solicitacaoRepository.findById(id);
	}
	
	public List<Solicitacao> findBySessao(Integer id_sessao) {
		return solicitacaoRepository.findBySessao(id_sessao);
	}
	
	@Transactional(readOnly = false)
	public void delete(Solicitacao sol) {
		solicitacaoRepository.delete(sol);
	}
}
