package poker.gui;

import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;

import javax.swing.plaf.basic.BasicSliderUI;
import javax.swing.plaf.metal.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import poker.*;

public class ActionPanel extends JPanel implements ItemListener, ChangeListener {
	
	private static final Log LOG = LogFactory.getLog(ActionPanel.class);
	
	JPanel cards; //a panel that uses CardLayout
	JTextField jtextfield;
	JSlider jslider;
	ActionCallback actionCallback;
	String raiseLanguage;
	double  raiseAmount;
	JButton b3;
	double  callableAmount;
	double  playerBetAmount;
    final static String BUTTONPANEL = "Card with JButtons";
    final static String TEXTPANEL = "Card with JTextField";
    private JPanel parent;
    private boolean notInitialized = true;
    
    public ActionPanel(JPanel parent) {
    	this.parent = parent;
    }
	
    public void addComponentsToPane() {
        parent.setLayout(null);
        
        if (LOG.isDebugEnabled())
			LOG.debug("***+++ TrackWidth: "+ UIManager.get( "Slider.trackWidth" ));
        if (LOG.isDebugEnabled())
			LOG.debug("***+++ TickLength: "+UIManager.get( "Slider.majorTickLength" ));
        //UIManager.put( "Slider.trackWidth", new Integer(60) );
        
        //Create the panel that contains the "cards".
        cards = new JPanel(new CardLayout());
        cards.setOpaque(false);
    }
    
    public void finalInitialization() {
        Insets insets = parent.getInsets();
        JPanel tpane = this;
        tpane.setLayout(new BorderLayout());
        tpane.add(cards, BorderLayout.CENTER);
        tpane.setBounds(405+insets.left, 450+insets.top, 350, 80);
        tpane.setOpaque(false);
        parent.add(tpane);
        parent.validate();
        notInitialized = false;
    }
    
	public void old_addComponentsToPane(JPanel pane) {
        pane.setLayout(null);
        
        //System.out.println("***+++ TrackWidth: "+ UIManager.get( "Slider.trackWidth" ));
        //System.out.println("***+++ TickLength: "+UIManager.get( "Slider.majorTickLength" ));
        UIManager.put( "Slider.trackWidth", new Integer(60) );
        
        //Put the JComboBox in a JPanel to get a nicer look.
        JPanel comboBoxPane = new JPanel(); //use FlowLayout
        String comboBoxItems[] = { BUTTONPANEL, TEXTPANEL };
        JComboBox cb = new JComboBox(comboBoxItems);
        cb.setEditable(false);
        cb.addItemListener(this);
        comboBoxPane.add(cb);
        comboBoxPane.setOpaque(false);

        JButton b1 = new JButton("Fold");
        JButton b2 = new JButton("Check");
        JButton b3 = new JButton("Raise");
        
        JPanel card2 = new JPanel();
        card2.add(jtextfield = new JTextField("0", 4));
        //card2.add(jtextfield = new JTextField("0", 6));card2.add(jtextfield = new JTextField("0", 6));
        jslider = new JSlider(0, 500, 0);
        jslider.setUI(new GBSliderUI());
        //jslider.updateUI();
        jslider.setMinorTickSpacing(50);
        jslider.setMajorTickSpacing(100);
        jslider.addChangeListener(this);
        JPanel holder = new JPanel();
        holder.setOpaque(false);
        //holder.setPreferredSize(new Dimension(130,20));
        holder.add(jslider);
        card2.add(holder);
        card2.setOpaque(false);
        //jslider.setSize(100,16);
        

        JPanel actionPane = new JPanel();
        actionPane.setLayout(new FlowLayout());
        //actionPane.setBackground(Color.gray);
        actionPane.setOpaque(false);
        actionPane.add(b1);
        actionPane.add(b2);
        actionPane.add(b3);
        
        //Create the panel that contains the "cards".
        Insets insets = pane.getInsets();
        cards = new JPanel(new CardLayout());
        cards.add(actionPane, BUTTONPANEL);
        cards.add(card2, TEXTPANEL);
        cards.setOpaque(false);
        JPanel tpane = this;
        tpane.setLayout(new BorderLayout());
        tpane.add(comboBoxPane, BorderLayout.PAGE_START);
        tpane.add(cards, BorderLayout.CENTER);
        tpane.setBounds(405+insets.left, 450+insets.top, 350, 80);
        tpane.setOpaque(false);
        pane.add(tpane);
 
        //System.out.println("Insets l:"+insets.left+ " t:"+insets.top);
        //Dimension size = b1.getPreferredSize();
        //b1.setBounds(25 + insets.left, 5 + insets.top,
        //             size.width, size.height);
        //size = b2.getPreferredSize();
        //b2.setBounds(55 + insets.left, 40 + insets.top,
        //             size.width, size.height);
        //size = b3.getPreferredSize();
        //b3.setBounds(150 + insets.left, 15 + insets.top,
        //             size.width + 50, size.height + 20);
        
        //
    }
	
