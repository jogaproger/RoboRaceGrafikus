package modell;

import java.awt.event.KeyEvent;

import gfx.KeyStates;
import modell.jatekobj.Robot;
import modell.palya.Irany;
import modell.palya.Sebesseg;

/**
 * Jatekos osztaly
 */
public class Jatekos {

	private int locked = -1, lo=-1, lr=-1;
	/**
     * Jatekos pontszama
     */
    private int pontszam;
    /**
     * Jatekos neve
     */
    private String nev;
    /**
     * Jatekos robotja
     */
    private Robot robot;
    
    static public final int[][] kiosztasok = {
    	{KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_RIGHT, KeyEvent.VK_LEFT, 
    		KeyEvent.VK_NUMPAD1, KeyEvent.VK_NUMPAD2 },
        {KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_D, KeyEvent.VK_A, 
        	KeyEvent.VK_Q, KeyEvent.VK_E },    	
        {KeyEvent.VK_T, KeyEvent.VK_G, KeyEvent.VK_H, KeyEvent.VK_F, 
           	KeyEvent.VK_V, KeyEvent.VK_B },    	
        {KeyEvent.VK_I, KeyEvent.VK_K, KeyEvent.VK_L, KeyEvent.VK_J, 
       		KeyEvent.VK_U, KeyEvent.VK_O }
    };
    
    /**
     * Fel, le, jobbra, balra, ragacs, olaj
     */
    int[] kiosztas;
    /**
     * Jatekos sorszama
     */
    int sorszam;

    /**
     * 
     * @param nev Jatekos neve
     * @param jatek Jatek objektum, amihez tartozik
     * @param sorszam 1..4 jatekos sorszama
     */
    public Jatekos(String nev, Jatek jatek, int sorszam) {
    	// Nev beallitasa
        this.nev = nev;
        this.sorszam = sorszam;
        robot = new Robot(this, jatek, sorszam);
        pontszam = 0;
        kiosztas = kiosztasok[(sorszam-1)%4];

        // A jateknak is tudnia kell a robotunkrol
        jatek.addRobot(robot);

    }

    public void addPont(int p) {
    	pontszam += p;
    }
    
    public void commitPont(Ranglista r) {
    	r.commit(nev, pontszam);
    }

    public void iranyit(KeyStates keyStates, int tick) {
   		
        if( keyStates.getKeyState(kiosztas[0]) )
        	iranyit(Irany.Fel, tick);
        if( keyStates.getKeyState(kiosztas[1]) )
        	iranyit(Irany.Le, tick);
        if( keyStates.getKeyState(kiosztas[2]) )
        	iranyit(Irany.Jobbra, tick);
        if( keyStates.getKeyState(kiosztas[3]) )
        	iranyit(Irany.Balra, tick);
        if( keyStates.getKeyState(kiosztas[4]) )
        	lerakRagacs(tick);
        if( keyStates.getKeyState(kiosztas[5]) )
        	lerakOlaj(tick);
    }

    private void iranyit(Irany irany, int tick) {
    	
    	if( locked > 0 && tick != locked && tick < locked + 10 )
    		return;
    	
		robot.iranyit(irany);		
		if( robot.getSebesseg().isNulla() )
			locked = tick;
		else
			locked = -1;
	}

	public void iranyit(Sebesseg seb) {

        for (Irany i : seb.iranySet()) {
            robot.iranyit(i);
        }
    }

	/**
	 * Jatekos sorszama
	 * @return a sorszam
	 */
    public int getSorszam() {
        return sorszam;
    }

    /**
     * Olaj lerakasa
     * @param tick adott tick
     */
    public void lerakOlaj(int tick) {
    	if( lo > 0 && tick < lo+10 )
    		return;

    	if( robot.lerakOlaj() )
    		lo = tick;
    }

    /**
     * Ragacs lerakasa
     * @param tick Adott tick
     */
    public void lerakRagacs(int tick) {
    	if( lr > 0 && tick < lr+10 )
    		return;
    	if( robot.lerakRagacs() )
    		lr = tick;
    }
    /**
     * Robot infojának kiírása
     */
    public void robotInfo(){
        robot.info();
    }

	public String getNev() {
		return nev;
	}

	public int getPont() {
		return pontszam;
	}

	public int getRagacsNum() {
		return robot.getRagacsNum();
	}

	public int getOlajNum() {
		return robot.getOlajNum();
	}

}
