package modell;
import java.util.ArrayList;

import modell.jatekobj.JatekObj;
import modell.jatekobj.Robot;
import modell.palya.Palya;
import skeleton.Logger;

public class Jatek {
	private static Jatek instance = new Jatek();
	
	private Ranglista ranglista = new Ranglista();
	private Jatekos[] jatekosok;
	private ArrayList<JatekObj> objects;
	private Palya palya;
	private boolean endflag;
	private int ido;
	
	public static Jatek getInstance(){
		return instance;		
	}
	
	public void ujJatek( String palya, int jatekosnum ){
		Logger.printCall(this, palya, ""+jatekosnum);
		
		jatekosok = new Jatekos[jatekosnum];
		objects = new ArrayList<JatekObj>();
		
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
		
		addJatekObj(r);
		
		Logger.printCallEnd();	
	}
	
	/**
	 * Kil�p�s a j�t�kb�l
	 * 
	 */	
	public void kilepes(){
		Logger.printCall(this);
		
		
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
