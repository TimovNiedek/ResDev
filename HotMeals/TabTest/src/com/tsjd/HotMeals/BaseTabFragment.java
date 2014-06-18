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

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import com.example.tabtest.R;

public class BaseTabFragment extends Fragment implements FragmentManager.OnBackStackChangedListener
{	
	/**
	 * Switch active fragment
	 * @param fragment to go to
	 * @param addToBackStack: whether or not to add new fragment to the backstack
	 */
	public void replaceFragment(Fragment fragment, boolean addToBackStack) {
        
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        if (addToBackStack) {
            transaction.addToBackStack(null);
            Log.d("replaceFragment", "Added to back stack");
        }
        
        transaction.replace(R.id.container_framelayout, fragment);
        transaction.commit();
        getChildFragmentManager().executePendingTransactions();
    }
	
	/**
	 * Switch fragment
	 * @param fragment to go to
	 * @param addToBackStack: whether or not to add new fragment to backstack
	 */
	public void addFragmentWithTransition(Fragment fragment, boolean addToBackStack) {
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        if (addToBackStack) {
            transaction.addToBackStack(null);
        }
        
    	transaction.replace(R.id.container_framelayout, fragment);
    	transaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right).show(fragment).commit();
    	
        getChildFragmentManager().executePendingTransactions();
        
    }

	/**
	 * remove fragment
	 * @return success?
	 */
    public boolean popFragment() {
        boolean isPop = false;
        if (getChildFragmentManager().getBackStackEntryCount() > 0) {
            isPop = true;
            getChildFragmentManager().popBackStack();
        }
        return isPop;
    }

    /**
     * Standard override
     */
	@Override
	public void onBackStackChanged() {
	}
}
