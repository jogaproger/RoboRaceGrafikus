package skeleton;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class Logger {

	private static Map<Object, String> names = new HashMap<Object, String>();
	private static final int tab = 15;		// Egy fuggvenyhivas ennyi behuzas
	private static int behuzas = 0;
	private static boolean enabled = true;	// Enged�lyezve van-e?
	private static int maxDepth = 100;
	public static int depth = 0;

	
	
	/**
	 * Ki�r�s m�lys�g�nek meghat�roz�sa, mennyire menj�nk bele als� szint�
	 * f�ggv�nyh�v�sokba
	 * @depth Ennyi db f�ggv�nyh�v�s m�lyre megy�nk
	 */	
	public static void setMaxDepth(int depth){
		maxDepth = depth;
	}
	
	/**
	 * N�vt�r elenged�se, mert a) nem haszn�ljuk a neveket
	 * b) Nem akarjuk hogy a n�vt�r miatt a haszn�latlan objektumok
	 * ne szabaduljanak fel
	 */
	public static void releaseNames(){
		names = new HashMap<Object, String>();
	}
	
	
	/**
	 * Enged�lyezi/letiltja a ki�r�st �s a hozz� tartoz� funkci�kat
	 * 
	 * @param value ha true, enged�lyezi, ha false, letiltja
	 */
	public static void setEnabled( boolean value ){
		enabled = value;		
	}
	
	/**
	 * Megadja az objektumhoz tartoz� nevet, amit ki�ratunk
	 * Ha nincs m�g neve, gy�rt egyet Oszt�lyn�vSorsz�m
	 * mint�ra
	 *
	 * @param obj  Az objektum, amelynek ismerni szeretn�nk a nev�t
	 */
	public static String resolveName( Object obj ){
		if( obj == null )
			return "null";
		
		String name = names.get(obj);
		
		// Ha name==null, nek�nk kell nevet alkotnunk
		for( int i = 0 ; name == null ; i++ ){
			String next = obj.getClass().getSimpleName().toLowerCase();
			// Ha i>0, akkor sorsz�mot kap az objektum
			if( i>0 )
				next = next+i;
			// Ha szabad a n�v, haszn�ljuk, egy�bk�nt keres�nk tov�bb
			if( !names.containsValue(next) )
				name = next;
			else
				name = null;	// menjen tov�bb a keres�s
		}
		
		// Ha m�g nem tett�k bele a nevet, most beletessz�k
		names.put( obj, name );
		return name;
		
	}
	/**
	 * Be�ll�tja egy objektum nev�t
	 *
	 * @param o  Az objektum, amelynek be�ll�tjuk a nev�t
	 * @param name  Az objektum �j neve
	 * @param owner Tulajdonos objektum(Ha van)
	 */
	public static void setName( Object o, String name, Object owner ){
		if( owner == null )
			names.put(o, name);
		else
			names.put(o, resolveName(owner)+"."+name);
	}

	/**
	 * Ki�rja a beh�z�snak megfelel� sz�m� space-et
	 * 
	 */
	private static void printTab(){
		if( !enabled || maxDepth<depth )
			return;
			
		for( int i = 0 ; i < behuzas ; i++ )	
			System.out.print(" ");
	}
	/**
	 * Egy tabnyi jobbra nyilat rajzol
	 * 
	 */
	private static void printArrowRight(){
		if( !enabled || maxDepth<depth )
			return;
			
		for( int i = 0 ; i < tab-1 ; i++ )	
			System.out.print("-");
		System.out.print(">");
	}
	/**
	 * Egy tabnyi balra nyilat rajzol
	 * 
	 */
	private static void printArrowLeft(){
		if( !enabled || maxDepth<depth )
			return;
			
		System.out.print("<");
		for( int i = 0 ; i < tab+3 ; i++ )	
			System.out.print("-");
	}
	/**
	 * Ki�rja a sz�veget, ha enged�lyezett a loggol�s
	 * 
	 */
	private static void print(String param){
		if( !enabled || maxDepth<depth )
			return;
			
		System.out.print(param);
	}
	/**
	 * Ki�rja a sz�veget sort�r�ssel, ha enged�lyezett a loggol�s
	 * 
	 */
	private static void println(String param){
		if( !enabled || maxDepth<depth )
			return;
			
		System.out.println(param);
	}
	/**
	 * K�rd�st tesz fel a felhaszn�l�nak
	 * 
	 * @param question Feltett k�rd�s
	 * @param answers V�laszt�si lehet�s�gek
	 * @return
	 */
	public static int askQuestion(String question, String... answers){
		BufferedReader br = new BufferedReader(
				new InputStreamReader(System.in)
				);
		printMessage(question);
		for( int i = 0 ; i < answers.length ; i++ )
		{
			printMessage(""+(i+1)+"."+answers[i]);
		}
		try{
			printTab();
			int i = Integer.parseInt(br.readLine());
			if( i < 1 || i > answers.length )
			{
				return 0;
			}
			return i;
		}
		catch(Exception e){
		}
		return 0;
	}
	
	/**
	 * N�veli a beh�z�st �s ki�ratja a h�v� met�dus megh�v�s�t stacktrace alapj�n
	 *
	 * @param obj h�v� met�dus this objektuma
	 * @param arguments argumentumlista, string eset�n �rt�k, objektum eset�n n�v �r�dik ki
	 */
	public static void printCall( Object obj, Object... arguments ){
		depth++;
		// Lek�rj�k a StackTrace-b�l a 2vel fentebbi met�dush�v�st
		StackTraceElement call = Thread.currentThread().getStackTrace()[2];
		
		String methodName = call.getMethodName();
		// Konstruktorokat nem <init>, hanem Oszt�lyn�v(..) m�don jelen�tj�k meg
		// Valamint fel��rjuk a <<create>> szignat�r�t
		if( methodName.equals("<init>") ){
			methodName = obj.getClass().getSimpleName();
			printTab();
			println("  <<create>>");			
		}
		
		printTab();
		printArrowRight();
		behuzas += tab;		
		print( resolveName(obj) + "."+methodName+"(");
		
		// Argumentumokat vessz�vel elv�lasztva ki�rjuk
		for( int i = 0 ; i < arguments.length; i++ )
		{
			// 2. elemt�l fogva vessz�t is �runk el�
			if( i>0 )
				print(", ");	
			Object param = arguments[i];
			
			// Ha a param�ter string, akkor �rt�ket szeretn�nk ki�ratni
			if( param instanceof String )
				print( (String)param );
			else
				print( resolveName(param) );
			
		}
		
		println(")");
		
	}
	
	/**
	 * Ki�ratja a megadott sztringet az adott beh�z�s mellett
	 * @param str Ki�rand� sztring
	 *
	 */
	public static void printMessage(String str){
		printTab();
		println(str);
	}
	/**
	 * Jel�li a met�dush�v�s v�g�t �s cs�kkenti a beh�z�st
	 *
	 */
	public static void printCallEnd(){
		behuzas -= tab;
		printTab();
		printArrowLeft();	
		println("");
		depth--;
	}
	
	
}
