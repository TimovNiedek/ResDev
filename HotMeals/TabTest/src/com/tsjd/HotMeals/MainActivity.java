package com.tsjd.HotMeals;

import java.io.IOException;

import android.database.SQLException;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.widget.TabHost.OnTabChangeListener;

import com.example.tabtest.R;

public class MainActivity extends FragmentActivity {
    private FragmentTabHost mTabHost;
    
    DataBaseHelper myDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        
        setContentView(R.layout.activity_main);
        createDatabase();
        
        mTabHost = (FragmentTabHost) findViewById(R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.tabFrameLayout);
                
        mTabHost.addTab(
                mTabHost.newTabSpec("tab1").setIndicator("Home", getResources().getDrawable(R.drawable.custom_home)),
                FragmentTab.class, null);
        mTabHost.addTab(
                mTabHost.newTabSpec("tab2").setIndicator("My Recipes", getResources().getDrawable(R.drawable.ic_action_favorite)),
                FragmentTab.class, null);
        mTabHost.addTab(
                mTabHost.newTabSpec("tab3").setIndicator("Search", getResources().getDrawable(R.drawable.ic_action_search)),
                SearchFragment.class, null);
        mTabHost.setOnTabChangedListener(new OnTabChangeListener(){    
            public void onTabChanged(String tabID) {    
            	mTabHost.clearFocus(); 
            }   
        });
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
    
    public DataBaseHelper getDatabaseHelper()
    {
    	return myDbHelper;
    }
}