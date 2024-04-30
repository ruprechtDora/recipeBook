package com.econsult.recept.model.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateRecipesDto {

	private Long id;
	private String name;
	private Integer servings;
	private Integer prepTimeInMinutes;
	private String shortDescription;
	private String notes;
	private String directions;
	private String image;
	private List<SaveRecipesIngredientsDto> ingredients;
	
}
