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
	static final long ONE_MINUTE_IN_MILLIS=60000;//millisecs

	public Salad(){
		this.toppings = new ArrayList<String>();
		this.details = new ArrayList<SaladTopping>();
		long temp = (new Date()).getTime();
		this.ready=new Date(temp + (15 * ONE_MINUTE_IN_MILLIS));
	}
	
	public Salad(ArrayList<String> toppings){
		this.toppings = toppings;
		this.details = new ArrayList<SaladTopping>();
		if (!toppings.isEmpty()) {
			for (String s : toppings) {
				SaladTopping t = new SaladTopping(s);
				this.details.add(t);
			}
		}
		long temp = (new Date()).getTime();
		this.ready=new Date(temp + (15 * ONE_MINUTE_IN_MILLIS));
	}
	
	public void setDate(Long date){
		this.ready = new Date(date);
	}
	public ArrayList<String> getToppings(){
		return this.toppings;
	}
	
	public void toggleSaladTopping(String topping){
		if(this.toppings.contains(topping)){
			toppings.remove(topping);
			for(SaladTopping t : details) {
				if(t.getName().equals(topping))
					details.remove(t);
			}
		}else{
			toppings.add(topping);
			details.add(new SaladTopping(topping));
		}
	}
	
	public double addPrice () {
		double total = 0;
		if(!details.isEmpty()) {
			for(SaladTopping t : details) 
				total += t.getPrice();
		}
		return total;
	}
	
	public int getCalories () {
		int fat = 0;
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
		return getToppingsString() + ITEM_SEP + ready.getTime(); 
	}
	
	public String toLog() {
		    return getToppingsString() + ITEM_SEP + ready.getTime() + ITEM_SEP; 
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
}
