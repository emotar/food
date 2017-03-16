package ga.javatw.user.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ga.javatw.user.model.UserGroup;

public interface UserGroupRepository extends JpaRepository<UserGroup, Long> {
	List<UserGroup> findByName(String name);
}
