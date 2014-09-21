package poker.gui;

import java.awt.Point;


public class PartialPot extends Pot {
	private Point  source;
	private Point  dest;
	private AnimationTracker tracker;

	public PartialPot(int seatNumber, int potNumber, double speed, double amt) {
		super(potNumber, amt);
		//this.amt = amt;
		source = Geometry.instance().getPotLoc(potNumber);
		dest = Geometry.instance().getBetLoc(seatNumber);
		//displayType = geometry.getBetDirection(seatNumber);
		tracker = new AnimationTracker(source, dest, speed);
		setLoc(source.x, source.y);
		//tthelper = new ToolTipHelper(this, source.x, source.y, Geometry.CHIP_HORIZONTAL, 100);  //TODO: replace 100
	}

	public void move(int timeDelta) {
		Point newLoc = tracker.move(timeDelta);
		setLoc(newLoc.x, newLoc.y);
		tthelper.reposition(newLoc.x, newLoc.y);
	}

	public int getTimeToDeliver() {
		return tracker.getTimeToDeliver();
	}
}

