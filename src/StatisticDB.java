import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * 
 * @author Bjoern Gross
 * 
 * A database for statistical purposes like total number of messages received,
 * average number of messages per user, users ranked by total number of messages
 * or most received message
 *
 * @TODO Implement calculations, right now only messages are collected
 * 
 */

public class StatisticDB extends Database {
	private static final String TABLEMESSAGES = "Messages";
	private static final String COLUSERNAME = "userName";
	private static final String COLUSERID = "userId";
	private static final String COLMESSAGE = "message";
	private static final String COLDATE = "date";
	
	public StatisticDB() {
		super("statistics");
		//if the database was just created, it will be empty, add the tables
		createDatabase();
	}
	
	private void createDatabase(){
		String query = "CREATE TABLE IF NOT EXISTS "+TABLEMESSAGES+" ( "
				+"`id`	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, "
				+ COLUSERNAME+"	TEXT, "
				+ COLUSERID+"	INTEGER, "
				+ COLMESSAGE+"	TEXT, "
				+ COLDATE+"	INTEGER"
				+");";
		super.update(query);
	}
	
	public int insertMessage(String senderUserName, int senderUserId, String message, int date){
		String sql = "INSERT INTO "+TABLEMESSAGES+" ("+COLUSERNAME+", "+COLUSERID+", "+COLMESSAGE+", "+COLDATE+") VALUES (?, ?, ?, ?);";
		int result = 0;
		try{
			PreparedStatement pstmt = super.getConnection().prepareStatement(sql);
			pstmt.setString(1, senderUserName);
			pstmt.setInt(2, senderUserId);
			pstmt.setString(3, message);
			pstmt.setInt(4, date);
			result = pstmt.executeUpdate();
		}catch(SQLException sqle){
			sqle.printStackTrace();
		}
		return result;
	}

}
