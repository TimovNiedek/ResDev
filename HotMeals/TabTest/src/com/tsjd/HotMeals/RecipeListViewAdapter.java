package com.tsjd.HotMeals;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tabtest.R;

/**
 * 
 * @author Sander van Dam
 * @author Daniel Roeven
 * @author Timo van Niedek
 * @author Jaco Schalij
 * @version 0.5
 *
 */

public class RecipeListViewAdapter extends ArrayAdapter<Recipe> {
	private final Context context;
	private Recipe[] values;
	//private String path;
	int layoutResourceID;

	/**
	 * A simple constructor which gets the two parameters and assigns them to local variables
	 * @param context the Context we're working in
	 * @param recipes the array with recipes we use to make the list
	 */
	public RecipeListViewAdapter(Context context, Recipe[] recipes) {
		super(context, R.layout.recipe_listview_item, recipes);
		this.context = context;
		this.values = recipes;

	}
	
	/**
	 * First a RowView is initiated and then it is filled with a TextView and an ImageView
	 * the TextViews and ImageViews are filled with an array
	 * @return rowView
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.recipe_listview_item, parent,
				false);
		TextView textView = (TextView) rowView.findViewById(R.id.label);
		ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);

		String uri = "@drawable/" + values[position].getPath();

		try {
			int imageResource = context.getResources().getIdentifier(uri, null,
					context.getPackageName());
			Drawable res = context.getResources().getDrawable(imageResource);
			imageView.setImageDrawable(res);
		} catch (Exception e) {
			int imageResource = context.getResources().getIdentifier(
					"@drawable/non_existing_photo", null,
					context.getPackageName());
			Drawable res = context.getResources().getDrawable(imageResource);
			imageView.setImageDrawable(res);
		}

		textView.setText(values[position].getName());

		TextView secondLine = (TextView) rowView.findViewById(R.id.secondLine);
		secondLine.setText(values[position].getTime() + " min | €"
				+ Recipe.doubleToCurrency(values[position].getPrice()));

		return rowView;
	}
}
