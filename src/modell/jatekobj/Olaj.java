package modell.jatekobj;

import gfx.ImageInstance;
import gfx.Resource;
import gfx.Scene;
import main.Main;
import modell.palya.Cella;

public class Olaj extends Folt {

	ImageInstance img = new ImageInstance( Resource.getImage("kepek/olaj.png") );
	static final double eletMP = 15;
	
    /**
     * Default konstruktor
     */
    public Olaj() {
    }

    public void simulate() {
        elet -= 100/(eletMP*Main.getTicksPerSecond()) ;
        super.simulate();
        if( cella == null ){
        	img.visible = false;
        }
        else
        {
        	if( elet < 20 )
        		img.visible = (((int)elet) % 2 == 0);
        	else
        		img.visible = true;
        	
        	Cella.Pos p = cella.getPos();
        	img.x = p.x;
        	img.y = p.y;
        }
    }

    /**
     * Megcsusztatja a robotot
     */
    public void ralep(Robot r) {
        r.csusztat();
    }

    @Override
    public String getAzon() {
        return "O ";
    }

	@Override
	public void addToScene(Scene scene) {
		scene.add(img, 2);
	}

}
