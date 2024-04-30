import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthComponent } from './components/auth/auth.component';
import { MainComponent } from './components/main/main.component';
import { RecipeComponent } from './components/main/recipes/recipe/recipe.component';
import { RecipesComponent } from './components/main/recipes/recipes.component';

const routes: Routes = [
  { path: 'auth', component: AuthComponent},
  { path: 'recipes', component: MainComponent, children: [
    { path: '', component: RecipesComponent},
    { path: ':id', component: RecipeComponent}
  ]},
  { path: '**', pathMatch: 'full', redirectTo: '/auth' },

];
@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
