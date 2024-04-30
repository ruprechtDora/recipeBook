package com.econsult.recept.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.econsult.recept.model.entity.Ingredients;

@Repository
public interface IngredientsRepository extends CrudRepository<Ingredients, Long> {
	
	List<Ingredients> findByRecipeIdAndIdNotIn(Long recipeId, List<Long> ids);
	
	void deleteByRecipeId(Long recipeId);
	
}
