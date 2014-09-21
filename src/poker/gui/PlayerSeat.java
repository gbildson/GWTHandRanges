package poker.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.ImageObserver;

public class PlayerSeat extends TableObject {
	private static final long CLEAR_STATUS_TIME = 1600;

	private String  statusString = null;
	private long    statusLastSetTime = -1;

	public PlayerSeat(int playerNum) {
		Point p = Geometry.instance().getPlayerLoc(playerNum);
		setLoc(p.x, p.y);
	}

	public void display(Graphics2D g2, ImageObserver table) {

		g2.drawImage(TableResources.circleImg, x, y, table);

		// Display a status line if desired
		if (statusString != null) {
			g2.setColor(Color.black);
			g2.setFont(TableResources.cuteFont);
			Font f = (Font) g2.getFont();
			g2.setColor(Color.lightGray);
			int sw;
			sw = g2.getFontMetrics(f).stringWidth(statusString);
			g2.setColor(Color.black);
			g2.drawString(statusString, (int) (x+40-sw/2),y+42);
		}
	}

	public void setStatusText(String text) {
		statusString = text;
		statusLastSetTime = System.currentTimeMillis();
	}

	public boolean checkToClearStatusString(long now) {
		if (statusLastSetTime > 0  && statusLastSetTime < (now - CLEAR_STATUS_TIME)) {
			statusString = null;
			statusLastSetTime = -1;
			return true;
		}
		return false;
	}
	
	public String toString() {
		return statusString;
	}
}

