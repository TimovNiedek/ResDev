package com.tsjd.HotMeals;

import com.example.tabtest.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class RecipeContainerFragment extends BaseTabFragment 
{
	 private boolean mIsViewInited;

	 /**
	  * Android-standard oncreate (inflate fragment)
	  */
     @Override
     public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
         return inflater.inflate(R.layout.container_fragment, null);
     }

     /**
      * Initialize view if not yet initialized
      */
     @Override
     public void onActivityCreated(Bundle savedInstanceState) {
         super.onActivityCreated(savedInstanceState);
         if (!mIsViewInited) {
             mIsViewInited = true;
             initView();
         }
     }

     /**
      * Initialize view with arguments from bundle
      */
     private void initView() {
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
