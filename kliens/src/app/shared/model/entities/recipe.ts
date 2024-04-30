import { Ingredient } from "./ingredient";

export interface Recipe {
  id: number;
  name: string;
  servings: number;
  prepTimeInMinutes: number;
  shortDescription: string;
  notes: string;
  directions: string;
  image: string;
  ingredients: Ingredient[]
}
