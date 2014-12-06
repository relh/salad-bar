package com.saladbar.houseoftoss;

import java.util.ArrayList;
import java.util.HashMap;

import android.widget.ImageView;

public class Salad{
	public static final String ITEM_SEP = System.getProperty("line.separator");
	private ArrayList<String> toppings;
	
	public Salad(){
		this.toppings = new ArrayList<String>();
	}
	
	public Salad(ArrayList<String> toppings){
		this.toppings = toppings;
	}
	
	public ArrayList<String> getToppings(){
		return this.toppings;
	}
	
	public void toggleSaladTopping(String topping){
		if(this.toppings.contains(topping)){
			toppings.remove(topping);
		}else{
			toppings.add(topping);
		}
	}
	
	public boolean hasTopping(String topping){
		return this.toppings.contains(topping);
	}
	
	public String toString() {
		return getToppingsString(); 
	}
	
	public String toLog() {
		    return getToppingsString() + ITEM_SEP; 
	}
	
	public String getToppingsString(){
		String res = "";
		for(int i = 0; i < toppings.size(); i++){
			res += toppings.get(i);
			if(i < toppings.size() - 1){
				res += ", ";
			}
		}
		return res;
	}
	
	public String getPrice(){
		return String.format("$%2.2f", (5.75 + toppings.size() * .50));
	}
}
