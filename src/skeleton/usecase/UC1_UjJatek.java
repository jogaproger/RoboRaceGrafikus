package skeleton.usecase;

import modell.Jatek;
import skeleton.Logger;

/**
 * �j j�t�k use case megval�s�t�sa
 *
 */
public class UC1_UjJatek extends UseCase {

	@Override
	public void execute() {
		// Inicializ�l�s, ilyenkor m�g nem �runk ki
		Logger.setEnabled(false);
		Jatek jatek = new Jatek();	
		
		Logger.setEnabled(true);
		jatek.ujJatek("palya.txt", 1);
	
	}

	@Override
	public String getName() {
		return "�j j�t�k";
	}

}
