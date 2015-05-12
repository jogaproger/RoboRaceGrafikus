package modell.jatekobj;

import modell.palya.Cella;
import gfx.ImageInstance;
import gfx.Resource;
import gfx.Scene;


public class Ragacs extends Folt{
	
	ImageInstance img = new ImageInstance(Resource.getImage("kepek/ragacs.png"));
	/**
	 * Default konstruktor
	 */
	public Ragacs(){
	}
	
	/**
	 * Lelassitja a robotot
	 */
	public void ralep(Robot r) {
		r.lassit();		
		elet -= 25.1;	// 4 ugras utan igy elpusztul a ragacs
	}

	@Override
    public void simulate() {
        super.simulate();
        if( cella == null ){
        	img.visible = false;
        }
        else
        {
        	img.visible = true;
        	Cella.Pos p = cella.getPos();
        	img.x = p.x;
        	img.y = p.y;
        }
    }

	@Override
	public String getAzon() {
		return "R ";
	}

	@Override
	public void addToScene(Scene scene) {
		scene.add(img, 2);
	}
	
	

}
