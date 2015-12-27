package tobias.test;

public class Ausgabe {

	private Koordinate[][] koords;
	private char[][] feld;
	
	public Ausgabe(Koordinate[][] koords) {
		this.koords=koords;
		feldErzeugen();
	}

	public void setKoordinaten(Koordinate[][] koords){
		this.koords=koords;
	}

	public char istStein(int x, int y) {
		if (x > 0) {
			x = x - 1;
			x = x / 2;
		}
		if (y > 0) {
			y = y - 1;
			y = y / 2;
		}
		if (x < 6 && y < 6 && koords[x][y].getSpielstein() != null) {
			if (koords[x][y].getSpielstein().isLady()) {
				return (char) (koords[x][y].getSpielstein().getColor() - ' ');
			} else {
				return koords[x][y].getSpielstein().getColor();
			}
		}

		else {
			return ' ';
		}
	}
	
	public void zeigeGedrehtesBrett(){
		dreheBrett();
		zeigeBrett();
		dreheBrett();
	}
	
	/**
	 * Wird NUR intern aufgerufen!! Wir benutzen in der Logik "zeigeGedrehtesBrett()" und "zeigeBrett()"
	 */
	public void dreheBrett(){

		Spielstein temp;
		char save, save2;

		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 6; j++) {

				temp = koords[i][j].getSpielstein();
				koords[i][j].setSpielstein(koords[5 - i][5-j].getSpielstein());
				koords[5 - i][5-j].setSpielstein(temp);;

			}
		}
		for(int i = 0; i < 6; i++){
			for(int j = 0; j < 6; j++){

				if(j==0){
					save=this.feld[i][0];
					save2=this.feld[12-i][0];
					this.feld[12-i][0]=save;
					this.feld[i][0]=save2;
				}
			}
		}
		for(int i = 0; i < 14; i++){
			for(int j = 0; j < 7; j++){
				if(i==13&&j!=0){
					save=this.feld[i][j];
					save2=this.feld[i][14-j];
					this.feld[i][14-j]=save;
					this.feld[i][j]=save2;
				}
			}
		}
	}
	
	public void feldErzeugen() {
		feld = new char[][] {

				{'-',' ','-',' ','-',' ','-',' ','-',' ','-',' ','-',' '},
				{'1','|','#','|',' ','|','#','|',' ','|','#','|',' ','|'},
				{'-',' ','-',' ','-',' ','-',' ','-',' ','-',' ','-',' '},
				{'2','|',' ','|','#','|',' ','|','#','|',' ','|','#','|'},
				{'-',' ','-',' ','-',' ','-',' ','-',' ','-',' ','-',' '},
				{'3','|','#','|',' ','|','#','|',' ','|','#','|',' ','|'},
				{'-',' ','-',' ','-',' ','-',' ','-',' ','-',' ','-',' '},
				{'4','|',' ','|','#','|',' ','|','#','|',' ','|','#','|'},
				{'-',' ','-',' ','-',' ','-',' ','-',' ','-',' ','-',' '},
				{'5','|','#','|',' ','|','#','|',' ','|','#','|',' ','|'},
				{'-',' ','-',' ','-',' ','-',' ','-',' ','-',' ','-',' '},
				{'6','|',' ','|','#','|',' ','|','#','|',' ','|','#','|'},
				{'-',' ','-',' ','-',' ','-',' ','-',' ','-',' ','-',' '},
				{' ','|','A','|','B','|','C','|','D','|','E','|','F','|'},	
		};
	}

	public void zeigeBrett() {
		boolean reihe = true;
		int reiheHelp = 0;

		for (int i = 0; i < 14; i++) {
			for (int j = 0; j < 14; j++) {
				if (i % 2 == 1 && j % 2 == 1 && j < 13 && i < 13) {
					if (reihe) {
						switch (j) {
						case 1:
							break;
						case 5:
							break;
						case 9:
							;
							break;
						default:
							feld[i][j+1] =this.istStein(i, j);
							break;
						}
					} else {
						switch (j) {
						case 3:
							break;
						case 7:
							break;
						case 11:
							break;
						default:
							feld[i][j + 1] = this.istStein(i, j);
						}
					}
				}
			}
			reiheHelp++;
			if (reiheHelp > 0 && reiheHelp % 2 == 0) {
				if (reihe) {
					reihe = false;
				} else {
					reihe = true;
				}
			}
		}
		for (int i = 0; i < 14; i++) {
			for (int j = 0; j < 14; j++) {
				System.out.print(feld[i][j]);
			}
			System.out.println();
		}
		System.out.println();
	}
}
