package org.pokerai.eval;

import org.pokerai.spears2p2.StateTableEvaluator;
import org.pokerai.spears2p2.Card;

import com.cardfight.client.CardFightResult;

import java.io.*;
import java.util.*;


public class CalculateStats {
	
  public static final void waitfor(int ms) { try { Thread.currentThread().sleep(ms); } catch (Exception e) { e.printStackTrace(); }}

  public static void main(String args[]) {
    //testHandMatchupsGB();
    testHandMatchupsGB2();
    //testHandRangesGB();
    //test3RandomGB();
    //test3Random2GB();
  }


  public static void testHandMatchupsGB2() {
//String priorHandType ="";
    StateTableEvaluator.initialize();
    Card[] cards = new Card[7];
    //for (int i = 0; i < 7; i++) cards[i] = pokerai.game.eval.spears2p2.Card.parse("AsAh");
    //pokerai.game.eval.spears.Card[] deck = pokerai.game.eval.spears.Card.values();
    long time = System.currentTimeMillis();
    long sum = 0;
    //long win1 = 0;
    //long win2 = 0;
    //long tie = 0;
	WinningStats stats1 = new WinningStats();
	WinningStats stats2 = new WinningStats();
    int rank1;
    int rank2;
    int h1, h2, h3, h4, h5, h6, h7;
	try {
		//FileReader input = new FileReader("neededHands.txt");
		FileReader input = new FileReader("QQneeded.txt");
		BufferedReader bufRead = new BufferedReader(input);
		String line;
		int count = 0;
		line = bufRead.readLine();
		count++;

		String hand1, hand2;
		String sh1c1, sh1c2, sh2c1, sh2c2;
boolean firstPass = true;
		while (line != null){
if (!firstPass) return;
firstPass = false;
			String [] pieces = line.split("\t");
			if (pieces.length == 2) {
				hand1 = pieces[0];
				hand2 = pieces[1];
                hand1 = "QhQd";
                hand2 = "Th5d";
				sh1c1 = hand1.substring(0,2);
				sh1c2 = hand1.substring(2,4);
				sh2c1 = hand2.substring(0,2);
				sh2c2 = hand2.substring(2,4);

    Card h1c1 = Card.parse(sh1c1);
    Card h1c2 = Card.parse(sh1c2);
    Card h2c1 = Card.parse(sh2c1);
    Card h2c2 = Card.parse(sh2c2);
	int h1o1 = h1c1.ordinal();
	int h1o2 = h1c2.ordinal();
	int h2o1 = h2c1.ordinal();
	int h2o2 = h2c2.ordinal();
    //System.out.println("h101: " + h1o1);
    //System.out.println("h101: " + h1o2);
    //System.out.println("h101: " + h2o1);
    //System.out.println("h101: " + h2o2);

    for (h1 = 0; h1 < 52; h1++) {
	  if ( h1 == h1o1 || h1 == h1o2 || h1== h2o1 || h1 == h2o2) continue;
      cards[0] = Card.get(h1);
      for (h2 = h1 + 1; h2 < 52; h2++) {
	    if ( h2 == h1o1 || h2 == h1o2 || h2== h2o1 || h2 == h2o2) continue;
        cards[1] = Card.get(h2);
        for (h3 = h2 + 1; h3 < 52; h3++) {
	      if ( h3 == h1o1 || h3 == h1o2 || h3== h2o1 || h3 == h2o2) continue;
          cards[2] = Card.get(h3);
          for (h4 = h3 + 1; h4 < 52; h4++) {
	        if ( h4 == h1o1 || h4 == h1o2 || h4== h2o1 || h4 == h2o2) continue;
            cards[3] = Card.get(h4);
            for (h5 = h4 + 1; h5 < 52; h5++) {
	          if ( h5 == h1o1 || h5 == h1o2 || h5== h2o1 || h5 == h2o2) continue;
              cards[4] = Card.get(h5);
              //for (h6 = h5 + 1; h6 < 52; h6++) {
                //for (h7 = h6 + 1; h7 < 52; h7++) {
                  //cards[5] = Card.get(h1o1);
                  //cards[6] = Card.get(h1o2);
                  cards[5] = h1c1;
                  cards[6] = h1c2;
                  rank1 = StateTableEvaluator.getRank(cards);
	//String handType =n4r(rank1);
	//if ( !handType.equals(priorHandType) ) {
	//System.out.println(""+h1c1+h1c2+cards[0]+cards[1]+cards[2]+cards[3]+cards[4]+"\t"+rank1+"\t"+n4r(rank1));
	//priorHandType = handType;
	//}
                  //cards[5] = Card.get(h2o1);
                  //cards[6] = Card.get(h2o2);
                  cards[5] = h2c1;
                  cards[6] = h2c2;
                  rank2 = StateTableEvaluator.getRank(cards);
	//System.out.println(""+h2c1+h2c2+cards[0]+cards[1]+cards[2]+cards[3]+cards[4]+"\t"+rank2+"\t"+n4r(rank2));
                  //System.out.println(sum);
				  if ( rank1 == rank2 ) {
					//tie++;
					stats1.tie++;
					stats2.tie++;
					stats1.ties[v4r(rank1)]++;
					stats2.ties[v4r(rank2)]++;
				  } else if ( rank1 > rank2 ) {
					//win1++;
					stats1.win++;
					stats2.lose++;
					stats1.winner[v4r(rank1)]++;
					stats2.loser[v4r(rank2)]++;
				  } else {
					//win2++;
					stats2.win++;
					stats1.lose++;
					stats2.winner[v4r(rank2)]++;
					stats1.loser[v4r(rank1)]++;
				  }
                }}}}}//}}
	/*
    //print(sum, time, 133784560, 4);
	//System.out.println(cards1 +"\t"+ cards2+"\t"+ wlt.win1+ "\t"+ wlt.win2 +"\t"+ wlt.tie);
	long total = stats1.win + stats1.tie + stats2.win;
	System.out.print(""+h1c1+h1c2+"\t"+h2c1+h2c2+"\t"+stats1.win+"\t"+stats2.win+"\t"+stats1.tie); 
	  //"\t"+((stats1.win*10000)/total) +
	  //"\t"+((stats2.win*10000)/total) +
	  //"\t"+((stats1.tie*10000)/total) );
	//System.out.print("  P1W:"); 
	//printAggregate(stats1.winner);
	//System.out.print("  P1L:"); 
	//printAggregate(stats1.loser);
	//System.out.print("  P1T:"); 
	//printAggregate(stats1.ties);
	//System.out.print("  P2W:"); 
	//printAggregate(stats2.winner);
	//System.out.print("  P2L:"); 
	//printAggregate(stats2.loser);
	//System.out.print("  P2T:"); 
	//printAggregate(stats2.ties); 

	//System.out.print("\t"); 
	printAggregate(stats1.winner);
	//System.out.print("  P1L:"); 
	//printAggregate(stats1.loser);
	//System.out.print("  P1T:"); 
	//printAggregate(stats1.ties);
	//System.out.print("  P2W:"); 
	printAggregate(stats2.winner);
	//System.out.print("  P2L:"); 
	//printAggregate(stats2.loser);
	//System.out.print("  P2T:"); 
	//printAggregate(stats2.ties); 
	System.out.println(); 
	*/
	System.out.println("["+h1c1+h1c2+"]" + "\t" + "["+h2c1+h2c2+"]"+ "\t" + stats1.win + "\t" + stats1.tie + "\t" + stats2.win); 

	stats1 = new WinningStats();
	stats2 = new WinningStats();
	//win1 = 0;
	//win2 = 0;
	//tie	= 0;
			}

			line = bufRead.readLine();
			count++;
		}
		count--;
		bufRead.close();
		//System.out.println("file line count: "+count);
	}catch (IOException e){
		e.printStackTrace();
	}
  }

