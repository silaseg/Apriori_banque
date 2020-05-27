package testfdd2;

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
        import javax.swing.JScrollPane;
        import javax.swing.JTable;
        import javax.swing.JTextArea;
        import javax.swing.ScrollPaneConstants;

public class DataNormalisationWindow extends JFrame{

    public static final String DBURL = "jdbc:oracle:thin:@localhost:1521:xe";
    public static final String DBUSER = "datamining2015";
    public static final String DBPASS = "datamining2015";
    
	private JButton next = new JButton("Next");
	private JButton previous = new JButton("Previous");
	private DataNettoyageWindow previousWindow;
	private JLabel stepName = new JLabel();
        private DataCodageWindow nextWindow;
        private boolean nextWindowCreated = false;

	public DataNormalisationWindow(){
		
		this.setTitle("Normalisation des données");
		this.setSize(800, 645);
		this.setResizable(false);
		this.setLayout(null);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(null);
                this.getContentPane().setBackground(new Color(255, 180, 255));
		this.setVisible(true);
		initComponents();
	}
	
	public DataNormalisationWindow(DataNettoyageWindow previousWindow){
		
		this.setTitle("Normalisation des données");
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
		stepName.setBounds(107, 45, 750, 40);
                stepName.setFont(new Font("Arial", Font.CENTER_BASELINE, 40));
		stepName.setText("   Normalisation des données");

		JScrollPane scroll = new JScrollPane();
		scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

                try {
                        scroll.setViewportView(DataNormalisationWindow.getTable());
                } catch (SQLException e) {
                        e.printStackTrace();
                }

		this.getContentPane().add(scroll);
		scroll.setBounds(20, 115, 750, 430);
		
		final DataNormalisationWindow temp = this;

                next.addActionListener(new ActionListener(){
			
			public void actionPerformed(ActionEvent e){
				
				if(nextWindowCreated){
				
					nextWindow.setVisible(true);
					temp.setVisible(false);
				
				}else{
					
					nextWindowCreated = true;
					DataCodageWindow dataCodageWindow = new DataCodageWindow(temp);
					temp.nextWindow = dataCodageWindow;
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
	
	public DataNettoyageWindow getPreviousWindow(){
		
		return this.previousWindow;
	}



    public static JTable getTable () throws SQLException{
         
        Connection con = DriverManager.getConnection(DBURL, DBUSER, DBPASS);
        Statement statement = con.createStatement();
        
        ResultSet tableResult = statement.executeQuery("SELECT * FROM TABLEBRUTE2");
        JTable table = new JTable(Main.buildTableModel(tableResult));

        tableResult.close();
        statement.close();
        con.close();
        
        return table;
    }

}
