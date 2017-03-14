package ga.javatw.food.service;

import java.util.List;

import org.springframework.data.domain.Page;

import ga.javatw.food.model.Food;

public interface FoodService {
	List<Food> findAll();
	Page<Food> findAllByPage(int page, int size);
	Page<Food> findByCategoryPage(int page, int size, String categoryId);
	Page<Food> findByRegionPage(int page, int size, String regionId);
}
