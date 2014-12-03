package cmsc436.GroupProject;

import java.util.ArrayList;

public class Salad {
	private SaladTopping lettuce;
	private SaladTopping dressing;
	
	private ArrayList<SaladTopping> protiens;
	private ArrayList<SaladTopping> cheeses;
	private ArrayList<SaladTopping> toppings;
	
	public Salad(){
		this.lettuce = null;
		this.dressing = null;	
		this.protiens = new ArrayList<SaladTopping>();
		this.cheeses = new ArrayList<SaladTopping>();
		this.toppings = new ArrayList<SaladTopping>();
	}
	
	public SaladTopping getlettuce(){
		return this.lettuce;
	}	
	
	public SaladTopping getDressing(){
		return this.dressing;
	}
	
	public ArrayList<SaladTopping> getProtiens(){
		return this.protiens;
	}	
	
	public ArrayList<SaladTopping> getCheeses(){
		return this.cheeses;
	}
	
	public ArrayList<SaladTopping> getToppings(){
		return this.toppings;
	}
	
	public void setSaladDressing(SaladTopping dressing){
		this.dressing = dressing;
	}
	
	public void setSaladLettuce(SaladTopping lettuce){
		this.lettuce = lettuce;
	}
	
	public void toggleSaladTopping(SaladTopping topping){
		if(this.toppings.contains(topping)){
			toppings.remove(topping);
		}else{
			toppings.add(topping);
		}
	}
	
	public void toggleCheeseTopping(SaladTopping cheese){
		if(this.cheeses.contains(cheese)){
			cheeses.remove(cheese);
		}else{
			cheeses.add(cheese);
		}
	}
	
	public void toggleProtienTopping(SaladTopping protien){
		if(this.protiens.contains(protien)){
			protiens.remove(protien);
		}else{
			protiens.add(protien);
		}
	}
	
	public boolean hasCheese(SaladTopping cheese){
		return this.cheeses.contains(cheese);
	}
	
	public boolean hasProtien(SaladTopping protien){
		return this.protiens.contains(protien);
	}
	
	public boolean hasTopping(SaladTopping topping){
		return this.toppings.contains(topping);
	}
}
