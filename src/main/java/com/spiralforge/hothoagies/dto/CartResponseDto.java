package com.spiralforge.hothoagies.dto;

import lombok.Setter;

import lombok.Getter;

@Getter
@Setter
public class CartResponseDto {

	private Long itemId;
	private String itemName;
	private Integer quantity;
	private Double totalPrice;
}
