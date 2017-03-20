package ga.javatw.user.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import ga.javatw.user.model.User;
import ga.javatw.user.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;


	@RequestMapping("/add")
	public String addUser(@Valid @ModelAttribute("userForm") User user, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "food/index";
		}
		user.setCdate(new Date());
		userService.addUser(user);
		return "redirect:/food/index";
	}


	/**
	 * 使用程序式的方式來登入 spring security
	 * @param username
	 * @param password
	 * @return
	 */
	@RequestMapping("/doLogin")
	public String doLogin(@RequestParam("lusername") String username, @RequestParam("lpassword") String password) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if ( authentication != null
                && authentication.isAuthenticated()) {
            return "redirect:/member/index";
        }

        return "food/index";
	}



	@RequestMapping("/login")
	public String login() {
		return "user/formLogin";
	}


	/**
	 * 使用程序式的方式來登出 spring security
	 *
	 * @param request
	 * @param response
	 * @return
	 */
    @RequestMapping(value="/logout")
    public String logout (HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/food/index?logout";
    }

}
