package com.tsjd.HotMeals;

import java.util.ArrayList;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.tabtest.R;
import com.tsjd.HotMeals.Recipe.Ingredient;

public class HomeTab extends Fragment {

	private DataBaseHelper recipesHelper;
	private ArrayList<Recipe> recentRecipes;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.home_layout, container, false);
        
        recipesHelper = ((MainActivity)this.getActivity()).getDatabaseHelper();
        
        TextView tv = (TextView) v.findViewById(R.id.text);
        tv.setText("Welcome to the Hotmeals App!");
        
        //Cursor cursor = getRecentRecipes();
        //recentRecipes = getRecipesFromCursor(cursor);
        
        //ListView recentsList = (ListView) v.findViewById(R.id.recentsList);
        
        return v;
    }
    
    private ArrayList<Recipe> getRecipesFromCursor(Cursor c)
    {
    	ArrayList<Recipe> recipes = new ArrayList<Recipe>();
    	
    	c.moveToFirst();
    	
    	Log.d("getRecipesFromCursor:HomeTab", "Row count: " + c.getCount());
    	
    	while (!c.isAfterLast())
    	{
    		recipes.add(getRecipeFromID(c.getInt(c.getColumnIndex("ID"))));
    		c.moveToNext();
    	}
    	
    	return recipes;
    }
    
    private Cursor getRecentRecipes()
    {
    	/**
    	 * SELECT ID FROM HotMeals 
		 * WHERE TimeViewed > 0
		 * ORDER BY TimeViewed DESC
    	 */
    	
    	String query;
		try {
			query = "SELECT ID FROM HotMeals WHERE TimeViewed > 0 ORDER BY TimeViewed DESC";
		} catch (Exception e) {
			throw new Error(e);
		}
    	
    	Cursor cursor = recipesHelper.getReadableDatabase().rawQuery(query, null);
    	return cursor;
    }
    
    private Recipe getRecipeFromID(int ID)
    {
    	/**
    	 * SELECT Naam, Bereiding, Tijd, Prijs, Favorite, ID, Path FROM HotMeals
		 * WHERE ID = 1
		 * 
		 * SELECT Hoeveelheid, Eenheid, Naam FROM Ingredienten WHERE ID = ...
    	 */
    	
    	
    	Cursor recipeCursor;
		Cursor ingredientsCursor;
		try {
			String recipeQuery = "SELECT Naam, Bereiding, Tijd, Prijs, Favorite, ID, Path FROM HotMeals WHERE ID = " + ID;
			recipeCursor = recipesHelper.getReadableDatabase().rawQuery(recipeQuery, null);
			
			String ingredientsQuery = "SELECT Hoeveelheid, Eenheid, Naam FROM Ingredienten WHERE ID = " + ID;
			ingredientsCursor = recipesHelper.getReadableDatabase().rawQuery(ingredientsQuery, null);
		} catch (Exception e1){
			throw new Error(e1);
		}
    	
    	ArrayList<Ingredient> ingredients = new ArrayList<Ingredient>();
    	
    	ingredientsCursor.moveToFirst();
		try {
			do{
				Recipe.Ingredient ingredient = new Recipe.Ingredient(ingredientsCursor.getFloat(ingredientsCursor.getColumnIndex("Hoeveelheid")), 
																	ingredientsCursor.getString(ingredientsCursor.getColumnIndex("Eenheid")), 
																	ingredientsCursor.getString(ingredientsCursor.getColumnIndex("Naam")));
				ingredients.add(ingredient);
			} while (ingredientsCursor.moveToNext());
		} catch (Exception e) {
			throw new Error(e);
		}
		
		ingredientsCursor.close();
    	
		recipeCursor.moveToFirst();
    	boolean favorite = recipeCursor.getInt(4) == 1;
    	Recipe recipe = new Recipe(recipeCursor.getString(0), ingredients, recipeCursor.getString(1), recipeCursor.getInt(2), recipeCursor.getFloat(3) / 100, favorite, recipeCursor.getInt(5), recipeCursor.getString(6));
    	return recipe;
    }
}