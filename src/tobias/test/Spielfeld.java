package tobias.test;

import java.util.ArrayList;

public class Spielfeld {

	private Koordinate[][] koords;
	private ArrayList<Spielstein> steine;

	public Spielfeld(ArrayList<Spielstein> s){
		this.steine = s;
		this.setKoords(this.initFeld());
	}

	public void zeigeBrett(){
		boolean reihe = true;
		int reiheHelp = 0;

		for(int i = 0; i < 13; i++){
			for(int j = 0; j < 13; j++){
				if(i % 2 == 0 && j % 2 == 1){
					System.out.print("-");
				}
				else if(i % 2 == 0 && j % 2 == 0){
					System.out.print(" ");
				}
				else if(i % 2 == 1 && j % 2 == 0){
					System.out.print("|");
				}
				else if(i % 2 == 1 && j % 2 == 1){
					if(reihe){
						switch(j){
						case 1: System.out.print("#");break;
						case 5: System.out.print("#");break;
						case 9: System.out.print("#");break;
						default: System.out.print(this.istStein(i/2, j/2));
						}
					}
					else{
						switch(j){
						case 3: System.out.print("#");break;
						case 7: System.out.print("#");break;
						case 11: System.out.print("#");break;
						default: System.out.print(this.istStein(i/2, j/2));
						}
					}

				}
			}
			System.out.println();
			reiheHelp++;
			if(reiheHelp > 0 && reiheHelp % 2 == 0){
				if(reihe){
					reihe = false;
				}
				else{
					reihe = true;
				}
			}

		}
	}

	public void dreheBrett(){

		
		
	}

	public void setSteine(ArrayList<Spielstein> s){
		this.steine = s;
	}

	public ArrayList<Spielstein> getSteine(){
		return this.steine;
	}

	public char istStein(int x, int y){

		if(koords[x][y].getSpielstein() != null) {
			if(koords[x][y].getSpielstein().isLady()) {
				return (char) (koords[x][y].getSpielstein().getColor() - ' ');
			} else {
				return koords[x][y].getSpielstein().getColor();
			} 
		} else {
			return ' ';
		}
	}

	public Koordinate[][] initFeld(){

		Koordinate[][] koords = new Koordinate[6][6];

		for(int i = 0; i < 6; i++){
			for(int j = 0; j < 6; j++){
				char neu = (char)(i+65);
				String name = "" + neu + (j+1);
				koords[i][j] = new Koordinate(i,j,null,name);
			}
		}

		return koords;
	}

	public void initStein(){
		this.koords[0][1].setSpielstein(new Spielstein(koords[0][1],'s', false));
		this.koords[0][3].setSpielstein(new Spielstein(koords[0][3],'s', false));
		this.koords[0][5].setSpielstein(new Spielstein(koords[0][5],'s', true));
		this.koords[1][0].setSpielstein(new Spielstein(koords[1][0],'s', false));
		this.koords[1][2].setSpielstein(new Spielstein(koords[1][2],'s', false));
		this.koords[1][4].setSpielstein(new Spielstein(koords[1][4],'s', false));
		this.koords[4][1].setSpielstein(new Spielstein(koords[4][1],'w', false));
		this.koords[4][3].setSpielstein(new Spielstein(koords[4][3],'w', false));
		this.koords[4][5].setSpielstein(new Spielstein(koords[4][5],'w', false));
		this.koords[5][0].setSpielstein(new Spielstein(koords[5][0],'w', true));
		this.koords[5][2].setSpielstein(new Spielstein(koords[5][2],'w', false));
		this.koords[5][4].setSpielstein(new Spielstein(koords[5][4],'w', false));
	}

	public Koordinate[][] getKoords() {
		return koords;
	}

	public void setKoords(Koordinate[][] koords) {
		this.koords = koords;
	}
}
