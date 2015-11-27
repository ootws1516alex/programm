package tobias.test;

import java.util.ArrayList;

public class Menue {

	public static void main(String[] args) {

		Spielfeld brett = new Spielfeld();

		brett.initStein();
		
		brett.zeigeBrett();
		
		System.out.println();
		
		brett.dreheBrett();
		
		brett.zeigeBrett();
	}

}
