package tobias.test;

import java.util.Scanner;


public class HumanVSHuman {

	private int color;
	private int time;
	Spielfeld spielfeld;
	Ausgabe ausgabe;
	Spieler spieler1;
	Spieler spieler2;
	Koordinate[][] koords;

	public HumanVSHuman(int time, int color) {
		this.time = time;
		this.color = color;
		initGame();
		//startGame();
	}

	public int getTime() {
		return time;
	}

	/**
	 * Funktioniert. Enthält im moment testaufrufe
	 */
	public void initGame() {
		spielfeld = new Spielfeld();
		this.koords = spielfeld.getKoords();
		Ausgabe ausgabe = new Ausgabe(koords);
		//ausgabe.zeigeBrett();
		//ziehen("B5A4",1);
		//ausgabe.zeigeBrett();
		//ausgabe.zeigeGedrehtesBrett();
		//ziehen("A2B3",0);
		//ausgabe.zeigeGedrehtesBrett();
		//ausgabe.zeigeBrett();
		
	}

	/**
	 * Soll die Rundenlogik beinhalten (TODO)
	 */
	public void startGame() {
		System.out.println("Das Spielfeld wurde erzeugt");
		System.out.print("Spieler1 (schwarz) geben sie ihren Namen ein: ");
		Scanner in = new Scanner(System.in);
		spieler1 = new Spieler(in.nextLine(), 's');
		System.out.print("Spieler2 (weiß) geben sie ihren Namen ein: ");
		spieler2 = new Spieler(in.nextLine(), 'w');
		int spielZug = 0;
		boolean setExit = false;
		boolean isLegit = true;
		do {
			do {
				if (spielZug % 2 == 0) {
					System.out
							.println(spieler1.getName()
									+ " ist am Zug, gib die Koordinaten für deinen Spielzug ein. (zb. B5C4)");
				} else {
					System.out
							.println(spieler2.getName()
									+ " ist am Zug, gib die Koordinaten für deinen Spielzug ein. (zb. B5C4)");
				}

				Scanner scanner = new Scanner(System.in);
				String aktuellerZug = scanner.nextLine();
				aktuellerZug = aktuellerZug.toUpperCase();

				if (!pruefeAufgueltigeKoord(aktuellerZug)) {
					isLegit = false;
					System.out
							.println("ungültige Koordinaten, bitte gültige Koordinaten eingeben (zb. B5C4)");
					spielZug--;
					spielfeld.dreheBrett();
				} else if (spielfeld.richtigerZug(aktuellerZug, spielZug) == false
						&& spielfeld.mussSchlagen(aktuellerZug, spielZug) == false) {

					// else if(spielfeld.richtigerZug(aktuellerZug,
					// spielZug)==false){
					System.out.println(spielfeld.richtigerZug(aktuellerZug,
							spielZug));
					System.out.println(spielZug);
					isLegit = false;
					System.out
							.println("ungültiger Zug, bitte Koordinaten erneut eingeben (zb. B5C4)");
					spielZug--;
					spielfeld.dreheBrett();

				} else {
					spielfeld.ziehen(aktuellerZug, spielZug);
				}
				spielfeld.zeigeBrett();
				spielfeld.dreheBrett();
				spielfeld.zeigeBrett();

				spielZug++;
			} while (isLegit == false);
		} while (setExit == false);

	}

