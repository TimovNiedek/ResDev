package com.example.thesettingsun;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.View;

public class Block extends View implements SurfaceHolder.Callback
{
	public static int SIZE = 50;
	
	private Paint paint = new Paint();
	private int color = Color.BLACK;
	
	public Block(Context context, AttributeSet attrs) {
		super(context);
	}
	
	public void setColor(int colour)
	{
		//this.color = colour;
		//paint.setColor(color);
	}
	
	@Override
	public void onDraw(Canvas canvas)
	{
		paint.setColor(color);
		canvas.drawRect(0, 0, SIZE, SIZE, paint);
	}

	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void surfaceCreated(SurfaceHolder arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
		// TODO Auto-generated method stub
		
	}

}
