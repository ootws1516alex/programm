package joern.spielbrett;

public class Koordinate {

	private int x,y;
	
	public Koordinate(int x, int y){
		this.x = x;
		this.y = y;
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
	
}
