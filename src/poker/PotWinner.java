
package poker;

public class PotWinner {
	private PlayerInGame player;
	private double       winnings;
	
	public PotWinner(PlayerInGame player, double winnings) {
		this.player = player;
		this.winnings = winnings;
	}

	public PlayerInGame getPlayer() {
		return player;
	}

	public double getWinnings() {
		return winnings;
	}
}
