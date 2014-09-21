package poker.gui;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.ImageObserver;

public class CommonCards extends TableObject {
	
	public static final int COMMON_CARD_OFFSET = 55;
	public static final int BOTTOM_OFFSET      = 50;

	
	private int cards[];
	private int numActive;

	public CommonCards(int cards[], int numActive) {
		this.cards      = cards;   //WARNING
		this.numActive  = numActive;
		Point p = Geometry.instance().getCommonCardLoc();
		setLoc(p.x, p.y);
	}

	public void display(Graphics2D g2, ImageObserver table) {
		for (int i = 0; i < this.cards.length && i < numActive; i++) {
			g2.drawImage(TableResources.cards[this.cards[i]], x+COMMON_CARD_OFFSET*i, y, table);
		}
	}
	
	public void setNumActive(int num) {
		numActive = num;
	}
	
	public String toString() {
		String cardStr = " cards:";
		for (int i = 0; i < cards.length; i++) 
			cardStr += " "+cards[i];
		return cardStr;
	}
}

