package ga.javatw.user.controller;

import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

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
}
