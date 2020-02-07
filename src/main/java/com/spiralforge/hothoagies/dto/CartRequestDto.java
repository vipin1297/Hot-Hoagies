package com.spiralforge.hothoagies.dto;

import java.util.List;

import com.spiralforge.hothoagies.entity.Item;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartRequestDto {

	private List<Item> items;
	private Integer quantity;
}
