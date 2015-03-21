package skeleton.usecase;

import modell.Jatek;
import skeleton.Logger;

public class UC4_JatekKezdes extends UseCase {

	@Override
	public void execute() {
		// Inicializ�l�s, ilyenkor m�g nem �runk ki
		Logger.setEnabled(false);
		Jatek jatek = new Jatek();	
		
		Logger.setMaxDepth(1);
		Logger.setEnabled(true);
		jatek.ujJatek("palya.txt", 1);
		jatek.simulate();
		
		Logger.setMaxDepth(100);
	}

	@Override
	public String getName() {
		return "J�t�k kezd�s";
	}

}
