package ga.javatw.user.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ga.javatw.user.model.User;
import ga.javatw.user.repository.UserRepository;
import ga.javatw.user.service.UserService;

@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService{

	private UserRepository userRepository;

	@Autowired
	public UserServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	@Transactional(readOnly = false)
	public User addUser(User user) {
		return this.userRepository.save(user);
	}

	@Override
	public User findByUsername(String username) {
		return this.userRepository.findByUsername(username);
	}

}
