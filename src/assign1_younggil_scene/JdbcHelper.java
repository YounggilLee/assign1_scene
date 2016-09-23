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

import java.util.logging.Level;
import java.util.logging.Logger;

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
            // Logger.getLogger(JdbcHelper.class.getName()).log(Level.SEVERE, null, ex);           
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

    public ResultSet query(String sql) {
        try {
           rs = stmt.executeQuery(sql);
           
        } catch (SQLException ex) {
            // Logger.getLogger(JdbcHelper.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.getMessage());
        }

        return rs;
    }

    public int update(String sql) {
        try {
             PreparedStatement ps = conn.prepareStatement(sql);
             
              // set params
            ps.setInt(1,6); //id
            ps.setString(2, "Grace "); //firstName
            ps.setString(3, "Hopper "); //lastName
                        //execute sql to update db 
           
            updateCount = ps.executeUpdate(sql);
        } catch (SQLException ex) {
            // Logger.getLogger(JdbcHelper.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.getMessage());
        }
        return updateCount;
    }

    public void printResult(ResultSet resultSet) {
        //Student std = new Student(); 
        try {
            while (resultSet.next()) {

               // students.add(new Student(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3)));
                      
            }
        } catch (SQLException ex) {
            Logger.getLogger(JdbcHelper.class.getName()).log(Level.SEVERE, null, ex);
        }

       
    }

}
