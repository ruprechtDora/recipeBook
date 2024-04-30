package com.econsult.recept.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import com.econsult.recept.model.dto.SaveRecipesDto;
import com.econsult.recept.model.dto.SaveRecipesIngredientsDto;
import com.econsult.recept.model.dto.UpdateRecipesDto;
import com.econsult.recept.model.entity.Recipes;
import com.econsult.recept.service.RecipesService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(RecipesController.class)
@AutoConfigureMockMvc(addFilters = false)
public class RecipesControllerTest {

	@Autowired
	private MockMvc mvc;

    @MockBean
    private RecipesService recipesService;

    @Autowired
    private ObjectMapper objectMapper;
    
    @Test
    public void testSaveRecipe() throws Exception {
        SaveRecipesDto saveRecipeDto = new SaveRecipesDto();
        saveRecipeDto.setName("Test Recipe");
        saveRecipeDto.setServings(4);
        saveRecipeDto.setPrepTimeInMinutes(30);
        saveRecipeDto.setShortDescription("Test description");
        saveRecipeDto.setNotes("Test notes");
        saveRecipeDto.setDirections("Test directions");

        List<SaveRecipesIngredientsDto> ingredients = new ArrayList<>();
        SaveRecipesIngredientsDto ingredientDto1 = new SaveRecipesIngredientsDto();
        ingredientDto1.setIngredientName("Ingredient 1");
        ingredientDto1.setAmount(100);
        ingredientDto1.setUnitOfMeasure("g");
        ingredients.add(ingredientDto1);

        saveRecipeDto.setIngredients(ingredients);

        Recipes savedRecipe = new Recipes();
        savedRecipe.setId(1L);
        savedRecipe.setName(saveRecipeDto.getName());
        savedRecipe.setServings(saveRecipeDto.getServings());
        savedRecipe.setPrepTimeInMinutes(saveRecipeDto.getPrepTimeInMinutes());
        savedRecipe.setShortDescription(saveRecipeDto.getShortDescription());
        savedRecipe.setNotes(saveRecipeDto.getNotes());
        savedRecipe.setDirections(saveRecipeDto.getDirections());

        when(recipesService.saveRecipe(Mockito.any(SaveRecipesDto.class)))
               .thenReturn(new ResponseEntity<>(savedRecipe, HttpStatus.OK));

        mvc.perform(post("/recipes/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(saveRecipeDto)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(1L))
        .andExpect(jsonPath("$.name").value("Test Recipe"));
    }
    
    @Test
    public void testUpdateRecipe() throws Exception {
        UpdateRecipesDto updateRecipeDto = new UpdateRecipesDto();
        updateRecipeDto.setId(1L);
        updateRecipeDto.setName("Updated Recipe");
        updateRecipeDto.setServings(6);
        updateRecipeDto.setPrepTimeInMinutes(45);
        updateRecipeDto.setShortDescription("Updated description");
        updateRecipeDto.setNotes("Updated notes");
        updateRecipeDto.setDirections("Updated directions");

        List<SaveRecipesIngredientsDto> ingredients = new ArrayList<>();
        SaveRecipesIngredientsDto ingredientDto1 = new SaveRecipesIngredientsDto();
        ingredientDto1.setIngredientName("Updated Ingredient 1");
        ingredientDto1.setAmount(200);
        ingredientDto1.setUnitOfMeasure("g");
        ingredients.add(ingredientDto1);

        updateRecipeDto.setIngredients(ingredients);

        Recipes updatedRecipe = new Recipes();
        updatedRecipe.setId(updateRecipeDto.getId());
        updatedRecipe.setName(updateRecipeDto.getName());
        updatedRecipe.setServings(updateRecipeDto.getServings());
        updatedRecipe.setPrepTimeInMinutes(updateRecipeDto.getPrepTimeInMinutes());
        updatedRecipe.setShortDescription(updateRecipeDto.getShortDescription());
        updatedRecipe.setNotes(updateRecipeDto.getNotes());
        updatedRecipe.setDirections(updateRecipeDto.getDirections());
        updatedRecipe.setImage(updateRecipeDto.getImage());

        when(recipesService.updateRecipe(Mockito.any(UpdateRecipesDto.class)))
               .thenReturn(new ResponseEntity<>(updatedRecipe, HttpStatus.OK));

        mvc.perform(put("/recipes/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateRecipeDto)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(1L))
        .andExpect(jsonPath("$.name").value("Updated Recipe"));
    }
    
    @Test
    public void testDeleteRecipe() throws Exception {
        Long recipeId = 1L;

        when(recipesService.deleteRecipe(recipeId))
               .thenReturn(new ResponseEntity<>(HttpStatus.NO_CONTENT));

        mvc.perform(delete("/recipes/{id}", recipeId))
                .andExpect(status().isNoContent());
    }
    
    @Test
    public void testGetRecipeById() throws Exception {
        Long recipeId = 1L;
        Recipes recipe = new Recipes();
        recipe.setId(recipeId);
        recipe.setName("Test Recipe");

        when(recipesService.getRecipeById(recipeId))
               .thenReturn(new ResponseEntity<>(recipe, HttpStatus.OK));

        mvc.perform(get("/recipes/{id}", recipeId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(recipeId))
                .andExpect(jsonPath("$.name").value("Test Recipe"));
    }
    
    @Test
    public void testGetAllRecipes() throws Exception {
        Recipes recipe1 = new Recipes();
        recipe1.setId(1L);
        recipe1.setName("Recipe 1");

        Recipes recipe2 = new Recipes();
        recipe2.setId(2L);
        recipe2.setName("Recipe 2");

        List<Recipes> recipeList = Arrays.asList(recipe1, recipe2);

        when(recipesService.getAllRecipes())
               .thenReturn(new ResponseEntity<>(recipeList, HttpStatus.OK));

        mvc.perform(get("/recipes/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value("Recipe 1"))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].name").value("Recipe 2"));
    }
}
