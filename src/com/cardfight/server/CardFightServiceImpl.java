package com.cardfight.server;

import java.util.ArrayList;
import java.util.HashSet;

import com.cardfight.client.CardFightResult;
import com.cardfight.client.CardFightSummary;

import com.cardfight.client.CardFightService;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import poker.Card;
import poker.HandRange;
import poker.CardCollection;
import poker.HandRange.RankTotals;

import com.cardfight.client.RangeUtils;


public class CardFightServiceImpl extends RemoteServiceServlet implements CardFightService {
	
	  public static void main(String args[]) {
		  	  ArrayList<String> list = new ArrayList<String>(3);
			  list.add("QQ+,AJs+");
			  list.add("KK+,AQs+");
			  list.add("AQs+,KJs+");
			  //list.add("88+,ATs+,KTs+,QJs,AJo+,KQo");
			  //list.add("99+,A7s+,K8s+,QTs+,JTs,ATo+,KTo+,QTo+,JTo");
			  //list.add("22+,ATs+,KTs+,QTs+,JTs,ATo+,KTo+,QTo+,JTo");
			  CardFightServiceImpl impl = new CardFightServiceImpl();
			  CardFightSummary summary = impl.calculate(list, "", "R:");
			  ArrayList<CardFightResult> result = summary.getResults();
			  System.out.println("input: "+ list);
			  System.out.println("output: "+ result);
		  }

	public CardFightSummary calculate(ArrayList<String> playersHands, String board, String discard) {
		/*ArrayList<CardFightResult> results = new ArrayList<CardFightResult>();
		for (int i = 0; i < playersHands.size(); i++) {
			CardFightResult result = new CardFightResult();
			result.equity = "25.00";
			result.winPct = "24.00";
			result.tiePct = "1.00";
			results.add(result);
		}
		return results;*/
		System.out.println("calculate: " + playersHands + " b: "+board+ " d: "+discard);
		// Look for range flag in discard String
		boolean rangeCalc = discard != null && discard.startsWith("R:");
		if ( rangeCalc )
			discard = discard.substring(2);
			
		if ( rangeCalc ) 
			return calculateRange(playersHands, board, discard);
		else 
			return CalculateStats.calculateFromCardFight(playersHands, board, discard);
	}
		
	private CardFightSummary calculateRange(ArrayList<String> playersHands, String board, String discard) {
		if ( !(playersHands.size() == 2 && board.equals("") && discard.equals("")) ) 
			return calculateComplexRange(playersHands, board, discard);
		
		long time = System.currentTimeMillis();
		
	    ArrayList<CardCollection> hands1 = null;
	    ArrayList<CardCollection> hands2 = null;
	    int count = 0;
		for (String shand : playersHands) {
			if ( count == 0 )
				hands1 = parseCardInput(shand);
			else if ( count == 1 )
				hands2 = parseCardInput(shand);
			else 
				return null;
			count++;
		}
		ArrayList<CardFightResult> results = new ArrayList<CardFightResult>(2);
		RankTotals[] totals = HandRange.runHandRangeDetail(hands1, hands2, "hand1", "hand2");
		
		long totalInc = 1; //4 * 3 * totals.length;
		long eqTot = 0;
		for (int i = 0; i < totals.length; i++) {
			//if ( totals[i].count > 0 ) {
				//totals[i].totalEquity = (totals[i].totalEquity*10000/totals[i].count);
				//totals[i].totalEquity /= totalInc;
				//totals[i].ties /= totalInc;
				eqTot += totals[i].totalEquity;
			//} else 
				//totals[i].totalEquity = 0;
		}
		long tot = 0;
		for (int i = 0; i < totals.length; i++) {
			tot = totals[i].count;
			totals[i].totalEquity = totals[i].totalEquity*100000/eqTot;
			CardFightResult result = new CardFightResult();
			result.equity = String.valueOf(  ((double)(totals[i].totalEquity))/1000  );
			result.winPct = String.valueOf(  ((double)(totals[i].wins*100000/tot))/1000  );
			result.tiePct = String.valueOf(  ((double)(totals[i].ties*100000/tot/totalInc)) /1000);
			System.out.print( totals[i].id + "\t" + totals[i].totalEquity +"\t" + totals[i].count + 
					"\tW%"+ result.winPct + "\tT%"+ result.tiePct + "\tEq%"+ result.equity+"\t");
			results.add(result); 
		}
		long time2 = System.currentTimeMillis();
		CardFightSummary summary = new CardFightSummary();
		summary.setResults(results);
		summary.setResultType(summary.ENUMERATION);
		summary.setCount(tot);
		summary.setTime(time2-time);
		
		return summary;
	}
	
