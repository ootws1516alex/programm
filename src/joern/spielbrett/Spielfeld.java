package joern.spielbrett;

import java.util.ArrayList;

public class Spielfeld {

 	private ArrayList<Spielstein> steine;
 	
 	public Spielfeld(ArrayList<Spielstein> s){
 		this.steine = s;
 	}
 	
 	public void zeigeBrett(){
 		this.dreheBrett();
 		for(int i = 0; i < 13; i++){
 			for(int j = 0; j < 13; j++){
 				if(i % 2 == 0 && j % 2 == 1){
 					System.out.print("-");
 				}
 				else if(i % 2 == 0 && j % 2 == 0){
 					System.out.print(" ");
 				}
 				else if(i % 2 == 1 && j % 2 == 0){
 					System.out.print("|");
 				}
 				else if(i % 2 == 1 && j % 2 == 1){
 					System.out.print(" ");
 				}
 			}
 			System.out.println();
 		}
 	}
 	
 	public void dreheBrett(){
 		for(Spielstein stein: steine){
 			stein.setX(5-stein.getX());
 			stein.setY(5-stein.getY());
 		}
 	}
 	
 	public void setSteine(ArrayList<Spielstein> s){
 		this.steine = s;
 	}
 	
 	public ArrayList<Spielstein> getSteine(){
 		return this.steine;
 	}
}
