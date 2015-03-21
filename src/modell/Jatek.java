package modell;
import java.util.ArrayList;

import modell.jatekobj.JatekObj;
import modell.jatekobj.Robot;
import modell.palya.Palya;
import skeleton.Logger;
/**
 * J�t�kot megval�s�t� oszt�ly
 *
 */
public class Jatek {
	/** Singleton P�ld�ny */
	private static Jatek instance = new Jatek();
	
	/** J�t�khoz tartoz� ranglista */
	private Ranglista ranglista = new Ranglista();
	/** J�t�kosok t�mbje */
	private Jatekos[] jatekosok;
	/** J�t�ev� objektumok list�ja*/
	private ArrayList<JatekObj> objects;
	/** A p�lya, amelyen a j�t�k zajlik */
	private Palya palya;
	/** J�t�k v�g�t jelzi */
	private boolean endflag;
	/** J�t�k kezdete �ta eltelt id� */
	private int ido;
	/** robotok sz�ma a p�ly�n */
	private int robotszam;
	
	/**
	 * J�t�kos p�ld�ny�nak visszaad�sa
	 * @return Singleton p�ld�ny
	 */
	public static Jatek getInstance(){
		return instance;		
	}
	
	public void ujJatek( String palyafajl, int jatekosnum ){
		Logger.printCall(this, palyafajl, ""+jatekosnum);
		
		jatekosok = new Jatekos[jatekosnum];
		objects = new ArrayList<JatekObj>();
		robotszam = 0;
		
		palya = new Palya(this);
		palya.betolt(palyafajl);
		
		for( int i = 0 ; i < jatekosnum ; i++ )
			jatekosok[i] = new Jatekos("N�v"+i);
		
		Logger.printCallEnd();
	}
	
	/**
	 * J�t�kobjektum hozz�ad�sa a j�t�khoz
	 * 
	 */	
	public void addJatekObj( JatekObj j ){
		Logger.printCall(this, j);
		
		
		Logger.printCallEnd();
	}
	
	/**
	 * Robot hozz�ad�sa a j�t�khoz �s lehelyez�se
	 * a k�vetkez� kezd�cell�ra
	 * 
	 */	
	public void addRobot(Robot r){
		Logger.printCall(this, r);
		
		palya.getStartCell(robotszam);
		robotszam++;
		
		addJatekObj(r);		
		Logger.printCallEnd();	
	}
	
	/**
	 * Kil�p�s a j�t�kb�l
	 * 
	 */	
	public void kilepes(){
		Logger.printCall(this);
		
		endflag = true;
		Logger.print("endflag = true");
		
		Logger.printCallEnd();	
	}

	/**
	 * J�t�k lej�tsz�sa
	 * 
	 */	
	public void simulate(){
		Logger.printCall(this);
		
		
		Logger.printCallEnd();	
	}
	
	/**
	 * Pontok elk�ld�se a ranglist�nak
	 * 
	 */	
	public void commitPontok(){
		Logger.printCall(this);
		
		
		Logger.printCallEnd();	
	}
	
}
