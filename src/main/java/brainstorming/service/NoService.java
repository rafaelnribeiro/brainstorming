package brainstorming.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import brainstorming.model.estrutura.No;
import brainstorming.repository.estrutura.NoRepository;

@Service
@Transactional(readOnly = false)
public class NoService {
	@Autowired
	private NoRepository<No> noRepository;
	
	public Optional<No> findOne(int id){
		return noRepository.findById(id);
	}
}
