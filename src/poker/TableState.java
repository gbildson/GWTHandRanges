
package poker;

public class TableState {
	public int          cards[];
	public double       pots[];
	public int          dealer;
	public PlayerState  players[];
	public boolean      active;

	public TableState(int numPlayers) {
		players = new PlayerState[numPlayers];
		for (int i = 0; i < numPlayers; i++) {
			players[i] = new PlayerState(i);
		}
	}
}
