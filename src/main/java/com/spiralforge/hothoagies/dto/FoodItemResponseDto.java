package com.spiralforge.hothoagies.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FoodItemResponseDto {
	private List<FoodItemList> foodItemList;
	private Integer statusCode;
	private String message;
}
