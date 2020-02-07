package com.spiralforge.hothoagies.dto;

import com.spiralforge.hothoagies.entity.FoodItem;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartItemDetailsDto {

	private Long cartItemId;
	private Integer quantity;
	private FoodItem item;
}
