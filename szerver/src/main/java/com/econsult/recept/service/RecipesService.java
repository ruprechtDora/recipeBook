package com.econsult.recept.service;

import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.econsult.recept.model.dto.SaveRecipesDto;
import com.econsult.recept.model.dto.UpdateRecipesDto;
import com.econsult.recept.model.entity.Ingredients;
import com.econsult.recept.model.entity.Recipes;
import com.econsult.recept.model.entity.Users;
import com.econsult.recept.repository.RecipesRepository;

import javax.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RecipesService {

	@Autowired
	private RecipesRepository recipesRepository;
	
	@Autowired
	private IngredientsService ingredientsService;
	
	@Transactional
	public ResponseEntity<Recipes> saveRecipe(SaveRecipesDto saveRecipeDto) {
		try {
			Recipes newRecipe = Recipes.builder()
					.name(saveRecipeDto.getName())
					.servings(saveRecipeDto.getServings())
					.prepTimeInMinutes(saveRecipeDto.getPrepTimeInMinutes())
					.shortDescription(saveRecipeDto.getShortDescription())
					.notes(saveRecipeDto.getNotes())
					.directions(saveRecipeDto.getDirections())
					.image(saveRecipeDto.getImage())
					.build();
			
			Recipes savedRecipe = recipesRepository.save(newRecipe);
			
			if(saveRecipeDto.getIngredients() == null)
				return ResponseEntity.ok(savedRecipe);
			
			List<Ingredients> newIngredientsList = ingredientsService.saveIngredients(savedRecipe.getId(), saveRecipeDto.getIngredients());
			
			savedRecipe.setIngredients(newIngredientsList);
							
			return ResponseEntity.ok(savedRecipe);
		} catch(Exception e) {
			log.error(e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@Transactional
	public ResponseEntity<Recipes> updateRecipe(UpdateRecipesDto updateRecipeDto) {
		try {
			if(updateRecipeDto.getId() == null)
				throw new NullPointerException("ID is missing!");
			
			Recipes recipe = recipesRepository.findById(updateRecipeDto.getId()).get();
			
			if(updateRecipeDto.getIngredients() != null && !updateRecipeDto.getIngredients().isEmpty()) {
				List<Ingredients> ingredients = ingredientsService.updateIngredients(updateRecipeDto.getId(), updateRecipeDto.getIngredients());
				if(ingredients == null)
					throw new Exception("Ingredients update failure!");
			}
			
			recipe.setName(updateRecipeDto.getName());
			recipe.setServings(updateRecipeDto.getServings());
			recipe.setPrepTimeInMinutes(updateRecipeDto.getPrepTimeInMinutes());
			recipe.setShortDescription(updateRecipeDto.getShortDescription());
			recipe.setNotes(updateRecipeDto.getNotes());
			recipe.setDirections(updateRecipeDto.getDirections());
			
			Recipes savedRecipe = recipesRepository.save(recipe);
							
			return ResponseEntity.ok(savedRecipe);
		} catch(Exception e) {
			log.error(e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	public ResponseEntity<?> deleteRecipe(Long id) {
		try {
			recipesRepository.deleteById(id);
			return ResponseEntity.ok().build();
		} catch(Exception e) {
			log.error(e.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}

	public ResponseEntity<Recipes> getRecipeById(Long id) {
		try {
			return ResponseEntity.ok(recipesRepository.findById(id).get());
		} catch(Exception e) {
			log.error(e.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}
	
	public ResponseEntity<Iterable<Recipes>> getAllRecipes() {
		try {
			return ResponseEntity.ok(recipesRepository.findAll());
		} catch(Exception e) {
			log.error(e.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}
	
	public ResponseEntity<Void> uploadImage(Long id, MultipartFile file) {
		try {
			Recipes recipe = recipesRepository.findById(id).get();
			
			byte[] image = file.getBytes();
			
			String imageEncoded = Base64.getEncoder().encodeToString(image);
			
			recipe.setImage(imageEncoded);
			
			recipesRepository.save(recipe);
			
			return ResponseEntity.ok().build();
		} catch(Exception e) {
			log.error(e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

}
