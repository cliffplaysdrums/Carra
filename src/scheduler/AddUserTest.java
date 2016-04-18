/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scheduler;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Ayomitunde
 */
public class AddUserTest {
    String userDept = null;
    User newUser = null;

    private String makeRandChar() {
        String username = "";
        for (int i = 0; i < 10; i++) {
            int j = (int) (Math.random() * 25);
            j += 97;
            char b = (char) j;
            username += b;
        }
        return username;
    }
    
    private String makeDate(){
        String date = "";
        int month = (int)(Math.random() * 12 + 1);
        int day = (int)(Math.random() * 20 + 1);
        int year = 2016;
        date += month+"/"+day+"/"+year;
        return date;
    }

    @Test
    public void testAddLogusers() {
        try {
            String username = makeRandChar();
            String password = makeRandChar();
            byte[] encryptedPassword = null;
            byte[] salt = null;
            Encryption enc = new Encryption();
            salt = enc.generateSalt();
            encryptedPassword = enc.getEncryptedPassword(password, salt);
            String email = username+"@gmail.com";
            newUser = new User(username, encryptedPassword, salt, email);
            userDept = GUI._dept[(int)(Math.random() * 8)];
            int i = (int)(Math.random() * 1);
            boolean isAdmin = i > 1;
            dbModel.insertUser(username, password, email, userDept, isAdmin);
            dbModel.addUserDept(username, userDept);
            ArrayList<User> departmentUsers = GUI._allDepts.get(userDept);
                    if (departmentUsers == null) {
                        departmentUsers = new ArrayList<>();
                    }
                    departmentUsers.add(newUser);
                    GUI._allDepts.put(userDept, departmentUsers);                    
                    GUI._userInfo.put(newUser, new ArrayList<>());
                    Serialize.saveUserFiles(Serialize._fileLocation);
                    assertTrue(dbModel.logUser(username, password));
                    } catch (NoSuchAlgorithmException | InvalidKeySpecException | SQLException | ClassNotFoundException ex) {
            Logger.getLogger(AddUserTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Test
    public void createEvent(){
        try{
            String eventName = makeRandChar();
            String eventDesc = makeRandChar()+" "+makeRandChar();
            String date = makeDate();
            String time = "11:05:50";
            Event e = new Event(eventName, eventDesc, date, time, newUser);
            System.out.println(eventName);
            dbModel.createEvent(eventName, eventDesc, date, time, "low", false, newUser.getUsername());
            assertTrue(dbModel.findEvent(eventName));
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(AddUserTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
