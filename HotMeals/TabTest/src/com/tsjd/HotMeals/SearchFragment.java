package com.tsjd.HotMeals;

import java.util.ArrayList;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.MultiAutoCompleteTextView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

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

public class SearchFragment extends BaseTabFragment 
{
	private MultiAutoCompleteTextView ingredientsView;
	private SeekBar budgetBar;
	private TextView budgetText;
	private SeekBar timeBar;
	private TextView timeText;
	private Button searchButton;
	private DataBaseHelper recipesHelper;
	private SQLiteDatabase recipesReadableDatabase;
	private ArrayList<String> ingredients;
	
	/**
	 * Android-standard oncreate
	 */
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

	/**
	 * Initialize stuff like databasehelpers
	 */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_search, container, false);
        
        ingredients = new ArrayList<String>();
        
        
        setRecipesHelper();
        recipesReadableDatabase = recipesHelper.getReadableDatabase();

        
        setIngredients();
        initializeUI(v);
        
        return v;
    }
    
    /**
     * Add listeners to sliders, textview, button
     * @param v De parent view van de UI elementen
     */
    private void initializeUI(View v)
    {
    	ArrayAdapter<String> adapter = new ArrayAdapter<String>(v.getContext(), android.R.layout.simple_list_item_1, ingredients);
        
        ingredientsView = (MultiAutoCompleteTextView) v.findViewById(R.id.multiAutoCompleteTextView1);
        ingredientsView.setAdapter(adapter);
        ingredientsView.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
        
        ingredientsView.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				ingredientsView.requestFocusFromTouch();
				InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
		        imm.showSoftInput(ingredientsView, InputMethodManager.SHOW_IMPLICIT);
				return true;
			}
		});
        
        budgetBar = (SeekBar) v.findViewById(R.id.seekBar1);
        budgetText = (TextView) v.findViewById(R.id.budgetTextView);
        budgetBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				budgetText.setText("€" + Recipe.doubleToCurrency((double)(progress)/100));
			}
		});
        
        timeBar = (SeekBar) v.findViewById(R.id.timeSeekBar);
        timeText = (TextView) v.findViewById(R.id.timeTextView);
        timeBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				int hours = progress / 60;
				int mins = progress % 60;
				timeText.setText(hours + "h " + mins + "m");
			}
		});
                
        searchButton = (Button) v.findViewById(R.id.searchButton);
        searchButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				ingredientsView.performValidation();
				
				String ingredientsText = ingredientsView.getText().toString();
				String budget = budgetText.getText().toString();
				if (budget.equals("eur0.00")) budget = "niet ingevuld";
				String time = timeText.getText().toString();
				if (time.equals("0h 0m")) time = "niet ingevuld";
				
				Cursor cursor;
				
				if (ingredientsText.equals("")) {
					cursor = search(ingredientsTextToArray(ingredientsText), budgetBar.getProgress(), timeBar.getProgress());
				} else {
					cursor = search(ingredientsTextToArray(ingredientsText), budgetBar.getProgress(), timeBar.getProgress());
				}
				ArrayList<Recipe> recipes = getRecipesFromCursor(cursor);
				cursor.close();
				goToResults(recipes);
			}
		});
        
        ingredientsView.setFocusable(true);
        ingredientsView.clearFocus();
    }
    
    /**
     * Go to results to show recipes
     * @param said recipes to show
     */
    private void goToResults(ArrayList<Recipe> recipes)
    {
    	Log.d("goToResults", "Size of recipes is: " + recipes.size());
    	
    	recipesReadableDatabase.close();
    
		Fragment newFragment = new RecipeListViewFragment();
		
		Bundle recipeBundle = new Bundle();
		recipeBundle.putParcelableArrayList("recipes", recipes);
		newFragment.setArguments(recipeBundle);
		
		Log.d("goToResults", "BaseTabFragment: " + getParentFragment().getTag());
		((BaseTabFragment)getParentFragment()).replaceFragment(newFragment, true);
    }
    
    /**
     * Convert search ingredients to arraylist
     * @param text from MultiAutocompleteTextView field
     * @return
     */
    private ArrayList<String> ingredientsTextToArray(String text)
    {
    	ArrayList<String> ingredients = new ArrayList<String>();
    	
    	Log.d("textToArray", "Length of text is: " + text.length());
    	
    	if (text.length() == 0){
    		Log.d("textToArray", "No ingredients were entered");
    	} else {
	    	
	    	String replacedText = text.replaceAll(", ", "-");
	    	Log.d("textToArray", "text is: " + replacedText);
	    	if (replacedText.contains("-")) 
	    	{	
	    		Log.d("textToArray", "text.contains(\"-\") is true");
	    		
				String[] ingredientsString;
				ingredientsString  = replacedText.split("-");
	    		for (int i = 0; i < ingredientsString.length; i++)
		    	{	
		    		ingredients.add(ingredientsString[i]);
		    	}
	    	} else {
	    		ingredients.add(text);
	    	}
    	}
    	return ingredients;
    }
    
    /**
     * Set databasehelper
     */
    private void setRecipesHelper()
    {
    	recipesHelper = ((MainActivity)this.getActivity()).getDatabaseHelper();
    }
    
    /**
     * Set matching ingredients to textfield
     */
    private void setIngredients()
    {
    	Cursor cursor = getIngredientMatches();
    	
    	do{
    		String currentElement = cursor.getString(0);
    		
    		addToAutocomplete(currentElement);
    	} while (cursor.moveToNext());
    	cursor.close();
    }
    
    /**
     * Add matching ingredients to MultiAutocompleteTextView
     * @param ingredient to add
     */
    private void addToAutocomplete(String ingredient)
    {
		Log.d("addToAutocomplete", ingredients.contains(ingredient) + " for " + ingredient);
		if (!ingredients.contains(ingredient))
		{
			ingredients.add(ingredient);
		}
    }
    
    /**
     * Get ingredient matches from database
     * @return cursor with ingredient matches
     */
    private Cursor getIngredientMatches() {
    	try{
    		
    		Cursor cursor = recipesReadableDatabase.query(true, "Ingredienten", new String[]{"Naam"}, null, null, null, null, null, null);
    		if (cursor == null) {
                return null;
            } else if (!cursor.moveToFirst()) {
                cursor.close();
                return null;
            }
            return cursor;
    	}
    	catch (SQLException e)
    	{
    		throw new Error(e);
    	}   
    }
    
    /**
     * Use SQL to search through the database to find matching ingredients
     * @param ingredients
     * @param maxPrice
     * @param minutes
     * @return cursor with recipes to show
     */
    private Cursor search(ArrayList<String> ingredients, int maxPrice, int minutes)
    {
    	//The SQL query to search the database
    	/* 
    	 * SELECT H.ID, H.Naam
	     * FROM HotMeals H
		 * INNER JOIN Ingredienten I ON (H.ID = I.ID)
		 * WHERE H.prijs <= 120 AND H.tijd <= 1000 
		 * AND I.Naam IN ("Kaas", "Ham")
		 * GROUP BY I.ID
     	 * ORDER BY COUNT(*) DESC, H.prijs DESC, H.tijd DESC
    	 */
    	
    	StringBuilder query = new StringBuilder();
    	query.append("SELECT H.ID, H.Naam "
    			+ "FROM HotMeals H "
    			+ "INNER JOIN Ingredienten I ON (H.ID = I.ID) "
    			+ "WHERE H.prijs <= \"" + maxPrice + "\"  AND H.tijd <= \"" + minutes + "\" ");
    	
    	if (ingredients.size() > 0)
    	{
    		Log.d("Search", "Ingredients.size > 0");
    		Log.d("Search", "First ingredient name is: " + ingredients.get(0));
    		query.append("AND I.NAAM IN (");
	    	for (int i = 0; i < ingredients.size(); i++)
	    	{
	    		String ingredient = ingredients.get(i);
	    		query.append("\"" + ingredient + "\"");
	    		if (i < ingredients.size() - 1) query.append(", ");
	    	}
	    	query.append(") ");
    	}
    	
    	query.append("GROUP BY I.ID "
    			+ "ORDER BY COUNT(*) DESC, H.prijs DESC, H.tijd DESC");
    	
    	Cursor cursor;
		try {
			cursor = recipesReadableDatabase.rawQuery(query.toString(), null);
		} catch (SQLException e) {
			throw new Error(e);
		}
    	
    	return cursor;
    }
    
    /**
     * From cursor to arraylist
     * @param cursor
     * @return arraylist
     */
    private ArrayList<Recipe> getRecipesFromCursor(Cursor c)
    {
    	Log.d("getRecipesFromCursor", "Amount of rows: " + c.getCount());
    	ArrayList<Recipe> recipes = new ArrayList<Recipe>();
    	c.moveToFirst();
    	while (!c.isAfterLast()) {
    		try {
    			Log.d("getRecipesFromCursor", "Amount of columns: " + c.getColumnCount());
    			recipes.add(getRecipeFromID(c.getInt(0)));
    		} catch (Exception e) {
    			throw new Error(e);
    		}
    		c.moveToNext();
    	}
    	c.close();
    	
    	return recipes;
    }
    
    /**
     * Retrieve recipe from recipe ID the other database
     * @param ID
     * @return recipe
     */
    private Recipe getRecipeFromID(int ID)
    {
    	//Use following SQL query to retrieve recipe from recipe ID
    	/*
    	 * SELECT Naam, Bereiding, Tijd, Prijs, Favorite, ID, Path FROM HotMeals
		 * WHERE ID = 1
		 * 
		 * SELECT Hoeveelheid, Eenheid, Naam FROM Ingredienten WHERE ID = ...
    	 */
    	
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