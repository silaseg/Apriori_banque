package testfdd2;

	import java.sql.Connection;
	import java.sql.Date;
	import java.sql.DriverManager;
	import java.sql.ResultSet;
	import java.sql.SQLException;
	import java.sql.Statement;

        import java.awt.BorderLayout;
        import java.awt.Color;
        import java.awt.Font;
        import java.awt.event.ActionEvent;
        import java.awt.event.ActionListener;
        import java.sql.SQLException;
        
        import javax.sql.rowset.CachedRowSet;
        import javax.swing.BorderFactory;
        import javax.swing.JButton;
        import javax.swing.JFrame;
        import javax.swing.JLabel;
        import javax.swing.JScrollPane;
        import javax.swing.JTable;
        import javax.swing.JTextArea;
        import javax.swing.ScrollPaneConstants;

    public class DataPreparationWindow extends JFrame {

    public static final String DBURL = "jdbc:oracle:thin:@localhost:1521:xe";
    public static final String DBUSER = "datamining2015";
    public static final String DBPASS = "datamining2015";
    
	private JButton next = new JButton("Next");
	private JLabel stepName = new JLabel();
        private DataNettoyageWindow nextWindow;
        private boolean nextWindowCreated = false;
	
	
	public DataPreparationWindow(){
		
		this.setTitle("Préparation des données");
		this.setSize(800, 645);
		this.setResizable(false);
		this.setLayout(null);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(null);
                this.getContentPane().setBackground(new Color(255, 180, 255));
		this.setVisible(true);
		initComponents();
	}
	
	private void initComponents(){
		
		this.getContentPane().add(next);
		next.setBounds(680, 565, 90, 30);
		this.getContentPane().add(stepName);
		stepName.setBounds(130, 45, 750, 40);
                stepName.setFont(new Font("Arial", Font.CENTER_BASELINE, 40));
		stepName.setText("   Préparation des données");

		JScrollPane scroll = new JScrollPane();
		scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

                try {
                        scroll.setViewportView(DataPreparationWindow.getTable());
                } catch (SQLException e) {
                        e.printStackTrace();
                }

		this.getContentPane().add(scroll);
		scroll.setBounds(20, 115, 750, 430);
		
		final DataPreparationWindow temp = this;

                next.addActionListener(new ActionListener(){
			
			public void actionPerformed(ActionEvent e){
				
				if(nextWindowCreated){
				
					nextWindow.setVisible(true);
					temp.setVisible(false);
				
				}else{
					
					nextWindowCreated = true;
					DataNettoyageWindow dataNettoyageWindow = new DataNettoyageWindow(temp);
					temp.nextWindow = dataNettoyageWindow;
					temp.setVisible(false);
				}
				
			}
		});                
        }

    public static JTable getTable () throws SQLException{
          
        Connection con = DriverManager.getConnection(DBURL, DBUSER, DBPASS);
        Statement statement = con.createStatement();
        
        ResultSet tableResult = statement.executeQuery("SELECT * FROM TABLEBRUTE");
        JTable table = new JTable(Main.buildTableModel(tableResult));     
        
        tableResult.close();
        statement.close();
        con.close();
        
        return table;
    }

}

