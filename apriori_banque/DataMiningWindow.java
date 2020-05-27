package testfdd2;

        import java.awt.Color;
        import java.awt.Font;
        import java.awt.event.ActionEvent;
        import java.awt.event.ActionListener;
        
        import javax.swing.BorderFactory;
        import javax.swing.JButton;
        import javax.swing.JFrame;
        import javax.swing.JLabel;
        import javax.swing.JScrollPane;
        import javax.swing.JTable;
        import javax.swing.JTextArea;
        import javax.swing.ScrollPaneConstants;
        
        import java.awt.Desktop;
        
        import java.io.File;
        import java.io.FileInputStream;
        import java.io.FileOutputStream;
        import java.io.IOException;
        import java.io.ObjectInputStream;
        import java.io.ObjectOutputStream;
        import java.sql.Connection;
        import java.sql.DriverManager;
        import java.sql.ResultSet;
        import java.sql.SQLException;
        import java.sql.Statement;
        import java.util.ArrayList;
        import java.util.Date;
        
        import javax.swing.JComboBox;
        import javax.swing.JOptionPane;
        import javax.swing.JTextField;

public class DataMiningWindow extends JFrame{
	
	private JButton previous = new JButton("Previous");
	
        private JButton start = new JButton("Start");
        private JButton details = new JButton("Details");
        
        private JTextField minSup = new JTextField();
        private JTextField minConf = new JTextField();
        
        private JLabel minSupText = new JLabel("Min Support");
        private JLabel minConfText = new JLabel("Min Confiance");
                    
        private DataCodageWindow previousWindow;
	private JLabel stepName = new JLabel();
        private JTextArea rulesDisplayer = new JTextArea();

        private static String serverName = "localhost";
        private static String portNumber = "1521";
        private static String sid = "xe";
        private static String url = "jdbc:oracle:thin:@" + serverName + ":" + portNumber + ":" + sid;
        private static String username = "datamining2015";
        private static String password = "datamining2015";
        
        private ArrayList<Regle> associationRules;
	
	public DataMiningWindow(){
		
		this.setTitle("Fouille de données");
		this.setSize(800, 645);
		this.setResizable(false);
		this.setLayout(null);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(null);
                this.getContentPane().setBackground(new Color(255, 180, 255));
		this.setVisible(true);
		initComponents();
	}
	
	public DataMiningWindow(DataCodageWindow previousWindow){
		
		this.setTitle("Fouille de données");
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
	
	    	
                this.getContentPane().add(start);
                start.setBounds(680, 565, 90, 30);
                this.getContentPane().add(details);
                details.setBounds(550, 565, 90, 30);
                details.setEnabled(false);
	    
		this.getContentPane().add(previous);
		previous.setBounds(20, 565, 90, 30);
		this.getContentPane().add(stepName);
		stepName.setBounds(187, 45, 750, 45);
		stepName.setFont(new Font("Arial", Font.CENTER_BASELINE, 40));
		stepName.setText("   Fouille de données");
		
                this.getContentPane().add(minSupText);
                minSupText.setBounds(20, 110, 90, 25);
                this.getContentPane().add(minSup);
                minSup.setBounds(103, 110, 100, 25);
                this.getContentPane().add(minConfText);
                minConfText.setBounds(240, 110, 90, 25);
                this.getContentPane().add(minConf);
                minConf.setBounds(335, 110, 100, 25);                
           
                rulesDisplayer.setFont(new Font("Calibri", 0, 14));         
                rulesDisplayer.setColumns(60);
                rulesDisplayer.setRows(35);
		JScrollPane scroll = new JScrollPane();
		scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scroll.setViewportView(rulesDisplayer);
		this.getContentPane().add(scroll);
		scroll.setBounds(20, 150, 750, 400);
		
		final DataMiningWindow temp = this;
		
		previous.addActionListener(new ActionListener(){
			
			public void actionPerformed(ActionEvent e){
				
				temp.getPreviousWindow().setVisible(true);
				temp.setVisible(false);
			}
		});

                start.addActionListener(new ActionListener(){
                        
                        public void actionPerformed(ActionEvent e){                                                    
                            
                            if(getMinSupValue() < 0 || getMinConflValue() < 0 || getMinSupValue() > 1 || getMinConflValue() > 1){
                                
                                JOptionPane.showMessageDialog(null, "Erreur de saisie!\n" +"Veuillez entrer des valeurs valides", "Erreur", JOptionPane.ERROR_MESSAGE);
                            
                            }else{
                                                                                           
                                Connection connection = openConnection();
                                ArrayList<ItemSet> DTransactionalTable = getDTransactionalTable(connection);
                                Main.size = DTransactionalTable.size();
                                closeConnection(connection);
    
                                rulesDisplayer.setText("\n--------------- START\n");
                                System.out.println("--------------- START at : " + new Date());
                                              
                                associationRules = new associationRulesGenerator(DTransactionalTable, getMinSupValue(), getMinConflValue()).launchAprioriAlgorithm();
                                    
                                    for(int i=0; i<associationRules.size(); i++){
                                            
                                            rulesDisplayer.append("\n "+associationRules.get(i)+"  ");
                                            rulesDisplayer.append("\n");
                                        
                                    }
                                    
                                rulesDisplayer.append("\n--------------- DONE");
                                System.out.println("--------------- DONE at : " + new Date());
                                
                                details.setEnabled(true);
                                
                            }                                
                                                          
                        }
                });


	    details.addActionListener(new ActionListener(){
	            
	            public void actionPerformed(ActionEvent e){

	                rulesDisplayer.setText("\n--------------- START\n");
                                                                
                        for(int i=0; i<associationRules.size(); i++){
                                
                                rulesDisplayer.append("\n "+associationRules.get(i).toStringDetails()+"  ");
                                rulesDisplayer.append("\n");                            
                        }
                        
	                rulesDisplayer.append("\n--------------- DONE");	                
	                
	            }
	    });
	    
	    
	}
	
