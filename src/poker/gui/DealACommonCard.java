package poker.gui;

import poker.CardCollection;
import poker.VisualFeedback;
import poker.*;

class DealACommonCard implements TableAnimation {
	private HoldemTable    vtable;
	private VisualFeedback vf;
	private int            cardNum;
	//private Card 		   commonCard;
	private long           startTime;
	private SmallCard      card;

	public DealACommonCard(HoldemTable vtable, VisualFeedback vf, int cardNum/*, Card commonCard*/) {
		this.vtable     = vtable;
		this.vf         = vf;
		this.cardNum    = cardNum;
		//this.commonCard = commonCard;
		startTime       = System.currentTimeMillis();
		card            = new SmallCard(cardNum, Geometry.CARD_SPEED);
		vtable.setCardInMotion(card); // Reusing this animation for common cards
		vtable.setCardAnimation(this);
		vtable.startAnim();
	}

	public void step() {
		int timeDelta = (int)(System.currentTimeMillis() - startTime);
		//System.out.println("** tD="+timeDelta);

		vtable.moveCardInMotion(timeDelta); //cardInMotion.move(timeDelta);
		if (timeDelta > (card.getTimeToDeliver()+10)) {
			done();
		}
		vtable.repaint();
	}


	private void done() {
		vtable.clearCardInMotion(); // cardInMotion  = null;
		//cardAnimation = null;
		vtable.setCardAnimation(null);
		vtable.stopAnim();
		vtable.setNumActiveCommonCards(cardNum);
	}

	public int maxDelay() {
		return ((card.getTimeToDeliver()+100));
	}
}
