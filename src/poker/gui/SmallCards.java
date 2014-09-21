
package poker.gui;

import java.awt.*;
import java.awt.image.ImageObserver;

public class SmallCards extends TableObject {
	private int seatNum;
	private int numCards;

	public SmallCards(int seatNum, int numCards) {
		this.seatNum = seatNum;
		this.numCards   = numCards;
		Point p = Geometry.instance().getSmallCardLoc(seatNum);
		setLoc(p.x, p.y);
	}

	public void display(Graphics2D g2, ImageObserver table) {
		if (numCards > 0)
    		g2.drawImage(TableResources.smallCardImg, x, y, table);
		if (numCards > 1)
    		g2.drawImage(TableResources.smallCardImg, 
			  x+Geometry.instance().getSCXOffset(1), y+Geometry.instance().getSCYOffset(1), table);
	}
	
	public String toString() {
		return "Seat "+seatNum+" - "+numCards+ " cards";
	}
}
