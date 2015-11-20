package joern;

import  joern.spielbrett.*;

import java.util.ArrayList;

import joern.spielbrett.Spielfeld;
import joern.spielbrett.Spielstein;


public class Main {

	public static void main(String[] args) {
		
		ArrayList<Spielstein> steine = new ArrayList<Spielstein>();
		
		steine.add(new Spielstein(0,0,'w', true));
		steine.add(new Spielstein(1,1,'w', false));
		steine.add(new Spielstein(0,2,'w', false));
		steine.add(new Spielstein(1,3,'w', false));
		steine.add(new Spielstein(0,4,'w', false));
		steine.add(new Spielstein(1,5,'w', false));
		steine.add(new Spielstein(4,0,'s', false));
		steine.add(new Spielstein(5,1,'s', false));
		steine.add(new Spielstein(4,2,'s', false));
		steine.add(new Spielstein(5,3,'s', false));
		steine.add(new Spielstein(4,4,'s', false));
		steine.add(new Spielstein(5,5,'s', true));
		
		Spielfeld test = new Spielfeld(steine);
		
		test.zeigeBrett();
		
		steine.get(1).setLady(true);
		
		test.zeigeBrett();

	}

}
