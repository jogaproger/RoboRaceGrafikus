package modell.jatekobj;

import gfx.ImageInstance;
import gfx.Resource;
import gfx.Scene;
import modell.palya.Cella;
import modell.visitors.JOVisitor;

public class Blokk extends JatekObj {
	
	ImageInstance img = new ImageInstance(Resource.getImage("kepek/blokk.png"));
	
	/**
	 * Default konstruktor
	 */
	public Blokk(){
		super(null);
	}
	
	/**
	 * Semmit nem csinal
	 */
	public void simulate() {
		if( cella == null )
			img.visible = false;
		else{
			img.visible = true;
			Cella.Pos p = cella.getPos();
			img.x = p.x;
			img.y = p.y;
			
		}
	}
	/**
	 * Megoli a robotot
	 */
	public void ralep(Robot r) {
		r.kill();
	}

	@Override
	public void accept(JOVisitor visitor) {
		visitor.visitBlokk(this);
	}

	@Override
	public String getAzon() {
		return "X ";
	}

	@Override
	public void addToScene(Scene scene) {
		scene.add(img, 3);
	}

	@Override
	public void info() {
		
	}

}
