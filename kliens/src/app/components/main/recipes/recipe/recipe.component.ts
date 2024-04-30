import { ChangeDetectionStrategy, Component, OnInit } from "@angular/core";
import { ActivatedRoute, Route } from "@angular/router";
import { RecipeService } from "src/app/core/service/recipe.service";
import { Recipe } from "src/app/shared/model/entities/recipe";

@Component({
  selector: 'app-recipe',
  templateUrl: './recipe.component.html',
  styleUrls: ['./recipe.component.css'],
  changeDetection: ChangeDetectionStrategy.OnPush

})
export class RecipeComponent implements OnInit {

  recipe$ = this.recipeService.recipe$;

  constructor(private recipeService: RecipeService,
    private route: ActivatedRoute
    ) {

  }

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');
    this.recipeService.loadRecipeById(+id);
  }

  updateRecipe(recipe: Recipe) {
    this.recipeService.openAddRecipeDialog(recipe);
  }

}
