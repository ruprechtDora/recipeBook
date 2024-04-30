import { ChangeDetectionStrategy, Component, Inject, OnInit } from "@angular/core";
import { Ingredient } from "../../model/entities/ingredient";
import { FormControl, FormGroup } from "@angular/forms";
import { RecipeForm, RecipeService } from "src/app/core/service/recipe.service";
import { MAT_DIALOG_DATA } from "@angular/material/dialog";
import { Recipe } from "../../model/entities/recipe";

@Component({
  selector: 'app-add-recipe',
  templateUrl: './add-recipe.component.html',
  styleUrls: ['./add-recipe.component.css'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class AddRecipeDialog implements OnInit {

  ingredientNameControl = new FormControl<string>('');
  ingredientAmountControl = new FormControl<number>(null);
  ingredientMeasureControl = new FormControl<string>('');

  recipeForm = new FormGroup<RecipeForm>({
    name: new FormControl<string>(''),
    servings: new FormControl<number>(null),
    prepTimeInMinutes: new FormControl<number>(null),
    shortDescription: new FormControl<string>(''),
    notes: new FormControl<string>(''),
    directions: new FormControl<string>(''),
    image: new FormControl<string>(null)
  })

  mode = 'SAVE';
  ingredients: Ingredient[] = new Array<Ingredient>();

  constructor(
    @Inject(MAT_DIALOG_DATA) public data: Recipe,
    private recipeService: RecipeService
  ) {

  }


  ngOnInit(): void {
    if (this.data) {
      console.log(this.data);
      this.mode = 'UPDATE';
      this.loadRecipeData(this.data);
    }
  }

  addIngredient() {
    const ingredient = {
      ingredientName: this.ingredientNameControl.value,
      amount: this.ingredientAmountControl.value,
      unitOfMeasure: this.ingredientMeasureControl.value
    } as Ingredient

    this.ingredients.push(ingredient);
    this.clearIngredientControls();

  }

  clearIngredientControls() {
    this.ingredientAmountControl.reset();
    this.ingredientMeasureControl.reset();
    this.ingredientNameControl.reset();
  }

  saveRecipe() {
    this.recipeService.saveRecipe(this.recipeForm, this.ingredients)
  }

  loadRecipeData(recipe: Recipe) {
    this.recipeForm.controls.name.setValue(recipe.name);
    this.recipeForm.controls.prepTimeInMinutes.setValue(recipe.prepTimeInMinutes);
    this.recipeForm.controls.servings.setValue(recipe.servings);
    this.recipeForm.controls.shortDescription.setValue(recipe.shortDescription);
    this.recipeForm.controls.notes.setValue(recipe.notes);
    this.recipeForm.controls.directions.setValue(recipe.directions);
    this.ingredients = recipe.ingredients;
  }

  updateRecipe() {
    this.recipeService.updateRecipe(this.recipeForm, this.ingredients, this.data.id);
  }

  changeImage(event: any) {
    this.recipeService.uploadRecipeImage(event.target.files[0], this.data.id);
  }

  clearIngredients() {
    this.ingredients = [];
  }

}
