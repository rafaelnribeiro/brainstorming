package brainstorming.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import brainstorming.model.Grupo;
import brainstorming.repository.GrupoRepository;

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
	public Grupo save(Grupo entity) {
		return grupoRepository.save(entity);
	}
	
	@Transactional(readOnly = false)
	public void delete(Grupo entity) {
		grupoRepository.delete(entity);
	}
}
