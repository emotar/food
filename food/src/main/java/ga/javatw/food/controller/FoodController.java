package ga.javatw.food.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ga.javatw.food.model.FoodCategory;
import ga.javatw.food.model.Region;
import ga.javatw.food.service.FoodCategoryService;
import ga.javatw.food.service.FoodService;
import ga.javatw.food.service.RegionService;
import ga.javatw.user.model.User;
import ga.javatw.user.service.UserService;

@Controller
@RequestMapping("/food")
public class FoodController {
	private static final Logger logger = LoggerFactory.getLogger(FoodController.class);

	@Autowired
	private FoodCategoryService fcService;

	@Autowired
	private FoodService fService;

	@Autowired
	private RegionService rService;

	@Autowired
	private UserService userService	;


	@RequestMapping("/index")
	public String index(Model model, @RequestParam(value = "tab", required = false) String tab) {


		Authentication auth = SecurityContextHolder.getContext().getAuthentication();


		User user = userService.findByUsername(auth.getName());


		if (null != auth) {
			logger.debug(auth.toString());
		}


		model.addAttribute("userForm", new User());
		logger.debug("IndexController.....index");
		return "food/index";
	}





}
