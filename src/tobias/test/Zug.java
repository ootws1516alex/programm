package tobias.test;

public class Zug {

	private Spielstein spielstein;
	private Koordinate[][] koords;
	
	public Zug(Spielstein spielstein, Koordinate[][] koords){
		this.setSpielstein(spielstein);
		this.setKoords(koords);	
	}

	public Spielstein getSpielstein() {
		return spielstein;
	}

	public void setSpielstein(Spielstein spielstein) {
		this.spielstein = spielstein;
	}

	public Koordinate[][] getKoords() {
		return koords;
	}

	public void setKoords(Koordinate[][] koords) {
		this.koords = koords;
	}
	
	
	
}
