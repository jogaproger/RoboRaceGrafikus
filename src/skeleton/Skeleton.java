package skeleton;

import java.io.BufferedReader;
import java.io.InputStreamReader;


public class Skeleton {

	
	public static void main( String[] args )
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		System.out.println("RoboRace - Szkeleton verzi�");
		System.out.println("56. Fixit Company");
		System.out.println();
		
		boolean vege = false;
		
		while( !vege ){
			System.out.println("V�lassz az al�bbi Use Case-ek k�z�l.");
			UseCase.Listaz();
			System.out.println("�rd be a Use case sorsz�m�t, vagy 0-t ha kis szeretn�l l�pni:");		
			try{
				int i = Integer.parseInt(br.readLine());
				if(  i==0 )
					vege = true;
				else{
					UseCase.Vegrehajt(i);
				}
			}
			catch(Exception e){
				System.out.println("Nem j� sz�mot adt�l meg, vagy helytelen form�tumban");	
				continue;
			}
		}

	}
	
}
