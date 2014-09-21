package poker;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

public class HandRange {
	
	private static String[] handRankingsLIMIT = {
		"AA", "KK", "QQ", "JJ", "AKs",
		"TT", "AQs", "AJs", "KQs", "AK",
		"99", "JTs", "QJs", "KJs", "ATs", "AQ",
		"T9s", "KQ", "88", "QTs", "98s", "J9s", "AJ", "KTs",
		"77", "87s", "Q9s", "T8s", "KJ", "QJ", "JT", "76s", "97s", "A9s", "A8s","A7s", "A6s", "A5s", "A4s", "A3s", "A2s", "65s",
		"66", "AT", "55", "86s", "KT", "QT", "54s", "K9s", "J8s", "75s",
		"44", "J9", "64s", "T9", "53s", "33", "98", "43s", "22", "K8s", "K7s", "K6s", "K5s", "K4s", "K3s", "K2s", "T7s", "Q8s", 
		"87", "A9", "Q9", "76", "42s", "32s", "96s", "85s", "J8", "J7s", "65", "54", "74s", "K9", "T8"};
	
	private static String[] handRankingsNoLimit = {
		"AA", "KK", 
		"QQ", "AKs","AK",
		"JJ", "TT", "99",
		"AQs", "88", "77", "66", 
		"AQ", "AJs", "ATs",
		"KQs", "QJs", "JTs", "T9s", "98s", "87s", "76s", "65s", "54s",
		"AJ", "AT", "KQ", 
		"55", "44", "33", "22", "A9s", "A8s", "A7s", "A6s", "A5s", "A4s", "A3s", "A2s", "A9", "A8", "A7", "KJs",  "KJ", "KTs", 
		"QTs", "J9s", "T8s", "97s", "86s", "75s", "64s", "A6", "A5", "A4", "A3", "A2", 
		"Q9s", "J8s", "T7s", "96s", "85s", "74s", "63s", "K9s", "K8s", "K7s", "Q8s", "KT", "QT", "JT", "K9", "QJ",
		"J9", "T9", "53s", "98", "43s", "K6s", "K5s", "K4s", "K3s", "K2s", "87", "Q9", "76", "42s", "32s", "J8", "J7s", "65", "54", "T8",
		};
	
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
	
	private static HashMap<String, Integer> handIndex;
	
	public static void main(String args[]) {
		//ArrayList<CardCollection> list = getPocketPair(Card.KING);
		//ArrayList<CardCollection> list = getSuitedConnectors(Card.ACE, Card.KING);
		//ArrayList<CardCollection> list = getUnsuitedConnectors(Card.ACE, Card.KING);
		//ArrayList<CardCollection> list = getCardRankSeq();

		
		//for (CardCollection cards : list) {
		//	System.out.println(cards);
		//}
		
		//runComps();
		//runPrelim();
		//runOrganizer();
		//runHandFrequencyCount();
		//runFullCount();
		//readFullOdds();
		//runFullOddsTest();
		//runRoughRankingExperiment();
		//runWhatsMissing();
		//runMapCardsTest();
		//runBetterRankingExperiment();
		//runRankingCalcs(true);
		//runHandDump();
		//runMatchups();
		runHandRangeTest();

		//runSpecificHandDump();
		//runHandRankingDump();
		//test3Hands();
	}
	
	public static void runPrelim() {
		System.out.println("LimitList:"+ handRankingsLIMIT.length);
		System.out.println("NoLimitList:"+ handRankings.length);
		HashSet hash = new HashSet();
		for (int i = 0; i < handRankings.length; i++) {
			hash.add(handRankings[i]);
		}
		for (int i = 0; i < handRankingsLIMIT.length; i++) {
			if ( !hash.contains(handRankingsLIMIT[i]) ) {
				System.out.print("\""+handRankingsLIMIT[i]+"\", ");
			}
		}
		System.out.println();
	}
	
	public static void runFullCount() {
		System.out.println("Hand Rankings list size: "+ handRankings.length);
		
		CardCollection cards;
		int countAll = 0;
		for (int i = 0; i < handRankings.length; i++) {
			ArrayList<CardCollection> list = enumerateCards(handRankings[i]);
			for (int j = 0; j < list.size(); j++) {
				cards = list.get(j);
				//System.out.println(cards);
				countAll++;
			}
		}
		System.out.println("Total enumerated cards: "+countAll);
	}
	
	private static class Ranking {
		String hand;
		long   score;
		long   count;
		int    number;
		int    members;
		int    numBetter;
		int    percentage;
	}
	
	private static ArrayList<Ranking> globalRankings;
	
	public static void runRankingCalcs(boolean output) {
		ArrayList<Ranking> list = new ArrayList<Ranking>(handRankings.length);
		Ranking currentRanking;
		int totalCount = 0;

		//System.out.println("Hand Rankings list size: "+ handRankings.length);
		try {
			FileReader input = new FileReader("newRankings.txt");

			BufferedReader bufRead = new BufferedReader(input);
			String line;
			int count = 0;
			line = bufRead.readLine();
			count++;

			ArrayList<CardCollection> members;
			while (line != null){
				String [] pieces = line.split("\t");
				if (pieces.length == 3) {	
					currentRanking = new Ranking();
					currentRanking.hand      = pieces[0];
					currentRanking.score     = Long.parseLong(pieces[1]);
					currentRanking.count     = Long.parseLong(pieces[2]);
					currentRanking.number    = count;
					members = enumerateCards(currentRanking.hand);
					totalCount              += members.size();
					currentRanking.members   = members.size();
					currentRanking.numBetter = totalCount;
					list.add(currentRanking);
				}

				//System.out.println(count+": "+line);
				line = bufRead.readLine();
				count++;
			}
			count--;
			bufRead.close();
			//System.out.println("file line count: "+count);
		}catch (IOException e){
			e.printStackTrace();
		}
		
		for ( Ranking ranking : list ) {
			ranking.percentage = (10000*ranking.numBetter) / totalCount;
			if ( output ) {
				System.out.println(
						ranking.hand+"\t"+
						ranking.score+"\t"+
						ranking.count+"\t"+
						ranking.number+"\t"+
						ranking.members+"\t"+
						ranking.numBetter+"\t"+
						ranking.percentage);
			}
		}
		if ( output )
			System.out.println("totalCount\t"+totalCount);
		globalRankings = list;
	}
	
