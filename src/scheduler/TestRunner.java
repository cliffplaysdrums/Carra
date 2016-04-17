/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scheduler;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

/**
 *
 * @author Ayomitunde
 */
public class TestRunner {
    public static void main(String[] args){
        Result rs = JUnitCore.runClasses(AddUserTest.class);
        for(Failure failure : rs.getFailures()){
            System.err.println(failure.toString());
        }
    }
    
}
