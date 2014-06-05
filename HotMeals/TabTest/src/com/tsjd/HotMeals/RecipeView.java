package com.tsjd.HotMeals;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.tabtest.R;

public class RecipeView extends Fragment {

	private DataBaseHelper recipesHelper;
	private TextView recipeName;
	private TextView recipeIngredients;
	private TextView recipeHowto;
	private TextView recipeTime;
	private TextView recipePrice;
	private Button favButton;
	
	
	private void setRecipesHelper()
    {
    	recipesHelper = ((MainActivity)this.getActivity()).getDatabaseHelper();
    }
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.recipe_view_fragment, container, false);
        
        //Bundle extras = getIntent().getExtras();
        
        setRecipesHelper();
        
        initializeUI(v);
        return v;
    }
	
	private void initializeUI(View v){
		recipeName = (TextView) v.findViewById(R.id.rvtextViewName);
		recipeIngredients = (TextView) v.findViewById(R.id.rvtextViewIngredients);
		recipeHowto = (TextView) v.findViewById(R.id.rvtextViewHowto);
		recipePrice = (TextView) v.findViewById(R.id.rvtextViewPrijs);
		recipeTime = (TextView) v.findViewById(R.id.rvtextViewTijd);
		favButton = (Button) v.findViewById(R.id.favouriteButton);
		
		favButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v){
				
			}
		});
	}
}
