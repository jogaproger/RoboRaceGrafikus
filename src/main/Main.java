package main;

import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import modell.Jatek;
import modell.Ranglista;
import gfx.KeyStates;

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
    static final int tickPerSecond = 30;
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
   
    
    public static int getTicksPerSecond() {
        return tickPerSecond;
    }

    Main() {
        window = new Window(WIDTH, HEIGHT);
        menupont = 0;
        menupic = new BufferedImage[MENUMERET][2];
		for( int i = 0 ; i < MENUMERET ; i++ )
    	{
    		String path = System.getProperty("user.dir") + "/kepek/";
    		try{
	    		menupic[i][0] = ImageIO.read(new File( path + "menu"+i+"off.png" ));
    		} catch(Exception ex) {
    			System.out.println( "skipping image..." );
    			menupic[i][0] = new BufferedImage(100, 10, BufferedImage.TYPE_INT_RGB);
    		}
    		try{
	    		menupic[i][1] = ImageIO.read(new File( path + "menu"+i+"on.png" ));
    		} catch(Exception ex) {
    			System.out.println( "skipping image..." );
    			menupic[i][1] = new BufferedImage(100, 10, BufferedImage.TYPE_INT_RGB);
    		}
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

                Jatek j = new Jatek(palya, nevek);
                j.futtat(120);

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
    		System.out.println(ex.getMessage());
    	}
    }

}
