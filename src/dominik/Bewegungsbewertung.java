package dominik;

public class Bewegungsbewertung {

	private String zug;
	private int bewertung;

	public Bewegungsbewertung(String zug, int bewertung) {
		this.zug = zug;
		this.bewertung = bewertung;
	}

	public int getBewertung() {
		return bewertung;
	}

	public void setBewertung(int bewertung) {
		this.bewertung = bewertung;
	}

	public String getZug() {
		return zug;
	}

	public void setZug(String zug) {
		this.zug = zug;
	}

}
