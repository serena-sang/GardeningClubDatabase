import java.awt.EventQueue;


import javax.swing.JFrame;
import java.awt.Color;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JToggleButton;
import javax.swing.JCheckBox;
import javax.swing.JTextArea;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.AbstractListModel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class AddGdnr extends JFrame{

	private JFrame frame;
	private JTextField jtxtfFirstName;
	private JTextField jtxtfLastName;
	private JTextField jtxtfEmail;
	private JTextField jtxtfHours;
	private JComboBox cboxGrade;
	private JComboBox cboxLevel;
	private JTextArea jtxtareaReason;
	private JCheckBox chckbxExec;
	private JList jListDays;
	private Database dataB = new Database();
	private Home homeUp = new Home();
	private String intermHours;
	private double hoursCt = 0;
	private boolean invalid = false;
	
	//public Connection conn = SQLiteConnection.ConnectDb();
	//Connection conn = null;
	
	//connects to SQLite database to retrieve and edit information
	private SQLiteConnection sqliteConn = new SQLiteConnection();
	private Connection conn = sqliteConn.getConnection();
	PreparedStatement pst;
	ResultSet res;
	
	//adds table
	static DefaultTableModel model = new DefaultTableModel();

	/**
	 * Create the application.
	 */
	public AddGdnr() {
		initialize();
		ImageIcon img = new ImageIcon("images/Gardening Club Database Logo.png"); // creates the image icon
		frame.setIconImage(img.getImage()); // displays icon
		
		//initializes columns
		Object col[] = {"First Name", "Last Name", "Grade", "Experience Level", "Email", "Volunteer Hours", "Days Available", "Reason for Gardening", "Executive"};
		model.setColumnIdentifiers(col);
		
		//dataB.updateTable();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("Gardener Information");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // closes when requested
		frame.setVisible(true);
		frame.setBounds(0, 0, 750, 700); // sets size
		frame.getContentPane().setLayout(null);
		frame.setResizable(false);
		
		frame.getContentPane().setBackground(new Color(230, 230, 250));
		frame.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(179, 203, 185));
		panel.setBounds(0, 0, 737, 149);
		frame.getContentPane().add(panel);
		
		JLabel gardenClub = new JLabel("Gardening Club"); // sets header
		gardenClub.setFont(new Font("Monospaced", Font.BOLD, 72));
		panel.add(gardenClub);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new LineBorder(new Color(0, 0, 0), 3, true));
		panel_1.setBounds(10, 159, 716, 450);
		frame.getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		// adds text
		JLabel jtxtFirst = new JLabel("First Name");
		jtxtFirst.setFont(new Font("Monospaced", Font.BOLD, 20));
		jtxtFirst.setBounds(25, 32, 169, 18);
		panel_1.add(jtxtFirst);
		
		JLabel jtxtGrade = new JLabel("Grade");
		jtxtGrade.setFont(new Font("Monospaced", Font.BOLD, 20));
		jtxtGrade.setBounds(25, 96, 96, 23);
		panel_1.add(jtxtGrade);
		
		JLabel jtxtEmail = new JLabel("Email");
		jtxtEmail.setFont(new Font("Monospaced", Font.BOLD, 20));
		jtxtEmail.setBounds(25, 168, 70, 21);
		panel_1.add(jtxtEmail);
		
		JLabel jtxtLevel = new JLabel("Experience Level");
		jtxtLevel.setFont(new Font("Monospaced", Font.BOLD, 20));
		jtxtLevel.setBounds(193, 96, 192, 23);
		panel_1.add(jtxtLevel);
		
		JLabel jtxtReason = new JLabel("Reasoning");
		jtxtReason.setFont(new Font("Monospaced", Font.BOLD, 20));
		jtxtReason.setBounds(439, 27, 117, 23);
		panel_1.add(jtxtReason);
		
		JLabel jtxtDays = new JLabel("Days Available");
		jtxtDays.setFont(new Font("Monospaced", Font.BOLD, 20));
		jtxtDays.setBounds(25, 284, 169, 23);
		panel_1.add(jtxtDays);
		
		JLabel jtxtHours = new JLabel("Volunteer Hours");
		jtxtHours.setFont(new Font("Monospaced", Font.BOLD, 20));
		jtxtHours.setBounds(25, 233, 192, 18);
		panel_1.add(jtxtHours);
		
		JLabel jtxtExecutive = new JLabel("Executive");
		jtxtExecutive.setFont(new Font("Monospaced", Font.BOLD, 20));
		jtxtExecutive.setBounds(225, 168, 117, 21);
		panel_1.add(jtxtExecutive);
		
		JLabel jtxtLast = new JLabel("Last Name");
		jtxtLast.setFont(new Font("Monospaced", Font.BOLD, 20));
		jtxtLast.setBounds(193, 32, 169, 18);
		panel_1.add(jtxtLast);
		
		//adds text fields for user input
		jtxtfFirstName = new JTextField();
		jtxtfFirstName.setBounds(25, 55, 131, 19);
		panel_1.add(jtxtfFirstName);
		jtxtfFirstName.setColumns(10);
		
		jtxtfLastName = new JTextField();
		jtxtfLastName.setColumns(10);
		jtxtfLastName.setBounds(193, 55, 131, 19);
		panel_1.add(jtxtfLastName);
		
		cboxLevel = new JComboBox();
		cboxLevel.setModel(new DefaultComboBoxModel(new String[] {"", "Beginner", "Intermediate", "Advanced"}));
		cboxLevel.setBounds(193, 119, 155, 21);
		panel_1.add(cboxLevel);
		
		cboxGrade = new JComboBox();
		cboxGrade.setModel(new DefaultComboBoxModel(new String[] {"", "9", "10", "11", "12"}));
		cboxGrade.setBounds(25, 119, 128, 21);
		panel_1.add(cboxGrade);
		
		jtxtfEmail = new JTextField();
		jtxtfEmail.setBounds(25, 191, 179, 19);
		panel_1.add(jtxtfEmail);
		jtxtfEmail.setColumns(10);
		
		jtxtfHours = new JTextField("0");
		jtxtfHours.setBounds(25, 255, 117, 19);
		panel_1.add(jtxtfHours);
		hoursCt = Double.parseDouble(jtxtfHours.getText());
		jtxtfHours.setColumns(10);
		
		chckbxExec = new JCheckBox("");
		chckbxExec.setBounds(341, 168, 21, 21);
		panel_1.add(chckbxExec);
		
		jtxtareaReason = new JTextArea();
		jtxtareaReason.setBounds(439, 72, 259, 347);
		panel_1.add(jtxtareaReason);
		
		//adds multiselect option
		jListDays = new JList();
		jListDays.setValueIsAdjusting(true);
		jListDays.setVisibleRowCount(1);
		jListDays.setModel(new AbstractListModel() {
			String[] values = new String[] {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});
		jListDays.setBounds(25, 317, 155, 102);
		panel_1.add(jListDays);
		
		JButton addHalf = new JButton("+0.5");
		addHalf.setBounds(169, 254, 85, 21);
		addHalf.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				hoursCt += 0.5;
				jtxtfHours.setText(hoursCt+"");
				System.out.println(hoursCt);
			}
		});
		panel_1.add(addHalf);
		
		JButton addOne = new JButton("+1");
		addOne.setBounds(263, 254, 85, 21);
		addOne.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				hoursCt += 1;
				jtxtfHours.setText(hoursCt+"");
				System.out.println(hoursCt);
			}
		});

		panel_1.add(addOne);
		
		JButton btnNewButton = new JButton("Add");
		btnNewButton.setBackground(new Color(255, 255, 255));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (jtxtfFirstName.getText().equals("")||jtxtfFirstName.getText().equals(null)) {
					invalid = true;
					JOptionPane.showMessageDialog(null, "Please enter a first name", "Error", JOptionPane.ERROR_MESSAGE);
				} else {
					invalid = false;
				}
				if (!invalid) {
					//inserts data into database
					String sqlInsert = "INSERT INTO GardenerDB(firstName, lastName, grade, level, email, hours, days, reason, executive) VALUES(?,?,?,?,?,?,?,?,?)";
					try {
						pst = conn.prepareStatement(sqlInsert);
						pst.setString(1, jtxtfFirstName.getText());
						pst.setString(2, jtxtfLastName.getText());
	
						pst.setString(3, (String)cboxGrade.getSelectedItem());
						pst.setString(4, (String) cboxLevel.getSelectedItem());
						pst.setString(5, jtxtfEmail.getText());
						pst.setString(6, hoursCt+"");
						String values = Arrays.toString(jListDays.getSelectedValues());
						String newDays = "";
						if (values.contains("[")||values.contains("]")) {
							newDays = values.replace("[","");
							newDays = newDays.replace("]","");
						}
						pst.setString(7, newDays);
						pst.setString(8, jtxtareaReason.getText());
						if (chckbxExec.isSelected()) {
						pst.setString(9, "1"); // set to true if executive
						} else { 
							pst.setString(9, "0");} 
						pst.executeUpdate();
						System.out.println("HERE");
						dataB.updateTable();
						homeUp.updateTable();
	
						frame.setVisible(false); 
						frame.dispose();
						
					}
					
				catch (Exception ex) {
					JOptionPane.showMessageDialog(null, ex);
					System.out.println(ex);
					ex.printStackTrace();
				}
					finally {
						try {
							res.close();
							pst.close();
						} catch (Exception exc) {
							//JOptionPane.showMessageDialog(null, e);
						}
					} 
				}
			}}
		);
		btnNewButton.setBounds(287, 632, 85, 21);
		frame.getContentPane().add(btnNewButton);
		
		JButton btnClose = new JButton("Close");
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false); 
				frame.dispose(); //Destroy the JFrame object
		}});
	
		btnClose.setBackground(new Color(255, 255, 255));
		btnClose.setBounds(396, 632, 85, 21);
		frame.getContentPane().add(btnClose);
			
		
	}
}


