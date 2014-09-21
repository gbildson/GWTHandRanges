
package poker;

import java.util.ArrayList;
import poker.gui.HoldemGame;

public class VisualFeedbackMultiplexor implements VisualFeedback {

	private ArrayList observers = new ArrayList();
	private ArrayList<VisualFeedback> newObservers = new ArrayList<VisualFeedback>();
	private SyncCallback syncCallback;
	private static boolean waiting = false;
	private static Object  waitingLock = new Object();

	public static void setWaiting(boolean waiting) {
		synchronized(waitingLock) {
			VisualFeedbackMultiplexor.waiting = waiting;
		}
	}
	
	public void ifWaitingSync() {
		synchronized(waitingLock) {
			if (waiting) {
				sync();
			}
		}
	}
	
	public void addObserver(VisualFeedback observer){
		observers.add(observer);
	}
	
	public void addObserverAfterSync(VisualFeedback observer){
		newObservers.add(observer);
		ifWaitingSync();
	}
	
	public void setSyncCallback(SyncCallback syncCallback) {
		this.syncCallback = syncCallback;
	}
	
	public void sync() {
		if (newObservers.size()> 0) {
			for (VisualFeedback vf : newObservers){
				syncCallback.finishInitNewTable(vf);
				addObserver(vf);
			}
			//HoldemGame.hackDump();
			do {
				newObservers.remove(newObservers.get(0));
			} while (newObservers.size()> 0);
		}
	}

	public void initTable(int numPlayers) {
		VisualFeedback vf;
		for (int i = 0; i < observers.size(); i++) {
			vf = (VisualFeedback) observers.get(i);
			vf.initTable(numPlayers);
		}
	}

	public void setDealer(int dealerLoc) {
		VisualFeedback vf;
		for (int i = 0; i < observers.size(); i++) {
			vf = (VisualFeedback) observers.get(i);
			vf.setDealer(dealerLoc);
		}
	}

	public void setPots(double pots[]) {
		VisualFeedback vf;
		for (int i = 0; i < observers.size(); i++) {
			vf = (VisualFeedback) observers.get(i);
			vf.setPots(pots);
		}
	}

	public void deliverPot(int potNum, PotWinner potWinners[]) {
		VisualFeedback vf;
		for (int i = 0; i < observers.size(); i++) {
			vf = (VisualFeedback) observers.get(i);
			vf.deliverPot(potNum, potWinners);
		}
	}

	public void setCommonCards(CardCollection cards) {
		VisualFeedback vf;
		for (int i = 0; i < observers.size(); i++) {
			vf = (VisualFeedback) observers.get(i);
			vf.setCommonCards(cards);
		}
	}

	public void setPlayer(int playerNum, String nickname) {
		VisualFeedback vf;
		for (int i = 0; i < observers.size(); i++) {
			vf = (VisualFeedback) observers.get(i);
			vf.setPlayer(playerNum, nickname);
		}
	}

	public void setPlayerAmount(int playerNum, String amount, int delay) {
		VisualFeedback vf;
		for (int i = 0; i < observers.size(); i++) {
			vf = (VisualFeedback) observers.get(i);
			vf.setPlayerAmount(playerNum, amount, delay);
		}
	}

	public void dealPlayerCard(int playerNum, CardCollection cards) {
		VisualFeedback vf;
		for (int i = 0; i < observers.size(); i++) {
			vf = (VisualFeedback) observers.get(i);
			vf.dealPlayerCard(playerNum, cards);
		}
	}

	public void setPlayerCards(int playerNum, CardCollection cards) {
		VisualFeedback vf;
		for (int i = 0; i < observers.size(); i++) {
			vf = (VisualFeedback) observers.get(i);
			vf.setPlayerCards(playerNum, cards);
		}
	}

	public void setAllShowCards(boolean showCards) {
		VisualFeedback vf;
		for (int i = 0; i < observers.size(); i++) {
			vf = (VisualFeedback) observers.get(i);
			vf.setAllShowCards(showCards);
		}
	}

	public void setPlayerShowCards(int playerNum, boolean showCards) {
		VisualFeedback vf;
		for (int i = 0; i < observers.size(); i++) {
			vf = (VisualFeedback) observers.get(i);
			vf.setPlayerShowCards(playerNum, showCards);
		}
	}

	public void setPlayerBets(int playerNum, double bets[], int delay) {
		VisualFeedback vf;
		for (int i = 0; i < observers.size(); i++) {
			vf = (VisualFeedback) observers.get(i);
			vf.setPlayerBets(playerNum, bets, delay);
		}
	}
	public void setPlayerBetState(int playerNum, String state) {
		VisualFeedback vf;
		for (int i = 0; i < observers.size(); i++) {
			vf = (VisualFeedback) observers.get(i);
			vf.setPlayerBetState(playerNum, state);
		}
	}

	public void collectBets(int potNum) {
		VisualFeedback vf;
		for (int i = 0; i < observers.size(); i++) {
			vf = (VisualFeedback) observers.get(i);
			vf.collectBets(potNum);
		}
	}
	
	public void takeAction(int bettingMode, String nick, double playerBet, double toCall, double callable, double bigBlind,
			  double maxRaiseIfNoLimit, double raiseIfLimit, double maxRaiseIfPotLimit, 
			  double minNonLimitRaise, ActionCallback ac) {
		VisualFeedback vf;
		for (int i = 0; i < observers.size(); i++) {
			vf = (VisualFeedback) observers.get(i);
			vf.takeAction(bettingMode, nick, playerBet, toCall, callable, bigBlind, maxRaiseIfNoLimit, raiseIfLimit, 
			  maxRaiseIfPotLimit, minNonLimitRaise, ac);
		}
	}
	
	public void actionTimeout() {
		VisualFeedback vf;
		for (int i = 0; i < observers.size(); i++) {
			vf = (VisualFeedback) observers.get(i);
			vf.actionTimeout();
		}
	}

	public void initRound() {
		VisualFeedback vf;
		for (int i = 0; i < observers.size(); i++) {
			vf = (VisualFeedback) observers.get(i);
			vf.initRound();
		}
	}

	public void update() {
		VisualFeedback vf;
		for (int i = 0; i < observers.size(); i++) {
			vf = (VisualFeedback) observers.get(i);
			vf.update();
		}
	}
}
