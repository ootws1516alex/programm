package tobias.test;

import java.util.Scanner;

public class HumanVSAi extends Spiellogik {

	private int time;
	Spielfeld spielfeld;
	Spieler spieler1;
	Spieler spieler2;
	
	public HumanVSAi(int time, int color){
		setTime(time);
		initGame();
		runGame();
	}
	
	public void initGame(){
		spielfeld = new Spielfeld();
		setKoords(spielfeld.getKoords());
		setwSteine(spielfeld.getwSteine());
		setsSteine(spielfeld.getsSteine());
		setAusgabe(new Ausgabe(getKoords()));
	}

	@Override
	public void runGame() {
		// TODO Auto-generated method stub
		
	}
	
}
