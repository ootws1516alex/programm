package dominik;

import java.util.ArrayList;

import com.sun.org.apache.bcel.internal.generic.LSTORE;

public abstract class Spiellogik {

	private Koordinate koords[][];
	private Spieler spieler1,spieler2;
	private Ausgabe ausgabe;
	private ArrayList<Spielstein> wSteine;
	private ArrayList<Spielstein> sSteine;
	private Bewegungsbewertung moeglicheZuege[][];

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
		for (int i = 0; i < aktuellerZug.length() ; i++) {
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

					if(yAktuell<4&&xAktuell<4){
						if(koords[yAktuell+1][xAktuell+1].getSpielstein()!=null){
							if(koords[yAktuell+1][xAktuell+1].getSpielstein().getColor()=='w'){
								if(koords[yAktuell+2][xAktuell+2].getSpielstein()==null){
									return true;
								}
							}
						}
					}	
					if(yAktuell<4&&xAktuell>1){
						if(koords[yAktuell+1][xAktuell-1].getSpielstein()!=null){
							if(koords[yAktuell+1][xAktuell-1].getSpielstein().getColor()=='w'){
								if(koords[yAktuell+2][xAktuell-2].getSpielstein()==null){
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

					if(yAktuell>1&&xAktuell>1){
						if(koords[yAktuell-1][xAktuell-1].getSpielstein()!=null){
							if(koords[yAktuell-1][xAktuell-1].getSpielstein().getColor()=='s'){
								if(koords[yAktuell-2][xAktuell-2].getSpielstein()==null){
									return true;
								}
							}
						}
					}	
					if(yAktuell>1&&xAktuell<4){
						if(koords[yAktuell-1][xAktuell+1].getSpielstein()!=null){
							if(koords[yAktuell-1][xAktuell+1].getSpielstein().getColor()=='s'){
								if(koords[yAktuell-2][xAktuell+2].getSpielstein()==null){
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
		int xBewegung;
		int yBewegung;
		int z;


		if(runde%2==1){
			z=0;
		}else{
			z=2;
		}

		if(koords[xSoll][ySoll].getSpielstein() != null) {
			return false;
		}

		if(koords[xAktuell][yAktuell].getSpielstein() != null && koords[xSoll][ySoll].getSpielstein()==null){
			if (xAktuell == xSoll + 1-z && (yAktuell == ySoll + 1 || yAktuell == ySoll - 1)) {
				return true;
			}
		}

		if((xAktuell == xSoll + 2||xAktuell == xSoll - 2) && (yAktuell == ySoll + 2 || yAktuell == ySoll - 2)) {

			if(xAktuell + 1 < 6 && yAktuell + 1 < 6)
				if((koords[xAktuell+1][yAktuell+1].getSpielstein()!=null)){
					if(koords[xAktuell+1][yAktuell+1].getSpielstein().getColor()!=koords[xAktuell][yAktuell].getSpielstein().getColor()){
						if(xAktuell+2 == xSoll && yAktuell+2 == ySoll) {
							return true;
						}
					}
				}
			if(xAktuell + 1 < 6 && yAktuell - 1 >= 0)
				if((koords[xAktuell+1][yAktuell-1].getSpielstein()!=null)){
					if((koords[xAktuell+1][yAktuell-1].getSpielstein().getColor()!=koords[xAktuell][yAktuell].getSpielstein().getColor())){
						if(xAktuell+2 == xSoll && yAktuell-2 == ySoll) {
							return true;
						}
					}
				}
			if(xAktuell - 1 >= 0 && yAktuell + 1 < 6)
				if((koords[xAktuell-1][yAktuell+1].getSpielstein()!=null)){
					if(koords[xAktuell-1][yAktuell+1].getSpielstein().getColor()!=koords[xAktuell][yAktuell].getSpielstein().getColor()){
						if(xAktuell-2 == xSoll && yAktuell+2 == ySoll) {
							return true;
						}
					}
				}
			if(xAktuell - 1 >= 0 && yAktuell - 1 >= 0)
				if((koords[xAktuell-1][yAktuell-1].getSpielstein()!=null)){
					if((koords[xAktuell-1][yAktuell-1].getSpielstein().getColor()!=koords[xAktuell][yAktuell].getSpielstein().getColor())){
						if(xAktuell-2 == xSoll && yAktuell-2 == ySoll) {
							return true;
						}
					}
				}
		}



		//		if((xAktuell == xSoll + 2||xAktuell == xSoll - 2) && (yAktuell == ySoll + 2 || yAktuell == ySoll - 2)) {	
		//
		//			if(xAktuell + 1 < 6 && yAktuell + 1 < 6)
		//				if((koords[xAktuell+1][yAktuell+1].getSpielstein()!=null)){
		//					if(koords[xAktuell+1][yAktuell+1].getSpielstein().getColor()!=koords[xAktuell][yAktuell].getSpielstein().getColor()){
		//						return true;
		//					}
		//				}
		//			if(xAktuell + 1 < 6 && yAktuell - 1 >= 0)
		//				if((koords[xAktuell+1][yAktuell-1].getSpielstein()!=null)){
		//					if((koords[xAktuell+1][yAktuell-1].getSpielstein().getColor()!=koords[xAktuell][yAktuell].getSpielstein().getColor())){
		//						return true;
		//					}
		//				}
		//			if(xAktuell - 1 >= 0 && yAktuell + 1 < 6)
		//				if((koords[xAktuell-1][yAktuell+1].getSpielstein()!=null)){
		//					if(koords[xAktuell-1][yAktuell+1].getSpielstein().getColor()!=koords[xAktuell][yAktuell].getSpielstein().getColor()){
		//						return true;
		//					}
		//				}
		//			if(xAktuell - 1 >= 0 && yAktuell - 1 >= 0)
		//				if((koords[xAktuell-1][yAktuell-1].getSpielstein()!=null)){
		//					if((koords[xAktuell-1][yAktuell-1].getSpielstein().getColor()!=koords[xAktuell][yAktuell].getSpielstein().getColor())){
		//						return true;
		//					}
		//				}
		//		}

		if(koords[xAktuell][yAktuell].getSpielstein() != null) {
			if(koords[xAktuell][yAktuell].getSpielstein().isLady()==true){

				xBewegung = xAktuell - xSoll;
				yBewegung = yAktuell - ySoll;
				int xBewegungsRichtung, yBewegungsRichtung;

				if(xBewegung < 0)
					yBewegungsRichtung = -1;
				else
					yBewegungsRichtung = 1;
				if(yBewegung < 0)
					xBewegungsRichtung = -1;
				else
					xBewegungsRichtung = 1;


				if(Math.abs(xBewegung) == Math.abs(yBewegung)) {
					for(int i = 1; i <= Math.abs(xBewegung); i++) {
						if((xAktuell+(i*xBewegungsRichtung) >= 0 && xAktuell+(i*xBewegungsRichtung) < 6) && (yAktuell+(i*yBewegungsRichtung) < 6 && yAktuell+(i*yBewegungsRichtung) >= 0)) {
							if(koords[xAktuell+(xBewegungsRichtung*i)][yAktuell+(yBewegungsRichtung*i)].getSpielstein() != null) {
								if(koords[xAktuell+(xBewegungsRichtung*i)][yAktuell+(yBewegungsRichtung*i)].getSpielstein().getColor() == koords[xAktuell][yAktuell].getSpielstein().getColor()) {
									return false;
								} else {
									if(xAktuell+(xBewegungsRichtung*(i+1)) == xSoll && yAktuell+(yBewegungsRichtung*(i+1)) == ySoll) {
										if(koords[xSoll][ySoll].getSpielstein() == null) {
											return true;
										}
									}
								}
							}
						}
					}
				}
			}
		}


		//					if(koords[xAktuell][yAktuell].getSpielstein().isLady()==true){
		//						int i1 = xSoll - xAktuell;
		//						int i2 = ySoll - yAktuell;
		//						if (Math.abs(i1) == Math.abs(i2)) {
		//							return true;
		//						}
		//					}

		return false;
	}


	public boolean canMove(int runde){
		//TODO pruefen ob Spieler noch ziehen/schlagen kann
		return false;
	}

	public String calculateAiMove(int runde) {
		String zug = null;

		if(runde%2 == 0) {
			if(mussSchlagen(runde)) {
				zug = schlagenAi(runde);
			} else {
				zug = bewegenAi(runde);
			}
		} else {
			if(mussSchlagen(runde)) {
				zug = schlagenAi(runde);
			} else {
				zug = bewegenAi(runde);
			}
		}
		return zug;
	}

	private String bewegenAi(int runde) {

		int listGroesse;

		if(runde%2 == 0) {
			bewegenLogik(runde, sSteine);
			listGroesse = sSteine.size();
		} else {
			bewegenLogik(runde, wSteine);
			listGroesse = wSteine.size();
		}

		Bewegungsbewertung best = new Bewegungsbewertung(null, -50);
		ArrayList<Bewegungsbewertung> bestList = new ArrayList();

		bestList.add(best);

		for(int i = 0; i < listGroesse; i++) {
			for(int j = 0; j < 9; j++) {
				if(moeglicheZuege[i][j] != null) {
					if(richtigerZug(moeglicheZuege[i][j].getZug(), runde)) {
						if(moeglicheZuege[i][j].getBewertung() > bestList.get(0).getBewertung()) {
							bestList.clear();
							bestList.add(moeglicheZuege[i][j]);
						} else if (moeglicheZuege[i][j].getBewertung() == bestList.get(0).getBewertung()){
							bestList.add(moeglicheZuege[i][j]);
						}
					}
				}
			}
		}

		best = bestList.get((int)(Math.random()*(bestList.size())));

		return best.getZug();
	}

	private void bewegenLogik(int runde, ArrayList<Spielstein> steine) {

		Spielstein stein;
		int bewertung, counter;
		String bewegung;
		moeglicheZuege = new Bewegungsbewertung[steine.size()][9];


		for(int i = 0; i < steine.size(); i++) {
			counter = 0;
			stein = steine.get(i);
			if(!stein.isLady()) {
				if(runde%2==0) {
					bewegung = stein.getKoordinate().getName() + ((char)(stein.getKoordinate().getName().charAt(0) + 1)) + ((char)(stein.getKoordinate().getName().charAt(1) + 1));
				} else {
					bewegung = stein.getKoordinate().getName() + ((char)(stein.getKoordinate().getName().charAt(0) + 1)) + ((char)(stein.getKoordinate().getName().charAt(1) - 1));
				}
				if(pruefeAufGueltigeKoord(bewegung)) {
					if(richtigerZug(bewegung, runde)) {
						bewertung = bewertenBewegen(bewegung, stein, runde);
						Bewegungsbewertung bewegungsbewertung = new Bewegungsbewertung(bewegung, bewertung);
						moeglicheZuege[i][counter] = bewegungsbewertung;
						counter++;	
					}
				}

				if(runde%2==0) {
					bewegung = stein.getKoordinate().getName() + ((char)(stein.getKoordinate().getName().charAt(0) - 1)) + ((char)(stein.getKoordinate().getName().charAt(1) + 1));
				} else {
					bewegung = stein.getKoordinate().getName() + ((char)(stein.getKoordinate().getName().charAt(0) - 1)) + ((char)(stein.getKoordinate().getName().charAt(1) - 1));
				}
				if(pruefeAufGueltigeKoord(bewegung)) {
					if(richtigerZug(bewegung, runde)) {
						bewertung = bewertenBewegen(bewegung, stein, runde);
						Bewegungsbewertung bewegungsbewertung = new Bewegungsbewertung(bewegung, bewertung);
						moeglicheZuege[i][counter] = bewegungsbewertung;
						counter++;
					}
				}

			} else {

				for(int j = 1; j <= 5; j++) {
					bewegung = stein.getKoordinate().getName() + ((char)(stein.getKoordinate().getName().charAt(0) + j)) + ((char)(stein.getKoordinate().getName().charAt(1) + j));
					if(pruefeAufGueltigeKoord(bewegung)) {
						if(richtigerZug(bewegung, runde)) {
							bewertung = bewertenBewegen(bewegung, stein, runde, j);
							Bewegungsbewertung bewegungsbewertung = new Bewegungsbewertung(bewegung, bewertung);
							moeglicheZuege[i][counter] = bewegungsbewertung;
							counter++;
						}
					}
					bewegung = stein.getKoordinate().getName() + ((char)(stein.getKoordinate().getName().charAt(0) + j)) + ((char)(stein.getKoordinate().getName().charAt(1) - j));
					if(pruefeAufGueltigeKoord(bewegung)) {
						if(richtigerZug(bewegung, runde)) {
							bewertung = bewertenBewegen(bewegung, stein, runde, j);
							Bewegungsbewertung bewegungsbewertung = new Bewegungsbewertung(bewegung, bewertung);
							moeglicheZuege[i][counter] = bewegungsbewertung;
							counter++;
						}
					}
					bewegung = stein.getKoordinate().getName() + ((char)(stein.getKoordinate().getName().charAt(0) - j)) + ((char)(stein.getKoordinate().getName().charAt(1) + j));
					if(pruefeAufGueltigeKoord(bewegung)) {
						if(richtigerZug(bewegung, runde)) {
							bewertung = bewertenBewegen(bewegung, stein, runde, j);
							Bewegungsbewertung bewegungsbewertung = new Bewegungsbewertung(bewegung, bewertung);
							moeglicheZuege[i][counter] = bewegungsbewertung;
							counter++;
						}
					}
					bewegung = stein.getKoordinate().getName() + ((char)(stein.getKoordinate().getName().charAt(0) - j)) + ((char)(stein.getKoordinate().getName().charAt(1) - j));
					if(pruefeAufGueltigeKoord(bewegung)) {
						if(richtigerZug(bewegung, runde)) {
							bewertung = bewertenBewegen(bewegung, stein, runde, j);
							Bewegungsbewertung bewegungsbewertung = new Bewegungsbewertung(bewegung, bewertung);
							moeglicheZuege[i][counter] = bewegungsbewertung;
							counter++;
						}
					}
				}

				//				bewegung = stein.getKoordinate().getName() + ((char)(stein.getKoordinate().getName().charAt(0) + 1)) + ((char)(stein.getKoordinate().getName().charAt(1) + 1));
				//				if(pruefeAufGueltigeKoord(bewegung)) {
				//					if(richtigerZug(bewegung, runde)) {
				//						bewertung = bewertenBewegen(bewegung, stein, runde);
				//						Bewegungsbewertung bewegungsbewertung = new Bewegungsbewertung(bewegung, bewertung);
				//						moeglicheZuege[i][0] = bewegungsbewertung;
				//					}
				//				}
				//				bewegung = stein.getKoordinate().getName() + ((char)(stein.getKoordinate().getName().charAt(0) + 1)) + ((char)(stein.getKoordinate().getName().charAt(1) - 1));
				//				if(pruefeAufGueltigeKoord(bewegung)) {
				//					if(richtigerZug(bewegung, runde)) {
				//						bewertung = bewertenBewegen(bewegung, stein, runde);
				//						Bewegungsbewertung bewegungsbewertung = new Bewegungsbewertung(bewegung, bewertung);
				//						moeglicheZuege[i][1] = bewegungsbewertung;
				//					}
				//				}
				//				bewegung = stein.getKoordinate().getName() + ((char)(stein.getKoordinate().getName().charAt(0) - 1)) + ((char)(stein.getKoordinate().getName().charAt(1) + 1));
				//				if(pruefeAufGueltigeKoord(bewegung)) {
				//					if(richtigerZug(bewegung, runde)) {
				//						bewertung = bewertenBewegen(bewegung, stein, runde);
				//						Bewegungsbewertung bewegungsbewertung = new Bewegungsbewertung(bewegung, bewertung);
				//						moeglicheZuege[i][2] = bewegungsbewertung;
				//					}
				//				}
				//				bewegung = stein.getKoordinate().getName() + ((char)(stein.getKoordinate().getName().charAt(0) - 1)) + ((char)(stein.getKoordinate().getName().charAt(1) - 1));
				//				if(pruefeAufGueltigeKoord(bewegung)) {
				//					if(richtigerZug(bewegung, runde)) {
				//						bewertung = bewertenBewegen(bewegung, stein, runde);
				//						Bewegungsbewertung bewegungsbewertung = new Bewegungsbewertung(bewegung, bewertung);
				//						moeglicheZuege[i][3] = bewegungsbewertung;
				//					}
				//				}
			}
		}
	}

	private String schlagenAi(int runde) {

		int listGroesse;

		if(runde%2 == 0) {
			schlagenLogik(runde, sSteine);
			listGroesse = sSteine.size();
		} else {
			schlagenLogik(runde, wSteine);
			listGroesse = wSteine.size();
		}

		Bewegungsbewertung best = new Bewegungsbewertung(null, -50);
		ArrayList<Bewegungsbewertung> bestList = new ArrayList();

		bestList.add(best);

		for(int i = 0; i < listGroesse; i++) {
			for(int j = 0; j < 9; j++) {
				if(moeglicheZuege[i][j] != null) {
					if(richtigerZug(moeglicheZuege[i][j].getZug(), runde)) {
						if(moeglicheZuege[i][j].getBewertung() > bestList.get(0).getBewertung()) {
							bestList.clear();
							bestList.add(moeglicheZuege[i][j]);
						} else if (moeglicheZuege[i][j].getBewertung() == bestList.get(0).getBewertung()){
							bestList.add(moeglicheZuege[i][j]);
						}
					}
				}
			}
		}

		best = bestList.get((int)(Math.random()*(bestList.size())));

		return best.getZug();
	}

	private void schlagenLogik(int runde, ArrayList<Spielstein> steine) {
		Spielstein stein;
		int bewertung, counter;
		String bewegung;
		moeglicheZuege = new Bewegungsbewertung[steine.size()][9];


		for(int i = 0; i < steine.size(); i++) {
			counter = 0;
			stein = steine.get(i);
			if(!stein.isLady()) {	
				bewegung = stein.getKoordinate().getName() + ((char)(stein.getKoordinate().getName().charAt(0) + 2)) + ((char)(stein.getKoordinate().getName().charAt(1) + 2));
				if(pruefeAufGueltigeKoord(bewegung)) {
					if(richtigerZug(bewegung, runde)) {
						bewertung = bewertenSchlagen(bewegung, stein, runde);
						Bewegungsbewertung bewegungsbewertung = new Bewegungsbewertung(bewegung, bewertung);
						moeglicheZuege[i][counter] = bewegungsbewertung;
						counter++;
					}
				}

				bewegung = stein.getKoordinate().getName() + ((char)(stein.getKoordinate().getName().charAt(0) + 2)) + ((char)(stein.getKoordinate().getName().charAt(1) - 2));
				if(pruefeAufGueltigeKoord(bewegung)) {
					if(richtigerZug(bewegung, runde)) {
						bewertung = bewertenSchlagen(bewegung, stein, runde);
						Bewegungsbewertung bewegungsbewertung = new Bewegungsbewertung(bewegung, bewertung);
						moeglicheZuege[i][counter] = bewegungsbewertung;
						counter++;
					}
				}

				bewegung = stein.getKoordinate().getName() + ((char)(stein.getKoordinate().getName().charAt(0) - 2)) + ((char)(stein.getKoordinate().getName().charAt(1) + 2));
				if(pruefeAufGueltigeKoord(bewegung)) {
					if(richtigerZug(bewegung, runde)) {
						bewertung = bewertenSchlagen(bewegung, stein, runde);
						Bewegungsbewertung bewegungsbewertung = new Bewegungsbewertung(bewegung, bewertung);
						moeglicheZuege[i][counter] = bewegungsbewertung;
						counter++;
					}
				}

				bewegung = stein.getKoordinate().getName() + ((char)(stein.getKoordinate().getName().charAt(0) - 2)) + ((char)(stein.getKoordinate().getName().charAt(1) - 2));
				if(pruefeAufGueltigeKoord(bewegung)) {
					if(richtigerZug(bewegung, runde)) {
						bewertung = bewertenSchlagen(bewegung, stein, runde);
						Bewegungsbewertung bewegungsbewertung = new Bewegungsbewertung(bewegung, bewertung);
						moeglicheZuege[i][counter] = bewegungsbewertung;
						counter++;
					}
				}


			} else {

				for(int j = 1; j <= 5; j++) {
					bewegung = stein.getKoordinate().getName() + ((char)(stein.getKoordinate().getName().charAt(0) + 2 + j)) + ((char)(stein.getKoordinate().getName().charAt(1) + 2 + j));
					if(pruefeAufGueltigeKoord(bewegung)) {
						if(richtigerZug(bewegung, runde)) {
							bewertung = bewertenSchlagen(bewegung, stein, runde, j);
							Bewegungsbewertung bewegungsbewertung = new Bewegungsbewertung(bewegung, bewertung);
							moeglicheZuege[i][counter] = bewegungsbewertung;
							counter++;
						}
					}

					bewegung = stein.getKoordinate().getName() + ((char)(stein.getKoordinate().getName().charAt(0) + 2 + j)) + ((char)(stein.getKoordinate().getName().charAt(1) - 2 - j));
					if(pruefeAufGueltigeKoord(bewegung)) {
						if(richtigerZug(bewegung, runde)) {
							bewertung = bewertenSchlagen(bewegung, stein, runde, j);
							Bewegungsbewertung bewegungsbewertung = new Bewegungsbewertung(bewegung, bewertung);
							moeglicheZuege[i][counter] = bewegungsbewertung;
							counter++;
						}
					}

					bewegung = stein.getKoordinate().getName() + ((char)(stein.getKoordinate().getName().charAt(0) - 2 - j)) + ((char)(stein.getKoordinate().getName().charAt(1) + 2 + j));
					if(pruefeAufGueltigeKoord(bewegung)) {
						if(richtigerZug(bewegung, runde)) {
							bewertung = bewertenSchlagen(bewegung, stein, runde, j);
							Bewegungsbewertung bewegungsbewertung = new Bewegungsbewertung(bewegung, bewertung);
							moeglicheZuege[i][counter] = bewegungsbewertung;
							counter++;
						}
					}

					bewegung = stein.getKoordinate().getName() + ((char)(stein.getKoordinate().getName().charAt(0) - 2 - j)) + ((char)(stein.getKoordinate().getName().charAt(1) - 2 - j));
					if(pruefeAufGueltigeKoord(bewegung)) {
						if(richtigerZug(bewegung, runde)) {
							bewertung = bewertenSchlagen(bewegung, stein, runde, j);
							Bewegungsbewertung bewegungsbewertung = new Bewegungsbewertung(bewegung, bewertung);
							moeglicheZuege[i][counter] = bewegungsbewertung;
							counter++;
						}
					}
				}
			}
		}
	}





//TODO dame muss rückwärs laufen









	private int bewertenSchlagen(String bewegung, Spielstein stein, int runde) {

		int xAktuell, xSoll, yAktuell, ySoll, xBewegung, yBewegung, bewertung = 0;

		xAktuell = bewegung.charAt(0) - '@' - 1;
		yAktuell = bewegung.charAt(1) - '0' - 1;
		xSoll = bewegung.charAt(2) - '@' - 1;
		ySoll = bewegung.charAt(3) - '0' - 1;
		xBewegung = (xAktuell - xSoll)/2;
		yBewegung = (yAktuell - ySoll)/2;


		pruefeAufGueltigeKoord(bewegung);


		if(ySoll-yBewegung >= 0 && ySoll-yBewegung <= 5 && xSoll-xBewegung >= 0 && xSoll-xBewegung <= 5) {
			if(koords[ySoll - yBewegung][xSoll - xBewegung].getSpielstein() != null) {
				if(runde%2 == 0) {
					if(koords[ySoll - yBewegung][xSoll - xBewegung].getSpielstein().getColor() == 'w') {
						bewertung -= 1;
					}
				} else {
					if(koords[ySoll - yBewegung][xSoll - xBewegung].getSpielstein().getColor() == 's') {
						bewertung -= 1;		

					}
				} 
			}
		}

		if(ySoll+yBewegung >= 0 && ySoll+yBewegung <= 5 && xSoll-xBewegung >= 0 && xSoll-xBewegung <= 5) {
			if(koords[ySoll + yBewegung][xSoll - xBewegung].getSpielstein() != null) {
				if(runde%2 == 0){
					if(koords[ySoll + yBewegung][xSoll - xBewegung].getSpielstein().getColor() == 'w') {
						if(ySoll-yBewegung >= 0 && ySoll-yBewegung <= 5 && xSoll+xBewegung >= 0 && xSoll+xBewegung <= 5)
							if(koords[ySoll - yBewegung][xSoll + xBewegung].getSpielstein() == null) {
								bewertung -= 1;		
							}
					}
				} else {
					if(koords[ySoll + yBewegung][xSoll - xBewegung].getSpielstein().getColor() == 's') {
						if(ySoll-yBewegung >= 0 && ySoll-yBewegung <= 5 && xSoll+xBewegung >= 0 && xSoll+xBewegung <= 5)
							if(koords[ySoll - yBewegung][xSoll + xBewegung].getSpielstein() == null) {
								bewertung -= 1;
							}
					}
				}
			} 
		}

		if(ySoll-yBewegung >= 0 && ySoll-yBewegung <= 5 && xSoll+xBewegung >= 0 && xSoll+xBewegung <= 5) {
			if(koords[ySoll - yBewegung][xSoll + xBewegung].getSpielstein() != null) {
				if(runde%2 == 0) {
					if(koords[ySoll - yBewegung][xSoll + xBewegung].getSpielstein().getColor() == 'w') {
						if(ySoll+yBewegung >= 0 && ySoll+yBewegung <= 5 && xSoll-xBewegung >= 0 && xSoll-xBewegung <= 5)
							if(koords[ySoll + yBewegung][xSoll - xBewegung].getSpielstein() == null) {
								bewertung -= 1;			
							}
					}
				} else {
					if(koords[ySoll - yBewegung][xSoll + xBewegung].getSpielstein().getColor() == 's') {
						if(ySoll+yBewegung >= 0 && ySoll+yBewegung <= 5 && xSoll-xBewegung >= 0 && xSoll-xBewegung <= 5)
							if(koords[ySoll + yBewegung][xSoll - xBewegung].getSpielstein() == null) {
								bewertung -= 1;
							}
					}
				}
			}
		}

		if(bewertung < -1) {
			bewertung = -1;
		}

		return bewertung;
	}

	private int bewertenSchlagen(String bewegung, Spielstein stein, int runde, int j) {

		int xAktuell, xSoll, yAktuell, ySoll, xBewegung, yBewegung, bewertung = 0;

		xAktuell = bewegung.charAt(0) - '@' - 1;
		yAktuell = bewegung.charAt(1) - '0' - 1;
		xSoll = bewegung.charAt(2) - '@' - 1;
		ySoll = bewegung.charAt(3) - '0' - 1;
		xBewegung = xAktuell - xSoll;
		yBewegung = yAktuell - ySoll;

		if(xBewegung > 1 || xBewegung < -1)
			xBewegung /= j;
		if(yBewegung > 1 || yBewegung < -1)
			yBewegung /= j;


		pruefeAufGueltigeKoord(bewegung);


		if(ySoll-yBewegung >= 0 && ySoll-yBewegung <= 5 && xSoll-xBewegung >= 0 && xSoll-xBewegung <= 5) {
			if(koords[ySoll - yBewegung][xSoll - xBewegung].getSpielstein() != null) {
				if(runde%2 == 0) {
					if(koords[ySoll - yBewegung][xSoll - xBewegung].getSpielstein().getColor() == 'w') {
						bewertung -= 2;
					}
				} else {
					if(koords[ySoll - yBewegung][xSoll - xBewegung].getSpielstein().getColor() == 's') {
						bewertung -= 2;		

					}
				} 
			}
		}

		if(ySoll+yBewegung >= 0 && ySoll+yBewegung <= 5 && xSoll-xBewegung >= 0 && xSoll-xBewegung <= 5) {
			if(koords[ySoll + yBewegung][xSoll - xBewegung].getSpielstein() != null) {
				if(runde%2 == 0){
					if(koords[ySoll + yBewegung][xSoll - xBewegung].getSpielstein().getColor() == 'w') {
						if(ySoll-yBewegung >= 0 && ySoll-yBewegung <= 5 && xSoll+xBewegung >= 0 && xSoll+xBewegung <= 5)
							if(koords[ySoll - yBewegung][xSoll + xBewegung].getSpielstein() == null) {
								bewertung -= 2;		
							}
					}
				} else {
					if(koords[ySoll + yBewegung][xSoll - xBewegung].getSpielstein().getColor() == 's') {
						if(ySoll-yBewegung >= 0 && ySoll-yBewegung <= 5 && xSoll+xBewegung >= 0 && xSoll+xBewegung <= 5)
							if(koords[ySoll - yBewegung][xSoll + xBewegung].getSpielstein() == null) {
								bewertung -= 2;
							}
					}
				}
			} 
		}

		if(ySoll-yBewegung >= 0 && ySoll-yBewegung <= 5 && xSoll+xBewegung >= 0 && xSoll+xBewegung <= 5) {
			if(koords[ySoll - yBewegung][xSoll + xBewegung].getSpielstein() != null) {
				if(runde%2 == 0) {
					if(koords[ySoll - yBewegung][xSoll + xBewegung].getSpielstein().getColor() == 'w') {
						if(ySoll+yBewegung >= 0 && ySoll+yBewegung <= 5 && xSoll-xBewegung >= 0 && xSoll-xBewegung <= 5)
							if(koords[ySoll + yBewegung][xSoll - xBewegung].getSpielstein() == null) {
								bewertung -= 2;			
							}
					}
				} else {
					if(koords[ySoll - yBewegung][xSoll + xBewegung].getSpielstein().getColor() == 's') {
						if(ySoll+yBewegung >= 0 && ySoll+yBewegung <= 5 && xSoll-xBewegung >= 0 && xSoll-xBewegung <= 5)
							if(koords[ySoll + yBewegung][xSoll - xBewegung].getSpielstein() == null) {
								bewertung -= 2;
							}
					}
				}
			}
		}

		if(bewertung < -2) {
			bewertung = -2;
		}

		return bewertung;
	}










	/**
	 * 
	 * @param bewegung Zug der Ausgeführt wird
	 * @param stein der bewegt wird
	 * @param runde
	 * @return asd bewertung für den zug
	 */
	private int bewertenBewegen(String bewegung, Spielstein stein, int runde) {

		int xAktuell, xSoll, yAktuell, ySoll, xBewegung, yBewegung, bewertung = 0;

		xAktuell = bewegung.charAt(0) - '@' - 1;
		yAktuell = bewegung.charAt(1) - '0' - 1;
		xSoll = bewegung.charAt(2) - '@' - 1;
		ySoll = bewegung.charAt(3) - '0' - 1;
		xBewegung = xAktuell - xSoll;
		yBewegung = yAktuell - ySoll;


		pruefeAufGueltigeKoord(bewegung);


		if(ySoll-yBewegung >= 0 && ySoll-yBewegung <= 5 && xSoll-xBewegung >= 0 && xSoll-xBewegung <= 5) {
			if(koords[ySoll - yBewegung][xSoll - xBewegung].getSpielstein() != null) {
				if(runde%2 == 0) {
					if(koords[ySoll - yBewegung][xSoll - xBewegung].getSpielstein().getColor() == 'w') {
						bewertung -= 10;
					}
				} else {
					if(koords[ySoll - yBewegung][xSoll - xBewegung].getSpielstein().getColor() == 's') {
						bewertung -= 10;			
					}
				}
			} 
		}

		if(ySoll+yBewegung >= 0 && ySoll+yBewegung <= 5 && xSoll-xBewegung >= 0 && xSoll-xBewegung <= 5) {
			if(koords[ySoll + yBewegung][xSoll - xBewegung].getSpielstein() != null) {
				if(runde%2 == 0){
					if(koords[ySoll + yBewegung][xSoll - xBewegung].getSpielstein().getColor() == 'w') {
						if(ySoll-yBewegung >= 0 && ySoll-yBewegung <= 5 && xSoll+xBewegung >= 0 && xSoll+xBewegung <= 5)
							if(koords[ySoll - yBewegung][xSoll + xBewegung].getSpielstein() == null) {
								bewertung -= 10;			
							}
					}
				} else {
					if(koords[ySoll + yBewegung][xSoll - xBewegung].getSpielstein().getColor() == 's') {
						if(ySoll-yBewegung >= 0 && ySoll-yBewegung <= 5 && xSoll+xBewegung >= 0 && xSoll+xBewegung <= 5)
							if(koords[ySoll - yBewegung][xSoll + xBewegung].getSpielstein() == null) {
								bewertung -= 10;
							}
					}
				}
			} 
		}

		if(ySoll-yBewegung >= 0 && ySoll-yBewegung <= 5 && xSoll+xBewegung >= 0 && xSoll+xBewegung <= 5) {
			if(koords[ySoll - yBewegung][xSoll + xBewegung].getSpielstein() != null) {
				if(runde%2 == 0) {
					if(koords[ySoll - yBewegung][xSoll + xBewegung].getSpielstein().getColor() == 'w') {
						if(ySoll+yBewegung >= 0 && ySoll+yBewegung <= 5 && xSoll-xBewegung >= 0 && xSoll-xBewegung <= 5)
							if(koords[ySoll + yBewegung][xSoll - xBewegung].getSpielstein() == null) {
								bewertung -= 10;			
							}
					}
				} else {
					if(koords[ySoll - yBewegung][xSoll + xBewegung].getSpielstein().getColor() == 's') {
						if(ySoll+yBewegung >= 0 && ySoll+yBewegung <= 5 && xSoll-xBewegung >= 0 && xSoll-xBewegung <= 5)
							if(koords[ySoll + yBewegung][xSoll - xBewegung].getSpielstein() == null) {
								bewertung -= 10;			
							}
					}
				}
			}
		}

		if(bewertung < -10) {
			bewertung = -10;
		}


		return bewertung;
	}

	/**
	 * 
	 * @param bewegung Zug der Ausgeführt wird
	 * @param stein der bewegt wird
	 * @param runde
	 * @return asd bewertung für den zug
	 */
	private int bewertenBewegen(String bewegung, Spielstein stein, int runde, int j) {

		int xAktuell, xSoll, yAktuell, ySoll, xBewegung, yBewegung, bewertung = 0;

		xAktuell = bewegung.charAt(0) - '@' - 1;
		yAktuell = bewegung.charAt(1) - '0' - 1;
		xSoll = bewegung.charAt(2) - '@' - 1;
		ySoll = bewegung.charAt(3) - '0' - 1;
		xBewegung = xAktuell - xSoll;
		yBewegung = yAktuell - ySoll;

		if(xBewegung > 1 || xBewegung < -1)
			xBewegung /= j;

		if(yBewegung > 1 || yBewegung < -1)
			yBewegung /= j;


		pruefeAufGueltigeKoord(bewegung);


		if(ySoll-yBewegung >= 0 && ySoll-yBewegung <= 5 && xSoll-xBewegung >= 0 && xSoll-xBewegung <= 5) {
			if(koords[ySoll - yBewegung][xSoll - xBewegung].getSpielstein() != null) {
				if(runde%2 == 0) {
					if(koords[ySoll - yBewegung][xSoll - xBewegung].getSpielstein().getColor() == 'w') {
						bewertung -= 20;
					}
				} else {
					if(koords[ySoll - yBewegung][xSoll - xBewegung].getSpielstein().getColor() == 's') {
						bewertung -= 20;
					}
				}
			} 
		}

		if(ySoll+yBewegung >= 0 && ySoll+yBewegung <= 5 && xSoll-xBewegung >= 0 && xSoll-xBewegung <= 5) {
			if(koords[ySoll + yBewegung][xSoll - xBewegung].getSpielstein() != null) {
				if(runde%2 == 0){
					if(koords[ySoll + yBewegung][xSoll - xBewegung].getSpielstein().getColor() == 'w') {
						if(ySoll-yBewegung >= 0 && ySoll-yBewegung <= 5 && xSoll+xBewegung >= 0 && xSoll+xBewegung <= 5)
							if(koords[ySoll - yBewegung][xSoll + xBewegung].getSpielstein() == null) {
								bewertung -= 20;
							}
					}
				} else {
					if(koords[ySoll + yBewegung][xSoll - xBewegung].getSpielstein().getColor() == 's') {
						if(ySoll-yBewegung >= 0 && ySoll-yBewegung <= 5 && xSoll+xBewegung >= 0 && xSoll+xBewegung <= 5)
							if(koords[ySoll - yBewegung][xSoll + xBewegung].getSpielstein() == null) {
								bewertung -= 20;					}
					}
				}
			} 
		}

		if(ySoll-yBewegung >= 0 && ySoll-yBewegung <= 5 && xSoll+xBewegung >= 0 && xSoll+xBewegung <= 5) {
			if(koords[ySoll - yBewegung][xSoll + xBewegung].getSpielstein() != null) {
				if(runde%2 == 0) {
					if(koords[ySoll - yBewegung][xSoll + xBewegung].getSpielstein().getColor() == 'w') {
						if(ySoll+yBewegung >= 0 && ySoll+yBewegung <= 5 && xSoll-xBewegung >= 0 && xSoll-xBewegung <= 5)
							if(koords[ySoll + yBewegung][xSoll - xBewegung].getSpielstein() == null) {
								bewertung -= 20;					}
					}
				} else {
					if(koords[ySoll - yBewegung][xSoll + xBewegung].getSpielstein().getColor() == 's') {
						if(ySoll+yBewegung >= 0 && ySoll+yBewegung <= 5 && xSoll-xBewegung >= 0 && xSoll-xBewegung <= 5)
							if(koords[ySoll + yBewegung][xSoll - xBewegung].getSpielstein() == null) {
								bewertung -= 20;				}
					}
				}
			}
		}

		if(bewertung < -20) {
			bewertung = -20;
		} 

		return bewertung;
	}

}

