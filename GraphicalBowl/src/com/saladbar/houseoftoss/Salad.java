package com.saladbar.houseoftoss;

import java.util.Date;
import java.util.ArrayList;
import java.util.HashMap;

import android.text.format.DateFormat;
import android.util.Log;
import android.widget.ImageView;

public class Salad{
	public static final String ITEM_SEP = System.getProperty("line.separator");
	private ArrayList<String> toppings;
	private Date ready;
	private ArrayList<SaladTopping> details;
	
	public Salad(){
		this.toppings = new ArrayList<String>();
		this.ready = new Date();
		Log.d("test", this.ready.toString());
	}
	
	public Salad(ArrayList<String> toppings){
		this.toppings = toppings;
		this.ready = new Date();
	}
	
	public void SetDate(Date date){
		this.ready = date;
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
	
	public void stringToItem () {
		if (!toppings.isEmpty()) {
			for (String s : toppings) {
				SaladTopping t = new SaladTopping(s);
				details.add(t);
			}
		}
	}
	
	public double addPrice () {
		double total = 0;
		stringToItem();
		if(!details.isEmpty()) {
			for(SaladTopping t : details) 
				total += t.getPrice();
		}
		return total;
	}
	
	public int getCalories () {
		int fat = 0;
		stringToItem();
		if(!details.isEmpty()) {
			for(SaladTopping t : details) 
				fat += t.getCalories();
		}
		return fat;
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
	
	public String getReady(){
		DateFormat df = new DateFormat();
		return (String) df.format("MMM-dd-yyyy hh:mm a", this.ready);
	}
	
	public String getPrice(){
		return String.format("$%2.2f", addPrice());
	}
	
	public String getCalories() {
		return Integer.toString(addCalories());
	}
}
