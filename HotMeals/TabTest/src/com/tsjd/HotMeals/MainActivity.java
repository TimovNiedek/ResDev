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
import com.tsjd.HotMeals.Recipe.Ingredient;
import com.tsjd.HotMeals.RecipeView;

public class MainActivity extends FragmentActivity {
    private FragmentTabHost mTabHost;
    
    DataBaseHelper myDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        
        setContentView(R.layout.activity_main);
        createDatabase();
        
        //Create tabhost 
        mTabHost = (FragmentTabHost) findViewById(R.id.tabhost);
        
        //Setup using standard layout
        mTabHost.setup(this, getSupportFragmentManager(), R.id.tabs);
        
        //Add three tabs
        
        mTabHost.addTab(
                mTabHost.newTabSpec("tab1").setIndicator("Home", getResources().getDrawable(R.drawable.custom_home)),
                RecipeContainerFragment.class, null);
        mTabHost.addTab(
                mTabHost.newTabSpec("tab2").setIndicator("My Recipes", getResources().getDrawable(R.drawable.ic_action_favorite)),
                HomeContainerFragment.class, null);
        mTabHost.addTab(
                mTabHost.newTabSpec("tab3").setIndicator("Search", getResources().getDrawable(R.drawable.ic_action_search)),
                SearchContainerFragment.class, null);
        
        //Set update listener, call updateTab on each update
        mTabHost.setOnTabChangedListener(new OnTabChangeListener(){    
            public void onTabChanged(String tabID) {    
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
    
    /*@Override
    public void onBackPressed() {
        boolean isPopFragment = false;
        String currentTabTag = mTabHost.getCurrentTabTag();
        if (currentTabTag.equals("Home")) {
            isPopFragment = ((BaseContainerFragment)getSupportFragmentManager().findFragmentByTag("Home")).popFragment();
        } else if (currentTabTag.equals("My Recipes")) {
            isPopFragment = ((BaseContainerFragment)getSupportFragmentManager().findFragmentByTag("My Recipes")).popFragment();
        } else if (currentTabTag.equals("Search")) {
            isPopFragment = ((BaseContainerFragment)getSupportFragmentManager().findFragmentByTag("Search")).popFragment();
        } 
        if (!isPopFragment) {
            finish();
        }
    }*/
    
    /*
     * Updates background colors of tab views using gradient PNG's.
     */
    private void updateTabs(FragmentTabHost mTabHost) {
    	for (int i = 0; i < mTabHost.getTabWidget().getChildCount(); i++) {
            /*Well aware that this method is deprecated. Still use it for the sole reason of that our test device is API level 10.
        	The non-deprecated replacement method requires API level 16.*/
        	mTabHost.getTabWidget().getChildAt(i).setBackgroundDrawable(getResources().getDrawable(R.drawable.tabgradient));
        }
    	mTabHost.getTabWidget().getChildAt(mTabHost.getCurrentTab()).setBackgroundDrawable(getResources().getDrawable(R.drawable.tabgradientactive));
    	mTabHost.getTabWidget().setStripEnabled(false);
    }
        
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
    String naam = "tosti";
    String bereiding = "gewoon tosti maken jonge";
    String path = "ic_action_search";
    int tijd = 10;
    int idee = 1;
    double prijs = 0.3;
    boolean favoriet = false;
    
    ArrayList<Ingredient> ingredientenlijst = new ArrayList<Ingredient>();
    Recipe recipe = new Recipe(naam, ingredientenlijst, bereiding, tijd, prijs, favoriet, idee, path);
    public Recipe getRecipe(){
    	return recipe;
    }
    
    public DataBaseHelper getDatabaseHelper()
    {
    	return myDbHelper;
    }
}