	public static void runMatchups() {
		runRankingCalcs(false);
		HashMap<String,HashMap<String,WinLoseTie>> fullOdds = readFullOdds();
		HashMap<String,Integer> handIndex = new HashMap<String,Integer>(handRankings.length);
		for (int i = 0; i < handRankings.length; i++)
			handIndex.put(handRankings[i], i);
		
		int handCount = 0;
		Ranking next;
		Ranking prev;
		for ( Ranking ranking : globalRankings ) {
			// Get an actual card hand for the general ranking hand
			ArrayList<CardCollection> realHands = enumerateCards(ranking.hand);
			CardCollection realCards = realHands.get(0);
			
			// Build up a list of the specific member hands for the general hand
			String specStr = "";
			String addOn = "";
			for ( CardCollection specificHand : realHands) {
				specStr += addOn + specificHand.toString();
				addOn = ",";
			}
				
			// Prepare matchup by percentages array
			ArrayList<ArrayList<String>> matchups = new ArrayList<ArrayList<String>>(20);
			for ( int i = 0; i < 20; i++)
				matchups.add(new ArrayList<String>()); 
			
			// Run through the possible matchups for this hand
			Ranking compRanking;
			for ( int i = 0; i < globalRankings.size(); i++) {
				//if ( i == ranking.number ) continue;
				
				compRanking = globalRankings.get(i);
				ArrayList<CardCollection> list = enumerateCards(compRanking.hand);
				CardCollection cards;
				WinLoseTie summedWLT = new WinLoseTie();
				int h1idx = handIndex.get(ranking.hand);
				int h2idx = handIndex.get(compRanking.hand);
				for (int j = 0; j < list.size(); j++) {
					cards = list.get(j);
					
					// Map hands to standard hands
					StringBuffer hand1, hand2; 
					if ( h2idx >= h1idx ) {
						hand1 = new StringBuffer(realCards.toString());
						hand2 = new StringBuffer(cards.toString());
					} else {
						hand2 = new StringBuffer(realCards.toString());
						hand1 = new StringBuffer(cards.toString());
					}
					mapCardsForStatsRequest(hand1, hand2);
					String scards1 = hand1.toString();
					String scards2 = hand2.toString();
					
					// Eliminate impossible second hands
					String h2c1 = scards2.substring(0,2);
					String h2c2 = scards2.substring(2,4);
					String h1c1 = scards1.substring(0,2);
					String h1c2 = scards1.substring(2,4);
					if ( h2c1.equals(h1c1) || h2c1.equals(h1c2) || h2c2.equals(h1c1) || h2c2.equals(h1c2) ) 
						continue;
					
					// Lookup the hand matchup
					WinLoseTie wlt;
					if (fullOdds.get(scards1) == null ) {
						System.out.println("NNNNNNNN :"+scards1+" - "+scards2);
						continue;
					} 
					else
						wlt = fullOdds.get(scards1).get(scards2);
					
					if ( wlt == null ) {
						System.out.println( " NULL on "+scards1+"-"+scards2);
						continue;
					}
					summedWLT.win1 += wlt.win1;
					summedWLT.win2 += wlt.win2;
					summedWLT.tie  += wlt.tie;
				}
				long total = summedWLT.win1+summedWLT.win2+summedWLT.tie;
				long wins;
				if ( h2idx >= h1idx ) 
					wins = summedWLT.win1;
				else
					wins = summedWLT.win2;
				long pctIdx = (((wins+(summedWLT.tie+1)/2)* 100 / total)) / 5;
				if ( pctIdx >= 20) {
					System.out.println("Percentage is too large "+ranking.hand+" - "+compRanking.hand);
				}
				matchups.get((int)pctIdx).add(compRanking.hand);
				//break; 
			}
			
			// Output some matchups
			/*System.out.println(ranking.hand+"\n"+"Winning Matchups");
			for (int j = 19; j >= 0; j--) {
				if ( j == 11 )
					System.out.println("Coin Flips");
				if ( j == 7 )
					System.out.println("Losing Matchups");
				System.out.print(""+j*5+"-"+(j+1)*5+"%:");
				ArrayList<String> groupMatchups = matchups.get((int)j);
				for ( String member : groupMatchups) {
					System.out.print(member+" ");
				}
				System.out.println();
			}
			System.out.println("Specific instances of hand: "+specStr);
			System.out.println();*/
			
			// Output some matchups
			System.out.print(ranking.hand+"\t");
			for (int j = 19; j >= 0; j--) {

				//System.out.print(""+j*5+"-"+(j+1)*5+"%:");
				System.out.print(",");
				ArrayList<String> groupMatchups = matchups.get((int)j);
				String sep = "";
				for ( String member : groupMatchups) {
					System.out.print(sep+member);
					sep = ",";
				}
				System.out.print("\t");
			}
			System.out.print(ranking.percentage+"\t");
			System.out.print(ranking.number+"\t");
			System.out.print(specStr+"\t");
			if ( handCount+1 < globalRankings.size() ) {
				next = globalRankings.get(handCount+1);
				System.out.print(next.hand+"\t");
			} else {
				System.out.print("none"+"\t");
			}
			if ( handCount > 0 ) {
				prev = globalRankings.get(handCount-1);
				System.out.print(prev.hand);
			} else {
				System.out.print("none");
			}
			System.out.println();
			handCount++;
		}
	}
	
	public static void runOrganizer() {
		// Pairs first, then suited, then unsuited to make logic easier.
		int cardType;
		ArrayList newList = new ArrayList(handRankingsNoLimit.length);
		for (int i = 0; i < handRankingsNoLimit.length; i++) {
			cardType = getCardTypeForRank(handRankingsNoLimit[i]);
			if (cardType == PAIR)
				newList.add(handRankingsNoLimit[i]);
		}
		for (int i = 0; i < handRankingsNoLimit.length; i++) {
			cardType = getCardTypeForRank(handRankingsNoLimit[i]);
			if (cardType == SUITED)
				newList.add(handRankingsNoLimit[i]);
		}
		for (int i = 0; i < handRankingsNoLimit.length; i++) {
			cardType = getCardTypeForRank(handRankingsNoLimit[i]);
			if (cardType == UNSUITED)
				newList.add(handRankingsNoLimit[i]);
		}
		for (int i = 0; i < newList.size(); i++) {
			System.out.print("\""+newList.get(i)+"\", ");
			if (i > 0 && i % 11 == 0)
				System.out.println();
		}
	}
	
	public static void runComps() {
		CardCollection cards1;
		String cardId1;
		String cardId2;
		int firstCardType;
		int secondCardType;
		ArrayList<CardCollection> list = getCardRankSeq();
		ArrayList<CardCollection> secondList;
		long startTime = System.currentTimeMillis();
		
		//int count = 0;
		for (int i = 0; i < list.size(); i++) {
			for (int j = i; j < list.size(); j++) {
		//for (int i = 0; i < list.size(); i++) {
			//for (int j = list.size()-1; j < list.size(); j++) {
				
				cards1 = list.get(i);
				//cards2 = list.get(j);
				cardId1       =  handRankings[i];
				cardId2       =  handRankings[j];
				firstCardType = getCardTypeForRank(cardId1);
				secondCardType = getCardTypeForRank(cardId1);
				//System.out.println("id:"+cardId1+"  type:"+otherCardType);
				secondList = getCardsForRankGivenOther(j, 0, firstCardType, i);
				for ( CardCollection cards2 : secondList) {
					WinLoseTie wlt = BestChances.calculateOdds(cards1, cards2, null);
					System.out.println(cards1 +"\t"+ cards2+"\t"+ wlt.win1+ "\t"+ wlt.win2 +"\t"+ wlt.tie);
					//count++;
					//break;
				}
			}
		}
		//System.out.println("count="+count);
		long endTime = System.currentTimeMillis();
		System.out.println("start:"+startTime+ "  end:"+endTime);
	}
	
	public static void runHandDump() {
		CardCollection cards1;
		String cardId1;
		int firstCardType;

		ArrayList<CardCollection> list = getCardRankSeq();
		ArrayList<CardCollection> secondList;
		
		for (int i = 0; i < list.size(); i++) {
			for (int j = i; j < list.size(); j++) {
		//for (int i = 0; i < list.size(); i++) {
			//for (int j = list.size()-1; j < list.size(); j++) {		
				cards1 = list.get(i);
				cardId1       =  handRankings[i];
				firstCardType = getCardTypeForRank(cardId1);
				secondList = getCardsForRankGivenOther(j, 0, firstCardType, i);
				for ( CardCollection cards2 : secondList) {
					System.out.println(cards1 +"\t"+ cards2);
				}
			}
		}
	}
	
