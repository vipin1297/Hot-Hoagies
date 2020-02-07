package com.spiralforge.hothoagies.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spiralforge.hothoagies.entity.Category;
import com.spiralforge.hothoagies.entity.FoodItem;

@Repository
public interface FoodItemRepository extends JpaRepository<FoodItem, Long> {

	List<FoodItem> findByCategory(Category category);

	FoodItem findByFoodItemId(Long foodItemId);

}
