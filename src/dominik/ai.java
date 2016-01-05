package dominik;

import java.util.ArrayList;

public class ai extends Spiellogik{

	private ArrayList<Spielstein> wSteine;
	private ArrayList<Spielstein> sSteine;
	private Koordinate koords[][];
	private Bewegungsbewertung moeglicheZuege[][] = new Bewegungsbewertung[6][4];


	public String ai(int runde) {
		String zug = null;

		if(runde%2 == 0) {
			if(mussSchlagen(runde)) {
				for(int i = 0; i < sSteine.size(); i++) {
					zug = schlagenlogik(runde);
				}
			} else {
				for(int i = 0; i < sSteine.size(); i++) {
					zug = bewegenAi(runde);
				}
			}
		} else {
			if(mussSchlagen(runde)) {
				for(int i = 0; i < wSteine.size(); i++) {
					zug = schlagenlogik(runde);
				}
			} else {
				for(int i = 0; i < wSteine.size(); i++) {
					zug = bewegenAi(runde);
				}
			}
		}
		return zug;
	}

	private String bewegenAi(int runde) {

		if(runde%2 == 0) {
			bewegenLogik(runde, sSteine);
		} else {
			bewegenLogik(runde, wSteine);
		}

		Bewegungsbewertung best = new Bewegungsbewertung(null, -50);

		for(int i = 0; i < 6; i++) {
			for(int j = 0; j < 6; j++) {
				if(moeglicheZuege[i][j].getBewertung() >= best.getBewertung()) {
					best = moeglicheZuege[i][j];
				}
			}
		}
		return best.getZug();
	}

	private void bewegenLogik(int runde, ArrayList<Spielstein> steine) {

		Spielstein stein, temp;
		int bewertung;
		String bewegung;

		if(runde%2 == 1) {
			for(int i = 0; i < 3; i++) {
				for(int j = 0; j < 6; j++) {

					temp = koords[i][j].getSpielstein();
					koords[i][j].setSpielstein(koords[5 - i][5-j].getSpielstein());
					koords[5 - i][5-j].setSpielstein(temp);;
				}
			}
		}

		for(int i = 0; i < steine.size(); i++) {
			stein = steine.get(i);
			if(!stein.isLady()) {
				bewegung = stein.getKoordinate().getName() + ((char)(stein.getKoordinate().getName().charAt(0) + 1)) + ((char)(stein.getKoordinate().getName().charAt(1) + 1));
				if(richtigerZug(bewegung, runde)) {
					bewertung = bewerten(bewegung, stein, runde);
					Bewegungsbewertung bewegungsbewertung = new Bewegungsbewertung(bewegung, bewertung);
					moeglicheZuege[i][0] = bewegungsbewertung;
				}

				bewegung = stein.getKoordinate().getName() + ((char)(stein.getKoordinate().getName().charAt(0) + 1)) + ((char)(stein.getKoordinate().getName().charAt(1) - 1));
				if(richtigerZug(bewegung, runde)) {
					bewertung = bewerten(bewegung, stein, runde);
					Bewegungsbewertung bewegungsbewertung = new Bewegungsbewertung(bewegung, bewertung);
					moeglicheZuege[i][1] = bewegungsbewertung;
				}

			} else {

				bewegung = stein.getKoordinate().getName() + ((char)(stein.getKoordinate().getName().charAt(0) + 1)) + ((char)(stein.getKoordinate().getName().charAt(1) + 1));
				if(richtigerZug(bewegung, runde)) {
					bewertung = bewerten(bewegung, stein, runde);
					Bewegungsbewertung bewegungsbewertung = new Bewegungsbewertung(bewegung, bewertung);
					moeglicheZuege[i][0] = bewegungsbewertung;
				}
				bewegung = stein.getKoordinate().getName() + ((char)(stein.getKoordinate().getName().charAt(0) + 1)) + ((char)(stein.getKoordinate().getName().charAt(1) - 1));
				if(richtigerZug(bewegung, runde)) {
					bewertung = bewerten(bewegung, stein, runde);
					Bewegungsbewertung bewegungsbewertung = new Bewegungsbewertung(bewegung, bewertung);
					moeglicheZuege[i][1] = bewegungsbewertung;
				}
				bewegung = stein.getKoordinate().getName() + ((char)(stein.getKoordinate().getName().charAt(0) - 1)) + ((char)(stein.getKoordinate().getName().charAt(1) + 1));
				if(richtigerZug(bewegung, runde)) {
					bewertung = bewerten(bewegung, stein, runde);
					Bewegungsbewertung bewegungsbewertung = new Bewegungsbewertung(bewegung, bewertung);
					moeglicheZuege[i][2] = bewegungsbewertung;
				}
				bewegung = stein.getKoordinate().getName() + ((char)(stein.getKoordinate().getName().charAt(0) - 1)) + ((char)(stein.getKoordinate().getName().charAt(1) - 1));
				if(richtigerZug(bewegung, runde)) {
					bewertung = bewerten(bewegung, stein, runde);
					Bewegungsbewertung bewegungsbewertung = new Bewegungsbewertung(bewegung, bewertung);
					moeglicheZuege[i][3] = bewegungsbewertung;
				}
			}
		}

		if(runde%2 == 1) {
			for(int i = 0; i < 3; i++) {
				for(int j = 0; j < 6; j++) {

					temp = koords[i][j].getSpielstein();
					koords[i][j].setSpielstein(koords[5 - i][5-j].getSpielstein());
					koords[5 - i][5-j].setSpielstein(temp);;
				}
			}
		}
	}

