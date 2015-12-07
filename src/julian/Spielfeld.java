package julian;

public class Spielfeld {

	private Koordinate[][] koords;
	private char[][] feld;
	private Spielstein spielstein1;
	private Spielstein spielstein2;
	private Spielstein spielstein3;
	private Spielstein spielstein4;
	private Spielstein spielstein5;
	private Spielstein spielstein6;
	private Spielstein spielstein7;
	private Spielstein spielstein8;
	private Spielstein spielstein9;
	private Spielstein spielstein10;
	private Spielstein spielstein11;
	private Spielstein spielstein12;

	public Spielfeld(){
		this.setKoords(this.initFeld());
		this.startAufstellung();
		this.feldErzeugen();
	}

	public void zeigeBrett(){
		boolean reihe = true;
		int reiheHelp = 0;



		for(int i = 0; i < 14; i++){
			for(int j = 0; j < 14; j++){
				if(i % 2 == 1 && j % 2 == 1&&j<13&&i<13){
					if(reihe){
						switch(j){
						case 1: break;
						case 5: break;
						case 9: ;break;
						default:feld[i][j+1]=this.istStein(i, j);break;



						}
					}
					else{
						switch(j){
						case 3:break;
						case 7:break;
						case 11:break;
						default: feld[i][j+1]=this.istStein(i, j);
						}
					}
				}
			}
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
		for(int i = 0; i < 14; i++){
			for(int j = 0; j < 14; j++){
				System.out.print(feld[i][j]);

			}
			System.out.println();
		}
		System.out.println();
	}

	public void feldErzeugen(){
		feld=new char[][]{

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



	public void printKoordinaten(Spielstein s){
		System.out.println(s.getKoordinate().getX()+" "+s.getKoordinate().getY());
	}


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

	public boolean isStein(int x, int y){

		if(koords[x][y].getSpielstein() != null) {
			return true;
		}

		else {
			return false;
		}
	}

	public boolean isLady(int x, int y){

		if(koords[x][y].getSpielstein().isLady()==true) {
			return true;
		}

		else {
			return false;
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

	public void startAufstellung(){

		char color1 = 0,color2=0;

		color1='s';
		color2='w';

		//		this.koords[0][1].setSpielstein(new Spielstein(koords[0][1],color1, false));
		//		this.koords[0][3].setSpielstein(new Spielstein(koords[0][3],color1, false));
		//		this.koords[0][5].setSpielstein(new Spielstein(koords[0][5],color1, true));
		//		this.koords[1][0].setSpielstein(new Spielstein(koords[1][0],color1, false));
		//		this.koords[1][2].setSpielstein(new Spielstein(koords[1][2],color1, false));
		//		this.koords[1][4].setSpielstein(new Spielstein(koords[1][4],color1, false));
		//		this.koords[4][1].setSpielstein(new Spielstein(koords[4][1],color2, false));
		//		this.koords[4][3].setSpielstein(new Spielstein(koords[4][3],color2, false));
		//		this.koords[4][5].setSpielstein(new Spielstein(koords[4][5],color2, false));
		//		this.koords[5][0].setSpielstein(new Spielstein(koords[5][0],color2, true));
		//		this.koords[5][2].setSpielstein(new Spielstein(koords[5][2],color2, false));
		//		this.koords[5][4].setSpielstein(new Spielstein(koords[5][4],color2, false));

		this.koords[0][1].setSpielstein(spielstein1=new Spielstein(koords[0][1],color2, false));
		this.koords[0][3].setSpielstein(spielstein2=new Spielstein(koords[0][3],color2, false));
		this.koords[0][5].setSpielstein(spielstein3=new Spielstein(koords[0][5],color2, true));
		this.koords[1][0].setSpielstein(spielstein4=new Spielstein(koords[1][0],color2, false));
		this.koords[1][2].setSpielstein(spielstein5=new Spielstein(koords[1][2],color2, false));
		this.koords[1][4].setSpielstein(spielstein6=new Spielstein(koords[1][4],color2, false));
		this.koords[4][1].setSpielstein(spielstein7=new Spielstein(koords[4][1],color1, false));
		this.koords[4][3].setSpielstein(spielstein8=new Spielstein(koords[4][3],color1, false));
		this.koords[4][5].setSpielstein(spielstein9=new Spielstein(koords[4][5],color1, false));
		this.koords[5][0].setSpielstein(spielstein10=new Spielstein(koords[5][0],color1, true));
		this.koords[5][2].setSpielstein(spielstein11=new Spielstein(koords[5][2],color1, false));
		this.koords[5][4].setSpielstein(spielstein12=new Spielstein(koords[5][4],color1, false));

		printKoordinaten(spielstein1);
		printKoordinaten(spielstein2);
		printKoordinaten(spielstein3);
		printKoordinaten(spielstein4);
		printKoordinaten(spielstein5);
		printKoordinaten(spielstein6);
		printKoordinaten(spielstein7);
		printKoordinaten(spielstein8);
		printKoordinaten(spielstein9);
		printKoordinaten(spielstein10);
		printKoordinaten(spielstein11);
		printKoordinaten(spielstein12);

	}

	public Koordinate[][] getKoords() {
		return koords;
	}

	public void setKoords(Koordinate[][] koords) {
		this.koords = koords;
	}

	public void ziehen(String aktuellerZug, int zug) {
				if(mussSchlagen(aktuellerZug, zug)==false){

		System.out.println(richtigerZug(aktuellerZug,zug));
		System.out.println(zug);
		if(richtigerZug(aktuellerZug,zug)==true){
			Spielstein temp;
			//		koords[aktuellerZug.charAt(0)-'@'-1][aktuellerZug.charAt(1)-'0'-1].getSpielstein().getKoordinate().setX(aktuellerZug.charAt(2)-'@'-1);
			//		koords[aktuellerZug.charAt(0)-'@'-1][aktuellerZug.charAt(1)-'0'-1].getSpielstein().getKoordinate().setY(aktuellerZug.charAt(3)-'0'-1);
			if(zug%2==0){
				temp = koords[aktuellerZug.charAt(1)-'0'-1][aktuellerZug.charAt(0)-'@'-1].getSpielstein();
				koords[aktuellerZug.charAt(1)-'0'-1][aktuellerZug.charAt(0)-'@'-1].setSpielstein(koords[aktuellerZug.charAt(3)-'0'-1][aktuellerZug.charAt(2)-'@'-1].getSpielstein());
				koords[aktuellerZug.charAt(3)-'0'-1][aktuellerZug.charAt(2)-'@'-1].setSpielstein(temp);

			}
			else if(zug%2==1){
				temp = koords[5-(aktuellerZug.charAt(1)-'0'-1)][5-(aktuellerZug.charAt(0)-'@'-1)].getSpielstein();
				koords[5-(aktuellerZug.charAt(1)-'0'-1)][5-(aktuellerZug.charAt(0)-'@'-1)].setSpielstein(koords[5-(aktuellerZug.charAt(3)-'0'-1)][5-(aktuellerZug.charAt(2)-'@'-1)].getSpielstein());
				koords[5-(aktuellerZug.charAt(3)-'0'-1)][5-(aktuellerZug.charAt(2)-'@'-1)].setSpielstein(temp);

			}
		}
				}
				else{
					schlagen(aktuellerZug, zug);
		
				}


	}
	private void schlagen(String aktuellerZug, int zug) {
		
		int yAktuell=aktuellerZug.charAt(1)-'0'-1;
		int xAktuell=aktuellerZug.charAt(0)-'@'-1;
		int ySoll=aktuellerZug.charAt(3)-'0'-1;
		int xSoll=aktuellerZug.charAt(2)-'@'-1;
		
		Spielstein temp;
		//		koords[aktuellerZug.charAt(0)-'@'-1][aktuellerZug.charAt(1)-'0'-1].getSpielstein().getKoordinate().setX(aktuellerZug.charAt(2)-'@'-1);
		//		koords[aktuellerZug.charAt(0)-'@'-1][aktuellerZug.charAt(1)-'0'-1].getSpielstein().getKoordinate().setY(aktuellerZug.charAt(3)-'0'-1);
		if(zug%2==0){
			temp = koords[xAktuell][yAktuell].getSpielstein();
			koords[xAktuell][yAktuell].setSpielstein(koords[xSoll][ySoll].getSpielstein());
			koords[xSoll][ySoll].setSpielstein(temp);
			if(xSoll==xAktuell+2){
				koords[xSoll-1][ ySoll+1].setSpielstein(null);
				
			}
			else if(xSoll==xAktuell-2){
				koords[xSoll+1][ ySoll+1].setSpielstein(null);
				
			}

		}
		else if(zug%2==1){
			temp = koords[5-xAktuell][5-yAktuell].getSpielstein();
			koords[5-xAktuell][5-yAktuell].setSpielstein(koords[5-xSoll][5-ySoll].getSpielstein());
			koords[5-xSoll][5-ySoll].setSpielstein(temp);
			if(5-xSoll==5-xAktuell+2){
				koords[5-xSoll-1][ 5-ySoll-1].setSpielstein(null);
				
			}
			else if(5-xSoll==5-xAktuell-2){
				koords[5-xSoll+1][ 5-ySoll-1].setSpielstein(null);
				
			}

		}

	}

	public boolean mussSchlagen(String aktuellerZug, int zug){
		boolean erg=false;
		int xAktuell=aktuellerZug.charAt(1)-'0'-1;
		int yAktuell=aktuellerZug.charAt(0)-'@'-1;
		int xSoll=aktuellerZug.charAt(3)-'0'-1;
		int ySoll=aktuellerZug.charAt(2)-'@'-1;

		if(zug%2==0){
			if(xSoll==xAktuell+2){
				if((isStein(xAktuell, yAktuell)==true&&(isStein(xSoll, ySoll)==false))&&isStein(xSoll-1, ySoll+1)==true){
					return true;
				}


			}
			else if(xSoll==xAktuell-2){
				if((isStein(xAktuell, yAktuell)==true&&(isStein(xSoll, ySoll)==false))&&isStein(xSoll+1, ySoll+1)==true){
					return true;		
				}
			}

		}
		else if(zug%2==1){
			if(5-xSoll==5-xAktuell+2){
				if((isStein(5-xAktuell, 5-yAktuell)==true&&(isStein(5-xSoll, 5-ySoll)==false))&&isStein(5-xSoll-1, 5-ySoll+1)==true){
					return true;
				}


			}
			else if(5-xSoll==5-xAktuell-2){
				if((isStein(5-xAktuell, 5-yAktuell)==true&&(isStein(5-xSoll, 5-ySoll)==false))&&isStein(5-xSoll+1, 5-ySoll+1)==true){
					return true;		
				}
			}

		}
		return erg;
	}

	public boolean richtigerZug(String aktuellerZug, int zug){
		boolean erg=true;
		int xAktuell=aktuellerZug.charAt(1)-'0'-1;
		int yAktuell=aktuellerZug.charAt(0)-'@'-1;
		int xSoll=aktuellerZug.charAt(3)-'0'-1;
		int ySoll=aktuellerZug.charAt(2)-'@'-1;

		if(zug%2==0){
			if((isStein(xAktuell, yAktuell)==true&&(isStein(xSoll, ySoll)==true))){
				return false;
			}
			else if(isStein(xAktuell, yAktuell)==false){
				return false;
			}
			else if(isLady(xAktuell, yAktuell)==false){
				if(xAktuell!=xSoll+1&&(yAktuell!=ySoll+1||yAktuell!=ySoll-1)){
					return false;
				}
			}
			else if(isLady(xAktuell, yAktuell)==true){
				int i1=xSoll-xAktuell;
				int i2=ySoll-yAktuell;
				if(Math.abs(i1)!=Math.abs(i2)){
					return false;
				}
			}
		}
		else if(zug%2==1){
			if((isStein(5-xAktuell, 5-yAktuell)==true&&(isStein(5-xSoll, 5-ySoll)==true))){
				return false;
			}
			else if(isStein(5-xAktuell, 5-yAktuell)==false){
				return false;
			}

			else if(isLady(5-xAktuell, 5-yAktuell)==false){
				if(5-xAktuell!=5-xSoll+1&&(5-yAktuell!=5-ySoll+1||5-yAktuell!=5-ySoll-1)){
					return false;
				}

			}
			else if(isLady(5-xAktuell, 5-yAktuell)==true){
				int i1=5-xSoll-5-xAktuell;
				int i2=5-ySoll-5-yAktuell;
				if(Math.abs(i1)!=Math.abs(i2)){
					return false;
				}
			}	
		}
		return erg;
	}
}
