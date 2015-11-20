package joern;

import  joern.spielbrett.*;

import java.util.ArrayList;

import joern.spielbrett.Spielfeld;
import joern.spielbrett.Spielstein;


public class Main {

	public static void main(String[] args) {
		
		ArrayList<Spielstein> steine = new ArrayList<Spielstein>();
		
		steine.add(new Spielstein(0,0,"S"));
		steine.add(new Spielstein(3,5,"S"));
		steine.add(new Spielstein(0,0,"S"));
		steine.add(new Spielstein(0,0,"S"));
		steine.add(new Spielstein(0,0,"S"));
		
		Spielfeld test = new Spielfeld(steine);
		
		test.zeigeBrett();

	}

}
