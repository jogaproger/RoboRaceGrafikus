package modell;

import gfx.Resource;
import gfx.Window;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Scanner;

/**
 * Ranglista osztaly
 */
public class Ranglista {

    /**
     * Nev es hozza tartozo pont tarolasa
     */
    static class NevPont {

        public String nev;
        public int pont;

        /**
         * Paros inicializalasa
         *
         * @param nev Jatekos neve
         * @param pont Jatekos pontszama
         */
        public NevPont(String nev, int pont) {
            this.nev = nev;
            this.pont = pont;
        }
    }

    private static final int count = 10;
    private static final String adatfajl = "hs.dat";
    private static final String splitter = "`";

    NevPont lista[];
   
    public void clear(){
    	lista = new NevPont[count];
        for (int i = 0; i < lista.length; i++) {
            lista[i] = new NevPont("", 0);
        }  	
    }

    public Ranglista() {
	    clear();
    }

    public void commit(String nev, int pont) {
    	load();
    	int i = 0;
    	for( int x = 0 ; x < count ; x++ )
    		if( lista[x].pont >= pont )
    			i = x+1;
    	
    	if( i < count ){
	    	for( int x = count-1 ; x > i ; x-- )
	    		lista[x] = lista[x-1];    		
	    	
	    	lista[i] = new NevPont( nev, pont );
    	}
    	save();
    }

    private void save() {
		File file = new File(System.getProperty("user.dir") + "/" + adatfajl);
		FileOutputStream fos = null;
		BufferedWriter bw = null;
		try {
			fos = new FileOutputStream(file);
			 
			bw = new BufferedWriter(new OutputStreamWriter(fos));
		 
			for (int i = 0; i < count; i++) {
				bw.write( lista[i].pont + splitter + lista[i].nev );
				bw.newLine();
			}
		
		} catch (FileNotFoundException e) {
			System.out.println("Nem talalhato: " + adatfajl);
		} catch (Exception e) {
			System.out.println("Nem talalhato: " + adatfajl);
		} finally{
			if( bw != null )
				try {
					bw.close();
				} catch (IOException e) {
				}
			else if( fos != null )
				try {
					fos.close();
				} catch (IOException e) {
				}
		}
	}

	private void load() {
		clear();
		File file = new File(System.getProperty("user.dir") + "/" + adatfajl);
        Scanner scanner = null;
        try {
            scanner = new Scanner(file);
            for( int i = 0 ; i < count && scanner.hasNextLine() ; i++ )
            {
            	 String[] line = scanner.nextLine().split(splitter);           	
            	 String nev = line[1];
            	 int pont = Integer.parseInt(line[0]);
            	 lista[i] = new NevPont(nev, pont);
            }
        }catch(Exception ex){
        }
        finally{
        	if( scanner != null )
        		scanner.close();
        }
	}

	public void megjelenit(Window window) {
		load();
    	Graphics g = window.getBackbufferGraphics();
    	
    	g.setColor(Color.BLACK);
    	g.fillRect(0, 0, window.getWidth(), window.getWidth());
    	
    	g.setColor(Color.WHITE);
    	g.setFont(Resource.getFont(20));
    	
    	for( int i = 0 ; i < count ; i++ )
    	{
    		int y = 40 + i*40;
    		g.drawString(""+(i+1)+".:"+lista[i].nev, 100, y);
    		g.drawString(""+lista[i].pont, 400, y);
    	}
    	window.swapBuffers();
    	while( !(
    			window.getKeyStates().getKeyState(KeyEvent.VK_ENTER) || 
    			window.getKeyStates().getKeyState(KeyEvent.VK_ESCAPE)
    			))
    		;
    }
}
