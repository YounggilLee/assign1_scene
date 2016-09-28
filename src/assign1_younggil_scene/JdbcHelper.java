package assign1_younggil_scene;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author yglee
 */
import java.sql.*;


public class JdbcHelper {

    Connection conn;
    Statement stmt;
    ResultSet rs;
    
    int updateCount = 0;
    
        public JdbcHelper() {
        conn = null;
        stmt = null;
        rs = null;
    }

    public boolean connect(String url, String user, String pass) {

        boolean result = false;
        try {
            this.conn = DriverManager.getConnection(url, user, pass);
            this.stmt = conn.createStatement();
            System.out.println("connection ok");
            result = true;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
                    
        }

        return result;

//        this.conn = DriverManager.getConnection(url, user, pass);
//        this.stmt = conn.createStatement();
//       return conn.isValid(0);
    }

    public void diconnect() {
        if (rs != null) {
            try {
                rs.close();
            } catch (Exception ex) {
            }
        }
        if (stmt != null) {
            try {
                stmt.close();
            } catch (Exception ex) {
            }

        }
        if (conn != null) {
            try {
                conn.close();
            } catch (Exception ex) {
            }

        }
    }

    public ResultSet query(int radioNumber,String sql) {
           
        try {
           // rs = stmt.executeQuery(sql);
           PreparedStatement ps = conn.prepareStatement(sql);  
           if (radioNumber == 1)
               ps.setString(1, "PROG10000"); //firstName
           else if (radioNumber == 2)
                ps.setString(1, "DBAS20000"); //firstName
           else
               ps.setString(1, "MATH30000"); //firstName
              rs =  ps.executeQuery();
        }   catch (SQLException ex) {

            System.out.println(ex.getMessage());
        }

        return rs;
    }

    public int update(String sql) {
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            updateCount = ps.executeUpdate();
        } catch (SQLException ex) {
            // Logger.getLogger(JdbcHelper.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.getMessage());
        }
        return updateCount;
    }



}
