package ejd;

/** 
 * Author:  Younggil Lee
 * Student ID: 991 395 505
 * Description: Make connection or disconnection between java and database 
 **/

import java.sql.*;

public class JdbcHelper {
    // instance variables
    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;
    private int updateCount = 0;
    
    //no-arg ctor: init instance vars
    public JdbcHelper() {
        connection = null;
        statement = null;
        resultSet = null;
    }

    public boolean connect(String url, String user, String pass) {
        
        //default value
        boolean connected = false;
        
        //load Jdbc Driver
        initJdbcDriver(url);
        
        try {
            this.connection = DriverManager.getConnection(url, user, pass);
            this.statement = connection.createStatement();
            connected = true;
        } catch (SQLException e) {
            System.err.println(e.getSQLState() + ":" + e.getMessage());
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        return connected;
    }
    
    // disconnect resultSet, Statment,Connection using a close method
    public void disconnect() {
         try { resultSet.close(); } catch (Exception e) { }
        try { statement.close(); } catch (Exception e) { } 
        try { connection.close(); } catch (Exception e) { }
    }
    
    //execute query. such as SELECT command
    //it returns ResultSet. IF failed, reuturn null
    public ResultSet query(int radioNumber, String sql) {
        resultSet = null;
        try {
             if(connection == null || connection.isClosed()){
                 System.err.println(" No connection established in query() yet");
                 return resultSet;
             }
             
             // execute 
            PreparedStatement ps = connection.prepareStatement(sql);
            
            // check user input and select course
            // set string course name to ?
            if (radioNumber == 1) {
                ps.setString(1, "PROG10000");
            } else if (radioNumber == 2) {
                ps.setString(1, "DBAS20000");
            } else {
                ps.setString(1, "MATH30000");
            }
            
            // store return value to resultset 
            resultSet = ps.executeQuery();
        } catch (SQLException ex) {

            System.out.println(ex.getMessage());
        }

        return resultSet;
    }
    
    //execute update for  CREATE, INSERT INTO, UPDATE, 
    //etc comn which does return ResultSet. If successful, 
    //return 0 or postive number otherwise, return -1 
    public int update(String sql) {
        
          // default return value
        int result = -1;
        
       // validate connection
        try {
             if(connection == null || connection.isClosed()){
                 System.err.println(" No connection established yet");
                 return result;
             }     
             
             //excute query
            result = statement.executeUpdate(sql);
        } catch (SQLException e) {
            System.err.println(e.getSQLState() + ":" + e.getMessage());
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
      return result;   
    }    
    
    
    
       private void initJdbcDriver(String url) {
        // test various databases
        try {
            if (url.contains("jdbc:mysql")) {
                Class.forName("com.mysql.jdbc.Driver");
            } else if (url.contains("jdbc:oracle")) {
                Class.forName("oracle.jdbc.OracleDriver");
            } else if (url.contains("jdbc:derby")) {
                Class.forName("org.apache.derby.jdbc.ClientDriver");
            } else if (url.contains("jdbc:db2")) {
                Class.forName("com.ibm.db2.jcc.DB2Driver");
            } else if (url.contains("jdbc:postgresql")) {
                Class.forName("org.postgresql.Driver");
            } else if (url.contains("jdbc:sqlite")) {
                Class.forName("org.sqlite.JDBC");
            } else if (url.contains("jdbc:sqlserver")) {
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            } else if (url.contains("jdbc:sybase")) {
                Class.forName("sybase.jdbc.sqlanywhere.IDriver");
            }
            // add other databases here
        } catch (ClassNotFoundException e) {
            System.err.println(e.getMessage());
        }
    }
    
}
