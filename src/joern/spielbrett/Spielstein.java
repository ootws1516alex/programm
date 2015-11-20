package joern.spielbrett;

public class Spielstein {

	private int x,y;
	private String color;
	private boolean lady = false;
	
	public Spielstein(int x, int y, String c){
		this.x = x;
		this.y = y;
		this.color = c;
	}
	
	public Spielstein(int x, int y, String c, boolean l){
		this.x = x;
		this.y = y;
		this.color = c;
		this.lady = l;
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
	
	public String getColor(){
		return this.color;
	}
	
	public void setLady(boolean l){
		this.lady = l;
	}
	
	public boolean getLady(){
		return this.lady;
	}
}
