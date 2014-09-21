package poker.gui;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.ImageObserver;
import java.text.NumberFormat;

public class Bet extends ChipObject implements HasToolTip {
	private double amts[];
	private int displayType;
	private AnimationTracker tracker;
	private ToolTipHelper tthelper;

	public Bet(int seatNumber, double amts[]) {
		this.amts = amts;
		Point p = Geometry.instance().getBetLoc(seatNumber);
		displayType = Geometry.instance().getBetDirection(seatNumber);
		setLoc(p.x, p.y);
		tthelper = new ToolTipHelper(this, computeTrueX(x,amts,displayType), p.y, computeWidth(amts), 100);  //TODO: replace 100
	}

	public void collect(int potNumber, double speed) {
		Point  source = new Point(x,y);
		Point  dest   = Geometry.instance().getPotLoc(potNumber);
		tracker = new AnimationTracker(source, dest, speed);

		int   timeToVicinity = tracker.getTimeToDeliver() * 94 / 100;
		Point vicinity       = tracker.move(timeToVicinity);
		tracker.updateDest(vicinity);
	}

	public void move(int timeDelta) {
		Point newLoc = tracker.move(timeDelta);
		setLoc(newLoc.x, newLoc.y);
	}

	public int getTimeToDeliver() {
		return tracker.getTimeToDeliver();
	}

	public void display(Graphics2D g2, ImageObserver table) {
		displayBets(g2, x, y, amts, displayType, table);
	}

	// Make room if you are displaying backwards from a point
	private int computeTrueX(int x, double[] amts, int displayType) {
		if ( displayType == Geometry.BACKWARD ) {
			x -= (amts.length - 1) * Geometry.CHIP_HORIZONTAL;
		}
		return x;
	}

	private int computeWidth(double[] amts) {
		return amts.length * Geometry.CHIP_HORIZONTAL;
	}

	private void displayBets(Graphics2D g2, int x, int y, double[] amts, 
			int displayType, ImageObserver table) { 

		x = computeTrueX(x, amts, displayType);
		for ( int i = 0; i < amts.length; i++ ) {
			displayChips(g2, x, y, amts[i], Geometry.INPLACE, table);
			x += Geometry.CHIP_HORIZONTAL;
		}
	}

	public boolean inside( int x, int y) {
		if (getAmountTotal() != 0.0d)
			return tthelper.inside(x, y);
		else
			return false;
	}

	public String getText() {
		double amtTot = getAmountTotal();
		return NumberFormat.getNumberInstance().format(amtTot);
	}
	
	private double getAmountTotal() {
		double amtTot = 0;
		for (int i = 0; i < amts.length; i++){
			amtTot += amts[i];
		}
		return amtTot;
	}

	public void deregister() {
		tthelper.deregister();
	}
	
	public String toString() {
		return "bet amt: "+getText()+ "anim: "+tracker;
	}
}