	public static void runFullOddsTest() {
		CardCollection cards1;
		String cardId1;
		String cardId2;
		int firstCardType;
		int secondCardType;
		ArrayList<CardCollection> list = getCardRankSeq();
		ArrayList<CardCollection> secondList;
		long startTime = System.currentTimeMillis();
		System.out.println("Total Memory"+Runtime.getRuntime().totalMemory());    
	    System.out.println("Free Memory"+Runtime.getRuntime().freeMemory());
		HashMap<String,HashMap<String,WinLoseTie>> odds = readFullOdds();
		System.out.println("Total Memory"+Runtime.getRuntime().totalMemory());    
	    System.out.println("Free Memory"+Runtime.getRuntime().freeMemory());
		
		//int count = 0;
		for (int i = 0; i < list.size()-1; i++) {
			for (int j = i; j < list.size()-1; j++) {
				cards1 = list.get(i);
				//cards2 = list.get(j);
				cardId1       =  handRankings[i];
				cardId2       =  handRankings[j];
				firstCardType = getCardTypeForRank(cardId1);
				secondCardType = getCardTypeForRank(cardId1);
				//System.out.println("id:"+cardId1+"  type:"+otherCardType);
				secondList = getCardsForRankGivenOther(j, 0, firstCardType, i);
				for ( CardCollection cards2 : secondList) {  
					WinLoseTie wlt = odds.get(cards1.toString()).get(cards2.toString());
					if ( wlt != null )
						System.out.println(cards1 +"\t"+ cards2+"\t"+ wlt.win1+ "\t"+ wlt.win2 +"\t"+ wlt.tie);
					else
						System.out.println(cards1 +"\t"+ cards2+"\tNULL");

					//count++;
					//break;
				}
			}
		}
		//System.out.println("count="+count);
		long endTime = System.currentTimeMillis();
		System.out.println("start:"+startTime+ "  end:"+endTime);
	}
	
	public static class RankTotals implements Comparable<RankTotals> {
		public long   totalEquity;
		public long   wins;
		public long   ties;
		public long   losses;
		public long   count;
		public String id;
		
		public int compareTo(RankTotals rt) {
			return  (int) (totalEquity - rt.totalEquity);
		}
	}
	
	public static void runRoughRankingExperiment() {
		CardCollection cards1;
		String cardId1;
		String cardId2;
		int firstCardType;
		int secondCardType;
		ArrayList<CardCollection> list = getCardRankSeq();
		ArrayList<CardCollection> secondList;
		HashMap<String,HashMap<String,WinLoseTie>> odds = readFullOdds();
		
		RankTotals[] totals = new RankTotals[handRankings.length];
		for (int i = 0; i < handRankings.length; i++) {
			totals[i] = new RankTotals();
			totals[i].id = handRankings[i];
		}
		
		//int count = 0;
		for (int i = 0; i < list.size()-1; i++) {
			for (int j = i; j < list.size()-1; j++) {
				cards1 = list.get(i);
				//cards2 = list.get(j);
				cardId1       =  handRankings[i];
				cardId2       =  handRankings[j];
				firstCardType = getCardTypeForRank(cardId1);
				secondCardType = getCardTypeForRank(cardId1);
				//System.out.println("id:"+cardId1+"  type:"+otherCardType);
				secondList = getCardsForRankGivenOther(j, 0, firstCardType, i);
				for ( CardCollection cards2 : secondList) {
					WinLoseTie wlt = odds.get(cards1.toString()).get(cards2.toString());
					//System.out.println(cards1 +"\t"+ cards2+"\t"+ wlt.win1+ "\t"+ wlt.win2 +"\t"+ wlt.tie);
					//count++;
					//break;
					totals[i].count++;
					totals[i].totalEquity += wlt.win1+wlt.tie;
					totals[j].count++;
					totals[j].totalEquity += wlt.win2+wlt.tie;
				}
			}
		}
		//System.out.println("count="+count);
		//long endTime = System.currentTimeMillis();
		//System.out.println("start:"+startTime+ "  end:"+endTime);
		
		for (int i = 0; i < handRankings.length; i++) {
			if ( totals[i].count > 0 )
				totals[i].totalEquity = (totals[i].totalEquity/totals[i].count);
			else 
				totals[i].totalEquity = 0;
		}
		Arrays.sort(totals);
		for (int i = 0; i < handRankings.length; i++) {
			System.out.println( totals[i].id + "\t" + totals[i].totalEquity +"\t" + totals[i].count);
		}
	}
	
	public static void runBetterRankingExperiment() {
		CardCollection cards1;
		String cardId1;
		String cardId2;
		int firstCardType;
		//int secondCardType;
		ArrayList<CardCollection> list = getCardRankSeq();
		ArrayList<CardCollection> secondList;
		HashMap<String,HashMap<String,WinLoseTie>> odds = readFullOdds();
		
		RankTotals[] totals = new RankTotals[handRankings.length];
		for (int i = 0; i < handRankings.length; i++) {
			totals[i] = new RankTotals();
			totals[i].id = handRankings[i];
		}
		
		int count = 0;
		int countnull = 0;
		for (int i = 0; i < list.size(); i++) {
			cards1  = list.get(i);
			cardId1 =  handRankings[i];
			ArrayList<CardCollection> c1list = enumerateCards(cardId1);
			
			for (int j = i; j < list.size(); j++) {
				//cards2 = list.get(j);
				cardId2       =  handRankings[j];
				firstCardType = getCardTypeForRank(cardId1);
				//secondCardType = getCardTypeForRank(cardId1);
				//System.out.println("id:"+cardId1+"  type:"+otherCardType);
				//secondList = getCardsForRankGivenOther(j, 0, firstCardType, i);
				secondList = enumerateCards(cardId2);

				for ( CardCollection cards2 : secondList) {

					// Eliminate impossible second hands
					String scards2 = cards2.toString();
					String scards1 = cards1.toString();
					String h2c1 = scards2.substring(0,2);
					String h2c2 = scards2.substring(2,4);
					String h1c1 = scards1.substring(0,2);
					String h1c2 = scards1.substring(2,4);
					if ( h2c1.equals(h1c1) || h2c1.equals(h1c2) || h2c2.equals(h1c1) || h2c2.equals(h1c2) ) 
						continue;
					
					// Map hands to standard hands
					StringBuffer hand1 = new StringBuffer(scards1);
					StringBuffer hand2 = new StringBuffer(scards2);
					mapCardsForStatsRequest(hand1, hand2);
					scards1 = hand1.toString();
					scards2 = hand2.toString();
					
					// Compute the odds for hands and total
					WinLoseTie wlt;
					
					if (odds.get(scards1) == null ) {
						System.out.println("NNNNNNNN :"+scards1+" - "+scards2);
						continue;
					} 
					else
					wlt = odds.get(scards1).get(scards2);
		
					// Take care of degenerate unsuited/unsuited case 
					if ( wlt == null ) {
						System.out.print("In "+ scards1+"-"+scards2+" replaced "+ scards2.substring(0,2));
						scards2 = scards2.substring(0,1) + scards1.substring(1,2) + scards2.substring(2,4);
						System.out.println(" with "+ scards2.substring(0,2));
						wlt = odds.get(scards1).get(scards2);
					}
					
					if ( wlt == null ) {
						System.out.println("Null result for :"+scards1+": - :"+scards2+":");
						countnull++;
					} else { 
						//System.out.println(cards1 +"\t"+ cards2+"\t"+ wlt.win1+ "\t"+ wlt.win2 +"\t"+ wlt.tie);
						count++;
						//break;
						totals[i].count++;
						totals[i].totalEquity += wlt.win1+(wlt.tie/2);
						totals[j].count++;
						totals[j].totalEquity += wlt.win2+(wlt.tie/2);
					}
				}
			}
		}
		//System.out.println("count="+count+ " countnull="+countnull);
		//long endTime = System.currentTimeMillis();
		//System.out.println("start:"+startTime+ "  end:"+endTime);
		
		for (int i = 0; i < handRankings.length; i++) {
			if ( totals[i].count > 0 )
				totals[i].totalEquity = (totals[i].totalEquity/totals[i].count);
			else 
				totals[i].totalEquity = 0;
		}
		Arrays.sort(totals);
		for (int i = 0; i < handRankings.length; i++) {
			System.out.println( totals[i].id + "\t" + totals[i].totalEquity +"\t" + totals[i].count);
		}
	}
	
