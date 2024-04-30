import { Ingredient } from "../entities/ingredient";

export interface SaveRecipeRequest {
  id?:number;
  name: string;
  servings: number;
  prepTimeInMinutes: number;
  shortDescription: string;
  notes: string;
  directions: string;
  image: string;
  ingredients: Ingredient[]
}
