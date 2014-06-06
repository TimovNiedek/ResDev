package com.tsjd.HotMeals;

import com.example.tabtest.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class RecipeContainerFragment extends BaseTabFragment 
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
         Log.e("test", "tab 1 init view");
         Fragment recipeView = new RecipeView();
         Bundle arguments = this.getArguments();
         recipeView.setArguments(arguments);
         try {
			replaceFragment(recipeView, false);
		} catch (Exception e) {
			throw e;
		}
     }
}
