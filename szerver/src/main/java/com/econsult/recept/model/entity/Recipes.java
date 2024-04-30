package com.econsult.recept.model.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "RECIPES")
public class Recipes {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private Integer servings;
	private Integer prepTimeInMinutes;
	private String shortDescription;
	private String notes;
	private String directions;
	private String image;
	@OneToMany(fetch = FetchType.LAZY, mappedBy="recipe", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<Ingredients> ingredients;
	
}
