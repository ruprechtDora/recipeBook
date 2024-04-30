package com.econsult.recept.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.econsult.recept.model.dto.SaveRecipesDto;
import com.econsult.recept.model.dto.UpdateRecipesDto;
import com.econsult.recept.model.entity.Recipes;
import com.econsult.recept.service.RecipesService;

@CrossOrigin
@RestController
@RequestMapping("/recipes")
public class RecipesController {

	@Autowired
	private RecipesService recipesService;
	
	@PostMapping(path = "/", consumes = "application/json")
	public ResponseEntity<Recipes> saveRecipe(@RequestBody SaveRecipesDto saveRecipeDto) {
		return recipesService.saveRecipe(saveRecipeDto);
	}
	
	@PutMapping(path = "/", consumes = "application/json")
	public ResponseEntity<Recipes> updateRecipe(@RequestBody UpdateRecipesDto updateRecipeDto) {
		return recipesService.updateRecipe(updateRecipeDto);
	}
	
	@DeleteMapping(path = "/{id}")
	public ResponseEntity<?> deleteRecipe(@PathVariable("id") Long id) {
		return recipesService.deleteRecipe(id);
	}
	
	@GetMapping(path = "/{id}")
	public ResponseEntity<Recipes> getRecipeById(@PathVariable("id") Long id) {
		return recipesService.getRecipeById(id);
	}
	
	@GetMapping(path = "/")
	public ResponseEntity<?> getAllRecipe() {
		return recipesService.getAllRecipes();
	}
	
	@PostMapping(path = "/{id}/uploadimage")
	public ResponseEntity<Void> uploadImage(@PathVariable("id") Long id, @RequestParam("image") MultipartFile file) {
		return recipesService.uploadImage(id, file);
	}
	
}
