import javax.swing.*;
import java.awt.*;

public class CardLayouts extends JFrame {
	static CardLayout cardL;
	static Container cont;

	Welcome welcomeP;
	SignUp signupP;
	LogIn loginP;
	Home homeP;
	Header headP;
	Database databaseP;

	public CardLayouts() {
		cont = getContentPane();
		cardL = new CardLayout();
		cont.setLayout(cardL);

		welcomeP = new Welcome();
		signupP = new SignUp();
		loginP = new LogIn();
		homeP = new Home();
		databaseP = new Database();

		cont.add("WelcomePanel", welcomeP);
		cont.add("SignUpPanel", signupP);
		cont.add("LogInPanel", loginP);
		cont.add("HomePanel", homeP);
		cont.add("DatabasePanel", databaseP);
	}
}