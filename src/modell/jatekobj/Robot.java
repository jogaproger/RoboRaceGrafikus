package modell.jatekobj;


import modell.Jatek;
import modell.Jatekos;
import modell.palya.Cella;
import modell.palya.Irany;
import modell.palya.Sebesseg;

/**
 * Robot osztaly
 *
 */
public class Robot extends AbstractRobot {

    /**
     * Ragacskeszlet
     */
    private int ragacsnum=3;
    /**
     * Olajkeszlet
     */
    private int olajnum=3;
    /**
     * IRanyithatatlan-e a robot
     */
    private boolean csusztatott=false;

    /**
     * Robothoz tartozo jatekos
     */
    private Jatekos jatekos;

    /**
     * Default konstruktor
     */
    public Robot(Jatekos jatekos, Jatek jatek) {
        super(jatek);

        ragacsnum = olajnum = 3;
        seb = new Sebesseg();
        this.jatekos = jatekos;
    }

    /**
     * Megadja mi tortenik, ha robot lep rank
     */
    public void ralep(Robot r) {

        if (r.seb.getNagysag() > seb.getNagysag()) {
            r.seb = Sebesseg.Sum(seb, r.seb);
            kill();
        } else {
            seb = Sebesseg.Sum(seb, r.seb);
            r.kill();
        }

    }

    /**
     * Modositja a robot allapotat ha lehetseges
     *
     * @param i Az adott irany
     */
    public void iranyit(Irany i) {

        if (allapot == RobotAllapot.ALLO && !csusztatott) 
            seb.modosit(i);
        // Valodi jatekban csak egyszer tortenik ez a fuggvenyhivas
        csusztatott = false;
    }

    /**
     * Ragacs lerakasa
     */
    public void lerakRagacs() {
    	
    	if( allapot == RobotAllapot.ALLO && ragacsnum>0 )
        {
    		ragacsnum--;
    		JatekObj jo = new Ragacs();
    		cella.add(jo);
    		jatek.addJatekObj(jo);
        }
    	if( ragacsnum <= 0 )
    		System.out.println("Nincs eleg ragacs");

    }

    /**
     * Olaj lerakasa
     */
    public void lerakOlaj() {

    	if( allapot == RobotAllapot.ALLO && olajnum>0 )
        {
    		olajnum--;
    		JatekObj jo = new Olaj();
    		cella.add(jo);
    		jatek.addJatekObj(jo);
        }
    	if( olajnum <= 0 )
    		System.out.println("Nincs eleg olaj");
    }

    /**
     * Megcsusztatja a robotot, tehat az nem tudja majd megvaltoztatni a
     * sebesseget a kovetkezo lehetosegnel
     */
    public void csusztat() {
    	csusztatott=true;
    }

    /**
     * Robot lassitasa, azaz sebessegenek megfelezese
     */
    public void lassit() {
        seb.felez();
    }

    /**
     * Palyakirajzolashoz azonosito lekerdezese
     */
    @Override
    public String getAzon() {
        return "J" + jatekos.getSorszam();
    }
    /**
     * Megh�v�dik, amikor le�rkezik a robot
     */
    @Override
    protected void erkezik(Cella c) {
    	jatekos.addPont((int) (100 * seb.getNagysag()));
        cella = c;
        forras = cel = null;
        c.ralep(this);
        if( allapot != RobotAllapot.HALOTT )
        	c.add(this);
    }
}
