package poker.gui;

import poker.CardCollection;
import poker.VisualFeedback;

class DealACard implements TableAnimation {
	private HoldemTable    vtable;
	private VisualFeedback vf;
	private int            playerNum;
	private CardCollection cards;
	private long           startTime;
	private SmallCard      card;

	public DealACard(HoldemTable vtable, VisualFeedback vf, int playerNum, CardCollection cards) {
		this.vtable    = vtable;
		this.vf        = vf;
		this.playerNum = playerNum;
		this.cards     = cards;
		startTime      = System.currentTimeMillis();
		card           = new SmallCard(playerNum, Geometry.CARD_SPEED, cards.size()-1);
		vtable.setCardInMotion(card); //cardInMotion   = card;
		//cardAnimation  = this;
		vtable.setCardAnimation(this);
		vtable.startAnim();
	}

	public void step() {
		int timeDelta = (int)(System.currentTimeMillis() - startTime);
		//System.out.println("tD="+timeDelta);

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
		vf.setPlayerCards(playerNum, cards);
	}

	public int maxDelay() {
		return ((card.getTimeToDeliver()+100));
	}
}
