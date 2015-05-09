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
        	KeyEvent.VK_X, KeyEvent.VK_C },    	
        {KeyEvent.VK_Z, KeyEvent.VK_H, KeyEvent.VK_J, KeyEvent.VK_G, 
           	KeyEvent.VK_B, KeyEvent.VK_N },    	
        {KeyEvent.VK_O, KeyEvent.VK_L, KeyEvent.VK_K, KeyEvent.VK_COLON, 
       		KeyEvent.VK_COMMA, KeyEvent.VK_PERIOD }
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

    public void iranyit(KeyStates keyStates) {
        if( keyStates.getKeyState(kiosztas[0]) )
        	iranyit(Irany.Fel);
        if( keyStates.getKeyState(kiosztas[1]) )
        	iranyit(Irany.Le);
        if( keyStates.getKeyState(kiosztas[2]) )
        	iranyit(Irany.Jobbra);
        if( keyStates.getKeyState(kiosztas[3]) )
        	iranyit(Irany.Balra);
        if( keyStates.getKeyState(kiosztas[4]) )
        	lerakRagacs();
        if( keyStates.getKeyState(kiosztas[5]) )
        	lerakOlaj();
    }

    private void iranyit(Irany irany) {
		robot.iranyit(irany);		
	}

	public void iranyit(Sebesseg seb) {

        for (Irany i : seb.iranySet()) {
            robot.iranyit(i);
        }
    }

    public int getSorszam() {
        return sorszam;
    }

    public void lerakOlaj() {
        robot.lerakOlaj();
    }

    public void lerakRagacs() {
        robot.lerakRagacs();
    }
    public void robotInfo(){
        robot.info();
    }

}