	private CardFightSummary calculateComplexRange(ArrayList<String> playersHands, String board, String discard) {
		ArrayList<CardFightResult> results;
		CardFightSummary summary;
		ArrayList<String> hands;
		ArrayList<ArrayList<String>> handRanges = new ArrayList<ArrayList<String>>(playersHands.size());
		long time = System.currentTimeMillis();
		
		int bsize = board.length() / 2;
		//System.out.println(" bsize: "+bsize);
		int dsize = discard.length() / 2;
		int psize = playersHands.size() * 2;
		long boards = choose((long)52-bsize-dsize-psize, (long)5-bsize);
		long tot = 0;
		
		long permCount = 1;
		for ( String range : playersHands ) {
			hands = parseCardInputAsString(range);
			System.out.println("range: "+ range+ " hands: "+hands);
			handRanges.add(hands);
			if ( permCount < 20000000l )  // already too many perms
				permCount *= hands.size();
		}
		System.out.println("permutations= "+permCount);

		if ( permCount < 100000 ) {
			ArrayList<ArrayList<String>> handCombos    = null;
			ArrayList<ArrayList<String>> newHandCombos = null;
			for ( ArrayList<String> hands2 : handRanges ) {
				if ( handCombos == null ) {
					newHandCombos = new ArrayList<ArrayList<String>>(hands2.size());
					for ( String aHand: hands2 ){
						ArrayList<String> newList = new ArrayList<String>();
						newList.add(aHand);
						newHandCombos.add(newList);
					}
				} else {
					newHandCombos = new ArrayList<ArrayList<String>>(hands2.size() * handCombos.size());
					for ( String aHand: hands2 ){
						for ( int count = 0; count < handCombos.size(); count++ ) {
							ArrayList<String> newList = new ArrayList<String>();
							newList.addAll(handCombos.get(count));
							newList.add(aHand);
							newHandCombos.add(newList);
						}
					}
				}
				handCombos = newHandCombos;
			}

			newHandCombos = new ArrayList<ArrayList<String>>();
			for( ArrayList<String> handList : handCombos ) {
				if ( !HandCompression.hasOverlap(handList) )
					newHandCombos.add(handList);
			}
			//System.out.println("combos: " +newHandCombos);
			System.out.println("combos size: " +newHandCombos.size());

			if ( (board == null || board.equals("")) && (discard == null || discard.equals("")) ) {
				//results =  CalculateStats.calculateWithSummation2(newHandCombos, board, discard);
				tot = boards *  newHandCombos.size() / 20;
			} else {
				//results = CalculateStats.calculateWithSummation(newHandCombos, board, discard);
				tot = boards * newHandCombos.size();
			}
			System.out.println("\nboards: "+ boards+" tot: "+tot/1000000);
			if ( tot > 3000000000l ) {
				boards = choose((long)52-bsize-dsize, (long)5-bsize);
				int numRand = (int)Math.min(240000000l/boards, (long)newHandCombos.size());
				summary = CalculateStats.calculateRandomWithSummation(newHandCombos, board, discard, numRand);
				System.out.println("\nnumRand: "+ numRand);
				summary.setResultType(summary.MONTECARLO);
			} else if ( (board == null || board.equals("")) && (discard == null || discard.equals("")) ) {
				summary =  CalculateStats.calculateWithSummation2(newHandCombos, board, discard, playersHands.size());
				System.out.println();
				summary.setResultType(summary.ENUMERATION);
			} else {
				summary = CalculateStats.calculateWithSummation(newHandCombos, board, discard);
				System.out.println();
				summary.setResultType(summary.ENUMERATION);
			}
		} else {
			boards = choose((long)52-bsize-dsize, (long)5-bsize);
			int numRand = (int) (300000000l/boards);
			numRand = Math.min( 1000000, numRand);
			tot = boards * numRand;
			System.out.println("\nboards: "+ boards+" tot: "+tot/1000000+"\n");
			summary = CalculateStats.calculateFullRandomWithSummation(handRanges, board, discard, numRand);
			System.out.println("\nnumRand: "+ numRand);
			summary.setResultType(summary.MONTECARLO);
		}
		long time2 = System.currentTimeMillis();
		summary.setTime(time2-time);
		System.out.println("Elapsed time: " + (time2-time));
		System.out.println("Res Count: "+summary.getCount());
		System.out.println("Status: "+ summary.getResultTypeString() + " calculations "+summary.getCount()+ " elapsed time "+(summary.getTime() / 1000) + "seconds");
		return summary;
	}
	
