package com.spiralforge.hothoagies.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.spiralforge.hothoagies.entity.CartItem;
import com.spiralforge.hothoagies.entity.User;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {


	@Query(value = "select * from hothoagiesdb.cart_item where user_id=:userId group by user_id, food_item_id order by count(*) Desc;", nativeQuery = true)
	List<CartItem> findAllByUser(@Param("userId") Long userId);

	@Query("select ct from CartItem ct where ct.user =:user and ct.orderDetail is null")
	List<CartItem> getCartItemByUser(User user);


}
