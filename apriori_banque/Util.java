package testfdd2;

        import java.io.File;
        import java.io.FileInputStream;
        import java.io.IOException;
        import java.io.ObjectInputStream;
        
        import java.io.ObjectOutputStream;
        
        import java.sql.Connection;
        import java.sql.ResultSet;
        import java.sql.SQLException;
        import java.sql.Statement;
        
        import java.util.ArrayList;
        import java.io.ObjectOutputStream;
        import java.io.FileOutputStream;

public class Util {
    
    public static File saveTransactionalTable(Connection connection){
            
            File file = new File("C:/JDeveloper/mywork/TestFDD2/TestFDD2/TableTransactionnelle.txt");
                            
            try{
                    
                    Statement statement = connection.createStatement();
                    String query = "SELECT * FROM CODAGETABLEBRUTE";

                    ResultSet resultat = statement.executeQuery(query);
                    int columnsNumber = resultat.getMetaData().getColumnCount()-1;
                    
                    ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
                    while(resultat.next()){
                            
                            for(int i=0; i<columnsNumber; i++){
                                                                    
                                    int val = resultat.getInt(i+2);
                                    oos.writeInt(val);
                            }
                    }
                    oos.close();
                    statement.close();
                    
            }catch(IOException e){
                    
                    e.printStackTrace();
            
            }catch(SQLException e){
                    
                    e.printStackTrace();
            }
            
            return file;
    }
}
