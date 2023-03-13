import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;

public class SignUp extends JPanel implements ActionListener {
	private JButton goLogIn;
	private JLabel username;
	private JTextField enterName;
	private JLabel password;
	private JPasswordField enterPass;
	private JLabel email;
	private JTextField enterEmail;
	private Header headerP;
	private Color btnBg = Color.decode("#403233");
	private Color btnTxtC = Color.decode(("#DDD8B8"));
	private final Font newButtonFont = new Font("Monospaced", Font.BOLD, 40);
	private boolean isSignedUp = false;

	public SignUp() {
		
		//style 
		this.setLayout(new BorderLayout(8, 10));
		Font newButtonFont = new Font("Monospaced", Font.BOLD, 40);
		this.setBackground(Color.WHITE);
		
		//log in button
		goLogIn = new JButton("Log In");
		goLogIn.addActionListener(this);
		goLogIn.setFont(newButtonFont);

		goLogIn.setFont(newButtonFont);
		goLogIn.setForeground(btnTxtC);
		goLogIn.setBackground(btnBg);

		//styles panel
		JPanel centerP = new JPanel();
		centerP.setBackground(Color.WHITE);
		centerP.setLayout(new GridLayout(3, 2, 30, 30));
		this.add(centerP, BorderLayout.CENTER);

		//requests for username
		username = new JLabel("Username: ");
		Font newFontSize = new Font(username.getFont().getName(), username.getFont().getStyle(), 40);
		username.setFont(newFontSize);
		centerP.add(username);
		
		//retrieves user input
		enterName = new JTextField(20);
		enterName.setFont(newFontSize);
		centerP.add(enterName);

		//requests for password
		password = new JLabel("Password: ");
		password.setFont(newFontSize);
		centerP.add(password);
		
		//retrieves password
		enterPass = new JPasswordField(50);
		enterPass.setFont(newFontSize);
		centerP.add(enterPass);
		centerP.add(goLogIn);

		//adds "Gardening Club" header
		headerP = new Header();
		this.add(headerP, BorderLayout.NORTH); 

	}

	//obtain username
	public String getUsername() {
		return enterName.toString();
	}

	//obtain password
	public String getPassword() {
		return enterPass.toString();
	}
	
	public boolean getSignedUp() {
		return isSignedUp;
	}
	
	public void setSignedUp(boolean val) {
		isSignedUp = val;
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == goLogIn) {
			try {
				//calls onto "Login" text file
				FileWriter fw = new FileWriter("login.txt", true);
				System.out.println(enterPass.getPassword());
				
				//enters data into text file
				String password = new String(enterPass.getPassword());
				fw.write(enterName.getText() + "\t" + password + "\n");
				fw.close();
				JFrame f = new JFrame();
				JOptionPane.showMessageDialog(f, "Registration Complete!");
				isSignedUp = true;
			} catch (IOException ex) {

			}
			CardLayouts.cardL.next(CardLayouts.cont);
		}
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		g.setFont(new Font("Monospaced", Font.BOLD, 50));
	}
}
