package com.spiralforge.hothoagies.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.spiralforge.hothoagies.entity.CartItem;
import com.spiralforge.hothoagies.entity.User;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

	@Query("select ct from CartItem ct where ct.user =:user and ct.orderDetail is null")
	List<CartItem> getCartItemByUser(User user);

}
