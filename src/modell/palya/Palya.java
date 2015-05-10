package modell.palya;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;
import java.util.Scanner;
import java.util.logging.Level;

import modell.Jatek;
import modell.jatekobj.JatekObj;
import modell.jatekobj.KisRobot;
import modell.jatekobj.Ragacs;
import modell.jatekobj.Blokk;
import modell.jatekobj.Olaj;
import modell.visitors.Utkereso;

/**
 * Palya osztaly
 */
public class Palya {

    /**
     * Palya szelessege, magassaga
     */
    private int szelesseg, magassag;
    /**
     * Palyakat alkoto cellak
     */
    private Cella[][] cellak;
    
    private int[][] seged;
    /**
     * Robot kezdopoziciojat tarolja
     */
    private Cella[] robotkezdo;
    /** Palyahoz tartozo jatek */
	private Jatek jatek;

    /**
     * Palya letrehozasa
     *
     * @param j A jatek, amelyhez a palya tartozik
     *
     */
    public Palya(Jatek j) {
    	jatek = j;
    }
    
    void create(int x, int y, JatekObj obj){
    	jatek.addJatekObj(obj);
    	cellak[x][y].add(obj);
    }

    /**
     * Palya betoltese fajlbol
     *
     * @param fajl Fajl neve kiterjesztessel egyutt a palyak mappabol
     * @return Sikeres volt-e a beolvasas
     */
    public boolean betolt(String fajl) {
        File file = new File(System.getProperty("user.dir") + "/palyak/" + fajl );
        Scanner scanner = null;
        try {
            scanner = new Scanner(file);
            if (scanner.hasNextLine()) {
                String[] palyameret = scanner.nextLine().split(" ");
                this.szelesseg = Integer.parseInt(palyameret[0]);
                this.magassag = Integer.parseInt(palyameret[1]);
                generalCella(this.szelesseg, this.magassag);

                System.out.println("Palya: " + this.szelesseg + this.magassag);
            }

            for (int y = 0; y < magassag && scanner.hasNext(); y++) {
                String palyasor = scanner.next();
                for (int x = 0; x < palyasor.length() && x < szelesseg; x++) {
                    char palyaelem = palyasor.charAt(x);
                    switch (palyaelem) {
                        case 'R':
                            create(x,y,new Ragacs());
                            break;
                        case 'X':
                        	create(x,y,new Blokk());
                            break;
                        case 'O':
                        	create(x,y,new Olaj());

                            break;
                        case '-':
                            break;
                        default:
                            if (palyaelem < '1' || palyaelem > '9') {
                                break;
                            }
                            try {
                                int robotnum = palyaelem - '0' - 1;
                                this.robotkezdo[robotnum] = cellak[x][y];
                            } catch (Exception ex) {
                            	ex.printStackTrace();
                                System.out.println("Maximum 4 jatekos lehet!");
                            }
                            break;
                    }
                }
            }

        } catch (FileNotFoundException ex) {
            return false;
        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }
        return true;
    }

    /**
     * i. kezdocella lekerdezese
     *
     * @param i Sorszam
     * @return Valamelyik sarokcella i-tol fuggoen
     */
    public Cella getStartCell(int i) {
        return this.robotkezdo[i];
    }

    /**
     * cella lekerdese az X,Y helyen
     *
     * @param x X koordinata
     * @param y Y koordinata
     * @return az adott cella, Blokkot tartalmazo cella ha nincs a palyan
     */
    Cella cellaxy(int x, int y) {
        // Ha palyan kivul esunk
        if (x < 0 || y < 0 || x >= szelesseg || y >= magassag) {
            Cella ret = new Cella(this, x, y);
            ret.add(new Blokk());	// Ez pusztitja el a kieso robotot
            return ret;
        } else {
            return cellak[x][y];
        }
    }

    /**
     * Palyaszerkesztes vegrehajtasa
     *
     */
    public void szerkeszt() {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String command = "";

        int x;
        int y;
        // TODO teszteles
        while (!"kesz".equals(command)) {
            System.out.print("palya>:");
            try {
                command = in.readLine();
                String[] args = command.toUpperCase().split(" ");
                if (args[0].equals("PALYA")) {
                    this.szelesseg = Integer.parseInt(args[1]);
                    this.magassag = Integer.parseInt(args[2]);
                    generalCella(this.szelesseg, this.magassag);
                } else if (args[0].equals("KEZD")) {
                    int robotszam = Integer.parseInt(args[1]);
                    x = Integer.parseInt(args[2]);
                    y = Integer.parseInt(args[3]);
                    if (x >= 0 && y >= 0 && x < szelesseg && y < magassag) {
                        try {
                            this.robotkezdo[robotszam - 1] = this.cellaxy(x, y);
                        } catch (java.lang.ArrayIndexOutOfBoundsException ex) {
                            System.out.println("Maximum 4 jatekos megengedett!");
                        }
                    } else {
                        System.out.println("Nincsen ilyen indexu cella!");
                    }
                } else if (args[0].equals("RAGACS")) {
                    x = Integer.parseInt(args[1]);
                    y = Integer.parseInt(args[2]);
                    create(x,y,new Ragacs());
                } else if (args[0].equals("OLAJ")) {
                    x = Integer.parseInt(args[1]);
                    y = Integer.parseInt(args[2]);
                    create(x,y,new Olaj());
                } else if (args[0].equals("BLOCK") || args[0].equals("BLOKK")) {
                    x = Integer.parseInt(args[1]);
                    y = Integer.parseInt(args[2]);
                    create(x,y,new Blokk());
                } else if (args[0].equals("INFO")) {
                    info();
                } else if (args[0].equals("KESZ")) {
                    break;
                } else {
                    System.out.println("Ervenytelen parancs");
                }
            } catch (IOException ex) {
                java.util.logging.Logger.getLogger(Palya.class.getName()).log(Level.SEVERE, null, ex);
            } catch (java.lang.ArrayIndexOutOfBoundsException ex) {
                System.out.println("Tul keves parameter!");
            } catch (java.lang.NumberFormatException ex) {
                System.out.println("Nem szamot kaptam!");
            }

        }

    }

