package tobias.test;

import java.util.ArrayList;
import java.util.Scanner;

public class HumanVSHuman {

	private int color;
	private int time;
	Spielfeld spielfeld;
	Ausgabe ausgabe;
	Spieler spieler1;
	Spieler spieler2;
	Koordinate[][] koords;
	private ArrayList<Spielstein> wSteine;
	private ArrayList<Spielstein> sSteine;

	public HumanVSHuman(int time, int color) {
		this.time = time;
		this.color = color;
		initGame();
		runGame();
	}
	
	/**
	 * holt sich ben�tigte daten aus der Klasse Spielfeld und erzeugt eine neues ausgabe Objekt
	 */
	public void initGame() {
		spielfeld = new Spielfeld();
		this.koords = spielfeld.getKoords();
		this.wSteine=spielfeld.getwSteine();
		this.sSteine=spielfeld.getsSteine();
		this.ausgabe = new Ausgabe(koords);
	}
	/**
	 * Spiellogik bzw. rundenlogik
	 */
	public void runGame() {
		Scanner scanner;
		String aktuellerZug;
		System.out.println("Das Spielfeld wurde erzeugt");
		System.out.print("Spieler1 (wei�) geben sie ihren Namen ein: ");
		Scanner in = new Scanner(System.in);
		spieler1 = new Spieler(in.nextLine(), 's', false);
		System.out.print("Spieler2 (schwarz) geben sie ihren Namen ein: ");
		spieler2 = new Spieler(in.nextLine(), 'w', true);
		int runde = 0;
		boolean setExit = false;
		boolean aufgabe1 = false;
		boolean aufgabe2 = false;
		boolean geschlagen=false;
		boolean legitZug=false;
		
		do{	
			if(runde%2 == 0){
				ausgabe.zeigeBrett();
			}else{
				ausgabe.zeigeGedrehtesBrett();
			}
			if (runde % 2 == 0) {
				System.out.println(spieler1.getName()+ " ist am Zug, gib die Koordinaten f�r deinen Spielzug ein. (zb. B5C4) oder 'AUFGEBEN' zum aufgeben ");
			} else {
				System.out.println(spieler2.getName()+ " ist am Zug, gib die Koordinaten f�r deinen Spielzug ein. (zb. B5C4) oder 'AUFGEBEN' zum aufgeben");
			}
				legitZug=false;
				do{
					
					scanner = new Scanner(System.in);
					aktuellerZug = scanner.nextLine();
					aktuellerZug = aktuellerZug.toUpperCase();
					
					if(!aktuellerZug.equals("AUFGEBEN")){
						if(pruefeAufgueltigeKoord(aktuellerZug)==false) {	
							System.out.println("Ung�ltige Koordinaten. Bitte g�ltige Koordinaten eingeben (zb. B5C4)!");
						}else if(richtigerZug(aktuellerZug, runde)==false){
							System.out.println("Ung�ltiger Zug. Bitte g�ltigen Zug eingeben!");
							legitZug=false;
						}else{
							legitZug=true;
						}
					}else{
						break;
					}
					
				}while(legitZug==false||pruefeAufgueltigeKoord(aktuellerZug)==false);
				if(!aktuellerZug.equals("AUFGEBEN")){
					
					geschlagen=bewegen(aktuellerZug,runde);
		
					if(geschlagen==true){
						if(runde%2==0){
							ausgabe.zeigeBrett();
						}else{
							ausgabe.zeigeGedrehtesBrett();
						}
						
						System.out.println("Weiteren Steine schlagen? G�ltige Koordinaten oder 'NEIN' eigeben:");
						String zweiterZug;
						boolean exit=false;
						legitZug=false;
				
						do{	
							scanner = new Scanner(System.in);
							zweiterZug = scanner.nextLine();
							zweiterZug = zweiterZug.toUpperCase();
							if(!zweiterZug.equals("NEIN")){
								if(pruefeAufgueltigeKoord(zweiterZug)==false){
									System.out.println("Ung�ltige Koordinaten. Bitte g�ltige Koordinaten oder 'NEIN' eingeben");
								}else if(richtigerZweitZug(aktuellerZug,zweiterZug,runde)==false){
									System.out.println("Ung�ltiger Zug. Bitte g�ltigen Zug oder 'NEIN' eingeben");
								}else{
									legitZug=true;
								}
							}else{
								exit=true;
								break;
							}
								
						}while((richtigerZweitZug(aktuellerZug,zweiterZug,runde)==false||pruefeAufgueltigeKoord(zweiterZug)==false));
						if(exit==false){
							bewegen(zweiterZug,runde);
						}
					}
				}else{
					if(aktuellerZug.equals("AUFGEBEN")&&runde%2==0){
						aufgabe1=true;
					}
					if(aktuellerZug.equals("AUFGEBEN")&&runde%2==1){
						aufgabe2=true;
					}
				}
			setExit=isFinished(aufgabe1,aufgabe2);			
			runde++;
		} while (setExit == false);
		System.out.println(whoWon(aufgabe1,aufgabe2));
	}
	/**
	 * gibt in einem String zur�ck wer gewonnen hat bzw ob das spiel unentschieden ausging. 
	 * Falls das spiel noch l�uft wird NULL zur�ckgegeben
	 * @param aufgabe1
	 * @param aufgabe2
	 * @return
	 */
	public String whoWon(boolean aufgabe1,boolean aufgabe2){
		if(wSteine.size()==0){
			return (spieler1.getName()+" hat das Spiel gewonnen");
		}
		if(sSteine.size()==0){
			return (spieler2.getName()+" hat das Spiel gewonnen");
		}
		if(sSteine.size()==1&&wSteine.size()==1){
			return ("Das Spiel geht unentschieden aus");
		}
		if(aufgabe1==true){
			return (spieler2.getName()+" hat das Spiel gewonnen");
		}
		if(aufgabe2==true){
			return (spieler1.getName()+" hat das Spiel gewonnen");
		}else{
			return ("Das Spiel l�uft noch!");
		}
	}
	/**
	 * �berpr�ft ob das spiel noch l�uft und gibt ein boolean zur�ck
	 * @param aufgabe1
	 * @param aufgabe2
	 * @return
	 */
	public boolean isFinished(boolean aufgabe1, boolean aufgabe2){
		if(wSteine.size()==0||sSteine.size()==0){
			return true;
		}
		if(wSteine.size()==1&&sSteine.size()==1){
			return true;
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
	 * entfernt falls n�tig einen zuf�lligen stein
	 * @param runde
	 */
	private void removeRandomStein(int runde){
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
	 * pr�ft ob eingabe korrekt ist
	 * @param aktuellerZug
	 * @return
	 */
	private boolean pruefeAufgueltigeKoord(String aktuellerZug) {
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
	 * Wird als erstes aufgerufen und ruft dann alle anderen n�tigen methoden auf 
	 * um einen zug korrekt durchzuf�hren. Gibt zur�ck ob ein stein geschlagen wurde
	 * @param aktuellerZug
	 * @param runde
	 * @return
	 */
	private boolean bewegen(String aktuellerZug, int runde){
		Spielstein geschlagener=null;
		geschlagener=schlagen(aktuellerZug,runde);
		
		if(geschlagener!=null){
			ziehen(aktuellerZug,runde);
		}else{
			if(mussSchlagen(runde)==true){
				removeRandomStein(runde);
			}else{
				ziehen(aktuellerZug,runde);
			}
		}
		makeNewLady();
		if(geschlagener!=null){
			return true;
		}
		return false;
	}
	/**
	 * Wandelt alle Steine am Gegnerischen Rand zu Ladies um
	 */
	public void makeNewLady(){
		for(int i=0;i<wSteine.size();i++){
			if(wSteine.get(i).getKoordinate().equals(koords[5][0])||wSteine.get(i).getKoordinate().equals(koords[5][2])
					||wSteine.get(i).getKoordinate().equals(koords[5][4])){
				wSteine.get(i).setLady(true);
			}
		}
		for(int i=0;i<sSteine.size();i++){
			if(sSteine.get(i).getKoordinate().equals(koords[0][1])||sSteine.get(i).getKoordinate().equals(koords[0][3])
					||sSteine.get(i).getKoordinate().equals(koords[0][5])){
				sSteine.get(i).setLady(true);
			}
		}
	}
	/**
	 * Checkt ob ein Stein geschlagen wurde und gibt diesen zur�ck. Wurde keinn Stein geschlagen wird NULL zur�ckgegeben.
	 * @param aktuellerZug
	 * @param runde
	 * @return
	 */
	private Spielstein schlagen(String aktuellerZug, int runde) {

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
	 * �berpr�ft ob schlagpflicht verletzt wurde und gibt dann true zur�ck, ansonsten false
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
	 * �berpfr�ft nach erstem schlagen ob ein zweites schlagen korrekt eingegeben wurde
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
	 * �berpr�ft ob ein eingegebener Zug m�glich ist
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
		
	
		if(runde%2==0){
			z=0;
		}else{
			z=2;
		}
		if(koords[xAktuell][yAktuell].getSpielstein() != null&&koords[xSoll][ySoll].getSpielstein()==null){
			if (xAktuell == xSoll + 1-z&& (yAktuell == ySoll + 1 || yAktuell == ySoll - 1)) {
				return true;
			}
			if (xAktuell == xSoll + 2-z*2&& (yAktuell == ySoll + 2 || yAktuell == ySoll - 2)) {
				if((ySoll>yAktuell)&&(koords[xAktuell-1+z][yAktuell+1].getSpielstein()!=null)){
					if(koords[xAktuell-1+z][yAktuell+1].getSpielstein().getColor()!=koords[xAktuell][yAktuell].getSpielstein().getColor()){
						return true;
					}
				}
				if((ySoll<yAktuell)&&(koords[xAktuell-1+z][yAktuell-1].getSpielstein()!=null)){
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
	
}
