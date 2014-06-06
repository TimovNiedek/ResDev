package com.tsjd.HotMeals;

import java.util.ArrayList;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class FavoritesHelper {
	
	private DataBaseHelper favoritesHelper;
	private SQLiteDatabase dBase;
	
	
	public FavoritesHelper (DataBaseHelper dbHelper){
		this.favoritesHelper = dbHelper;
		this.dBase = dbHelper.getReadableDatabase();
	}
	
	/**
	 * Haal alle recepten die gemarkeerd zijn als favoriet uit de database
	 * @return de recepten in een arraylist
	 */
	public ArrayList<Recipe> getFavorites(){
		ArrayList<Recipe> favorites = new ArrayList<Recipe>();
		String query = "SELECT ID FROM HotMeals WHERE Favorite = 1";
		Cursor cursor;
		try {
			cursor = favoritesHelper.getWritableDatabase().rawQuery(query, null);
		} catch (Exception e1) {
			throw e1;
		}
		
		cursor.moveToFirst();
		
		while (!cursor.isAfterLast())
		{
			try {
				
				favorites.add(getRecipeFromID(cursor.getInt(0)));
				cursor.moveToNext();
			}
			catch(Exception e){
				throw new Error(e);
			}
		}
		
		cursor.close();
		
		return favorites;
	}
	
	private Recipe getRecipeFromID(int ID)
    {
    	/**
    	 * SELECT Naam, Bereiding, Tijd, Prijs, Favorite, ID, Path FROM HotMeals
		 * WHERE ID = 1
		 * 
		 * SELECT Hoeveelheid, Eenheid, Naam FROM Ingredienten WHERE ID = ...
    	 */
    	
		Log.d("FavoritesHelper:getRecipeFromID", "Current ID is: " + ID);
    	
    	Cursor recipeCursor;
		Cursor ingredientsCursor;
		try {
			String recipeQuery = "SELECT Naam, Bereiding, Tijd, Prijs, Favorite, ID, Path FROM HotMeals WHERE ID = " + ID;
			recipeCursor = favoritesHelper.getReadableDatabase().rawQuery(recipeQuery, null);
			
			String ingredientsQuery = "SELECT Hoeveelheid, Eenheid, Naam FROM Ingredienten WHERE ID = " + ID;
			ingredientsCursor = favoritesHelper.getReadableDatabase().rawQuery(ingredientsQuery, null);
		} catch (Exception e1){
			throw new Error(e1);
		}
    	
    	ArrayList<Ingredient> ingredients = new ArrayList<Ingredient>();
    	
    	ingredientsCursor.moveToFirst();
		try {
			while (!ingredientsCursor.isAfterLast()) {
				Ingredient ingredient = new Ingredient(ingredientsCursor.getFloat(ingredientsCursor.getColumnIndex("Hoeveelheid")), 
																	ingredientsCursor.getString(ingredientsCursor.getColumnIndex("Eenheid")), 
																	ingredientsCursor.getString(ingredientsCursor.getColumnIndex("Naam")));
				ingredients.add(ingredient);
				ingredientsCursor.moveToNext();
			}
		} catch (Exception e) {
			throw new Error(e);
		}
		
		ingredientsCursor.close();
    	
		recipeCursor.moveToFirst();
    	boolean favorite = recipeCursor.getInt(4) == 1;
    	Recipe recipe = new Recipe(recipeCursor.getString(0), ingredients, recipeCursor.getString(1), recipeCursor.getInt(2), recipeCursor.getFloat(3) / 100, favorite, recipeCursor.getInt(5), recipeCursor.getString(6));
    	recipeCursor.close();
    	return recipe;
    }

}
