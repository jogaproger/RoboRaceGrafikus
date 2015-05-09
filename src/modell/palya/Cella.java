package modell.palya;

import java.util.ArrayList;

import modell.jatekobj.JatekObj;
import modell.jatekobj.Robot;
import modell.visitors.JOVisitor;

public class Cella {

    private int x;
    private int y;
    private ArrayList<JatekObj> o;
    private Palya palya;
    
    /**
     * Egyszeru koordinatapar
     */
    public class Pos{ public int x, y; public Pos(int x, int y){this.x=x;this.y=y;} };
    
    public Pos getPos(){
    	return new Pos( 15 + x * 50, 15 + y * 50 );   	
    }

    Cella(Palya p, int x, int y) {

        this.x = x;
        this.y = y;
        o = new ArrayList<>();
        this.palya = p;

    }

    /**
     * Kovetkezo cella lekerdezese,sebessegtol fuggoen
     *
     * @param s Aktualis sebesseg
     * @return
     */
    public Cella getKov(Sebesseg s) {
        Cella c = palya.cellaxy(x + s.x, y + s.y);
        return c;
    }

    /**
     * Jatekobjektum hozzaadasa a cellahoz
     *
     * @param j Lerakando jatekobjektum
     */
    public void add(JatekObj j) {

    	if(!o.contains(j)){
    		o.add(j);
    		j.addToCella(this);
    	}

    }

    /**
     * Jatekobjektum eltavolitasa a cellarol
     *
     * @param j Eltavolitando jatekobjektum
     */
    public void remove(JatekObj j) {
        o.remove(j);
    }

    /**
     * Robot ralep a cellara
     *
     */
    public void ralep(Robot r) {
        for (JatekObj obj : o) {
            obj.ralep(r);
        }
    }

    @Override
    public String toString() {
        return "c[" + x + "," + y + "]";
    }

    public Cella keresFolt() {
        return palya.keresFolt(x, y);
    }

    public void accept(JOVisitor visitor) {
        for (JatekObj obj : o) {
            obj.accept(visitor);
        }
    }

    public String getAzon() {
        if (o.size() == 0) {
            return "  ";
        } else if (o.size() > 1) {
            return "..";

        } else {
            return o.get(0).getAzon();
        }
    }

	public boolean ures() {
		return o.isEmpty();
	}

}
