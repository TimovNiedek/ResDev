package com.tsjd.HotMeals;

import java.util.ArrayList;

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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.MultiAutoCompleteTextView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.example.tabtest.R;

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
        
        ingredientsView = (MultiAutoCompleteTextView) v.findViewById(R.id.multiAutoCompleteTextView2);
        ingredientsView.setAdapter(adapter);
        ingredientsView.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
        
        ingredientsView.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				ingredientsView.requestFocusFromTouch();
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
				budgetText.setText("$" + (double)(progress)/10 + "0");
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
				if (ingredientsText.equals("")) ingredientsText = "niet ingevuld";
				String budget = budgetText.getText().toString();
				if (budget.equals("$0.00")) budget = "niet ingevuld";
				String time = timeText.getText().toString();
				if (time.equals("0h 0m")) time = "niet ingevuld";
				
				/*searchText.setText(	"Ingredients=" + ingredientsText + 
									"\nBudget=" + budget + 
									"\nTime=" + time);*/
				
			}
		});
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
    		/*StringBuilder ingredient = new StringBuilder();		oude code, bewaard voor mogelijke terugdraaiings
    		
    		for (int i = 0; i < currentElement.length(); i++) 
    		{
    			char currentChar = currentElement.charAt(i);
    			Log.v("ingredients", "" + currentChar);
				if (currentChar != ',' && currentChar != ' ')
				{
					ingredient.append(currentChar);
				} else if (currentChar == ',') {
					addToAutocomplete(ingredient.toString());
					ingredient = new StringBuilder();
				}
			}*/
    		
    		addToAutocomplete(currentElement);
    	} while (cursor.moveToNext());
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
    		
    		Cursor cursor = recipesHelper.getReadableDatabase().query(true, "Ingrediënten", new String[]{"Naam"}, null, null, null, null, null, null);
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
    	/* SELECT * 
    	 * FROM HotMeals H, Ingrediënten I
    	 * WHERE H.prijs <= maxPrice AND H.tijd <= minutes
    	 * AND COUNT(
    	 * 		SELECT Naam FROM Ingrediënten I
    	 * 		WHERE H.ID = I.ID
    	 * 		AND (I.Naam = ingredients[0] OR I.Naam = ingredients[1] OR ......)
    	 * 		)
    	 * > 0		
    	 * ORDER BY COUNT(
    	 * 		SELECT Naam FROM Ingrediënten I
    	 * 		WHERE H.ID = I.ID
    	 * 		AND (I.Naam = ingredients[0] OR I.Naam = ingredients[1] OR ......)
    	 * 		) DESC, H.prijs DESC, H.tijd DESC
    	 */
    	
    	StringBuilder query = new StringBuilder();
    	StringBuilder subQuery = new StringBuilder();
    	subQuery.append("SELECT * FROM Ingrediënten I"
    			+ " WHERE H.ID = I.ID"
    			+ " AND (");
    	
    	for (int i = 0; i < ingredients.size(); i++)
    	{
    		String ingredient = ingredients.get(i);
    		subQuery.append("I.Naam = " + ingredient);
    		if (i < ingredients.size() - 1) subQuery.append(" OR ");
    	}
    	
    	subQuery.append(")");
    	
    	query.append("SELECT * FROM HotMeals H, Ingrediënten I"
    						+ "WHERE H.prijs <= " + maxPrice
    						+ " AND H.tijd <= " + minutes
    						+ " AND COUNT(" + subQuery + ") > 0"
    						+ " ORDER BY COUNT(" + subQuery + ") DESC, H.prijs DESC, H.tijd DESC");
    	
    	Log.d("search", query.toString());
    	
    	return recipesHelper.getReadableDatabase().rawQuery(query.toString(), null);
    }
}
