package com.spiralforge.hothoagies.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartRequestDto {

	private List<ItemRequestDto> items;
}
