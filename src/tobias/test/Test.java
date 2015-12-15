package tobias.test;


public class Test {

	public static void main(String[] args) {
		Spielfeld s=new Spielfeld();
		Ausgabe a= new Ausgabe(s.getKoords());
		//a.zeigeBrett();
		//a.zeigeGedrehtesBrett();
		HumanVSHuman hu = new HumanVSHuman(100,1);
	}
	
}
