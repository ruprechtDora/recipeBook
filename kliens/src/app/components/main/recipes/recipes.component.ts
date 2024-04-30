import { ChangeDetectionStrategy, Component, Input, OnDestroy, OnInit } from "@angular/core";
import { FormControl } from "@angular/forms";
import { Subscription, switchMap, tap } from "rxjs";
import { RecipeService } from "src/app/core/service/recipe.service";
import { Recipe } from "src/app/shared/model/entities/recipe";

@Component({
  selector: 'app-recipes',
  templateUrl: './recipes.component.html',
  styleUrls: ['./recipes.component.css'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class RecipesComponent implements OnInit, OnDestroy {

  recipes: Recipe[];
  recipes$ = this.recipeService.recipes$.pipe(
    tap(recipes => {
      if (this.searchControl.value) {
        this.recipes = recipes.filter(recipe => recipe.name.trim().toUpperCase().includes(this.searchControl.value.toUpperCase()));
      } else {
        this.recipes = recipes;
      }
    })
  );


  subscrtiptionContainer = new Subscription();

  searchControl = new FormControl<string>(null);
  searchControl$ = this.searchControl.valueChanges.pipe(
    switchMap(value => this.recipes$)
  );

  constructor(
    private recipeService: RecipeService
  ) {

  }
  ngOnDestroy(): void {
    this.subscrtiptionContainer.unsubscribe();
  }

  ngOnInit(): void {
    this.recipeService.getAllRecipes();
  }

  editRecipe(recipe: Recipe) {
    this.recipeService.editRecipe(recipe);
  }

  addRecipe() {
    this.subscrtiptionContainer.add(this.recipeService.openAddRecipeDialog());
  }

  deleteRecipe(recipeId: number) {
    this.subscrtiptionContainer.add(this.recipeService.deleteRecipe(recipeId));
  }
}
