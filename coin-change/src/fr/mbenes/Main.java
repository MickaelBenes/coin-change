package fr.mbenes;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import fr.mbenes.business.Coin;
import fr.mbenes.business.CoinMachine;

public class Main {

	public static void main( String[] args ) {
		Scanner reader = new Scanner( System.in );  // reading from System.in
		try {
			System.out.println( "Input price." );
			double price = Double.valueOf( reader.next() );
			
			System.out.println( "Input money." );
			double money	= Double.valueOf( reader.next() );
			
			double change	= -Math.floor( (price - money) * 100 ) / 100; // calculates change, up to 2 decimal places
			
			System.out.println( "Change : " + change + "€" );
			if ( change == 0 ) {
				return;
			}
			
			change *= 100; // multiplies change by 100 to simplify the upcoming math
			
			CoinMachine cm		= new CoinMachine();
			List<Coin> minCoins	= cm.getMinChange( change );
			List<Coin> maxCoins	= cm.getMaxChange( change );
			
			// divide every coin value by 100 to see their IRL values
			minCoins.stream()
					.forEach( c -> c.setValue(c.getValue() / 100) );
			maxCoins.stream()
					.forEach( c -> c.setValue(c.getValue() / 100) );
			
			// calculate the sums of all coin value to see if it matches the change amounnt
			double minTotal	= minCoins
					.stream()
					.collect( Collectors.summingDouble(c -> c.getValue() * c.getCount()) );
			double maxTotal	= maxCoins
					.stream()
					.collect( Collectors.summingDouble(c -> c.getValue() * c.getCount()) );
			
			System.out.println( "Min coins : " + minCoins );
			System.out.println( "Total : " + minTotal );
			System.out.println( "Max coins : " + maxCoins );
			System.out.println( "Total : " + maxTotal );
		}
		catch ( Exception e ) {
			e.printStackTrace();
		}
		finally {
			reader.close();
		}
	}
	
}
