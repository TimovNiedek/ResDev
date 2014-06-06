package com.tsjd.HotMeals;

import java.util.ArrayList;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.example.tabtest.R;

public class RecipeListViewFragment extends BaseTabFragment {

	private ArrayList<Recipe> recipes;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_recipelistview, container,
				false);

		
		

		return v;
	}
	
	@Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle arguments = this.getArguments();
		recipes =  arguments.getParcelableArrayList("recipes");
		
		Log.d("onCreateView: RLVF", "Recipes is null is: " + (recipes == null));
		try {
			Log.d("onCreateView: RLVF", recipes.toString());
		} catch (Exception e) {
			throw new Error(e);
		}
		
		Log.d("onCreateView: RLVF", "Size of arguments is: " + arguments.size() + " and contains recipes is: " + arguments.containsKey("recipes"));
		Log.d("onCreateView: RLVF", "Length of recipes is: " + recipes.size());
		
		final ListView listview = (ListView) getView().findViewById(R.id.listview);
		
		RecipeListViewAdapter adapter = new RecipeListViewAdapter(getActivity(), recipeArrayListToArray(recipes));
		listview.setAdapter(adapter);
		listview.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Recipe recipe = (Recipe)listview.getItemAtPosition(position);
				Bundle arguments = new Bundle();
				arguments.putParcelable("recipe", recipe);
				Fragment recipeFragment = new RecipeContainerFragment();
				recipeFragment.setArguments(arguments);
				((BaseTabFragment)getParentFragment()).addFragmentWithTransition(recipeFragment, true);
				
	        }
		});
    }
	
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

	public void onListItemClick(ListView l, View v, int position, long id) {
		// do something with the data
	}
}
