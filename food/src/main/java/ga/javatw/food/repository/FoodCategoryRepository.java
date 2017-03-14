package ga.javatw.food.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ga.javatw.food.model.FoodCategory;

public interface FoodCategoryRepository extends JpaRepository<FoodCategory, Long> {

}
