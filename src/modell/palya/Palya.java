package modell.palya;

import modell.Jatek;
import skeleton.Logger;

/**
 *	P�lya oszt�ly
 */
public class Palya {

	private static final int meret = 4;
	private int szelesseg, magassag;
	private Cella[][] cellak;
	private Jatek jatek;
	
	/**
	 * P�lya l�trehoz�sa
	 * @param j A j�t�k, amelyhez a p�lya tartozik
	 * 
	 */
	public Palya(Jatek j){
		Logger.printCall(this, j);
		jatek = j;
		szelesseg = magassag = meret;
		cellak = new Cella[szelesseg][magassag];
		
		Logger.printCallEnd();	
	}
	
}
