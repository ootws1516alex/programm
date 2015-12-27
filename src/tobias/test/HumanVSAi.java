package tobias.test;

import java.util.ArrayList;
import java.util.Scanner;

public class HumanVSAi extends Spiellogik {

	private int time;
	Spielfeld spielfeld;
	private int color;
	
	public HumanVSAi(int time, int color){
		setTime(time);
		setColor(color);
		initGame();
		runGame();
	}
	
	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
	}

	public void initGame(){
		spielfeld = new Spielfeld();
		setKoords(spielfeld.getKoords());
		setwSteine(spielfeld.getwSteine());
		setsSteine(spielfeld.getsSteine());
		setAusgabe(new Ausgabe(getKoords()));
	}

	public void runGame() {
		Scanner scanner;
		String aktuellerZug;
		System.out.println("Das Spielfeld wurde erzeugt");
		System.out.print("Spieler geben sie ihren Namen ein: ");
		Scanner in = new Scanner(System.in);
		if(getColor()==2){
			setSpieler1(new Spieler(in.nextLine(), 's'));
			setSpieler2(new Spieler("Computer", 'w'));
			System.out.println(getSpieler2().getName());
		}else{
			setSpieler2(new Spieler(in.nextLine(), 'w'));
			setSpieler1(new Spieler("Computer", 's'));
		}
		int runde = 0;
		boolean setExit = false;
		boolean aufgabe1 = false;
		boolean aufgabe2 = false;
		int geschlagen=0;
		boolean legitZug=false;
		boolean aiZug=false;
		
		do{		
			getAusgabe().zeigeBrett();
			aiZug=false;
			if (runde % 2 == 0) {
				if(getSpieler1().getName().equals("Computer")){
					String aiMove = calculateAiMove(runde);
					bewegen(aiMove,runde);
					aiZug=true;
					System.out.println("Der Computer hat seinen Zug gemacht!");
				}else{
					System.out.println(getSpieler1().getName()+ " ist am Zug, gib die Koordinaten für deinen Spielzug ein. (zb. B5C4) oder 'AUFGEBEN' zum aufgeben ");
				}
			} else {
				if(getSpieler2().getName().equals("Computer")){
					String aiMove = calculateAiMove(runde);
					bewegen(aiMove,runde);
					aiZug=true;
					System.out.println("Der Computer hat seinen Zug gemacht!");
				}else{
					System.out.println(getSpieler2().getName()+ " ist am Zug, gib die Koordinaten für deinen Spielzug ein. (zb. B5C4) oder 'AUFGEBEN' zum aufgeben ");
				}
			}
			
			if(aiZug==false){
				legitZug=false;	
				do{
					
					scanner = new Scanner(System.in);
					aktuellerZug = scanner.nextLine();
					aktuellerZug = aktuellerZug.toUpperCase();
					
					if(!aktuellerZug.equals("AUFGEBEN")){
						if(pruefeAufgueltigeKoord(aktuellerZug)==false) {	
							System.out.println("Ungültige Koordinaten. Bitte gültige Koordinaten eingeben (zb. B5C4)!");
						}else if(richtigerZug(aktuellerZug, runde)==false){
							System.out.println("Ungültiger Zug. Bitte gültigen Zug eingeben!");
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
					if(geschlagen==2){
						System.out.println("Es hat schlagpflicht bestanden! Einer ihrer Steine wurde entfernt!");
					}
					if(geschlagen==1){
						if(runde%2==0){
							getAusgabe().zeigeBrett();
						}else{
							getAusgabe().zeigeGedrehtesBrett();
						}
						
						System.out.print("Sie haben einen Stein geschlagen. Weiteren Steine schlagen? ");
						System.out.println("Gültige Koordinaten oder 'NEIN' eigeben:");
						String zweiterZug;
						boolean exit=false;
						legitZug=false;
				
						do{	
							scanner = new Scanner(System.in);
							zweiterZug = scanner.nextLine();
							zweiterZug = zweiterZug.toUpperCase();
							if(!zweiterZug.equals("NEIN")){
								if(pruefeAufgueltigeKoord(zweiterZug)==false){
									System.out.println("Ungültige Koordinaten. Bitte gültige Koordinaten oder 'NEIN' eingeben");	
								}else if(richtigerZweitZug(aktuellerZug,zweiterZug,runde)==false){
									System.out.println("Ungültiger Zug. Bitte gültigen Zug oder 'NEIN' eingeben");
									legitZug=false;
								}else{
									legitZug=true;
								}
							}else{
								exit=true;
								break;
							}
								
						}while((legitZug==false||pruefeAufgueltigeKoord(zweiterZug)==false));
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
			}
			setExit=isFinished(aufgabe1,aufgabe2);			
			runde++;
		} while (setExit == false);
		
		switch(this.whoWon(aufgabe1,aufgabe2)){
			case 1: System.out.println(getSpieler1().getName()+" gewinnt!");
				break;
			case 2: System.out.println(getSpieler2().getName()+" gewinnt!");
				break;
			case 3: System.out.println("Das Spiel endet unentschieden!");
				break;
			case 4: System.out.println(getSpieler1().getName()+" gewinnt durch Aufgabe von Spieler 2");
				break;
			case 5: System.out.println(getSpieler2().getName()+" gewinnt durch Aufgabe von Spieler 1");
				break;
		}	
	}
	
}
