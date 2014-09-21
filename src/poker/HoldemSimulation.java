
package poker;

public class HoldemSimulation {
	private static TexasHoldem game;
	
	public static void main(String args[]) {
		boolean automated = (args.length > 0 && args[0].equals("-a"));
		initGame(automated, null);
	}

	public static void initGame(boolean automated, VisualFeedback vf) {
	    //Player p1 = new Player(15.0, 0.0, "player1");
	    Player p1 = new Player(30000000.0, 0.0, "player1");
	    Player p2 = new Player(30000000.0, 0.0, "player2");
	    //Player p2 = new Player(25.0, 0.0, "player2");
	    //Player p3 = new Player(16.0, 0.0, "player3");
	    Player p3 = new Player(4000.0, 0.0, "player3");
	    Player p4 = new Player(4000.0, 0.0, "player4");
	    Player p5 = new Player(4000.0, 0.0, "player5");
	    Player p6 = new Player(20000.0, 0.0, "player6");
	    Player p7 = new Player(30000000.0, 0.0, "player7");
	    Player p8 = new Player(30000000.0, 0.0, "player8");
	    Player p9 = new Player(30000000.0, 0.0, "player9");
	    Player p10 = new Player(30000000.0, 0.0, "player10");
		PlayerInGame player1 = new PlayerInGame(p1, p1.getPlayMoney());
		PlayerInGame player2 = new PlayerInGame(p2, p2.getPlayMoney());
		PlayerInGame player3 = new PlayerInGame(p3, p3.getPlayMoney());
		PlayerInGame player4 = new PlayerInGame(p4, p4.getPlayMoney());
		PlayerInGame player5 = new PlayerInGame(p5, p5.getPlayMoney());
		PlayerInGame player6 = new PlayerInGame(p6, p6.getPlayMoney());
		PlayerInGame player7 = new PlayerInGame(p7, p7.getPlayMoney());
		PlayerInGame player8 = new PlayerInGame(p8, p8.getPlayMoney());
		PlayerInGame player9 = new PlayerInGame(p9, p9.getPlayMoney());
		PlayerInGame player10 = new PlayerInGame(p10, p10.getPlayMoney());
		
		//game = new TexasHoldem(10, 10.0, 20.0, 0.0, 1.0, TexasHoldem.NO_LIMIT); 
		game = new TexasHoldem(10, 0.01d, 0.02d, 0.0d, 0.01d, TexasHoldem.NO_LIMIT);
		
		//game.setAutomatedBetting(automated);
		game.setVisualFeedback(vf);
		if (vf instanceof VisualFeedbackMultiplexor) {  // TODO: Clean this up.
			((VisualFeedbackMultiplexor) vf).setSyncCallback(game);
		}
		if ( vf != null ) {
			vf.initTable(10);
			vf.initRound();
		}
		game.takeSeat(0, player1);
		game.takeSeat(1, player2);
		game.takeSeat(2, player3);
		game.takeSeat(3, player4);
		game.takeSeat(4, player5);
		game.takeSeat(5, player6);
		game.takeSeat(6, player7);
		game.takeSeat(7, player8);
		game.takeSeat(8, player9);
		game.takeSeat(9, player10);
		if ( vf != null ) {
			vf.update();
		}

		while (true) {
			game.playRound();
			
			try {
			Thread.sleep(5000);
			} catch (InterruptedException ie) {}
		}
	}
	
	public static void initNewTable(VisualFeedback vf) {
		game.initNewTable(vf);
	}

	/*
	private static void basicTest() {
		//  basic tests
		Deck deck = new Deck();
		System.out.println("Deck: "+deck);

		Shoe shoe = new Shoe();
		shoe.clear();
		shoe.addDeck(deck);
		System.out.println("Shoe: "+shoe);

		shoe.shuffle();
		System.out.println("Shuffled Shoe: "+shoe);

		Hand hand1 = new Hand();
		Hand hand2 = new Hand();
		CommonCards common = new CommonCards();
		hand1.add(shoe.dealCard());
		hand2.add(shoe.dealCard());
		hand1.add(shoe.dealCard());
		hand2.add(shoe.dealCard());
		common.add(shoe.dealCard());
		common.add(shoe.dealCard());
		common.add(shoe.dealCard());
		common.add(shoe.dealCard());
		common.add(shoe.dealCard());
		System.out.println("Hand1: "+hand1);
		System.out.println("Hand2: "+hand2);
		System.out.println("CommonCards: "+common);
		System.out.println("Shoe: "+shoe);
	}

	public static void oldTests() {
		//TwoCardHands two = new TwoCardHands();
		//two.displayRanks();

		//deck = new Deck();
		//shoe.clear();
		//shoe.addDeck(deck);
		//deck = new Deck();
		//shoe.addDeck(deck);
		//System.out.println("Two decks: "+shoe);

		//shoe.shuffle();
		//System.out.println("Two Shuffled decks: "+shoe);
	}
	*/
}
