package tobias.test;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Menue {

	public static void main(String[] args) {

		//starteMenue();
		
		//Spielfeld brett = new Spielfeld();
		//brett.initStein();
		//brett.zeigeBrett();
		//System.out.println();
		//brett.dreheBrett();
		//brett.zeigeBrett();
	}
	
	public static void starteMenue(){
		
		boolean setExit=false;
		boolean isLegal=true;
		int mode;
		
		do{
			System.out.println("Wilkommen bei 'Fear the lady' ");
			System.out.println("1: Spielen");
			System.out.println("2: Anleitung lesen");
			System.out.println("3: Beenden");
			System.out.print("Bitte auswählen: ");
			
			do{
				isLegal=true;
				try{
					Scanner scanner = new Scanner(System.in);
					mode = scanner.nextInt();
				}catch(Exception e){
					mode=5;
				}finally{
					System.out.println();
				}
				switch(mode){
					case 1: spielen();
						break;
					case 2: anleitung();		
						break;
					case 3: setExit=true;
						break;
					default:System.out.println("Bitte gültigen Wert eingeben!"); 
							isLegal=false;
				}
			}while(isLegal==false);	
				
		}while(setExit==false);
	}
	
	public static void spielen(){
		Scanner scanner;
		int color=0;
		int mode =0;
		int time=0;
		boolean isLegal;
		
		System.out.println("1: Mensch vs Ai");
		System.out.println("2: Mensch vs Mensch");
		System.out.print("Bitte Spielmodus auswählen: ");
		
		do{
			isLegal=true;
			try{
				scanner = new Scanner(System.in);
				mode = scanner.nextInt();
			}catch(Exception e){
				mode=5;
			}finally{
				System.out.println();
			}
			if(mode!=1&&mode!=2){
				isLegal=false;
				System.out.println("Bitte gültigen Wert eingeben!");
			}
		}while(isLegal==false);
			
		
		System.out.print("Zugzeit in Sekunden eingeben(min.10): ");
		do{
			isLegal=true;
			try{
				scanner = new Scanner(System.in);
				time = scanner.nextInt();
			}catch(Exception e){
				isLegal=false;
			}finally{
				System.out.println();
			}
			
			if(time<10){
				isLegal=false;
				System.out.println("Bitte gültigen Wert eingeben!");
			}
		}while(isLegal==false);
		
		if(mode==1){
			//HumanVSAi game = new HumanVSAi(time,color);
		}else{
			//HumanVSHuman game = new HumanVSHuman(time,color);
		}
		
	}
	
	public static void anleitung(){
		System.out.println("bla");
	}

}
