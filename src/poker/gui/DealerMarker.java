package poker.gui;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.ImageObserver;

public class DealerMarker extends TableObject {

	private int playerNum;
	
	public DealerMarker(int playerNum) {
		Point p = Geometry.instance().getDealerLoc(playerNum);
		setLoc(p.x, p.y);
		this.playerNum = playerNum;
	}

	public void moveDealer(int playerNum) {
		Point p = Geometry.instance().getDealerLoc(playerNum);
		x = p.x;
		y = p.y;
	}

	public void display(Graphics2D g2, ImageObserver table) {
		g2.drawImage(TableResources.dealerImg, x, y, table);
	}
	
	public String toString() {
		return "Dealer: "+playerNum;
	}
}

