package com.tsjd.HotMeals;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Ingredient implements Parcelable, Serializable{
	public float quantity;
	public String unit;
	public String name;
	
	/**
	 * Manual constructor
	 * @param quantity
	 * @param unit
	 * @param name
	 */
	public Ingredient(float quantity, String unit, String name)
	{
		this.quantity = quantity;
		this.unit = unit;
		this.name = name;
	}
	
	/**
	 * Constructor using parcelable
	 * @param in
	 */
	public Ingredient(Parcel in){
        quantity = in.readFloat();
        unit = in.readString();
        name = in.readString();
    }

	/**
	 * Parcelable needs this function
	 */
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * Override standard write function
	 */
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeFloat(quantity);
		dest.writeString(unit);
		dest.writeString(name);
	}
	
	/**
	 * Fairly standard parcel-creation code
	 */
	public static final Parcelable.Creator<Ingredient> CREATOR = 
        new Parcelable.Creator<Ingredient>() {
        public Ingredient createFromParcel(Parcel in) {
            return new Ingredient(in);
        }

        public Ingredient[] newArray(int size) {
            return new Ingredient[size];
        }
    };
}
