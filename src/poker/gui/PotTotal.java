package poker.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.ImageObserver;


public class PotTotal extends TableObject {
	private double amount;

	public PotTotal(double amount) {
		this.amount   = amount;
		Point p = Geometry.instance().getPotTotalLoc();
		setLoc(p.x, p.y);
	}

	public void display(Graphics2D g2, ImageObserver table) {
		String total = "POT $"+getChipAmount(amount);

		// Compute the required width
		g2.setFont(TableResources.myfont);
		Font f = (Font) g2.getFont();
		int sw = g2.getFontMetrics(f).stringWidth(total);

		int w = 10 + sw;
		int h = 19;
		int gutter  = 4;
		g2.setColor(TableResources.LIGHT_TAN);
		g2.fillRect(x,y,w,h);	
		g2.setColor(Color.black);
		g2.drawRect(x,y,w,h);	
		g2.setColor(Color.black);
		g2.drawString(total, (int) (x+w/2-sw/2),y+h-gutter);
		g2.setColor(Color.magenta);
	}
	
	private String getChipAmount(double amt) {
		String amount = "" + amt;
		if (amount.endsWith(".0"))
			amount = amount.substring(0, amount.length()-2);
		return amount;
	}
	
	public String toString() {
		return "amount : "+amount;
	}
}
