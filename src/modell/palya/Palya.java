package modell.palya;

import modell.Jatek;
import skeleton.Logger;

/**
 *	P�lya oszt�ly
 */
public class Palya {

	private int szelesseg, magassag;
	private Cella[][] cellak;
	private Jatek jatek;
	
	
	public Palya(Jatek j){
		Logger.printCall(this, j);
		jatek = j;
		
		Logger.printCallEnd();	
	}
	
}
