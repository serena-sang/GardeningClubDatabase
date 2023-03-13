import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;

public class LogIn extends JPanel implements ActionListener {

	private JLabel username;
	private JTextField enterName;
	private JLabel password;
	private JPasswordField enterPass;
	private JButton goHome;
	private JButton backSignUp;
	private Header headerP;
	private SignUp signUpInfo = new SignUp();
	private Color btnBg = Color.decode("#403233");
	private Color btnTxtC = Color.decode(("#DDD8B8"));
	private final Font newButtonFont = new Font("Monospaced", Font.BOLD, 40);

	public LogIn() {
		this.setLayout(new BorderLayout(8, 10));
		this.setBackground(Color.WHITE);

		JPanel centerP = new JPanel();
		centerP.setBackground(Color.WHITE);
		centerP.setLayout(new GridLayout(3, 2, 30, 30));
		this.add(centerP, BorderLayout.CENTER);

		if (signUpInfo.getSignedUp()) {
			CardLayouts.cardL.next(CardLayouts.cont);
			signUpInfo.setSignedUp(false);
		}
		
		goHome = new JButton("Sign In");
		goHome.setBounds(260, 500, 245, 75);
		goHome.addActionListener(this);
		
		backSignUp = new JButton("Sign Up");
		backSignUp.setBounds(560, 500, 245, 75);
		backSignUp.addActionListener(this);

		backSignUp.setFont(newButtonFont);
		backSignUp.setForeground(btnTxtC);
		backSignUp.setBackground(btnBg);
		
		goHome.setFont(newButtonFont);
		goHome.setForeground(btnTxtC);
		goHome.setBackground(btnBg);

		username = new JLabel("Username: ");
		// username.setBounds(10, 20, 80, 25);
		Font newFontSize = new Font(username.getFont().getName(), username.getFont().getStyle(), 40);
		username.setFont(newFontSize);
		centerP.add(username);
		enterName = new JTextField(20);
		enterName.setFont(newFontSize);
		centerP.add(enterName);

		password = new JLabel("Password: ");
		password.setFont(newFontSize);
		centerP.add(password);
		enterPass = new JPasswordField(50);
		enterPass.setFont(newFontSize);
		centerP.add(enterPass);

		centerP.add(goHome);
		centerP.add(backSignUp);

		headerP = new Header();
		this.add(headerP, BorderLayout.NORTH); // adds gardening club header

	}

	public void actionPerformed(ActionEvent e) {
		boolean isMatched = false;
		if (e.getSource() == goHome) {
			String givenName = signUpInfo.getUsername();
			String givenPass = signUpInfo.getPassword();
			try {
				FileReader fr = new FileReader("login.txt");
				BufferedReader br = new BufferedReader(fr);
				String checkLine;
				while ((checkLine = br.readLine()) != null) {
					String name = new String(enterName.getText());
					String password = new String(enterPass.getPassword());
					if (checkLine.equals(name + "\t" + password)) {
						isMatched = true;
						break;
					}
				}
				fr.close();
			} catch (Exception ex) {

			}
		}
		if (isMatched) {
			CardLayouts.cardL.next(CardLayouts.cont);
		} else if (e.getSource() == backSignUp) {
			CardLayouts.cardL.previous(CardLayouts.cont);
		} else {
			JFrame f = new JFrame();
			JOptionPane.showMessageDialog(f, "Invalid Username or Password");
		}
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setFont(new Font("Monospaced", Font.BOLD, 50));
	}
}
