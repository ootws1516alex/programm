package tobias.test;

public class HumanVSAi {

	private int color;
	private int time;
	private Koordinate[][] koords;
	
	public HumanVSAi(int time, int color){
		this.time=time;
		this.color=color;
		initGame();
		startGame();
	}

	public int getTime() {
		return time;
	}
	
	public void initGame(){
		Spielfeld spielfeld = new Spielfeld();
		spielfeld.initStein(color);
		koords=spielfeld.getKoords();
	}
	public void startGame(){
		
		
		
		
	}
	
	
}
