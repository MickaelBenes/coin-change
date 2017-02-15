package fr.mbenes.business;

public class Coin {

	private double	_value;
	private int		_available;
	private int		_count;
	
	public Coin( double value, int available ) {
		this._value		= value;
		this._available	= available;
		this._count		= 0;
	}
	
	public double getValue() {
		return this._value;
	}
	
	public void setValue( double value ) {
		this._value = value;
	}
	
	public void setAvailable( int available ) {
		this._available = available;
	}
	
	public int getCount() {
		return this._count;
	}
	
	public boolean getHasMore() {
		return this._available > 0;
	}
	
	public void decrementAvailable() {
		if ( this.getHasMore() ) {
			this._available--;
		}
	}
	
	public void incrementCount() {
		this._count++;
	}
	
	public void decrementCount() {
		this._count--;
	}
	
	@Override
	public String toString() {
		return this._value + "x" + this._count;
	}
	
}