  public static void testHandMatchupsGB() {
//String priorHandType ="";
    StateTableEvaluator.initialize();
    Card[] cards = new Card[7];
    //for (int i = 0; i < 7; i++) cards[i] = Card.parse("AsAh");
    //Card[] deck = Card.values();
    long time = System.currentTimeMillis();
    long sum = 0;
    //long win1 = 0;
    //long win2 = 0;
    //long tie = 0;
	WinningStats stats1 = new WinningStats();
	WinningStats stats2 = new WinningStats();
    int rank1;
    int rank2;
    int h1, h2, h3, h4, h5, h6, h7;
	try {
		//FileReader input = new FileReader("neededHands.txt");
		FileReader input = new FileReader("QQneeded.txt");
		BufferedReader bufRead = new BufferedReader(input);
		String line;
		int count = 0;
		line = bufRead.readLine();
		count++;

		String hand1, hand2;
		String sh1c1, sh1c2, sh2c1, sh2c2;
		while (line != null){
			String [] pieces = line.split("\t");
			if (pieces.length == 2) {
				hand1 = pieces[0];
				hand2 = pieces[1];
				sh1c1 = hand1.substring(0,2);
				sh1c2 = hand1.substring(2,4);
				sh2c1 = hand2.substring(0,2);
				sh2c2 = hand2.substring(2,4);

    Card h1c1 = Card.parse(sh1c1);
    Card h1c2 = Card.parse(sh1c2);
    Card h2c1 = Card.parse(sh2c1);
    Card h2c2 = Card.parse(sh2c2);
	int h1o1 = h1c1.ordinal();
	int h1o2 = h1c2.ordinal();
	int h2o1 = h2c1.ordinal();
	int h2o2 = h2c2.ordinal();
    //System.out.println("h101: " + h1o1);
    //System.out.println("h101: " + h1o2);
    //System.out.println("h101: " + h2o1);
    //System.out.println("h101: " + h2o2);
    for (h1 = 0; h1 < 52; h1++) {
	  if ( h1 == h1o1 || h1 == h1o2 || h1== h2o1 || h1 == h2o2) continue;
      cards[0] = Card.get(h1);
      for (h2 = h1 + 1; h2 < 52; h2++) {
	    if ( h2 == h1o1 || h2 == h1o2 || h2== h2o1 || h2 == h2o2) continue;
        cards[1] = Card.get(h2);
        for (h3 = h2 + 1; h3 < 52; h3++) {
	      if ( h3 == h1o1 || h3 == h1o2 || h3== h2o1 || h3 == h2o2) continue;
          cards[2] = Card.get(h3);
          for (h4 = h3 + 1; h4 < 52; h4++) {
	        if ( h4 == h1o1 || h4 == h1o2 || h4== h2o1 || h4 == h2o2) continue;
            cards[3] = Card.get(h4);
            for (h5 = h4 + 1; h5 < 52; h5++) {
	          if ( h5 == h1o1 || h5 == h1o2 || h5== h2o1 || h5 == h2o2) continue;
              cards[4] = Card.get(h5);
              //for (h6 = h5 + 1; h6 < 52; h6++) {
                //for (h7 = h6 + 1; h7 < 52; h7++) {
                  //cards[5] = Card.get(h1o1);
                  //cards[6] = Card.get(h1o2);
                  cards[5] = h1c1;
                  cards[6] = h1c2;
                  rank1 = StateTableEvaluator.getRank(cards);
	//String handType =n4r(rank1);
	//if ( !handType.equals(priorHandType) ) {
	//System.out.println(""+h1c1+h1c2+cards[0]+cards[1]+cards[2]+cards[3]+cards[4]+"\t"+rank1+"\t"+n4r(rank1));
	//priorHandType = handType;
	//}
                  //cards[5] = Card.get(h2o1);
                  //cards[6] = Card.get(h2o2);
                  cards[5] = h2c1;
                  cards[6] = h2c2;
                  rank2 = StateTableEvaluator.getRank(cards);
	//System.out.println(""+h2c1+h2c2+cards[0]+cards[1]+cards[2]+cards[3]+cards[4]+"\t"+rank2+"\t"+n4r(rank2));
                  //System.out.println(sum);
				  if ( rank1 == rank2 ) {
					//tie++;
					stats1.tie++;
					stats2.tie++;
					stats1.ties[v4r(rank1)]++;
					stats2.ties[v4r(rank2)]++;
				  } else if ( rank1 > rank2 ) {
					//win1++;
					stats1.win++;
					stats2.lose++;
					stats1.winner[v4r(rank1)]++;
					stats2.loser[v4r(rank2)]++;
				  } else {
					//win2++;
					stats2.win++;
					stats1.lose++;
					stats2.winner[v4r(rank2)]++;
					stats1.loser[v4r(rank1)]++;
				  }
                }}}}}//}}
	/*
    //print(sum, time, 133784560, 4);
	//System.out.println(cards1 +"\t"+ cards2+"\t"+ wlt.win1+ "\t"+ wlt.win2 +"\t"+ wlt.tie);
	long total = stats1.win + stats1.tie + stats2.win;
	System.out.print(""+h1c1+h1c2+"\t"+h2c1+h2c2+"\t"+stats1.win+"\t"+stats2.win+"\t"+stats1.tie); 
	  //"\t"+((stats1.win*10000)/total) +
	  //"\t"+((stats2.win*10000)/total) +
	  //"\t"+((stats1.tie*10000)/total) );
	//System.out.print("  P1W:"); 
	//printAggregate(stats1.winner);
	//System.out.print("  P1L:"); 
	//printAggregate(stats1.loser);
	//System.out.print("  P1T:"); 
	//printAggregate(stats1.ties);
	//System.out.print("  P2W:"); 
	//printAggregate(stats2.winner);
	//System.out.print("  P2L:"); 
	//printAggregate(stats2.loser);
	//System.out.print("  P2T:"); 
	//printAggregate(stats2.ties); 

	//System.out.print("\t"); 
	printAggregate(stats1.winner);
	//System.out.print("  P1L:"); 
	//printAggregate(stats1.loser);
	//System.out.print("  P1T:"); 
	//printAggregate(stats1.ties);
	//System.out.print("  P2W:"); 
	printAggregate(stats2.winner);
	//System.out.print("  P2L:"); 
	//printAggregate(stats2.loser);
	//System.out.print("  P2T:"); 
	//printAggregate(stats2.ties); 
	System.out.println(); 
	*/
	System.out.println("["+h1c1+h1c2+"]" + "\t" + "["+h2c1+h2c2+"]"+ "\t" + stats1.win + "\t" + stats1.tie + "\t" + stats2.win); 

	stats1 = new WinningStats();
	stats2 = new WinningStats();
	//win1 = 0;
	//win2 = 0;
	//tie	= 0;
			}

			line = bufRead.readLine();
			count++;
		}
		count--;
		bufRead.close();
		//System.out.println("file line count: "+count);
	}catch (IOException e){
		e.printStackTrace();
	}
  }

private static String n4r(int handRank) {
handRank++;
  if      (handRank < 1278) return "High Card";
  else if (handRank < 4138) return "One Pair  ";
  else if (handRank < 4996) return "Two Pair   ";
  else if (handRank < 5854) return "Three of a Kind   ";
  else if (handRank < 5864) return "Straight     ";
  else if (handRank < 7141) return "Flush   ";
  else if (handRank < 7297) return "Full House    ";
  else if (handRank < 7453) return "Four of a Kind    ";
  else                      return "Straight Flush   ";
}

private static int v4r(int handRank) {
handRank++;
  if      (handRank < 1278) return 0;
  else if (handRank < 4138) return 1;
  else if (handRank < 4996) return 2;
  else if (handRank < 5854) return 3;
  else if (handRank < 5864) return 4;
  else if (handRank < 7141) return 5;
  else if (handRank < 7297) return 6;
  else if (handRank < 7453) return 7;
  else                      return 8;
}
static class WinningStats {
	public long win;
	public long lose;
	public long tie;
	public long winner[] = new long[10];
	public long loser[]  = new long[10];
	public long ties[]    = new long[10];

}
private static void printAggregate(long[] results) {
	for (int i = 0; i < 10; i++) {
		System.out.print("\t" + results[i]);
		//if (i > 0 && (i % 5) == 0 )
			//System.out.println();
	}
	//System.out.println();
}

static int[] offsets = new int[] {0, 1277, 4137, 4995, 5853, 5863, 7140, 7296, 7452};

// These have been sorted by pair, suited, unsuited to make processing logic easier.
private static String[] handRankings = {
	"AA", "KK", "QQ", "JJ", "TT", "99", "88", "77", "66", "55", "44", "33",
	"22", "AKs", "AQs", "AJs", "ATs", "KQs", "QJs", "JTs", "T9s", "98s", "87s",
	"76s", "65s", "54s", "A9s", "A8s", "A7s", "A6s", "A5s", "A4s", "A3s", "A2s",
	"KJs", "KTs", "QTs", "J9s", "T8s", "97s", "86s", "75s", "64s", "Q9s", "J8s",
	"T7s", "96s", "85s", "74s", "63s", "K9s", "K8s", "K7s", "Q8s", "53s", "43s",
	"K6s", "K5s", "K4s", "K3s", "K2s", "42s", "32s", "J7s", //
	"Q6s", "92s", "72s", "J6s", "Q7s", "52s", "J5s", "T2s", "J4s", "T3s",
	"T5s", "J3s", "Q2s", "62s", "82s", "T4s", "Q3s", "J2s", "95s", "Q4s",
	"84s", "94s", "73s", "93s", "T6s", "83s", "Q5s",
	"AK", "AQ", "AJ",
	"AT", "KQ", "A9", "A8", "A7", "KJ", "A6", "A5", "A4", "A3", "A2",
	"KT", "QT", "JT", "K9", "QJ", "J9", "T9", "98", "87", "Q9", "76",
	"J8", "65", "54", "T8",//
	"43", "42", "32", "97", "96", "95", "94", "93", "92", "82", "83", "86", "84",
	"85", "J2", "J3", "J4", "J5", "J6", "J7", "Q5", "Q6", "Q7", "Q8", "Q2", "Q3",
	"Q4", "72", "73", "74", "75", "K2", "K7", "K8", "K5", "K6", "K3", "K4", "T6",
	"T7", "T4", "T5", "64", "62", "63", "T3", "T2", "52", "53"};
private static HashMap<String, Integer> handNumber = new HashMap<String, Integer>(handRankings.length);

static { 
  for (int i = 0; i < handRankings.length; i++) {
	handNumber.put(handRankings[i], i);
  }
}
static class RankTotals implements Comparable<RankTotals> {
	long   totalEquity;
	long   wins;
	long   ties;
	long   losses;
	int    thirds;
	long    count;
	String id;
	
