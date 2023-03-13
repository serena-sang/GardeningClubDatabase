import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Home extends JPanel implements ActionListener {
	private Header headerP;
	private JTable table;
	// private JTabbedPane homeTabbedPane = new JTabbedPane();
	// private StoreInfo addInfo;
	private JButton addGardener;
	private JButton viewDatabase;
	private String fName;
	private int rowCt;
	private JLabel gdnrLbl = new JLabel();
	//private Database dataB = new Database();
	static DefaultTableModel model = new DefaultTableModel();
	private SQLiteConnection sqliteConn = new SQLiteConnection();
	private Connection conn = sqliteConn.getConnection();
	PreparedStatement pst;
	ResultSet res;
	
	private GdnrInfoF gndrInfoF;

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
				Object[] columnData = new Object[2];
				
				while (res.next()) {
					columnData[0] = res.getString("firstName");
					columnData[1] = res.getString("lastName");
					model.addRow(columnData);
				}
				
			}catch(Exception e) {
				JOptionPane.showMessageDialog(null, e);
				e.printStackTrace();
			}
			/*finally {
				try {
					//res.close();
					pst.close();
				} catch (Exception exc) {
					JOptionPane.showMessageDialog(null, exc);
				}
			} */
			//DefaultTableModel model = (DefaultTableModel) table.getModel();
			model.addRow(new Object[] {
					//54:01
			});
			if (table.getSelectedRow() == -1) {
				if (table.getRowCount() == 0) {
					JOptionPane.showMessageDialog(null, "Update Confirmed", "Gardening Database", JOptionPane.OK_OPTION);
				}
			}
			
			/*
			rowCt = table.getRowCount();
			model.removeRow(rowCt-1);
			rowCt = rowCt - 1;
			System.out.println(rowCt);
			gdnrLbl.setText("Gardeners: " + rowCt); */
			
		}
	}
	
	public Home() {
		this.setLayout(new BorderLayout());
		//this.setLayout(null);
		this.setBackground(Color.WHITE);
		addGardener = new JButton("Add");

		Panel gndrPanel = new Panel(); // panel on the left
		Panel homePanel = new Panel(); // panel on the right

		gndrPanel.setLayout(null);

		homePanel.setLayout(null);
		homePanel.setBackground(new Color(236, 250, 255));

		this.add(gndrPanel, BorderLayout.WEST);
		this.add(homePanel, BorderLayout.EAST);
		gndrPanel.setPreferredSize(new Dimension(700, 300));
		homePanel.setPreferredSize(new Dimension(400, 300));

		headerP = new Header();
		this.add(headerP, BorderLayout.NORTH); // adds gardening club header
		
		addGardener.setBounds(325, 10, 60, 20);
		addGardener.addActionListener(this);
		gndrPanel.add(addGardener);
		
		JPanel tableP = new JPanel();
		tableP.setBounds(40, 40, 600, 500);
		gndrPanel.add(tableP);
		tableP.setLayout(null); 
		
		JScrollPane scrollPane = new JScrollPane();
		gndrPanel.add(new JScrollPane(table));
		scrollPane.setBounds(0, 0, 600, 500);
		tableP.add(scrollPane);
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Last Name", "First Name", "ID"
			}
		)); 
		scrollPane.setViewportView(table);
		
		Object col[] = {"First Name", "Last Name"};
		model.setColumnIdentifiers(col);
		table.setModel(model);
		sort();
		
		gdnrLbl.setFont(new Font("Tahoma", Font.PLAIN, 24));
		gdnrLbl.setBounds(150, 150, 176, 22);
		gdnrLbl.setText("Garden On!");
		homePanel.add(gdnrLbl); 

		table.addMouseListener(new MouseAdapter() {
		    public void mouseClicked(MouseEvent e) {
		        // Get the row index at the point where the mouse was clicked
		        int rowIndex = table.rowAtPoint(e.getPoint());
		        System.out.println(table.getValueAt(rowIndex, 0).toString());
		        fName = table.getValueAt(rowIndex, 0).toString();
		        new GdnrInfoF().renderWindow(fName);
		        
		    }
		});
		
		//view database button
		viewDatabase = new JButton("Database");
		viewDatabase.addActionListener(this);
		viewDatabase.setBounds(150, 75, 100, 27);
		homePanel.add(viewDatabase);
		
		JLabel welcomeLbl = new JLabel("Welcome!");
		welcomeLbl.setBounds(175, 200, 300, 100);
		homePanel.add(welcomeLbl);
		
		JLabel explLbl = new JLabel("To edit data, click on a row in the table.");
		explLbl.setBounds(100, 220, 300, 100);
		homePanel.add(explLbl);
		
		updateTable();
		
	}
	
	public String getFName() {
		return fName;
	}
	
	public void sort() {
		TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<DefaultTableModel>(model);
		table.setRowSorter(sorter);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == addGardener) {
			try {
				//StoreInfo addInfo = new StoreInfo();
				AddGdnr addG = new AddGdnr();
			} catch (Exception ex) {

			}
		}
		if (e.getSource() == viewDatabase) {
			try {
				CardLayouts.cardL.next(CardLayouts.cont);
			} catch (Exception ex) {

			}
		}
	}
}
