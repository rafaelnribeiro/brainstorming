package brainstorming.service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import brainstorming.model.Role;
import brainstorming.model.User;
import brainstorming.repository.RoleRepository;
import brainstorming.repository.UserRepository;
import brainstorming.util.exceptions.BusinessException;

@Service
@Transactional(readOnly = true)
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	public List<User> findAll() {
		return userRepository.findAll();
	}
	
	public Optional<User> findOne(Integer id) {
		return userRepository.findById(id);
	}
	
	public User findByEmail(String email) {
		return userRepository.findByEmail(email);
	}
	
	@Transactional(readOnly = false)
	public User save(User entity) throws BusinessException {
		if (entity.getName().trim().isEmpty()) {
			throw new BusinessException("Campo 'Nome' Vazio");
		}
		if(entity.getEmail().trim().isEmpty()) {
			throw new BusinessException("Campo 'Email' Vazio");	
		}
		if(this.findByEmail(entity.getEmail()) != null) {
			throw new BusinessException("Email já cadastrado");	
		}
		if (entity.getPassword().trim().isEmpty() || entity.getPassword().trim().length() < 6 ) {
			throw new BusinessException("Campo 'Senha' vazio' ou a senha digitada tem menos que 6 caracteres");
		}
		
		
		entity.setPassword(bCryptPasswordEncoder.encode(entity.getPassword()));
		Role userRole = roleRepository.findByRole("USER");
		entity.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
		entity.setActive(1);
		return userRepository.save(entity);		
	}
	
	@Transactional(readOnly = false)
	public void delete(User entity) {
		userRepository.delete(entity);
	}
	
	
}
