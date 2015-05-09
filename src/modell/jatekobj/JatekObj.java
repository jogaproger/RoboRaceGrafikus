package modell.jatekobj;

import gfx.Scene;
import modell.Jatek;
import modell.palya.Cella;
import modell.visitors.JOVisitor;

/**
 * Absztrakt jatekobjektum
 */
public abstract class JatekObj {

    protected Cella cella;
    protected Jatek jatek;
    
    public JatekObj( Jatek jatek ){
    	this.jatek=jatek;
    }

    /**
     * Pillanatnyi muveletek vegrehajtasa
     */
    public abstract void simulate();

    /**
     * Megadja mit csinal, ha robot lep az objektumra
     *
     * @param r A robot, amely ralep
     */
    public abstract void ralep(Robot r);

    /**
     * Hozzaadjuk egy cellahoz
     */
    public void addToCella(Cella c) {
        cella = c;
        c.add(this);
    }
	/**
	 * Visitor fogadasa
	 * @param visitor
	 */
    public abstract void accept(JOVisitor visitor);

    public abstract String getAzon();

    public abstract void info();
	/**
	 * Hozzaadas a jelenethez
	 * @param scene A jelenet
	 */
	public abstract void addToScene(Scene scene);

}
