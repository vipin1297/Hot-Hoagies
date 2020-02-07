package com.spiralforge.hothoagies.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PreferenceResponseDto {
	private List<PreferenceList> preferenceList;
	private Integer statusCode;
	private String message;
}
