import { Injectable } from "@angular/core";
import { MatDialog } from "@angular/material/dialog";
import { AddRecipeDialog } from "src/app/shared/components/add-recipe/add-recipe.component";
import { ApiService } from "../http/api.service";
import { FormControl, FormGroup } from "@angular/forms";
import { Ingredient } from "src/app/shared/model/entities/ingredient";
import { SaveRecipeRequest } from "src/app/shared/model/request/save-recipe-request";
import { UtilService } from "./util.service";
import { BehaviorSubject } from "rxjs";
import { Recipe } from "src/app/shared/model/entities/recipe";
import { Router } from "@angular/router";

export interface RecipeForm {
  name: FormControl<string>,
  servings: FormControl<number>,
  prepTimeInMinutes: FormControl<number>,
  shortDescription: FormControl<string>,
  notes: FormControl<string>,
  directions: FormControl<string>,
  image: FormControl<string>
}

@Injectable({
  providedIn: 'root'
})
export class RecipeService {

  recipeSubject = new BehaviorSubject<Recipe>(null);
  recipe$ = this.recipeSubject.asObservable();

  recipesSubject = new BehaviorSubject<Recipe[]>(null);
  recipes$ = this.recipesSubject.asObservable();

  constructor(
    private dialog: MatDialog,
    private apiService: ApiService,
    private utilService: UtilService,
    private router: Router
  ) {}

  openAddRecipeDialog(recipe?: Recipe) {
    this.dialog.open(AddRecipeDialog, {data: recipe});
  }

  getAllRecipes() {
    return this.apiService.getAllRecipes().subscribe({
      next: response => this.nextRecipesSubjectValue(response)
    });
  }

  saveRecipe(recipeForm: FormGroup<RecipeForm>, ingredients: Ingredient[]) {
    const request = this.createSaveRecipeRequest(recipeForm, ingredients);

    this.apiService.saveRecipe(request).subscribe({
      next: response => {
        this.utilService.showSnackBar('success', 'Recipe added successfully!');
        this.dialog.closeAll();
        this.getAllRecipes();
      }
    })
  }

  updateRecipe(recipeForm: FormGroup<RecipeForm>, ingredients: Ingredient[], recipeId: number) {
    const request = this.createSaveRecipeRequest(recipeForm, ingredients, recipeId);
    this.apiService.updateRecipe(request).subscribe({
      next: response => {
        this.utilService.showSnackBar('success', 'Recipe has been updated successfully!');
        this.dialog.closeAll();
        this.loadRecipeById(recipeId);
      }
    });
  }

  createSaveRecipeRequest(recipeForm: FormGroup<RecipeForm>, ingredients: Ingredient[], recipeId?: number) {
    return {
      id: recipeId,
      name: recipeForm.controls.name.value,
      servings: recipeForm.controls.servings.value,
      prepTimeInMinutes: recipeForm.controls.prepTimeInMinutes.value,
      shortDescription: recipeForm.controls.shortDescription.value,
      notes: recipeForm.controls.notes.value,
      directions: recipeForm.controls.directions.value,
      image: recipeForm.controls.image.value,
      ingredients: ingredients
    } as SaveRecipeRequest
  }

  deleteRecipe(recipeId: number) {
    return this.apiService.deleteRecipe(recipeId).subscribe({
      next: response => {
        this.utilService.showSnackBar('success', 'Recipe has deleted successfully!');
        this.getAllRecipes();
      },
      complete: () => this.apiService.getAllRecipes()
    })
  }

  editRecipe(recipe: Recipe) {
    this.nextRecipeSubjectValue(recipe);
    this.router.navigate(['/recipes/' + recipe.id]);
  }

  nextRecipeSubjectValue(recipe: Recipe) {
    this.recipeSubject.next(recipe);
  }

  loadRecipeById(recipeId: number) {
    return this.apiService.loadRecipeById(recipeId).subscribe({
      next: response => this.nextRecipeSubjectValue(response)
    });
  }

  nextRecipesSubjectValue(recipes: Recipe[]) {
    this.recipesSubject.next(recipes);
  }

  uploadRecipeImage(file: File, recipeId: number) {
    return this.apiService.uploadRecipeImage(file, recipeId).subscribe();
  }
}
