package dominik;

import java.util.ArrayList;

public abstract class Spiellogik {

	private Koordinate koords[][];
	private Spieler spieler1,spieler2;
	private Ausgabe ausgabe;
	private ArrayList<Spielstein> wSteine;
	private ArrayList<Spielstein> sSteine;
	private Bewegungsbewertung moeglicheZuege[][] = new Bewegungsbewertung[6][4];

	public Koordinate[][] getKoords() {
		return koords;
	}
	public void setKoords(Koordinate[][] koords) {
		this.koords = koords;
	}
	public Spieler getSpieler1() {
		return spieler1;
	}
	public void setSpieler1(Spieler spieler1) {
		this.spieler1 = spieler1;
	}
	public Spieler getSpieler2() {
		return spieler2;
	}
	public void setSpieler2(Spieler spieler2) {
		this.spieler2 = spieler2;
	}
	public Ausgabe getAusgabe() {
		return ausgabe;
	}
	public void setAusgabe(Ausgabe ausgabe) {
		this.ausgabe = ausgabe;
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
	public int getTime() {
		return time;
	}
	public void setTime(int time) {
		this.time = time;
	}
	public Spielfeld getSpielfeld() {
		return spielfeld;
	}
	public void setSpielfeld(Spielfeld spielfeld) {
		this.spielfeld = spielfeld;
	}
	private int time;
	Spielfeld spielfeld;
	/**
	 * gibt in einem String zurück wer gewonnen hat bzw ob das spiel unentschieden ausging. 
	 * Falls das spiel noch läuft wird NULL zurückgegeben
	 * @param aufgabe1
	 * @param aufgabe2
	 * @return
	 */
	public int whoWon(boolean aufgabe1,boolean aufgabe2){
		if(wSteine.size()==0){
			return 1;
		}
		if(sSteine.size()==0){
			return 2;
		}
		if(sSteine.size()==1&&wSteine.size()==1){
			if(sSteine.get(0).isLady()==true&&wSteine.get(0).isLady()==true){
				return 3;
			}
		}
		if(aufgabe1==true){
			return 4;
		}
		if(aufgabe2==true){
			return 5;
		}else{
			return 0;
		}
	}
	/**
	 * Überprüft ob das spiel noch läuft und gibt ein boolean zurück
	 * @param aufgabe1
	 * @param aufgabe2
	 * @return
	 */
	public boolean isFinished(boolean aufgabe1, boolean aufgabe2){

		if(wSteine.size()==0||sSteine.size()==0){
			return true;
		}
		if(wSteine.size()==1&&sSteine.size()==1){
			if(wSteine.get(0).isLady()==true&&sSteine.get(0).isLady()==true){
				return true;
			}
		}
		if(aufgabe1==true){
			return true;
		}
		if(aufgabe2==true){
			return true;
		}
		return false;
	}

	/**
	 * entfernt falls nötig einen zufälligen stein
	 * @param runde
	 */
	public void removeRandomStein(int runde){
		int random=10;
		Spielstein toRemove;
		if(runde%2==1){
			while(random>wSteine.size()-1){
				random=(int)Math.random()*10;
			}
			toRemove=wSteine.get(random);
			toRemove.getKoordinate().setSpielstein(null);
			toRemove.setKoordinate(null);
			wSteine.remove(toRemove);
		}else{
			while(random>sSteine.size()-1){
				random=(int)Math.random()*10;
			}
			toRemove=sSteine.get(random);
			toRemove.getKoordinate().setSpielstein(null);
			toRemove.setKoordinate(null);
			sSteine.remove(toRemove);	
		}
	}

	/**
	 * prüft ob eingabe korrekt ist
	 * @param aktuellerZug
	 * @return
	 */
	public boolean pruefeAufGueltigeKoord(String aktuellerZug) {
		if (aktuellerZug.length() != 4) {
			return false;
		}
		String moeglicheBuchstaben = "ABCDEF";
		for (int i = 0; i < aktuellerZug.length() - 1; i++) {
			if (i % 2 == 0) {
				if (moeglicheBuchstaben.indexOf(aktuellerZug.charAt(i)) == -1) {
					return false;
				}
			} else {
				if (aktuellerZug.charAt(i) < '1'|| aktuellerZug.charAt(i) > '6') {
					return false;
				}
			}
		}
		return true;
	}
	/**
	 * bewegt einen Stein
	 * @param aktuellerZug
	 * @param zug
	 */

	public void ziehen(String aktuellerZug, int zug) {
		Spielstein temp;

		int yAktuell = aktuellerZug.charAt(1) - '0' - 1;
		int xAktuell = aktuellerZug.charAt(0) - '@' - 1;
		int ySoll = aktuellerZug.charAt(3) - '0' - 1;
		int xSoll = aktuellerZug.charAt(2) - '@' - 1;

		temp = koords[yAktuell][xAktuell].getSpielstein();
		koords[yAktuell][xAktuell].getSpielstein().setKoordinate(null);
		koords[yAktuell][xAktuell].setSpielstein(null);
		koords[ySoll][xSoll].setSpielstein(temp);		
		temp.setKoordinate(koords[ySoll][xSoll]);
	}
	/**
	 * Wird als erstes aufgerufen und ruft dann alle anderen nötigen methoden auf 
	 * um einen zug korrekt durchzuführen. Gibt 1 zurück bei geschlagenem stein, 2 bei zufällig entferntem Stein und andernfalls 0
	 * @param aktuellerZug
	 * @param runde
	 * @return
	 */
	public int bewegen(String aktuellerZug, int runde){
		Spielstein geschlagener=null;
		geschlagener=schlagen(aktuellerZug,runde);
		int erg=0;
		if(geschlagener!=null){
			ziehen(aktuellerZug,runde);
		}else{
			if(mussSchlagen(runde)==true){
				removeRandomStein(runde);
				erg=2;
			}else{
				ziehen(aktuellerZug,runde);
			}
		}
		makeNewLady();
		if(geschlagener!=null){
			erg=1;
		}
		return erg;
	}
	/**
	 * Wandelt alle Steine am Gegnerischen Rand zu Ladies um
	 */
	public void makeNewLady(){
		for(int i=0;i<sSteine.size();i++){
			if(sSteine.get(i).getKoordinate().equals(koords[5][0])||sSteine.get(i).getKoordinate().equals(koords[5][2])
					||sSteine.get(i).getKoordinate().equals(koords[5][4])){
				sSteine.get(i).setLady(true);
			}
		}
		for(int i=0;i<wSteine.size();i++){
			if(wSteine.get(i).getKoordinate().equals(koords[0][1])||wSteine.get(i).getKoordinate().equals(koords[0][3])
					||wSteine.get(i).getKoordinate().equals(koords[0][5])){
				wSteine.get(i).setLady(true);
			}
		}
	}
	/**
	 * Checkt ob ein Stein geschlagen wurde und gibt diesen zurück. Wurde keinn Stein geschlagen wird NULL zurückgegeben.
	 * @param aktuellerZug
	 * @param runde
	 * @return
	 */
	public Spielstein schlagen(String aktuellerZug, int runde) {

		int yAktuell = aktuellerZug.charAt(1) - '0' - 1;
		int xAktuell = aktuellerZug.charAt(0) - '@' - 1;
		int ySoll = aktuellerZug.charAt(3) - '0' - 1;
		int xSoll = aktuellerZug.charAt(2) - '@' - 1;


		Spielstein geschlagener=null;

		if(yAktuell>ySoll&&xAktuell<xSoll){
			int j=xAktuell+1;
			for(int i=yAktuell-1;i>ySoll;i--){
				if(koords[i][j].getSpielstein()!=null){
					geschlagener=koords[i][j].getSpielstein();
					koords[i][j].setSpielstein(null);
					wSteine.remove(geschlagener);
					sSteine.remove(geschlagener);
				}
				j++;
			}
		}
		if(yAktuell>ySoll&&xAktuell>xSoll){
			int j=xAktuell-1;
			for(int i=yAktuell-1;i>ySoll;i--){
				if(koords[i][j].getSpielstein()!=null){
					geschlagener=koords[i][j].getSpielstein();
					koords[i][j].setSpielstein(null);
					wSteine.remove(geschlagener);
					sSteine.remove(geschlagener);
				}
				j--;
			}
		}	
		if(yAktuell<ySoll&&xAktuell<xSoll){
			int j=xAktuell+1;
			for(int i=yAktuell+1;i<ySoll;i++){
				if(koords[i][j].getSpielstein()!=null){
					geschlagener=koords[i][j].getSpielstein();
					koords[i][j].setSpielstein(null);
					wSteine.remove(geschlagener);
					sSteine.remove(geschlagener);
				}
				j++;
			}
		}	
		if(yAktuell<ySoll&&xAktuell>xSoll){
			int j=xAktuell-1;
			for(int i=yAktuell+1;i<ySoll;i++){
				if(koords[i][j].getSpielstein()!=null){
					geschlagener=koords[i][j].getSpielstein();
					koords[i][j].setSpielstein(null);
					wSteine.remove(geschlagener);
					sSteine.remove(geschlagener);
				}
				j--;
			}	
		}
		return geschlagener;
	}

	/**
	 * Überprüft ob schlagpflicht verletzt wurde und gibt dann true zurück, ansonsten false
	 * @param runde
	 * @return
	 */
	public boolean mussSchlagen(int runde) {

		int yAktuell = 0;
		int xAktuell = 0;

		if(runde%2==0){
			for(int i=0;i<sSteine.size();i++){
				if(sSteine.get(i).isLady()==true){
					yAktuell=sSteine.get(i).getKoordinate().getY();
					xAktuell=sSteine.get(i).getKoordinate().getX();
					int j=xAktuell+1;
					for(int k=yAktuell-1;k>0&&j<5;k--){
						if(koords[k][j].getSpielstein()!=null){
							if(koords[k][j].getSpielstein().getColor()=='s'){
								break;
							}
							if(koords[k][j].getSpielstein().getColor()=='w'){
								if(koords[k-1][j+1].getSpielstein()==null){
									return true;	
								}
							}
						}
						j++;
					}
					j=xAktuell-1;
					for(int k=yAktuell-1;k>0&&j>0;k--){
						if(koords[k][j].getSpielstein()!=null){
							if(koords[k][j].getSpielstein().getColor()=='s'){
								break;
							}
							if(koords[k][j].getSpielstein().getColor()=='w'){
								if(koords[k-1][j-1].getSpielstein()==null){
									return true;	
								}
							}
						}
						j--;
					}
					j=xAktuell-1;
					for(int k=yAktuell+1;k<5&&j>0;k++){
						if(koords[k][j].getSpielstein()!=null){
							if(koords[k][j].getSpielstein().getColor()=='s'){
								break;
							}
							if(koords[k][j].getSpielstein().getColor()=='w'){
								if(koords[k+1][j-1].getSpielstein()==null){
									return true;	
								}
							}
						}
						j--;
					}
					j=xAktuell+1;
					for(int k=yAktuell+1;k<5&&j<5;k++){
						if(koords[k][j].getSpielstein()!=null){
							if(koords[k][j].getSpielstein().getColor()=='s'){
								break;
							}
							if(koords[k][j].getSpielstein().getColor()=='w'){
								if(koords[k+1][j+1].getSpielstein()==null){
									return true;	
								}
							}
						}
						j++;
					}	
				}else{
					yAktuell=sSteine.get(i).getKoordinate().getY();
					xAktuell=sSteine.get(i).getKoordinate().getX();
					if(yAktuell>1&&xAktuell>1){
						if(koords[yAktuell-1][xAktuell-1].getSpielstein()!=null){
							if(koords[yAktuell-1][xAktuell-1].getSpielstein().getColor()=='w'){
								if(koords[yAktuell-2][xAktuell-2].getSpielstein()==null){
									return true;
								}
							}
						}
					}	
					if(yAktuell>1&&xAktuell<4){
						if(koords[yAktuell-1][xAktuell+1].getSpielstein()!=null){
							if(koords[yAktuell-1][xAktuell+1].getSpielstein().getColor()=='w'){
								if(koords[yAktuell-2][xAktuell+2].getSpielstein()==null){
									return true;
								}
							}
						}
					}		
				}	
			}
		}
		if(runde%2==1){
			for(int i=0;i<wSteine.size();i++){
				if(wSteine.get(i).isLady()==true){
					yAktuell=wSteine.get(i).getKoordinate().getY();
					xAktuell=wSteine.get(i).getKoordinate().getX();
					int j=xAktuell+1;
					for(int k=yAktuell-1;k>0&&j<5;k--){
						if(koords[k][j].getSpielstein()!=null){
							if(koords[k][j].getSpielstein().getColor()=='w'){
								break;
							}
							if(koords[k][j].getSpielstein().getColor()=='s'){
								if(koords[k-1][j+1].getSpielstein()==null){
									return true;	
								}
							}
						}
						j++;
					}
					j=xAktuell-1;
					for(int k=yAktuell-1;k>0&&j>0;k--){
						if(koords[k][j].getSpielstein()!=null){
							if(koords[k][j].getSpielstein().getColor()=='w'){
								break;
							}
							if(koords[k][j].getSpielstein().getColor()=='s'){
								if(koords[k-1][j-1].getSpielstein()==null){
									return true;	
								}
							}
						}
						j--;
					}
					j=xAktuell-1;
					for(int k=yAktuell+1;k<5&&j>0;k++){
						if(koords[k][j].getSpielstein()!=null){
							if(koords[k][j].getSpielstein().getColor()=='w'){
								break;
							}
							if(koords[k][j].getSpielstein().getColor()=='s'){
								if(koords[k+1][j-1].getSpielstein()==null){
									return true;	
								}
							}
						}
						j--;
					}
					j=xAktuell+1;
					for(int k=yAktuell+1;k<5&&j<5;k++){
						if(koords[k][j].getSpielstein()!=null){
							if(koords[k][j].getSpielstein().getColor()=='w'){
								break;
							}
							if(koords[k][j].getSpielstein().getColor()=='s'){
								if(koords[k+1][j+1].getSpielstein()==null){
									return true;	
								}
							}
						}
						j++;
					}	
				}else{
					yAktuell=wSteine.get(i).getKoordinate().getY();
					xAktuell=wSteine.get(i).getKoordinate().getX();
					if(yAktuell<4&&xAktuell>1){
						if(koords[yAktuell+1][xAktuell-1].getSpielstein()!=null){
							if(koords[yAktuell+1][xAktuell-1].getSpielstein().getColor()=='s'){
								if(koords[yAktuell+2][xAktuell-2].getSpielstein()==null){
									return true;
								}
							}
						}
					}	
					if(yAktuell<4&&xAktuell<4){
						if(koords[yAktuell+1][xAktuell+1].getSpielstein()!=null){
							if(koords[yAktuell+1][xAktuell+1].getSpielstein().getColor()=='s'){
								if(koords[yAktuell+2][xAktuell+2].getSpielstein()==null){
									return true;
								}
							}
						}
					}		
				}	
			}
		}
		return false;
	}

	/**
	 * Überpfrüft nach erstem schlagen ob ein zweites schlagen korrekt eingegeben wurde
	 * @param aktuellerZug
	 * @param zweiterZug
	 * @param runde
	 * @return
	 */
	public boolean richtigerZweitZug(String aktuellerZug,String zweiterZug, int runde) {

		int y1 = aktuellerZug.charAt(3) - '0' - 1;
		int x1 = aktuellerZug.charAt(2) - '@' - 1;
		int z;
		int yAktuell = zweiterZug.charAt(1) - '0' - 1;
		int xAktuell = zweiterZug.charAt(0) - '@' - 1;
		int ySoll = zweiterZug.charAt(3) - '0' - 1;
		int xSoll = zweiterZug.charAt(2) - '@' - 1;
		if(runde%2==0){
			z=0;
		}else{
			z=2;
		}
		if(yAktuell==y1&&xAktuell==x1){
			if(ySoll==yAktuell-2+2*z&&xSoll==xAktuell-2){
				if(koords[yAktuell-1+z][xAktuell-1].getSpielstein()!=null){
					return true;
				}
			}
			if(ySoll==yAktuell-2+2*z&&xSoll==xAktuell+2){
				if(koords[yAktuell-1+z][xAktuell+1].getSpielstein()!=null){
					return true;
				}
			}
		}
		return false;
	}
	/**
	 * Überprüft ob ein eingegebener Zug möglich ist
	 * @param aktuellerZug
	 * @param runde
	 * @return
	 */

	public boolean richtigerZug(String aktuellerZug, int runde){

		int xAktuell = aktuellerZug.charAt(1) - '0' - 1;
		int yAktuell = aktuellerZug.charAt(0) - '@' - 1;
		int xSoll = aktuellerZug.charAt(3) - '0' - 1;
		int ySoll = aktuellerZug.charAt(2) - '@' - 1;
		int z;


		if(runde%2 == 1){
			z = 0;
		}else{
			z = 2;
		}
		if(koords[xAktuell][yAktuell].getSpielstein() != null && koords[xSoll][ySoll].getSpielstein() == null){
			if (xAktuell == xSoll + 1-z && (yAktuell == ySoll + 1 || yAktuell == ySoll - 1)) {
				return true;
			}
			if (xAktuell == xSoll + 2-z*2 && (yAktuell == ySoll + 2 || yAktuell == ySoll - 2)) {
				if((ySoll>yAktuell)&&(koords[xAktuell-1+z][yAktuell+1].getSpielstein()!= null)){
					if(koords[xAktuell-1+z][yAktuell+1].getSpielstein().getColor()!=koords[xAktuell][yAktuell].getSpielstein().getColor()){
						return true;
					}
				}
				if((ySoll<yAktuell) && (koords[xAktuell-1+z][yAktuell-1].getSpielstein()!= null)){
					if((koords[xAktuell-1+z][yAktuell-1].getSpielstein().getColor()!=koords[xAktuell][yAktuell].getSpielstein().getColor())){
						return true;
					}
				}
			}
			if(koords[xAktuell][yAktuell].getSpielstein().isLady()==true){
				int i1 = xSoll - xAktuell;
				int i2 = ySoll - yAktuell;
				if (Math.abs(i1) == Math.abs(i2)) {
					return true;
				}
			}
		}
		return false;
	}


	public boolean canMove(int runde){

		return false;
	}

	public String calculateAiMove(int runde) {
		String zug = null;

		if(runde%2 == 0) {
			if(mussSchlagen(runde)) {
				for(int i = 0; i < sSteine.size(); i++) {
					zug = schlagenlogik(runde);
				}
			} else {
				for(int i = 0; i < sSteine.size(); i++) {
					zug = bewegenAi(runde);
				}
			}
		} else {
			if(mussSchlagen(runde)) {
				for(int i = 0; i < wSteine.size(); i++) {
					zug = schlagenlogik(runde);
				}
			} else {
				for(int i = 0; i < wSteine.size(); i++) {
					zug = bewegenAi(runde);
				}
			}
		}
		return zug;
	}

	private String bewegenAi(int runde) {

		if(runde%2 == 0) {
			bewegenLogik(runde, sSteine);
		} else {
			bewegenLogik(runde, wSteine);
		}

		Bewegungsbewertung best = new Bewegungsbewertung(null, -50);

		for(int i = 0; i < 6; i++) {
			for(int j = 0; j < 6; j++) {
				if(moeglicheZuege[i][j].getBewertung() >= best.getBewertung()) {
					best = moeglicheZuege[i][j];
				}
			}
		}
		return best.getZug();
	}

	private void bewegenLogik(int runde, ArrayList<Spielstein> steine) {

		Spielstein stein, temp;
		int bewertung;
		String bewegung;

		//		if(runde%2 == 1) {
		//			for(int i = 0; i < 6; i++) {
		//				for(int j = 0; j < 3; j++) {
		//
		//					temp = koords[i][j].getSpielstein();
		//					koords[i][j].setSpielstein(koords[5 - i][5-j].getSpielstein());
		//					koords[5 - i][5-j].setSpielstein(temp);;
		//
		//
		//					if(koords[i][j].getSpielstein() != null)
		//						koords[i][j].getSpielstein().setKoordinate(new Koordinate(5-j, 5-i, "" + ((char)((5-i)+'A')) + ((char)((5-j)+'1'))));
		//					if(koords[5 - i][5-j].getSpielstein() != null)
		//						koords[5 - i][5-j].getSpielstein().setKoordinate(new Koordinate(j, i, "" + ((char)(i + 'A')) + ((char)(j + '1'))));
		//
		//
		//					if(koords[i][j].getSpielstein() != null)
		//						koords[i][j].getSpielstein().setKoordinate(new Koordinate(5 - j, 5 - i, "" + ((char) (i + 4 + 'A')) + ((char)(j + 5 + '0'))));
		//					if(koords[5 - i][5-j].getSpielstein() != null)
		//						koords[5 - i][5-j].getSpielstein().setKoordinate(new Koordinate(j, i, "" + ((char) (i +'A')) + ((char)(j + '0'))));
		//				}
		//			}
		//		}


		//TODO OutOfBounds durch Abfrage ob >6 <0 entgehen
		//TODO Herausfinden warum die Steine um 90° gedreht sind
		for(int i = 0; i < steine.size(); i++) {
			stein = steine.get(i);
			if(!stein.isLady()) {
				if(runde%2==0) {
					bewegung = stein.getKoordinate().getName() + ((char)(stein.getKoordinate().getName().charAt(0) + 1)) + ((char)(stein.getKoordinate().getName().charAt(1) + 1));
				} else {
					bewegung = stein.getKoordinate().getName() + ((char)(stein.getKoordinate().getName().charAt(0) + 1)) + ((char)(stein.getKoordinate().getName().charAt(1) - 1));
				}
				if(pruefeAufGueltigeKoord(bewegung)) {
					if(richtigerZug(bewegung, runde)) {
						bewertung = bewerten(bewegung, stein, runde);
						Bewegungsbewertung bewegungsbewertung = new Bewegungsbewertung(bewegung, bewertung);
						moeglicheZuege[i][0] = bewegungsbewertung;
					}
				}

				if(runde%2==0) {
					bewegung = stein.getKoordinate().getName() + ((char)(stein.getKoordinate().getName().charAt(0) - 1)) + ((char)(stein.getKoordinate().getName().charAt(1) + 1));
				} else {
					bewegung = stein.getKoordinate().getName() + ((char)(stein.getKoordinate().getName().charAt(0) - 1)) + ((char)(stein.getKoordinate().getName().charAt(1) - 1));
				}
				if(pruefeAufGueltigeKoord(bewegung)) {
					if(richtigerZug(bewegung, runde)) {
						bewertung = bewerten(bewegung, stein, runde);
						Bewegungsbewertung bewegungsbewertung = new Bewegungsbewertung(bewegung, bewertung);
						moeglicheZuege[i][1] = bewegungsbewertung;
					}
				}

			} else {
				bewegung = stein.getKoordinate().getName() + ((char)(stein.getKoordinate().getName().charAt(0) + 1)) + ((char)(stein.getKoordinate().getName().charAt(1) + 1));
				if(pruefeAufGueltigeKoord(bewegung)) {
					if(richtigerZug(bewegung, runde)) {
						bewertung = bewerten(bewegung, stein, runde);
						Bewegungsbewertung bewegungsbewertung = new Bewegungsbewertung(bewegung, bewertung);
						moeglicheZuege[i][0] = bewegungsbewertung;
					}
				}
				bewegung = stein.getKoordinate().getName() + ((char)(stein.getKoordinate().getName().charAt(0) + 1)) + ((char)(stein.getKoordinate().getName().charAt(1) - 1));
				if(pruefeAufGueltigeKoord(bewegung)) {
					if(richtigerZug(bewegung, runde)) {
						bewertung = bewerten(bewegung, stein, runde);
						Bewegungsbewertung bewegungsbewertung = new Bewegungsbewertung(bewegung, bewertung);
						moeglicheZuege[i][1] = bewegungsbewertung;
					}
				}
				bewegung = stein.getKoordinate().getName() + ((char)(stein.getKoordinate().getName().charAt(0) - 1)) + ((char)(stein.getKoordinate().getName().charAt(1) + 1));
				if(pruefeAufGueltigeKoord(bewegung)) {
					if(richtigerZug(bewegung, runde)) {
						bewertung = bewerten(bewegung, stein, runde);
						Bewegungsbewertung bewegungsbewertung = new Bewegungsbewertung(bewegung, bewertung);
						moeglicheZuege[i][2] = bewegungsbewertung;
					}
				}
				bewegung = stein.getKoordinate().getName() + ((char)(stein.getKoordinate().getName().charAt(0) - 1)) + ((char)(stein.getKoordinate().getName().charAt(1) - 1));
				if(pruefeAufGueltigeKoord(bewegung)) {
					if(richtigerZug(bewegung, runde)) {
						bewertung = bewerten(bewegung, stein, runde);
						Bewegungsbewertung bewegungsbewertung = new Bewegungsbewertung(bewegung, bewertung);
						moeglicheZuege[i][3] = bewegungsbewertung;
					}
				}
			}
		}

		//		if(runde%2 == 1) {
		//			for(int i = 0; i < 3; i++) {
		//				for(int j = 0; j < 6; j++) {
		//
		//					temp = koords[i][j].getSpielstein();
		//					koords[i][j].setSpielstein(koords[5 - i][5-j].getSpielstein());
		//					koords[5 - i][5-j].setSpielstein(temp);;
		//					koords[i][j].getSpielstein().setKoordinate(new Koordinate(j, i, "" + ((char) i + 'A') + j));
		//					koords[5 - i][5-j].getSpielstein().setKoordinate(new Koordinate(5 - j, 5 - i, "" + ((char) (i - 5) + 'A') + (j-5)));
		//				}
		//			}
		//		}
	}

	private String schlagenlogik(int runde) {

		return null;
	}

	private int bewerten(String bewegung, Spielstein stein, int runde) {

		int xAktuell, xSoll, yAktuell, ySoll, xBewegung, yBewegung, bewertung = 0;

		xAktuell = bewegung.charAt(0) - '@' - 1;
		yAktuell = bewegung.charAt(1) - '0' - 1;
		xSoll = bewegung.charAt(2) - '@' - 1;
		ySoll = bewegung.charAt(3) - '0' - 1;
		xBewegung = xAktuell - xSoll;
		yBewegung = yAktuell - ySoll;



		if(koords[ySoll - yBewegung][xSoll - xBewegung].getSpielstein() != null) {
			if(runde%2 == 0) {
				if(koords[ySoll - yBewegung][xSoll - xBewegung].getSpielstein().getColor() == 'w') {
					if(stein.isLady()) {
						bewertung -= 20;
					} else {
						bewertung -= 10;
					}
				}
			} else {
				if(koords[ySoll - yBewegung][xSoll - xBewegung].getSpielstein().getColor() == 's') {
					if(stein.isLady()) {
						bewertung -= 20;
					} else {
						bewertung -= 10;
					}				}
			}
		} 
		if(koords[ySoll + yBewegung][xSoll - xBewegung].getSpielstein() != null) {
			if(runde%2 == 0){
				if(koords[ySoll + yBewegung][xSoll - xBewegung].getSpielstein().getColor() == 'w') {
					if(koords[ySoll - yBewegung][xSoll + xBewegung].getSpielstein() == null) {
						if(stein.isLady()) {
							bewertung -= 20;
						} else {
							bewertung -= 10;
						}					}
				}
			} else {
				if(koords[ySoll + yBewegung][xSoll - xBewegung].getSpielstein().getColor() == 's') {
					if(koords[ySoll - yBewegung][xSoll + xBewegung].getSpielstein() == null) {
						if(stein.isLady()) {
							bewertung -= 20;
						} else {
							bewertung -= 10;
						}					}
				}
			}
		} 
		if(koords[ySoll - yBewegung][xSoll + xBewegung].getSpielstein() != null) {
			if(runde%2 == 0) {
				if(koords[ySoll - yBewegung][xSoll + xBewegung].getSpielstein().getColor() == 'w') {
					if(koords[ySoll + yBewegung][xSoll - xBewegung].getSpielstein() == null) {
						if(stein.isLady()) {
							bewertung -= 20;
						} else {
							bewertung -= 10;
						}					}
				}
			} else {
				if(koords[ySoll - yBewegung][xSoll + xBewegung].getSpielstein().getColor() == 's') {
					if(koords[ySoll + yBewegung][xSoll - xBewegung].getSpielstein() == null) {
						if(stein.isLady()) {
							bewertung -= 20;
						} else {
							bewertung -= 10;
						}					}
				}
			}
		}

		if(stein.isLady()) {
			if(bewertung < -20) {
				bewertung = -20;
			} 
		} else {
			if(bewertung < -10) {
				bewertung = -10;
			}
		}

		return bewertung;
	}



}
