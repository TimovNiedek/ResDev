package com.brojob.settingsun;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path.Direction;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameView extends SurfaceView implements
        SurfaceHolder.Callback {

    private GameThread gameThread;
    private Piece clickedPiece;
    private int downX, downY;
    private Context context;

    public GameView(Context context) {
        super(context);
        getHolder().addCallback(this);
        this.gameThread = new GameThread(getHolder(), context);
        setFocusable(true);
    }

    public GameThread getThread()
    {
        return this.gameThread;
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    /**
     * Starts thread if the surface has been created.
     * @param holder
     */
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        this.gameThread.setRunning(true);
        this.gameThread.start();
    }

    /**
     * Stops thread if the surface is destroyed.
     * @param holder
     */
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        while (retry) {
            try {
                gameThread.join();
                retry = false;
            } catch (InterruptedException e)
            {
            }
        }
    }

    /**
     * This function registers the piece which is selected and the direction it is tried to move to.
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        float x = event.getX();  // or getRawX();
        float y = event.getY();
        switch(action){
            case MotionEvent.ACTION_DOWN:
                clickedPiece = this.gameThread.isPieceClicked((int) x, (int) y);
                this.downX = (int) x;
                this.downY = (int) y;
                if(this.gameThread.isResetClicked((int)x, (int)y))
                {
                    this.gameThread.resetGame();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                if(clickedPiece != null) {
                    float xDiff = (downX > x) ? (downX - x) : (x - downX);
                    float yDiff = (downY > y) ? (downY - y) : (y - downY);

                    if (xDiff > yDiff) {
                        if (downX > x) {
                            this.gameThread.movePiece(clickedPiece, Directions.WEST);
                        } else if (downX < x) {
                            this.gameThread.movePiece(clickedPiece, Directions.EAST);
                        }
                    } else {
                        if (downY > y) {
                            this.gameThread.movePiece(clickedPiece, Directions.NORTH);
                        } else if (downY < y) {
                            this.gameThread.movePiece(clickedPiece, Directions.SOUTH);
                        }
                    }
                }
                clickedPiece = null;
                break;
        }
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
    }
}