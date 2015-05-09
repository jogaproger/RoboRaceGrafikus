package modell.jatekobj;

import gfx.Resource;
import gfx.Scene;
import modell.palya.Cella;
import modell.visitors.TakaritoVisitor;

public class KisRobot extends AbstractRobot {

	
	public KisRobot() {
		super(null, Resource.getImage("kepek/kisrobot.png"));
	}

	@Override
	protected void erkezik(Cella c) {		
		c.add(this);
		cella = c;
		cel = forras = null;
	}

	@Override
	public void ralep(Robot r) {
		kill();		
	}
	
	@Override
	public void simulate(){
		super.simulate();
		if( allapot==RobotAllapot.ALLO ){
			TakaritoVisitor tv = new TakaritoVisitor();
			cella.accept( tv );
			
			if( !tv.takaritott )
			{
				Cella kov = cella.keresFolt();
				if( kov != null && kov != cella)
					ugrik( kov );		
			}
		}
	}
	@Override
	public String getAzon() {
		return "KR";
	}

	@Override
	public void addToScene(Scene scene) {
		scene.add(img, 3);
	}


}