	private static ArrayList<CardCollection> buildRandomHand() {
		ArrayList<CardCollection> hands = new ArrayList<CardCollection>(1326);

		for (int i = 0; i < handRankings.length; i++) {
			hands.addAll(enumerateCards(handRankings[i]));
		}
		System.out.println("allhands="+hands.size());
		return hands;
	}
	
	private static HashMap<String,HashMap<String,WinLoseTie>> odds;
	
	public static void runHandRangeTest() {
		long mem1 = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
		long t1 = System.currentTimeMillis();
		odds = readFullOdds();
		long t2 = System.currentTimeMillis();
		System.gc(); try { Thread.currentThread().sleep(1000); } catch (Exception e) {};
		long mem2 = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
		System.out.println("Memory used: " + ((mem2-mem1)/(1024*1024)) + " Mb" +" Time :"+(t2-t1));

		ArrayList<CardCollection> hands1 = new ArrayList<CardCollection>();
		ArrayList<CardCollection> hands2 = new ArrayList<CardCollection>();
		
		//hands1.add(createCards("AhKh"));
		//hands1.addAll(enumerateCards("AKs"));
		hands1.addAll(enumerateCards("TT"));
		hands1.addAll(enumerateCards("JJ"));
		hands1.addAll(enumerateCards("QQ"));
		hands1.addAll(enumerateCards("KK"));
		hands1.addAll(enumerateCards("AA"));
		hands1.addAll(enumerateCards("AQ"));
		hands1.addAll(enumerateCards("AJ"));
		hands1.addAll(enumerateCards("AK"));
		hands1.addAll(enumerateCards("AQs"));
		hands1.addAll(enumerateCards("AJs"));
		hands1.addAll(enumerateCards("AKs"));
		//hands1.add(createCards("QdQh"));
		hands2 = buildRandomHand();
		//hands2.add(createCards("Th5d"));
		//hands2.add(createCards("3c2h"));
		//hands2.add(createCards("3d2h"));


		//runHandRangeSpecific(hands1, hands2, hands1.toString(), hands2.toString());
		runHandRangeSpecific(hands1, hands2, "TT+AJs+AJo+", "random");

		/*ArrayList<CardCollection> allHands = buildRandomHand();
		
		for (CardCollection hand: allHands) {
			hands2 = new ArrayList<CardCollection>();
			hands2.add(hand);
			runHandRangeSpecific(hands1, hands2, hands1.toString(), hands2.toString());
		}*/
	}
	
	public static void runHandRangeSpecific(ArrayList<CardCollection> hands1, ArrayList<CardCollection> hands2, String id1, String id2) {
		RankTotals[] totals = runHandRangeDetail(hands1, hands2, id1, id2);
		
		/*
		long eqTot = 0;
		for (int i = 0; i < 2; i++) {
			if ( totals[i].count > 0 ) {
				totals[i].totalEquity = (totals[i].totalEquity*10000/totals[i].count);
			    eqTot += totals[i].totalEquity;
				//System.out.println("  eqTot="+eqTot);
			} else 
				totals[i].totalEquity = 0;
		}
		Arrays.sort(totals);
		for (int i = 0; i < 2; i++) {
			long tot = totals[i].wins + totals[i].ties + totals[i].losses;
			//System.out.println("tot="+tot+"  eqTot="+eqTot);
			System.out.println( totals[i].id + "\t" + totals[i].totalEquity +"\t" + totals[i].count+
			  //"\tEq%"+(totals[i].totalEquity*10000/eqTot) );
			  "\tW%"+(totals[i].wins*10000/tot) + "\tT%"+(totals[i].ties*10000/tot) + "\tEq%"+(totals[i].totalEquity*10000/eqTot));
		}
		System.out.println("Hands1:"+hands1);
		System.out.println("Hands2:"+hands2);
		long time3 = System.currentTimeMillis();
		System.out.println("T delta :"+(time3 - time));
		*/
		System.out.println(totals[0].id + "\t" + totals[1].id + "\t" + totals[0].wins + "\t" + totals[0].ties + "\t" + totals[1].wins); 
	}
	
	public static RankTotals[] runHandRangeDetail(ArrayList<CardCollection> hands1, ArrayList<CardCollection> hands2, String id1, String id2) {
		System.out.println("hands1 size:" +hands1.size()+ "  hands2 size:"+hands2.size());
		
		if ( odds == null ) {
			long mem1 = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
			long t1 = System.currentTimeMillis();
			odds = readFullOdds();
			long t2 = System.currentTimeMillis();
			System.gc(); try { Thread.currentThread().sleep(1000); } catch (Exception e) {};
			long mem2 = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
			System.out.println("Odds Memory used: " + ((mem2-mem1)/(1024*1024)) + " Mb" +" Time :"+(t2-t1));
			System.out.println("Current Directory: " +  System.getProperty("user.dir"));
		}
		
		long time = System.currentTimeMillis();
		
		RankTotals[] totals = new RankTotals[2];
		for (int i = 0; i < 2; i++) {
			totals[i] = new RankTotals();
			//totals[i].id = ""+i;
			if (i == 0)
		        totals[i].id = id1;
		    if (i == 1)
		        totals[i].id = id2;
		}
		
		for ( CardCollection hand1 : hands1) {
			for ( CardCollection hand2 : hands2) {
				String scards2 = hand2.toString();
				String scards1 = hand1.toString();
				
				// See if the hand order needs to be reversed before lookup
				int h1Idx = getCardIndexForSpecificCards(scards1);
				int h2Idx = getCardIndexForSpecificCards(scards2);
				boolean reversed = false;
				if ( h1Idx > h2Idx) {
					String temp = scards2;
					scards2 = scards1;
					scards1 = temp;
					reversed = true;
				}
				
				// Eliminate impossible second hands
				String h2c1 = scards2.substring(0,2);
				String h2c2 = scards2.substring(2,4);
				String h1c1 = scards1.substring(0,2);
				String h1c2 = scards1.substring(2,4);
				if ( h2c1.equals(h1c1) || h2c1.equals(h1c2) || h2c2.equals(h1c1) || h2c2.equals(h1c2) ) 
					continue;
				
				// Map hands to standard hands
				StringBuffer hhand1 = new StringBuffer(scards1);
				StringBuffer hhand2 = new StringBuffer(scards2);
//System.out.println("b cards1: "+scards1+"  cards2: "+scards2);
				mapCardsForStatsRequest(hhand1, hhand2);  
				scards1 = hhand1.toString();
				scards2 = hhand2.toString();
	

// Eliminate impossible second hands
String nh2c1 = scards2.substring(0,2);
String nh2c2 = scards2.substring(2,4);
String nh1c1 = scards1.substring(0,2);
String nh1c2 = scards1.substring(2,4);
if ( nh2c1.equals(nh1c1) || nh2c1.equals(nh1c2) || nh2c2.equals(nh1c1) || nh2c2.equals(nh1c2) ) {
System.out.println("Skipping impossible hand - cards1:"+scards1+"  cards2: "+scards2);
	continue;
}
				
				// Compute the odds for hands and total
				WinLoseTie wlt;
				
//System.out.println("a cards1: "+scards1+"  cards2: "+scards2);
				if (odds.get(scards1) == null ) {
					System.out.println("NNNNNNNN :"+scards1+" - "+scards2);
					continue;
				} 
				else
				wlt = odds.get(scards1).get(scards2);
//System.out.println("RANGE CALC WLT :"+wlt.win1 + "\t" + wlt.win1 + "\t" + wlt.tie);
				// Take care of degenerate unsuited/unsuited case 
				if ( wlt == null ) {
					System.out.print("In "+ scards1+"-"+scards2+" replaced "+ scards2.substring(0,2));
					scards2 = scards2.substring(0,1) + scards1.substring(1,2) + scards2.substring(2,4);
					System.out.println(" with "+ scards2.substring(0,2));
					wlt = odds.get(scards1).get(scards2);
				}
				
				if ( wlt == null ) {
					System.out.println("Null result for :"+scards1+": - :"+scards2+":");
					System.out.println("Orig hands: "+h1c1+h1c2+" - "+h2c1+h2c2);
					//countnull++;
				} else { 
					//System.out.println(cards1 +"\t"+ cards2+"\t"+ wlt.win1+ "\t"+ wlt.win2 +"\t"+ wlt.tie);
					//count++;
					//break;
					int first  = 0;
					int second = 1;
					if ( reversed ) {
						first  = 1;
						second = 0;
					}
					totals[first].count += wlt.win1 + wlt.tie + wlt.win2;
					totals[first].totalEquity += wlt.win1+(wlt.tie/2);
					totals[first].wins += wlt.win1;
					totals[first].ties += wlt.tie;
					totals[first].losses += wlt.win2;
					
					totals[second].count += wlt.win1 + wlt.tie + wlt.win2;
					totals[second].totalEquity += wlt.win2+(wlt.tie/2);
					totals[second].wins += wlt.win2;
					totals[second].ties += wlt.tie;
					totals[second].losses += wlt.win1;
					//System.out.println("count="+totals[0].count);
				}
			}
		}
		/*
		long eqTot = 0;
		for (int i = 0; i < 2; i++) {
			if ( totals[i].count > 0 ) {
				totals[i].totalEquity = (totals[i].totalEquity*10000/totals[i].count);
			    eqTot += totals[i].totalEquity;
				//System.out.println("  eqTot="+eqTot);
			} else 
				totals[i].totalEquity = 0;
		}
		Arrays.sort(totals);
		for (int i = 0; i < 2; i++) {
			long tot = totals[i].wins + totals[i].ties + totals[i].losses;
			//System.out.println("tot="+tot+"  eqTot="+eqTot);
			System.out.println( totals[i].id + "\t" + totals[i].totalEquity +"\t" + totals[i].count+
			  //"\tEq%"+(totals[i].totalEquity*10000/eqTot) );
			  "\tW%"+(totals[i].wins*10000/tot) + "\tT%"+(totals[i].ties*10000/tot) + "\tEq%"+(totals[i].totalEquity*10000/eqTot));
		}
		System.out.println("Hands1:"+hands1);
		System.out.println("Hands2:"+hands2);
		long time3 = System.currentTimeMillis();
		System.out.println("T delta :"+(time3 - time));
		*/
		//System.out.println(totals[0].id + "\t" + totals[1].id + "\t" + totals[0].wins + "\t" + totals[0].ties + "\t" + totals[1].wins); 
		return totals;
	}
	
