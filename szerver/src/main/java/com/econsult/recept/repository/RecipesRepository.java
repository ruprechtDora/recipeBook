package com.econsult.recept.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.econsult.recept.model.entity.Recipes;

@Repository
public interface RecipesRepository extends CrudRepository<Recipes, Long> {

}
