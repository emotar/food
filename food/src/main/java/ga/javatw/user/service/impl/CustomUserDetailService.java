package ga.javatw.user.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ga.javatw.user.model.User;
import ga.javatw.user.model.UserGroup;
import ga.javatw.user.repository.UserRepository;
import ga.javatw.user.service.UserService;

@Service
public class CustomUserDetailService implements UserDetailsService{
	private static final Logger logger = LoggerFactory.getLogger(CustomUserDetailService.class);
	@Autowired
	private UserRepository userRepository;

	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username);
		logger.debug(user.toString());

		if (null == user) {
			throw new UsernameNotFoundException("User not found");
		} else {
			return new org.springframework.security.core.userdetails.User(user.getUsername(),
						user.getPassword(), true, true, true, true, getGrantedAuthorities(user));
		}



	}

	private Collection<? extends GrantedAuthority> getGrantedAuthorities(User user) {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

	      for(UserGroup item : user.getUserGroup()){

	            authorities.add(new SimpleGrantedAuthority("ROLE_" + item.getName()));
	        }

		return authorities;
	}


}
