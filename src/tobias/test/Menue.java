package tobias.test;

import java.util.ArrayList;

public class Menue {

	public static void main(String[] args) {

		ArrayList<Spielstein> bla = new ArrayList<Spielstein>() ;

		Spielfeld brett = new Spielfeld(bla);

		brett.initStein();
		
		brett.zeigeBrett();
		
		System.out.println();
		
		brett.dreheBrett();
		
		brett.zeigeBrett();
	}

}
