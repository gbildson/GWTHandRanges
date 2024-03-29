package poker.gui;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import poker.PotWinner;

class DeliverPot implements TableAnimation {
	private static final Log LOG = LogFactory.getLog(DeliverPot.class);
	
	HoldemTable vtable;
	private PotWinner  potWinners[];
	private PartialPot partialPots[];
	private long       startTime;
	private int        endTime;
	private int        potNum;

	public DeliverPot(HoldemTable vtable, int potNum, PotWinner potWinners[]) {
		if (LOG.isDebugEnabled())
			LOG.debug("Created DeliverPot anim :"+potNum);
		
		this.vtable     = vtable;
		this.potWinners = potWinners;
		startTime       = System.currentTimeMillis();
		endTime         = 0;
		this.potNum = potNum;

		partialPots = new PartialPot[potWinners.length];
		PotWinner potWinner;
		for (int i = 0; i < potWinners.length; i++) {
			potWinner = potWinners[i];
			partialPots[i] = new PartialPot(potWinner.getPlayer().getSeatNum(), potNum,
					Geometry.POT_SPEED, potWinner.getWinnings());
			int dTime = partialPots[i].getTimeToDeliver();
			endTime = Math.max(endTime, dTime);
		}
		vtable.setPotsInMotion(potNum, partialPots);  //potsInMotion  = partialPots;
		//potsAnimation = this;
		vtable.addPotsAnimation(this);
		vtable.startAnim();
	}



	public void step() {
		int timeDelta = (int)(System.currentTimeMillis() - startTime);
		//System.out.println("tD="+timeDelta);

		for (int i = 0; i < partialPots.length; i++) {
			partialPots[i].move(timeDelta);
		}
		if (timeDelta > (endTime+300)) {
			done();
		}
		vtable.repaint();
	}

	private void done() {
		//for (int i = 0; i < potsInMotion.length; i++) {
		//ToolTipHelper.clearToolTip(potsInMotion[i]);
		//}
		//potsInMotion  = null;
		//System.out.println("**In Done** -"+potNum);
		//for (int i = 0; i < partialPots.length; i++) { //TODO: Note this is double work.
			//ToolTipHelper.clearToolTip(partialPots[i]);
		//}
		vtable.clearPotsInMotion(potNum);
		//potsAnimation = null;
		vtable.removePotsAnimation(this);
		vtable.stopAnim();
		ToolTipHelper.dump();
		//System.out.println("**Out of Done** -"+potNum);
	}

	public int maxDelay() {
		return (endTime+80);
	}
}
