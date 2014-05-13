package com.brojob.settingsun;

public class BoundingBox {
	protected int width;
	protected int height;
	protected int upperLeftX;
	protected int upperLeftY;

	public BoundingBox(int upperLeftX, int upperLeftY, int width, int height) {
		this.width = width;
		this.height = height;
		this.upperLeftX = upperLeftX;
		this.upperLeftY = upperLeftY;
	}

	public boolean collides(BoundingBox second) {
		if (upperLeftX >= second.upperLeftX
				&& upperLeftX <= second.upperLeftX + second.width
				&& upperLeftY >= second.upperLeftY
				&& upperLeftY <= second.upperLeftY + second.height) {

			return true;
		} else if (upperLeftX + width >= second.upperLeftX
				&& upperLeftX + width <= second.upperLeftX + second.width 
				&& upperLeftY >= second.upperLeftY
				&& upperLeftY <= second.upperLeftY + second.height) {
			return true;
		} else if (upperLeftX >= second.upperLeftX
				&& upperLeftX <= second.upperLeftX + second.width
				&& upperLeftY + height >= second.upperLeftY
				&& upperLeftY + height <= second.upperLeftY
						+ second.height) {
			return true;
		} else if (upperLeftX + width >= second.upperLeftX
				&& upperLeftX + width <= second.upperLeftX + second.width
				&& upperLeftY + height >= second.upperLeftY
				&& upperLeftY + height <= second.upperLeftY
						+ second.height) {
			return true;
		} else {
			return false;
		}
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int getUpperLeftX() {
		return upperLeftX;
	}

	public int getUpperLeftY() {
		return upperLeftY;
	}
}
