package fr.mbenes.business;

public class Drink {

	private String _name;
	private double _price;
	
	public Drink( String name, double price ) {
		this._name	= name;
		this._price	= price;
	}
	
	public String getName() {
		return this._name;
	}
	
	public double getPrice() {
		return this._price;
	}
	
}
