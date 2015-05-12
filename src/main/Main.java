package main;

import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import modell.Jatek;
import modell.Ranglista;
import gfx.KeyStates;
import gfx.Resource;

import java.awt.Color;
import java.awt.Graphics;

import gfx.Window;

import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Main {

    /**
     * Ennyi tick van egy masodperc alatt nem valtoztathato
     */
    static final int ticksPerSecond = 30;
    /**
     * Ablak meretei
     */
    static final int WIDTH = 640, HEIGHT = 480;
    
    /**
     * Ennyi menupont van
     */
    static final int MENUMERET = 3;
    
    /**
     * Ablak, amelyikre rajzolunk
     */
    Window window;
    
    /**
     * Ez a menupont van kivalasztva [0, MENUMERET)
     */
    int menupont=0;
    
    /**
     * Menupontok kepei
     */
    private BufferedImage[][] menupic;
    
    /**
     * Idoszinkronizalashoz a legutolso szinkronizalas ideje
     */
    public static long lastTime = System.currentTimeMillis();
    /**
     * A legutolso synctime utan var, amig nem telik el adott ido
     */
    public static void SyncTime(){
    	long dt = 1000 / ticksPerSecond;
    	long target = lastTime + dt;

    	while( System.currentTimeMillis() < target-2 )
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
			}
    	
    	while( System.currentTimeMillis() < target )
    		;
    	
    	lastTime = System.currentTimeMillis();
    }
    
    /**
     * Masodpercenkenti tickek szama
     * @return Masodpercenkenti tickek szama
     */
    public static int getTicksPerSecond() {
        return ticksPerSecond;
    }

    /**
     * Hibauzenet kuldese
     * @param message hibauzenet
     */
    public static void ErrorMessage(String message) {
		JOptionPane.showMessageDialog(null, 
				message,
				"Hiba!", 
				JOptionPane.INFORMATION_MESSAGE);
	}

    Main() {
        window = new Window(WIDTH, HEIGHT);
        menupont = 0;
        menupic = new BufferedImage[MENUMERET][2];
		for( int i = 0 ; i < MENUMERET ; i++ )
    	{
    		String path = System.getProperty("user.dir") + "/kepek/";
	    	menupic[i][0] = Resource.getImage( path + "menu"+i+"off.png" );
	    	menupic[i][1] = Resource.getImage( path + "menu"+i+"on.png" );
    	}
            	
        
    }

    void draw() {

    	Graphics g = window.getBackbufferGraphics();
    	
    	g.setColor(Color.BLACK);
    	g.fillRect(0, 0, WIDTH, HEIGHT);
    	
    	for( int i = 0 ; i < MENUMERET ; i++ )
    	{
    		BufferedImage pic = 
    				menupic[i][menupont == i ? 1 : 0];
    		int y = 100 + i * 100;
    		int x = WIDTH / 2 - pic.getWidth()/2;
    		g.drawImage(pic, x, y, null);
    	}
    	window.swapBuffers();
    }

    void run() {

    	boolean exit = false;
    	KeyStates ks = window.getKeyStates();

    	while(!exit){
    		draw();
    		KeyStates.Change[] changes = ks.pullChanges();
    		
    		if( changes[KeyEvent.VK_UP] == KeyStates.Change.PUSHED )
    			menupont += MENUMERET-1;
    		if( changes[KeyEvent.VK_DOWN] == KeyStates.Change.PUSHED )
    			menupont ++;
    		menupont %= MENUMERET;
    		
    		if( changes[KeyEvent.VK_ENTER] == KeyStates.Change.PUSHED )
    		{
    			switch( menupont )
    			{
					case 0: 
						jatek(); 
						break;
					case 1: 
						ranglista(); 
						break;
					default: 
						exit = true; 
						break;
    			}
    			ks.pullChanges();
    		}
    		
    	}
    	window.close();
    	
    }

    void ranglista() {
        	(new Ranglista()).megjelenit(window);
    }
    /**
     * Jatek kezdese
     */
    void jatek() {
        String palya;
        String[] nevek;
        int jatekosszam;
        // Felugro ablakok
        try {
            File mappa = new File(System.getProperty("user.dir") + "/palyak");
            String palyak[] = mappa.list();
            palya = (String) JOptionPane.showInputDialog(null,
                    "Valassz palyat!",
                    "Palyavalaszto",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    palyak,
                    palyak[0]);

            if (palya != null) {
                String[] jatekos = {"2", "3", "4"};
                try {
                    jatekosszam = Integer.parseInt((String) JOptionPane.showInputDialog(null,
                            "Hany jatekos legyen?",
                            "Jatekosok szama",
                            JOptionPane.INFORMATION_MESSAGE,
                            null,
                            jatekos,
                            jatekos[0]));

                } catch (NumberFormatException e) {
                    jatekosszam = 2;
                }
                nevek = new String[jatekosszam];
                JPanel myPanel = new JPanel();
                myPanel.setLayout(new BoxLayout(myPanel, BoxLayout.PAGE_AXIS));
                for (int i = 0; i < jatekosszam; i++) {
                    myPanel.add(new JLabel("Jatekos" + (i + 1) + ":"));
                    myPanel.add(new JTextField("Jatekosnev" + (i + 1), 15));
                    myPanel.add(Box.createVerticalStrut(15));
                }

                JOptionPane.showConfirmDialog(null, myPanel,
                        "Add meg a jatekosok neveit!", JOptionPane.OK_CANCEL_OPTION);
                int num = 0;
                for (int i = 0; i < myPanel.getComponentCount(); i++) {
                    if (myPanel.getComponent(i) instanceof JTextField) {
                        nevek[num] = ((JTextField) myPanel.getComponent(i)).getText();
                        num++;
                    }
                }
                
                try{
	                Jatek j = new Jatek(palya, nevek);
	                j.futtat(120, window);
                }catch(Exception ex){
                	ErrorMessage( ex.getMessage() );
                	ex.printStackTrace();
                }

            }
        } catch (NullPointerException e) {
            System.err.println("Nem talalhato a megadott eleresi utvonal:" + System.getProperty("user.dir"));
        } catch (ArrayIndexOutOfBoundsException e) {
            System.err.println("Nem talaltam palyat a megadott helyen:" + System.getProperty("user.dir"));
        }

    }

	public static void main(String[] args) {
    	try{
    		(new Main()).run();
    	}catch( Exception ex )
    	{
    		ex.printStackTrace();
    		ErrorMessage(ex.getMessage());
    	}
    }

}
