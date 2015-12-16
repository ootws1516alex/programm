package tobias.test;

public class Koordinate {

	private String name;
	private int x,y;
	private Spielstein spielstein;
	
	public Koordinate(int y, int x, String name){
		this.x = x;
		this.y = y;
		this.setName(name);
	}

	public void setX(int x){
		this.x = x;
	}
	
	public int getX(){
		return this.x;
	}
	
	public void setY(int y){
		this.y = y;
	}
	
	public int getY(){
		return this.y;
	}
	
	public Spielstein getSpielstein(){
		return this.spielstein;
	}
	
	public void setSpielstein(Spielstein spielstein){
		this.spielstein=spielstein;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
