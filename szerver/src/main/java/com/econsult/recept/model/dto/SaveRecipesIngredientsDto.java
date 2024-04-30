package com.econsult.recept.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaveRecipesIngredientsDto {

	private Integer amount;
	private String unitOfMeasure;
	private String ingredientName;
	
}

