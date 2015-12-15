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
		//startGame();
	}
	public int getTime() {
		return time;
	}
	public void initGame() {
		spielfeld = new Spielfeld();
		this.koords = spielfeld.getKoords();
		this.wSteine=spielfeld.getwSteine();
		this.sSteine=spielfeld.getsSteine();
		Ausgabe ausgabe = new Ausgabe(koords);
		ausgabe.zeigeBrett();
		ziehen("B5A4",1);
		ausgabe.zeigeBrett();
		ausgabe.zeigeGedrehtesBrett();
		ziehen("A2B3",0);
		//ausgabe.zeigeGedrehtesBrett();
		//ausgabe.zeigeBrett();
		
	}
	public void startGame() {
		Scanner scanner;
		String aktuellerZug;
		System.out.println("Das Spielfeld wurde erzeugt");
		System.out.print("Spieler1 (schwarz) geben sie ihren Namen ein: ");
		Scanner in = new Scanner(System.in);
		spieler1 = new Spieler(in.nextLine(), 's', false);
		System.out.print("Spieler2 (weiß) geben sie ihren Namen ein: ");
		spieler2 = new Spieler(in.nextLine(), 'w', true);
		int runde = 0;
		boolean setExit = false;
		boolean isLegit = true;
		
		
		do{
			
			if(runde%2 == 0){
				ausgabe.zeigeBrett();
			}else{
				ausgabe.zeigeGedrehtesBrett();
			}
			if (runde % 2 == 0) {
				System.out.println(spieler1.getName()+ " ist am Zug, gib die Koordinaten für deinen Spielzug ein. (zb. B5C4)");
			} else {
				System.out.println(spieler2.getName()+ " ist am Zug, gib die Koordinaten für deinen Spielzug ein. (zb. B5C4)");
			}
			
			scanner = new Scanner(System.in);
			aktuellerZug = scanner.nextLine();
			aktuellerZug = aktuellerZug.toUpperCase();
			
			while(pruefeAufgueltigeKoord(aktuellerZug)==false&&richtigerZug(aktuellerZug, runde)==true) {	
				System.out.println("Ungültige Koordinaten bzw. ungültiger Zug. Bitte gültige Koordinaten eingeben (zb. B5C4)");
				scanner = new Scanner(System.in);
				aktuellerZug = scanner.nextLine();
				aktuellerZug = aktuellerZug.toUpperCase();
			}
	
			if(mussSchlagen(aktuellerZug,runde)==true){
				removeRandomStein();
			}else{
				bewegen(aktuellerZug, runde);
			}
			if(kannSchlagen(aktuellerZug,runde)==true){
						
			}
					
			
		setExit=isFinished();			
		runde++;
		} while (setExit == false);
		
	}
	private void removeRandomStein(int runde){
		if(runde%2==0){
			
		}else{
			
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
		temp = koords[aktuellerZug.charAt(1) - '0' - 1][aktuellerZug.charAt(0) - '@' - 1].getSpielstein();
		koords[aktuellerZug.charAt(1) - '0' - 1][aktuellerZug.charAt(0) - '@' - 1].setSpielstein(null);
		koords[aktuellerZug.charAt(3) - '0' - 1][aktuellerZug.charAt(2) - '@' - 1].setSpielstein(temp);				
	}
	private void bewegen(String aktuellerZug, int runde){
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
	}
	
	
	private Spielstein schlagen(String aktuellerZug, int runde) {

		int yAktuell = aktuellerZug.charAt(1) - '0' - 1;
		int xAktuell = aktuellerZug.charAt(0) - '@' - 1;
		int ySoll = aktuellerZug.charAt(3) - '0' - 1;
		int xSoll = aktuellerZug.charAt(2) - '@' - 1;
		Spielstein geschlagener=null;
		
		if(xAktuell>xSoll&&yAktuell<ySoll){
			int j=yAktuell+1;
			for(int i=xAktuell-1;i>xSoll;i--){
				if(koords[i][j].getSpielstein()!=null){
					geschlagener=koords[i][j].getSpielstein();
					koords[i][j].setSpielstein(null);
				}
			}
		}
		if(xAktuell>xSoll&&yAktuell>ySoll){
			int j=yAktuell-1;
			for(int i=xAktuell-1;i>xSoll;i--){
				if(koords[i][j].getSpielstein()!=null){
					geschlagener=koords[i][j].getSpielstein();
					koords[i][j].setSpielstein(null);
				}
			}
		}	
		if(xAktuell<xSoll&&yAktuell<ySoll){
			int j=yAktuell+1;
			for(int i=xAktuell+1;i<xSoll;i++){
				if(koords[i][j].getSpielstein()!=null){
					geschlagener=koords[i][j].getSpielstein();
					koords[i][j].setSpielstein(null);
				}
			}
		}	
		if(xAktuell<xSoll&&yAktuell>ySoll){
			int j=yAktuell-1;
			for(int i=xAktuell+1;i<xSoll;i++){
				if(koords[i][j].getSpielstein()!=null){
					geschlagener=koords[i][j].getSpielstein();
					koords[i][j].setSpielstein(null);
				}
			}	
		}
		return geschlagener;
	}
	public boolean mussSchlagen(String aktuellerZug, int runde) {

		int xAktuell = aktuellerZug.charAt(1) - '0' - 1;
		int yAktuell = aktuellerZug.charAt(0) - '@' - 1;
		int xSoll = aktuellerZug.charAt(3) - '0' - 1;
		int ySoll = aktuellerZug.charAt(2) - '@' - 1;
		int x,y,z;
		Spielstein aktuellerStein;
	
		for(int i = 0;i<wSteine.size();i++){
			aktuellerStein=wSteine.get(i);
			x=aktuellerStein.getKoordinate().getX();
			y=aktuellerStein.getKoordinate().getY();
			if(runde%2==0){
				if(aktuellerStein.isLady()==true){
					
						
						
				}else{
					
				}
			}else{
				if(aktuellerStein.isLady()==true){
					
				}else{
					
				}
			}
			
		}

		return false;
	}
	public boolean Stub(String aktuellerZug, int runde) {

		int xAktuell = aktuellerZug.charAt(1) - '0' - 1;
		int yAktuell = aktuellerZug.charAt(0) - '@' - 1;
		int xSoll = aktuellerZug.charAt(3) - '0' - 1;
		int ySoll = aktuellerZug.charAt(2) - '@' - 1;
		int z;
		if(runde%2==0){
			z=2;
		}else{
			z=0;
		}
		if (koords[xAktuell][yAktuell].getSpielstein() != null && (koords[xSoll][ySoll].getSpielstein() != null)) {
			return false;
		} else if (koords[xAktuell][yAktuell].getSpielstein() == null) {
			return false;
		} else if (koords[xAktuell][yAktuell].getSpielstein().isLady() == false) {
			if (xAktuell != xSoll + 1-z&& (yAktuell != ySoll + 1 || yAktuell != ySoll - 1)) {
				return false;
			}
		} else if (koords[xAktuell][yAktuell].getSpielstein().isLady() == true) {
			int i1 = xSoll - xAktuell;
			int i2 = ySoll - yAktuell;
			if (Math.abs(i1) != Math.abs(i2)) {
				return false;
			}
		}
		return true;
	}
	public boolean richtigerZug(String aktuellerZug, int runde){
		
		int xAktuell = aktuellerZug.charAt(1) - '0' - 1;
		int yAktuell = aktuellerZug.charAt(0) - '@' - 1;
		int xSoll = aktuellerZug.charAt(3) - '0' - 1;
		int ySoll = aktuellerZug.charAt(2) - '@' - 1;
		int z;
		if(runde%2==0){
			z=2;
		}else{
			z=0;
		}
		if(koords[xAktuell][yAktuell].getSpielstein() != null&&koords[xSoll][ySoll].getSpielstein()==null){
			if (xAktuell == xSoll + 1-z&& (yAktuell == ySoll + 1 || yAktuell == ySoll - 1)) {
				return true;
			}
			if (xAktuell == xSoll + 2-z*2&& (yAktuell == ySoll + 2 || yAktuell == ySoll - 2)) {
				if((ySoll>yAktuell)&&(koords[xAktuell+1-z][yAktuell+1].getSpielstein()!=null)){
					if(koords[xAktuell+1-z][yAktuell+1].getSpielstein().getColor()!=koords[xAktuell][yAktuell].getSpielstein().getColor()){
						return true;
					}
				}
				if((ySoll<yAktuell)&&(koords[xAktuell+1-z][yAktuell-1].getSpielstein()!=null)){
					if((koords[xAktuell+1-z][yAktuell-1].getSpielstein().getColor()!=koords[xAktuell][yAktuell].getSpielstein().getColor())){
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