	public int compareTo(RankTotals rt) {
		return  (int) (totalEquity - rt.totalEquity);
	}
}
public static final int SUITED = 0, 
						UNSUITED = 1,
						PAIR = 2,
						UNKNOWN = -1;

public static int getCardTypeForSpecificCards(String cards) {
	if (cards.length() != 4)
		return UNKNOWN;
	if (cards.substring(1,2).equals(cards.substring(3,4)))
		return SUITED;
	else if (cards.substring(0,1).equals(cards.substring(2,3)))
		return PAIR;
	else 
		return UNSUITED;
}

private static int getIndex(int r1, int r2) {
  r1--;
  r2--;
  if (r1 < r2)  {
    int temp = r1;
    r1 = r2;
    r2 = temp;
  }
  String c1 = Card.get(r1).toString();
  String c2 = Card.get(r2).toString();
  int type = getCardTypeForSpecificCards(c1+c2);
  String hand = c1.substring(0,1) + c2.substring(0,1);
  if ( type == SUITED )
	hand += "s";
//System.out.println("hand:"+hand+"|");
  int index = handNumber.get(hand);
  return index;
}
	
  public static void test3RandomGB() {
  System.gc(); try { Thread.currentThread().sleep(1000); } catch (Exception e) {};
  long mem1 = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
  StateTableEvaluator.initialize();
  System.gc(); try { Thread.currentThread().sleep(1000); } catch (Exception e) {};
  long mem2 = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
  System.out.println("Memory used: " + ((mem2-mem1)/(1024*1024)) + " Mb");
  long time = System.currentTimeMillis();

  RankTotals[] totals = new RankTotals[handRankings.length];
  for (int i = 0; i < handRankings.length; i++) {
	  totals[i] = new RankTotals();
	  totals[i].id = handRankings[i];
  }

  int h1, h2, h3, h4, h5, h6, h7;
  int u0, u1, u2, u3, u4, u5;
  int[] handRanks = StateTableEvaluator.handRanks;
  int[]          handEnumerations = new int[10];
  int[][] equivalencyEnumerations = new int[10][3000];
  String[] handDescriptions = {"Invalid Hand", "High Card", "One Pair", "Two Pair", "Three of a Kind",
                             "Straight", "Flush", "Full House", "Four of a Kind", "Straight Flush"};
  int numHands = 0;
  int handRank;
  int rr1, rr2, rr3, rr4, type;
  int maxRR, nmax;

  int r1, r2, r3, r4, r5, r6, r7, r8;
  int hand1, hand2, hand3, hand4;
  for (r1 = 1; r1 < 46; r1++) {
	for (r2 = r1 + 1; r2 < 47; r2++) {
	  hand1 = getIndex(r1,r2);
	  for (r3 = r2 + 1; r3 < 48; r3++) {
		for (r4 = r3 + 1; r4 < 49; r4++) {
	      hand2 = getIndex(r3,r4);
		  for (r5 = r4 + 1; r5 < 50; r5++) {
			for (r6 = r5 + 1; r6 < 51; r6++) {
	          hand3 = getIndex(r5,r6);
			  for (r7 = r6 + 1; r7 < 52; r7++) {
			    for (r8 = r7 + 1; r8 < 53; r8++) {
	              hand4 = getIndex(r7,r8);
//System.out.println("h1:"+hand1+" h2:"+hand2+" h3:"+hand3+" h4:"+hand4);

  for (h1 = 1; h1 < 49; h1++) {
	if (h1==r1||h1==r2||h1==r3||h1==r4||h1==r5||h1==r6||h1==r7||h1==r8) continue;
	u0 = handRanks[53 + h1];
	for (h2 = h1 + 1; h2 < 50; h2++) {
	  if (h2==r1||h2==r2||h2==r3||h2==r4||h2==r5||h2==r6||h2==r7||h2==r8) continue;
	  u1 = handRanks[u0 + h2];
	  for (h3 = h2 + 1; h3 < 51; h3++) {
	    if (h3==r1||h3==r2||h3==r3||h3==r4||h3==r5||h3==r6||h3==r7||h3==r8) continue;
		u2 = handRanks[u1 + h3];
		for (h4 = h3 + 1; h4 < 52; h4++) {
	      if (h4==r1||h4==r2||h4==r3||h4==r4||h4==r5||h4==r6||h4==r7||h4==r8) continue;
		  u3 = handRanks[u2 + h4];
		  for (h5 = h4 + 1; h5 < 53; h5++) {
	        if (h5==r1||h5==r2||h5==r3||h5==r4||h5==r5||h5==r6||h5==r7||h5==r8) continue;
			u4 = handRanks[u3 + h5];

			// Handle hand1 (r1,r2)
			u5 = handRanks[u4 + r1];
			rr1 = handRanks[u5 + r2];
			type = (rr1 >>> 12) - 1;
			rr1 = rr1 & 0xFFF;
			rr1 = offsets[type] + rr1 - 1;  
			
			// Handle hand2 (r3,r4)
			u5 = handRanks[u4 + r3];
			rr2 = handRanks[u5 + r4];
			type = (rr2 >>> 12) - 1;
			rr2 = rr2 & 0xFFF;
			rr2 = offsets[type] + rr2 - 1;  
			
			// Handle hand3 (r5,r6)
			u5 = handRanks[u4 + r5];
			rr3 = handRanks[u5 + r6];
			type = (rr3 >>> 12) - 1;
			rr3 = rr3 & 0xFFF;
			rr3 = offsets[type] + rr3 - 1;  
			
			// Handle hand4 (r7,r8)
			u5 = handRanks[u4 + r7];
			rr4 = handRanks[u5 + r8];
			type = (rr4 >>> 12) - 1;
			rr4 = rr4 & 0xFFF;
			rr4 = offsets[type] + rr4 - 1;  

			maxRR = rr1;
			nmax = 1;
			if ( rr2 == maxRR )
			  nmax++;
			else if ( rr2 > maxRR ) {
				maxRR = rr2;
			    nmax = 1;
			} 
			if ( rr3 == maxRR )
			  nmax++;
			else if ( rr3 > maxRR ) {
				maxRR = rr3;
			    nmax = 1;
			} 
			if ( rr4 == maxRR )
			  nmax++;
			else if ( rr4 > maxRR ) {
				maxRR = rr4;
			    nmax = 1;
			} 

			if (rr1 == maxRR) {
			  totals[hand1].totalEquity += (4 / nmax);
			  if ( nmax == 3 )
			    totals[hand1].thirds++;
			}
			if (rr2 == maxRR) {
			  totals[hand2].totalEquity += (4 / nmax);
			  if ( nmax == 3 )
			    totals[hand2].thirds++;
			}
			if (rr3 == maxRR) {
			  totals[hand3].totalEquity += (4 / nmax);
			  if ( nmax == 3 )
			    totals[hand3].thirds++;
			}
			if (rr4 == maxRR) {
			  totals[hand4].totalEquity += (4 / nmax);
			  if ( nmax == 3 )
			    totals[hand4].thirds++;
			}
			totals[hand1].count++;
			totals[hand2].count++;
			totals[hand3].count++;
			totals[hand4].count++;
			

				/*
				handRank = handRanks[u5 + h7];
				handEnumerations[handRank >>> 12]++;
				numHands++;
				equivalencyEnumerations[handRank >>> 12][handRank & 0xFFF]++;
				*/
			  }}}}}
  //print(sum, time/NT, 133784560, 6);
  //printWithFractionalTime(sum, time, 133784560, 6, (long)NT);
  } } } } } } } }
  for (int i = 0; i < handRankings.length; i++) {
	  if ( totals[i].count > 0 ) {
		  totals[i].totalEquity += ((totals[i].thirds+1)/3);
		  totals[i].totalEquity = (totals[i].totalEquity/totals[i].count);
	  } else 
		  totals[i].totalEquity = 0;
  }
  Arrays.sort(totals);
  for (int i = 0; i < handRankings.length; i++) {
	  System.out.println( totals[i].id + "\t" + totals[i].totalEquity +"\t" + totals[i].count);
  }
  }

