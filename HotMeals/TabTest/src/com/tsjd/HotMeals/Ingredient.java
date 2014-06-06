package com.tsjd.HotMeals;

import java.lang.Override;
import java.lang.String;

import android.os.Parcel;
import android.os.Parcelable;

public class Ingredient implements Parcelable{
	public float quantity;
	public String unit;
	public String name;
	
	public Ingredient(float quantity, String unit, String name)
	{
		this.quantity = quantity;
		this.unit = unit;
		this.name = name;
	}
	
	public Ingredient(Parcel in){
        quantity = in.readFloat();
        unit = in.readString();
        name = in.readString();
    }

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeFloat(quantity);
		dest.writeString(unit);
		dest.writeString(name);
	}
	
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
