package ga.javatw.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	@Autowired
	UserDetailsService userDetailsService;


	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeRequests().
			antMatchers("/member/**").access("hasRole('ROLE_member') or hasRole('ROLE_admin')").
			antMatchers("/admin/**").hasRole("admin").
			anyRequest().permitAll().and().formLogin().loginPage("/food/index").successForwardUrl("/member/index");
	}


	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {

		//使用暫存的方式來驗
//		auth.inMemoryAuthentication()
//			.withUser("jerry").password("123456").roles("user", "admin");

		auth.userDetailsService(userDetailsService);


	}

}
