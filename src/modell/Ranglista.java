package modell;

import skeleton.Logger;
/**
 * Ranglista oszt�ly
 */
public class Ranglista {
	
	/**
	 * N�v �s hozz� tartoz� pont t�rol�sa
	 */
	static class NevPont{
		public String nev;
		public int pont;
		/**
		 * P�ros inicializ�l�sa
		 * @param nev J�t�kos neve
		 * @param pont J�t�kos pontsz�ma
		 */
		public NevPont( String nev, int pont ){
			this.nev = nev;
			this.pont = pont;			
		}
	}
	
	private static final int count=10;
	
	NevPont lista[];
	
	public Ranglista(){
		Logger.printCall(this);
		
		lista = new NevPont[count];
		for(int i = 0 ; i < lista.length; i++)
			lista[i] = new NevPont("",0);
		
		Logger.printCallEnd();		
	}
	
	public void commit(String nev, int pont){
		Logger.printCall(this, nev, ""+pont);
		
		Logger.printCallEnd();		
	}
	
	public void megjelenit(){
		Logger.printCall(this);
		
		Logger.printCallEnd();		
	}
}
