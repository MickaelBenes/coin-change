package fr.mbenes.business;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class CoinMachine {
	
	private List<Coin>	_coinsAvailable;
	
	public CoinMachine() {
		this._coinsAvailable	= new ArrayList<>();
	}
	
	/**
	 * Initializes the list of all available coins, sorted by the coins' value (ASC or DESC).
	 * 
	 * @param	order	the order in which sort the list
	 */
	private void _initCoinsAvailable( String order ) {
		if ( !this._coinsAvailable.isEmpty() ) {
			this._coinsAvailable.clear();
		}
		
		// here, the values of IRL coins are multiplied by 100 to have simplify math
		this._coinsAvailable.add( new Coin(1, 5) );
		this._coinsAvailable.add( new Coin(2, 5) );
		this._coinsAvailable.add( new Coin(5, 5) );
		this._coinsAvailable.add( new Coin(10, 5) );
		this._coinsAvailable.add( new Coin(20, 5) );
		this._coinsAvailable.add( new Coin(50, 5) );
		this._coinsAvailable.add( new Coin(100, 5) );
		this._coinsAvailable.add( new Coin(200, 5) );
		
		Comparator<? super Coin> comparator;
		if ( order.equals("DESC") ) { // order the coins from max to min value
			comparator = (c1, c2) -> Double.compare( c2.getValue(), c1.getValue() );
		}
		else { // order the coins from min to max value
			comparator = (c1, c2) -> Double.compare( c1.getValue(), c2.getValue() );
		}
		
		this._coinsAvailable = this._coinsAvailable
				.stream()
				.sorted( comparator )
				.collect( Collectors.toList() );
	}
	
	/**
	 * Gets the list of coin with the minimum coins needed to return the change amount.
	 * 
	 * @param	change	the change amount
	 * @return	the list of coin with the minimum coins needed to return the change amount
	 */
	public List<Coin> getMinChange( double change ) {
		this._initCoinsAvailable( "DESC" );
		
		List<Coin> coins = new ArrayList<>();
		
		for ( Coin c : this._coinsAvailable) {
			// get all the coins until we match the change amount
			// if no more current coin available, look for the next
			while ( change >= c.getValue() && c.getHasMore() ) {
				if ( !coins.contains(c) ) { // increment the current coin's count and add it to the list
					c.incrementCount();
					coins.add( c );
				}
				else { // get the current coin from the list and increment its count
					int index = coins.indexOf( c );
					coins.get( index ).incrementCount();
				}
				
				c.decrementAvailable();
				change -= c.getValue();
			}
		}
		
		return coins;
	}
	
	/**
	 * Return the list of coins to get the change amount with the maximum of coins.
	 * 
	 * @param	change	the change amount
	 * @return	the list of coins to get the change amount with the maximum of coins.
	 */
	public List<Coin> getMaxChange( double change ) {
		this._initCoinsAvailable( "ASC" );
		
		List<Coin> coins	= new ArrayList<>();
		double amount		= 0;
		
		for ( Coin c : this._coinsAvailable ) {
			while ( change > amount && c.getHasMore() ) { // gets all the coins until the change amount is exceeded
				if ( !coins.contains(c) ) {
					c.incrementCount();
					coins.add( c );
				}
				else {
					int index = coins.indexOf( c );
					coins.get( index ).incrementCount();
				}
				
				c.decrementAvailable(); // one less current coin available
				amount += c.getValue();
			}
		}
		
		// the amount could match the change
		// in that case we can return the calculated list as is ("lumière !" Don't get it ? "As is" => "Aziz", lumière...
		// Still not ? Watch The Fifth Element in French)
		if ( Double.compare(amount, change) == 0 ) {
			return coins;
		}
		
		/*
		 * if we arrive here, it means that the amount exceeds the change
		 * and that we're going to give back too much money,  which is bad
		 * so we need to get the list of minimum coins needed
		 * to remove the extra coins from our list
		 */
		
		// the list of coins to remove from our main list
		List<Coin> coinsToRemove	= getMinChange( amount - change );
		
		// remove every extra coin from coins that are in coins2
		// seems a bit ugly to me, there might be a better way to improve it with streams...
		coins.removeIf( coin -> {
			for ( Coin c : coinsToRemove ) {
				if ( Double.compare(coin.getValue(), c.getValue()) == 0 ) {
					for ( int i = 1; i <= c.getCount(); i++ ) {
						coin.decrementCount();
					}
				}
				
				if ( coin.getCount() <= 0 )	return true;
			}
			
			return false;
		});
		
		return coins;
	}

}
