package modell;

import gfx.KeyStates;
import gfx.Resource;
import gfx.Scene;
import gfx.Window;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import main.Main;
import modell.jatekobj.JatekObj;
import modell.jatekobj.Robot;
import modell.palya.Palya;

/**
 * Jatekot megvalosito osztaly
 *
 */
public class Jatek {
	/**
	 * Háttérkép
	 */
	BufferedImage bg = Resource.getImage("kepek/gbg.png");
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
	        
	        KeyStates keystates = window.getKeyStates();
	        keystates.pullChanges();
	
	        for (
        		int tick = 0;
        		!endflag && tick < jatekidoSec * Main.getTicksPerSecond(); 
        		tick++) 
	        {
	        	KeyStates.Change[] changes = keystates.pullChanges();       
	        	if( changes[KeyEvent.VK_ESCAPE] == KeyStates.Change.PUSHED )
	        		kilepes();
	        	
	        	if( changes[KeyEvent.VK_P] == KeyStates.Change.PUSHED )
	        		pause(window);
	        	
	        	if( (tick-(10 * Main.getTicksPerSecond())) % 
	        			  (20 * Main.getTicksPerSecond()) == 0 )
	        		palya.kisrobot();
	        	
	        	Main.SyncTime();
	            if ( --skipnum <= 0) {

	                for (Jatekos jatekos : jatekosok)
		                jatekos.iranyit(keystates, tick);
	            }
	            // Minden jatekobjektum viselkedesenek megvalositasa
	            for (JatekObj jobj : objects) {
	                jobj.simulate();
	            }
	            draw(window, tick / (double)(Main.getTicksPerSecond()), jatekidoSec);
	        }

            commitPontok();
	       
    	}catch(Exception ex){
    		ex.printStackTrace();    		
    	}
    }

    private void pause(Window window) {
		Graphics g = window.getBackbufferGraphics();
		BufferedImage img = Resource.getImage("kepek/pause.png");
		
		g.drawImage(img, 40, 40, null);
		window.swapBuffers();
		while( window.getKeyStates().pullChanges()[KeyEvent.VK_P] 
				!= KeyStates.Change.PUSHED );
		
		
	}

	private void draw(Window window, double time, double maxtime) {
		Graphics g = window.getBackbufferGraphics();

		g.drawImage( bg, 0, 0, null );
		scene.draw(g);
		
		g.setColor(Color.BLACK);
		g.setFont( Resource.getFont( 12 ) );
		
		int sec = (int) (maxtime - time);
		int min = sec/60;
		sec -= 60*min;
		
		g.drawString("Hatralevo ido: " + min + ":"+((sec/10)%10)+(sec%10),
				480, 30);
		
		for( int i = 0 ; i < jatekosok.length ; i++ )
		{
			Jatekos j = jatekosok[i];
			int y = 60 + i*34;
			g.drawString(j.getNev(), 480, y);
			g.drawString(""+j.getPont(), 600, y);
			g.drawString("Ragacs/olaj: "+j.getRagacsNum()+"/"+j.getOlajNum(), 480, y+15);
		}
		
		window.swapBuffers();		
    }

    /**
     * Pontok elkuldese a ranglistanak
     *
     */
    public void commitPontok() {
        for (Jatekos jatekos : jatekosok)
        	jatekos.commitPont(new Ranglista());
    }

}