  public static void test3Random2GB() {
  System.gc(); try { Thread.currentThread().sleep(1000); } catch (Exception e) {};
  long mem1 = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
  StateTableEvaluator.initialize();
  System.gc(); try { Thread.currentThread().sleep(1000); } catch (Exception e) {};
  long mem2 = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
  System.out.println("Memory used: " + ((mem2-mem1)/(1024*1024)) + " Mb");
  long time = System.currentTimeMillis();

  RankTotals[] totals = new RankTotals[handRankings.length];
  for (int i = 0; i < handRankings.length; i++) {
	  totals[i] = new RankTotals();
	  totals[i].id = handRankings[i];
  }

  int h1, h2, h3, h4, h5, h6, h7;
  int u0, u1, u2, u3, u4, u5;
  int[] handRanks = StateTableEvaluator.handRanks;
  int[]          handEnumerations = new int[10];
  int[][] equivalencyEnumerations = new int[10][3000];
  String[] handDescriptions = {"Invalid Hand", "High Card", "One Pair", "Two Pair", "Three of a Kind",
                             "Straight", "Flush", "Full House", "Four of a Kind", "Straight Flush"};
  int numHands = 0;
  int handRank;
  int rr1, rr2, rr3, rr4, type;
  int maxRR, nmax;

  Random generator = new Random();

  int r1, r2, r3, r4, r5, r6, r7, r8;
  int hand1, hand2, hand3, hand4;
//System.out.println("h1:"+hand1+" h2:"+hand2+" h3:"+hand3+" h4:"+hand4);

  for (h1 = 1; h1 < 49; h1++) {
	//if (h1==r1||h1==r2||h1==r3||h1==r4||h1==r5||h1==r6||h1==r7||h1==r8) continue;
	u0 = handRanks[53 + h1];
	for (h2 = h1 + 1; h2 < 50; h2++) {
	  //if (h2==r1||h2==r2||h2==r3||h2==r4||h2==r5||h2==r6||h2==r7||h2==r8) continue;
	  u1 = handRanks[u0 + h2];
	  for (h3 = h2 + 1; h3 < 51; h3++) {
	    //if (h3==r1||h3==r2||h3==r3||h3==r4||h3==r5||h3==r6||h3==r7||h3==r8) continue;
		u2 = handRanks[u1 + h3];
		for (h4 = h3 + 1; h4 < 52; h4++) {
	      //if (h4==r1||h4==r2||h4==r3||h4==r4||h4==r5||h4==r6||h4==r7||h4==r8) continue;
		  u3 = handRanks[u2 + h4];
		  for (h5 = h4 + 1; h5 < 53; h5++) {
	        //if (h5==r1||h5==r2||h5==r3||h5==r4||h5==r5||h5==r6||h5==r7||h5==r8) continue;
			u4 = handRanks[u3 + h5];
//System.out.println("h1:"+h1+" h2:"+h2+" h3:"+h3+" h4:"+h4+" h5:"+h5);

			for ( int ri = 0; ri <200; ri++ ) { 
			  r1 = r2 = r3 = r4 = r5 = r6 = r7 = r8 = h1;

			  while ( r1==h1||r1==h2||r1==h3||r1==h4||r1==h5 )
			    r1 = generator.nextInt(52) + 1;
			  while ( r2==h1||r2==h2||r2==h3||r2==h4||r2==h5||r2==r1 )
			    r2 = generator.nextInt(52) + 1;
			  while ( r3==h1||r3==h2||r3==h3||r3==h4||r3==h5||r3==r1||r3==r2 )
			    r3 = generator.nextInt(52) + 1;
			  while ( r4==h1||r4==h2||r4==h3||r4==h4||r4==h5||r4==r1||r4==r2||r4==r3 )
			    r4 = generator.nextInt(52) + 1;
			  while ( r5==h1||r5==h2||r5==h3||r5==h4||r5==h5||r5==r1||r5==r2||r5==r3||r5==r4 )
			    r5 = generator.nextInt(52) + 1;
			  while ( r6==h1||r6==h2||r6==h3||r6==h4||r6==h5||r6==r1||r6==r2||r6==r3||r6==r4||r6==r5 )
			    r6 = generator.nextInt(52) + 1;
			  while ( r7==h1||r7==h2||r7==h3||r7==h4||r7==h5||r7==r1||r7==r2||r7==r3||r7==r4||r7==r5||r7==r6 )
			    r7 = generator.nextInt(52) + 1;
			  while ( r8==h1||r8==h2||r8==h3||r8==h4||r8==h5||r8==r1||r8==r2||r8==r3||r8==r4||r8==r5||r8==r6||r8==r7 )
			    r8 = generator.nextInt(52) + 1;
			  hand1 = getIndex(r1,r2);
	      	  hand2 = getIndex(r3,r4);
	          hand3 = getIndex(r5,r6);
	          hand4 = getIndex(r7,r8);

			// Handle hand1 (r1,r2)
			u5 = handRanks[u4 + r1];
			rr1 = handRanks[u5 + r2];
			type = (rr1 >>> 12) - 1;
			rr1 = rr1 & 0xFFF;
			rr1 = offsets[type] + rr1 - 1;  
			
			// Handle hand2 (r3,r4)
			u5 = handRanks[u4 + r3];
			rr2 = handRanks[u5 + r4];
			type = (rr2 >>> 12) - 1;
			rr2 = rr2 & 0xFFF;
			rr2 = offsets[type] + rr2 - 1;  
			
			// Handle hand3 (r5,r6)
			u5 = handRanks[u4 + r5];
			rr3 = handRanks[u5 + r6];
			type = (rr3 >>> 12) - 1;
			rr3 = rr3 & 0xFFF;
			rr3 = offsets[type] + rr3 - 1;  
			
			// Handle hand4 (r7,r8)
			u5 = handRanks[u4 + r7];
			rr4 = handRanks[u5 + r8];
			type = (rr4 >>> 12) - 1;
			rr4 = rr4 & 0xFFF;
			rr4 = offsets[type] + rr4 - 1;  

			maxRR = rr1;
			nmax = 1;
			if ( rr2 == maxRR )
			  nmax++;
			else if ( rr2 > maxRR ) {
				maxRR = rr2;
			    nmax = 1;
			} 
			if ( rr3 == maxRR )
			  nmax++;
			else if ( rr3 > maxRR ) {
				maxRR = rr3;
			    nmax = 1;
			} 
			if ( rr4 == maxRR )
			  nmax++;
			else if ( rr4 > maxRR ) {
				maxRR = rr4;
			    nmax = 1;
			} 

			if (rr1 == maxRR) {
			  totals[hand1].totalEquity += (4 / nmax);
			  if ( nmax == 3 )
			    totals[hand1].thirds++;
			}
			if (rr2 == maxRR) {
			  totals[hand2].totalEquity += (4 / nmax);
			  if ( nmax == 3 )
			    totals[hand2].thirds++;
			}
			if (rr3 == maxRR) {
			  totals[hand3].totalEquity += (4 / nmax);
			  if ( nmax == 3 )
			    totals[hand3].thirds++;
			}
			if (rr4 == maxRR) {
			  totals[hand4].totalEquity += (4 / nmax);
			  if ( nmax == 3 )
			    totals[hand4].thirds++;
			}
			totals[hand1].count++;
			totals[hand2].count++;
			totals[hand3].count++;
			totals[hand4].count++;
			}
			

				/*
				handRank = handRanks[u5 + h7];
				handEnumerations[handRank >>> 12]++;
				numHands++;
				equivalencyEnumerations[handRank >>> 12][handRank & 0xFFF]++;
				*/
			  }}}}}
  for (int i = 0; i < handRankings.length; i++) {
	  if ( totals[i].count > 0 ) {
		  totals[i].totalEquity += ((totals[i].thirds+1)/3);
		  totals[i].totalEquity = (totals[i].totalEquity*10000/totals[i].count);
	  } else 
		  totals[i].totalEquity = 0;
  }
  Arrays.sort(totals);
  for (int i = 0; i < handRankings.length; i++) {
	  System.out.println( totals[i].id + "\t" + totals[i].totalEquity +"\t" + totals[i].count);
  }
  }


