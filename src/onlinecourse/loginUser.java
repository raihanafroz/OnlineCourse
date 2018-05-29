/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package onlinecourse;

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
public class loginUser {
    static boolean loginAdmin(int adminId,String password){
        boolean b=false;
        Connection con=OnlineCourse.Conn();
        try {
            Statement st=con.createStatement();
            ResultSet rs=st.executeQuery("select *from ADMIN where ADMINID='"+adminId+"' and password='"+password+"'");
            int count=0;
            while(rs.next()){
                count=count+1;
            }
            if(count==1){
                b=true;
            }
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(loginUser.class.getName()).log(Level.SEVERE, null, ex);
        }
        return b;
    }
    static boolean loginAdvisor(int advisorId,String password){
        boolean b=false;
        Connection con=OnlineCourse.Conn();
        try {
            Statement st=con.createStatement();
            ResultSet rs=st.executeQuery("select *from ADVISOR where advisorId='"+advisorId+"' and password='"+password+"'");
            int count=0;
            while(rs.next()){
                count=count+1;
            }
            if(count==1){
                b=true;
            }
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(loginUser.class.getName()).log(Level.SEVERE, null, ex);
        }
        return b;
    }
    static boolean loginTeacher(int teacherId,String password){
        boolean b=false;
        Connection con=OnlineCourse.Conn();
        try {
            Statement st=con.createStatement();
            ResultSet rs=st.executeQuery("select *from Teacher where teacherId='"+teacherId+"' and password='"+password+"'");
            int count=0;
            while(rs.next()){
                count=count+1;
            }
            if(count==1){
                b=true;
            }
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(loginUser.class.getName()).log(Level.SEVERE, null, ex);
        }
        return b;
    }
    static boolean loginStudent(int studentId,String password){
        boolean b=false;
        Connection con=OnlineCourse.Conn();
        try {
            Statement st=con.createStatement();
            ResultSet rs=st.executeQuery("select *from student where studentId='"+studentId+"' and password='"+password+"'");
            int count=0;
            while(rs.next()){
                count=count+1;
            }
            if(count==1){
                b=true;
            }
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(loginUser.class.getName()).log(Level.SEVERE, null, ex);
        }
        return b;
    }

    static boolean checkAdvisor(int advisorId,String securityKey){
        boolean b=false;
        Connection con=OnlineCourse.Conn();
        try {
            Statement st=con.createStatement();
            ResultSet rs=st.executeQuery("select *from ADVISOR where advisorId='"+advisorId+"' and securityKey='"+securityKey+"'");
            int count=0;
            while(rs.next()){
                count=count+1;
            }
            if(count==1){
                b= true;
            }
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(loginUser.class.getName()).log(Level.SEVERE, null, ex);
        }
        return b;
    }
    static boolean checkTeacher(int teacherId,String securityKey){
        boolean b=false;
        Connection con=OnlineCourse.Conn();
        try {
            Statement st=con.createStatement();
            ResultSet rs=st.executeQuery("select *from TEACHER where TEACHERID='"+teacherId+"' and SECURITYKEY='"+securityKey+"'");
            int count=0;
            while(rs.next()){
                count=count+1;
            }
            if(count==1){
                b=true;
            }
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(loginUser.class.getName()).log(Level.SEVERE, null, ex);
        }
        return b;
    }
    static boolean checkStudent(int studentId,String securityKey){
        boolean b=false;
        Connection con=OnlineCourse.Conn();
        try {
            Statement st=con.createStatement();
            ResultSet rs=st.executeQuery("select *from student where studentId='"+studentId+"' and securityKey='"+securityKey+"'");
            int count=0;
            while(rs.next()){
                count=count+1;
            }
            if(count==1){
                b= true;
            }
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(loginUser.class.getName()).log(Level.SEVERE, null, ex);
        }
        return b;
    }

    static boolean updateAdvisor(int advisorId,String password,String securityKey){
        boolean b=false;
        Connection con=OnlineCourse.Conn();
        PreparedStatement ps;
        try {
                ps=con.prepareStatement("update advisor set password=? where advisorid=? and securityKey=?");
                ps.setString(1, password);
                ps.setInt(2, advisorId);
                ps.setString(3, securityKey);
                if(ps.executeUpdate()>0){
                    b=true;
                }
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(loginUser.class.getName()).log(Level.SEVERE, null, ex);
            }
        return b;
    }
    static boolean updateTeacher(int teacherId,String password,String securityKey){
        boolean b=false;
        Connection con=OnlineCourse.Conn();
        PreparedStatement ps;
        try {
                ps=con.prepareStatement("update teacher set password=? where teacherId=? and securityKey=?");
                ps.setString(1, password);
                ps.setInt(2, teacherId);
                ps.setString(3, securityKey);
                if(ps.executeUpdate()>0){
                    b=true;
                }
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(loginUser.class.getName()).log(Level.SEVERE, null, ex);
            }
        return b;
    }
    static boolean updateStudent(int studentId,String password,String securityKey){
        boolean b=false;
        Connection con=OnlineCourse.Conn();
        PreparedStatement ps;
        try {
                ps=con.prepareStatement("update student set password=? where studentId=? and securityKey=?");
                ps.setString(1, password);
                ps.setInt(2, studentId);
                ps.setString(3, securityKey);
                if(ps.executeUpdate()>0){
                    b=true;
                }
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(loginUser.class.getName()).log(Level.SEVERE, null, ex);
            }
        return b;
    }
    
}
