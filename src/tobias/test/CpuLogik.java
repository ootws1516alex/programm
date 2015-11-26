package tobias.test;

import java.util.ArrayList;

public class CpuLogik {
	
	ArrayList<Spielstein> spielSteineS;
	ArrayList<Spielstein> spielSteineW;
	
	public CpuLogik(ArrayList<Spielstein> spielSteineW, ArrayList<Spielstein> spielSteineS){
		this.spielSteineW=spielSteineW;
		this.spielSteineS=spielSteineS;
	}
	
	public void priority(){
		int x,y;
		Spielstein aktStein;
		aktStein=spielSteineW.get(0);
		x=aktStein.getK().getX();
		y=aktStein.getK().getY();
		
		
	}
	
	
}
