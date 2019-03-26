package brainstorming.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import brainstorming.model.Voto;
import brainstorming.repository.VotoRepository;
import brainstorming.util.exceptions.BusinessException;

@Service
@Transactional(readOnly = true)
public class VotoService {
	
	@Autowired
	private VotoRepository votoRepository;
	
	@Transactional(readOnly = false)
	public Voto save(Voto vote) throws BusinessException{
		/*if (vote.iTipo() == ) {
			
		}*/
		return votoRepository.save(vote);
	}
	
	public Optional<Voto> findOne(Integer id) {
		return votoRepository.findById(id);
	}
	
	public List<Voto> findAll(){
		return votoRepository.findAll();
	}
	
	
}
