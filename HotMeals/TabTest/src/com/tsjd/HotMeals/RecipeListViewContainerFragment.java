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
        
         return inflater.inflate(R.layout.container_fragment, null);
     }

     @Override
     public void onActivityCreated(Bundle savedInstanceState) {
         super.onActivityCreated(savedInstanceState);
         
         
         if (!mIsViewInited) {
             mIsViewInited = true;
             initView();
         }
     }

     private void initView() {
         
         RecipeListViewFragment recipeListViewFragment = new RecipeListViewFragment();
         
         Bundle arguments = this.getArguments();
         
         recipeListViewFragment.setArguments(arguments);
         
         replaceFragment(recipeListViewFragment, false);
     }
}