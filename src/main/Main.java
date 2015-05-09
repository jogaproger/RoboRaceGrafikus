package main;

import java.awt.event.KeyEvent;

import modell.Jatek;
import modell.Ranglista;
import gfx.KeyStates;
import java.awt.LayoutManager;
import java.awt.LayoutManager2;
import gfx.Window;
import java.io.File;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

public class Main {

    /**
     * Ennyi tick van egy masodperc alatt nem valtoztathato
     */
    static final int tickPerSecond = 30;

    Window window;
    int menupont;
    static final int MENUMERET = 3;

    public static int getTicksPerSecond() {
        return tickPerSecond;
    }

    Main() {
        window = new Window(640, 480);
        menupont = 0;
    }

    void draw() {

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
    }

    void ranglista() {
        	(new Ranglista()).megjelenit(window);
    }

    void jatek() {
        String palya;
        String[] nevek;
        int jatekosszam;
        try {
            File mappa = new File(System.getProperty("user.dir") + "\\palyak");
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

        new Main().run();
    }

}
