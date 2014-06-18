package com.tsjd.HotMeals;

import com.example.tabtest.R;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * 
 * @author Daniel Roeven
 * @author Sander van Dam
 * @author Timo van Niedek
 * @author Jaco Schalij
 * @version 0.5
 *
 */

public class FragmentTab extends BaseTabFragment {

	/**
	 * Android-standard oncreate
	 */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * initialize view, mostly
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_layout, container, false);
        TextView tv = (TextView) v.findViewById(R.id.text);
        tv.setText(this.getTag() + " Content");
        return v;
    }
}