package tobias.test;

/**
 * Dieses Interface wird alle verlangte Methoden der verlangten Wrapper-Klasse
 * aus OOT haben.
 * 
 * @author Fernando
 *
 */
public interface GruppeX {
 
    /**
     * Zeigt dem Programm ob es zuerst anfängt.
     */
    public void youAreFirst();
 
    /**
     * Zeigt dem Programm das es als Zweites anfängt.
     */
    public void youAreSecond();
 
    /**
     * Zeigt dem Programm ob das Spiel noch läuft.
     * 
     * @return true, wenn das Spiel noch läuft
     * @return false, wenn einer gewonnen hat oder kein Spielzug mehr möglich
     *         ist
     */
    public boolean isRunning();
 
    /**
     * Unter der Voraussetzung, dass das Spiel für dieses Programm beendet ist,
     * fragt diese Methode das jeweilige Programm, ob der Computer gewonnen oder
     * verloren hat
     * 
     * @return 1, falls Programm gewonnen hat
     * @return 0, falls ein Unentschieden ist
     * @return -1, falls Programm verloren hat
     */
    public int whoWon();
 
    /**
     * Übergibt dem jeweiligen Programm den gegnerischen Zug.
     * 
     * @param hisMove
     * @return true, falls der Zug des Gegners gültig ist
     * @return false, falls der Zug des Gegbers ungültig ist.
     */
    public boolean takeYourMove(String hisMove);
 
    /**
     * Übergibt dem jeweiligen Programm den gegnerischen Zug.
     * 
     * @return String, den Zug des Programms
     */
    public String getMyMove();
 
    /**
     * Zeigt dem Programm ob der gegnerische Zug erlaubt ist.
     * 
     * @return true, falls der Zug des Gegners erlaubt ist.
     * @return false, falls der Zug des Gegners nicht erlaubt ist
     */
    public boolean canYouMove();
 
    /**
     * Zeigt dem Programm ob sein Move erlaubt ist.
     * 
     * @return true, falls der Zug erlaubt ist.
     * @return false, falls der Zug nicht erlaubt ist.
     */
    public boolean canIMove();
 
    /**
     * Druckt das Spielbrett im aktuellen Zustand auf der Konsole aus.
     */
    public void printBoard();
    
}