	public void takeAction(int bettingMode, String nick, double playerBet, double toCall, double callable, double bigBlind,
			  double maxRaiseIfNoLimit, double raiseIfLimit, double maxRaiseIfPotLimit, double minNonLimitRaise, ActionCallback ac){
		final int _bettingMode = bettingMode;
		final String _nick = nick;
		final double _playerBet = playerBet;
		final double _toCall = toCall;
		final double _callable = callable;
		final double _bigBlind = bigBlind;
		final double _maxRaiseIfNoLimit = maxRaiseIfNoLimit;
		final double _raiseIfLimit = raiseIfLimit;
		final double _maxRaiseIfPotLimit = maxRaiseIfPotLimit;
		final double _minNonLimitRaise = minNonLimitRaise;
		final ActionCallback _ac = ac;
		
        //Schedule a job for the event-dispatching thread:
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	if (notInitialized)
            		addComponentsToPane();
            	real_takeAction(_bettingMode, _nick, _playerBet, _toCall, _callable, _bigBlind,
            	  _maxRaiseIfNoLimit, _raiseIfLimit, _maxRaiseIfPotLimit, _minNonLimitRaise, _ac);
            	if (notInitialized)
            		finalInitialization();
            }
        });
    }
	
	private void real_takeAction(int bettingMode, String nick, double playerBet, double toCall, double callable, double bigBlind,
			  double maxRaiseIfNoLimit, double raiseIfLimit, double maxRaiseIfPotLimit, 
			  double minNonLimitRaise, ActionCallback ac) {
		actionCallback = ac;
		
		
		JLabel  playerLabel = new JLabel("***** "+nick+" ");
		JButton b1;
		JButton b2;
		//JButton b3 = null;

		b1 = new JButton("Fold");
		b1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actionCallback.deliverBettingResponse(-1.0d);
				clearPanel();
			}
		});
		if ( toCall == 0.0d ) {
			b2 = new JButton("Check");
			b2.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					actionCallback.deliverBettingResponse(0.0d);
					clearPanel();
				}
			});
		} else{
			b2 = new JButton("Call "+getChipAmount(callable));
			callableAmount = callable;
			playerBetAmount = playerBet;
			b2.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (LOG.isDebugEnabled())
						LOG.debug("CALLABLE AMOUNT:"+callableAmount+playerBetAmount);
					actionCallback.deliverBettingResponse(callableAmount+playerBetAmount);
					clearPanel();
				}
			});
		}
		
		JPanel raiseControl = null;
		// If you have enough money to raise more than the minimum
		//System.out.println("********* mR:"+maxRaiseIfNoLimit+" rL:"+raiseIfLimit);
		if ( maxRaiseIfNoLimit > raiseIfLimit+0.001d ) {
			if (bettingMode == TexasHoldem.LIMIT) 
				raiseAmount = raiseIfLimit+toCall+playerBet; 
			else {
				raiseAmount = minNonLimitRaise+toCall+playerBet;
				//System.out.println("mNLR:"+minNonLimitRaise+" toC:"+toCall+" raiseAmount:"+raiseAmount);

				// Create a sliding raise control with text input
				raiseControl = new JPanel(new FlowLayout());
				raiseControl.add(jtextfield = new JTextField(getChipAmount(raiseAmount), 5));

				jtextfield.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						raiseAmount = Double.valueOf(jtextfield.getText());
						b3.setText(getRaiseText());
					}
				});

				//card2.add(jtextfield = new JTextField("0", 6));card2.add(jtextfield = new JTextField("0", 6));
				double maxRaise = ( bettingMode == TexasHoldem.POT_LIMIT ? maxRaiseIfPotLimit : maxRaiseIfNoLimit);
				maxRaise += toCall;
				if (LOG.isDebugEnabled())
					LOG.debug("RA:"+((int)raiseAmount)+" MR:"+((int)maxRaise));
				raiseAmount = Math.min(raiseAmount, maxRaise);  // If a raise puts you all in then don't exceed that.
				jslider = new JSlider((int)raiseAmount, (int)maxRaise+1, (int)raiseAmount);  // TODO: Max won't work without+1
				jslider.setUI(new GBSliderUI());
				//jslider.updateUI();
				jslider.setMinorTickSpacing((int) raiseAmount / 2);
				jslider.setMajorTickSpacing((int) raiseAmount);
				jslider.setExtent((int) bigBlind);
				//jslider.setMajorTickSpacing((int)minNonLimitRaise);
				//jslider.setSnapToTicks(true);

				jslider.addChangeListener(this);
				//JPanel holder = new JPanel();
				//holder.setOpaque(false);
				//holder.setPreferredSize(new Dimension(130,20));
				raiseControl.add(jslider);
				//raiseControl.add(holder);
				raiseControl.setOpaque(false);
			}
		} else {
			// raise only what you can
			raiseAmount = raiseIfLimit+toCall+playerBet; 
		}
		//System.out.println("********* rA:"+raiseAmount);

		if (raiseAmount > 0.001d) {
			
			if ( toCall == 0.0d )
				raiseLanguage = "Bet ";
			else
				raiseLanguage = "Raise to ";
			b3 = new JButton(getRaiseText()+"   ");
			//raiseAmount = raiseIfLimit+toCall;
			b3.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					//System.out.println("RAISEAMOUNT:"+raiseAmount);
					actionCallback.deliverBettingResponse(raiseAmount);
					clearPanel();
				}
			});
		}

		JPanel topPane    = new JPanel(new BorderLayout());
		topPane.setOpaque(false);
		JPanel centerPane = new JPanel(new BorderLayout());
		centerPane.setOpaque(false);
		centerPane.add(playerLabel, BorderLayout.WEST);
		
		if (raiseControl != null) {
			centerPane.add(raiseControl, BorderLayout.EAST);
		}
		topPane.add(centerPane, BorderLayout.CENTER);
		
		JPanel actionPane = new JPanel();
		actionPane.setLayout(new FlowLayout());
		//actionPane.setBackground(Color.gray);
		actionPane.setOpaque(false);
		//actionPane.add(playerLabel);
		actionPane.add(b1);
		actionPane.add(b2);
		//System.out.println("********* rL:"+raiseIfLimit);
		if (raiseIfLimit > 0.001d){ 
			actionPane.add(b3);
			//System.out.println("Added b3 text:"+b3.getText());
		}
		topPane.add(actionPane, BorderLayout.SOUTH);

		cards.add(topPane, nick);
		CardLayout cl = (CardLayout)(cards.getLayout());
		cl.show(cards, nick);
		parent.validate();  // TODO: Is this necessary or correct?
		cards.repaint();
	}
	
	private String getRaiseText() {
		return raiseLanguage + getChipAmount(raiseAmount);
	}
	
	private String getChipAmount(double amt) {
		//long lamt = (long)(amt * 100);
		//amt = (double)(lamt / 100);
		int idx;
		String amount = "" + amt;
		if (amount.endsWith(".0")) {
			amount = amount.substring(0, amount.length()-2);
		} else if ( (idx = amount.indexOf(".")) > 0 ) {
			amount = amount.substring(0, idx+3);
		}
		return amount;
	}
	
	public void actionTimeout() {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	clearPanel();
            }
        });
	}
	
	private void clearPanel() {
		cards.removeAll();
	}
	
    public void itemStateChanged(ItemEvent evt) {
        CardLayout cl = (CardLayout)(cards.getLayout());
        cl.show(cards, (String)evt.getItem());
        if (LOG.isDebugEnabled())
			LOG.debug("***+++ Slider Width: "+jslider.getWidth());

    }
    
    /** Listen to the slider. */
    public void stateChanged(ChangeEvent e) {
        JSlider source = (JSlider)e.getSource();
        if (!source.getValueIsAdjusting()) {
            int fps = (int)source.getValue();
            int inc = source.getExtent();
            if (LOG.isDebugEnabled())
    			LOG.debug("inc = "+inc+" fps="+fps);
            if ( fps % inc > 0 && fps % inc < (inc/2) ) {
            	fps = ((fps / inc) * inc)+inc;
            	source.setValue(fps);
            } else if ( fps % inc > 0 && fps % inc >= (inc/2) ) {
            	fps = ((fps / inc) * inc);
            	source.setValue(fps);
            }
            jtextfield.setText(""+fps);
            if (LOG.isDebugEnabled())
    			LOG.debug("+++ vvvv:" + fps);
            raiseAmount = Double.valueOf(fps);
			b3.setText(getRaiseText());
        }
        if (LOG.isDebugEnabled())
			LOG.debug("+++ width:"+source.getWidth()+" height:" + source.getHeight());
    }
	
	public static ActionPanel create(JPanel panel) {
		final JPanel      fpane       = panel;
		final ActionPanel actionPanel = new ActionPanel(fpane);
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        //javax.swing.SwingUtilities.invokeLater(new Runnable() {
        //    public void run() {
        //    	actionPanel.addComponentsToPane();
        //    }
        //});
        return actionPanel;
    }
}

/*class GBSlider extends JSlider {
	
	public GBSlider(int one, int two, int three) {
		super(one,two,three);
	}
	
	public Dimension getPreferredSize(JComponent c)	{
		JSlider slider = (JSlider) c;
		int size = 21;
		return new Dimension(size, 	120);
	}
}*/

class GBSliderUI extends MetalSliderUI {
	public GBSliderUI() {
		super();
	}
	
	public Dimension getPreferredHorizontalSize() {
		return new Dimension(120, 21);
	}
	
    /*protected Dimension getThumbSize() {
        Dimension size = new Dimension();

        if ( slider.getOrientation() == JSlider.VERTICAL ) {
            size.width = 20;
            size.height = 11;
        }
        else {
            size.width = 11;
            size.height = 20;
        }

        return size;
    }*/
}
