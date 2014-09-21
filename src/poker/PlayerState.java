
package poker;

public class PlayerState {
	public int     position;
	public boolean sitting;
	public int     activeCards;
	public String  nickname;
	public String  amount;
	public boolean showCards;
	public int     cards[];
	public double  bets[];
	
	public PlayerState(int position) {
		this.position = position;
	}
}

