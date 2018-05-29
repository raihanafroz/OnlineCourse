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
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author hp
 */
public class createUser {
    static boolean addAdvisor(int advisorId,String firstName,String lastName,String password,String securityKey,String email,String contactNo,String gender,String path){
        Connection con=OnlineCourse.Conn();
        PreparedStatement ps;
        try {
            FileInputStream fin = null;
            try {
                fin = new FileInputStream(path);
            } catch (FileNotFoundException ex) {
                
            }
            ps=con.prepareStatement("insert into ADVISOR(ADVISORID,FIRSTNAME,LASTNAME,PASSWORD,SECURITYKEY,EMAIL,CONTACTNO,GENDER,pic) values (?,?,?,?,?,?,?,?,?)");
            ps.setInt(1, advisorId);
            ps.setString(2, firstName);
            ps.setString(3, lastName);
            ps.setString(4, password);
            ps.setString(5, securityKey);
            ps.setString(6, email);
            ps.setString(7, contactNo);
            ps.setString(8, gender);
            try {
                ps.setBinaryStream(9, fin, fin.available());
            } catch (IOException ex) {
            }
            if(ps.executeUpdate()>0){
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(createUser.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    
    static boolean addTeacher(int teacherId,String firstName,String lastName,String password,String securityKey,String email,String contactNo,String gender,String firstChoice,String secondChoice,String pic){
        Connection con=OnlineCourse.Conn();
        PreparedStatement ps;
        try {
            FileInputStream fin = null;
            try {
                fin = new FileInputStream(pic);
            } catch (FileNotFoundException ex) {
                
            }
            ps=con.prepareStatement("insert into TEACHER(TEACHERID,FIRSTNAME,LASTNAME,PASSWORD,SECURITYKEY,EMAIL,CONTACTNO,GENDER,FIRSTCHOICE,SECONDCHOICE,PIC) values (?,?,?,?,?,?,?,?,?,?,?)");
            ps.setInt(1, teacherId);
            ps.setString(2, firstName);
            ps.setString(3, lastName);
            ps.setString(4, password);
            ps.setString(5, securityKey);
            ps.setString(6, email);
            ps.setString(7, contactNo);
            ps.setString(8, gender);
            ps.setString(9, firstChoice);
            ps.setString(10, secondChoice);
            try {
                ps.setBinaryStream(11, fin, fin.available());
            } catch (IOException ex) {
            }
            if(ps.executeUpdate()>0){
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(createUser.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    
    static boolean addStudent(int studentId,String firstName,String lastName,String password,String securityKey,String religion,String gender,String email,String contactNo,String district,String birthday,String department,int year,String semester,String pic_path){
        
        Connection con=OnlineCourse.Conn();
        PreparedStatement ps;
        try {
            FileInputStream fin = null;
            try {
                fin = new FileInputStream(pic_path);
            } catch (FileNotFoundException ex) {
                
            }
            ps=con.prepareStatement("insert into STUDENT(STUDENTID,FIRSTNAME,LASTNAME,PASSWORD,SECURITYKEY,RELIGION,GENDER,EMAIL,CONTACTNO,DISTRICT,BIRTHDAY,DEPARTMENT,YEAR,SEMESTER,PIC) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
            ps.setInt(1, studentId);
            ps.setString(2, firstName);
            ps.setString(3, lastName);
            ps.setString(4, password);
            ps.setString(5, securityKey);
            ps.setString(6, religion);
            ps.setString(7, gender);
            ps.setString(8, email);
            ps.setString(9, contactNo);
            ps.setString(10, district);
            ps.setString(11, birthday);
            ps.setString(12, department);
            ps.setInt(13, year);
            ps.setString(14, semester);
            try {
                ps.setBinaryStream(15, fin, fin.available());
            } catch (IOException ex) {
            }
            if(ps.executeUpdate()>0){
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(createUser.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    static boolean deleteUser(int whatId,String whatType){
        String whatTypeId=whatType.concat("id");
        Connection con=OnlineCourse.Conn();
        PreparedStatement ps;
        try {
            ps=con.prepareStatement("delete from "+whatType+" where "+whatTypeId+"=?" );
            ps.setInt(1, whatId);
            if(ps.executeUpdate()>0){
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(createUser.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
}
