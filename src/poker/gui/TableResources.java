package poker.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;

public class TableResources {
	public static Image tableImg;
	public static Image circleImg;
	//private Image circleImg;
	public static Image smallCardImg;
	public static Image dealerImg;
	public static Image cards[];
	public static Image chips[];
	public static double chipVal[] = 
	  {0.01,
	   0.05,
	   0.25,
	   1,
	   5,
	   25,
	   100,
	   500,
	   1000,
	   5000,
	   25000,
	   100000,
	   500000,
	   1000000,
	   5000000};
	private static boolean firstPass=true;

	public static void init(HoldemTable defToolkit) {
		if (!firstPass) return;

		//Toolkit defToolkit = Toolkit.getDefaultToolkit();
		
		String parentDir = "chipsdeck/";
		String newRes = "resources/";
		
		cards = new Image[52];
		
		String name;
		int count=0;
		for (int i=1; i <= 4; i++) {
			for (int j=2; j <= 14; j++) {
				name = parentDir+"card" + 
				  String.valueOf(i) +
				  "-" +
				  (j < 10 ? "0" : "") + 
				  String.valueOf(j) +
				  ".gif";
				
				cards[count] = defToolkit.getImage(name);
				count++;
			}
		}

		boolean useNewResources = true;
		chips = new Image[15];
		
		//Old Resources
		if (!useNewResources) {
			chips[0] = defToolkit.getImage(parentDir+"chip000001.gif");
			chips[1] = defToolkit.getImage(parentDir+"chip000005.gif");
			chips[2] = defToolkit.getImage(parentDir+"chip000025.gif");
			chips[3] = defToolkit.getImage(parentDir+"chip0001.gif");
			chips[4] = defToolkit.getImage(parentDir+"chip0005.gif");
			chips[5] = defToolkit.getImage(parentDir+"chip0025.gif");

			//chips[4] = defToolkit.getImage(newRes+"5 copy.gif");
			//chips[5] = defToolkit.getImage(newRes+"25 copy.gif");

			chips[6] = defToolkit.getImage(parentDir+"chip0100.gif");
			chips[7] = defToolkit.getImage(parentDir+"chip0500.gif");
			chips[8] = defToolkit.getImage(parentDir+"chip1000.gif");
			chips[9] = defToolkit.getImage(parentDir+"chip5000.gif");
			chips[10] = defToolkit.getImage(parentDir+"chip25000.gif");
			chips[11] = defToolkit.getImage(parentDir+"chip100000.gif");
			chips[12] = defToolkit.getImage(parentDir+"chip500000.gif");
			chips[13] = defToolkit.getImage(parentDir+"chip1000000.gif");
			chips[14] = defToolkit.getImage(parentDir+"chip5000000.gif");

			dealerImg = defToolkit.getImage(parentDir+"chip-D.gif");
			//dealerImg = defToolkit.getImage(newRes+"D copy.gif");

			tableImg  = defToolkit.getImage(parentDir+"bg.jpg");
			//tableImg  = defToolkit.getImage(newRes+"Table.jpg");
			circleImg = defToolkit.getImage(parentDir+"circle.gif");
			//circleImg = defToolkit.getImage(newRes+"Circle_copy.gif");

			smallCardImg = defToolkit.getImage(parentDir+"smallcard3.gif");
			//smallCardImg = defToolkit.getImage(newRes+"Miniature 4 copy.gif");
			//smallCardImg = defToolkit.getImage(newRes+"Card_copy.gif");
		} else {
			// New Resources
			//chips[0] = defToolkit.getImage(newRes+"1c copy.gif");
			//chips[1] = defToolkit.getImage(newRes+"5c copy.gif");
			//chips[0] = defToolkit.getImage(newRes+"1C.gif");
			//chips[1] = defToolkit.getImage(newRes+"5C.gif");
			//chips[2] = defToolkit.getImage(newRes+"25c copy.gif");
			chips[0] = defToolkit.getImage(newRes+"1c_Greenish.gif");
			//chips[1] = defToolkit.getImage(newRes+"5c_Purplish.gif");
			//chips[2] = defToolkit.getImage(newRes+"25c_Orangish.gif");
			//chips[0] = defToolkit.getImage(newRes+"1c_.gif");
			chips[1] = defToolkit.getImage(newRes+"5c_.gif");
			chips[2] = defToolkit.getImage(newRes+"25c_.gif");
			
			chips[3] = defToolkit.getImage(newRes+"1 copy.gif");
			//chips[3] = defToolkit.getImage(newRes+"1 a copy.gif");

			//chips[4] = defToolkit.getImage(newRes+"5 copy.gif");
			chips[4] = defToolkit.getImage(newRes+"5 a copy.gif");
			chips[5] = defToolkit.getImage(newRes+"25 copy.gif");


			chips[6] = defToolkit.getImage(newRes+"100 copy.gif");
			chips[7] = defToolkit.getImage(newRes+"500 copy.gif");
			chips[8] = defToolkit.getImage(newRes+"1K copy.gif");
			chips[9] = defToolkit.getImage(newRes+"5K copy.gif");
			//chips[9] = defToolkit.getImage(newRes+"5K 1copy.gif");

			chips[10] = defToolkit.getImage(newRes+"25K copy.gif");
			//chips[10] = defToolkit.getImage(newRes+"25K 1 copy.gif");
			//chips[11] = defToolkit.getImage(newRes+"100K copy.gif");
			chips[11] = defToolkit.getImage(newRes+"100K.gif");
			
			//chips[12] = defToolkit.getImage(newRes+"500K copy.gif");
			chips[12] = defToolkit.getImage(newRes+"500K.gif");
			
			chips[13] = defToolkit.getImage(newRes+"1M copy.gif");
			chips[14] = defToolkit.getImage(newRes+"5M copy.gif");

			//dealerImg = defToolkit.getImage(newRes+"D copy.gif");
			dealerImg = defToolkit.getImage(newRes+"D 1 copy.gif");

			tableImg  = defToolkit.getImage(newRes+"Table.jpg");
			circleImg = defToolkit.getImage(newRes+"Circle_copy.gif");

			//smallCardImg = defToolkit.getImage(newRes+"Miniature 4 copy.gif");
			smallCardImg = defToolkit.getImage(newRes+"Card_copy.gif");
		}
		
		firstPass=false;
	}
	
	public static Font myfont;
	public static Font cuteFont;
	public static Color LIGHT_TAN;
	static { 
		myfont = new Font("dialog", Font.BOLD, 13); 
		cuteFont = new Font("Times Roman", Font.BOLD, 15); 
		LIGHT_TAN = new Color(255, 220, 160);
	}
}