	//static {
	//	System.out.println("TT:"+getPocketPair(10-2));
	//	System.out.println("AKs:"+getSuitedConnectors(14-2, 13-2));
	//	System.out.println("AQo:"+getUnsuitedConnectors(14-2, 12-2));
	//}

    private static class GBHand {
	    int card1;
	    int card2;

		public String toString() {
            String c1 = Card.get(card1).toString();
            String c2 = Card.get(card2).toString();
			return c1 + c2;
		}
    }

    private static ArrayList<GBHand> getPocketPair(int rank) {
		ArrayList<GBHand> hands = new ArrayList<GBHand>();	

		int base = rank * 4;
		for (int i = 0; i < 4; i++) {
			for ( int j = i+1; j < 4; j++) {
			    GBHand hh = new GBHand();
				hh.card1 = base + i;
				hh.card2 = base + j;
				hands.add(hh);
			}
		}
		
		return hands;
    } 

    private static ArrayList<GBHand> getSuitedConnectors(int topRank, int bottomRank) {
		ArrayList<GBHand> hands = new ArrayList<GBHand>();	

		int topBase = topRank * 4;
		int botBase = bottomRank * 4;
		for (int i = 0; i < 4; i++) {
			GBHand hh = new GBHand();
			hh.card1 = topBase + i;
			hh.card2 = botBase + i;
			hands.add(hh);
		}
		
		return hands;
    } 

    private static ArrayList<GBHand> getUnsuitedConnectors(int topRank, int bottomRank) {
		ArrayList<GBHand> hands = new ArrayList<GBHand>();	

		int topBase = topRank * 4;
		int botBase = bottomRank * 4;
		for (int i = 0; i < 4; i++) {
			for ( int j = 0; j < 4; j++) {
				if ( i == j ) continue;
			    GBHand hh = new GBHand();
			    hh.card1 = topBase + i;
			    hh.card2 = botBase + j;
			    hands.add(hh);
			}
		}
		
		return hands;
    } 

