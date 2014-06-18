package com.tsjd.HotMeals;

import java.util.ArrayList;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.example.tabtest.R;

/**
 * 
 * @author Daniel Roeven
 * @author Sander van Dam
 * @author Timo van Niedek
 * @author Jaco Schalij
 * @version 0.5
 *
 */

public class HomeTab extends BaseTabFragment {

	private DataBaseHelper recipesHelper;
	private SQLiteDatabase recipesReadableDatabase;
	private ArrayList<Recipe> recentRecipes;
	
	/**
	 * Android-standard onCreate
	 */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * Initialize necessary items such as database(helper), lists etc.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.home_layout, container, false);
        
        //Retrieve databasehelper
        recipesHelper = ((MainActivity)this.getActivity()).getDatabaseHelper();
        
        //Initialize textview with introduction
        TextView tv = (TextView) v.findViewById(R.id.text);
        tv.setText("Welcome to the Hotmeals App!" + '\n'
        		+ "You can search for recipes by pressing the search tab," + '\n'
        		+ "or view recipes that you've marked as a favorite by pressing the favorites tab!" + '\n'
        		+ "Or if you like, browse the recipes you've recently viewed down below.");
        
        //Set top margins so that no overlap occurs
        MarginLayoutParams margins = (MarginLayoutParams) tv.getLayoutParams();
		margins.topMargin = ((MainActivity) this.getActivity()).getTabBarHeight();
		tv.setLayoutParams(margins);
        recipesReadableDatabase = recipesHelper.getReadableDatabase();
        
        //Init database list
        Cursor cursor = getRecentRecipes();
        recentRecipes = getRecipesFromCursor(cursor);
        cursor.close();
        
        //Done with database
        recipesReadableDatabase.close();
        
        //Populate list
        final ListView recentsList = (ListView) v.findViewById(R.id.recentsList);
        RecipeListViewAdapter adapter = new RecipeListViewAdapter(getActivity(), recipeArrayListToArray(recentRecipes));
		recentsList.setAdapter(adapter);
		
		//When recipe in list clicked, go to recipe
		recentsList.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Recipe recipe = (Recipe)recentsList.getItemAtPosition(position);
				goToRecipe(recipe);
	        }
		});
        
        return v;
    }
    
    /**
     * Go to recipeview using a bundle to pass along the recipe
     * @param recipe to bundle and pass along
     */
    private void goToRecipe(Recipe recipe)
    {
    	Bundle arguments = new Bundle();
		arguments.putParcelable("recipe", recipe);
		Fragment recipeFragment = new RecipeContainerFragment();
		recipeFragment.setArguments(arguments);
		try {
			((BaseTabFragment)getParentFragment()).replaceFragment(recipeFragment, true);
		} catch (Exception e) {
			throw e;
		} 
    }
    
    /**
     * Fill arraylist with recipes from cursor
     * @param cursor with recipes
     * @return arraylist with recipes
     */
    private ArrayList<Recipe> getRecipesFromCursor(Cursor c)
    {
    	//Move to first recipe
    	ArrayList<Recipe> recipes = new ArrayList<Recipe>();
    	c.moveToFirst();
    	
    	//Then add all recipes in returned cursor to the arraylist
    	while (!c.isAfterLast())
    	{
    		try{
    			recipes.add(getRecipeFromID(c.getInt(c.getColumnIndex("ID"))));
    		} catch (Exception e) {
    			throw new Error(e);
    		}
    		c.moveToNext();
    	}
    	
    	//Close cursor
    	c.close();
    	
    	//Return said arraylist
    	return recipes;
    }
    
    /**
     * Adapter needs array, so creates an array with same content as an arraylist
     * @param arraylist with recipes
     * @return array with recipes
     */
    private Recipe[] recipeArrayListToArray(ArrayList<Recipe> recipes)
	{
		Recipe[] recipesArray;
		try {
			recipesArray = new Recipe[recipes.size()];
		} catch (Exception e) {
			throw new Error(e);
		}
		for (int i = 0; i < recipes.size(); i++)
		{
			recipesArray[i] = recipes.get(i);
		}
		
		return recipesArray;
	}
    
    /**
     * Use SQL to get recent recipe ID's
     * @return cursor with recent recipe ID's
     */
    private Cursor getRecentRecipes()
    {
    	//Use following query to retrieve recent recipe ID's
    	/*
    	 * SELECT ID FROM HotMeals 
		 * WHERE TimeViewed > 0
		 * ORDER BY TimeViewed DESC
    	 */
    	
    	Cursor cursor;
    	
    	String query = "SELECT ID FROM HotMeals WHERE TimeViewed > 0 ORDER BY TimeViewed DESC";
		
    	//Apply query to cursor
    	try {
			cursor = recipesReadableDatabase.rawQuery(query, null);
		} catch (Exception e) {
			throw new Error(e);
		}
		
    	return cursor;
    }
    
    /**
     * Use SQL to retrieve recipe from recipe ID
     * @param ID to retrieve recipe from
     * @return recipe
     */
    private Recipe getRecipeFromID(int ID)
    {
    	//Use following query to convert ID's to recipes
    	/*
    	 * SELECT Naam, Bereiding, Tijd, Prijs, Favorite, ID, Path FROM HotMeals
		 * WHERE ID = 1
		 * 
		 * SELECT Hoeveelheid, Eenheid, Naam FROM Ingredienten WHERE ID = ...
    	 */
    	
    	//Apply queries to cursors
    	Cursor recipeCursor;
		Cursor ingredientsCursor;
		try {
			String recipeQuery = "SELECT Naam, Bereiding, Tijd, Prijs, Favorite, ID, Path FROM HotMeals WHERE ID = " + ID;
			recipeCursor = recipesReadableDatabase.rawQuery(recipeQuery, null);
			
			String ingredientsQuery = "SELECT Hoeveelheid, Eenheid, Naam FROM Ingredienten WHERE ID = " + ID;
			ingredientsCursor = recipesReadableDatabase.rawQuery(ingredientsQuery, null);
		} catch (Exception e1){
			throw new Error(e1);
		}
    
		//Create ingredients arraylist
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
    	
		//Create recipe
		Recipe recipe;
		try {
			recipeCursor.moveToFirst();
	    	boolean favorite = recipeCursor.getInt(4) == 1;
	    	recipe = new Recipe(recipeCursor.getString(0), ingredients, recipeCursor.getString(1), recipeCursor.getInt(2), recipeCursor.getFloat(3) / 100, favorite, recipeCursor.getInt(5), recipeCursor.getString(6));
	    	recipeCursor.close();
		} catch (Exception e) {
			throw new Error(e);
		}
    	return recipe;
    }
}