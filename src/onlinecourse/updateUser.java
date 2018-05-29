/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package onlinecourse;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hp
 */
public class updateUser {
    static boolean updatePicture(int whatId,String whatType,String path){
        String typeId=whatType.concat("id");
        Connection con=OnlineCourse.Conn();
        PreparedStatement ps;
        try {
            FileInputStream fin = null;
            try {
                fin = new FileInputStream(path);
            } catch (FileNotFoundException ex) {

            }
            ps=con.prepareStatement("update "+whatType+" set PIC=? where "+typeId+"='"+whatId+"'");
            try {
                ps.setBinaryStream(1, fin, fin.available());
            } catch (IOException ex) {
            
            }
            if(ps.executeUpdate()>0){
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(updateUser.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    static boolean changePassword(int whatId,String whatType,String oldPassword,String newPassword){
        String typeId=whatType.concat("id");
        Connection con=OnlineCourse.Conn();
        PreparedStatement ps;
        try {
            ps=con.prepareStatement("update "+whatType+" set password=? where "+typeId+"='"+whatId+"'and password='"+oldPassword+"'");
            ps.setString(1, newPassword);
            if(ps.executeUpdate()>0){
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(updateUser.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    static boolean changeSecurityKey(int whatId, String whatType, String securityKey){
        String typeId=whatType.concat("id");
        Connection con=OnlineCourse.Conn();
        PreparedStatement ps;
        try {
            ps=con.prepareStatement("update "+whatType+" set securitykey=? where "+typeId+"='"+whatId+"'");
            ps.setString(1, securityKey);
            if(ps.executeUpdate()>0){
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(updateUser.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    static boolean changeAdvisorDetails(int whatId,String firstName,String lastName,String email,String contactNo,String gender){
        Connection con=OnlineCourse.Conn();
        try {
            Statement s=con.createStatement();
            int rs=s.executeUpdate("update ADVISOR set FIRSTNAME='"+firstName+"',LASTNAME='"+lastName+"',EMAIL='"+email+"',CONTACTNO='"+contactNo+"',GENDER='"+gender+"' where ADVISORID='"+whatId+"'");
            if(rs>0){
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(updateUser.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    static boolean changeTeacherDetails(int whatId,String firstName,String lastName,String email,String contactNo,String gender,String firstChoice,String secondChoice){
        Connection con=OnlineCourse.Conn();
        try {
            Statement s=con.createStatement();
            int rs=s.executeUpdate("update Teacher set FIRSTNAME='"+firstName+"',LASTNAME='"+lastName+"',EMAIL='"+email+"',CONTACTNO='"+contactNo+"',GENDER='"+gender+"',firstchoice='"+firstChoice+"',secondchoice='"+secondChoice+"' where TEACHERID='"+whatId+"'");
            if(rs>0){
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(updateUser.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    static boolean changeStudentDetails(int whatId,String firstName,String lastName,String religion,String email,String contactNo,String district){
        Connection con=OnlineCourse.Conn();
        try {
            Statement s=con.createStatement();
            int rs=s.executeUpdate("update STUDENT set FIRSTNAME='"+firstName+"',LASTNAME='"+lastName+"',RELIGION='"+religion+"',EMAIL='"+email+"',CONTACTNO='"+contactNo+"',DISTRICT='"+district+"' where STUDENTID='"+whatId+"'");
            if(rs>0){
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(updateUser.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    static boolean setAdvisor_perBatch(int whatId,String batch){
        Connection con=OnlineCourse.Conn();
        try {
            Statement s=con.createStatement();
            int rs=s.executeUpdate("update ADVISOR set BATCH='"+batch+"' where ADVISORID='"+whatId+"'");
            if(rs>0){
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(updateUser.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    static boolean notify(String batch,String semester){
        Connection con=OnlineCourse.Conn();
        PreparedStatement ps; 
        try {
            ps=con.prepareStatement("Select *from COURSEOFFER where BATCH='"+batch+"' and SEMESTER='"+semester+"'");
            ResultSet rs=ps.executeQuery();
            while(rs.next()){
                con.close();
                return true;
            }
        } catch (SQLException ex) {
                Logger.getLogger(updateUser.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
}
