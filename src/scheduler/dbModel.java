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
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import static scheduler.EditUser._dtm;

/**
 *
 * @author Ayomitunde
 */
public class dbModel {

    private static String sql = null;

    public static void insertUser(String username, String password, String email, String dept, boolean isAdmin) throws SQLException, ClassNotFoundException {
        Connection conn = null;
        PreparedStatement stmt = null;
        password = Encryption.getMD5(password);
        try {
            conn = getConnection();
            sql = "insert into user(username, password, email, isAdmin) values(?, ?, ?, ?)";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.setString(3, email);
            stmt.setBoolean(4, isAdmin);
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
    
    public static void removeUser(String username) throws SQLException{
        Connection conn = null;
        PreparedStatement stmt = null;
        try{
            conn = getConnection();
            sql = "delete from user where username = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.executeUpdate();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(dbModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void deleteEvent(String eventName, String eventTime) throws SQLException{
        Connection conn = null;
        PreparedStatement stmt = null;
        try{
            conn = getConnection();
            sql = "delete from event where eventName = ? and eventTime = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, eventName);
            stmt.setString(2, eventTime);
            stmt.executeUpdate();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(dbModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static boolean logUser(String user, String pass) throws ClassNotFoundException, SQLException{
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String username = null;
        String password = null;
        pass = Encryption.getMD5(pass);
        try {
            conn = getConnection();
            sql = "select username, password from user where username = ? and password = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, user);
            stmt.setString(2, pass);
            rs = stmt.executeQuery();
            while (rs.next()) {
                username = rs.getString("username");
                password = rs.getString("password");
                if(username != null && password != null){
                    return true;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(dbModel.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (stmt != null && conn != null) {
                stmt.close();
                conn.close();
            }
        }
        return false;
    }
    
    
    public static void addUserDept(String username, String dept) throws ClassNotFoundException, SQLException{
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = getConnection();
            sql = "INSERT INTO UserDept (userId, deptId) VALUES (?, ?)";
            stmt = conn.prepareStatement(sql);
            int userId = findId(username, "User");
            int deptId = findId(dept, "Dept");
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


    public static void createEvent(String eventName, String eventDescr, String eventDate, String eventTime, String eventPriority, boolean rescheduled, String creator) throws SQLException, ClassNotFoundException {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = getConnection();
            sql = "insert into event(eventName, eventDescription, eventDate, eventTime, eventPriority, rescheduled, creator) values(?,?,?,?,?,?,?)";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, eventName);
            stmt.setString(2, eventDescr);
            stmt.setString(3, eventDate);
            stmt.setString(4, eventTime);
            stmt.setString(5, eventPriority);
            stmt.setBoolean(6, rescheduled);
            stmt.setString(7, creator);
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
    
    public static void makeAdmin(String username, boolean bool) throws SQLException{
        Connection conn = null;
        PreparedStatement stmt = null;
        try{
            conn = getConnection();
            sql = "update user set isAdmin = ? where username = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setBoolean(1, bool);
            stmt.setString(2, username);
            stmt.executeUpdate();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(dbModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static int findId(String name, String query) throws ClassNotFoundException, SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Integer id = null;
        try {
            conn = getConnection();
            if ("Event".equals(query)) {
                sql = "select eventId from Event where eventName = ?";
            } else if("User".equals(query)){
                sql = "select userId from user where username = ?";
            }else if("Dept".equals(query)){
                sql = "select deptId from Department where deptName = ?";
            }
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, name);
            rs = stmt.executeQuery();
            if (rs.next()) {
                if ("Event".equals(query)) {
                    id = rs.getInt("eventId");
                } else if("User".equals(query)){
                    id = rs.getInt("userId");
                }else if("Dept".equals(query)){
                    id = rs.getInt("deptId");
                }
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

    public static void showUsers(String un) throws ClassNotFoundException, SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String username = null;
        try {
            conn = getConnection();
            sql = "select username from user where username <> ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, un);
            rs = stmt.executeQuery();
            while (rs.next()) {
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
    
    public static void showList() throws ClassNotFoundException, SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String username = null;
        Boolean isAdmin = null;
        try {
            conn = getConnection();
            sql = "select username, isAdmin from user";
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();
            while (rs.next()) {
                username = rs.getString("username");
                isAdmin = rs.getBoolean("isAdmin");
                _dtm.addRow(new Object[]{username, isAdmin});
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
    
    public static boolean findUser(String username) throws ClassNotFoundException{
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        boolean foundUser = false;
        try{
            conn = getConnection();
            sql = "select username from user where username = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            rs = stmt.executeQuery();
            while(rs.next()){
                foundUser = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(dbModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return foundUser;
    }
    public static void authenticateUser(String username, String password){
        
    }

    public static void updateUserEvents(int userId) throws SQLException, ClassNotFoundException {
        ArrayList<Event> currUserEvents = GUI._userInfo.get(GUI._currentUser);
        currUserEvents.clear();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            sql = "select eventName, eventDescription, eventDate, eventTime, eventPriority, rescheduled from Event e "
                    + "inner join userevent ue on e.eventId = ue.eventId "
                    + "inner join user u on ue.userId = u.userId"
                    + " where u.userId = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, userId);
            rs = stmt.executeQuery();
            while (rs.next()) {
                String eventName = rs.getString("eventName");
                String eventDescr = rs.getString("eventDescription");
                String eventTime = rs.getString("eventTime");
                String eventDate = rs.getString("eventDate");
                String eventPriority = rs.getString("eventPriority");
                boolean rescheduled = rs.getBoolean("rescheduled");
                User creator = null; // should get the user
                Event e = new Event(eventName, eventDescr, eventDate, eventTime, creator);
                currUserEvents.add(e);
            }
            GUI._userInfo.put(GUI._currentUser, currUserEvents);
        } catch (SQLException ex) {
            Logger.getLogger(dbModel.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (stmt != null && conn != null) {
                stmt.close();
                conn.close();
            }
        }
    }

    public static void insertUserEvent(int userId, int eventId) throws SQLException, ClassNotFoundException {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = getConnection();
            sql = "insert into userevent (userId, eventId) values(?,?)";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, userId);
            stmt.setInt(2, eventId);
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
