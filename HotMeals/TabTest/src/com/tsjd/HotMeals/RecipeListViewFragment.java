package com.tsjd.HotMeals;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.example.tabtest.R;

/**
 * 
 * @author Sander van Dam
 * @author Daniel Roeven
 * @author Timo van Niedek
 * @author Jaco Schalij
 * @version 0.5
 * 
 *          This class creates the fragment in which the RecipeList is created,
 *          it creates a ListView and uses RecipeListViewAdapter to fill this
 *          ListView Because RecipeListViewAdapter only accepts an array and we
 *          have an ArrayList of the recipes, we fill an array with the contents
 *          from the ArrayList
 * 
 */
public class RecipeListViewFragment extends BaseTabFragment {

	private ArrayList<Recipe> recipes;

	/**
	 * Standard Android function
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	/**
	 * This function sets the view to our ListView
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_recipelistview, container,
				false);
		
		return v;
	}

	/**
	 * 
	 * When the Activity is created we send a Bundle with extra arguments
	 * obtained with a parcelable to the fragment
	 * 
	 */
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		this.getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
		
		Bundle arguments = this.getArguments();
		recipes = arguments.getParcelableArrayList("recipes");

		Log.d("onCreateView: RLVF", "Recipes is null is: " + (recipes == null));
		try {
			Log.d("onCreateView: RLVF", recipes.toString());
		} catch (Exception e) {
			throw new Error(e);
		}

		Log.d("onCreateView: RLVF",
				"Size of arguments is: " + arguments.size()
						+ " and contains recipes is: "
						+ arguments.containsKey("recipes"));
		Log.d("onCreateView: RLVF", "Length of recipes is: " + recipes.size());

		final ListView listview = (ListView) getView().findViewById(
				R.id.listview);

		RecipeListViewAdapter adapter = new RecipeListViewAdapter(
				getActivity(), recipeArrayListToArray(recipes));
		listview.setAdapter(adapter);
		listview.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Recipe recipe = (Recipe) listview.getItemAtPosition(position);
				Bundle arguments = new Bundle();
				arguments.putParcelable("recipe", recipe);
				Fragment recipeFragment = new RecipeView();
				recipeFragment.setArguments(arguments);
				((BaseTabFragment) getParentFragment()).replaceFragment(
						recipeFragment, true);

			}
		});
	}

	/**
	 * Fills an array with the contents of an ArrayList
	 * 
	 * @param recipes the ArrayList with recipes
	 * @return recipes an array of the param recipes 
	 */
	private Recipe[] recipeArrayListToArray(ArrayList<Recipe> recipes) {

		Recipe[] recipesArray;
		try {
			recipesArray = new Recipe[recipes.size()];
		} catch (Exception e) {
			throw new Error(e);
		}
		for (int i = 0; i < recipes.size(); i++) {
			recipesArray[i] = recipes.get(i);
		}

		return recipesArray;
	}
}
