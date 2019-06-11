package brainstorming.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import brainstorming.model.Ideia;
import brainstorming.model.Participacao;
import brainstorming.model.Sessao;
import brainstorming.repository.ParticipacaoRepository;
import brainstorming.repository.SessaoRepository;
import brainstorming.service.SolucaoStrategy.SolucaoLimitePorNo;
import brainstorming.service.SolucaoStrategy.SolucaoMaisVotada;
import brainstorming.service.SolucaoStrategy.SolucaoMaisVotadaPorNo;
import brainstorming.service.SolucaoStrategy.SolucaoStrategy;
import brainstorming.service.rankingStrategy.RankingQualitative;
import brainstorming.service.rankingStrategy.RankingQuantitative;
import brainstorming.service.rankingStrategy.RankingStrategy;
import brainstorming.util.exceptions.BusinessException;

@Service
@Transactional(readOnly = true)
public class SessaoService {
	RankingStrategy rankingStrategy = new RankingQuantitative();
	//RankingStrategy rankingStrategy = new RankingQualitative();
		
	SolucaoStrategy solucaoStrategy = new SolucaoMaisVotada();
	//SolucaoStrategy solucaoStrategy = new SolucaoMaisVotadaPorNo();
	//SolucaoStrategy solucaoStrategy = new SolucaoLimitePorNo();

	@Autowired
	private SessaoRepository sessaoRepository;
	
	@Autowired
	private ParticipacaoRepository participacaoRepository;
	
	public List<Sessao> findAll() {
		return sessaoRepository.findAll();
	}
	
	public Optional<Sessao> findOne(Integer id) {
		return sessaoRepository.findById(id);
	}
	
	@Transactional(readOnly = false, rollbackFor = BusinessException.class)
	public Sessao save(Sessao entity) throws BusinessException {
		if(entity.getId() == null) {
			if(sessaoRepository.existsByTema(entity.getTema()))
				throw new BusinessException("Já existe uma sessão com esse tema");
		}else {
			if(sessaoRepository.existsAnotherByTema(entity.getTema(), entity.getId()))
				throw new BusinessException("Já existe uma sessão com esse tema");
		}
			
		return sessaoRepository.save(entity);
	}
	
	@Transactional(readOnly = false, rollbackFor = BusinessException.class)
	public void finalizarSessao(Sessao sessao) throws BusinessException {
		Sessao s = sessaoRepository.findById(sessao.getId()).get();
		
		rankingStrategy.updateRanking(s.getGrupo(), s);
		for (Participacao p : s.getGrupo().getParticipacoes()) {
			participacaoRepository.save(p);
		}
		
		List<Ideia> solucao =  solucaoStrategy.calcSolucao(s);
		
		for (Ideia ideia : solucao) {
			ideia.setSessaoSolucionada(s);
		}		
		s.setSolucao(solucao);		
		s.setFinalizado(true);		
		sessaoRepository.save(s);
		
	}
	
	@Transactional(readOnly = false)
	public void delete(Sessao entity) {
		sessaoRepository.delete(entity);
	}
}
