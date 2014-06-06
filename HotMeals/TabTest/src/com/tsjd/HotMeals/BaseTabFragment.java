package com.tsjd.HotMeals;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import com.example.tabtest.R;

public class BaseTabFragment extends Fragment 
{
	public void replaceFragment(Fragment fragment, boolean addToBackStack) {
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        if (addToBackStack) {
            transaction.addToBackStack(null);
        }
        
        try{
        	transaction.replace(R.id.container_framelayout, fragment);
            transaction.commit();
	        getChildFragmentManager().executePendingTransactions();
        } catch (Exception e) {
        	throw new Error(e);
        }
    }
	
	public void addFragmentWithTransition(Fragment fragment, boolean addToBackStack) {
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        if (addToBackStack) {
            transaction.addToBackStack(null);
        }
        
        try{
        	
        	transaction.replace(R.id.container_framelayout, fragment);
        	transaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right).show(fragment).commit();
        	
        	
        	//transaction.replace(R.id.container_framelayout, fragment);
            //transaction.commit();
        	
	        getChildFragmentManager().executePendingTransactions();
        } catch (Exception e) {
        	throw new Error(e);
        }
    }

    public boolean popFragment() {
        Log.e("test", "pop fragment: " + getChildFragmentManager().getBackStackEntryCount());
        boolean isPop = false;
        if (getChildFragmentManager().getBackStackEntryCount() > 0) {
            isPop = true;
            getChildFragmentManager().popBackStack();
        }
        return isPop;
    }
}