    private static void fillInTakenCardList(ArrayList<GBHand> hands, boolean list[]) {
		for ( GBHand hand : hands) {
			list[hand.card1] = true;
			list[hand.card2] = true;
		}
	}
	
  private static ArrayList<GBHand> buildRandomHand() {
	ArrayList<GBHand> hands = new ArrayList<GBHand>(1326);
	
		
	int count=0;
	for (int i = 0; i < 52; i++) {
	  for (int j = i+1; j < 52; j++) {
	    GBHand hand = new GBHand();	
		hand.card1 = i;
	    hand.card2 = j;
		hands.add(hand);
		count++;
	  }
	}
	//System.out.println("allhands="+count);
	return hands;
  }

  public static void testHandRangesGB() {
      ArrayList<GBHand> hands1 = new ArrayList<GBHand>(1);
	  GBHand hand1 = new GBHand();
	  //hand1.card1 = Card.parse("Kh").ordinal();
	  //hand1.card2 = Card.parse("Jh").ordinal();
	  //hand1.card1 = Card.parse("Ac").ordinal();
	  //hand1.card2 = Card.parse("4c").ordinal();
	  //hand1.card1 = Card.parse("Ah").ordinal();
	  //hand1.card2 = Card.parse("Kh").ordinal();
	  hand1.card1 = Card.parse("Qd").ordinal();
	  hand1.card2 = Card.parse("Qh").ordinal();
	  hands1.add(hand1);
      //ArrayList<GBHand> h1r = getSuitedConnectors(14-2, 13-2);
	  //hands1.addAll(h1r);

      ArrayList<GBHand> hands2 = new ArrayList<GBHand>();
	  //GBHand hand2 = new GBHand();
      //ArrayList<GBHand> range  = getPocketPair(10-2);
	  //hands2.addAll(range);
      //range = getPocketPair(11-2);
	  //hands2.addAll(range);
      //range = getUnsuitedConnectors(14-2, 12-2);
	  //hands2.addAll(range);
      //ArrayList<GBHand> range  = getPocketPair(13-2);
	  //hands2.addAll(range);
	  //hands2 = buildRandomHand();
	  GBHand hand2 = new GBHand();
	  hand2.card1 = Card.parse("Th").ordinal();
	  hand2.card2 = Card.parse("5d").ordinal();
	  hands2.add(hand2);
      //QhQdTh5d        1505541 7983    198780  1493077 6717    212510

	  ArrayList<Integer> board = new ArrayList<Integer>(0);
      //board.add(Card.parse("4s").ordinal());
      //board.add(Card.parse("3c").ordinal());
      //board.add(Card.parse("2c").ordinal());

	  System.gc(); try { Thread.currentThread().sleep(1000); } catch (Exception e) {};
	  long mem1 = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
	  StateTableEvaluator.initialize();
	  System.gc(); try { Thread.currentThread().sleep(1000); } catch (Exception e) {};
	  long mem2 = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
	  //System.out.println("Memory used: " + ((mem2-mem1)/(1024*1024)) + " Mb");
	  long time = System.currentTimeMillis();

      testHandRangesSpecific(hands1, hands2, board, "QQ", "Th5d");

	/*
	  for (int i = 0; i < 52; i++) {
		for (int j = i+1; j < 52; j++) {
	      ArrayList<GBHand> hands2 = new ArrayList<GBHand>(1);
		  GBHand hand = new GBHand();	
		  hand.card1 = i;
		  hand.card2 = j;
		  hands2.add(hand);
          testHandRangesSpecific(hands1, hands2, board, hands1.toString(), hands2.toString());
		}
	  }
	  */
  }

  public static void testHandRangesGB2() {
      ArrayList<GBHand> hands1 = new ArrayList<GBHand>(1);
	  GBHand hand1 = new GBHand();
	  //hand1.card1 = Card.parse("Kh").ordinal();
	  //hand1.card2 = Card.parse("Jh").ordinal();
	  //hand1.card1 = Card.parse("Ac").ordinal();
	  //hand1.card2 = Card.parse("4c").ordinal();
	  //hand1.card1 = Card.parse("Ah").ordinal();
	  //hand1.card2 = Card.parse("Kh").ordinal();
	  hand1.card1 = Card.parse("Qd").ordinal();
	  hand1.card2 = Card.parse("Qh").ordinal();
	  hands1.add(hand1);
      //ArrayList<GBHand> h1r = getSuitedConnectors(14-2, 13-2);
	  //hands1.addAll(h1r);

      ArrayList<GBHand> hands2 = new ArrayList<GBHand>();
	  //GBHand hand2 = new GBHand();
      //ArrayList<GBHand> range  = getPocketPair(10-2);
	  //hands2.addAll(range);
      //range = getPocketPair(11-2);
	  //hands2.addAll(range);
      //range = getUnsuitedConnectors(14-2, 12-2);
	  //hands2.addAll(range);
      //ArrayList<GBHand> range  = getPocketPair(13-2);
	  //hands2.addAll(range);
	  hands2 = buildRandomHand();

	  ArrayList<Integer> board = new ArrayList<Integer>(0);
      //board.add(Card.parse("4s").ordinal());
      //board.add(Card.parse("3c").ordinal());
      //board.add(Card.parse("2c").ordinal());

	  System.gc(); try { Thread.currentThread().sleep(1000); } catch (Exception e) {};
	  long mem1 = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
	  StateTableEvaluator.initialize();
	  System.gc(); try { Thread.currentThread().sleep(1000); } catch (Exception e) {};
	  long mem2 = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
	  //System.out.println("Memory used: " + ((mem2-mem1)/(1024*1024)) + " Mb");
	  long time = System.currentTimeMillis();

      testHandRangesSpecific(hands1, hands2, board, "QQ", "random");

	/*
	  for (int i = 0; i < 52; i++) {
		for (int j = i+1; j < 52; j++) {
	      ArrayList<GBHand> hands2 = new ArrayList<GBHand>(1);
		  GBHand hand = new GBHand();	
		  hand.card1 = i;
		  hand.card2 = j;
		  hands2.add(hand);
          testHandRangesSpecific(hands1, hands2, board, hands1.toString(), hands2.toString());
		}
	  }
	  */
  }

