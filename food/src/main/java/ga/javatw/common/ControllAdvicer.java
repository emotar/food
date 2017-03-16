package ga.javatw.common;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import ga.javatw.food.model.FoodCategory;
import ga.javatw.food.model.Region;
import ga.javatw.food.service.FoodCategoryService;
import ga.javatw.food.service.RegionService;

@ControllerAdvice
public class ControllAdvicer {

	@Autowired
	FoodCategoryService foodCategoryService;

	@Autowired
	RegionService regionService;




	@ModelAttribute("allFoodCategory")
	public List<FoodCategory> allFoodCategory() {
		return foodCategoryService.findAll();
	}

	@ModelAttribute("allRegion")
	public List<Region> allRegion() {
		return regionService.findAll();
	}
}
