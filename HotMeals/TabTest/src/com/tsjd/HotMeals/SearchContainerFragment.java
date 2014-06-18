package com.tsjd.HotMeals;

/**
 * 
 * @author Daniel Roeven
 * @author Sander van Dam
 * @author Timo van Niedek
 * @author Jaco Schalij
 * @version 0.5
 *
 */

import com.example.tabtest.R;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class SearchContainerFragment extends BaseTabFragment 
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

     /**
      * Replace Fragment
      */
     private void initView() {
         replaceFragment(new SearchFragment(), false);
     }
}
