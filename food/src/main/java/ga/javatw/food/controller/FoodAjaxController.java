package ga.javatw.food.controller;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ga.javatw.common.AjaxPageResult;
import ga.javatw.common.AjaxResult;
import ga.javatw.common.utils.AjaxPageResultUtil;
import ga.javatw.food.model.Food;
import ga.javatw.food.repository.FoodRepository;
import ga.javatw.food.service.FoodService;

@RestController
@RequestMapping("/ajax")
public class FoodAjaxController {


	private static final String SEARCH_FOOD_TITLE = "title";
	private static final String SEARCH_FOOD_PRICE = "price";
	private static final String SEARCH_FOOD_DESCRIPTION= "description";

	@Autowired
	private FoodService foodService;

	@Autowired
	private FoodRepository fr;

	private static final Logger logger = LoggerFactory.getLogger(FoodAjaxController.class);


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
		AjaxPageResult<Food> ajaxPageResult = null;
		try {
			Page<Food> pageResult = foodService.findAllByPage(page, size);
			ajaxPageResult =  AjaxPageResultUtil.<Food>transform(pageResult);
		} catch (Exception e) {
			ajaxPageResult =  new AjaxPageResult<Food>("error", e.toString());
		}

		return ajaxPageResult;
	}





	@RequestMapping("/findByCategoryPage")
	public AjaxPageResult<Food> findByCategoryPage(@RequestParam("page") int page,
														@RequestParam("size") int size,
															@RequestParam("categoryId") String categoryId) {


		AjaxPageResult<Food> ajaxPageResult =  null;
		try {
			Page<Food> pageResult = foodService.findByCategoryPage(page, size, categoryId);
			ajaxPageResult = AjaxPageResultUtil.<Food>transform(pageResult);
		} catch (Exception e) {
			ajaxPageResult =  new AjaxPageResult<Food>("error", e.toString());
		}

		return ajaxPageResult;
	}



	@RequestMapping("/findByRegionPage")
	public AjaxPageResult<Food> findByRegionPage(@RequestParam("page") int page,
														@RequestParam("size") int size,
															@RequestParam("regionId") String regionId) {

		AjaxPageResult<Food> ajaxPageResult = null;
		try {
			Page<Food> pageResult = foodService.findByRegionPage(page, size, regionId);
			ajaxPageResult =  AjaxPageResultUtil.<Food>transform(pageResult);
		} catch (Exception e) {
			ajaxPageResult =  new AjaxPageResult<Food>("error", e.toString());
		}

		return ajaxPageResult;
	}



	@RequestMapping(value = "/queryFoodBySearchType", consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
	public AjaxPageResult<Food> findBySearchType(@RequestBody MultiValueMap<String, String> map) {

		 String searchType = map.get("searchType").get(0);
		 String searchWord = map.get("searchWord").get(0);
		 int searchPageSize = Integer.parseInt(map.get("searchPageSize").get(0));
		 int searchPage = Integer.parseInt(map.get("searchPage").get(0));
		 int priceStart = Integer.parseInt(map.get("priceStart").get(0));
		 int priceEnd = Integer.parseInt(map.get("priceEnd").get(0));
		 AjaxPageResult<Food> ajaxPageResult = null;
		 Page<Food> searchResult = null;

		 try {

			 if (StringUtils.equals(SEARCH_FOOD_TITLE, searchType)) {
				 searchResult = foodService.findByTitleStarsWith(searchPage, searchPageSize, searchWord);

			 } else if (StringUtils.equals(SEARCH_FOOD_PRICE, searchType)) {
				 searchResult = foodService.findByPriceBetween(searchPage, searchPageSize, priceStart, priceEnd);

			 } else if (StringUtils.equals(SEARCH_FOOD_DESCRIPTION, searchType)) {
				 searchResult = foodService.findByDescriptionLike(searchPage, searchPageSize, "%" + searchWord + "%");
			 }
			 ajaxPageResult =  AjaxPageResultUtil.<Food>transform(searchResult);

		 } catch (Exception e) {
			 ajaxPageResult =  new AjaxPageResult<Food>("error", e.toString());
		 }

		return ajaxPageResult;

	}


	@RequestMapping("/findByUsernamePage")
	public AjaxPageResult<Food> findByUsernamePage(@RequestParam("page") int page,
														@RequestParam("size") int size,
															@RequestParam("username") String username) {

		AjaxPageResult<Food> ajaxPageResult = null;
		try {
			Page<Food> searchResult = foodService.findByUsername(page, size, username);
			ajaxPageResult =  AjaxPageResultUtil.<Food>transform(searchResult);
		} catch (Exception e) {
			ajaxPageResult =  new AjaxPageResult<Food>("error", e.toString());
		}
		return ajaxPageResult;


	}



	@RequestMapping("/removeFoodById")
	public AjaxResult<String> removeFoodById(@RequestParam("foodId") Long foodId) {
		AjaxResult<String> ajaxResult = null;
		try {
			foodService.removeFoodById(foodId);
			ajaxResult = new AjaxResult<>("ok", "");
		} catch (Exception e) {
			ajaxResult = new AjaxResult<>("error", e.toString());
		}


		return ajaxResult;


	}



}




