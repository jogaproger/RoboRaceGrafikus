package skeleton.usecase;

import skeleton.Logger;

/**
 * Absztrakt UseCase oszt�ly, amely egy UseCase fel�let�t adja meg
 * Strategy minta
 * 
 */
public abstract class UseCase {
	String name;
	
	/** Usecase v�grehajt�sa */
	abstract public void execute();
	
	/** Usecase v�grehajt�sa */
	abstract public String getName();
		
	
	/**
	 * Use Case defini�l�s�hoz sz�rmaztassuk le a UseCase oszt�lyt
	 * �rjunk met�dust neki, amely v�grehajtja, illetve lek�rdi a nev�t
	 * majd adjuk hozz� ezen t�mbh�z:
	 * Tipp: El�bb hozz�adni a t�mbh�z, majd legener�ltatni eclipse-szel
	 */
	static UseCase[] useCases = {
		new UC1_UjJatek(),
		new UC2_Ranglista(),
		new UC3_RobotVezerles(),
		new UC4_Kilepes(),
		new UC5_JatekVege(),
		new UC6_OlajfoltGatol(),
		new UC7_RagacsLassit(),
		new UC8_BlokkElpusztit()		
	};
	
	public static void Listaz(){
		for( int i = 0 ; i < useCases.length ; i++ )	
			System.out.println( ""+(i+1)+". "+useCases[i].getName());
	}
	
	public static boolean Vegrehajt(int i){
		i--;	// 1-t�l van sz�mozva, de nek�nk 0-t�l kell
		if( i < 0 || i >= useCases.length )
			return false;
		
		System.out.println(useCases[i].getName()+" v�grehajt�sa:");
		useCases[i].execute();
		Logger.releaseNames();	// Use Case �ltal foglalt nevek m�r nem maradhatnak
				
		return true;
	}
	
	
}
