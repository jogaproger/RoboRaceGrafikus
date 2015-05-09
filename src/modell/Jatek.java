package modell;

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
     * Jatekhoz tartozo ranglista
     */
    private Ranglista ranglista;
    /**
     * Jatekosok tombje
     */
    private Jatekos[] jatekosok;
    /**
     * Jateevo objektumok listaja
     */
    private ArrayList<JatekObj> objects;
    
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

    public Jatek(String palyafajl, String[] jatekosnevek ) {
        ujJatek(palyafajl, jatekosnevek);
    }

    public void ujJatek(String palyafajl, String[] jatekosnevek ) {

        jatekosok = new Jatekos[jatekosnevek.length];
        objects = new ArrayList<JatekObj>();
        kezdoIndex = 0;

        palya = new Palya(this);
        if (palyafajl == null || !palya.betolt(palyafajl)) {
            palya.szerkeszt();
        }

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
    public void futtat(double jatekidoSec) {
    	try{
	        System.out.println("Jatek kezdes:");
	        int skipnum = 0;
	
	        for (
        		int tick = 0;
        		!endflag && tick < jatekidoSec * Main.getTicksPerSecond(); 
        		tick++) 
	        {
	        	System.out.println(tick + ". kor");
	            if ( --skipnum <= 0) {
	                minden_jatekosra:
	                for (Jatekos jatekos : jatekosok) {
	                    String line;
	                    String cmd[] = null;
	                    System.out.println();
	
	                    // Jatekosonkent adhatunk akarhany parancsot:
	                 /*   while (
	                    		(line = Input.getLine(jatekos.getSorszam() + ".jatekos>")) 
	                    			!= null) {
	                        cmd = line.toUpperCase().split(" ");
	                      
	                        if (cmd[0].equals("IRANYIT")) {
	                            if (!parancsIranyit(cmd, jatekos)) {
	                                System.out.println("Iranyit - Ervenytelen parameter");
	                            }
	                        } 
	                        else if (cmd[0].equals("NEXT")) 
	                        {
	                            break;
	                        } 	                       
	                        else if (cmd[0].equals("KILEP")) 
	                        {
	                            kilepes();
	                            break minden_jatekosra;
	                        } 
	                        else if (cmd[0].equals("INFO")) 
	                        {
	                        	if(!parancsInfo( cmd ))
	                        		System.out.println("Info - Ervenytelen parameter");
	                        } 
	                        else if (cmd[0].equals("LERAK")) 
	                        {
	                            if (!parancsLerak(cmd, jatekos)) 
	                                System.out.println("Lerak - Ervenytelen parameter");
	                        }
	                        else if (cmd[0].equals("KISROBOT")) 
	                        {
	                            if (!parancsKisRobot(cmd)) 
	                                System.out.println("Kisrobot - Ervenytelen parameter");
	                        } 
	                        else if (cmd[0].equals("SKIP")) 
	                        {
	                            skipnum = parancsSkip(cmd);
	                            break minden_jatekosra;
	                        } 
	                        else
	                        {
	                        	System.out.println("Ervenytelen parancs");
	                        }
	                    }*/
	                }
	            }
	            // Minden jatekobjektum viselkedesenek megvalositasa
	            for (JatekObj jobj : objects) {
	                jobj.simulate();
	            }
	        }

            for (Jatekos jatekos : jatekosok)
            	jatekos.commitPont(new Ranglista());
	       
    	}catch(Exception ex){
    		ex.printStackTrace();    		
    	}
    }

    private boolean parancsInfo(String[] cmd) {

        palya.info();
        for( JatekObj jo : this.objects)
        	jo.info();
        return true;
		
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

	public boolean parancsLerak(String[] cmd, Jatekos jatekos) {
        if (cmd.length < 2) {
            return false;
        }

        if (cmd[1].equals("OLAJ")) {
            jatekos.lerakOlaj();
        } else if (cmd[1].equals("RAGACS")) {
            jatekos.lerakRagacs();
        } else {
            return false;
        }

        return true;
    }

    private int parancsSkip(String[] cmd) {
        int ret = 1;
        try {
            ret = Integer.parseInt(cmd[1]);
        } catch (Exception ex) {
        }

        return ret < 1 ? 1: ret;
    }

    private boolean parancsIranyit(String[] cmd, Jatekos jatekos) {
        if (cmd.length < 2) {
            return false;
        }

        for (Irany irany : Irany.values()) {
            if (irany.toString().toUpperCase().equals(cmd[1])) {
                jatekos.iranyit(irany);
                return true;
            }
        }

        if (cmd.length >= 3) {
            try {
                int x = Integer.parseInt(cmd[1]);
                int y = Integer.parseInt(cmd[2]);

                jatekos.iranyit(new Sebesseg(x, y));
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
