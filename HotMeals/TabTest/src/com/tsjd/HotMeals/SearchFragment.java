package com.tsjd.HotMeals;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
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
import com.tsjd.HotMeals.Recipe.Ingredient;

public class SearchFragment extends Fragment 
{
	private MultiAutoCompleteTextView ingredientsView;
	private SeekBar budgetBar;
	private TextView budgetText;
	private SeekBar timeBar;
	private TextView timeText;
	private Button searchButton;
	//private TextView searchText;
	
	private DataBaseHelper recipesHelper;
	private ArrayList<String> ingredients;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_search, container, false);
        
        ingredients = new ArrayList<String>();
        
        setRecipesHelper();
        setIngredients();
        initializeUI(v);
        
        return v;
    }
    
    /**
     * Voeg listeners toe aan de sliders, textview en button
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
				budgetText.setText("$" + (double)(progress)/100 + "0");
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
        
        //searchText = (TextView) v.findViewById(R.id.textView4);
        
        searchButton = (Button) v.findViewById(R.id.searchButton);
        searchButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				ingredientsView.performValidation();
				
				String ingredientsText = ingredientsView.getText().toString();
				//if (ingredientsText.equals("")) ingredientsText = "niet ingevuld";
				String budget = budgetText.getText().toString();
				if (budget.equals("$0.00")) budget = "niet ingevuld";
				String time = timeText.getText().toString();
				if (time.equals("0h 0m")) time = "niet ingevuld";
				
				Cursor cursor;
				
				if (ingredientsText.equals("")) {
					cursor = search(ingredientsTextToArray(ingredientsText), budgetBar.getProgress(), timeBar.getProgress());
				} else {
					cursor = search(ingredientsTextToArray(ingredientsText), budgetBar.getProgress(), timeBar.getProgress());
				}
				ArrayList<Recipe> recipes = getRecipesFromCursor(cursor);
				goToResults(recipes);
			}
		});
        
        ingredientsView.setFocusable(true);
        ingredientsView.clearFocus();
    }
    
    //TODO: Vul de code in om naar de resultaten te gaan
    private void goToResults(ArrayList<Recipe> recipes)
    {
    	Log.d("goToResults", "Size of recipes is: " + recipes.size());
    }
    
    private ArrayList<String> ingredientsTextToArray(String text)
    {
    	ArrayList<String> ingredients = new ArrayList<String>();
    	String replacedText = text.replaceAll(", ", "-");
    	Log.d("textToArray", "text is: " + replacedText);
    	if (replacedText.contains("-")) 
    	{	
    		Log.d("textToArray", "text.contains(\"-\") is true");
    		
			String[] ingredientsString;
			ingredientsString  = replacedText.split("-");
			Log.d("textToArray", "length: " + ingredientsString.length);
    		for (int i = 0; i < ingredientsString.length; i++)
	    	{	
	    		ingredients.add(ingredientsString[i]);
	    		Log.d("textToArray", ingredientsString[i]);
	    	}
    	} else if (text == ""){
    		// Do nothing, no ingredients were entered
    	} else {
    		ingredients.add(text);
    	}
    	return ingredients;
    }
    
    private void setRecipesHelper()
    {
    	recipesHelper = ((MainActivity)this.getActivity()).getDatabaseHelper();
    }
    
    private void setIngredients()
    {
    	Cursor cursor = getIngredientMatches();
    	
    	do{
    		String currentElement = cursor.getString(0);
    		
    		addToAutocomplete(currentElement);
    	} while (cursor.moveToNext());
    	cursor.close();
    }
    
    private void addToAutocomplete(String ingredient)
    {
		Log.d("addToAutocomplete", ingredients.contains(ingredient) + " for " + ingredient);
		if (!ingredients.contains(ingredient))
		{
			ingredients.add(ingredient);
			Log.d("setIngredients", ingredient + " added.");
		}
    }
    
    private Cursor getIngredientMatches() {
    	try{
    		
    		Cursor cursor = recipesHelper.getReadableDatabase().query(true, "Ingredienten", new String[]{"Naam"}, null, null, null, null, null, null);
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
    
    private Cursor search(ArrayList<String> ingredients, int maxPrice, int minutes)
    {
    	/* 
    	 * SELECT H.ID, H.Naam
	     * FROM HotMeals H
		 * INNER JOIN Ingredienten I ON (H.ID = I.ID)
		 * WHERE H.prijs <= 120 AND H.tijd <= 1000 
		 * AND I.Naam IN ("Kaas", "Ham")
		 * GROUP BY I.ID
     	 * ORDER BY COUNT(*) DESC, H.prijs DESC, H.tijd DESC
    	 */
    	
    	Log.d("Search", "Searching with maxPrice: " + maxPrice + " minutes: " + minutes);
    	
    	
    	StringBuilder query = new StringBuilder();
    	query.append("SELECT H.ID, H.Naam "
    			+ "FROM HotMeals H "
    			+ "INNER JOIN Ingredienten I ON (H.ID = I.ID) "
    			+ "WHERE H.prijs <= \"" + maxPrice + "\"  AND H.tijd <= \"" + minutes + "\" ");
    	
    	if (ingredients.size() > 0)
    	{
    		Log.d("Search", "Ingredients.size > 0");
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
    	
    	Log.d("search", query.toString());
    	
    	Cursor cursor;
		try {
			cursor = recipesHelper.getReadableDatabase().rawQuery(query.toString(), null);
		} catch (SQLException e) {
			throw new Error(e);
		}
    	
    	return cursor;
    }
    
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
