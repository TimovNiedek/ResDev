package com.tsjd.HotMeals;

import com.example.tabtest.R;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 
 * @author Daniel Roeven
 * @author Sander van Dam
 * @author Timo van Niedek
 * @author Jaco Schalij
 * @version 0.5
 *
 */

public class RecipeListViewContainerFragment extends BaseTabFragment 
{
	 private boolean mIsViewInited;

	 /**
	  * Android-standard oncreate (inflate container fragment)
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
      * Initialize view using arguments from bundle
      */
     private void initView() {
         RecipeListViewFragment recipeListViewFragment = new RecipeListViewFragment();
         Bundle arguments = this.getArguments();
         recipeListViewFragment.setArguments(arguments);
         replaceFragment(recipeListViewFragment, false);
     }
}
