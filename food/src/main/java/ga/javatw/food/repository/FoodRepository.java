package ga.javatw.food.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import ga.javatw.food.model.Food;
import ga.javatw.food.model.FoodCategory;
import ga.javatw.food.model.Region;

public interface FoodRepository extends JpaRepository<Food, Long>{
	Page<Food> findByCategory(FoodCategory category, Pageable page);
	Page<Food> findByRegion(Region region, Pageable page);
	Page<Food> findByCategory_Title(String categoryTitle, Pageable page);
	Page<Food> findByTitleStartsWith(String title, Pageable page);
	Page<Food> findByPriceBetween(long start, long end, Pageable page);
	Page<Food> findByDescriptionLike(String word, Pageable page);

}