	public DataCodageWindow getPreviousWindow(){
		
		return this.previousWindow;
	}

    public static File saveTransactionalTable(Connection connection){
            
            File file = null;
            try {
                          
                    Statement statement = connection.createStatement();
                    String query = "SELECT * FROM CODAGETABLEBRUTE";

                    ResultSet resultat = statement.executeQuery(query);
                    int columnsNumber = resultat.getMetaData().getColumnCount();

					
                    file = new File("C:/Users/ke/Desktop/DATAMINING2015/base.txt");
                    ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
                    
                    while(resultat.next()){
                           
                            for(int i=1; i<columnsNumber; i++){
                                    
                                    int val = resultat.getInt(i+1);
                                    oos.writeInt(val);
                            }
                    }
                    
                   oos.close();
                    statement.close();
            
            }catch(SQLException e){
                          
                    e.printStackTrace();
            
            }catch(IOException e){
                    
                    e.printStackTrace();
            }
            
            return file;
    }
    
    public static ArrayList<ItemSet> getDTransactionalTable(Connection connection){
            
            ArrayList<ItemSet> DTransactionalTable = new ArrayList<ItemSet>();
            File file = Util.saveTransactionalTable(connection);
                            
            try{
                    
                    Statement statement = connection.createStatement();
                    String query = "SELECT * FROM CODAGETABLEBRUTE";

                    ResultSet resultat = statement.executeQuery(query);
                    int columnsNumber = resultat.getMetaData().getColumnCount()-1;
                    
                   ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
                    while(resultat.next()){
                            
                            ItemSet itemSet = new ItemSet();
                            for(int i=0; i<columnsNumber; i++){
                                    
                                    int val = ois.readInt();
                                    if(val == 1){
                                            
                                            String item = resultat.getMetaData().getColumnName(i+2);
                                            itemSet.getItems().add(item);
                                    }
                            }
                            DTransactionalTable.add(itemSet);
                    }
                    ois.close();
                    statement.close();
                    
            }catch(IOException e){
                    
                    e.printStackTrace();
            
            }catch(SQLException e){
                    
                    e.printStackTrace();
            }
            
            return DTransactionalTable;
    }
    
    public static Connection openConnection(){
            
            Connection connection = null;
            try{
                  
                    connection = DriverManager.getConnection(url, username, password);
          
            }catch (SQLException e){
                  
                    e.printStackTrace();
            }
            
            return connection;
    }
    
    public static void closeConnection(Connection connection){
    
            try{
                  
                    connection.close();
          
            }catch (SQLException e){
                  
                    e.printStackTrace();
            }
    }

    public double getMinSupValue(){
                
                
                if(minSup.getText().equals("")){
                    
                    return -1;                
                }                  
            
                return Double.parseDouble(minSup.getText());
        }
        
        public double getMinConflValue(){
                
                
                if(minConf.getText().equals("")){
                    
                    return -1;                
                }                  
            
                return Double.parseDouble(minConf.getText());
        }
        
}


