package testfdd2;

        import java.awt.BorderLayout;
        import java.awt.Color;
        import java.awt.Font;
        import java.awt.event.ActionEvent;
        import java.awt.event.ActionListener;
        import java.sql.SQLException;
        
        import javax.swing.BorderFactory;
        import javax.swing.JButton;
        import javax.swing.JFrame;
        import javax.swing.JLabel;
        import javax.swing.JScrollPane;
        import javax.swing.JTable;
        import javax.swing.JTextArea;
        import javax.swing.ScrollPaneConstants;

    public class WelcomeWindow extends JFrame {

	private JButton go = new JButton("Go To Application");
	private JLabel welcome = new JLabel();
        private JLabel theme = new JLabel();
        private JLabel theme2 = new JLabel();
        private JLabel names = new JLabel();
        private JLabel student1 = new JLabel();
        private JLabel student2 = new JLabel();
        private JLabel encadrant = new JLabel();
        private JLabel universityYear = new JLabel();
	
	
	public WelcomeWindow(){
		
		this.setTitle("Bienvenue");
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
		
		this.getContentPane().add(go);
		go.setBounds(620, 565, 150, 30);
            
		this.getContentPane().add(welcome);
		welcome.setBounds(200, 45, 750, 60);
                welcome.setFont(new Font("Arial", Font.BOLD + Font.ITALIC, 60));
                welcome.setForeground(new Color(185, 100, 0));
		welcome.setText("   Bienvenue");
                
                this.getContentPane().add(theme);
                theme.setBounds(20, 130, 750, 35);
                theme.setFont(new Font("Arial", Font.BOLD, 32));
                theme.setForeground(new Color(0, 0, 255));
                theme.setText("    Réalisation d'une application data mining en");
                
                this.getContentPane().add(theme2);
                theme2.setBounds(20, 185, 750, 35);
                theme2.setFont(new Font("Arial", Font.BOLD, 32));
                theme2.setForeground(new Color(0, 0, 255));
                theme2.setText("   utilisant la technique des règles d'association");
                    
                this.getContentPane().add(names);
                names.setBounds(20, 270, 250, 40);
                names.setFont(new Font("Arial", Font.BOLD, 25));
                names.setForeground(new Color(0, 155, 0));
                names.setText("    Réalisée Par :");
        
                this.getContentPane().add(student1);
                student1.setBounds(100, 310, 300, 40);
                student1.setFont(new Font("Arial", Font.BOLD + Font.ITALIC, 20));
                student1.setForeground(new Color(0, 155, 0));
                student1.setText("   - Seddiki Halima");
                 
                this.getContentPane().add(student2);
                student2.setBounds(100, 340, 300, 40);
                student2.setFont(new Font("Arial", Font.BOLD + Font.ITALIC, 20));
                student2.setForeground(new Color(0, 155, 0));
                student2.setText("   - Seghier Wassila");
                    
                this.getContentPane().add(encadrant);
                encadrant.setBounds(260, 410, 750, 40);
                encadrant.setFont(new Font("Arial", Font.BOLD + Font.ITALIC, 20));
                encadrant.setForeground(new Color(0, 155, 0));
                encadrant.setText("Encadrant : Mme. Betouati.F");
                    
                this.getContentPane().add(universityYear);
                universityYear.setBounds(257, 520, 750, 40);
                universityYear.setFont(new Font("Arial", Font.BOLD + Font.ITALIC, 18));
                universityYear.setText("Année universitaire : 2014-2015");
                
                final WelcomeWindow temp = this;
                go.addActionListener(new ActionListener(){
			
			public void actionPerformed(ActionEvent e){
				
                            temp.setVisible(false);
                            DataPreparationWindow dataPreparationWindow = new DataPreparationWindow();
			}
		});                
        }
}

