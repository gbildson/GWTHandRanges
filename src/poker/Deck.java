
package poker;

public class Deck extends CardCollection {

	public Deck() {
		for(int i=0; i <= 3; i++) {
			for (int j=0; j <= 12; j++) {
			    add(Card.create(i,j));
			}
		}
	}
}
