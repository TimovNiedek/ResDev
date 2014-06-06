package com.tsjd.HotMeals;

import com.example.tabtest.R;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class RecipeListViewContainerFragment extends BaseTabFragment 
{
	 private boolean mIsViewInited;

     @Override
     public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
         Log.e("test", "tab 1 oncreateview");
         return inflater.inflate(R.layout.container_fragment, null);
     }

     @Override
     public void onActivityCreated(Bundle savedInstanceState) {
         super.onActivityCreated(savedInstanceState);
         Log.e("test", "tab 1 container on activity created");
         
         if (!mIsViewInited) {
             mIsViewInited = true;
             initView();
         }
     }

     private void initView() {
         
         RecipeListViewFragment recipeListViewFragment = new RecipeListViewFragment();
         
         Bundle arguments = this.getArguments();
         Log.d("initView", "Recipes is null is: " + (getArguments().getParcelable("recipes") == null));
         Log.d("initView", "containsKey recipes is null is: " + getArguments().containsKey("recipes"));
         Log.d("initView", "arguments are: " + arguments.toString());
         
         recipeListViewFragment.setArguments(arguments);
         
         replaceFragment(recipeListViewFragment, false);
     }
}
