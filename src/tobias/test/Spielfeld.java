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
		}else {
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

		sSteine=new ArrayList<Spielstein>();
		wSteine=new ArrayList<Spielstein>();
		
		color1='s';
		color2='w';
		steine=new Spielstein[12];
		this.koords[0][1].setSpielstein(new Spielstein(koords[0][1],color2, false));
		this.koords[0][3].setSpielstein(new Spielstein(koords[0][3],color2, false));
		this.koords[0][5].setSpielstein(new Spielstein(koords[0][5],color2, true));
		this.koords[1][0].setSpielstein(new Spielstein(koords[1][0],color2, false));
		this.koords[1][2].setSpielstein(new Spielstein(koords[1][2],color2, false));
		this.koords[1][4].setSpielstein(new Spielstein(koords[1][4],color2, false));
		this.koords[4][1].setSpielstein(new Spielstein(koords[4][1],color1, false));
		this.koords[4][3].setSpielstein(new Spielstein(koords[4][3],color1, false));
		this.koords[4][5].setSpielstein(new Spielstein(koords[4][5],color1, false));
		this.koords[5][0].setSpielstein(new Spielstein(koords[5][0],color1, true));
		this.koords[5][2].setSpielstein(new Spielstein(koords[5][2],color1, false));
		this.koords[5][4].setSpielstein(new Spielstein(koords[5][4],color1, false));
		
		wSteine.add(this.koords[0][1].getSpielstein());
		wSteine.add(this.koords[0][3].getSpielstein());
		wSteine.add(this.koords[0][5].getSpielstein());
		wSteine.add(this.koords[1][0].getSpielstein());
		wSteine.add(this.koords[1][2].getSpielstein());
		wSteine.add(this.koords[1][4].getSpielstein());
		sSteine.add(this.koords[4][1].getSpielstein());
		sSteine.add(this.koords[4][3].getSpielstein());
		sSteine.add(this.koords[4][5].getSpielstein());
		sSteine.add(this.koords[5][0].getSpielstein());
		sSteine.add(this.koords[5][2].getSpielstein());
		sSteine.add(this.koords[5][4].getSpielstein());
		
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
