package tobias.test;

public class Spieler {
	
	String name;
	char farbe;
	
	public Spieler(String name,char farbe){
		this.name=name;
		this.farbe=farbe;
	}
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public char getFarbe() {
		return farbe;
	}
	public void setFarbe(char farbe) {
		this.farbe = farbe;
	}
	
}
