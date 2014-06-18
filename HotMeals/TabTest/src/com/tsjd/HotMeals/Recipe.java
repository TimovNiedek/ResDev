package com.tsjd.HotMeals;

import java.util.ArrayList;
import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * 
 * @author Daniel Roeven
 * @author Sander van Dam
 * @author Timo van Niedek
 * @author Jaco Schalij
 * @version 0.5
 *
 */

public class Recipe implements Parcelable {

	private int ID;
	private String name;
	private ArrayList<Ingredient> ingredients;
	private String howto;
	private int time;
	private double price;
	private boolean favourite;
	private String path;
	
	/**
	 * Incredibly interesting constructor eh?
	 * @param naam
	 * @param ingredienten
	 * @param bereiding
	 * @param tijd
	 * @param prijs
	 * @param favoriet
	 * @param idee
	 * @param path
	 */
	public Recipe(String naam, ArrayList<Ingredient> ingredienten, String bereiding, int tijd, double prijs, boolean favoriet, int idee, String path){
		this.name = naam;
		this.ID = idee;
		this.ingredients = ingredienten;
		this.howto = bereiding;
		this.time = tijd;
		this.price = prijs;
		this.favourite = favoriet;
		this.path = path;
	}
	
	/**
	 * Create string from ingredients to display
	 * @return string
	 */
	public String ingredientenToString(){
		String result;
		
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < this.ingredients.size(); i++){
			sb.append(this.ingredients.get(i).quantity + " ").append(this.ingredients.get(i).unit + " ").append(this.ingredients.get(i).name);
			sb.append("  ");
		}
		result = sb.toString();
		 
		return result;
	}
	
	/**
	 * Incredibly interesting getter eh?
	 * @return name
	 */
	public String getName(){
		return this.name;
	}
	
	/**
	 * Incredibly interesting getter eh?
	 * @return ID
	 */
	public int getId(){
		return this.ID;
	}
	
	/**
	 * Incredibly interesting getter eh?
	 * @return how to description
	 */
	public String getBereiding(){
		return this.howto;
	}
	
	/**
	 * Incredibly interesting getter eh?
	 * @return time
	 */
	public int getTime(){
		return this.time;
	}
	
	/**
	 * Incredibly interesting getter eh?
	 * @return name
	 */
	public double getPrice(){
		return this.price;
	}
	
	/**
	 * Incredibly interesting getter eh?
	 * @return favourite
	 */
	public boolean favoriet(){
		return this.favourite;
	}
	
	/**
	 * Incredibly interesting getter eh?
	 * @return path
	 */
	public String getPath(){
		return this.path;
	}
	
	/**
	 * Incredibly interesting getter eh?
	 * @return favourite
	 */
	public void toggleFavourite(){
		if (this.favourite){
			this.favourite = false;
		}
		else{
			this.favourite = true;
		}
	}
	
	/**
	 * Parcel accepter which sets local recipe items
	 */
	@SuppressWarnings("unchecked")
	public Recipe(Parcel in){
		
		/*
		 * private int ID;
			private String name;
			private ArrayList<Ingredient> ingredients;
			private String howto;
			private int time;
			private double price;
			private boolean favourite;
			private String path;
		 */
        
		ID = in.readInt();
		name = in.readString();
		ingredients = (ArrayList<Ingredient>)in.readSerializable();
		howto = in.readString();
		time = in.readInt();
		price = in.readDouble();
		favourite = (in.readInt() == 1);
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
	 * Write to parcel 
	 */
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		/*
		 * private int ID;
			private String name;
			private ArrayList<Ingredient> ingredients;
			private String howto;
			private int time;
			private double price;
			private boolean favourite;
			private String path;
		 */
		
		dest.writeInt(ID);
		dest.writeString(name);
		dest.writeSerializable(ingredients);
		dest.writeString(howto);
		dest.writeInt(time);
		dest.writeDouble(price);
		int favouriteInt = (favourite) ? 1 : 0;
		dest.writeInt(favouriteInt);
		dest.writeString(path);
	}
	
	/**
	 * Fairly standard parcel-creation code
	 */
	public static final Parcelable.Creator<Recipe> CREATOR = 
            new Parcelable.Creator<Recipe>() {
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };
    
    /**
     * Change double value to string with two decimals (to print as currency)
     * @param double value
     * @return string with two decimals
     */
	public static String doubleToCurrency(double number)
    {
    	String result;
		float epsilon = 0.004f; // 4 tenths of a cent
		if (Math.abs(Math.round(number) - number) < epsilon) {
			result = String.format("%2.0f", number); // sdb
		} else {
		    result = String.format("%2.2f", number); // dj_segfault
		}
		
		result.replace(" ", "");	// Als het budget < 10, haal de leegte weg van het tiental
		return result;
    }
}
