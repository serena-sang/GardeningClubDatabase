import java.awt.EventQueue;
import java.sql.ResultSet;
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
import javax.swing.JFileChooser;
import javax.swing.JToggleButton;
import javax.swing.RowFilter;
import javax.swing.JCheckBox;
import javax.swing.JTextArea;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.AbstractListModel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Database extends JPanel implements ActionListener{

	private JFrame frame;
	private JTable table;
	private JButton homeBtn;
	
	private SQLiteConnection sqliteConn = new SQLiteConnection();
	private Connection conn = sqliteConn.getConnection();
	PreparedStatement pst;
	ResultSet res;
	static DefaultTableModel model = new DefaultTableModel();
	private JTextField nameTxtField;
	private JTextField nameTxt;
	private boolean hasRan = false;
	private boolean hasName = false;
	private boolean hasAvail = false;
	private boolean hasExp = false;
	private JTextField availTxt;
	private Home homeUp = new Home();
	private String sqlSearch;
	private int rowCt;
	private String daysAva;
	private final JPanel panel = new JPanel();

	/**
	 * Launch the application.
	 */
	
	public void updateTable() {
		while (model.getRowCount()>0) //clears the JTable to refresh
        {
           model.removeRow(0);
        }
		if(conn != null) {
			String sql = "SELECT * FROM GardenerDB";
			try {
				pst = conn.prepareStatement(sql);
				res = pst.executeQuery();
				Object[] columnData = new Object[9];
				
				while (res.next()) {
					//columnData[0] = res.getString("ID");
					columnData[0] = res.getString("firstName");
					columnData[1] = res.getString("lastName");
					columnData[2] = res.getInt("grade");
					columnData[3] = res.getString("level");
					columnData[4] = res.getString("email");
					columnData[5] = res.getDouble("hours");
					columnData[6] = res.getString("days");
 					columnData[7] = res.getString("reason");
					columnData[8] = res.getBoolean("executive");
					model.addRow(columnData);
				}
			}catch(Exception e) {
				JOptionPane.showMessageDialog(null, e);
				e.printStackTrace();
			}
			finally {
				try {
					res.close();
					pst.close();
				} catch (Exception exc) {
					JOptionPane.showMessageDialog(null, exc);
				}
			}  
			model.addRow(new Object[] {
					//54:01
			});
			if (table.getSelectedRow() == -1) {
				if (table.getRowCount() == 0) {
					JOptionPane.showMessageDialog(null, "Update Confirmed", "Gardening Database", JOptionPane.OK_OPTION);
				}
			}
		}
	}
	
	/**
	 * Create the application.
	 */
	public Database() {
		initialize();
		
		Object col[] = {"First Name", "Last Name", "Grade", "Experience Level", "Email", "Volunteer Hours", "Days Available", "Reason for Gardening", "Executive"};
		model.setColumnIdentifiers(col);
		table.setModel(model);
		sort();
		
		homeBtn = new JButton("Home");
		homeBtn.setBounds(392, 639, 105, 21);
		homeBtn.addActionListener(this);
		this.add(homeBtn);
		homeBtn.setBackground(new Color(255, 255, 255));
		
		JLabel searchLabel = new JLabel("Search for ...");
		searchLabel.setFont(new Font("Tahoma", Font.PLAIN, 22));
		searchLabel.setBounds(10, 169, 152, 35);
		this.add(searchLabel);
		
		JLabel nameLabel = new JLabel("Name");
		nameLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		nameLabel.setBounds(154, 159, 45, 13);
		this.add(nameLabel);
		
		JLabel availLabel = new JLabel("Availability");
		availLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		availLabel.setBounds(469, 159, 125, 13);
		this.add(availLabel);
		
		JLabel experienceLabel = new JLabel("Experience");
		experienceLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		experienceLabel.setBounds(689, 159, 125, 13);
		this.add(experienceLabel);
		
		nameTxt = new JTextField();
		nameTxt.setBounds(154, 182, 231, 19);
		add(nameTxt);
		nameTxt.setColumns(10);
		if (!nameTxt.getText().isEmpty()) {
			hasName = true;
		} else {
			hasName = false;
		}
		
		JComboBox expCBox = new JComboBox();
		expCBox.setModel(new DefaultComboBoxModel(new String[] {"", "Beginner", "Intermediate", "Advanced"}));
		expCBox.setBounds(689, 181, 178, 21);
		add(expCBox);
		
		availTxt = new JTextField();
		availTxt.setColumns(10);
		availTxt.setBounds(469, 182, 178, 19);
		add(availTxt);
		
		JButton searchBtn = new JButton("Search");
		searchBtn.setBounds(921, 181, 85, 21);
		this.add(searchBtn);
		searchBtn.setBackground(new Color(255, 255, 255));
		searchBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) { //nested if?
				String nameSearch = nameTxt.getText();
				System.out.println(nameSearch);
				
				String availSearch = availTxt.getText();
				String expSearch = expCBox.getSelectedItem().toString();
				filter(nameSearch, availSearch, expSearch);
				updateTable();

			}
		});
		
		JButton deleteBtn = new JButton("Delete");
		deleteBtn.setBounds(559, 639, 105, 21);
		deleteBtn.setBackground(new Color(255, 255, 255));
		deleteBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = table.getSelectedRow();
				String cell = table.getModel().getValueAt(row,0).toString();
				String sql = "DELETE FROM GardenerDB WHERE firstName = '" + cell + "';";
				try {
					pst = conn.prepareStatement(sql);
					pst.execute();
					JOptionPane.showMessageDialog(null, "Deleted Successfully!");
					//updateTable();
				}catch (Exception ex){
					JOptionPane.showMessageDialog(null,ex);
				}
				model = (DefaultTableModel) table.getModel();
				if (table.getSelectedRow() == -1) {
					if (table.getRowCount() == 0) {
						JOptionPane.showMessageDialog(null, "No data to delete ", "Gardening Database", JOptionPane.OK_OPTION);
					} else {
						JOptionPane.showMessageDialog(null, "Select a row to delete ", "Gardening Database", JOptionPane.OK_OPTION);
					}
				} else {
					model.removeRow(table.getSelectedRow());
				}
				homeUp.updateTable();
			}
		});
		
		add(deleteBtn); 
		
		JButton exportBtn = new JButton("Export");
		exportBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				JFileChooser fileCh = new JFileChooser();
				fileCh.setDialogTitle("Specify where to save the file");
				int userSel = fileCh.showSaveDialog(panel);
				if(userSel == JFileChooser.APPROVE_OPTION) {
					File filetoSave = fileCh.getSelectedFile();
					try {
						FileWriter fw = new FileWriter(filetoSave);
						BufferedWriter bw = new BufferedWriter(fw);
						for (int i = 0; i < model.getColumnCount(); i++) {
				            bw.write(model.getColumnName(i) + ",");
				        }
						bw.newLine();
						
						for (int i = 0; i < model.getRowCount(); i++) {
							for (int j = 0; j < model.getColumnCount(); j++) {
								bw.write(table.getValueAt(i, j).toString() + ",");
							}
							bw.newLine(); //separates records per line
							
						}
						JOptionPane.showMessageDialog(panel, "Saved!", "INFORMATION", JOptionPane.INFORMATION_MESSAGE);
						bw.close();
						fw.close();
					} catch(IOException ioex){
						JOptionPane.showMessageDialog(panel, ioex.toString(), "ERROR", JOptionPane.ERROR_MESSAGE);
					}
									}
				
			}
		});
		
		exportBtn.setBounds(969, 639, 85, 21);
		add(exportBtn);
		updateTable();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		this.setBackground(new Color(230, 230, 250));
		this.setLayout(null);
		
		panel.setBackground(new Color(179, 203, 185));
		panel.setBounds(0, 0, 1086, 149);
		this.add(panel);
		
		JLabel lblNewLabel = new JLabel("Gardening Club");
		lblNewLabel.setFont(new Font("Monospaced", Font.BOLD, 72));
		panel.add(lblNewLabel);
		
		JPanel tableP = new JPanel();
		tableP.setBackground(new Color(230, 230, 250));
		tableP.setBorder(new LineBorder(new Color(0, 0, 0), 3, true));
		tableP.setBounds(10, 214, 1044, 415);
		this.add(tableP);
		tableP.setLayout(null); 
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 10, 1024, 395);
		tableP.add(scrollPane);
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Reason", "Executive", "Days Available", "Volunteer Hours", "Email", "Experience Level", "Grade", "Last Name", "First Name", "ID"
			}
		));
		scrollPane.setViewportView(table);
	}
	
	public void sort() {
		TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<DefaultTableModel>(model);
		table.setRowSorter(sorter);
	}
	
	public void filter(String nameQuery, String availQuery, String expQuery) {
		TableRowSorter<DefaultTableModel> tr = new TableRowSorter<DefaultTableModel>(model);
		List<RowFilter<Object,Object>> rfs = new ArrayList<RowFilter<Object,Object>>(3);
		rfs.add(RowFilter.regexFilter("(?i)"+nameQuery, 0));
		rfs.add(RowFilter.regexFilter("(?i)"+availQuery, 6));
		rfs.add(RowFilter.regexFilter("(?i)"+expQuery, 3));
		
		RowFilter<Object,Object> af = RowFilter.andFilter(rfs);
		System.out.println(rfs.toString());
		System.out.println(af.toString());
		tr.setRowFilter(af);

		table.setRowSorter(tr);
	}
	
	public int getRowCt() {
		return rowCt;
	}
	
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == homeBtn) {
			try {
				System.out.println("Click");
				CardLayouts.cardL.previous(CardLayouts.cont);
			} catch (Exception ex) {

			}
		}
	}
}