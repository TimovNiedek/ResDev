package com.tsjd.HotMeals;

import java.util.ArrayList;

import android.os.Parcel;
import android.os.Parcelable;



public class Recipe implements Parcelable {

	private int ID;
	private String name;
	private ArrayList<Ingredient> ingredients;
	private String howto;
	private int time;
	private double price;
	private boolean favourite;
	private String path;
	
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
	
	public String ingredientenToString(){
		String result = "hoi";
		
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < this.ingredients.size(); i++){
			sb.append(this.ingredients.get(i).quantity).append(this.ingredients.get(i).unit).append(this.ingredients.get(i).name);
			sb.append("  ");
		}
		result = sb.toString();
		 
		return result;
	}
	
	public String getName(){
		return this.name;
	}
	
	public int getId(){
		return this.ID;
	}
	
	public String getBereiding(){
		return this.howto;
	}
	
	public int getTime(){
		return this.time;
	}
	
	public double getPrice(){
		return this.price;
	}
	
	public boolean favoriet(){
		return this.favourite;
	}
	
	public String getPath(){
		return this.path;
	}
	
	public void toggleFavourite(){
		if (this.favourite){
			this.favourite = false;
		}
		else{
			this.favourite = true;
		}
	}
	
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

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

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
     * Functie om een double waarde (voor budget) om te zetten naar een string met altijd twee decimalen
     * @param number de double waarde
     * @return de string
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
		
		result.replace(" ", "");	// Als het budget < €10, haal de leegte weg van het tiental
		return result;
    }
}
