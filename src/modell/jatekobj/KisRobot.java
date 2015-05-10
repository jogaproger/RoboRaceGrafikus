package modell.jatekobj;

import gfx.Resource;

import modell.palya.Cella;
import modell.visitors.TakaritoVisitor;

public class KisRobot extends AbstractRobot {

	/**
	 * KisRobot inicializalasa
	 */
	public KisRobot() {
		super( null, Resource.getImage("kepek/kisrobot.png"));
	}

	/**
	 * Kisrobot erkezese egy cellara
	 */
	@Override
	protected void erkezik(Cella c) {		
		c.add(this);
		cella = c;
		cel = forras = null;
	}
	/**
	 * Kisrobotra lepunk
	 */
	@Override
	public void ralep(Robot r) {
		kill();		
	}
	
	/**
	 * Kisrobot szimulacioja
	 */
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
	/**
	 * Azonosito lekerdezese
	 */
	@Override
	public String getAzon() {
		return "KR";
	}

	@Override
	protected int getLayer() {
		return 3;
	}


}