	public static void runSpecificHandDump() {
		String cardId1;

		for (int i = 0; i < handRankings.length; i++) {
			cardId1 =  handRankings[i];
			ArrayList<CardCollection> c1list = enumerateCards(cardId1);
			System.out.print( cardId1 + "\t" );

			String addOn = "";
			for ( CardCollection hand : c1list ) {
				System.out.print( addOn + hand.toString() );
				addOn = ",";
			}
			System.out.println();
		}
	}
	
	public static void runHandRankingDump() {
		String cardId1;

		for (int i = 0; i < handRankings.length; i++) {
			cardId1 =  handRankings[i];
			System.out.println( cardId1 );
		}
	}
	
	
    public static void runHandFrequencyCount() {
    	HashMap<String,Integer> hash = new HashMap<String,Integer>();
    	String  cards;
    	Integer countObj;
    	int     count;
    	
    	for (int i = 0; i < 10000000; i++) {
    		cards = dealCardsFromRandomDeck();
    		countObj = hash.get(cards);
    		if (countObj == null) {
    			count = 1;
    		} else {
    			count = countObj;
    			count++;
    		}
    		hash.put(cards, count);
      	}
    	
    	// Get the ranked hands
    	System.out.println("Ranked Hands:");
		for (int i = 0; i < handRankings.length; i++) {
			cards =  handRankings[i];
    		countObj = hash.remove(cards);
    		if (countObj == null) {
    			count = 0;
    		} else {
    			count = countObj;
    		}
    		System.out.println(cards +"\t"+ count);
		}
		
    	// Get the unranked hands
    	System.out.println("\nUnranked Hands:");
    	ArrayList<String> list = new ArrayList<String>();
    	list.addAll(hash.keySet());
		for (int i = 0; i < list.size(); i++) {
			cards =  list.get(i);
    		countObj = hash.get(cards);
    		if (countObj == null) {
    			count = 0;
    		} else {
    			count = countObj;
    		}
    		System.out.println(cards +"\t"+ count);
		}
    }
    
    // Get a string representation of a hand of pairs, suited or unsuited cards ordered by rank.
    public static String dealCardsFromRandomDeck( ) {
    	Card card1;
    	Card card2;
    	Card temp;
    	
		Deck deck = new Deck();
		deck.shuffle();
		card1 = deck.remove();
		card2 = deck.remove();
		if (card1.getRank() < card2.getRank()) {
			temp  = card1;
			card1 = card2;
			card2 = temp;
		}
		
		String hand = card1.getFaceValueString() + card2.getFaceValueString();
		if (card1.getSuit() == card2.getSuit())
			hand += "s";

		return hand;
	}
	
	public static ArrayList<CardCollection> getCardRankSeq() {
		ArrayList<CardCollection> list = new ArrayList<CardCollection>(handRankings.length);
		for ( int rank = 0; rank < handRankings.length; rank++ ) {
			list.add(getCardsForRank(rank,0));
		}
		return list;
	}
	public static final int SUITED = 0, 
	                        UNSUITED = 1,
	                        PAIR = 2,
	                        UNKNOWN = -1;
	
	public static int getCardTypeForRank(String cardId) {
		if (cardId.length() == 3 && cardId.substring(2,3).equals("s"))
			return SUITED;
		else if (cardId.length() == 2 && cardId.substring(0,1).equals(cardId.substring(1,2)))
			return PAIR;
		else if (cardId.length() == 2 )
			return UNSUITED;
		return UNKNOWN;
	}
	
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
	
