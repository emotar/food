package ga.javatw.user.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ga.javatw.user.model.User;
import ga.javatw.user.model.UserGroup;
import ga.javatw.user.repository.UserGroupRepository;
import ga.javatw.user.repository.UserRepository;
import ga.javatw.user.service.UserService;

@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService{

	public static final String USER_DEFAULT_GROUP = "member";
	private UserRepository userRepository;

	@Autowired
	private UserGroupRepository userGroupRepository;

	@Autowired
	public UserServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	@Transactional(readOnly = false)
	public User addUser(User user) {
		List<UserGroup> userGroups = userGroupRepository.findByName(USER_DEFAULT_GROUP);
		UserGroup group = userGroups.get(0);

		group.addNewUser(user);

		return this.userRepository.save(user);
	}

	@Override
	public User findByUsername(String username) {
		return this.userRepository.findByUsername(username);
	}

}
