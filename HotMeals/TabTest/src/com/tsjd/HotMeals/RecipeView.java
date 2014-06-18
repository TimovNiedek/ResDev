package com.tsjd.HotMeals;

/**
 * 
 * @author Daniel Roeven
 * @author Sander van Dam
 * @author Timo van Niedek
 * @author Jaco Schalij
 * @version 0.5
 *
 */

import android.content.res.Resources.NotFoundException;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tabtest.R;

public class RecipeView extends BaseTabFragment {

	private DataBaseHelper recipesHelper;
	private Recipe recipe;
	private TextView recipeName;
	private TextView recipeIngredients;
	private TextView recipeHowto;
	private TextView recipeTime;
	private TextView recipePrice;
	private ImageView receptImage;
	private Button favButton;
	
	/**
	 * Gets the databasehelper instance from the MainActivity.
	 */
	private void setRecipesHelper()
    {
    	recipesHelper = ((MainActivity)this.getActivity()).getDatabaseHelper();
    }
    
	/**
	 * Android standard onCreate().
	 */
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
	
	/**
	 * Gets the recipe to be viewed from the bundle.
	 * Updates the TimeViewed column in the database.
	 */
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.recipe_view_fragment, container, false);
        
        Bundle arguments = new Bundle();
        arguments = this.getArguments();
        recipe = arguments.getParcelable("recipe");
        if (recipe == null) throw new Error("Recipe could not be found in bundle");
        
        try{
        	setRecipesHelper();
        } catch (Exception e){
        	throw e;
        }
        initializeUI(v);
        recipesHelper.updateTimeViewed((int)(System.currentTimeMillis()/60000), recipe.getId());
		
        return v;
    }
	
	/**
	 * Every view in the xml layout is initialized and set.
	 * OnclickListeners are set.
	 * @param v
	 */
	private void initializeUI(View v){
		recipeName = (TextView) v.findViewById(R.id.rvtextViewName);
		recipeIngredients = (TextView) v.findViewById(R.id.rvtextViewIngredients);
		recipeHowto = (TextView) v.findViewById(R.id.rvtextViewHowto);
		recipePrice = (TextView) v.findViewById(R.id.rvtextViewPrijs);
		recipeTime = (TextView) v.findViewById(R.id.rvtextViewTijd);
		receptImage = (ImageView) v.findViewById(R.id.rvimageViewMain);
		favButton = (Button) v.findViewById(R.id.favouriteButton);
		
		String uri = "@drawable/"+ recipe.getPath();

		try {
			int imageResource = getResources().getIdentifier(uri, null, getActivity().getPackageName());

			Drawable res = getResources().getDrawable(imageResource);
			receptImage.setImageDrawable(res);
		} catch (NotFoundException e) {
			Log.d("Path not found", "The path " + recipe.getPath() + " doesn't return a value");
		}		
		
		recipeName.setText(recipe.getName());
		recipeIngredients.setText(recipe.ingredientenToString());
		recipeHowto.setText(recipe.getBereiding());
		recipePrice.setText("Price per serving: €"+ Recipe.doubleToCurrency(recipe.getPrice()));
		recipeTime.setText("Time: "+ recipe.getTime()+" Minuten");
		setFavouriteButtonText();
		
		favButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v){
				recipesHelper.changeFavourite(recipe.favoriet(), recipe.getId());
				
				recipe.toggleFavourite();
				setFavouriteButtonText();
			}
		});
	}
	
	/**
	 * A simple function to set the text on the button depending on whether or not it is a favourited recipe.
	 */
	private void setFavouriteButtonText(){
		((MainActivity)this.getActivity()).updateFavorites = true;
		if(recipe.favoriet()){
			favButton.setText("Unfavourite this Recipe");
		}
		else{
			favButton.setText("Favourite this Recipe");
		}
	}
}