  public static void testHandRangesSpecific(ArrayList<GBHand> hands1, ArrayList<GBHand> hands2, ArrayList<Integer> board, String id1, String id2) {

      boolean usedCards[] = new boolean[52];
	  if ( hands1.size() == 1 ) {
	      fillInTakenCardList(hands1, usedCards);
	  } 
	  if ( hands2.size() == 1 ) {
	      fillInTakenCardList(hands2, usedCards);
	  } 
	  
	  GBHand hand1, hand2;
	  int h1, h2, h3, h4, h5, h6, h7;
      //Card card;
      //for (h1 = 0; h1 < 52; h1++) {
          //card = Card.get(h1);
		  //System.out.println(""+h1+"\t"+card);
	  //}

	  //if (true) return;


	  RankTotals[] totals = new RankTotals[2];
	  for (int i = 0; i < 2; i++) {
		  totals[i] = new RankTotals();
		  if (i == 0)
		    totals[i].id = id1;
		  if (i == 1)
		    totals[i].id = id2;
	  }

	  int u0, u1, u2, u3, u4, u5;
      int rr1, rr2, type;
      int maxRR, nmax;
	  int[] handRanks = StateTableEvaluator.handRanks;
	  /*
	  int[]          handEnumerations = new int[10];
	  int[][] equivalencyEnumerations = new int[10][3000];
	  String[] handDescriptions = {"Invalid Hand", "High Card", "One Pair", "Two Pair", "Three of a Kind",
								 "Straight", "Flush", "Full House", "Four of a Kind", "Straight Flush"};
	  int numHands = 0;
	  int handRank;
	  */

	//System.out.println("h1:"+hand1+" h2:"+hand2+" h3:"+hand3+" h4:"+hand4);

	  // Define accelerators for prespecified board cards
	  boolean h1done = false;
	  boolean h2done = false;
	  boolean h3done = false;
	  boolean h4done = false;
	  boolean h5done = false;
int cc=0;

	  for (h1 = 1; h1 < 49 && !h1done; h1++) {
		if ( board.size() >= 1 ) {
		  h1 = board.get(0)+1;		
		  h1done = true;
		}
		if (usedCards[h1-1]) continue;
		usedCards[h1-1] = true;
		u0 = handRanks[53 + h1];
//System.out.println("h1:"+h1+" h1done="+h1done);
		for (h2 = !h1done ? h1 + 1 : 1; h2 < 50 && !h2done; h2++) {
		  if ( board.size() >= 2 ) {
			h2 = board.get(1)+1;		
		    h2done = true;
		  }
		  if (usedCards[h2-1]) continue;
		  usedCards[h2-1] = true;
		  u1 = handRanks[u0 + h2];
//System.out.println("h1:"+h1+" h2:"+h2+" h2done="+h2done);
		  for (h3 = !h2done ? h2 + 1 : 1; h3 < 51 && !h3done; h3++) {
			if ( board.size() >= 3 ) {
			  h3 = board.get(2)+1;		
		      h3done = true;
			}
		    if (usedCards[h3-1]) continue;
		    usedCards[h3-1] = true;
			u2 = handRanks[u1 + h3];
//System.out.println("h1:"+h1+" h2:"+h2+" h3:"+h3+" h3done="+h3done);
			for (h4 = !h3done ? h3 + 1 : 1; h4 < 52||h4done; h4++) {
			  if ( board.size() >= 4 ) {
			    h4 = board.get(3)+1;		
		        h4done = true;
			  }
		      if (usedCards[h4-1]) continue;
		      usedCards[h4-1] = true;
			  u3 = handRanks[u2 + h4];
			  for (h5 = !h4done ? h4 + 1 : 1; h5 < 53 || h5done; h5++) {
			    if ( board.size() >= 5 ) {
			      h5 = board.get(4)+1;		
		          h5done = true;
			    }
		        if (usedCards[h5-1]) continue;
		        usedCards[h5-1] = true;
				u4 = handRanks[u3 + h5];
	//if (cc++ %1000 == 0)
	//System.out.println("h1:"+h1+" h2:"+h2+" h3:"+h3+" h4:"+h4+" h5:"+h5);

				for ( int uh1 = 0; uh1 < hands1.size(); uh1++ ) { 
				  hand1 = hands1.get(uh1);
	              if ( hands1.size() > 1 ) {
				    if ( usedCards[hand1.card1] || usedCards[hand1.card2] ) continue;
				    usedCards[hand1.card1] = true;
					usedCards[hand1.card2] = true;
				  }
				  for ( int uh2 = 0; uh2 < hands2.size(); uh2++ ) { 
				    hand2 = hands2.get(uh2);
	                if ( hands2.size() > 1 ) {
				      //if ( usedCards[hand2.card1] || usedCards[hand2.card2] ) {
					//	String skipped = 
                     //     Card.get(hand2.card1).toString() + 
                      //    Card.get(hand2.card2).toString();
					//	System.out.println("Skipping: "+skipped);
					 // }
				      if ( usedCards[hand2.card1] || usedCards[hand2.card2] ) continue;
				      usedCards[hand2.card1] = true;
					  usedCards[hand2.card2] = true;
				    }

					// Handle hand1 
					u5 = handRanks[u4 + hand1.card1+1];
					rr1 = handRanks[u5 + hand1.card2+1];
					type = (rr1 >>> 12) - 1;
					rr1 = rr1 & 0xFFF;
					rr1 = offsets[type] + rr1 - 1;  
					
					// Handle hand2 
					u5 = handRanks[u4 + hand2.card1+1];
					rr2 = handRanks[u5 + hand2.card2+1];
					type = (rr2 >>> 12) - 1;
					rr2 = rr2 & 0xFFF;
					rr2 = offsets[type] + rr2 - 1;  

					maxRR = rr1;
					nmax = 1;
					if ( rr2 == maxRR )
					  nmax++;
					else if ( rr2 > maxRR ) {
						maxRR = rr2;
						nmax = 1;
					} 

					if (rr1 == maxRR) {
					  totals[0].totalEquity += (2 / nmax);
					  if ( rr1 != rr2)  {
					    totals[0].wins++;
					    totals[1].losses++;
					  } else {
					    totals[0].ties++;
					  }

					}
					if (rr2 == maxRR) {
					  totals[1].totalEquity += (2 / nmax);
					  if ( rr1 != rr2)  {
					    totals[1].wins++;
					    totals[0].losses++;
					  } else {
					    totals[1].ties++;
					  }
					}
					totals[0].count++;
					totals[1].count++;
				    
	                if ( hands2.size() > 1 ) {
				      usedCards[hand2.card1] = false;
					  usedCards[hand2.card2] = false;
				    }
			      }
	              if ( hands1.size() > 1 ) {
				    usedCards[hand1.card1] = false;
				    usedCards[hand1.card2] = false;
				  }
				}
		        usedCards[h5-1] = false;
			  }  
		      usedCards[h4-1] = false;
			}  
		    usedCards[h3-1] = false;
		  }  
		  usedCards[h2-1] = false;
		}  
		usedCards[h1-1] = false;
	  }  

	  long time2 = System.currentTimeMillis();
	  //System.out.println("T delta : "+(time2 - time));
				
					/*
					handRank = handRanks[u5 + h7];
					handEnumerations[handRank >>> 12]++;
					numHands++;
					equivalencyEnumerations[handRank >>> 12][handRank & 0xFFF]++;
					*/
	  /*
	  long eqTot = 0;
	  for (int i = 0; i < 2; i++) {
		  if ( totals[i].count > 0 ) {
			  totals[i].totalEquity = (totals[i].totalEquity*10000/totals[i].count);
			  eqTot += totals[i].totalEquity;
		  } else 
			  totals[i].totalEquity = 0;
	  }
	  //Arrays.sort(totals);
	  for (int i = 0; i < 2; i++) {
		  long tot = totals[i].wins + totals[i].ties + totals[i].losses;
		  System.out.println( totals[i].id + "\t" + totals[i].totalEquity +"\t" + totals[i].count + 
            "\tW%"+(totals[i].wins*10000/tot) + "\tT%"+(totals[i].ties*10000/tot) + "\tEq%"+(totals[i].totalEquity*10000/eqTot));
	  }
	  */
	  //System.out.println("Hands1:"+hands1);
	  //System.out.println("Hands2:"+hands2);
	  long time3 = System.currentTimeMillis();
	  
	  
	  //System.out.println("T delta II :"+(time3 - time));
	  
	  System.out.println(totals[0].id + "\t" + totals[1].id + "\t" + totals[0].wins + "\t" + totals[0].ties + "\t" + totals[1].wins); 
  }
  
