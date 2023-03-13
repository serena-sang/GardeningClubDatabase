import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JCheckBox;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.AbstractListModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;

import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.awt.event.ActionEvent;

public class GdnrInfoF extends JFrame {

	private JPanel gdnrPane;
	private JTextField fNameTxt;
	private JTextField lNameTxt;
	private JTextField emailTxt;
	private JTextField hoursTxt;
	private Home homeData;
	private Database dataB;
	private String name;
	private String lastName;
	private int grade;
	private String level;
	private String email;
	private Double hours;
	private String days;
	private String reason;
	private boolean exec;
	private JFrame infoF;
	private JList jListDays;
	private double hoursCt = 0;
	private String allDays = "";
	
	static DefaultTableModel model = new DefaultTableModel();
	private SQLiteConnection sqliteConn = new SQLiteConnection();
	private Connection conn = sqliteConn.getConnection();
	PreparedStatement pst;
	ResultSet resset;
	private JTextField textField;

	/**
	 * Create the frame.
	 */
	//public GdnrInfoF(String firstName) {
	public GdnrInfoF() {
		infoF = new JFrame();
	}
	

	public void renderWindow(String name) {
		
		this.setBounds(0,0,800,500);
		
		gdnrPane = new JPanel();
		gdnrPane.setBackground(new Color(0,0,0,0));
		gdnrPane.setBorder(new EmptyBorder(5,5,5,5));
		homeData = new Home();
		dataB = new Database();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBounds(0,0,800,560);
		gdnrPane = new JPanel();
		gdnrPane.setBackground(new Color(225, 255, 225));
		gdnrPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(gdnrPane);
		gdnrPane.setLayout(null);
		
		JLabel fNameLabel = new JLabel("First Name");
		fNameLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		fNameLabel.setBounds(22, 23, 124, 34);
		gdnrPane.add(fNameLabel);
		
		JLabel lNameLabel = new JLabel("Last Name");
		lNameLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lNameLabel.setBounds(236, 23, 117, 34);
		gdnrPane.add(lNameLabel);
		
		fNameTxt = new JTextField();
		fNameTxt.setColumns(10);
		fNameTxt.setBounds(22, 60, 170, 19);
		
		gdnrPane.add(fNameTxt);
		String sqlFName = "SELECT * FROM GardenerDB WHERE firstName = '" + name + "';";
		try {
			pst = conn.prepareStatement(sqlFName);
			resset = pst.executeQuery();
			lastName = resset.getString("lastName");
			grade = resset.getInt("grade");
			level = resset.getString("level");
			email = resset.getString("email");
			hours = resset.getDouble("hours");
			days = resset.getString("days");
			reason = resset.getString("reason");
			exec = resset.getBoolean("executive");
		}catch (Exception ex){
			JOptionPane.showMessageDialog(null,ex);
			System.out.println(ex);
		}finally {
			try {
				resset.close();
				pst.close();
			} catch (Exception exc) {
				JOptionPane.showMessageDialog(null, exc);
			}
		}  

		fNameTxt.setText(name);
		
		lNameTxt = new JTextField();
		lNameTxt.setColumns(10);
		lNameTxt.setBounds(236, 60, 170, 19);
		lNameTxt.setText(lastName);
		gdnrPane.add(lNameTxt);
		
		JLabel daysLabel = new JLabel("Days Available");
		daysLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		daysLabel.setBounds(22, 130, 146, 34);
		gdnrPane.add(daysLabel);
		
		if (days.contains("[") || days.contains("]")) {
			StringBuilder sb = new StringBuilder(days);
			sb.delete(sb.length()-1,sb.length());
			sb.delete(0,1);
			days = sb.toString();
		}
		
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
		gdnrPane.add(jListDays);
		
		List<String> array = Arrays.asList(days.split(", "));
		String[] valuesDays = new String[] {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
		ArrayList<Integer> indices = new ArrayList<Integer>(array.size());
		for (String day:array) {
			indices.add(Arrays.asList(valuesDays).indexOf(day));
		}
		int[] arr = indices.stream().mapToInt(i -> i).toArray();
		jListDays.setSelectedIndices(arr);
		
		JLabel emailLabel = new JLabel("Email");
		emailLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		emailLabel.setBounds(22, 430, 146, 34);
		gdnrPane.add(emailLabel);
		
		emailTxt = new JTextField();
		emailTxt.setColumns(10);
		emailTxt.setBounds(22, 464, 179, 19);
		emailTxt.setText(email);
		gdnrPane.add(emailTxt);
		
		JLabel expLabel = new JLabel("Experience Level");
		expLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		expLabel.setBounds(307, 141, 179, 34);
		gdnrPane.add(expLabel);
		
		JComboBox cboxLevel = new JComboBox();
		cboxLevel.setModel(new DefaultComboBoxModel(new String[] {"", "Beginner", "Intemediate", "Advanced"}));
		cboxLevel.setBounds(307, 178, 155, 21);
		cboxLevel.setSelectedItem(level);
		gdnrPane.add(cboxLevel);
		
		JLabel execLabel = new JLabel("Executive?");
		execLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		execLabel.setBounds(307, 269, 103, 34);
		gdnrPane.add(execLabel);
		
		JCheckBox execChkBx = new JCheckBox("");
		execChkBx.setSelected(exec);
		execChkBx.setBounds(416, 279, 27, 21);
		
		gdnrPane.add(execChkBx);
		
		JLabel gradeLabel = new JLabel("Grade");
		gradeLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		gradeLabel.setBounds(307, 344, 146, 34);
		gdnrPane.add(gradeLabel);
		
		JComboBox cboxGrade = new JComboBox();
		cboxGrade.setModel(new DefaultComboBoxModel(new String[] {"", "9", "10", "11", "12"}));
		cboxGrade.setBounds(307, 376, 128, 21);
		gdnrPane.add(cboxGrade);
		cboxGrade.setSelectedItem(grade + "");
		
		JLabel hoursLabel = new JLabel("Volunteer Hours");
		hoursLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		hoursLabel.setBounds(270, 435, 146, 34);
		gdnrPane.add(hoursLabel);
		
		hoursTxt = new JTextField("0");
		hoursTxt.setColumns(10);
		hoursTxt.setBounds(270, 469, 117, 19);
		hoursTxt.setText(hours.toString());
		hoursCt = Double.parseDouble(hoursTxt.getText());
		hoursTxt.setColumns(10);
		gdnrPane.add(hoursTxt);
		
		JLabel reasonLabel = new JLabel("Reasoning");
		reasonLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		reasonLabel.setBounds(534, 23, 103, 34);
		gdnrPane.add(reasonLabel);
		
		JTextArea reasonTxtArea = new JTextArea();
		reasonTxtArea.setBounds(534, 76, 215, 276);
		reasonTxtArea.setText(reason);
		gdnrPane.add(reasonTxtArea);
		
		JButton closeBtn = new JButton("Close");
		closeBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false); //sets the frame invisible
				dispose(); //destroy the JFrame object
			}
		});
		closeBtn.setFont(new Font("Tahoma", Font.PLAIN, 18));
		closeBtn.setBounds(574, 469, 103, 31);
		gdnrPane.add(closeBtn);
		
		JButton updateBtn = new JButton("Update");
		updateBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String sqlUpdate = "UPDATE GardenerDB SET lastName = ?, grade = ?, level = ?, email = ?, hours = ?, days = ?, reason = ?, executive = ? WHERE firstName = '" + name + "';";
				try {
					PreparedStatement pr = conn.prepareStatement(sqlUpdate);
					pr.setString(1, lNameTxt.getText());
					pr.setString(2, (String)cboxGrade.getSelectedItem());
					pr.setString(3, (String) cboxLevel.getSelectedItem());
					pr.setString(4, emailTxt.getText());
					pr.setString(5, hoursTxt.getText());
					String values = Arrays.toString(jListDays.getSelectedValues());
					String newDays = "";
					if (values.contains("[")||values.contains("]")) {
						newDays = values.replace("[","");
						newDays = newDays.replace("]","");
					}
					pr.setString(6, newDays);
					pr.setString(7, reasonTxtArea.getText());
					if (execChkBx.isSelected()) {
					pr.setString(8, "1"); // set to true if executive
					} else { 
						pr.setString(8, "0");} 
					pr.executeUpdate();
					homeData.updateTable();
					dataB.updateTable();
					setVisible(false); //sets the frame invisible
					dispose(); //destroy the JFrame object
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		JButton addHalf = new JButton("+0.5");
		addHalf.setBounds(397, 468, 70, 21);
		addHalf.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				hoursCt += 0.5;
				hoursTxt.setText(hoursCt+"");
			}
		});
		gdnrPane.add(addHalf);
		
		JButton addOne = new JButton("+1");
		addOne.setBounds(477, 468, 70, 21);
		addOne.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				hoursCt += 1;
				hoursTxt.setText(hoursCt+"");
			}
		});
		gdnrPane.add(addOne);
		
		updateBtn.setFont(new Font("Tahoma", Font.PLAIN, 18));
		updateBtn.setBounds(687, 469, 95, 31);
		gdnrPane.add(updateBtn);
		
		textField = new JTextField();
		textField.setBounds(22, 10, 3, 0);
		gdnrPane.add(textField);
		textField.setColumns(10);
		
		JLabel dayLabel = new JLabel(days);
		dayLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		dayLabel.setBounds(22, 182, 300, 131);
		gdnrPane.add(dayLabel);
		this.setVisible(true);
		
	}
}
