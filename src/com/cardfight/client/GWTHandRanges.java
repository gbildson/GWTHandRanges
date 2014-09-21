package com.cardfight.client;

import com.cardfight.client.img.GWTImages;
import com.cardfight.client.img.GWTImageBundle;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.IncompatibleRemoteServiceException;
import com.google.gwt.user.client.rpc.InvocationException;
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
//import java.awt.datatransfer.Clipboard;
//import java.awt.datatransfer.DataFlavor;
//import java.awt.datatransfer.StringSelection;
//import java.awt.Toolkit;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class GWTHandRanges implements EntryPoint, ClickListener{
	int             currentNumPlayers = 0;
	HorizontalPanel playerNum[];
	VerticalPanel playerNumParent[];

	FlowPanel   playerStats[];

    AbsolutePanel   aPanel;
    RadioButton radioButton2;
    RadioButton radioButton3;
    RadioButton radioButton4;
    RadioButton radioButton5;
    Button      calculateOdds;
    Button      clearButton;
    HashMap <PushButton, String> deck        = new HashMap <PushButton,String>();
    HashMap <String, PushButton> deckBypass  = new HashMap <String,PushButton>();
    HashMap <PushButton, String> dealt       = new HashMap <PushButton,String>();
    HashMap <String, PushButton> hiddenCards = new HashMap <String, PushButton>();
    HashMap <String, Image>      dealtImages = new HashMap <String, Image>();
    HashMap <String, String>     dealtValues = new HashMap <String, String>();
    boolean dirty = false;
    
    private VerticalPanel   urlp;
    private Label           qbox;
    private Button          ucopy;
    private Button          ulaunch;
    private RootPanel       content;
    
	private static String rank[] = {"A", "K", "Q", "J", "T", "9", "8", "7", "6", "5", "4", "3", "2"};
	private static String suit[] = {"s", "h", "d", "c"};
	private static HashSet<String> rankSet = null;
	private static HashSet<String> suitSet = null;

	static { 
		rankSet = new HashSet<String>();
		for (int i = 0; i < rank.length; i++)
			rankSet.add(rank[i]);
		suitSet = new HashSet<String>();
		for (int i = 0; i < suit.length; i++)
			suitSet.add(suit[i]);
	}

  /**
   * This is the entry point method.
   */
  public void onModuleLoad() {
    //Image img = new Image("http://code.google.com/webtoolkit/logo-185x175.png");
    //Button button = new Button("Click me");
    
    // We can add style names
    //button.addStyleName("pc-template-btn");
    // or we can set an id on a specific element for stylingo
    //img.getElement().setId("pc-template-img");
    
    //VerticalPanel vPanel = new VerticalPanel();
    //vPanel.setWidth("100%");
    //vPanel.setHorizontalAlignment(VerticalPanel.ALIGN_CENTER);
    //vPanel.add(img);
    //vPanel.add(button);
    
    Grid g = new Grid(2, 2);
    g.addStyleName("pc2-color-grid");
	final Image[][] imgSmallCards = {
			{new Image(),new Image(),new Image(),new Image(),new Image(),new Image(),new Image(),new Image(),new Image(), new Image(),new Image(),new Image(),new Image()}, 
			{new Image(),new Image(),new Image(),new Image(),new Image(),new Image(),new Image(),new Image(),new Image(), new Image(),new Image(),new Image(),new Image()}, 
			{new Image(),new Image(),new Image(),new Image(),new Image(),new Image(),new Image(),new Image(),new Image(), new Image(),new Image(),new Image(),new Image()}, 
			{new Image(),new Image(),new Image(),new Image(),new Image(),new Image(),new Image(),new Image(),new Image(), new Image(),new Image(),new Image(),new Image()} 
	};
	
	for( int j=0; j<4; j++ ) {
		for( int k=0; k<13; k++ ) {
	        //imgSmallCards[j][k].setUrl( "img/"+rank[k]+suit[j]+"50.gif" );
			imgSmallCards[j][k] = GWTImageBundle.getImage(rank[k]+suit[j]+"50");
			//GWTImageBundle.getProto(rank[k]+suit[j]+"50").applyTo(imgSmallCards[j][k]);
	        //imgSmallCards[j][k].setVisibleRect(1, 2, 12, 33);
		}
	}
	//HorizontalPanel[] vp = new HorizontalPanel[4];
    HorizontalPanel hPanel = null;
	
	for( int j=0; j<4; j++ ){
		for( int k=0; k<13; k++ ) {
			int nj = j / 2;
			int nk = ((j*13 + k) / 13) % 2;
			if ( k % 13 == 0 ){ 
			    hPanel = new HorizontalPanel();
			    hPanel.setWidth("100%");
			    hPanel.setHorizontalAlignment(HorizontalPanel.ALIGN_CENTER);
				hPanel.addStyleName("pc2-template-grid");
			    g.setWidget( nj, nk,  hPanel);
			}
			PushButton pb = new PushButton(imgSmallCards[j][k]);
			pb.addStyleName("pc2-template-btn");
		    pb.addClickListener(this);
		    deck.put(pb, rank[k]+suit[j]);
		    deckBypass.put(rank[k]+suit[j], pb);
		    hPanel.add(pb);
		}
	}
	
    HorizontalPanel numPlayerPanel = new HorizontalPanel();
	numPlayerPanel.setVerticalAlignment(HorizontalPanel.ALIGN_MIDDLE);
    Label numLabel;
    numPlayerPanel.add(numLabel = new Label("Number of Players:"));
    numLabel.addStyleName("inp-text");
    radioButton2 = new RadioButton("players", "2");
    radioButton2.setChecked(true);
    radioButton3 = new RadioButton("players", "3");
    radioButton4 = new RadioButton("players", "4");
    radioButton5 = new RadioButton("players", "5");
    numPlayerPanel.add( radioButton2 );
    numPlayerPanel.add( radioButton3 );
    numPlayerPanel.add( radioButton4 );
    numPlayerPanel.add( radioButton5 );
    radioButton2.addClickListener(this);
    radioButton3.addClickListener(this);
    radioButton4.addClickListener(this);
    radioButton5.addClickListener(this);
    /*
    radioButton2.addClickListener(new ClickListener() {
        public void onClick(Widget sender) {
          System.out.println("2 players");
        }
     });
    radioButton3.addClickListener(new ClickListener() {
        public void onClick(Widget sender) {
          System.out.println("3 players");
        }
     });
    radioButton4.addClickListener(new ClickListener() {
        public void onClick(Widget sender) {
          System.out.println("4 players");
        }
     });
    radioButton5.addClickListener(new ClickListener() {
        public void onClick(Widget sender) {
          System.out.println("5 players");
        }
     });
    */
    calculateOdds = new Button("Calculate");
    calculateOdds.setStyleName("inp-but");
    calculateOdds.addClickListener(this);
    numPlayerPanel.add( calculateOdds );
    clearButton = new Button("Clear");
    clearButton.setStyleName("inp-but");
    clearButton.addClickListener(this);
    numPlayerPanel.add( new Label("    ") );
    numPlayerPanel.add( clearButton );
    //numPlayerPanel.setSize("450px", "28px");
    numPlayerPanel.setStyleName("inp-pnl");
    
    
    DockPanel dock = new DockPanel();
    //dock.setStyleName("cw-DockPanel");
    //dock.setSpacing(4);
    dock.setHorizontalAlignment(DockPanel.ALIGN_CENTER);
    // Add text all around
    Label selectCardLabel = new Label("Select Cards");
    selectCardLabel.setStyleName("ccl");
    dock.add(selectCardLabel, DockPanel.NORTH);
    dock.add(numPlayerPanel, DockPanel.SOUTH);
    //dock.add(new HTML(constants.cwDockPanelEast()), DockPanel.EAST);
    //dock.add(new HTML(constants.cwDockPanelWest()), DockPanel.WEST);
    //dock.add(new HTML(constants.cwDockPanelNorth2()), DockPanel.NORTH);
    dock.add(g, DockPanel.SOUTH);
    dock.addStyleName("pc2-color-dock");
    FlowPanel flow = new FlowPanel();
    flow.add(dock);

    flow.addStyleName("pc2-color-tp");
   
    
    aPanel = new AbsolutePanel();
    aPanel.setWidth("100%");
    //aPanel.add(img, 5, 5);
    //aPanel.add(button, 45, 45);
    //aPanel.add(g, 160, 290);
    //aPanel.add(numPlayerPanel, 180, 390);
    aPanel.add(flow, 160,300);
    aPanel.setSize("750px", "500px");
    aPanel.setStyleName("cb");
    
    
    setNumPlayers(2);
    buildCommonCard();

    // Build a url display
    urlp = new VerticalPanel();
    //urlp.setHorizontalAlignment(VerticalPanel.ALIGN_CENTER);
    HorizontalPanel urlline = new HorizontalPanel();
    urlline.setHorizontalAlignment(VerticalPanel.ALIGN_CENTER);
    Label ulabel = new Label("URL:   ");
    ulabel.setStyleName("ulabel");
    qbox = new Label();
    //qbox.setText("http://www.cardfight.com/hr"+qstring);
    qbox.setWidth("420px");
    qbox.setHorizontalAlignment(VerticalPanel.ALIGN_LEFT);
    urlline.setStyleName("urlp");
    urlline.add(ulabel);
    urlline.add(new Label("   "));
    urlline.add(qbox);
    urlp.add(urlline);
    HorizontalPanel urlcntrl = new HorizontalPanel();
    ucopy   = new Button("Copy to clipboard");
    ulaunch = new Button("Open in new window");
    ucopy.setStyleName("ubutton");
    ulaunch.setStyleName("ubutton");
    ucopy.addClickListener(this);
    ulaunch.addClickListener(this);
    urlp.setHorizontalAlignment(VerticalPanel.ALIGN_CENTER);
    urlcntrl.add(ucopy);
    urlcntrl.add(ulaunch);
    urlp.add(urlcntrl);
    urlp.setVisible(false);
    aPanel.add(urlp, 160, 440);

    // Add image and button to the RootPanel
    //RootPanel.get().add(aPanel);
    content = RootPanel.get("content");
    if (content != null) {
        content.add(aPanel);
    }
    
    parseURL();
    	
    // Create the dialog box
    //final DialogBox dialogBox = new DialogBox();
    //dialogBox.setText("Welcome to GWT!");
    //dialogBox.setAnimationEnabled(true);
    //Button closeButton = new Button("close");
    //VerticalPanel dialogVPanel = new VerticalPanel();
    //dialogVPanel.setWidth("100%");
    //dialogVPanel.setHorizontalAlignment(VerticalPanel.ALIGN_CENTER);
    //dialogVPanel.add(closeButton);

    //closeButton.addClickListener(new ClickListener() {
      //public void onClick(Widget sender) {
        //dialogBox.hide();
      //}
    //});

    // Set the contents of the Widget
    //dialogBox.setWidget(dialogVPanel);
    
    //button.addClickListener(new ClickListener() {
     // public void onClick(Widget sender) {
        //dialogBox.center();
        //dialogBox.show();
      //}
    //});

  }
  
  private void setURLInfo(ArrayList<String> playerHands, String commonCards) {
	  String qstring = "";
	  for( String hand : playerHands )
		  qstring += hand + "+";

	  if ( commonCards.length() > 0 ){
		  qstring += commonCards;
	  } else {
		  qstring = qstring.substring(0, qstring.length()-1);
	  }
	  urlp.setVisible(true);
	  String fqstring = "http://www.cardfight.com/hr?"+qstring;
	  qbox.setText(fqstring);
	  
	    //final Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
	    //StringSelection data = new StringSelection(fqstring);
	    //clipboard.setContents(data, data);
	    //setClipboard(fqstring);
  }
  
  
  public native static void setClipboard(String str)/*-{
  	  window.clipboardData.setData("Text",str); 
}-*/;
  
  private static native void open(String url, String name, String features) /*-{
	      $wnd.open(url, name, features);
  }-*/;
  
  private void parseURL() {
	  String qstring=Window.Location.getQueryString();
	  System.out.println("q="+qstring);
	  if ( qstring == null || qstring.length() < 3)
		  return;
	  if ( qstring.startsWith("?") )
		  qstring = qstring.substring(1);
	  System.out.println("q2="+qstring);

	  String []handlist=qstring.split("\\+");
	  ArrayList<String> hands = new ArrayList<String>();
	  //String commonCards = null;
	  int numHands=0;
	  //String allCards="";
	  for ( int i = 0; i < handlist.length; i++) {
		  String cardstr = handlist[i];
		  System.out.println("q3="+cardstr);

		  if ( cardstr.length() == 4 ) {
			  numHands++;
			  ArrayList<String> hand=parseCards(cardstr);
			  System.out.println("hs="+hand.size());
			  if (hand == null || hand.size() != 2)
				  return;
			  hands.addAll(hand);
			  //allCards += hand.get(0);
			  //System.out.println("allCards="+allCards);
		  } else if ( cardstr.length() >= 6 ) {
			  ArrayList<String> hand=parseCards(cardstr);
			  if (hand == null)
				  break;
			  //commonCards = hand.get(0);
			  hands.addAll(hand);
			  //allCards += hand.get(0);
			  break;
		  }
	  }
	  System.out.println("numHands="+numHands);

	  if ( numHands >= 2 && numHands <= 5 ) {
		  if ( numHands == 2 )
			  radioButton2.setChecked(true);
		  else if ( numHands == 3 )
			  radioButton3.setChecked(true);
		  else if ( numHands == 4 )
			  radioButton4.setChecked(true);
		  else if ( numHands == 5 )
			  radioButton5.setChecked(true);
		  removePlayersHands(numHands);
		  clearPlayerStats();
		  setNumPlayers(numHands);
		  for (String card: hands) {
			  //String card = allCards.substring(0,2);
			  setCard(card);
			  System.out.println("setCard="+card);

			  //allCards = allCards.substring(2);
		  }
		  if ( validateInputs() ) {
		      clearPlayerStats();
		      doCalculate();
		  }
	  }
  }
  
  public static ArrayList<String> parseCards(String cards) {
	  ArrayList<String> results = new ArrayList<String>();
	  cards = cards.replaceAll(" ", "");
	  cards = cards.replaceAll(",", "");
	  cards = cards.replaceAll(";", "");
	  
	  String c1;
	  String c2;

	  while (cards.length() > 0) {
		  c1 = cards.substring(0,1);
		  c1 = c1.toUpperCase();
		  c2 = cards.substring(1,2);
		  c2 = c2.toLowerCase();
		  if ( rankSet.contains(c1) && suitSet.contains(c2) ) {
			  results.add(c1+c2);
		  } else
			  return null;
		  cards = cards.substring(2);
	  }
	  return results;
  }
  
  private void setCard(String card) {
	  PushButton sender = deckBypass.get(card);
      //val = deck.get(sender);
	  System.out.println("Deck card :"+card);
	  String name = findDealableSlot();
	  if ( name != null ) {
		  clearPlayerStats();
		  hiddenCards.put(card, ((PushButton)sender));
		  ((PushButton)sender).setEnabled(false);
		  dealtValues.put(name, card);
		  Image dealtImg = dealtImages.get(name);
		  dealtImg.setUrl("img/"+card+"50.gif");
	  }
  }
  
  public void onClick(Widget sender) {
	  String val;
	  if (sender == radioButton2) {
		  System.out.println("2 players");
		  if (currentNumPlayers != 2) {
			  removePlayersHands(2);
			  clearPlayerStats();
			  setNumPlayers(2);
		  }
	  } else if (sender == radioButton3) {
		  System.out.println("3 players");
		  if (currentNumPlayers != 3) {
		      removePlayersHands(3);
		      clearPlayerStats();
		      setNumPlayers(3);
		  }
	  } else if (sender == radioButton4) {
		  System.out.println("4 players");
		  if (currentNumPlayers != 4) {
			  removePlayersHands(4);
			  clearPlayerStats();
			  setNumPlayers(4);
		  }
	  } else if (sender == radioButton5) {
		  System.out.println("5 players");
		  if (currentNumPlayers != 5) {
			  removePlayersHands(5);
			  clearPlayerStats();
			  setNumPlayers(5);
		  }
	  }  else if (sender == calculateOdds) {
		  System.out.println("Calculate some frickin odds");
		  if ( validateInputs() ) {
		      clearPlayerStats();
		      doCalculate();
		  }
	  }  else if (sender == clearButton) {
		  System.out.println("Clear table");
		  clearPlayerStats();
		  returnAllToDeck();
	  } else if ( (val = deck.get(sender)) != null ) {
		  System.out.println("Deck card :"+val);
		  String name = findDealableSlot();
		  if ( name != null ) {
			  clearPlayerStats();
			  hiddenCards.put(val, ((PushButton)sender));
			  ((PushButton)sender).setEnabled(false);
			  dealtValues.put(name, val);
			  Image dealtImg = dealtImages.get(name);
			  dealtImg.setUrl("img/"+val+"50.gif");
		  }
	  } else if ( (val = dealt.get(sender)) != null ) {
		  System.out.println("Dealt card :"+val);
		  if ( returnToDeck(val) ) 
			  clearPlayerStats();
	  } else if ( sender == ucopy ) {
		  //String fqstring = "http://www.cardfight.com/hr?"+qstring;
		  setClipboard(qbox.getText());
	  } else if ( sender == ulaunch ) {
		  //String fqstring = "http://www.cardfight.com/hr?"+qstring;
		  open(qbox.getText(), "_blank", "");
	  } else {
		  System.out.println("Implement me: "+sender);
	  }
  }
  
  private static class Point {
	  public Point(int x, int y) {
		  this.x = x;
		  this.y = y;
	  }
	  public int x;
	  public int y;
  }
  private static Point playerLoc[] = new Point[5];
  static {
		// Small card locations
		//smallCardLocs[0] = new Point(540, 90);
		//smallCardLocs[1] = new Point(600, 115);
		//smallCardLocs[2] = new Point(675, 190);
		//smallCardLocs[3] = new Point(588, 270);
		//smallCardLocs[4] = new Point(427, 335);
		//smallCardLocs[5] = new Point(352, 335);
		//smallCardLocs[6] = new Point(187, 270);
		//smallCardLocs[7] = new Point(100, 190);
		//smallCardLocs[8] = new Point(176, 115);
		//smallCardLocs[9] = new Point(237, 90);
		//playerLocs[0] = new Point(480, 7);
		//playerLocs[1] = new Point(620, 65);
		//playerLocs[2] = new Point(700, 170);
		//playerLocs[3] = new Point(600, 280);
		//playerLocs[4] = new Point(450, 330);
		//playerLocs[5] = new Point(270, 330);
		//playerLocs[6] = new Point(115, 280);
		//playerLocs[7] = new Point(20, 170);
		//playerLocs[8] = new Point(100, 65);
		//playerLocs[9] = new Point(240, 7);
	  playerLoc[0] = new Point(110,  115);
	  playerLoc[1] = new Point(230, 57);
	  playerLoc[2] = new Point(350, 40);
	  playerLoc[3] = new Point(470, 57);
	  playerLoc[4] = new Point(590, 115);
  }
  
  private void setNumPlayers(int num) {
	  currentNumPlayers = num;
	  if (playerNum == null) {
		  playerNumParent = new VerticalPanel[5];
		  playerNum = new HorizontalPanel[5];
		  for (int i = 0; i < 5; i++) {
			  playerNum[i] = new HorizontalPanel();
			  playerNumParent[i] = new VerticalPanel();
			  playerNumParent[i].setHorizontalAlignment(VerticalPanel.ALIGN_CENTER);
			  playerNum[i].addStyleName("pc3-template-pnl");
			  Image  cardImg1 = new Image("img/blank_card.gif");
			  Image  cardImg2 = new Image("img/blank_card.gif");
			  PushButton cardButton1 = new PushButton(cardImg1);
			  PushButton cardButton2 = new PushButton(cardImg2);
			  cardButton1.addStyleName("pc2-template-btn");
			  cardButton2.addStyleName("pc2-template-btn");
			  cardButton1.addClickListener(this);
			  cardButton2.addClickListener(this);
			  dealt.put(cardButton1, "P"+i+"C1");
			  dealt.put(cardButton2, "P"+i+"C2");
			  dealtImages.put("P"+i+"C1", cardImg1);
			  dealtImages.put("P"+i+"C2", cardImg2);

			  playerNum[i].add(cardButton1);
			  playerNum[i].add(cardButton2);
			  playerNumParent[i].add(playerNum[i]);
			  if ( i >= num ) {
				  playerNumParent[i].setVisible(false);
			  }
			  aPanel.add(playerNumParent[i], playerLoc[i].x, playerLoc[i].y);
		  }
	  } else {
		  for (int i = 0; i < 5; i++) {
		      playerNumParent[i].setVisible(i < num);
		  }
	  }
  }
  
  private void setPlayerStats(CardFightSummary summary) {
	  ArrayList<CardFightResult> results = summary.getResults();
	  playerStats = new FlowPanel[results.size()];
	  for (int i = 0; i < results.size(); i++) {
		  CardFightResult result = results.get(i);
		  playerStats[i] = new FlowPanel();
		  playerStats[i].addStyleName("pc2-white-result");
		  VerticalPanel vp = new VerticalPanel();
		  vp.addStyleName("cf-results-area");
		  Label eqLabel  = new Label("Equity: "+ result.equity+ "%");
		  Label winLabel = new Label("Win: "+ result.winPct+ "%");
		  Label tieLabel = new Label("Tie: "+ result.tiePct+ "%");

		  vp.add(eqLabel);
		  vp.add(winLabel);
		  vp.add(tieLabel);
		  playerStats[i].add(vp);

		  //aPanel.add(playerStats[i], playerLoc[i].x, playerLoc[i].y+75);
		  playerNumParent[i].add(playerStats[i]);
	  }
  }
  
  private void clearPlayerStats() {
	  dirty = true;
	  urlp.setVisible(false);
	  if ( playerStats != null ) {
		  for (int i = 0; i < playerStats.length; i++) {
			  playerNumParent[i].remove(playerStats[i]);
		  }
		  playerStats = null;
	  }
  }
  
  private boolean validateInputs()  {
	  // Validate that players cards are there
      for (int i = 0; i < currentNumPlayers; i++) {
    	  String hand = "";
		  for (int j = 0; j < 2; j++) {
			  String name = "P"+i+"C"+(j+1);
			  String val = dealtValues.get(name);
			  if ( val == null ) {
				  createErrorMsg("Please ensure that all players have cards.");
				  return false;
			  }
		  }
      }
      
      // Validate that we don't have a partial flop
      String val;
      int missingCount = 0;
	  for (int i = 0; i < 3; i++) {
		  String name = "F"+i;
		  val = dealtValues.get(name);
		  if ( val == null ) 
			  missingCount++;
      }
	  if (missingCount != 0 && missingCount != 3) {
		  createErrorMsg("If any flop cards are specified, all three must be chosen.");
		  return false;
	  }
	  if (!dirty) {
	      createErrorMsg("Nothing has changed to require a new calculation.");
	      return false;
	  }
	  
	  return true;
  }

  
  private void buildCommonCard() {
	  HorizontalPanel commonCards = new HorizontalPanel();
	  HorizontalPanel flop = new HorizontalPanel();
	  flop.addStyleName("pc3-template-pnl");
	  flop.setVerticalAlignment(HorizontalPanel.ALIGN_MIDDLE);
	  Label flabel;
	  flop.add(flabel = new Label("Flop"));

	  HorizontalPanel turn = new HorizontalPanel();
	  turn.addStyleName("pc3-template-pnl");
	  turn.setVerticalAlignment(HorizontalPanel.ALIGN_MIDDLE);
	  Label tlabel;
	  turn.add(tlabel = new Label("Turn"));
	  flabel.addStyleName("ccl");
	  tlabel.addStyleName("ccl");

	  for (int i = 0; i < 4; i++) {
		  //playerNum[i].addStyleName("pc3-template-pnl");
		  Image  cardImg1 = new Image("img/blank_card.gif");
		  PushButton cardButton1 = new PushButton(cardImg1);
		  cardButton1.addStyleName("pc2-template-btn");
		  cardButton1.addClickListener(this);
		  ((i>=3) ? turn : flop).add(cardButton1);
		  String name;
		  if ( i < 3 )
			  name = "F"+i;
		  else
			  name = "T0";
		  dealt.put(cardButton1, name);
		  dealtImages.put(name, cardImg1);
      }
	  commonCards.add(flop);
	  commonCards.add(turn);
	  aPanel.add(commonCards, 260, 220);
  }
  
  private String findDealableSlot() {
	  String val;
	  for (int i = 0; i < currentNumPlayers; i++) {
		  for (int j = 0; j < 2; j++) {
			  String name = "P"+i+"C"+(j+1);
			  val = dealtValues.get(name);
			  if ( val == null ) 
				  return name;
		  }
	  }
	  for (int i = 0; i < 4; i++) {
		  String name;
		  if ( i < 3 )
			  name = "F"+i;
		  else
			  name = "T0";
		  val = dealtValues.get(name);
		  if ( val == null ) 
			  return name;
      }
	  return null;
  }
  
  private void removePlayersHands(int newNum) {
      if ( newNum >= currentNumPlayers )
    	  return;
      
	  //String val;
      for (int i = currentNumPlayers - 1; i >= newNum; i--) {
		  for (int j = 0; j < 2; j++) {
			  String name = "P"+i+"C"+(j+1);
			  returnToDeck(name);
			  /*
			  val = dealtValues.get(name);
			  if ( val != null ) {
				  PushButton pb = hiddenCards.remove(val);
				  pb.setEnabled(true);
				  dealtValues.remove(name);
				  Image dealtImg = dealtImages.get(name);
				  dealtImg.setUrl("img/blank_card.gif");
			  }
			  */
		  }
      }
  }
  
  private ArrayList<String> getPlayerHands() {
      ArrayList<String> results = new ArrayList<String>();
      
      for (int i = 0; i < currentNumPlayers; i++) {
    	  String hand = "";
		  for (int j = 0; j < 2; j++) {
			  String name = "P"+i+"C"+(j+1);
			  String val = dealtValues.get(name);
			  if ( val == null )
				  return null;
			  hand += val;
			  if ( j == 1 ) {
				  results.add(hand);
			  }
		  }
      }
      return results;
  }
  
  private String getCommonCards() {
      String results = "";
      
      String val;
	  for (int i = 0; i < 4; i++) {
		  String name;
		  if ( i < 3 )
			  name = "F"+i;
		  else
			  name = "T0";
		  val = dealtValues.get(name);
		  if ( val == null ) 
			  return results;
		  results += val;
      }
      return results;
  }
  
  private void returnAllToDeck() {
	  String name;
      for (int i = 0; i < currentNumPlayers; i++) {
		  for (int j = 0; j < 2; j++) {
			  name = "P"+i+"C"+(j+1);
			  returnToDeck(name);
		  }
      }
	  for (int i = 0; i < 4; i++) {
		  if ( i < 3 )
			  name = "F"+i;
		  else
			  name = "T0";
		  returnToDeck(name);
      }
  }
  
  private boolean returnToDeck(String name) {
	  String val = dealtValues.get(name);
	  if ( val != null ) {
		  PushButton pb = hiddenCards.remove(val);
		  pb.setEnabled(true);
		  dealtValues.remove(name);
		  Image dealtImg = dealtImages.get(name);
		  dealtImg.setUrl("img/blank_card.gif");
		  return true;
	  }
	  return false;
  }
  
  public void doCalculate() {
	  // (1) Create the client proxy. Note that although you are creating the
	  // service interface proper, you cast the result to the asynchronous
	  // version of the interface. The cast is always safe because the 
	  // generated proxy implements the asynchronous interface automatically.
	  //
	  CardFightServiceAsync cardFightService = (CardFightServiceAsync) GWT.create(CardFightService.class);

	  // (2) Create an asynchronous callback to handle the result.
	  //
	  AsyncCallback<CardFightSummary> callback = new AsyncCallback<CardFightSummary>() {
	    public void onSuccess(CardFightSummary summary) {
	      // do some UI stuff to show success
	        //System.out.println("res: "+result);
	        setPlayerStats(summary);
	        dirty = false;
	    }

	    public void onFailure(Throwable caught) {
	      // do some UI stuff to show failure
	        // Convenient way to find out which exception was thrown.
	        try {
	            throw caught;
	        } catch (IncompatibleRemoteServiceException e) {
	            // this client is not compatible with the server; cleanup and refresh the 
	            // browser
	            System.out.println("Not Good :" +e);
	        } catch (InvocationException e) {
	            // the call didn't complete cleanly
		        System.out.println("Not Good :" +e);
	        } catch (Throwable e) {
	            // last resort -- a very unexpected exception
			    System.out.println("Not Good :" +e);
	        }
	    }
	  };

	  // (3) Make the call. Control flow will continue immediately and later
	  // 'callback' will be invoked when the RPC completes.
	  //
	  //ArrayList<CardFightResult> calculate(ArrayList<String> playersHands, String board, String discard)
	  ArrayList<String> playerHands = getPlayerHands();
	  if ( playerHands == null ) {
		  System.out.println("Implement player card error");
	  }
	  String commonCards = getCommonCards();
	  if ( commonCards.length() > 0 && commonCards.length() < 6 ){
		  System.out.println("Implement partial flop error");
	  }
	  setURLInfo(playerHands, commonCards);
	  String discard = "";
	  cardFightService.calculate(playerHands, commonCards, discard, callback);
	}
  
    private void createErrorMsg(String msg) {
        // Create the dialog box
        final DialogBox dialogBox = new DialogBox();
        dialogBox.setText("Input Error");
        //dialogBox.setStyleName("error-window");
        dialogBox.setAnimationEnabled(true);
        Button closeButton = new Button("close");
        closeButton.setStyleName("error-but");
        VerticalPanel dialogVPanel = new VerticalPanel();
        dialogVPanel.setWidth("100%");
        dialogVPanel.setHorizontalAlignment(VerticalPanel.ALIGN_CENTER);
        Label msgLabel = new Label(msg);
  	    msgLabel.addStyleName("ccl");
  	    dialogVPanel.add(new Label("    "));
        dialogVPanel.add(msgLabel);
        dialogVPanel.add(new Label("    "));
        dialogVPanel.add(closeButton);
        
        dialogBox.center();
        dialogBox.show();

        closeButton.addClickListener(new ClickListener() {
          public void onClick(Widget sender) {
            dialogBox.hide();
          }
        });

        // Set the contents of the Widget
        dialogBox.setWidget(dialogVPanel);
        
        //button.addClickListener(new ClickListener() {
         // public void onClick(Widget sender) {
            //dialogBox.center();
            //dialogBox.show();
          //}
        //});
    }
}
