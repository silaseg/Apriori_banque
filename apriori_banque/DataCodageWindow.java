package testfdd2;

	import java.sql.Date;
	import java.sql.ResultSet;
	import java.sql.SQLException;
	import java.sql.Statement;

        import java.awt.Color;
        import java.awt.Font;
        import java.awt.event.ActionEvent;
        import java.awt.event.ActionListener;
        
        import java.sql.Connection;
        import java.sql.DriverManager;
        
        import javax.swing.BorderFactory;
        import javax.swing.JButton;
        import javax.swing.JFrame;
        import javax.swing.JLabel;
        import javax.swing.JOptionPane;
        import javax.swing.JScrollPane;
        import javax.swing.JTable;
        import javax.swing.JTextArea;
        import javax.swing.ScrollPaneConstants;

public class DataCodageWindow extends JFrame{
	
    public static final String DBURL = "jdbc:oracle:thin:@localhost:1521:xe";
    public static final String DBUSER = "datamining2015";
    public static final String DBPASS = "datamining2015";
    
	private JButton next = new JButton("Next");
	private JButton previous = new JButton("Previous");
	private DataNormalisationWindow previousWindow;
	private JLabel stepName = new JLabel();
        private DataMiningWindow nextWindow;
        private boolean nextWindowCreated = false;	

	public DataCodageWindow(){
		
		this.setTitle("Codage des données");
		this.setSize(800, 645);
		this.setResizable(false);
		this.setLayout(null);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(null);
                this.getContentPane().setBackground(new Color(255, 180, 255));
		this.setVisible(true);
		initComponents();
	}
	
	public DataCodageWindow(DataNormalisationWindow previousWindow){
		
		this.setTitle("Codage des données");
		this.setSize(800, 645);
		this.setResizable(false);
		this.setLayout(null);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.previousWindow = previousWindow;
		this.setLocationRelativeTo(null);
                this.getContentPane().setBackground(new Color(255, 180, 255));
		this.setVisible(true);
		initComponents();
	}
	
	private void initComponents(){
		
		this.getContentPane().add(next);
		next.setBounds(680, 565, 90, 30);
		this.getContentPane().add(previous);
		previous.setBounds(20, 565, 90, 30);
		this.getContentPane().add(stepName);
		stepName.setBounds(168, 45, 750, 45);
                stepName.setFont(new Font("Arial", Font.CENTER_BASELINE, 40));
                stepName.setText("   Codage des données");

		JScrollPane scroll = new JScrollPane();
		scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

                try {
                        scroll.setViewportView(DataCodageWindow.getTable());
                } catch (SQLException e) {
                        e.printStackTrace();
                }

		this.getContentPane().add(scroll);
		scroll.setBounds(20, 115, 750, 430);
		
		final DataCodageWindow temp = this;
                
                next.addActionListener(new ActionListener(){
			
			public void actionPerformed(ActionEvent e){
				
				if(nextWindowCreated){
				
					nextWindow.setVisible(true);
					temp.setVisible(false);
				
				}else{
					
					nextWindowCreated = true;
					DataMiningWindow dataMiningWindow = new DataMiningWindow(temp);
					temp.nextWindow = dataMiningWindow;
					temp.setVisible(false);
				}
				
			}
		});                
                
		previous.addActionListener(new ActionListener(){
			
			public void actionPerformed(ActionEvent e){
				
				temp.getPreviousWindow().setVisible(true);
				temp.setVisible(false);
			}
		});
	}
	
	public DataNormalisationWindow getPreviousWindow(){
		
		return this.previousWindow;
	}



    public static JTable getTable () throws SQLException{
        
        Connection con = DriverManager.getConnection(DBURL, DBUSER, DBPASS);
        Statement statement = con.createStatement();
        
        ResultSet tableResult = statement.executeQuery("SELECT * FROM CODAGETABLEBRUTE");
        JTable table = new JTable(Main.buildTableModel(tableResult));
  
        tableResult.close();
        statement.close();
        con.close();
        
        return table;
    }
    
}
