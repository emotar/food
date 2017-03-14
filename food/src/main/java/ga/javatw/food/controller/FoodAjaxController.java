package ga.javatw.food.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ga.javatw.common.AjaxPageResult;
import ga.javatw.common.AjaxResult;
import ga.javatw.common.utils.AjaxPageResultUtil;
import ga.javatw.food.model.Food;
import ga.javatw.food.service.FoodService;

@RestController
@RequestMapping("/ajax")
public class FoodAjaxController {

	@Autowired
	private FoodService foodService;

	@RequestMapping("/queryAllFood")
	public AjaxResult<Food> queryAllFood() {
		AjaxResult<Food> ajaxResult = null;
		try {
			List<Food> result = foodService.findAll();
			ajaxResult = new AjaxResult<Food>(result);
		} catch (Exception e) {
			ajaxResult = new AjaxResult<Food>("error", e.toString());
		}

		return ajaxResult;
	}


	@RequestMapping("/queryAllFoodByPage")
	public AjaxPageResult<Food> queryAllFoodByPage(@RequestParam("page") int page, @RequestParam("size") int size) {
		Page<Food> pageResult = foodService.findAllByPage(page, size);
		AjaxPageResult<Food> ajaxPageResult =  AjaxPageResultUtil.<Food>transform(pageResult);

		return ajaxPageResult;
	}





	@RequestMapping("/findByCategoryPage")
	public AjaxPageResult<Food> findByCategoryPage(@RequestParam("page") int page,
														@RequestParam("size") int size,
															@RequestParam("categoryId") String categoryId) {

		Page<Food> pageResult = foodService.findByCategoryPage(page, size, categoryId);
		AjaxPageResult<Food> ajaxPageResult =  AjaxPageResultUtil.<Food>transform(pageResult);

		return ajaxPageResult;
	}



	@RequestMapping("/findByRegionPage")
	public AjaxPageResult<Food> findByRegionPage(@RequestParam("page") int page,
														@RequestParam("size") int size,
															@RequestParam("regionId") String regionId) {

		Page<Food> pageResult = foodService.findByRegionPage(page, size, regionId);
		AjaxPageResult<Food> ajaxPageResult =  AjaxPageResultUtil.<Food>transform(pageResult);

		return ajaxPageResult;
	}






}
