package skeleton;

/**
 * Absztrakt UseCase oszt�ly, amely egy UseCase fel�let�t adja meg
 * Strategy minta
 * 
 */
public abstract class UseCase {
	String name;
	
	/** Usecase v�grehajt�sa */
	abstract public void run();
	
	/** Usecase v�grehajt�sa */
	abstract public String getName();
	
	/**
	 * Uj j�t�k kezd�se
	 *
	 */
	private class UjJatekUC extends UseCase{
		@Override
		public void run() {
			// Itt v�grehajthatunk egy use case-t			
		}

		@Override
		public String getName() {
			return "�j j�t�k kezd�se";
		}
	}
	
	
	/**
	 * Use Case defini�l�s�hoz sz�rmaztassuk le a UseCase oszt�lyt
	 * inner classk�nt �s adjuk hozz� a t�mbh�z
	 * 
	 */
	static UseCase[] useCases = {
		new UjJatekUC()			
	};
	
	static void UseCaseListaz(){
		for( int i = 0 ; i < useCases.length ; i++ )	
			System.out.println( ""+(i+1)+". "+useCases[i].getName());
	}
	
	
	
}
