package tobias.test;

public class Spielstein {

	private Koordinate k;
	private char color;
	private boolean lady;
	
	public Spielstein(int x, int y, char c, boolean l){
		this.setK(new Koordinate(x,y));
		this.color = c;
		this.lady = l;
	}
	
	public char getColor(){
		if(lady){
			return (char)(this.color - ' ');
		}
		return this.color;
	}
	
	public void setLady(boolean l){
		this.lady = l;
	}
	
	public boolean getLady(){
		return this.lady;
	}

	public Koordinate getK() {
		return k;
	}

	public void setK(Koordinate k) {
		this.k = k;
	}
	
	
}
