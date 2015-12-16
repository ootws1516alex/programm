package julian;
import java.util.Scanner;
public class HumanVSHuman {
	


		private int color;
		private int time;
		Spielfeld spielfeld;
		Spieler spieler1;
		Spieler spieler2;
		
		public HumanVSHuman(int time, int color){
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
			boolean geschlagen;
			do{
				do{
				if(spielZug%2==0){
				System.out.println(spieler1.getName()+" ist am Zug, gib die Koordinaten für deinen Spielzug ein. (zb. B5C4)");
				}else{
				System.out.println(spieler2.getName()+" ist am Zug, gib die Koordinaten für deinen Spielzug ein. (zb. B5C4)");
				}
				
						Scanner scanner = new Scanner(System.in);
						String aktuellerZug = scanner.nextLine();
						aktuellerZug=aktuellerZug.toUpperCase();
						geschlagen=spielfeld.hatGeschlagen(aktuellerZug, spielZug);
					
						if(!pruefeAufgueltigeKoord(aktuellerZug)){
							isLegit=false;
							System.out.println("ungültige Koordinaten, bitte gültige Koordinaten eingeben (zb. B5C4)");
							spielZug--;
							spielfeld.dreheBrett();
						}
						else if(spielfeld.richtigerZug(aktuellerZug, spielZug)==false&&geschlagen==false){
							System.out.println(spielfeld.richtigerZug(aktuellerZug,spielZug));
							System.out.println(spielZug);
							isLegit=false;
							System.out.println("ungültiger Zug, bitte Koordinaten erneut eingeben (zb. B5C4)");
							spielZug--;
							spielfeld.dreheBrett();
							
						}
						else{
							spielfeld.ziehen(aktuellerZug, spielZug);
					}
						spielfeld.zeigeBrett();
						if(geschlagen==true){
						while(!aktuellerZug.equals("NEIN")){
							System.out.println("Sie haben einen gegnerischen Stein geschlagen, wenn Sie einen weiteren Stein schlagen möchten geben Sie die Koordinaten ein, ansonsten schreiben Sie NEIN");
							
								String alterAktZug=aktuellerZug;
								aktuellerZug = scanner.nextLine();
								aktuellerZug=aktuellerZug.toUpperCase();
								if(!aktuellerZug.equals("NEIN")){
								if(!pruefeAufgueltigeKoord(aktuellerZug)){
									System.out.println("ungültige Koordinaten, bitte gültige Koordinaten eingeben (zb. B5C4)");
									spielfeld.dreheBrett();
									aktuellerZug=alterAktZug;
								}	
								else if(alterAktZug.charAt(2)!=aktuellerZug.charAt(0)||alterAktZug.charAt(3)!=aktuellerZug.charAt(1)){
									System.out.println("Sie können nur mit dem gleichen Stein schlagen, mit dem Sie zuvor geschlagen haben");
									spielfeld.dreheBrett();
									aktuellerZug=alterAktZug;
								}
									
								
								
								else if(spielfeld.hatGeschlagen(aktuellerZug, spielZug)==false){
									System.out.println(spielfeld.richtigerZug(aktuellerZug,spielZug));
									System.out.println(spielZug);
									System.out.println("ungültiger Zug, bitte Koordinaten erneut eingeben (zb. B5C4)");
									spielfeld.dreheBrett();
									aktuellerZug=alterAktZug;
									
								}
								else{
									spielfeld.ziehen(aktuellerZug, spielZug);
							}
								spielfeld.zeigeBrett();
								spielfeld.dreheBrett();
								spielfeld.zeigeBrett();
							
								}
								}
						}
						
						spielfeld.dreheBrett();
						spielfeld.zeigeBrett();
						
						
						spielZug++;
				}while(isLegit==false);
			}while(setExit==false);

				
				
				
				
			
			
			
		}

		private boolean pruefeAufgueltigeKoord(String aktuellerZug) {
			if(aktuellerZug.length()!=4){
				return false;
			}
			String moeglicheBuchstaben="ABCDEF";
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
