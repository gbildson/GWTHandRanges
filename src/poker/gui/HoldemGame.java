
package poker.gui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.JButton;
/*
import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.image.ImageObserver;
import java2d.DemoPanel;
import java2d.AnimatingSurface;
import java.util.*;
import java.io.*;
*/

import poker.*;


/**
 * Animated gif with a transparent background.
 */
public class HoldemGame {

	private static boolean firstWindow = true;
	
    public static void createControl(Surface surface) {
        //final DemoPanel dp = new DemoPanel(surface); 
        Frame f = new Frame("Gregs Test - " + surface.name);
        f.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {System.exit(0);}
            public void windowDeiconified(WindowEvent e) { /*doStart(surface);*/  }

            public void windowIconified(WindowEvent e) { /*doStop(surface);*/ }
        });
        f.add("Center", surface);
        
        if (firstWindow) {
        	JButton b = new JButton("New Window");
        	f.add("South", b);
//        	Equivalent code using an inner class instead of EventHandler.
            b.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    addNewTable();
                }
            });
        }
        firstWindow = false;
        
        //f.add("South", createArrows());
        f.pack();
        f.setSize(new Dimension(800,600));
        f.setVisible(true);
        //if (surface.animating != null) {
            //surface.animating.start();
        //}

    }

	/*private static void doStart(Surface surface) {
		surface.startClock();
		surface.animating.start();
	}*/

	/*private static void doStop(Surface surface) {
		surface.animating.stop();
	}*/


    public static void main(String args[]) {
		//startGame(args);
		//startGame(args);
		boolean automated = (args.length > 0 && args[0].equals("-a"));
		GameThread gt1 = new GameThread(automated);
		gt1.start();
    }
	
	static class GameThread extends Thread {
		private boolean automated;
		public GameThread(boolean automated) {
			this.automated = automated;
			//setDaemon(true);
		}
		
		public void run() {
			startGame(automated);
		}
    }

	private static VisualFeedbackMultiplexor vfm;
	
    public static void startGame(boolean automated) {
        HoldemTable t;
        createControl(t = new HoldemTable());
        t1 = t;
		VisualFeedback vf = t.getVisualFeedback();
		vf = new VisualFeedbackThreader(vf);

		vfm = new VisualFeedbackMultiplexor();

		vfm.addObserver(vf);

		// second view
        /*
		createControl(t = new HoldemTable());
		vf = t.getVisualFeedback();
		vf = new VisualFeedbackThreader(vf);
		vfm.addObserver(vf);
		*/

		HoldemSimulation.initGame(automated, vfm);

    }
    
    private static HoldemTable t1;
    private static HoldemTable t2;

    public static void addNewTable() {
		// New view
    	HoldemTable t;
    	VisualFeedback vf;
        createControl(t = new HoldemTable());
        t2 = t;
		vf = t.getVisualFeedback();
		HoldemSimulation.initNewTable(vf);

    	vf = new VisualFeedbackThreader(vf);
    	vfm.addObserverAfterSync(vf);
    }
    
    public static void hackDump() {
		System.out.println("Table 1:");
		t1.printState();
		System.out.println("Table 2:");
		t2.printState();
    }
    
}
