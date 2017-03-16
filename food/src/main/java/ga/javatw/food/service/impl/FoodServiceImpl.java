package ga.javatw.food.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ga.javatw.food.model.Food;
import ga.javatw.food.model.FoodCategory;
import ga.javatw.food.model.Region;
import ga.javatw.food.repository.FoodRepository;
import ga.javatw.food.service.FoodService;

@Service
@Transactional(readOnly = true)
public class FoodServiceImpl implements FoodService {

	private FoodRepository foodRepository;

	@Autowired
	public FoodServiceImpl(FoodRepository foodRepository) {
		this.foodRepository = foodRepository;

	}

	@Override
	public List<Food> findAll() {
		return this.foodRepository.findAll();
	}

	@Override
	public Page<Food> findAllByPage(int page, int size) {
		PageRequest request = new PageRequest(page - 1, size);
		Page<Food> result = this.foodRepository.findAll(request);
		return result;

	}

	@Override
	public Page<Food> findByCategoryPage(int page, int size, String categoryId) {
		FoodCategory categoryObj = new FoodCategory();
		categoryObj.setId(Long.valueOf(categoryId));

		PageRequest pageRequest = new PageRequest(page - 1, size);
		Page<Food> result = this.foodRepository.findByCategory(categoryObj, pageRequest);
		return result;
	}

	@Override
	public Page<Food> findByRegionPage(int page, int size, String regionId) {
		Region region = new Region();
		region.setId(regionId);

		PageRequest pageRequest = new PageRequest(page - 1, size);
		Page<Food> result = this.foodRepository.findByRegion(region, pageRequest);

		return result;

	}

	@Override
	public Page<Food> findByTitleStarsWith(int searchPage, int searchPageSize, String searchWord) {
		PageRequest pageRequest = new PageRequest(searchPage - 1, searchPageSize);
		Page<Food> result = this.foodRepository.findByTitleStartsWith(searchWord, pageRequest);
		return result;
	}

	@Override
	public Page<Food> findByPriceBetween(int searchPage, int searchPageSize, int priceStart, int priceEnd) {
		PageRequest pageRequest = new PageRequest(searchPage - 1, searchPageSize);

		Page<Food> result = this.foodRepository.findByPriceBetween(priceStart * 1L, priceEnd * 1L, pageRequest);

		return result;
	}

	@Override
	public Page<Food> findByDescriptionLike(int searchPage, int searchPageSize, String word) {
		PageRequest pageRequest = new PageRequest(searchPage - 1, searchPageSize);
		Page<Food> result = this.foodRepository.findByDescriptionLike(word, pageRequest);
		return result;
	}

	@Override
	public Page<Food> findByUsername(int page, int size, String username) {
		PageRequest pageRequest = new PageRequest(page - 1, size);
		Page<Food> result = this.foodRepository.findByUser_Username(username, pageRequest);
		return result;
	}

	@Override
	@Transactional(readOnly = false)
	public void removeFoodById(Long foodId) {
		this.foodRepository.delete(foodId);

	}

	@Override
	@Transactional(readOnly = false)
	public Food saveFood(Food food) {
		Food resultFood = this.foodRepository.save(food);
		return resultFood;
	}

	@Override
	@Transactional(readOnly = false)
	public int updateFood(Food oldFood) {
		int result = this.foodRepository.updateFood(
							oldFood.getId(), oldFood.getTitle(), oldFood.getDescription(),
								oldFood.getPrice(), oldFood.getImage(), oldFood.getCategory(),
									oldFood.getRegion());
		return result;
	}


	@Override
	@Transactional(readOnly = false)
	public void removeImageById(Long foodId) {
		Food food = this.foodRepository.findOne(foodId);
		food.setImage(null);

	}

}
