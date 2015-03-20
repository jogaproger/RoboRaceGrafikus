package skeleton;


public class Skeleton {

	
	public static void main( String[] args )
	{
		try{
			new Skeleton().A();
		}
		catch( Exception e )
		{
			e.printStackTrace();	
		}
	}
	
	void B(){
		Logger.printCall(this, "random string as B's param");
		Logger.printCallEnd();		
	}
	
	void C(){
		Logger.printCall(this);
		
		D(this, 3);
		
		Logger.printCallEnd();		
	}
	
	void D( Skeleton param, int i )
	{
		// Mindig thissel kezd�dik
		// az els� param�ter egy Skeletonobjektum, aminek neve van ez�rt sim�n �tadjuk
		// A m�sodik egy sima �rt�k, ezt stringg� konvert�ljuk �s �gy adjuk �t
		Logger.printCall(this, param, ""+i );
		
		Logger.print("Egy�b sz�veg ki�r�sa D-b�l");
		
		Logger.printCallEnd();
	}
	
	void A(){
		Logger.printCall(this);
		
		B();
		C();
		
		Logger.printCallEnd();		
	}
	
}
