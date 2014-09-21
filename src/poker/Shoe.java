
package poker;

public class Shoe extends CardCollection {
	public Shoe() {
	}

	public void addDeck(Deck deck) {
		addAll(deck);	
	}

	public Card dealCard() {
		return remove();
	}
}
