package poker.gui;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.ImageObserver;

public class SmallCard extends TableObject {
	private Point  source;
	private Point  dest;
	private AnimationTracker tracker;

	/*
	 * Create a card for a player.
	 */
	public SmallCard(int seatNum, double speed, int cardNum) {
		source = Geometry.instance().getDealLoc();
		Point p2 = Geometry.instance().getSmallCardLoc(seatNum);
		source = Geometry.instance().getDealLoc();
		dest = new Point(p2.x + Geometry.instance().getSCXOffset(cardNum), 
				p2.y + Geometry.instance().getSCYOffset(cardNum));
		tracker = new AnimationTracker(source, dest, speed);
		setLoc(source.x, source.y);
	}
	
	/*
	 * Create a common card.
	 */
	public SmallCard(int cardNum, double speed) {
		source = Geometry.instance().getDealLoc();
		
		Point p = Geometry.instance().getCommonCardLoc();
		dest = new Point(p.x+CommonCards.COMMON_CARD_OFFSET*cardNum, p.y+CommonCards.BOTTOM_OFFSET);
		
		tracker = new AnimationTracker(source, dest, speed);
		setLoc(source.x, source.y);
	}

	public void move(int timeDelta) {
		Point newLoc = tracker.move(timeDelta);
		setLoc(newLoc.x, newLoc.y);
	}

	public int getTimeToDeliver() {
		return tracker.getTimeToDeliver();
	}

	public void display(Graphics2D g2, ImageObserver table) {
		g2.drawImage(TableResources.smallCardImg, x, y, table);
	}
}