	private String schlagenlogik(int runde) {

		return null;
	}

	private int bewerten(String bewegung, Spielstein stein, int runde) {

		int xAktuell, xSoll, yAktuell, ySoll, xBewegung, yBewegung, bewertung = 0;

		xAktuell = bewegung.charAt(0) - '@';
		yAktuell = bewegung.charAt(1) - '0';
		xSoll = bewegung.charAt(2) - '@';
		ySoll = bewegung.charAt(3) - '0';
		xBewegung = xAktuell - xSoll;
		yBewegung = yAktuell - ySoll;



		if(koords[ySoll - yBewegung][xSoll - xBewegung].getSpielstein() != null) {
			if(runde%2 == 0) {
				if(koords[ySoll - yBewegung][xSoll - xBewegung].getSpielstein().getColor() == 'w') {
					if(stein.isLady()) {
						bewertung -= 20;
					} else {
						bewertung -= 10;
					}
				}
			} else {
				if(koords[ySoll - yBewegung][xSoll - xBewegung].getSpielstein().getColor() == 's') {
					if(stein.isLady()) {
						bewertung -= 20;
					} else {
						bewertung -= 10;
					}				}
			}
		} 
		if(koords[ySoll + yBewegung][xSoll - xBewegung].getSpielstein() != null) {
			if(runde%2 == 0){
				if(koords[ySoll + yBewegung][xSoll - xBewegung].getSpielstein().getColor() == 'w') {
					if(koords[ySoll - yBewegung][xSoll + xBewegung].getSpielstein() != null) {
						if(stein.isLady()) {
							bewertung -= 20;
						} else {
							bewertung -= 10;
						}					}
				}
			} else {
				if(koords[ySoll + yBewegung][xSoll - xBewegung].getSpielstein().getColor() == 's') {
					if(koords[ySoll - yBewegung][xSoll + xBewegung].getSpielstein() != null) {
						if(stein.isLady()) {
							bewertung -= 20;
						} else {
							bewertung -= 10;
						}					}
				}
			}
		} 
		if(koords[ySoll - yBewegung][xSoll + xBewegung].getSpielstein() != null) {
			if(runde%2 == 0) {
				if(koords[ySoll - yBewegung][xSoll + xBewegung].getSpielstein().getColor() == 'w') {
					if(koords[ySoll + yBewegung][xSoll - xBewegung].getSpielstein() != null) {
						if(stein.isLady()) {
							bewertung -= 20;
						} else {
							bewertung -= 10;
						}					}
				}
			} else {
				if(koords[ySoll - yBewegung][xSoll + xBewegung].getSpielstein().getColor() == 's') {
					if(koords[ySoll + yBewegung][xSoll - xBewegung].getSpielstein() != null) {
						if(stein.isLady()) {
							bewertung -= 20;
						} else {
							bewertung -= 10;
						}					}
				}
			}
		}

		if(stein.isLady()) {
			if(bewertung < -20) {
				bewertung = -20;
			} else {
				if(bewertung < -10) {
					bewertung = -10;
				}
			}
		}

		return bewertung;
	}
}