	private long choose(long n, long k) {
		if ( k == 0 ) 
			return 1;
		//System.out.println("n: "+n+" k: "+k);
		//System.out.println("n: "+fact(n));
		//System.out.println("k: "+fact(k));
		//System.out.println("n-k: "+fact(n-k));
		//return fact(n) / fact(k) / fact(n-k);

		return prod(n, n-k) / fact(k);
	}
	
	private long prod(long n, long nMinusk) {
		long sum = n;
		for (n--; n >= nMinusk; n--)
			sum *= n;
		return sum;
	}
	
	private long fact(long n) {
		long sum = n;
		for (n--; n >= 2; n--)
			sum *= n;
		return sum;
	}
	
	private ArrayList<CardCollection> parseCardInput(String range) {
		ArrayList<CardCollection> hands = new ArrayList<CardCollection>();

		ArrayList<String> cards = RangeUtils.parseCards(range);
		if ( cards != null ) {
			if ( cards.size() == 2 ) {
				Card card1 = Card.create(cards.get(0));
				Card card2 = Card.create(cards.get(1));
				CardCollection cc = new CardCollection();
				cc.add(card1);
				cc.add(card2);
				hands.add(cc);
				return hands;
			} else {
				return null;
			}
		} 
		HashSet<String> ranges = RangeUtils.parseRange(range);
		for ( String hand: ranges) {
			// Suppress "o" for offsuit in HandRanges
			if ( hand.length() == 3 && hand.substring(2,3).equals("o") )
				hand = hand.substring(0,2); 
			hands.addAll(HandRange.enumerateCards(hand));
		}
		return hands;
	}
	
	private ArrayList<String> parseCardInputAsString(String range) {
		ArrayList<String> hands = new ArrayList<String>();

		ArrayList<String> cards = RangeUtils.parseCards(range);
		if ( cards != null ) {
			if ( cards.size() == 2 ) {
				String hand = cards.get(0) + cards.get(1);
				hands.add(hand);
				return hands;
			} else {
				return null;
			}
		} 
		HashSet<String> ranges = RangeUtils.parseRange(range);
		ArrayList<CardCollection> col = new ArrayList<CardCollection>();
		for ( String hand: ranges) {
			// Suppress "o" for offsuit in HandRanges
			if ( hand.length() == 3 && hand.substring(2,3).equals("o") )
				hand = hand.substring(0,2); 
			col.addAll(HandRange.enumerateCards(hand));
		}
		for (CardCollection cc: col) 
			hands.add(cc.toString());
		
		return hands;
	}

}

