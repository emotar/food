package ga.javatw.food.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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
	Page<Food> findByUser_Username(String username, Pageable page);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Food f SET f.title = :title, f.description = :description,"
    		+ "f.price = :price, f.image = :image, f.category = :category, f.region = :region WHERE f.id = :id")
	int updateFood(@Param("id") Long id, @Param("title") String title,
					@Param("description") String description, @Param("price") long price,
					@Param("image") String image, @Param("category") FoodCategory category,
					@Param("region") Region region);
}
