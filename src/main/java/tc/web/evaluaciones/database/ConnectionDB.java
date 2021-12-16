package tc.web.evaluaciones.database;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionDB{
    public ConnectionDB(){
        
    }

    public static Connection createConnection(){
        Connection connection = null;
        try{
            connection = DriverManager.getConnection("jdbc:mariadb://db4free.net:3306/evaluaciones1", "nadiaitzel", "Lovato01");
        }catch(Exception exception){

        }
        return connection;
    }
}
