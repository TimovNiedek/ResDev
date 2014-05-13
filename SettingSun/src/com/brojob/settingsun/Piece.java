package com.brojob.settingsun;

import android.util.Log;

public class Piece {
	protected BoundingBox box;
	PieceTypes ptype;

	public Piece(int x, int y, PieceTypes ptype) {
		this.ptype = ptype;
		box = new BoundingBox(x, y, this.getWidth(), this.getHeight());
		Log.d(this.toString(), "Constructor of " + ptype + " at x: " + x + " y: " + y);
	}

	public int getWidth() {
		switch (ptype) {
		case SUN:
			return 1;
		case SMALL:
			return 0;
		case TALL:
			return 0;
		case FAT:
			return 1;
		default:
			return 0;
		}
	}

	public int getHeight() {
		switch (ptype) {
		case SUN:
			return 1;
		case SMALL:
			return 0;
		case TALL:
			return 1;
		case FAT:
			return 0;
		default:
			return 0;
		}
	}

	public int getX() {
		return box.getUpperLeftX();
	}

	public int getY() {
		return box.getUpperLeftY();
	}

	public boolean collides(BoundingBox box) {
		return this.box.collides(box);
	}

	public void move(Directions direction) {
		switch (direction) {
		case NORTH:
			box = new BoundingBox(this.getX(), this.getY() - 1,
					this.getWidth(), this.getHeight());
			break;
		case EAST:
			box = new BoundingBox(this.getX() + 1, this.getY(),
					this.getWidth(), this.getHeight());
			break;
		case SOUTH:
			box = new BoundingBox(this.getX(), this.getY() + 1,
					this.getWidth(), this.getHeight());
			break;
		case WEST:
			box = new BoundingBox(this.getX() - 1, this.getY(),
					this.getWidth(), this.getHeight());
			break;
		}
	}

	public PieceTypes getType() {
		return ptype;
	}

}
