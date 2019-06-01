package brainstorming.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import brainstorming.model.Participacao;
import brainstorming.model.Sessao;
import brainstorming.repository.ParticipacaoRepository;
import brainstorming.repository.SessaoRepository;
import brainstorming.service.rankingStrategy.RankingQuantitative;
import brainstorming.service.rankingStrategy.RankingStrategy;
import brainstorming.util.exceptions.BusinessException;

@Service
@Transactional(readOnly = true)
public class SessaoService {
	RankingStrategy rankingStrategy = new RankingQuantitative();

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
	
	@Transactional(readOnly = false)
	public void finalizarSessao(Sessao sessao) {
		Sessao s = sessaoRepository.findById(sessao.getId()).get();
		
		rankingStrategy.updateRanking(s.getGrupo(), s);
		for (Participacao p : s.getGrupo().getParticipacoes()) {
			participacaoRepository.save(p);
		}
		
	}
	
	@Transactional(readOnly = false)
	public void delete(Sessao entity) {
		sessaoRepository.delete(entity);
	}
}
