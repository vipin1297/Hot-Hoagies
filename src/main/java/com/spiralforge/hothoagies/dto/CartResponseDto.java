package com.spiralforge.hothoagies.dto;

import lombok.Setter;

import java.util.List;

import lombok.Getter;

@Getter
@Setter
public class CartResponseDto {

	private List<ItemResponseDto> items;
	private Double totalPrice;
}
