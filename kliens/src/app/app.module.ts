import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { AuthComponent } from './components/auth/auth.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {MatTabsModule} from '@angular/material/tabs';
import {MatButtonModule} from '@angular/material/button';
import { MainComponent } from './components/main/main.component';
import {MatIconModule} from '@angular/material/icon';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { RecipesComponent } from './components/main/recipes/recipes.component';
import { RecipeComponent } from './components/main/recipes/recipe/recipe.component';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { ReactiveFormsModule } from '@angular/forms';
import {MatSnackBarModule} from '@angular/material/snack-bar';
import { AuthInterceptor } from './core/interceptor/auth.interceptor';
import { ToastrModule } from 'ngx-toastr';
import {MatDialogModule} from '@angular/material/dialog';
import { AddRecipeDialog } from './shared/components/add-recipe/add-recipe.component';
import { UserOperationDialog } from './shared/components/user-operation/user-operation.component';







@NgModule({
  declarations: [
    AppComponent,
    AuthComponent,
    MainComponent,
    RecipesComponent,
    RecipeComponent,
    AddRecipeDialog,
    UserOperationDialog

  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    AppRoutingModule,
    MatTabsModule,
    MatButtonModule,
    MatIconModule,
    HttpClientModule,
    FontAwesomeModule,
    ReactiveFormsModule,
    MatSnackBarModule,
    ToastrModule.forRoot(),
    MatDialogModule
  ],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true},
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
