package poker.gui;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.ImageObserver;

public class PlayersCards extends TableObject {
	private int seatNum;
	private int numCards;
	private int cards[];

	public PlayersCards(int seatNum, int cards[]) {
		this.seatNum = seatNum;
		this.numCards   = cards.length;
		this.cards      = cards;  //WARNING
		Point p = Geometry.instance().getPlayerLoc(seatNum);
		setLoc(p.x + 7, p.y + 3);
	}

	public void display(Graphics2D g2, ImageObserver table) {
		if (numCards > 0)
			g2.drawImage(TableResources.cards[cards[0]], x, y, table);
		if (numCards > 1)
			g2.drawImage(TableResources.cards[cards[1]], x+16, y+4, table);
	}
	
	public String toString() {
		String cardStr = "";
		for( int i = 0; i < cards.length; i++)
			cardStr += " " +cards[i];
		return "Seat "+seatNum+" - "+numCards+ " cards"+ " card ints:"+ cardStr;
	}
}
