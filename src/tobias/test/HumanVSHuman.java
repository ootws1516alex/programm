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
		startGame();
	}
	public int getTime() {
		return time;
	}
	public void initGame() {
		spielfeld = new Spielfeld();
		this.koords = spielfeld.getKoords();
		this.wSteine=spielfeld.getwSteine();
		this.sSteine=spielfeld.getsSteine();
		this.ausgabe = new Ausgabe(koords);
	}
	public void startGame() {
		Scanner scanner;
		String aktuellerZug;
		System.out.println("Das Spielfeld wurde erzeugt");
		System.out.print("Spieler1 (weiß) geben sie ihren Namen ein: ");
		Scanner in = new Scanner(System.in);
		spieler1 = new Spieler(in.nextLine(), 's', false);
		System.out.print("Spieler2 (schwarz) geben sie ihren Namen ein: ");
		spieler2 = new Spieler(in.nextLine(), 'w', true);
		int runde = 0;
		boolean setExit = false;
		boolean aufgabe1 = false;
		boolean aufgabe2 = false;
		boolean geschlagen=false;
		
		do{	
			if(runde%2 == 0){
				ausgabe.zeigeBrett();
			}else{
				//ausgabe.zeigeGedrehtesBrett();
				ausgabe.zeigeBrett();
			}
			if (runde % 2 == 0) {
				System.out.println(spieler1.getName()+ " ist am Zug, gib die Koordinaten für deinen Spielzug ein. (zb. B5C4) oder 'AUFGEBEN' zum aufgeben ");
			} else {
				System.out.println(spieler2.getName()+ " ist am Zug, gib die Koordinaten für deinen Spielzug ein. (zb. B5C4) oder 'AUFGEBEN' zum aufgeben");
			}

			scanner = new Scanner(System.in);
			aktuellerZug = scanner.nextLine();
			aktuellerZug = aktuellerZug.toUpperCase();
			if(!aktuellerZug.equals("AUFGEBEN")){
				while(pruefeAufgueltigeKoord(aktuellerZug)==false||richtigerZug(aktuellerZug, runde)==false) {	
					System.out.println("Ungültige Koordinaten bzw. ungültiger Zug. Bitte gültige Koordinaten eingeben (zb. B5C4)");
					scanner = new Scanner(System.in);
					aktuellerZug = scanner.nextLine();
					aktuellerZug = aktuellerZug.toUpperCase();
				}	
	
				geschlagen=bewegen(aktuellerZug,runde);
			
				if(geschlagen==true){
					System.out.println("Weiteren Steine schlagen? Gültige Koordinaten oder 'NEIN' eigeben:");
					scanner = new Scanner(System.in);
					String zweiterZug;
					boolean exit=false;
					zweiterZug = scanner.nextLine();
					zweiterZug = aktuellerZug.toUpperCase();
				
					while(pruefeAufgueltigeKoord(zweiterZug)==false||richtigerZweitZug(aktuellerZug, zweiterZug, runde)==false) {	
						System.out.println("Ungültige Koordinaten bzw. ungültiger Zug. Bitte gültige Koordinaten oder'NEIN' eingeben");
						scanner = new Scanner(System.in);
						zweiterZug = scanner.nextLine();
						zweiterZug = zweiterZug.toUpperCase();
					}
				
					if(zweiterZug.equals("NEIN")){
						exit=true;
					}
					if(exit==false){
						bewegen(zweiterZug,runde);
					}
				}
			}
			if(aktuellerZug.equals("AUFGEBEN")&&runde%2==0){
				aufgabe1=true;
			}
			if(aktuellerZug.equals("AUFGEBEN")&&runde%2==1){
				aufgabe2=true;
			}
			setExit=isFinished(aufgabe1,aufgabe2);			
			runde++;
		} while (setExit == false);
		
		if(wSteine.size()==0){
			System.out.println(spieler1.getName()+" hat das Spiel gewonnen");
		}
		if(sSteine.size()==0){
			System.out.println(spieler2.getName()+" hat das Spiel gewonnen");
		}
		if(sSteine.size()==1&&wSteine.size()==1){
			System.out.println("Das Spiel geht unentschieden aus");
		}
		if(aufgabe1==true){
			System.out.println(spieler2.getName()+" hat das Spiel gewonnen");
		}
		if(aufgabe2==true){
			System.out.println(spieler1.getName()+" hat das Spiel gewonnen");
		}
	}
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
	private void removeRandomStein(int runde){
		int random=10;
		if(runde%2==0){
			while(random>wSteine.size()-1){
				random=(int)Math.random()*10;
			}
			wSteine.remove(random);
		}else{
			while(random>sSteine.size()-1){
				random=(int)Math.random()*10;
			}
			sSteine.remove(random);
		}
	}
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
	private boolean bewegen(String aktuellerZug, int runde){
		Spielstein geschlagener=null;
		geschlagener=schlagen(aktuellerZug,runde);
		
		if(geschlagener!=null){
			ziehen(aktuellerZug,runde);
		}else{
			if(mussSchlagen(aktuellerZug,runde)==true){
				removeRandomStein(runde);
			}else{
				ziehen(aktuellerZug,runde);
			}
		}
		checkIfNewLady();
		if(geschlagener!=null){
			return true;
		}
		return false;
	}
	public void checkIfNewLady(){
		for(int i=0;i<wSteine.size();i++){
			System.out.println();
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
	public boolean mussSchlagen(String aktuellerZug, int runde) {

		int yAktuell = aktuellerZug.charAt(1) - '0' - 1;
		int xAktuell = aktuellerZug.charAt(0) - '@' - 1;
		int ySoll = aktuellerZug.charAt(3) - '0' - 1;
		int xSoll = aktuellerZug.charAt(2) - '@' - 1;
		int x,y,z;
		Spielstein aktuellerStein;
	
		
		
		
	
		return false;
	}
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
