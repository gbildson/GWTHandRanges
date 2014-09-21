package poker.gui;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.ImageObserver;
import java.text.NumberFormat;

public class Pot extends ChipObject implements HasToolTip {
	private double amt;
	protected ToolTipHelper tthelper;

	//protected Pot() {
	//}

	public Pot(int potNumber, double amt) {
		this.amt = amt;
		Point p = Geometry.instance().getPotLoc(potNumber);
		setLoc(p.x, p.y);
		tthelper = new ToolTipHelper(this, p.x, p.y, Geometry.CHIP_HORIZONTAL, 100);  //TODO: replace 100
	}

	public void display(Graphics2D g2, ImageObserver table) {
		displayChips(g2, x, y, amt, Geometry.FORWARD, table);
	}

	public boolean inside( int x, int y) {
		if (amt != 0.0d)
			return tthelper.inside(x, y);
		else
			return false;
	}

	public String getText() {
		return NumberFormat.getNumberInstance().format(amt);
	}

	public void deregister(){
		tthelper.deregister();
	}
	
	public String toString() {
		return "pot: "+getText();
	}
}
