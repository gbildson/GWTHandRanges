
package poker.gui;

import java.awt.Point;

class Geometry {
	public static final int FORWARD  = 1;
	public static final int BACKWARD = -1;
	public static final int INPLACE  = 0;
	public static final int CHIP_VERTICAL   = 5;
	public static final int CHIP_HORIZONTAL = 23;
	public static double CARD_SPEED = 3.0;
	public static double POT_SPEED  = 0.4;
	public static double BET_SPEED  = 0.5;
	private Point   commonCardLoc;
	private Point   dealLoc;
	private Point[] playerLocs;
	private Point[] boxLocs;
	private Point[] dealerLocs;
	private Point[] smallCardLocs;
	private Point[] betLocs;
	private int[]   betDirection;
	private Point[] potLocs;
	private Point   potTotalLoc;
	
	private static Geometry instance;
	
	public static Geometry instance() {
		if ( instance == null )
			instance = new Geometry();
		return instance;
	}
	
	public Geometry() {
		// Player locations
		playerLocs = new Point[10];
		playerLocs[0] = new Point(480, 7);
		playerLocs[1] = new Point(620, 65);
		playerLocs[2] = new Point(700, 170);
		playerLocs[3] = new Point(600, 280);
		playerLocs[4] = new Point(450, 330);
		playerLocs[5] = new Point(270, 330);
		playerLocs[6] = new Point(115, 280);
		playerLocs[7] = new Point(20, 170);
		playerLocs[8] = new Point(100, 65);
		playerLocs[9] = new Point(240, 7);

		// Player Information locations
		boxLocs = new Point[10];
		boxLocs[0] = new Point(560,30);
		boxLocs[1] = new Point(700,90);
		boxLocs[2] = new Point(700,250);
		boxLocs[3] = new Point(590,360);
		boxLocs[4] = new Point(445,410);
		boxLocs[5] = new Point(270,410);
		boxLocs[6] = new Point(120,360);
		boxLocs[7] = new Point(10,250);
		boxLocs[8] = new Point(10,90);
		boxLocs[9] = new Point(150,30);

		// Dealer button locations
		dealerLocs = new Point[10];
		dealerLocs[0] = new Point(510, 90);
		dealerLocs[1] = new Point(650, 150);
		dealerLocs[2] = new Point(680, 230);
		dealerLocs[3] = new Point(580, 305);
		dealerLocs[4] = new Point(530, 330);
		dealerLocs[5] = new Point(250, 330);
		dealerLocs[6] = new Point(190, 310);
		dealerLocs[7] = new Point(100, 225);
		dealerLocs[8] = new Point(130, 150);
		dealerLocs[9] = new Point(270, 90);

		// Small card locations
		smallCardLocs = new Point[10];
		smallCardLocs[0] = new Point(540, 90);
		smallCardLocs[1] = new Point(600, 115);
		smallCardLocs[2] = new Point(675, 190);
		smallCardLocs[3] = new Point(588, 270);
		smallCardLocs[4] = new Point(427, 335);
		smallCardLocs[5] = new Point(352, 335);
		smallCardLocs[6] = new Point(187, 270);
		smallCardLocs[7] = new Point(100, 190);
		smallCardLocs[8] = new Point(176, 115);
		smallCardLocs[9] = new Point(237, 90);

		// Bet locations
		betLocs = new Point[10];
		betLocs[0] = new Point(510, 125); // BACKWARD
		betLocs[1] = new Point(615, 160); // BACKWARD
		betLocs[2] = new Point(640, 210); // BACKWARD
		betLocs[3] = new Point(560, 275); // BACKWARD
		betLocs[4] = new Point(495, 310); // BACKWARD
		betLocs[5] = new Point(285, 310);
		betLocs[6] = new Point(215, 275);
		betLocs[7] = new Point(130, 210);
		betLocs[8] = new Point(160, 160);
		betLocs[9] = new Point(260, 125);
		
		// Direction for bet display
		betDirection = new int[10];
		betDirection[0] = BACKWARD;
		betDirection[1] = BACKWARD;
		betDirection[2] = BACKWARD;
		betDirection[3] = BACKWARD;
		betDirection[4] = BACKWARD;
		betDirection[5] = FORWARD;
		betDirection[6] = FORWARD;
		betDirection[7] = FORWARD;
		betDirection[8] = FORWARD;
		betDirection[9] = FORWARD;

		// Pot locations
		potLocs = new Point[10];
		potLocs[0] = new Point(340,245);
		potLocs[1] = new Point(380,265);
		potLocs[2] = new Point(300,265);
		potLocs[3] = new Point(340,300);
		potLocs[4] = new Point(380,340);
		potLocs[5] = new Point(300,340);
		potLocs[6] = new Point(340,380);
		potLocs[7] = new Point(380,380);
		potLocs[8] = new Point(300,380);
		potLocs[9] = new Point(340,200);

		commonCardLoc = new Point(270, 160);

		// Point from which dealer deals
		dealLoc = new Point(300, 50);

		// Pot Total Ceter Location
		potTotalLoc = new Point(364, 12);
	}

	public Point getPlayerLoc(int playerNum) {
		return playerLocs[playerNum];
	}

	public Point getDealerLoc(int playerNum) {
		return dealerLocs[playerNum];
	}

	public Point getSmallCardLoc(int playerNum) {
		return smallCardLocs[playerNum];
	}

	public int getSCXOffset(int cardNum) {
    		return cardNum*5;
	}

	public int getSCYOffset(int cardNum) {
    		return cardNum*3;
	}

	public Point getDealLoc() {
		return dealLoc;
	}

	public Point getCommonCardLoc() {
		return commonCardLoc;
	}

	public Point getPotLoc(int potNumber) {
		return potLocs[potNumber];
	}

	public Point getBetLoc(int seatNum) {
		return betLocs[seatNum];
	}

	public int getBetDirection(int seatNum) {
		return betDirection[seatNum];
	}

	public Point getBoxLoc(int seatNum) {
		return boxLocs[seatNum];
	}

	public Point getPotTotalLoc() {
		return potTotalLoc;
	}
}
