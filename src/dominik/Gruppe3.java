package dominik;

public class Gruppe3 extends Spiellogik implements GruppeX{

	int runde=0;
	boolean isBlack=false;
	
	public Gruppe3() {
		initGame();
	}
	
	public void initGame() {
		spielfeld = new Spielfeld();
		setKoords(spielfeld.getKoords());
		setwSteine(spielfeld.getwSteine());
		setsSteine(spielfeld.getsSteine());
		setAusgabe(new Ausgabe(getKoords()));
	}
	
	@Override
	public void youAreFirst() {
		runde=0;
		isBlack=true;
	}

	@Override
	public void youAreSecond() {
		isBlack=false;
		runde=1;		
	}

	@Override
	public boolean isRunning() {
		return !isFinished(false,false);
	}

	@Override
	public int whoWon() {
		if(isBlack=true){
			if(getwSteine().isEmpty()==true){
				return 1;
			}
			if(canMove(1)==false){
				return 1;
			}
		}else{
			if(getsSteine().isEmpty()==true){
				return 1;
			}
			if(canMove(0)==false){
				return 1;
			}
		}
		return 0;
	}

	@Override
	public boolean takeYourMove(String hisMove) {
		if(this.pruefeAufGueltigeKoord(hisMove)){
			if(this.richtigerZug(hisMove, runde)){
				bewegen(hisMove,runde);
				runde++;
				return true;
			}
		}
		return false;
	}

	@Override
	public String getMyMove() {
		String move;
		move=calculateAiMove(runde);
		bewegen(move,runde);
		runde++;
		return move;
	}

	@Override
	public boolean canYouMove() {
		if(isBlack==true){
			if(canMove(0)==false){
				return false;
			}
		}else{
			if(canMove(1)==false){
				return false;
			}
		}
		return true;
	}

	@Override
	public boolean canIMove() {
		if(isBlack==true){
			if(canMove(1)==false){
				return false;
			}
		}else{
			if(canMove(0)==false){
				return false;
			}
		}
		return true;
	}

	@Override
	public void printBoard() {
		getAusgabe().zeigeBrett();
	}
	
}
