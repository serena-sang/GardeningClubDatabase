import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class Main {
	public static void main(String[] args) {
		JFrame f = new JFrame("Gardening Club Database");
		CardLayouts multC = new CardLayouts();
		ImageIcon img = new ImageIcon("images/Gardening Club Database Logo.png"); // creates the image icon
		multC.setIconImage(img.getImage()); // displays icon

		multC.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // make frame closed when x button is pressed
		multC.setVisible(true); // make the frame visible
		multC.setSize(1100, 700); // set the size of the frame
		multC.setResizable(true); // makes the frame resizeable
	}

}