  public static  ArrayList<CardFightResult> calculateSpecific(ArrayList<GBHand> hands, ArrayList<Integer> board) {
      boolean usedCards[] = new boolean[52];
	  fillInTakenCardList(hands, usedCards);
	  
	  GBHand hand;
	  int h1, h2, h3, h4, h5, h6, h7;
	  RankTotals[] totals = new RankTotals[hands.size()];
	  for (int i = 0; i < hands.size(); i++) {
		  totals[i] = new RankTotals();
	  }
	  long totalInc = 4 * 3 * totals.length;
	  long unitInc;

	  int u0, u1, u2, u3, u4, u5;
      int type;
      int maxRR = 0, nmax = 0, urr;
      int rr[] = new int[hands.size()];  // Resulting ranks for all hands

	  int[] handRanks = StateTableEvaluator.handRanks;
	  
	  // Define accelerators for prespecified board cards
	  boolean h1done = false;
	  boolean h2done = false;
	  boolean h3done = false;
	  boolean h4done = false;
	  boolean h5done = false;
int cc=0;

	  for (h1 = 1; h1 < 49 && !h1done; h1++) {
		if ( board.size() >= 1 ) {
		  h1 = board.get(0)+1;		
		  h1done = true;
		}
		if (usedCards[h1-1]) continue;
		usedCards[h1-1] = true;
		u0 = handRanks[53 + h1];
//System.out.println("h1:"+h1+" h1done="+h1done);
		for (h2 = !h1done ? h1 + 1 : 1; h2 < 50 && !h2done; h2++) {
		  if ( board.size() >= 2 ) {
			h2 = board.get(1)+1;		
		    h2done = true;
		  }
		  if (usedCards[h2-1]) continue;
		  usedCards[h2-1] = true;
		  u1 = handRanks[u0 + h2];
//System.out.println("h1:"+h1+" h2:"+h2+" h2done="+h2done);
		  for (h3 = !h2done ? h2 + 1 : 1; h3 < 51 && !h3done; h3++) {
			if ( board.size() >= 3 ) {
			  h3 = board.get(2)+1;		
		      h3done = true;
			}
		    if (usedCards[h3-1]) continue;
		    usedCards[h3-1] = true;
			u2 = handRanks[u1 + h3];
//System.out.println("h1:"+h1+" h2:"+h2+" h3:"+h3+" h3done="+h3done);
			for (h4 = !h3done ? h3 + 1 : 1; h4 < 52||h4done; h4++) {
			  if ( board.size() >= 4 ) {
			    h4 = board.get(3)+1;		
		        h4done = true;
			  }
		      if (usedCards[h4-1]) continue;
		      usedCards[h4-1] = true;
			  u3 = handRanks[u2 + h4];
			  for (h5 = !h4done ? h4 + 1 : 1; h5 < 53 || h5done; h5++) {
			    if ( board.size() >= 5 ) {
			      h5 = board.get(4)+1;		
		          h5done = true;
			    }
		        if (usedCards[h5-1]) continue;
		        usedCards[h5-1] = true;
				u4 = handRanks[u3 + h5];
	//if (cc++ %1000 == 0)
	//System.out.println("h1:"+h1+" h2:"+h2+" h3:"+h3+" h4:"+h4+" h5:"+h5);

				for ( int uh = 0; uh < hands.size(); uh++ ) { 
					hand = hands.get(uh);

					// Handle hand 
					u5 = handRanks[u4 + hand.card1+1];
					urr = handRanks[u5 + hand.card2+1];
					type = (urr >>> 12) - 1;
					urr = urr & 0xFFF;
					urr = offsets[type] + urr - 1;
					rr[uh] = urr;
					
					if ( uh == 0 ) {
						maxRR = urr;
						nmax = 1;
					} else {
						if ( urr == maxRR )
							nmax++;
						else if ( urr > maxRR ) {
							maxRR = urr;
							nmax = 1;
						} 
					}
				}
				unitInc = (totalInc / nmax);
				for (int i = 0; i < totals.length; i++ ) {
					urr = rr[i];
					if (urr == maxRR) {
					  totals[i].totalEquity += unitInc;
					  if ( nmax == 1 )  {
					    totals[i].wins++;
					  } else {
					    totals[i].ties += unitInc;
					  }
					} else {
					    totals[i].losses++;
					}
					totals[i].count++;
				}
		        usedCards[h5-1] = false;
			  }  
		      usedCards[h4-1] = false;
			}  
		    usedCards[h3-1] = false;
		  }  
		  usedCards[h2-1] = false;
		}  
		usedCards[h1-1] = false;
	  }    
	  long eqTot = 0;
	  for (int i = 0; i < totals.length; i++) {
		  if ( totals[i].count > 0 ) {
			  //totals[i].totalEquity = (totals[i].totalEquity*10000/totals[i].count);
			  totals[i].totalEquity /= totalInc;
			  totals[i].ties /= totalInc;
			  eqTot += totals[i].totalEquity;
		  } else 
			  totals[i].totalEquity = 0;
	  }
	  ArrayList<CardFightResult> results = new ArrayList<CardFightResult>(totals.length);
	  for (int i = 0; i < totals.length; i++) {
		  long tot = totals[i].count;
		  totals[i].totalEquity = totals[i].totalEquity*10000/eqTot;
		  System.out.println( totals[i].id + "\t" + totals[i].totalEquity +"\t" + totals[i].count + 
            "\tW%"+(totals[i].wins*10000/tot) + "\tT%"+(totals[i].ties*10000/tot) + "\tEq%"+(totals[i].totalEquity*10000/eqTot));
		  CardFightResult result = new CardFightResult();
		  result.equity = String.valueOf(totals[i].totalEquity*10000/eqTot);
		  result.winPct = String.valueOf(totals[i].wins*10000/tot);
		  result.tiePct = String.valueOf(totals[i].ties*10000/tot);
		  results.add(result); 
	  }
	  System.out.println(totals[0].id + "\t" + totals[1].id + "\t" + totals[0].wins + "\t" + totals[0].ties + "\t" + totals[1].wins); 
      return results;
  }
  
	public static ArrayList<CardFightResult> calculateFromCardFight(ArrayList<String> playersHands, String board, String discard) {
		ArrayList<GBHand> hands = new ArrayList<GBHand>(playersHands.size());
		for (String shand : playersHands) {
			GBHand hand = new GBHand();
			hand.card1 = Card.parse(shand.substring(0,2)).ordinal();
			hand.card2 = Card.parse(shand.substring(2,4)).ordinal();
			hands.add(hand);
		}

		ArrayList<Integer> board2 = new ArrayList<Integer>(0);
		while ( board.length() >= 2 ) {
			int card = Card.parse(board.substring(0,2)).ordinal();
		    board2.add(card);
		    board = board.substring(2);
		}

		System.gc(); try { Thread.currentThread().sleep(1000); } catch (Exception e) {};
		long mem1 = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
		StateTableEvaluator.initialize();
		System.gc(); try { Thread.currentThread().sleep(1000); } catch (Exception e) {};
		long mem2 = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
		//System.out.println("Memory used: " + ((mem2-mem1)/(1024*1024)) + " Mb");
		long time = System.currentTimeMillis();
		
		return calculateSpecific(hands, board2);
	}
}
	
	
