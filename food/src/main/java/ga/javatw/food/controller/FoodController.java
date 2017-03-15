package ga.javatw.food.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import ga.javatw.food.model.FoodCategory;
import ga.javatw.food.model.Region;
import ga.javatw.food.service.FoodCategoryService;
import ga.javatw.food.service.FoodService;
import ga.javatw.food.service.RegionService;
import ga.javatw.user.model.User;

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


	@RequestMapping("/index")
	public String index(Model model) {
		model.addAttribute("userForm", new User());
		logger.debug("IndexController.....index");
		return "food/index";
	}





}
