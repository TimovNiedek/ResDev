package com.tsjd.HotMeals;

import java.io.IOException;
import java.util.ArrayList;

import android.database.SQLException;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.widget.TabHost.OnTabChangeListener;

import com.example.tabtest.R;
import com.tsjd.HotMeals.Recipe.Ingredient;

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
        mTabHost.setup(this, getSupportFragmentManager(), R.id.tabFrameLayout);
        
        //Add three tabs
        mTabHost.addTab(
                mTabHost.newTabSpec("tab1").setIndicator("Home", getResources().getDrawable(R.drawable.custom_home)),
                HomeTab.class, null);
        mTabHost.addTab(
                mTabHost.newTabSpec("tab2").setIndicator("My Recipes", getResources().getDrawable(R.drawable.ic_action_favorite)),
                FragmentTab.class, null);
        mTabHost.addTab(
                mTabHost.newTabSpec("tab3").setIndicator("Search", getResources().getDrawable(R.drawable.ic_action_search)),
                SearchFragment.class, null);
        
        //Set update listener, call updateTab on each update
        mTabHost.setOnTabChangedListener(new OnTabChangeListener(){    
            public void onTabChanged(String tabID) {    
            	mTabHost.clearFocus();
            	updateTabs(mTabHost); //Update to set active tab color
            }   
        });
        
        //Update once to set tab colors
        updateTabs(mTabHost);
    }
    
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
    String path = "whatever";
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