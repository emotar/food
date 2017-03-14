package ga.javatw.food.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ga.javatw.food.model.FoodCategory;
import ga.javatw.food.repository.FoodCategoryRepository;
import ga.javatw.food.service.FoodCategoryService;

@Service
@Transactional(readOnly = true)
public class FoodCategoryServiceImpl implements FoodCategoryService {

	private FoodCategoryRepository foodCategoryRepository;

	@Autowired
	public FoodCategoryServiceImpl(FoodCategoryRepository foodCategoryRepository) {
		this.foodCategoryRepository = foodCategoryRepository;
	}

	@Override
	public List<FoodCategory> findAll() {
		return this.foodCategoryRepository.findAll();
	}
}
