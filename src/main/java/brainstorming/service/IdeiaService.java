package brainstorming.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import brainstorming.model.Ideia;
import brainstorming.model.User;
import brainstorming.repository.IdeiaRepository;
import brainstorming.util.exceptions.BusinessException;

@Service
@Transactional(readOnly = true)
public class IdeiaService {
	
	@Autowired
	private IdeiaRepository ideiaRepository;
	
	public List<Ideia> findAll() {
		return ideiaRepository.findAll();
	}
	
	public Optional<Ideia> findOne(Integer id) {
		return ideiaRepository.findById(id);
	}
	
	@Transactional(readOnly = false)
	public Ideia save(Ideia entity) throws BusinessException {
		System.out.println("1 =====================>" +ideiaRepository.findById(entity.getId()).get().getTitulo());
		if (entity.getTitulo().trim().isEmpty()) {
			throw new BusinessException("Titulo vazio");
		}	
		System.out.println("2 =====================>" +ideiaRepository.findById(entity.getId()).get().getTitulo());
		if(ideiaRepository.existsByTitulo(entity.getTitulo())) {
			System.out.println("3 =====================>" +ideiaRepository.findById(entity.getId()).get().getTitulo());
			throw new BusinessException("Já existe uma ideia com esse titulo");
		}
		System.out.println("4 =====================>" +ideiaRepository.findById(entity.getId()).get().getTitulo());
		return ideiaRepository.save(entity);
	}
	
	@Transactional(readOnly = false)
	public void delete (Ideia entity) {
		ideiaRepository.delete(entity);
	}
	
	@Transactional(readOnly = false)
	public void vote(Ideia ideia, User votante) throws BusinessException {
		if(ideia.getVotantes().contains(votante)) {
			throw new BusinessException("Usuário já votou nesta ideia, não é possível votar novamente");
		}
		ideia.getVotantes().add(votante);
		ideiaRepository.save(ideia);
	}
	
	@Transactional(readOnly = false)
	public void unvote(Ideia ideia, User votante) throws BusinessException {
		if(!ideia.getVotantes().contains(votante)) {
			throw new BusinessException("Usuário não votou nesta ideia, impossível remover o voto");
		}
		ideia.getVotantes().remove(votante);
		ideiaRepository.save(ideia);
	}
}
