package modell;

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

    int sorszam = 0;

    public Jatekos(String nev, Jatek jatek, int sorszam) {
    	// Nev beallitasa
        this.nev = nev;
        this.sorszam = sorszam;
        robot = new Robot(this, jatek);
        pontszam = 0;

        // A jateknak is tudnia kell a robotunkrol
        jatek.addRobot(robot);

    }

    public void addPont(int p) {
    	pontszam += p;
    }
    
    public void commitPont(Ranglista r) {
    	r.commit(nev, pontszam);
    }

    public void iranyit(Irany i) {
        robot.iranyit(i);
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