	private static int getIndex(String cardID) {
		if (handIndex == null) {
	        handIndex = new HashMap<String, Integer>(handRankings.length);
	        for (int i = 0; i < handRankings.length; i++)
	        	handIndex.put(handRankings[i], i);
		}
		
		//Integer
		
		return handIndex.get(cardID);
	}
	public static int getCardIndexForSpecificCards(String cards) {
		if (cards.length() != 4)
			return -1;
		
		int type = getCardTypeForSpecificCards(cards);
		
		String r1 = cards.substring(0,1);
		String r2 = cards.substring(2,3);
		
		String cardID;
		
		if (type == PAIR) {
			cardID = r1 + r1;
			return getIndex(cardID);
		}
		
		if (type == SUITED) {
			cardID = r1 + r2 + "s";
		} else {
			cardID = r1 + r2;
		}
		
		return getIndex(cardID);
	}
	

	
	public static ArrayList<CardCollection> getCardsForRankGivenOther(int rank, int element, int firstCardType, int firstCardRank) {
		ArrayList<CardCollection> list = new ArrayList<CardCollection>();
		CardCollection cards = new CardCollection();
		if ( rank < 0 || rank >= handRankings.length)
			return null;
		String cardId = handRankings[rank];
		int    cardType = getCardTypeForRank(cardId);
		String firstCardId = handRankings[firstCardRank];
		String fullCard;
		if (cardType == PAIR) {
			// All to Pair
			//   different suits
			String suitStr1 = "h";
			String suitStr2 = "d";
			if (firstCardType == PAIR) {
				suitStr1 = "c";
				suitStr2 = "s";
			}
			fullCard = cardId.substring(0,1) + suitStr1 + cardId.substring(1,2) + suitStr2;
			cards = createCards(fullCard);
			list.add(cards);
			
			// Pair to Pair
			//   matching suits (if not identical pair)
			if (firstCardType == PAIR && rank != firstCardRank) {
				suitStr1 = "h";
				suitStr2 = "d";
				fullCard = cardId.substring(0,1) + suitStr1 + cardId.substring(1,2) + suitStr2;
				cards = createCards(fullCard);
				list.add(cards);
			}
			
			// Pair to Pair
			//   one matching suit (if not identical pair)
			if (firstCardType == PAIR && rank != firstCardRank) {
				suitStr1 = "h";
				suitStr2 = "s";
				fullCard = cardId.substring(0,1) + suitStr1 + cardId.substring(1,2) + suitStr2;
				cards = createCards(fullCard);
				list.add(cards);
			}
						
		} else if (cardType == SUITED){
			// All to Suited
			//   different suits
			String suitStr = "c";
			if (firstCardType == SUITED || firstCardType == UNSUITED)
				suitStr = "h";
			fullCard = cardId.substring(0,1) + suitStr + cardId.substring(1,2) + suitStr;
			cards = createCards(fullCard);
			list.add(cards);
			
			// Pair to Suited
			//   one matching suit (unless either card equal to pair)
			if (firstCardType == PAIR && !cardId.substring(0,1).equals(firstCardId.substring(0,1)) && 
				!cardId.substring(0,1).equals(firstCardId.substring(1,2))) {
				String suitStr1 = "h";
				String suitStr2 = "h";
				fullCard = cardId.substring(0,1) + suitStr1 + cardId.substring(1,2) + suitStr2;
				cards = createCards(fullCard);
				list.add(cards);
			}
			
			// Suited to Suited
			//   matching suits (if not overlapping)
			if (firstCardType == SUITED && 
				!cardId.substring(0,1).equals(firstCardId.substring(0,1)) && 
				!cardId.substring(0,1).equals(firstCardId.substring(1,2)) &&
				!cardId.substring(1,2).equals(firstCardId.substring(0,1)) && 
				!cardId.substring(1,2).equals(firstCardId.substring(1,2))) {
				String suitStr1 = "c";
				String suitStr2 = "c";
				fullCard = cardId.substring(0,1) + suitStr1 + cardId.substring(1,2) + suitStr2;
				cards = createCards(fullCard);
				list.add(cards);
			}
			
		} else if (cardType == UNSUITED ) {
			// All to Unsuited
			//   different suits
			String suitStr1 = "c";
			String suitStr2 = "s";
			if (firstCardType == SUITED || firstCardType == UNSUITED) {
				suitStr1 = "h";
				suitStr2 = "d";
			}
			fullCard = cardId.substring(0,1) + suitStr1 + cardId.substring(1,2) + suitStr2;
			cards = createCards(fullCard);
			list.add(cards);
			
			// Pair to Unsuited
			//   one matching suit - top  (unless equal to pair)
			if (firstCardType == PAIR && !cardId.substring(0,1).equals(firstCardId.substring(0,1))) {
				suitStr1 = "h";
				suitStr2 = "c";
				fullCard = cardId.substring(0,1) + suitStr1 + cardId.substring(1,2) + suitStr2;
				cards = createCards(fullCard);
				list.add(cards);
			}
				
			// Pair to Unsuited   
		    //   one matching suit - bottom (unless equal to pair)
			//   Note: This is only relevant if the top card was equal to pair since stats are identical
			//   - update: note is only true if suited connectors so let rip
			if (firstCardType == PAIR && 
				!cardId.substring(1,2).equals(firstCardId.substring(0,1)) /* &&
				cardId.substring(0,1).equals(firstCardId.substring(0,1)) see note */ ) {
				suitStr1 = "c";
				suitStr2 = "h";
				fullCard = cardId.substring(0,1) + suitStr1 + cardId.substring(1,2) + suitStr2;
				cards = createCards(fullCard);
				list.add(cards);
			}
			
			// Pair to Unsuited
			//   both matching suits - (unless either equal to pair)
			if (firstCardType == PAIR && 
				!cardId.substring(0,1).equals(firstCardId.substring(0,1)) &&
				!cardId.substring(1,2).equals(firstCardId.substring(0,1))) {
				suitStr1 = "h";
				suitStr2 = "d";
				fullCard = cardId.substring(0,1) + suitStr1 + cardId.substring(1,2) + suitStr2;
				cards = createCards(fullCard);
				list.add(cards);
			}
			if (firstCardType == SUITED) {
				boolean topUnsuitedCardInSuitedRange = 
					cardId.substring(0,1).equals(firstCardId.substring(0,1)) ||
					cardId.substring(0,1).equals(firstCardId.substring(1,2));
				boolean bottomUnsuitedCardInSuitedRange = 
					cardId.substring(1,2).equals(firstCardId.substring(0,1)) ||
					cardId.substring(1,2).equals(firstCardId.substring(1,2));

				// Suited to Unsuited
				//   one matching suit - top  (unless top in suited range)
				if (!topUnsuitedCardInSuitedRange) {
					suitStr1 = "c";
					suitStr2 = "s";
					fullCard = cardId.substring(0,1) + suitStr1 + cardId.substring(1,2) + suitStr2;
					cards = createCards(fullCard);
					list.add(cards);
				}
				
				// Suited to Unsuited
				//   one matching suit - bottom (unless bottom in suited range)
				//   Note: This is only necessary if the prior case was impossible given duplicate stats
				//   update: note is only true if connectors so let rip.
				if (/*topUnsuitedCardInSuitedRange &&*/ !bottomUnsuitedCardInSuitedRange) {
					suitStr1 = "s";
					suitStr2 = "c";
					fullCard = cardId.substring(0,1) + suitStr1 + cardId.substring(1,2) + suitStr2;
					cards = createCards(fullCard);
					list.add(cards);
				}
			}
			
			if (firstCardType == UNSUITED) {
				boolean topCardMatches = 
					cardId.substring(0,1).equals(firstCardId.substring(0,1));
				boolean bottomCardMatches = 
					cardId.substring(1,2).equals(firstCardId.substring(1,2));
				boolean bottomCardMatchesTop = 
					cardId.substring(1,2).equals(firstCardId.substring(0,1));
				boolean topCardMatchesBottom = 
					cardId.substring(0,1).equals(firstCardId.substring(1,2));
				// Unsuited to Unsuited
				//   one matching suit - top to top (unless duplicate card)
				if (!topCardMatches) {
					suitStr1 = "c";
					suitStr2 = "h";
					fullCard = cardId.substring(0,1) + suitStr1 + cardId.substring(1,2) + suitStr2;
					cards = createCards(fullCard);
					list.add(cards);
				}
				
				// Unsuited to Unsuited
				//   one matching suit - bottom to bottom (unless duplicate range)
				if (!bottomCardMatches) {
					suitStr1 = "h";
					suitStr2 = "s";
					fullCard = cardId.substring(0,1) + suitStr1 + cardId.substring(1,2) + suitStr2;
					cards = createCards(fullCard);
					list.add(cards);
				}
				
				// Unsuited to Unsuited
				//   matching suits (if not overlapping ranges)
				if (!topCardMatches && !bottomCardMatches) {
					suitStr1 = "c";
					suitStr2 = "s";
					fullCard = cardId.substring(0,1) + suitStr1 + cardId.substring(1,2) + suitStr2;
					cards = createCards(fullCard);
					list.add(cards);
				}
				
				// Unsuited to Unsuited
				//   matching suits but inverted unless either would be duplicate
				if (!bottomCardMatchesTop && !topCardMatchesBottom) {
					suitStr1 = "s";
					suitStr2 = "c";
					fullCard = cardId.substring(0,1) + suitStr1 + cardId.substring(1,2) + suitStr2;
					cards = createCards(fullCard);
					list.add(cards);
				}
				
				// Unsuited to Unsuited
				//   ?? bottom to top ??
				if (!bottomCardMatchesTop) {
					suitStr1 = "h";
					suitStr2 = "c";
					fullCard = cardId.substring(0,1) + suitStr1 + cardId.substring(1,2) + suitStr2;
					cards = createCards(fullCard);
					list.add(cards);
				}
				
				// Unsuited to Unsuited
				//   ?? top to bottom ??
				if (!bottomCardMatchesTop && false) {
					suitStr1 = "s";
					suitStr2 = "h";
					fullCard = cardId.substring(0,1) + suitStr1 + cardId.substring(1,2) + suitStr2;
					cards = createCards(fullCard);
					list.add(cards);
				}
				
				// Unsuited to Unsuited
				//   ?? top to bottom ??
				if (!topCardMatchesBottom) {
					suitStr1 = "s";
					suitStr2 = "h";
					fullCard = cardId.substring(0,1) + suitStr1 + cardId.substring(1,2) + suitStr2;
					cards = createCards(fullCard);
					list.add(cards);
				}
			}
		} else {
			//System.out.println("xx:"+cardId+ " - "+cardId.substring(2,3) + " len:"+cardId.length());
			return null;
		}
		
		//cards = createCards(fullCard);
		//System.out.println("fc:"+fullCard);
		
		return list;
	}
	
