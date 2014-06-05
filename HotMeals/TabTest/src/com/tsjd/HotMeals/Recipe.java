package com.tsjd.HotMeals;

import java.util.ArrayList;



public class Recipe {

	private int ID;
	private String name;
	private ArrayList<Ingredient> ingredients;
	private String howto;
	private int time;
	private double price;
	private boolean favourite;
	private String path;
	public static class Ingredient{
		public float quantity;
		public String unit;
		public String name;
		
		public Ingredient(float quantity, String unit, String name)
		{
			this.quantity = quantity;
			this.unit = unit;
			this.name = name;
		}
	};
	
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
		String result;
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i <= this.ingredients.size(); i++){
			sb.append(this.ingredients.get(i).quantity).append(this.ingredients.get(i).unit).append(this.ingredients.get(i).name);
			sb.append("  ");
		}
		result = sb.toString();
		return result;
	}
	
	public String getName(){
		return this.name;
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
}
