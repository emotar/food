package ga.javatw.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ga.javatw.user.model.User;

public interface UserRepository extends JpaRepository<User, Long>{

	User findByUsername(String username);
}
