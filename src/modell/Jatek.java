package modell;

import gfx.Scene;
import gfx.Window;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import main.Main;
import modell.jatekobj.JatekObj;
import modell.jatekobj.Robot;
import modell.palya.Irany;
import modell.palya.Palya;
import modell.palya.Sebesseg;

/**
 * Jatekot megvalosito osztaly
 *
 */
public class Jatek {

    /**
     * Jatekosok tombje
     */
    private Jatekos[] jatekosok;
    /**
     * Jateevo objektumok listaja
     */
    private ArrayList<JatekObj> objects;
    
    Scene scene;
    
    /**
     * A palya, amelyen a jatek zajlik
     */
    private Palya palya;
    
    /**
     * Jatek veget jelzi
     */
    private boolean endflag;
    

    /**
     * robotok szama a palyan
     */
    private int kezdoIndex;

    public Jatek(String palyafajl, String[] jatekosnevek ) throws Exception {
        ujJatek(palyafajl, jatekosnevek);
    }

    public void ujJatek(String palyafajl, String[] jatekosnevek ) throws Exception {

        jatekosok = new Jatekos[jatekosnevek.length];
        objects = new ArrayList<JatekObj>();
        scene = new Scene();
        kezdoIndex = 0;

        palya = new Palya(this);
        if (palyafajl == null || !palya.betolt(palyafajl)) 
        	throw new Exception("Nem sikerult betolteni a palyat");

        for (int i = 0; i < jatekosnevek.length; i++) {
            jatekosok[i] = new Jatekos(jatekosnevek[i], this, 1 + i);
        }
    }

    public int getJatekosNum() {
        return jatekosok.length;
    }

    /**
     * Jatekobjektum hozzaadasa a jatekhoz
     *
     */
    public void addJatekObj(JatekObj j) {
    	
        this.objects.add(j);
        j.addToScene( scene );
        
    }


	/**
     * Robot hozzaadasa a jatekhoz es lehelyezese a kovetkezo kezdocellara
     */
    public void addRobot(Robot r) {
        r.addToCella(palya.getStartCell(kezdoIndex));
        kezdoIndex++;
        addJatekObj(r);
    }

    /**
     * Kilepes a jatekbol
     *
     */
    public void kilepes() {
        endflag = true;
    }

    /**
     * Jatek lejatszasa
     *
     */
    public void futtat(double jatekidoSec, Window window) {
    	try{
	        System.out.println("Jatek kezdes:");
	        int skipnum = 0;
	
	        for (
        		int tick = 0;
        		!endflag && tick < jatekidoSec * Main.getTicksPerSecond(); 
        		tick++) 
	        {
	        	Main.SyncTime();
	            if ( --skipnum <= 0) {

	                for (Jatekos jatekos : jatekosok)
		                jatekos.iranyit(window.getKeyStates());
	            }
	            // Minden jatekobjektum viselkedesenek megvalositasa
	            for (JatekObj jobj : objects) {
	                jobj.simulate();
	            }
	            draw(window);
	        }

            for (Jatekos jatekos : jatekosok)
            	jatekos.commitPont(new Ranglista());
	       
    	}catch(Exception ex){
    		ex.printStackTrace();    		
    	}
    }

    private void draw(Window window) {
		Graphics g = window.getBackbufferGraphics();
		g.setColor( Color.GRAY );
		g.fillRect(0,  0, window.getWidth(), window.getHeight());
		scene.draw(g);
		window.swapBuffers();		
    }

	private boolean parancsKisRobot(String[] cmd) {
    	if( cmd.length< 2 )
    	{
    		palya.kisrobot();
    		return true;
    	}	
    	else{
    		try {
    			int x  = Integer.parseInt(cmd[1]);
    			int y  = Integer.parseInt(cmd[2]);
    			if( palya.kisrobot(x, y) )
    				return true;
    		} catch (Exception ex) {
    			return false;
            }	
    	}
		return false;
	}

    /**
     * Pontok elkuldese a ranglistanak
     *
     */
    public void commitPontok() {
    }

}
