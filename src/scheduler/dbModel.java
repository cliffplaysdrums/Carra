/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scheduler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ayomitunde
 */
public class dbModel {

    private static String sql = null;

    public static void insertUser(String username, String password, String email, String dept, boolean isAdmin) throws SQLException, ClassNotFoundException {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = getConnection();
            sql = "insert into user(username, password, email, isAdmin) values(?, ?, ?, ?)";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.setString(3, email);
            stmt.setBoolean(4, isAdmin);
            stmt.executeUpdate();
            sql = "INSERT INTO Department (deptName) VALUES (?)";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, dept);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(dbModel.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (stmt != null && conn != null) {
                stmt.close();
                conn.close();
            }

        }
    }
    
    public static void createEvent(String eventName, String eventTime, String eventPriority, boolean rescheduled, String creator) throws SQLException, ClassNotFoundException{
        Connection conn = null;
        PreparedStatement stmt = null;
        try{
            conn = getConnection();
            sql = "insert into event(eventName, eventTime, eventPriority, rescheduled, creator) values(?,?,?,?,?)";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, eventName);
            stmt.setString(2, eventTime);
            stmt.setString(3, eventPriority);
            stmt.setBoolean(4, rescheduled);
            stmt.setString(5, creator);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(dbModel.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (stmt != null && conn != null) {
                stmt.close();
                conn.close();
            }
        }
        
    }

    public static int findId(String name, String query) throws ClassNotFoundException, SQLException{
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Integer id = null;
        try{
            conn = getConnection();
            if("Event".equals(query)){
                sql = "select eventId from Event where eventName = ?";
            }else{
                sql = "select userId from user where username = ?";
            }
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, name);
            rs = stmt.executeQuery();
            if("Event".equals(query)){
                id = rs.getInt("eventId");
            }else{
                id = rs.getInt("userId");
            }
            id = rs.getInt(1);
        } catch (SQLException ex) {
            Logger.getLogger(dbModel.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (stmt != null && conn != null) {
                stmt.close();
                conn.close();
            }
        }
        return id;
    }
    
    public static void showUsers() throws ClassNotFoundException, SQLException{
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String username = null;
        try{
            conn = getConnection();
            sql = "select username from user";
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();
            while(rs.next()){
                username = rs.getString("username");
                CreateEvent._userModel.setValueAt(username, CreateEvent._rowCounter, 0);
                CreateEvent._rowCounter++;
            }
        } catch (SQLException ex) {
            Logger.getLogger(dbModel.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (stmt != null && conn != null) {
                stmt.close();
                conn.close();
            }
        }
    }
    public static void insertUserEvent(int userId, int deptId) throws SQLException, ClassNotFoundException{
        Connection conn = null;
        PreparedStatement stmt = null;
        try{
            conn = getConnection();
            sql = "insert into userevent (userId, deptId) values(?,?)";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, userId);
            stmt.setInt(2, deptId);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(dbModel.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (stmt != null && conn != null) {
                stmt.close();
                conn.close();
            }
        }
    }
    private static Connection getConnection() throws ClassNotFoundException {
        Connection conn = null;
        try {
            String driver = "oracle.jdbc.driver.OracleDriver";
            String host = "jdbc:mysql://localhost:3306/scheduler";
            String userName = "root";
            String password = "";
            Class.forName(driver);
            conn = DriverManager.getConnection(host, userName, password);
        } catch (SQLException ex) {
            Logger.getLogger(dbModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return conn;
    }

}
