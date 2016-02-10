import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Database{
  	private Connection con = null;
  	private String name;
	
  	public Database(String database){
  		try {
  			Class.forName("org.sqlite.JDBC");
  			name = database;
  			con = DriverManager.getConnection("jdbc:sqlite:"+name+".db");
  			
		} catch (Exception se) {
			System.out.println("Error:" + se);
			se.printStackTrace();
		}
  	}
  	
  	public Connection getConnection(){
  		return con;
  	}
  	
  	public int update(String query){
  		int result = -1;
  		try{
  			Statement stmt = con.createStatement();
  			result = stmt.executeUpdate(query);
  			stmt.close();
  		}catch(Exception e){
  			e.printStackTrace();
  		}
  		return result;
  	}
  	
  	public void close(){
  		try
	      {
			if (con != null) con.close();	
			System.out.println("Closed the database '"+name+"'");
		  }catch(SQLException e){
	        // connection close failed.
	        System.err.println(e);
	      }
  	}
 
}
