import java.util.HashMap;
import java.util.Map;

public class Logger {

	static Map<Object, String> names = new HashMap<Object, String>();
	static final int tab = 10;	// Egy fuggvenyhivas ennyi behuzas
	static int behuzas = 0;
	
	/**
	 * Megadja az objektumhoz tartoz� nevet, amit ki�ratunk
	 * Ha nincs m�g neve, gy�rt egyet Oszt�lyn�vSorsz�m
	 * mint�ra
	 *
	 * @param o  Az objektum, amelynek ismerni szeretn�nk a nev�t
	 */
	static String resolveName( Object o ){
		if( o == null )
			return "null";
		
		String name = names.get(o);
		
		for( int i = 0 ; name == null ; i++ ){
			String next = o.getClass().getName().toLowerCase() + i;
			if( !names.containsValue(next) )
				name = next;
		}
		names.put( o, name );
		return name;
		
	}
	/**
	 * Be�ll�tja egy objektum nev�t
	 *
	 * @param o  Az objektum, amelynek be�ll�tjuk a nev�t
	 * @param name  Az objektum �j neve
	 */
	static void setName( Object o, Object owner, String name ){
		if( owner == null )
			names.put(o, name);
		else
			names.put(o, resolveName(owner)+"."+name);
	}

	/**
	 * Ki�rja a beh�z�snak megfelel� sz�m� space-et
	 * 
	 */
	public static void printTab(){
		for( int i = 0 ; i < behuzas ; i++ )	
			System.out.print(" ");
	}
	/**
	 * Egy tabnyi jobbra nyilat rajzol
	 * 
	 */
	public static void printArrowRight(){
		for( int i = 0 ; i < tab-1 ; i++ )	
			System.out.print("-");
		System.out.print(">");
	}
	/**
	 * Egy tabnyi balra nyilat rajzol
	 * 
	 */
	public static void printArrowLeft(){
		System.out.print("<");
		for( int i = 0 ; i < tab+3 ; i++ )	
			System.out.print("-");
	}
	
	/**
	 * Ki�ratja a h�v� met�dus megh�v�s�t stacktrace alapj�n
	 *
	 * @param obj h�v� met�dus this objektuma
	 * @param arguments argumentumlista, string eset�n �rt�k, objektum eset�n n�v �r�dik ki
	 */
	static void printCall( Object obj, Object... arguments ){
		printTab();
		printArrowRight();
		behuzas += tab;
		StackTraceElement call = Thread.currentThread().getStackTrace()[2];
		
		System.out.print( resolveName(obj) + "."+call.getMethodName()+"(");
		for( int i = 0 ; i < arguments.length; i++ )
		{
			if( i>0 )
				System.out.print(", ");
			Object param = arguments[i];
			
			if( param instanceof String )
				System.out.print( (String)param );
			else
				System.out.print( resolveName(param) );
			
		}
		
		System.out.println(")");
		
	}
	
	/**
	 * Ki�ratja a megadott sztringet az adott beh�z�s mellett
	 * @param str Ki�rand� sztring
	 *
	 */
	static void print(String str){
		printTab();
		System.out.println(str);
	}
	
	static void printCallEnd(){
		behuzas -= tab;
		printTab();
		printArrowLeft();	
		System.out.println();
	}
	
	
}
