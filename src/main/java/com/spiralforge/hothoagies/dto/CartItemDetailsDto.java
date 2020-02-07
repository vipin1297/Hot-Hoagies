package com.spiralforge.hothoagies.dto;

import lombok.Setter;

import com.spiralforge.hothoagies.entity.Item;

import lombok.Getter;

@Getter
@Setter
public class CartItemDetailsDto {

	private Long cartItemId;
	private Integer quantity;
	private Item item;
}
