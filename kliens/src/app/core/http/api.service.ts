import { HttpClient, HttpResponse } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Recipe } from "src/app/shared/model/entities/recipe";
import { UserData } from "src/app/shared/model/entities/user-data";
import { LoginRequest } from "src/app/shared/model/request/login-request";
import { RegisterRequest } from "src/app/shared/model/request/register-request";
import { SaveRecipeRequest } from "src/app/shared/model/request/save-recipe-request";
import { UpdateUserDataRequest } from "src/app/shared/model/request/update-user-data-request";
import { LoginResponse } from "src/app/shared/model/response/login-response";
import { environment } from "src/environments/environments";

@Injectable({
  providedIn: 'root'
})
export class ApiService {
  environment = environment;

  constructor(private http: HttpClient) { }

  signUp(request: RegisterRequest) {
    return this.http.post<any>(this.environment.backendUrl + 'users/', request);
  }

  signIn(request: LoginRequest) {
    return this.http.post<LoginResponse>(this.environment.backendUrl + 'auth/login', request);
  }

  checkSession(sessionId: string) {
    return this.http.get<{isValid: boolean}>(this.environment.backendUrl + 'auth/checksession/' + sessionId);
  }

  getAllRecipes() {
    return this.http.get<Recipe[]>(this.environment.backendUrl + 'recipes/');
  }

  saveRecipe(request: SaveRecipeRequest) {
    return this.http.post<Recipe>(this.environment.backendUrl + 'recipes/', request);
  }

  updateRecipe(request: SaveRecipeRequest) {
    return this.http.put<Recipe>(this.environment.backendUrl + 'recipes/', request);
  }

  deleteRecipe(recipeId: number) {
    return this.http.delete<any>(this.environment.backendUrl + 'recipes/' + recipeId);
  }

  loadUserData(sessionId: string) {
    return this.http.get<UserData>(this.environment.backendUrl + 'users/bysession/' + sessionId);
  }

  updateUserData(request: UpdateUserDataRequest) {
    return this.http.put<UserData>(this.environment.backendUrl + 'users/', request);
  }

  uploadProfilImage(file: File, userId: number) {
    let formData = new FormData();
    formData.append('profil-image', file);
    return this.http.post<any>(this.environment.backendUrl + 'users/' + userId + '/uploadprofileimage', formData);
  }

  loadRecipeById(recipeId: number) {
    return this.http.get<Recipe>(this.environment.backendUrl + 'recipes/' + recipeId);
  }

  uploadRecipeImage(file: File, recipeId: number) {
    let formData = new FormData();
    formData.append('image', file);
    return this.http.post<any>(this.environment.backendUrl + 'recipes/' + recipeId +'/uploadimage', formData);
  }
}
