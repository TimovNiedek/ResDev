package com.tsjd.HotMeals;

import java.io.IOException;
import java.util.ArrayList;

import android.database.SQLException;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TextView;

import com.example.tabtest.R;

public class MainActivity extends FragmentActivity {
   
	public static String PACKAGE_NAME;
	private FragmentTabHost mTabHost;
	private ArrayList<Recipe> favorites = new ArrayList<Recipe>();
	private Bundle favoritesBundle = new Bundle();
	public boolean updateFavorites = false;
    private DataBaseHelper myDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        // Set the static package name
        PACKAGE_NAME = getApplicationContext().getPackageName();
        
        createDatabase();
        
        //Create tabhost 
        mTabHost = (FragmentTabHost) findViewById(R.id.tabhost);
        
        //Setup using standard layout
        mTabHost.setup(this, getSupportFragmentManager(), R.id.tabs);
        
        // Initialize FavoritesHelper
        final FavoritesHelper favoritesHelper = new FavoritesHelper(myDbHelper);
        favorites = favoritesHelper.getFavorites();
        favoritesBundle.putParcelableArrayList("recipes", favorites);
        
        //Add three tabs
        mTabHost.addTab(
                mTabHost.newTabSpec("Home").setIndicator("Home", getResources().getDrawable(R.drawable.custom_home)),
                HomeContainerFragment.class, null);
        mTabHost.addTab(
                mTabHost.newTabSpec("Favorites").setIndicator("My Recipes", getResources().getDrawable(R.drawable.ic_action_favorite)),
                RecipeListViewContainerFragment.class, favoritesBundle);
        mTabHost.addTab(
                mTabHost.newTabSpec("Search").setIndicator("Search", getResources().getDrawable(R.drawable.ic_action_search)),
                SearchContainerFragment.class, null);
        
        //Set update listener, call updateTab on each update
        mTabHost.setOnTabChangedListener(new OnTabChangeListener(){    
            public void onTabChanged(String tabID) {    
            	// Update favorites
            	if(updateFavorites)
            	{
            		favorites = favoritesHelper.getFavorites();
            		favoritesBundle.clear();
            		favoritesBundle.putParcelableArrayList("recipes", favorites);
            	}
            	mTabHost.clearFocus();
            	updateTabs(mTabHost); //Update to set active tab color
            }   
        });
        
        //Update once to set tab colors and set text color
        updateTabs(mTabHost);
        for (int i = 0; i < mTabHost.getTabWidget().getChildCount(); i++) {
        	TextView title = (TextView) mTabHost.getTabWidget().getChildAt(i).findViewById(android.R.id.title);
        	title.setTextColor(Color.rgb(40, 45, 06));
        }
        mTabHost.getTabWidget().setStripEnabled(true);
    }
    
    //Override standard back button functionality
    @Override
    public void onBackPressed() {
        boolean isPopFragment = false;
        String currentTabTag = mTabHost.getCurrentTabTag();
        if (currentTabTag.equals("Home")) {
            isPopFragment = ((BaseTabFragment)getSupportFragmentManager().findFragmentByTag("Home")).popFragment();
        } else if (currentTabTag.equals("My Recipes")) {
            isPopFragment = ((BaseTabFragment)getSupportFragmentManager().findFragmentByTag("My Recipes")).popFragment();
        } else if (currentTabTag.equals("Search")) {
            isPopFragment = ((BaseTabFragment)getSupportFragmentManager().findFragmentByTag("Search")).popFragment();
        } 
        if (!isPopFragment) {
            finish();
        }
    }
    
    // Updates background colors of tab views using gradient PNG's.
    @SuppressWarnings("deprecation")
	private void updateTabs(FragmentTabHost mTabHost) {
    	for (int i = 0; i < mTabHost.getTabWidget().getChildCount(); i++) {
            /*Well aware that this method is deprecated. Still use it for the sole reason of that our test device is API level 10.
        	The non-deprecated replacement method requires API level 16.*/
        	mTabHost.getTabWidget().getChildAt(i).setBackgroundDrawable(getResources().getDrawable(R.drawable.tabgradient));
        }
    	mTabHost.getTabWidget().getChildAt(mTabHost.getCurrentTab()).setBackgroundDrawable(getResources().getDrawable(R.drawable.tabgradientactive));
    	mTabHost.getTabWidget().setStripEnabled(false);
    }
    
    //Return tab bar height so as to set content areas at correct height (so that no overlap occurs)
    public int getTabBarHeight() {
    	return mTabHost.getTabWidget().getChildAt(1).getLayoutParams().height;
    }
    
    //Create database if none exists
    private void createDatabase()
    {
    	myDbHelper = new DataBaseHelper(this);
        try {
        	myDbHelper.createDataBase();
	 	} catch (IOException ioe) {
 			throw new Error("Unable to create database");
	 	}
	 	
        try {
	 		myDbHelper.openDataBase();
	 	}catch(SQLException sqle){
	 		throw sqle;
	 	}
    }
    
    //Return databasehelper to objects that need to access database
    public DataBaseHelper getDatabaseHelper()
    {
    	return myDbHelper;
    }
}