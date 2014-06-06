package com.tsjd.HotMeals;

//import android.app.Fragment;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
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
	private View backView;
	
	
	private void setRecipesHelper()
    {
    	recipesHelper = ((MainActivity)this.getActivity()).getDatabaseHelper();
    }
    
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.recipe_view_fragment, container, false);
        
        recipe = ((MainActivity)this.getActivity()).getRecipe();
        try{
        setRecipesHelper();
        } catch (Exception e){
        	throw e;
        }
        initializeUI(v);
        return v;
    }
	
	private void initializeUI(View v){
		recipeName = (TextView) v.findViewById(R.id.rvtextViewName);
		recipeIngredients = (TextView) v.findViewById(R.id.rvtextViewIngredients);
		recipeHowto = (TextView) v.findViewById(R.id.rvtextViewHowto);
		recipePrice = (TextView) v.findViewById(R.id.rvtextViewPrijs);
		recipeTime = (TextView) v.findViewById(R.id.rvtextViewTijd);
		receptImage = (ImageView) v.findViewById(R.id.rvimageViewMain);
		favButton = (Button) v.findViewById(R.id.favouriteButton);
		backView = (View) v.findViewById(R.id.rvView);
		MarginLayoutParams margins = (MarginLayoutParams) backView.getLayoutParams();
		margins.topMargin = ((MainActivity) this.getActivity()).getTabBarHeight();
		Log.d("Tab bar height", "" + ((MainActivity) this.getActivity()).getTabBarHeight());
		backView.setLayoutParams(margins);
		
		
		backView.setClickable(true);
		
		//String pathName = "/TabTest/res/drawable-hdpi/"+ recipe.getPath()+".png"; 
		String uri = "@drawable/"+ recipe.getPath();

		int imageResource = getResources().getIdentifier(uri, null, getActivity().getPackageName());

		Drawable res = getResources().getDrawable(imageResource);
		receptImage.setImageDrawable(res);		
		
		recipeName.setText(recipe.getName());
		recipeIngredients.setText(recipe.ingredientenToString());
		recipeHowto.setText(recipe.getBereiding());
		recipePrice.setText("Price per serving: "+ recipe.getPrice());
		recipeTime.setText("Time: "+ recipe.getTime()+" Minuten");
		setFavouriteButtonText();
		
		backView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View V){
				
			}
		});
		favButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v){
				recipesHelper.changeFavourite(recipe.favoriet(), recipe.getId());
				recipe.toggleFavourite();
				setFavouriteButtonText();
			}
		});
	}
	
	private void setFavouriteButtonText(){
		if(recipe.favoriet()){
			favButton.setText("Favourite this Recipe");
		}
		else{
			favButton.setText("Unfavourite this Recipe");
		}
	}
}
