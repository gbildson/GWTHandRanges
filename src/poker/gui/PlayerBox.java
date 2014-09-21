package poker.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.ImageObserver;

public class PlayerBox extends TableObject {

	private String nickname;
	private String amount;

	public PlayerBox(int seatNumber, String nickname, String amount) {
		this.nickname = nickname;
		this.amount   = amount;
		Point p = Geometry.instance().getBoxLoc(seatNumber);
		setLoc(p.x, p.y);
	}

	public void setAmount(String amount) {
		this.amount   = amount;
	}

	public void display(Graphics2D g2, ImageObserver table) {
		int w = 90;
		int h = 34;
		int gutter  = 3;
		g2.setColor(Color.black);
		g2.fillRect(x,y,w,h);	
		g2.setColor(Color.white);
		g2.drawRect(x,y,w,h);	
		g2.setColor(Color.gray);
		g2.drawLine(x+1,y+h/2,x+w-1,y+h/2);	
		g2.setFont(TableResources.myfont);
		Font f = (Font) g2.getFont();
		g2.setColor(Color.lightGray);
		int sw;
		if ( nickname != null ) {
			sw = g2.getFontMetrics(f).stringWidth(nickname);
			g2.drawString(nickname, (int) (x+w/2-sw/2),y+h/2-gutter);
		}
		if ( amount != null ) {
			sw = g2.getFontMetrics(f).stringWidth(amount);
			g2.drawString(amount, (int) (x+w/2-sw/2),y+h-gutter);
		}
		g2.setColor(Color.magenta);
	}
	
	public String toString() {
		return "Nick: "+nickname+" amt: "+amount;
	}
}

