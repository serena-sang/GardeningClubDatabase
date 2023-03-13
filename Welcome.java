import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Welcome extends JPanel implements ActionListener {
	private JButton goSignUp;
	private JButton goLogIn;
	private Header headerP;
	private Color btnBg = Color.decode("#403233");
	private Color btnTxtC = Color.decode(("#DDD8B8"));
	private Font newButtonFont = new Font("Monospaced", Font.BOLD, 40);

	public Welcome() {

		//creates buttons for user to click
		goSignUp = new JButton("Sign Up");
		goLogIn = new JButton("Log In");
		goSignUp.addActionListener(this);
		goLogIn.addActionListener(this);

		//styles page
		this.setLayout(new BorderLayout(10, 100));
		this.setBackground(Color.WHITE);

		//sets up panel
		JPanel centerP = new JPanel();
		centerP.setBackground(Color.WHITE);
		centerP.setLayout(new GridLayout(2, 1, 30, 30));
		this.add(centerP, BorderLayout.CENTER);

		// adds gardening club header
		headerP = new Header();
		this.add(headerP, BorderLayout.NORTH); 

		//uses BorderLayout to style
		JPanel empty = new JPanel();
		this.add(empty, BorderLayout.EAST);
		this.add(empty, BorderLayout.WEST);
		this.add(empty, BorderLayout.SOUTH);

		//styles buttons
		goLogIn.setFont(newButtonFont); 
		goLogIn.setForeground(btnTxtC);
		goLogIn.setBackground(btnBg);
		goSignUp.setFont(newButtonFont);
		goSignUp.setForeground(btnTxtC);
		goSignUp.setBackground(btnBg);
		centerP.add(goSignUp);
		centerP.add(goLogIn);

	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == goSignUp) {
			CardLayouts.cardL.next(CardLayouts.cont);
		}
		if (e.getSource() == goLogIn) {
			CardLayouts.cardL.next(CardLayouts.cont);
			CardLayouts.cardL.next(CardLayouts.cont);
		}
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setFont(new Font("Arial", Font.BOLD, 60));
	}
}