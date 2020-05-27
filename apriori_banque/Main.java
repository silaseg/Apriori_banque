package testfdd2;

	import java.sql.Date;
	import java.sql.ResultSet;
	import java.sql.SQLException;
	import java.sql.Statement;

        import java.awt.Color;
        import java.awt.Image;
        import java.io.File;
        import java.io.IOException;
        
        import java.sql.CallableStatement;
        import java.sql.Connection;
        
        import java.sql.DatabaseMetaData;
        import java.sql.DriverManager;
        
        import java.sql.ResultSetMetaData;
        
        import java.util.Vector;
        
        import javax.imageio.ImageIO;
        import javax.swing.ImageIcon;
        import javax.swing.JLabel;
        import javax.swing.JOptionPane;
        import javax.swing.JProgressBar;
        import javax.swing.JScrollPane;
        import javax.swing.JTable;
        import javax.swing.JWindow;
        import javax.swing.table.DefaultTableModel;

public class Main{

	        
	        public static final String DBURL = "jdbc:oracle:thin:@localhost:1521:xe";
	        public static final String DBUSER = "datamining2015";
	        public static final String DBPASS = "datamining2015";
                public static int size;
	        
        public static void main(String[] args) throws SQLException {

            Connection con = DriverManager.getConnection(DBURL, DBUSER, DBPASS);            
            Statement statement = con.createStatement();
        
            String sqlProcedureCall;                     
            CallableStatement statementProcedure;
            
            sqlProcedureCall = "{call APPEL_GENERAL()}";                     
            statementProcedure = con.prepareCall(sqlProcedureCall);
            statementProcedure.executeQuery();
            
            statement.close();
            con.close();
            
            WelcomeWindow welcomWindow = new WelcomeWindow();

    }      
	
    public static DefaultTableModel buildTableModel(ResultSet rs)
            throws SQLException {

        ResultSetMetaData metaData = rs.getMetaData();

        Vector<String> columnNames = new Vector<String>();
        int columnCount = metaData.getColumnCount();
        for (int column = 1; column <= columnCount; column++) {
            columnNames.add(metaData.getColumnName(column));
        }

        Vector<Vector<Object>> data = new Vector<Vector<Object>>();
        while (rs.next()) {
            Vector<Object> vector = new Vector<Object>();
            for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                vector.add(rs.getObject(columnIndex));
            }
            data.add(vector);
        }

        return new DefaultTableModel(data, columnNames);

    }

    public static String getCommentField(String field){
        
            if( field.equals("INF18") ||                        
                field.equals("ENTRE18ET30") || 
                field.equals("ENTRE30ET35") || 
                field.equals("ENTRE35ET55") || 
                field.equals("ENTRE55ET70") || 
                field.equals("SUP70") ){
                
                return "Age";
            
            }else if( field.equals("ANGEM") || 
                    field.equals("ANSEJ") ||
                    field.equals("CNAC") ){
                
                    return "Classe";
            
                }else if( field.equals("INF8") ||
                        field.equals("SUP8") ){
                    
                        return "Duree";
                    
                        }else if (field.equals("INF100") ||
                                field.equals("ENTRE100ET600") ||
                                field.equals("ENTRE600ET1M") ||
                                field.equals("SUP1M") ){
                            
                                return "Somme";
                            
                            }else if( field.equals("INF70") ||
                                    field.equals("ENTRE70ET90") ||
                                    field.equals("SUP90") ){
                                
                                    return "TauxBonifier";
                                
                                }else if( field.equals("OUI") ||
                                        field.equals("NON")
                                        ){
                                        
                                        return "Decision";
                                    }
            
    return "?";
}

}

