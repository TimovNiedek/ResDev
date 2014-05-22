package com.example.searchtest;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

public class SeekBarHint extends SeekBar {
	
	private TextView hintText;

	public SeekBarHint(Context context) {
		super(context);
	}

	public SeekBarHint(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public SeekBarHint(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}
	
	@Override
	public void onFinishInflate()
	{
		super.onFinishInflate();
		
		hintText = new TextView(getContext());
		hintText.setText("Test");
	}
	
	
	@Override
	protected void onDraw(Canvas c) {
	    //int thumb_x = ( this.getProgress()/this.getMax() ) * this.getWidth();
	    //int middle = this.getHeight()/2;
	    super.onDraw(c);
	    RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
	    lp.setMargins(this.getThumbOffset(), 50, 0, 0);
	    hintText.setLayoutParams(lp);
	}

}
