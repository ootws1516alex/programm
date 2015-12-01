package tobias.test;

public class Spielfeld {

	private Koordinate[][] koords;

	public Spielfeld(){
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

		Spielstein temp;

		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 6; j++) {

				temp = koords[i][j].getSpielstein();
				koords[i][j].setSpielstein(koords[5 - i][5-j].getSpielstein());
				koords[5 - i][5-j].setSpielstein(temp);;

			}
		}

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

	public void initStein(int color){
		
		char color1,color2;
		if(color==1){
			color1='s';
			color2='w';
		}else{
			color1='w';
			color2='s';
		}
		
		this.koords[0][1].setSpielstein(new Spielstein(koords[0][1],color1, false));
		this.koords[0][3].setSpielstein(new Spielstein(koords[0][3],color1, false));
		this.koords[0][5].setSpielstein(new Spielstein(koords[0][5],color1, true));
		this.koords[1][0].setSpielstein(new Spielstein(koords[1][0],color1, false));
		this.koords[1][2].setSpielstein(new Spielstein(koords[1][2],color1, false));
		this.koords[1][4].setSpielstein(new Spielstein(koords[1][4],color1, false));
		this.koords[4][1].setSpielstein(new Spielstein(koords[4][1],color2, false));
		this.koords[4][3].setSpielstein(new Spielstein(koords[4][3],color2, false));
		this.koords[4][5].setSpielstein(new Spielstein(koords[4][5],color2, false));
		this.koords[5][0].setSpielstein(new Spielstein(koords[5][0],color2, true));
		this.koords[5][2].setSpielstein(new Spielstein(koords[5][2],color2, false));
		this.koords[5][4].setSpielstein(new Spielstein(koords[5][4],color2, false));
	}
	
	
	

	public Koordinate[][] getKoords() {
		return koords;
	}

	public void setKoords(Koordinate[][] koords) {
		this.koords = koords;
	}
}
