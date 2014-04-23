package com.example.thesettingsun;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.View;

public class Block extends View implements SurfaceHolder.Callback
{
	Paint paint = new Paint();

	public Block(Context context) {
		super(context);
		
		
	}
	
	@Override
	public void onDraw(Canvas canvas)
	{
		paint.setColor(Color.GREEN);
		canvas.drawRect(0, 0, 30, 30, paint);
		
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