	// Pair to Pair
	//   different suits
	//   matching suits (if not identical pair)
	//   one matching suit (if not identical pair)
	
	// Pair to Suited
	//   different suits
	//   one matching suit (unless either card equal to pair)
	// Pair to Unsuited
	//   different suits
	//   one matching suit - top  (unless equal to pair)
    //   one matching suit - bottom (unless equal to pair)
	//   both matching suits - (unless either equal to pair)
	
	// Suited to Suited
	//   different suits
	//   matching suits (if not overlapping)
	// Suited to Unsuited
	//   different suits
	//   one matching suit - top  (unless top in suited range)
    //   one matching suit - bottom (unless bottom in suited range)
	
	// Unsuited to Unsuited
	//   different suits
	//   one matching suit - top to top (unless duplicate range)
	//   one matching suit - top to bottom (unless top == bottom)
    //   one matching suit - bottom to bottom (unless duplicate range)
	//   ?? bottom to top ??
    //   matching suits (if not overlapping ranges)
	
	public static CardCollection getCardsForRank(int rank, int element) {
		CardCollection cards = new CardCollection();
		if ( rank < 0 || rank >= handRankings.length)
			return null;
		String cardId = handRankings[rank];
		int    cardType = getCardTypeForRank(cardId);
		String fullCard;
		if (cardType == PAIR) {
			fullCard = cardId.substring(0,1) + "h" + cardId.substring(1,2) + "d";
		} else if (cardType == SUITED){
			fullCard = cardId.substring(0,1) + "c" + cardId.substring(1,2) + "c";
		} else if (cardType == UNSUITED ) {
			fullCard = cardId.substring(0,1) + "c" + cardId.substring(1,2) + "s";
		} else {
			//System.out.println("xx:"+cardId+ " - "+cardId.substring(2,3) + " len:"+cardId.length());
			return null;
		}
		
		cards = createCards(fullCard);
		//System.out.println("fc:"+fullCard);
		
		return cards;
	}
	
	private static CardCollection createCards( String cardString ) {
		CardCollection ret = new CardCollection();
		for (int i = 0; (i+2) <= cardString.length(); i += 2) {
			ret.add(Card.create(cardString.substring(i,i+2)));
		}
		return ret;
	}
	
	public static ArrayList<CardCollection> getPocketPair(int rank) {
		ArrayList<CardCollection> list = new ArrayList<CardCollection>(6);
		
		for (int suit1 = Suit.HEARTS; suit1 <= Suit.CLUBS; suit1++) {
			for (int suit2 = suit1+1; suit2 <= Suit.CLUBS; suit2++) {
				CardCollection cards = new CardCollection();
				cards.add(Card.create(suit1, rank));
				cards.add(Card.create(suit2, rank));
				list.add(cards);
			}
		}
		return list;
	}
	
	public static ArrayList<CardCollection> getSuitedConnectors(int topRank, int bottomRank) {
		ArrayList<CardCollection> list = new ArrayList<CardCollection>(6);
		
		for (int suit1 = Suit.HEARTS; suit1 <= Suit.CLUBS; suit1++) {
			CardCollection cards = new CardCollection();
			cards.add(Card.create(suit1, topRank));
			cards.add(Card.create(suit1, bottomRank));
			list.add(cards);
		}
		return list;
	}
	
	public static ArrayList<CardCollection> getUnsuitedConnectors(int topRank, int bottomRank) {
		ArrayList<CardCollection> list = new ArrayList<CardCollection>(6);
		
		for (int suit1 = Suit.HEARTS; suit1 <= Suit.CLUBS; suit1++) {
			for (int suit2 = Suit.HEARTS; suit2 <= Suit.CLUBS; suit2++) {
				if (suit1 == suit2) continue;
				CardCollection cards = new CardCollection();
				cards.add(Card.create(suit1, topRank));
				cards.add(Card.create(suit2, bottomRank));
				list.add(cards);
			}
		}
		return list;
	}
	
	public static ArrayList<CardCollection> enumerateCards(String cardId) {
		int cardType = getCardTypeForRank(cardId);
		if (cardType == PAIR) {
			int rank;
			String rankSymbol = cardId.substring(0,1);
			rank = Card.parseRank(rankSymbol);
			return getPocketPair(rank);
		} else if (cardType == SUITED){
			int topRank;
			int bottomRank;
			String rankSymbol = cardId.substring(0,1);
			topRank = Card.parseRank(rankSymbol);
			rankSymbol = cardId.substring(1,2);
			bottomRank = Card.parseRank(rankSymbol);
			return getSuitedConnectors(topRank, bottomRank);
		} else if (cardType == UNSUITED ) {
			int topRank;
			int bottomRank;
			String rankSymbol = cardId.substring(0,1);
			topRank = Card.parseRank(rankSymbol);
			rankSymbol = cardId.substring(1,2);
			bottomRank = Card.parseRank(rankSymbol);
			return getUnsuitedConnectors(topRank, bottomRank);
		} else {
			//System.out.println("xx:"+cardId+ " - "+cardId.substring(2,3) + " len:"+cardId.length());
			return null;
		}
	}
	
