package brainstorming.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import brainstorming.model.Sessao;
import brainstorming.model.User;
import brainstorming.repository.SessaoRepository;

@Service
@Transactional(readOnly = true)
public class SessaoService {

	@Autowired
	private SessaoRepository sessaoRepository;
	
	public List<Sessao> findAll() {
		return sessaoRepository.findAll();
	}
	
	public Optional<Sessao> findOne(Integer id) {
		return sessaoRepository.findById(id);
	}
	
	@Transactional(readOnly = false)
	public Sessao save(Sessao entity) {
		return sessaoRepository.save(entity);
	}
	
//	@Transactional(readOnly = false)
//	public Sessao addParticipante(Sessao entity, User participante) throws ParticipanteJaAdicionadoException {
//		if(entity.getGrupo().getParticipantes().contains(participante)){
//			throw new ParticipanteJaAdicionadoException();
//		}
//		entity.getGrupo().getParticipantes().add(participante);
//		return sessaoRepository.save(entity);
//	}
// Remover e tratar exceção em GrupoService
	
	@Transactional(readOnly = false)
	public void delete(Sessao entity) {
		sessaoRepository.delete(entity);
	}
}
