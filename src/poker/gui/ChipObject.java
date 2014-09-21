package poker.gui;

import java.awt.Graphics2D;
import java.awt.image.ImageObserver;

public abstract class ChipObject extends TableObject {

	protected void displayChips(Graphics2D g2, int x, int y, double amt, 
			int  displayType, ImageObserver table) {
		int    cnt;
		int    cno;
		double val;
		//System.out.println("Amt:"+amt);
		for ( int i = 14; i>=0; i-- ) {
			val = TableResources.chipVal[i];
			if (amt >= val) {
				cnt = (int) (amt/val);
				//System.out.print(val+":"+cnt+", ");
				amt -= ((double)cnt) * val;
				displayChipStack(g2,x,y,i,cnt, table);
				if (displayType == Geometry.FORWARD) {
					x += Geometry.CHIP_HORIZONTAL;
				} else if (displayType == Geometry.BACKWARD) {
					x -= Geometry.CHIP_HORIZONTAL;
				} else if  (displayType == Geometry.INPLACE) {
					y -= Geometry.CHIP_VERTICAL * cnt;
				}
			}
		}
		//System.out.println("");
	}

	protected void displayChipStack(Graphics2D g2,int x,int y,int cno,int cnt, 
			ImageObserver table) {
		for (int i = 0; i < cnt; i++) {
			g2.drawImage(TableResources.chips[cno], x, y-Geometry.CHIP_VERTICAL*i, table);
		}
	}
}
