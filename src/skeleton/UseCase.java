package skeleton;

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
	 * Uj j�t�k kezd�se
	 *
	 */
	static private class UjJatekUC extends UseCase{
		@Override
		public void execute() {
			// Itt v�grehajthatunk egy use case-t			
		}

		@Override
		public String getName() {
			return "�j j�t�k kezd�se";
		}
	}
	
	
	/**
	 * Use Case defini�l�s�hoz sz�rmaztassuk le a UseCase oszt�lyt
	 * statikus inner classk�nt �s adjuk hozz� a t�mbh�z
	 * 
	 */
	static UseCase[] useCases = {
		new UjJatekUC()		
		
	};
	
	static void UseCaseListaz(){
		for( int i = 0 ; i < useCases.length ; i++ )	
			System.out.println( ""+(i+1)+". "+useCases[i].getName());
	}
	
	static boolean UseCaseVegrehajt(int i){
		i--;	// 1-t�l van sz�mozva, de nek�nk 0-t�l kell
		if( i < 0 || i >= useCases.length )
			return false;
		
		System.out.println(useCases[i].getName()+" v�grehajt�sa:");
		useCases[i].execute();
				
		return true;
	}
	
	
}
