package com.spiralforge.hothoagies.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ItemResponseDto implements Serializable{
	
	private Long itemId;
	private String itemName;
	private Integer quantity;
}
