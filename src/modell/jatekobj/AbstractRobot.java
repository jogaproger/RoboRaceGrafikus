package modell.jatekobj;

import java.awt.image.BufferedImage;

import gfx.ImageInstance;
import main.Main;
import modell.Jatek;
import modell.palya.Cella;
import modell.palya.Sebesseg;
import modell.visitors.JOVisitor;

public abstract class AbstractRobot extends JatekObj {

	protected ImageInstance img;
    /**
     * Robot allapotat leiro belso enumeracio
     */
    protected enum RobotAllapot {
        HALOTT, ALLO, UGRO
    };
    /**
     * Robot allapota
     */
    protected RobotAllapot allapot;

    /**
     * Ugras eseten ahonnan elugrunk
     */
    protected Cella forras;
    /**
     * Ugras eseten ahova erkezni fogunk
     */
    protected Cella cel;
    /**
     * mennyi ideje van a robot a levegoben
     */
    protected int ugrasidoTick;

    /**
     * Robot sebessege
     */
    Sebesseg seb;

    /**
     * Robot ugrasi ideje
     */
    double totalUgrasIdoSec = 1;
    

    public AbstractRobot(Jatek jatek, BufferedImage bimg) {
    	super(jatek);
    	img = new ImageInstance(bimg);
    	img.visible = true;
        seb = new Sebesseg();
        allapot = RobotAllapot.ALLO;
    }
    
    public AbstractRobot(BufferedImage bimg) {
    	super(null);
    	this.img = new ImageInstance(bimg);
        seb = new Sebesseg();
        allapot = RobotAllapot.ALLO;
    }

    /**
     * Robot elpusztitasa
     */
    public void kill() {
    	img.visible = false;
        allapot = RobotAllapot.HALOTT;
    }

    protected void ugrik(Sebesseg seb) {
        ugrik(cella.getKov(seb));
    }

    protected void ugrik(Cella celcella) {
        cella.remove(this);
        forras = cella;
        cel = celcella;
        allapot = RobotAllapot.UGRO;
        ugrasidoTick = 0;
    }

    /**
     * Robot pillanatnyi mukodesenek vegrehajtasa
     */
    public void simulate() {
    	
    	Cella.Pos pos = null, pos2 = null;
    	
        switch (allapot) {
            case ALLO:
            	pos = cella.getPos();
            	img.x = pos.x;
            	img.y = pos.y;
                if (!seb.isNulla()) {
                    ugrik(seb);
                }
                break;
            case HALOTT:
            	if( cella != null )
            		cella.remove(this);
            	cella = null;
                break;
            case UGRO:
                ugrasidoTick++;
                double t = (double)ugrasidoTick / ( totalUgrasIdoSec * Main.getTicksPerSecond());
                pos = forras.getPos();
                pos2 = cel.getPos();
                img.x = (int) (pos.x * (1-t) + pos2.x * t);
                img.y = (int) (pos.y * (1-t) + pos2.y * t);
                
                if (ugrasidoTick > totalUgrasIdoSec * Main.getTicksPerSecond()) {
                    erkezik(cel);
                    if( allapot == RobotAllapot.UGRO )
                    	allapot = RobotAllapot.ALLO;
                }

                break;
            default:
                break;

        }
        
        
        
    }

	/**
     * Akkor hivodik meg, amikor az abstract robot leer a cellara
     *
     * @param c Celcella
     */
    protected abstract void erkezik(Cella c);

    /**
     * Visitor fogadasa
     */
    public void accept(JOVisitor visitor) {
        visitor.visitAbstractRobot(this);
    }

    public void info() {
    	System.out.print( getClass().getSimpleName() );
        switch (allapot) {
            case ALLO:
                System.out.println(" allo: "+ cella.toString());
                break;
            case UGRO:
                System.out.print(" ugro: ");
                System.out.print( forras.toString() + " -> ");
                System.out.println( cel.toString() + ": " 
                + ugrasidoTick + "/"
                +	totalUgrasIdoSec*Main.getTicksPerSecond());
                break;
            case HALOTT:
                System.out.println("halott");
                break;
        }
    }
   
    
}
