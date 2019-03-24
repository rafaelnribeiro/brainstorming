package brainstorming.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import brainstorming.model.Grupo;
import brainstorming.model.User;
import brainstorming.repository.GrupoRepository;
import brainstorming.util.exceptions.BusinessException;

@Service
@Transactional(readOnly = true)
public class GrupoService {
	
	@Autowired
	private GrupoRepository grupoRepository;
	
	public List<Grupo> findAll() {
		return grupoRepository.findAll();
	}
	
	public Optional<Grupo> findOne(Integer id) {
		return grupoRepository.findById(id);
	}
	
	@Transactional(readOnly = false)
	public Grupo save(Grupo entity) throws BusinessException {
		if (entity.getNome().trim().isEmpty()) {
			throw new BusinessException("Nome do grupo vazio");
		}
		return grupoRepository.save(entity);
	}
	
	@Transactional(readOnly = false)
	public void delete(Grupo entity) {
		grupoRepository.delete(entity);
	}
	
	@Transactional(readOnly = false)
	public void addParticipante(Grupo entity, User participante) throws BusinessException{
		if (entity.getParticipantes().contains(participante)) {
			throw new BusinessException("Usuário já pertence ao grupo");
		}
		entity.getParticipantes().add(participante);
		grupoRepository.save(entity);
	}
	
	@Transactional(readOnly = false)
	public void rmvParticipante(Grupo entity, User participante){
		entity.getParticipantes().remove(participante);
		grupoRepository.save(entity);
	}
}
