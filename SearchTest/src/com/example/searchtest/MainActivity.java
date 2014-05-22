package com.example.searchtest;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.MultiAutoCompleteTextView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.SeekBar.OnSeekBarChangeListener;;

public class MainActivity extends ActionBarActivity {
	
	
	
	private static final String[] INGREDIENTS = new String[]{
		"Spaghetti", "Rijst", "Komkommer", "Risotto"
	};
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
        
        
    }

    @Override
    protected void onResume()
    {
    	super.onResume();
    	
    	/*ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, INGREDIENTS);
        
        MultiAutoCompleteTextView ingredients = (MultiAutoCompleteTextView) findViewById(R.id.multiAutoCompleteTextView1);
        Log.d("onCreate", ingredients.getHint().toString());
        ingredients.setAdapter(adapter);
        ingredients.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
    	
    	private MultiAutoCompleteTextView ingredients;
    	private SeekBar budgetBar;
    	private TextView budgetText;
    	private SeekBar timeBar;
    	private TextView timeText;
    	private Button searchButton;
    	private TextView searchText;
    	
        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(rootView.getContext(), android.R.layout.simple_list_item_1, INGREDIENTS);
            
            ingredients = (MultiAutoCompleteTextView) rootView.findViewById(R.id.multiAutoCompleteTextView1);
            ingredients.setAdapter(adapter);
            ingredients.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
            
            budgetBar = (SeekBar) rootView.findViewById(R.id.seekBar1);
            budgetText = (TextView) rootView.findViewById(R.id.budgetTextView);
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
					budgetText.setText("Û" + (double)(progress)/10 + "0");
				}
			});
            
            timeBar = (SeekBar) rootView.findViewById(R.id.timeSeekBar);
            timeText = (TextView) rootView.findViewById(R.id.timeTextView);
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
            
            searchText = (TextView) rootView.findViewById(R.id.textView4);
            
            searchButton = (Button) rootView.findViewById(R.id.searchButton);
            searchButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					ingredients.performValidation();
					
					String ingredientsText = ingredients.getText().toString();
					if (ingredientsText.equals("")) ingredientsText = "niet ingevuld";
					String budget = budgetText.getText().toString();
					if (budget.equals("Û0.00")) budget = "niet ingevuld";
					String time = timeText.getText().toString();
					if (time.equals("0h 0m")) time = "niet ingevuld";
					
					searchText.setText(	"Ingredients=" + ingredientsText + 
										"\nBudget=" + budget + 
										"\nTime=" + time);
					
				}
			});
            
            return rootView;
        }
    }

}
