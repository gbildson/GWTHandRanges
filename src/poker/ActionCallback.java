package poker;

public class ActionCallback implements WaitableCallback {

	private double bet = -1.0d;
	private VisualFeedback _vfeedback;
	
	public ActionCallback(VisualFeedback vf) {
		_vfeedback = vf;
	}
	
	public void deliverBettingResponse(double bet) {
		this.bet = bet;
		responded();
	}
	
	public double getBettingResponse() {
		return bet;
	}
	

	public void responded() {
		synchronized(this) {
			notify();
		}
	}


	public void waitForResponse(long time) {
		synchronized(this) {
			((VisualFeedbackMultiplexor)_vfeedback).setWaiting(true);
			try { wait(time);  
			} catch (InterruptedException ie) {
				_vfeedback.actionTimeout();
			}
			((VisualFeedbackMultiplexor)_vfeedback).setWaiting(false);
		}
	}

}
