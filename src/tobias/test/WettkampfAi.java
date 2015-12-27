package tobias.test;

public class WettkampfAi extends Spiellogik{
	
	private int color;
	
	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
	}

	public WettkampfAi(int color){
		setColor(color);
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
	
	public void runGame(){
	
		
		
		

	}	
}
