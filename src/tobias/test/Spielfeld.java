package tobias.test;

import java.util.ArrayList;

public class Spielfeld {

	private Koordinate[][] koords;
	private Spielstein[] steine;
	private ArrayList<Spielstein> wSteine;
	private ArrayList<Spielstein> sSteine;
	
	public Spielfeld(){
		this.setKoords(this.initFeld());
		this.startAufstellung();
		
	}

	public char istStein(int x, int y){
		if(x>0){
			x=x-1;
			x=x/2;
		}
		if(y>0){
			y=y-1;
			y=y/2;
		}
		if(x<6&&y<6 &&koords[x][y].getSpielstein() != null) {
			if(koords[x][y].getSpielstein().isLady()) {
				return (char) (koords[x][y].getSpielstein().getColor() - ' ');
			} else {
				return koords[x][y].getSpielstein().getColor();
			} 
		}

		else {
			return ' ';
		}
	}

	public Koordinate[][] initFeld(){

		Koordinate[][] koords = new Koordinate[6][6];

		for(int i = 0; i < 6; i++){
			for(int j = 0; j < 6; j++){
				char neu = (char)(i+65);
				String name = "" + neu + (j+1);
				koords[i][j] = new Koordinate(i,j,name);
			}
		}
		return koords;
	}

	public void startAufstellung(){

		char color1,color2;

		color1='s';
		color2='w';
		steine=new Spielstein[12];
		this.koords[0][1].setSpielstein(steine[0]=new Spielstein(koords[0][1],color2, false));
		this.koords[0][3].setSpielstein(steine[1]=new Spielstein(koords[0][3],color2, false));
		this.koords[0][5].setSpielstein(steine[2]=new Spielstein(koords[0][5],color2, true));
		this.koords[1][0].setSpielstein(steine[3]=new Spielstein(koords[1][0],color2, false));
		this.koords[1][2].setSpielstein(steine[4]=new Spielstein(koords[1][2],color2, false));
		this.koords[1][4].setSpielstein(steine[5]=new Spielstein(koords[1][4],color2, false));
		this.koords[4][1].setSpielstein(steine[6]=new Spielstein(koords[4][1],color1, false));
		this.koords[4][3].setSpielstein(steine[7]=new Spielstein(koords[4][3],color1, false));
		this.koords[4][5].setSpielstein(steine[8]=new Spielstein(koords[4][5],color1, false));
		this.koords[5][0].setSpielstein(steine[9]=new Spielstein(koords[5][0],color1, true));
		this.koords[5][2].setSpielstein(steine[10]=new Spielstein(koords[5][2],color1, false));
		this.koords[5][4].setSpielstein(steine[11]=new Spielstein(koords[5][4],color1, false));
		
		for(int i=0;i<6;i++){
			wSteine.add(steine[i]);
		}
		for(int i=6;i<12;i++){
			sSteine.add(steine[i]);
		}
		
	}

	public ArrayList<Spielstein> getwSteine() {
		return wSteine;
	}

	public void setwSteine(ArrayList<Spielstein> wSteine) {
		this.wSteine = wSteine;
	}

	public ArrayList<Spielstein> getsSteine() {
		return sSteine;
	}

	public void setsSteine(ArrayList<Spielstein> sSteine) {
		this.sSteine = sSteine;
	}

	public Koordinate[][] getKoords() {
		return koords;
	}

	public void setKoords(Koordinate[][] koords) {
		this.koords = koords;
	}

}