	/**
	 * Funktioniert
	 * @param aktuellerZug
	 * @return
	 */
	private boolean pruefeAufgueltigeKoord(String aktuellerZug) {
		if (aktuellerZug.length() != 4) {
			return false;
		}
		String moeglicheBuchstaben = "ABCDEF";
		for (int i = 0; i < aktuellerZug.length() - 1; i++) {
			if (i % 2 == 0) {
				if (moeglicheBuchstaben.indexOf(aktuellerZug.charAt(i)) == -1) {
					return false;
				}
			} else {
				if (aktuellerZug.charAt(i) < '1'
						|| aktuellerZug.charAt(i) > '6') {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * Führt eine Bewegung aus. Funktioniert
	 * @param aktuellerZug
	 * @param zug
	 */
	public void ziehen(String aktuellerZug, int zug) {
		Spielstein temp;
		temp = koords[aktuellerZug.charAt(1) - '0' - 1][aktuellerZug.charAt(0) - '@' - 1].getSpielstein();
		koords[aktuellerZug.charAt(1) - '0' - 1][aktuellerZug.charAt(0) - '@' - 1].setSpielstein(null);
		koords[aktuellerZug.charAt(3) - '0' - 1][aktuellerZug.charAt(2) - '@' - 1].setSpielstein(temp);				
	}

	/**
	 * TODO(komplett überarbeitung)
	 * @param aktuellerZug
	 * @param zug
	 */
	private void schlagen(String aktuellerZug, int zug) {

		int yAktuell = aktuellerZug.charAt(1) - '0' - 1;
		int xAktuell = aktuellerZug.charAt(0) - '@' - 1;
		int ySoll = aktuellerZug.charAt(3) - '0' - 1;
		int xSoll = aktuellerZug.charAt(2) - '@' - 1;

		Spielstein temp;
		// koords[aktuellerZug.charAt(0)-'@'-1][aktuellerZug.charAt(1)-'0'-1].getSpielstein().getKoordinate().setX(aktuellerZug.charAt(2)-'@'-1);
		// koords[aktuellerZug.charAt(0)-'@'-1][aktuellerZug.charAt(1)-'0'-1].getSpielstein().getKoordinate().setY(aktuellerZug.charAt(3)-'0'-1);
		if (zug % 2 == 0) {
			temp = koords[xAktuell][yAktuell].getSpielstein();
			koords[xAktuell][yAktuell].setSpielstein(koords[xSoll][ySoll]
					.getSpielstein());
			koords[xSoll][ySoll].setSpielstein(temp);
			if (xSoll == xAktuell + 2) {
				koords[xSoll - 1][ySoll + 1].setSpielstein(null);

			} else if (xSoll == xAktuell - 2) {
				koords[xSoll + 1][ySoll + 1].setSpielstein(null);

			}

		} else if (zug % 2 == 1) {
			temp = koords[5 - xAktuell][5 - yAktuell].getSpielstein();
			koords[5 - xAktuell][5 - yAktuell]
					.setSpielstein(koords[5 - xSoll][5 - ySoll].getSpielstein());
			koords[5 - xSoll][5 - ySoll].setSpielstein(temp);
			if (5 - xSoll == 5 - xAktuell + 2) {
				koords[5 - xSoll - 1][5 - ySoll - 1].setSpielstein(null);

			} else if (5 - xSoll == 5 - xAktuell - 2) {
				koords[5 - xSoll + 1][5 - ySoll - 1].setSpielstein(null);
			}
		}
	}

	/**
	 *
	 * Muss NACH einem zug überprüfen ob schlagpflicht bestanden hätte.
	 * TODO(komplett überarbeitung)
	 * @param aktuellerZug
	 * @param zug
	 * @return
	 */
	public boolean mussSchlagen(String aktuellerZug, int zug) {

		int xAktuell = aktuellerZug.charAt(1) - '0' - 1;
		int yAktuell = aktuellerZug.charAt(0) - '@' - 1;
		int xSoll = aktuellerZug.charAt(3) - '0' - 1;
		int ySoll = aktuellerZug.charAt(2) - '@' - 1;
		int z;

		if (zug % 2 == 0) {
			z = 0;
		} else {
			z = 5;
		}

		
			if (z - xSoll == z - xAktuell + 2) {
				if ((koords[z - xAktuell][z - yAktuell].getSpielstein() != null && (koords[z
						- xSoll][z - ySoll].getSpielstein() == null))
						&& koords[z - xSoll][z - ySoll + 1].getSpielstein() != null) {
					return true;
				}
			} else if (z - xSoll == z - xAktuell - 2) {
				if ((koords[z - xAktuell][z - yAktuell].getSpielstein() != null && (koords[z
						- xSoll][z - ySoll].getSpielstein() == null))
						&& koords[z - xSoll][z - ySoll + 1].getSpielstein() != null) {
					return true;
				}
			}
		
		return false;
	}
	/**
	 * Überprüft im Moment nur ob eine Bewegung(kein schlag) möglich ist.
	 * Schlag-Zug muss auch true ergeben, oder extra methode "richtigerSchlagZug()" ?? (TODO)
	 * 
	 * @param aktuellerZug
	 * @param zug
	 * @return
	 */
	public boolean richtigerZug(String aktuellerZug, int zug) {

		int xAktuell = aktuellerZug.charAt(1) - '0' - 1;
		int yAktuell = aktuellerZug.charAt(0) - '@' - 1;
		int xSoll = aktuellerZug.charAt(3) - '0' - 1;
		int ySoll = aktuellerZug.charAt(2) - '@' - 1;
		int z;
		if(zug%2==0){
			z=2;
		}else{
			z=0;
		}
			if (koords[xAktuell][yAktuell].getSpielstein() != null && (koords[xSoll][ySoll].getSpielstein() != null)) {
				return false;
			} else if (koords[xAktuell][yAktuell].getSpielstein() == null) {
				return false;
			} else if (koords[xAktuell][yAktuell].getSpielstein().isLady() == false) {
				if (xAktuell != xSoll + 1-z&& (yAktuell != ySoll + 1 || yAktuell != ySoll - 1)) {
					return false;
				}
			} else if (koords[xAktuell][yAktuell].getSpielstein().isLady() == true) {
				int i1 = xSoll - xAktuell;
				int i2 = ySoll - yAktuell;
				if (Math.abs(i1) != Math.abs(i2)) {
					return false;
				}
			}
		return true;
	}
}
