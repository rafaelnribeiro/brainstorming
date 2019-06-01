package brainstorming.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import brainstorming.model.Grupo;
import brainstorming.model.Participacao;
import brainstorming.model.User;
import brainstorming.repository.GrupoRepository;
import brainstorming.repository.ParticipacaoRepository;
import brainstorming.util.exceptions.BusinessException;

@Service
@Transactional(readOnly = true)
public class GrupoService {
	
	@Autowired
	private GrupoRepository grupoRepository;
	
	@Autowired
	private ParticipacaoRepository participacaoRepository;
		
	public List<Grupo> findAll() {
		return grupoRepository.findAll();
	}
	
	public Optional<Grupo> findOne(Integer id) {
		return grupoRepository.findById(id);
	}
	
	@Transactional(readOnly = false, rollbackFor = BusinessException.class)
	public Grupo save(Grupo entity) throws BusinessException {
		if (entity.getNome().trim().isEmpty()) {
			throw new BusinessException("Nome do grupo vazio");
		}
		if (grupoRepository.existsByNome(entity.getNome())) {
			throw new BusinessException("Nome do grupo já existe");
		}
		return grupoRepository.save(entity);
		
	}
	
	@Transactional(readOnly = false)
	public void delete(Grupo entity) {
		grupoRepository.delete(entity);
	}
	
	@Transactional(readOnly = false)
	public void addParticipacao(Grupo entity, User participante) throws BusinessException{
		if (entity.getParticipantes().contains(participante)) {
			throw new BusinessException("Usuário já pertence ao grupo");
		}
		Participacao p = new Participacao();
		p.setGrupo(entity);
		p.setParticipante(participante);
		p.setPontos(0);
		participacaoRepository.save(p);
	}
	
	@Transactional(readOnly = false)
	public void rmvParticipacao(Grupo entity, User participante){
		entity.getModeradores().remove(participante);
		for (Participacao p : entity.getParticipacoes()) {
			if(p.getParticipante().getId() == participante.getId()) {
				entity.getParticipacoes().remove(p);
				participacaoRepository.delete(p);
				break;
			}
		}	
	}
	
	@Transactional(readOnly = false)
	public void addModerador(Grupo entity, User moderador) {
		entity.getModeradores().add(moderador);
		grupoRepository.save(entity);
	}
	
	@Transactional(readOnly = false)
	public void rmvModerador(Grupo entity, User moderador) {
		entity.getModeradores().remove(moderador);
		grupoRepository.save(entity);
	}
}
