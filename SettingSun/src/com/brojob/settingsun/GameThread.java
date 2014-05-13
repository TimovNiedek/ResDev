package com.brojob.settingsun;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.SurfaceHolder;

@SuppressLint("WrongCall")
public class GameThread extends Thread {
	public final int BOARDWIDTH = 3;
	public final int BOARDHEIGHT = 4;
	public int multiplier = 3;

	public int endX = 1, endY = 0;

	private int fieldHeight;
	private int fieldWidth;
	private int blockWidth;
	private int resetWidth, resetHeight;
	private int paddingHeight;
	private int paddingWidth;

	private DisplayMetrics metrics;

	private boolean running;
	private SurfaceHolder surfaceHolder;
	private Context context;
	private Bitmap sunp, tallp, fatp, smallp, newgame, background;
	private Resources resources;
	
	private Paint paint;

	public SettingSun getSettingSun() {
		return settingSun;
	}

	private SettingSun settingSun;

	public GameThread(SurfaceHolder surfaceHolder, Context context) {
		this.surfaceHolder = surfaceHolder;
		this.context = context;
		resources = context.getResources();

		paint = new Paint();
		
		smallp = BitmapFactory.decodeResource(resources, R.drawable.small);

		metrics = context.getResources().getDisplayMetrics();

		paddingWidth = metrics.widthPixels / 5;
		paddingHeight = metrics.heightPixels / 30;
		fieldWidth = metrics.widthPixels - (paddingWidth * 2);
		blockWidth = fieldWidth / (BOARDWIDTH + 1);
		fieldHeight = blockWidth * (BOARDHEIGHT + 1);
		multiplier = blockWidth / smallp.getWidth();

		settingSun = new SettingSun(BOARDWIDTH, BOARDHEIGHT);

		sunp = BitmapFactory.decodeResource(resources, R.drawable.sun);
		sunp = Bitmap.createScaledBitmap(sunp, blockWidth * 2, blockWidth * 2,
				true);
		smallp = Bitmap.createScaledBitmap(smallp, blockWidth * 1, blockWidth * 1, true);
		tallp = BitmapFactory.decodeResource(resources, R.drawable.tall);
		tallp = Bitmap.createScaledBitmap(tallp, blockWidth * 1, blockWidth * 2,
				true);
		fatp = BitmapFactory.decodeResource(resources, R.drawable.fat);
		fatp = Bitmap.createScaledBitmap(fatp, blockWidth * 2, blockWidth * 1,
				true);
		newgame = BitmapFactory.decodeResource(resources, R.drawable.newgame);
		int newWidth = blockWidth * 1;
		int newHeight = (int) (blockWidth * 0.5);
		
		newgame = Bitmap.createScaledBitmap(newgame, newWidth, newHeight, true);
		background = BitmapFactory.decodeResource(resources,
				R.drawable.background);
		background = Bitmap.createScaledBitmap(background, metrics.widthPixels,
				metrics.heightPixels, true);

	}

	public void setRunning(boolean running) {
		this.running = running;
	}

	@Override
	public void run() {

		this.generateField();
		Canvas c;
		while (running) {
			c = null;

			try {
				c = surfaceHolder.lockCanvas(null);
				synchronized (surfaceHolder) {
					onDraw(c);
				}
			} finally {
				if (c != null) {
					surfaceHolder.unlockCanvasAndPost(c);
				}
			}

			if (this.settingSun.gameEnd()) {
				this.setRunning(false);
			}

		}

	}

	public void onDraw(Canvas canvas) {
		canvas.drawBitmap(background, 0, 0, null);
		canvas.drawBitmap(newgame, paddingWidth + (float)(3.0 * blockWidth), (float)(paddingHeight + 5.5 * blockWidth - newgame.getHeight()), null);

		paint.setColor(Color.BLACK);
		paint.setTextSize(30);
		canvas.drawText("Moves: " + this.settingSun.AmountOfMoves(), paddingWidth, (float)(paddingHeight + 5.5 * blockWidth), paint);

		for (Piece piece : settingSun.getPieces()) {
			int x = piece.getX();
			int y = piece.getY();
			
			switch (piece.getType()) {
			case SMALL:
				canvas.drawBitmap(smallp, paddingWidth + x * blockWidth,
						paddingHeight + y * blockWidth, null);
				break;
			case TALL:
				canvas.drawBitmap(tallp, paddingWidth + x * blockWidth,
						paddingHeight + y * blockWidth, null);
				break;
			case FAT:
				canvas.drawBitmap(fatp, paddingWidth + x * blockWidth,
						paddingHeight + y * blockWidth, null);
				break;
			case SUN:
				canvas.drawBitmap(sunp, paddingWidth + x * blockWidth,
						paddingHeight + y * blockWidth, null);
				break;

			}
		}
	}

	public boolean addPiece(int x, int y, PieceTypes ptype) {
		Piece piece = new Piece(x, y, ptype);
		settingSun.addPiece(piece);
		if (ptype == PieceTypes.SUN) {
			settingSun.end(piece, endX, endY);
		}
		return true;
	}

	private void generateField() {
		settingSun.reset();
		addPiece(1, 0, PieceTypes.SMALL);
		addPiece(1, 1, PieceTypes.SMALL);
		addPiece(2, 0, PieceTypes.SMALL);
		addPiece(2, 1, PieceTypes.SMALL);

		addPiece(0, 0, PieceTypes.TALL);
		addPiece(3, 0, PieceTypes.TALL);
		addPiece(0, 3, PieceTypes.TALL);
		addPiece(3, 3, PieceTypes.TALL);

		addPiece(1, 2, PieceTypes.FAT);

		addPiece(1, 3, PieceTypes.SUN);
	}

	public Piece isPieceClicked(int x, int y) {
		for (Piece piece : settingSun.getPieces()) {
			int pieceX = paddingWidth + piece.getX() * blockWidth;
			int pieceY = paddingHeight + piece.getY() * blockWidth;

			int height = getHeight(piece);
			int width = getWidth(piece);

			if (x >= pieceX && y >= pieceY && x <= pieceX + width
					&& y <= pieceY + height) {
				return piece;
			}
		}
		return null;
	}

	public boolean isResetClicked(int x, int y) {
		if (x >= paddingWidth + 3 * blockWidth
				&& y >= 6 * blockWidth - (int) (0.3 * blockWidth)
				&& x <= paddingWidth + 3 * blockWidth + resetWidth
				&& y <= 6 * blockWidth - (int) (0.3 * blockWidth) + resetHeight) {
			return true;
		}
		return false;
	}

	public int getWidth(Piece piece) {
		switch (piece.getType()) {
		case TALL:
			return tallp.getWidth();
		case SMALL:
			return smallp.getWidth();
		case FAT:
			return fatp.getWidth();
		case SUN:
			return sunp.getWidth();
		}
		return 0;
	}

	public int getHeight(Piece piece) {
		switch (piece.getType()) {
		case TALL:
			return tallp.getHeight();
		case SMALL:
			return smallp.getHeight();
		case FAT:
			return fatp.getHeight();
		case SUN:
			return sunp.getHeight();
		}
		return 0;
	}

	public boolean movePiece(Piece piece, Directions direction) {
		if (settingSun.ableToMove(piece, direction)) {
			settingSun.MoveIt(piece, direction);
			return true;
		}
		return false;
	}

	public void resetGame() {
		getSettingSun().reset();
		generateField();
	}
}