	public static HashMap<String,HashMap<String,WinLoseTie>> readFullOdds() {
		HashMap<String,HashMap<String,WinLoseTie>> globalHashMap = new HashMap<String,HashMap<String,WinLoseTie>>();
		try {
			//FileReader input = new FileReader("fullodds.txt");
			//FileReader input = new FileReader("allodds.txt");
			FileReader input = new FileReader("fullstats.txt");

			BufferedReader bufRead = new BufferedReader(input);
			String line;
			int count = 0;
			line = bufRead.readLine();
			count++;
			String priorCards1 = "";
			HashMap<String,WinLoseTie> priorHashMap = null;
			//CardCollection cards1 = null; 
			//CardCollection cards2 = null;
			WinLoseTie stats;
//boolean foundTarget=false;
			while (line != null){
				String [] pieces = line.split("\t");
				//if (pieces.length == 5) {
				if (pieces.length >= 8) {	
//if (pieces[0].equals("QhQd") && pieces[1].equals("Th5d")) {
//System.out.println("Loaded QhQd Th5d : "+pieces[2]+"\t"+pieces[3]+"\t"+pieces[4]);
//foundTarget = true;
//}
					if (!pieces[0].equals(priorCards1)) {
						//cards1 = createCards(pieces[0]);
						priorHashMap = globalHashMap.get(pieces[0]);
						if (priorHashMap == null)
						    priorHashMap = new HashMap<String,WinLoseTie>();
						globalHashMap.put(pieces[0], priorHashMap);
						priorCards1 = pieces[0];
					}
					//cards2 = createCards(pieces[1]);
					stats = new WinLoseTie();
					stats.win1 = Long.parseLong(pieces[2]);
					stats.win2 = Long.parseLong(pieces[3]);
					stats.tie  = Long.parseLong(pieces[4]);
					priorHashMap.put(pieces[1], stats);
//if (foundTarget) {
//System.out.println("priorCards1 :" +priorCards1+ " pieces[0] :"+pieces[0]+" pieces[1] :"+pieces[1]+ "\t"+stats.win1+ "\t"+stats.win2 + "\t"+stats.tie);
//foundTarget=false;
//WinLoseTie wlt = globalHashMap.get(pieces[0]).get(pieces[1]);
//System.out.println("WLT :"+wlt.win1 + "\t" + wlt.win1 + "\t" + wlt.tie);
//}
				    //System.out.println(cards1 +"\t"+ cards2+"\t"+ stats.win1+ "\t"+ stats.win2 +"\t"+ stats.tie);
				}

				//System.out.println(count+": "+line);
				line = bufRead.readLine();
				count++;
			}
			count--;
			bufRead.close();
			//System.out.println("file line count: "+count);
		}catch (IOException e){
			e.printStackTrace();
		}
		return globalHashMap;
	}
	
	public static void mapCardsForStatsRequest(StringBuffer cards1, StringBuffer cards2) {
		String hand1 = cards1.toString();
		String hand2 = cards2.toString();
		int    type1 = getCardTypeForSpecificCards(hand1);
		int    type2 = getCardTypeForSpecificCards(hand2);
//System.out.println("hand1: "+hand1+" hand2: "+hand2);
		
		String h1r1 = hand1.substring(0,1);
		String h1r2 = hand1.substring(2,3);
		String h1s1 = hand1.substring(1,2);
		String h1s2 = hand1.substring(3,4);
		
		String h2r1 = hand2.substring(0,1);
		String h2r2 = hand2.substring(2,3);
		String h2s1 = hand2.substring(1,2);
		String h2s2 = hand2.substring(3,4);
		String h2ns1, h2ns2;

		
		// Assumes bigger pair/starting card is first.
		// Assumes 1,2 ordered by PAIR, SUITED, UNSUITED
		// Assumes cards within hand are ordered by rank
		// Assumes no duplicate cards
//System.out.println("type 1:"+type1);

		if ( type1 == PAIR /*&& type2 != PAIR*/ ) {
			hand1 = h1r1 + "h" + h1r2 + "d";
			boolean h2s1equal = h2s1.equals(h1s1) ||  h2s1.equals(h1s2);
			boolean h2s2equal = type2 == PAIR && (h2s2.equals(h1s1) || h2s2.equals(h1s2));
			boolean bothSidesEqual = (h2s2.equals(h1s1) || h2s2.equals(h1s2)) && h2s1equal;
			boolean usingFirst = h2s2equal && !h2s1equal;
			h2ns1 = "c";  // Defaulting 
			h2ns2 = "s";  // Defaulting 
			if ( (h2s1equal || h2s2equal) ) {
				h2ns1 = "h";
				if ( type2 == UNSUITED ){
					h2ns2 = "c";  // Defaulting 
				}
			} else {
				h2ns1 = "c";
			}
			if ( h2s2.equals(h2s1) ) {
				h2ns2 = h2ns1;
//System.out.println("case 1");
			} else if ( h2s2equal && h2s1equal ) {
				h2ns2 = "d";
//System.out.println("case 2");
			} else if (bothSidesEqual && type2 == UNSUITED && !h2ns1.equals("h")) { //( h2s2.equals(h1s2) && h2s1.equals(h1s1)) {
				h2ns2 = "h";
//System.out.println("case 3");
			} else if ( type2 == UNSUITED && (h2s2.equals(h1s1) || h2s2.equals(h1s2)) && !h2ns1.equals("h") ) { 
				h2ns2 = "h";
//System.out.println("case 5");
			} else if (bothSidesEqual && type2 == UNSUITED && h2ns1.equals("h")) { //( h2s2.equals(h1s2) && h2s1.equals(h1s1)) {
				h2ns2 = "d";
//System.out.println("case 6");
			} else if ( type2 == UNSUITED && (h2s2.equals(h1s1) || h2s2.equals(h1s2)) && h2ns1.equals("h") ) { 
				h2ns2 = "d";
//System.out.println("case 7");
			} else  {
//System.out.println("case 4");
//System.out.println("h2s2:"+h2s2);
//System.out.println("h1s2:"+h1s2);
//System.out.println("h2s1:"+h2s1);
//System.out.println("h1s1:"+h1s1);

			}

		} else if ( type1 == SUITED ) {
			hand1 = h1r1 + "c" + h1r2 + "c";
			boolean overlap = h2r1.equals(h1r1) || h2r2.equals(h1r1) || h2r2.equals(h1r1) || h2r2.equals(h1r2);
			if ( type2 == SUITED && ( overlap || !h2s1.equals(h1s1) )) {
				h2ns1 = "h";
				h2ns2 = "h";
			} else if ( type2 == SUITED ) {
				h2ns1 = "c";
				h2ns2 = "c";
			} else { // UNSUITED 
				if (h2s1.equals(h1s1)) {
					h2ns1 = "c";
					h2ns2 = "s";
				} else if (h2s2.equals(h1s1)) {
					h2ns2 = "c";
					h2ns1 = "s";
				} else {
					h2ns1 = "h";
					h2ns2 = "d";
				}
			}
		} else if ( type1 == UNSUITED ) {
			hand1 = h1r1 + "c" + h1r2 + "s";
			h2ns1 = "h";  // Defaulting 
			h2ns2 = "d";  // Defaulting 
			if (h2s1.equals(h1s1)) {
				h2ns1 = "c";
				h2ns2 = "h";  // Defaulting 
			} else if (h2s1.equals(h1s2)) {
				h2ns1 = "s";
				h2ns2 = "h";  // Defaulting 
			} 
			if (h2s2.equals(h1s1)) {
				h2ns2 = "c";
			} else if (h2s2.equals(h1s2)) {
				h2ns2 = "s";
			}
		} else {  // Impossible
			h2ns1 = h2ns2 = "x";
		}
		hand2 = h2r1 + h2ns1 + h2r2 + h2ns2;
		cards1.setLength(0);
		cards2.setLength(0);
		cards1.append(hand1);
		cards2.append(hand2);
	}
	
	public static void runOneMapCardsTest(String scards1, String scards2) {
		StringBuffer cards1, cards2;
		
		cards1 = new StringBuffer(scards1);
		cards2 = new StringBuffer(scards2);
		System.out.println("Before :"+cards1+ " - "+cards2);
		mapCardsForStatsRequest(cards1, cards2);
		System.out.println("After  :"+cards1+ " - "+cards2+"\n");
	}
	
	public static void runMapCardsTest() {
		runOneMapCardsTest("AsKd", "8d7c");
		runOneMapCardsTest("AsKd", "8h7c");
		runOneMapCardsTest("AsKd", "8s7c");
		runOneMapCardsTest("AsAd", "8d7c");
		
		runOneMapCardsTest("AsKs", "8d7c");
		runOneMapCardsTest("AsKs", "8d7s");
		runOneMapCardsTest("AsKs", "8s7d");
	}
	
	public static void runMapTestOnAllCards() {
		
	}
	


}
