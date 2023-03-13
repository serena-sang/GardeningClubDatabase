import java.sql.*;
import javax.swing.*;

public class SQLiteConnection {
	public static Connection conn = null;
	public SQLiteConnection() {
		try {
			Class.forName("org.sqlite.JDBC");
			conn = DriverManager.getConnection("jdbc:sqlite:database\\GardenerDB.db");
		}
		catch(Exception e) {
			JOptionPane.showMessageDialog(null, e); // displays error message
			System.out.println(e);
		}
	}
	public static Connection getConnection() {
		return conn;
	}
}
