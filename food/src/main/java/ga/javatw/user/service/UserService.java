package ga.javatw.user.service;

import ga.javatw.user.model.User;

public interface UserService {
	User addUser(User user);

	User findByUsername(String username);
}
