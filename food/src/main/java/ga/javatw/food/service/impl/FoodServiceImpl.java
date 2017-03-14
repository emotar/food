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

}
