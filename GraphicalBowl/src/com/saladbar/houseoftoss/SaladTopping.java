package com.saladbar.houseoftoss;

public class SaladTopping {
    private String name;
    private double price;
    private int calories;
    
    public SaladTopping(String name){
    	this.name = name;
    }
    
    public String getName(){
    	return this.name;
    }
    
    public double getPrice() {
    	if (this.name.equals("Spinach") || this.name.equals("Lettuce"))
    		this.price = 0.99;
    	else if (this.name.equals("Egg") || this.name.equals("Ham")
    			|| this.name.equals("Bacon") || this.name.equals("Kidney Beans")
    			|| this.name.equals("Garbanzo Beans") || this.name.equals("Shrimp")
    			|| this.name.equals("Chicken")) 
    		this.price = 2.49;
    	else
    		this.price = 1.49;
    	
    	return this.price;
    }
    
    public float getCalories() {
    	switch (this.name) {
    		case "Spinach" : this.calories = 16;
    		break;
    		case "Lettuce" : this.calories = 15;
    		break;
    		case "Egg" : this.calories = 70;
    		break;
    		case "Ham" : this.calories = 120;
    		break;
    		case "Bacon" : this.calories = 46;
    		break;
    		case "Kidney Beans" : this.calories = 144;
    		break;
    		case "Shrimp" : this.calories = 100;
    		break;
    		case "Garbanzo Beans" : this.calories = 150;
    		break;
    		case "Chicken" : this.calories = 142;
    		break;
    		case "Applesauce" : this.calories = 80;
    		break;
    		case "Artichokes" : this.calories = 55;
    		break;
    		case "Olives" : this.calories = 4;
    		break;
    		case "Broccoli" : this.calories = 40;
    		break;
    		case "Cauliflower" : this.calories = 40;
    		break;
    		case "Cherries" : this.calories = 50;
    		break;
    		case "Tomatoes" : this.calories = 20;
    		break;
    		case "Corn" : this.calories = 72;
    		break;
    		case "Cottage Cheese" : this.calories = 100;
    		break;
    		case "Craisins" : this.calories = 130;
    		break;
    		case "Noodles" : this.calories = 122;
    		break;
    		case "Croutons" : this.calories = 132;
    		break;
    		case "Feta Cheese" : this.calories = 75;
    		break;
    		case "Gelatin" : this.calories = 15;
    		break;
    		case "Hummus" : this.calories = 90;
    		break;
    		case "Jalapenos" : this.calories = 15;
    		break;
    		case "Carrots" : this.calories = 70;
    		break;
    		case "Mushrooms" : this.calories = 35;
    		break;
    		case "Parmesan" : this.calories = 122;
    		break;
    		case "Peas" : this.calories = 63;
    		break;
    		case "Pepperoncini" : this.calories = 50;
    		break;
    		case "Beets" : this.calories = 50;
    		break;
    		case "Radishes" : this.calories = 10;
    		break;
    		case "Raisins" : this.calories = 130;
    		break;
    		case "Peppers" : this.calories = 20;
    		break;
    		case "Mozzarella" : this.calories = 72;
    		break;
    		case "Monterey Jack" : this.calories = 105;
    		break;
    		case "Cucumbers" : this.calories = 16;
    		break;
    		case "Peaches" : this.calories = 50;
    		break;
    		case "Red Onion" : this.calories = 10;
    		break;
    		case "Prunes" : this.calories = 100;
    		break;
    		case "Strawberry Whip" : this.calories = 100;
    		break;
    		case "Sunflower Seeds" : this.calories = 165;
    		break;
    		default: this.calories = 0;
    		break;
    	}
    		
    	return this.calories;
    }
}
