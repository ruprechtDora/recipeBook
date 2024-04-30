package com.econsult.recept.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.econsult.recept.model.dto.SaveRecipesIngredientsDto;
import com.econsult.recept.model.dto.UpdateIngredientsDto;
import com.econsult.recept.model.entity.Ingredients;
import com.econsult.recept.repository.IngredientsRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class IngredientsService {

	@Autowired
	private IngredientsRepository ingredientsRepository;
	
	public List<Ingredients> saveIngredients(Long recipeId, List<SaveRecipesIngredientsDto> saveIngredientsDtoList) {
		try {
			List<Ingredients> newIngredientsList = new ArrayList<>();
			for(int i = 0; i < saveIngredientsDtoList.size(); i++) {
				Ingredients newIngredients = Ingredients.builder()
						.recipeId(recipeId)
						.amount(saveIngredientsDtoList.get(i).getAmount())
						.unitOfMeasure(saveIngredientsDtoList.get(i).getUnitOfMeasure())
						.ingredientName(saveIngredientsDtoList.get(i).getIngredientName())
						.build();
				newIngredientsList.add(newIngredients);
			}
			
			ingredientsRepository.saveAll(newIngredientsList);
			
			return newIngredientsList;
		} catch(Exception e) {
			log.error(e.getMessage());
			return null;
		}
	}
	
	public List<Ingredients> updateIngredients(Long recipeId, List<SaveRecipesIngredientsDto> updateIngredientsDtoList) {
		try {
			ingredientsRepository.deleteByRecipeId(recipeId);
			
			List<Ingredients> newIngredientsList = new ArrayList<>();
			for(int i = 0; i < updateIngredientsDtoList.size(); i++) {
				Ingredients newIngredients = Ingredients.builder()
						.recipeId(recipeId)
						.amount(updateIngredientsDtoList.get(i).getAmount())
						.unitOfMeasure(updateIngredientsDtoList.get(i).getUnitOfMeasure())
						.ingredientName(updateIngredientsDtoList.get(i).getIngredientName())
						.build();
				newIngredientsList.add(newIngredients);
			}
			
			ingredientsRepository.saveAll(newIngredientsList);
			
			return newIngredientsList;
		} catch(Exception e) {
			log.error(e.getMessage());
			return null;
		}
	}
}