    Cella getkov(Cella c, Sebesseg s) {
        return c.getKov(s);
    }
    
   	/**
   	 * Megadja azt a cellát, amerre a KisRobot foltot talál
   	 * @param x0 KisRobot x koordinátája
   	 * @param y0 KisRobot y koordinátája
   	 * @return
   	 */
    public Cella keresFolt(int x0, int y0) {
    	if( x0 < 0 || y0 < 0 || x0 >= szelesseg || y0 >= magassag )
    		return null;
    	
    	// A segedben eltarolunk egy szegelyt is
    	for( int x = -1 ; x < szelesseg+1 ; x++ )
    		for( int y = -1 ; y < magassag+1 ; y++ )
    			seged[x+1][y+1] = 
    				(x < 0 || y < 0 || x >= szelesseg || y >= magassag)
    					? -1
    					: 0;	// Minel kisebb, annal tavolibb
    		
    	Utkereso kereso = new Utkereso();
    	for( int i = 0 ; i < szelesseg ; i++ )
    		for( int j = 0 ; j < magassag ; j++ )
    		{
    			int x = i+1, y = j+1;
    			kereso.reset();
    			cellak[i][j].accept(kereso);
    			if( kereso.blokkol )
    				seged[x][y] = -1;
    			else if( kereso.folt )
    				seged[x][y] = szelesseg+magassag+1;
    		}
    	
    	int iter = szelesseg > magassag ? szelesseg : magassag;
    	for( int k = 0 ; k < iter ; k++ )
	    	for( int i = 0 ; i < szelesseg ; i++ )
	    		for( int j = 0 ; j < magassag ; j++ )
	    		{
	    			int x = i+1, y = j+1;
	    			
	    			if( seged[x][y] < 0 )
	    				continue;
	    			
	    			for( int dx = -1 ; dx <= 1 ; dx++ )
	    				for( int dy = -1 ; dy <= 1 ; dy++ )
			    			if( seged[x][y] < seged[x+dx][y+dy] -1 )
			    				seged[x][y] = seged[x+dx][y+dy] -1;
	    		}
    	
    	x0++;
    	y0++;
    	int xret = x0, yret=y0;
    	
    	if( seged[xret][yret] < seged[x0+1][y0+0] )
			{ xret = x0+1;	yret = y0+0;	}    	
    	if( seged[xret][yret] < seged[x0+0][y0+1] )
    		{ xret = x0+0;	yret = y0+1;	}    	
    	if( seged[xret][yret] < seged[x0-1][y0+0] )
			{ xret = x0-1;	yret = y0+0;	}    	
    	if( seged[xret][yret] < seged[x0+0][y0-1] )
    		{ xret = x0+0;	yret = y0-1;	}    	
    	
    	if( seged[xret][yret] < seged[x0+1][y0+1] )
			{ xret = x0+1;	yret = y0+1;	}    	
    	if( seged[xret][yret] < seged[x0+1][y0-1] )
    		{ xret = x0+1;	yret = y0-1;	}    	
    	if( seged[xret][yret] < seged[x0-1][y0-1] )
			{ xret = x0-1;	yret = y0-1;	}    	
    	if( seged[xret][yret] < seged[x0-1][y0+1] )
    		{ xret = x0-1;	yret = y0+1;	}    	   	
    	
    	xret--;
    	yret--;
        return cellak[xret][yret];
    }

    public void info() {
        String elvalaszto = "";
        for (int num = 0; num < (this.szelesseg * 3) + 1; num++) {
            elvalaszto += "-";
        }

        for (int y = 0; y < this.magassag; y++) {
            System.out.println(elvalaszto);
            String line = "|";
            for (int x = 0; x < this.szelesseg; x++) {
                line += this.cellak[x][y].getAzon() + "|";
            }

            System.out.println(line);
        }
        System.out.println(elvalaszto);

    }

    private void generalCella(int sz, int m) {
    	szelesseg = sz;
    	magassag = m;
    	
    	seged = new int[sz+2][m+2];
        cellak = new Cella[sz][m];
        
        for (int y = 0; y < m; y++) 
            for (int x = 0; x < sz; x++) 
                cellak[x][y] = new Cella(this, x, y);

        robotkezdo = new Cella[4];
        robotkezdo[0] = cellak[0][0];
        robotkezdo[1] = cellak[sz - 1][m - 1];
        robotkezdo[2] = cellak[sz - 1][0];
        robotkezdo[3] = cellak[0][m - 1];

    }


	public void kisrobot() {
		Random random = new Random();
		
		for( int j = 0 ; j < 100 ; j++ )
		{
			int x = random.nextInt(szelesseg);
			int y = random.nextInt(magassag);
			if( cellak[x][y].ures() ){
				kisrobot(x,y);
				return;
			}
		}
	}
    /**
     * kisRobotot tesz az adott koordinatakra
     * @param x x koordinata
     * @param y y koordinata
     * @return palyan vannak-e a koordinatak
     */
	public boolean kisrobot(int x, int y) {
		if( x < 0 || y < 0 || x >= szelesseg || y >= magassag )
			return false;
		
		System.out.println("Kisrobot letrehozva");		
		create(x,y,new KisRobot());
		return true;
	}
}
