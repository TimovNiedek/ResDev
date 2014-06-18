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

public class HomeContainerFragment extends BaseTabFragment 
{
	 private boolean mIsViewInited;

	 /**
	  * Initialize view
	  */
     @Override
     public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
         return inflater.inflate(R.layout.container_fragment, null);
     }

     /**
      * if view is not initialized, initialize
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
      * Replace tab
      */
     private void initView() {
         replaceFragment(new HomeTab(), false);
     }
}
