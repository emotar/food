package ga.javatw.food.service;

import java.util.List;

import org.springframework.data.domain.Page;

import ga.javatw.common.AjaxPageResult;
import ga.javatw.food.model.Food;

public interface FoodService {
	List<Food> findAll();
	Page<Food> findAllByPage(int page, int size);
	Page<Food> findByCategoryPage(int page, int size, String categoryId);
	Page<Food> findByRegionPage(int page, int size, String regionId);
	Page<Food> findByTitleStarsWith(int searchPage, int searchPageSize, String searchWord);
	Page<Food> findByPriceBetween(int searchPage, int searchPageSize, int priceStart, int priceEnd);
	Page<Food> findByDescriptionLike(int searchPage, int searchPageSize, String string);
	Page<Food> findByUsername(int page, int size, String username);
	void removeFoodById(Long foodId);
	Food saveFood(Food food);
	int updateFood(Food oldFood);
	void removeImageById(Long foodId);


}
