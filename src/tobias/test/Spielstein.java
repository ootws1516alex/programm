package tobias.test;

public class Spielstein {

	private Koordinate koordinate;
	private char color;
	private boolean lady;
	
	public Spielstein(Koordinate koordinate, char color, boolean lady){
		this.color = color;
		this.lady = lady;
		this.koordinate=koordinate;
	}
	
	
	public char getColor() {
		return this.color;
	}

	public void setColor(char color) {
		this.color = color;
	}

	public void setLady(boolean lady){
		this.lady = lady;
	}
	
	public boolean isLady(){
		return this.lady;
	}

	public Koordinate getKoordinate() {
		return koordinate;
	}

	public void setKoordinate(Koordinate koordinate) {
		this.koordinate = koordinate;
	}
	
	
}
