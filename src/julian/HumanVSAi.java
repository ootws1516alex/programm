package julian;

import java.util.Scanner;

public class HumanVSAi {

	private int color;
	private int time;
	Spielfeld spielfeld;
	Spieler spieler1;
	Spieler spieler2;
	
	public HumanVSAi(int time, int color){
		this.time=time;
		this.color=color;
		initGame();
		startGame();
	}

	public int getTime() {
		return time;
	}
	
	public void initGame(){
		spielfeld = new Spielfeld();
		spielfeld.zeigeBrett();
		if(color==1){
			spielfeld.dreheBrett();
		}
		
	}
	public void startGame(){
		System.out.println("Das Spielfeld wurde erzeugt");
		System.out.print("Spieler1 (schwarz) geben sie ihren Namen ein: ");
		Scanner in = new Scanner(System.in);
		spieler1=new Spieler(in.nextLine(),'s');
		System.out.print("Spieler2 (weiß) geben sie ihren Namen ein: ");
		spieler2=new Spieler(in.nextLine(),'w');
		int spielZug=0;
		boolean setExit=false;
		boolean isLegit=true;
		do{
			do{
			if(spielZug%2==0){
			System.out.println(spieler1.getName()+" ist am Zug, gib die Koordinaten für deinen Spielzug ein. (zb. B5C4)");
			}else{
			System.out.println(spieler2.getName()+" ist am Zug, gib die Koordinaten für deinen Spielzug ein. (zb. B5C4)");
			}
			spielfeld.zeigeBrett();
					Scanner scanner = new Scanner(System.in);
					String aktuellerZug = scanner.nextLine();
				
					if(!pruefeAufgueltigeKoord(aktuellerZug)){
						isLegit=false;
						System.out.println("ungültige Koordinaten, bitte gültige Koordinaten eingeben (zb. B5C4)");
						spielZug--;
						spielfeld.dreheBrett();
					}
					else if(!pruefeAufgueltigenZug(aktuellerZug)){
						isLegit=false;
						System.out.println("ungültiger Zug, bitte Koordinaten erneut eingeben (zb. B5C4)");
						spielZug--;
						spielfeld.dreheBrett();
					}
					
					
					
					spielfeld.dreheBrett();
					spielZug++;
			}while(isLegit==false);
		}while(setExit==false);

			
			
			
			
		
		
		
	}

	private boolean pruefeAufgueltigenZug(String aktuellerZug) {
		// TODO Auto-generated method stub
		return true;
	}

	private boolean pruefeAufgueltigeKoord(String aktuellerZug) {
		if(aktuellerZug.length()!=4){
			return false;
		}
		String moeglicheBuchstaben="ABCDEFabcdef";
		for(int i=0; i<aktuellerZug.length()-1;i++){
			if(i%2==0){
				if(moeglicheBuchstaben.indexOf(aktuellerZug.charAt(i))==-1){
					return false;
				}
			}
			else{
				if(aktuellerZug.charAt(i)<'1'||aktuellerZug.charAt(i)>'6'){
					return false;
				}
			}
			
		}
		
		return true;

	}
	
	
}
