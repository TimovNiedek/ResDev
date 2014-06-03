package com.tsjd.HotMeals;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.MultiAutoCompleteTextView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.example.tabtest.R;

public class SearchFragment extends Fragment 
{
	private MultiAutoCompleteTextView ingredients;
	private SeekBar budgetBar;
	private TextView budgetText;
	private SeekBar timeBar;
	private TextView timeText;
	private Button searchButton;
	//private TextView searchText;
	
	private static final String[] INGREDIENTS = new String[]{
		"Spaghetti", "Rijst", "Komkommer", "Risotto"
	};
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_search, container, false);
        
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(v.getContext(), android.R.layout.simple_list_item_1, INGREDIENTS);
        
        ingredients = (MultiAutoCompleteTextView) v.findViewById(R.id.multiAutoCompleteTextView2);
        ingredients.setAdapter(adapter);
        ingredients.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
        
        budgetBar = (SeekBar) v.findViewById(R.id.seekBar1);
        budgetText = (TextView) v.findViewById(R.id.budgetTextView);
        budgetBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				// TODO Auto-generated method stub
				budgetText.setText("$" + (double)(progress)/10 + "0");
			}
		});
        
        timeBar = (SeekBar) v.findViewById(R.id.timeSeekBar);
        timeText = (TextView) v.findViewById(R.id.timeTextView);
        timeBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				// TODO Auto-generated method stub
				int hours = progress / 60;
				int mins = progress % 60;
				timeText.setText(hours + "h " + mins + "m");
			}
		});
        
        //searchText = (TextView) v.findViewById(R.id.textView4);
        
        searchButton = (Button) v.findViewById(R.id.searchButton);
        searchButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				ingredients.performValidation();
				
				String ingredientsText = ingredients.getText().toString();
				if (ingredientsText.equals("")) ingredientsText = "niet ingevuld";
				String budget = budgetText.getText().toString();
				if (budget.equals("$0.00")) budget = "niet ingevuld";
				String time = timeText.getText().toString();
				if (time.equals("0h 0m")) time = "niet ingevuld";
				
				/*searchText.setText(	"Ingredients=" + ingredientsText + 
									"\nBudget=" + budget + 
									"\nTime=" + time);*/
				
			}
		});
        
        return v;
    }
}
