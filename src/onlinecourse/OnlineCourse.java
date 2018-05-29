/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package onlinecourse;

import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JFrame;

/**
 *
 * @author HP
 */
public class OnlineCourse {
    
    public static Connection Conn(){
        try{
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection connect = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","SYSTEM","123456");
            return connect;
            }
        catch(Exception e)
        {
            return null;
        }
    }
    public static JFrame f=new JFrame();
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        frontPage f=new frontPage();
        f.setVisible(true);
        
    }
}
