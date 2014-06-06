package com.tsjd.HotMeals;

//import android.app.Fragment;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
import java.io.File;

import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tabtest.R;

public class RecipeView extends Fragment {

	private DataBaseHelper recipesHelper;
	private Recipe recipe;
	private TextView recipeName;
	private TextView recipeIngredients;
	private TextView recipeHowto;
	private TextView recipeTime;
	private TextView recipePrice;
	private ImageView receptImage;
	private Button favButton;
	private Drawable image;
	
	
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
		try{
		recipePrice = (TextView) v.findViewById(R.id.rvtextViewPrijs);
		}catch(Exception e){
			throw e;
		}
		recipeTime = (TextView) v.findViewById(R.id.rvtextViewTijd);
		receptImage = (ImageView) v.findViewById(R.id.rvimageViewMain);
		favButton = (Button) v.findViewById(R.id.favouriteButton);
		
		//String pathName = "/res/drawable-hdpi/"+ recipe.getPath()+".png"; 
		
		
		
		
		
		try{
		File file = new File("/TabTest/res/drawable-hdpi/custom_home.png");
		Uri uri = Uri.fromFile(file);
		String path = uri.getPath();
		Drawable image = Drawable.createFromPath(path);
		receptImage.setImageDrawable(image);
		receptImage.setBackgroundColor(-65536);
		receptImage.setVisibility(0);
		}catch (Exception e){
			throw e;
		}
		
		receptImage.setBackgroundColor(0);
		
		recipeName.setText(recipe.getName());
		recipeIngredients.setText(recipe.ingredientenToString());
		recipeHowto.setText(recipe.getBereiding());
		recipePrice.setText(recipe.getPrice()+"");
		recipeTime.setText(recipe.getTime()+"");
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
	
	private void setFavouriteButtonText(){
		if(recipe.favoriet()){
			favButton.setText("Favourite this Recipe");
		}
		else{
			favButton.setText("Unfavourite this Recipe");
		}
	}
}
