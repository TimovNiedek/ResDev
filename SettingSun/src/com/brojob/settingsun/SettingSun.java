package com.brojob.settingsun;

import java.util.ArrayList;

import android.util.Log;

public class SettingSun {

	protected int xDim, yDim, moveCounter;
	protected ArrayList<Piece> pieces;
	protected Piece endPiece;
	protected int endX, endY;

	public SettingSun(int xDim, int yDim) {
		this.xDim = xDim;
		this.yDim = yDim;
		this.moveCounter = 0;
		pieces = new ArrayList<Piece>();
	}

	protected boolean spaceFree(int x, int y, int width, int height) {
		BoundingBox bBox = new BoundingBox(x, y, width, height);
		for (Piece piece : pieces) {
			if (piece.collides(bBox)) {
				return false;
			}
		}

		return true;
	}

	public void addPiece(Piece piece) {

		//if (this.spaceFree(piece.getX(), piece.getY(), piece.getWidth(),
		//		piece.getHeight())) {
			//if (!pieces.contains(piece)) {
				pieces.add(piece);
				Log.d("addPiece", piece.getType().toString());
			//}
	//	}

	}

	public void end(Piece piece, int endX, int endY) {
		if (pieces.contains(piece)) {
			endPiece = piece;
			this.endX = endX;
			this.endY = endY;
		}
	}

	public boolean ableToMove(Piece piece, Directions direction) {

		int newX, newY;

		switch (direction) {
		case NORTH:
			newX = piece.getX();
			newY = piece.getY() - 1;
			break;
		case EAST:
			newX = piece.getX() + 1;
			newY = piece.getY();
			break;
		case SOUTH:
			newX = piece.getX();
			newY = piece.getY() + 1;
			break;
		case WEST:
			newX = piece.getX() - 1;
			newY = piece.getY();
			break;
		default:
			newX = piece.getX();
			newY = piece.getY();
			break;

		}

		if (newX < 0 || newY < 0 || newX + piece.getWidth() > xDim
				|| newY + piece.getHeight() > yDim) {
			return false;
		}

		BoundingBox bBox = new BoundingBox(newX, newY, piece.getWidth(),
				piece.getHeight());
		for (Piece piecey : pieces) {
			if (piecey != piece) {
				if (piecey.collides(bBox)) {
					return false;
				}
			}
		}

		return true;

	}

	public void MoveIt(Piece piece, Directions oneDirection) {
		if (ableToMove(piece, oneDirection)) {
			moveCounter++;
			piece.move(oneDirection);
		}
	}

	public Piece getPieceAtPos(int posX, int posY) {
		BoundingBox bBox = new BoundingBox(posX, posY, 1, 1);
		for (Piece piece : pieces) {
			if (piece.collides(bBox)) {
				return piece;
			}
		}
		return null;
	}
	
	public boolean gameEnd()
	{
		return (endPiece.getX() == endX && endPiece.getY() == endY);
	}
	
	public int AmountOfMoves() 
	{
		return moveCounter;
	}
	
	public ArrayList<Piece> getPieces()
	{
		return pieces;
	}
	
	public void reset()
	{
		pieces = new ArrayList<Piece>();
		moveCounter = 0;
	}

}
