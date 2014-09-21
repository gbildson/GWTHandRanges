
package poker.gui;

import java.awt.*;
import java.awt.image.ImageObserver;

public abstract class TableObject {
	protected int x;
	protected int y;

	public TableObject() {
	}

	public void setLoc(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public abstract void display(Graphics2D g2, ImageObserver table);
}

