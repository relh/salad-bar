package com.saladbar.houseoftoss;

public class SaladTopping {
    private String name;
    private String imageSrc;
    private Float price;
    private Float calories;
    
    public SaladTopping(String name, Float price, Float calories, String src){
    	this.name = name;
    	this.imageSrc = src;
    	this.price = price;
    	this.calories = calories;
    }
    
    public String getName(){
    	return this.name;
    }
    
    public String getImgSrc(){
    	return this.imageSrc;
    }
    
    public Float getPrice() {
        return this.price;
    }
    
    public Float getCalories() {
        return this.calories;
    }
}
