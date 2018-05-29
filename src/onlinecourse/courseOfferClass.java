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
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author hp
 */
public class courseOfferClass {
    static void fillComboBox_course(JComboBox combo){
        Connection con=OnlineCourse.Conn();
        PreparedStatement ps; 
        try {
                ps=con.prepareStatement("select *from courseDetails");
                ResultSet rs=ps.executeQuery();
                while(rs.next()){
                    combo.addItem(rs.getString(2));
                }
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(courseOfferClass.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
    static void fillComboBox_teacher(JComboBox combo,String sub){
        combo.removeAllItems();
        Connection con=OnlineCourse.Conn();
        PreparedStatement ps; 
        combo.addItem("        ----Select----");
        try {
            ps=con.prepareStatement("select DISTINCT(FIRSTNAME),LASTNAME,TEACHERID from TEACHER where (FIRSTCHOICE='"+sub+"' or SECONDCHOICE='"+sub+"')");
            ResultSet rs=ps.executeQuery();
            while(rs.next()){
                combo.addItem(rs.getString(1).concat(" "+rs.getString(2)+"("+rs.getInt(3)+")"));
            }
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(courseOfferClass.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    static String getCourseCode(String courseName){
        String courseCode="";
        Connection con=OnlineCourse.Conn();
        PreparedStatement ps;
        try {
            ps=con.prepareStatement("select *from courseDetails where CourseName='"+courseName+"'");
            ResultSet rs=ps.executeQuery();
            while(rs.next()){
                courseCode=rs.getString(4);
            }
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(courseOfferClass.class.getName()).log(Level.SEVERE, null, ex);
        }
        return courseCode;
    }
    
    static void fillComboBox_setTeacherAsAAdvisor(JComboBox combo){
        combo.removeAllItems();
        Connection con=OnlineCourse.Conn();
        PreparedStatement ps; 
        combo.addItem("              ----Select----");
        try {
            ps=con.prepareStatement("select FIRSTNAME,LASTNAME,advisorid from ADVISOR");
            ResultSet rs=ps.executeQuery();
            while(rs.next()){
                combo.addItem("("+rs.getInt(3)+") "+rs.getString(1).concat(" "+rs.getString(2)));
            }
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(courseOfferClass.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    static void fillComboBox_semester_on_setAdvisor(JComboBox combo){
        combo.removeAllItems();
        Connection con=OnlineCourse.Conn();
        PreparedStatement ps; 
        combo.addItem("              ----Select----");
        try {
            ps=con.prepareStatement("select SEMESTER,YEAR from STUDENT");
            ResultSet rs=ps.executeQuery();
            while(rs.next()){
                combo.addItem(rs.getString(1).concat("("+rs.getString(2))+")");
            }
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(courseOfferClass.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    static String getCoursePREREQUISITE(String courseCode){
        Connection con=OnlineCourse.Conn();
        PreparedStatement ps;
        try {
            ps=con.prepareStatement("select *from courseDetails where CourseCode='"+courseCode+"'");
            ResultSet rs=ps.executeQuery();
            while(rs.next()){
                return rs.getString("PREREQUISITE");
            }
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(courseOfferClass.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }
    static double getCourseHoure(String courseCode){
        double houre=0;
        Connection con=OnlineCourse.Conn();
        PreparedStatement ps;
        try {
            ps=con.prepareStatement("select *from courseDetails where CourseCode='"+courseCode+"'");
            ResultSet rs=ps.executeQuery();
            while(rs.next()){
                houre=Double.parseDouble(rs.getString(3));
            }
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(courseOfferClass.class.getName()).log(Level.SEVERE, null, ex);
        }
        return houre;
    }
    static void fillOfferableTable(JTable table,String batch,String semester){
        
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        Connection con=OnlineCourse.Conn();
        PreparedStatement ps;
        try{
            ps=con.prepareStatement("Select *from COURSEOFFER where BATCH='"+batch+"' and SEMESTER='"+semester+"'");
            ResultSet rs=ps.executeQuery();
            while(rs.next()){
                Object [] row={rs.getString(1),rs.getString(2),getCourseHoure(rs.getString(2)),rs.getString(3),getCoursePREREQUISITE(rs.getString(2)),false};
                model.addRow(row);
            }
        }catch(Exception e){
            
        }
    }
    static boolean addMarkSheetFromStudent(int studentId,String batch,String semester,String courseName,String courseCode,int teacherID,String result,String active){
        boolean b=false;
        if(nodata(studentId, courseCode, batch, semester)){
            Connection con=OnlineCourse.Conn();
            PreparedStatement ps;
            try {
                ps=con.prepareStatement("insert into MARKSHEET(STUDENTID,BATCH,SEMESTER,COURSENAME,COURSECODE,TEACHERID,RESULT,ACTIVE) values (?,?,?,?,?,?,?,?)");
                ps.setInt(1, studentId);
                ps.setString(2, batch);
                ps.setString(3, semester);
                ps.setString(4, courseName);
                ps.setString(5, courseCode);
                ps.setInt(6, teacherID);
                ps.setString(7, result);
                ps.setString(8, active);
                if(ps.executeUpdate()>0){
                    b=true;
                }
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(createUser.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            if(updateMarkSheetFromStudent(studentId, batch, semester, courseCode, active)){
                b=true;
            }            
        }
        return b;
    }
    
    static String Lastdate(String batch,String semester){
        String date="";
        Connection con=OnlineCourse.Conn();
        try {
            Statement st=con.createStatement();
            ResultSet rs=st.executeQuery("select *from COURSEOFFER where batch='"+batch+"' and semester='"+semester+"'");
            while(rs.next ()){
                date=rs.getString("LASTDATE");
            }
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(courseOfferClass.class.getName()).log(Level.SEVERE, null, ex);
        }
        return date;
    }
    static boolean nodata(int studentId,String courseCode,String batch,String semester){
        boolean b=true;
        Connection con=OnlineCourse.Conn();
        try {
            Statement st=con.createStatement();
            ResultSet rs=st.executeQuery("select *from  MARKSHEET where BATCH='"+batch+"' and SEMESTER='"+semester+"' and COURSECODE='"+courseCode+"' and STUDENTID='"+studentId+"'");
            if(rs.next ()){
                if(!(rs.getString("STUDENTID").equals(""))){
                    b=false;
                }
            }
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(courseOfferClass.class.getName()).log(Level.SEVERE, null, ex);
        }
        return b;
    }
    static boolean updateMarkSheetFromStudent(int studentId,String batch,String semester,String courseCode,String active){
        boolean b=false;
        Connection con=OnlineCourse.Conn();
        PreparedStatement ps;
        try {
            ps=con.prepareStatement("update MARKSHEET set ACTIVE='"+active+"' where STUDENTID='"+studentId+"' and BATCH='"+batch+"' and SEMESTER='"+semester+"' and COURSECODE='"+courseCode+"'");
            if(ps.executeUpdate()>0){
                b=true;
            }
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(courseOfferClass.class.getName()).log(Level.SEVERE, null, ex);
        }
        return b;
    }
    static boolean checkPrerequisite(int studentId,String courseCode,String batch){
        boolean b=false;
        Connection con=OnlineCourse.Conn();
        try {
            Statement st=con.createStatement();
            ResultSet rs=st.executeQuery("select *from  MARKSHEET where BATCH='"+batch+"' and COURSECODE='"+courseCode+"' and STUDENTID='"+studentId+"'");
            if(rs.next ()){
                if(rs.getString("RESULT")==null){
                }else{
                    if(Double.parseDouble(rs.getString("RESULT"))>=2){
                        b=true;
                    }
                }
            }
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(courseOfferClass.class.getName()).log(Level.SEVERE, null, ex);
        }
        return b;
    }
    
    static void fillComboBox_Result(JComboBox combo,int teacherID,String semester){
        combo.removeAllItems();
        Connection con=OnlineCourse.Conn();
        PreparedStatement ps; 
        combo.addItem("      ----Select----");
        try {
            ps=con.prepareStatement("select DISTINCT(COURSENAME)from  MARKSHEET where SEMESTER='"+semester+"' and TEACHERID='"+teacherID+"'");
            ResultSet rs=ps.executeQuery();
            while(rs.next()){
                combo.addItem(rs.getString(1));
            }
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(courseOfferClass.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    static String getStudentName(int studentID){
        String name="";
        Connection con=OnlineCourse.Conn();
        PreparedStatement ps;
        try {
            ps=con.prepareStatement("select *from STUDENT where STUDENTID='"+studentID+"'");
            ResultSet rs=ps.executeQuery();
            if(rs.next()){
                name=rs.getString("FIRSTNAME").concat(" "+rs.getString("LASTNAME"));
            }
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(courseOfferClass.class.getName()).log(Level.SEVERE, null, ex);
        }
        return name;
    }
    static void fillSetResultTable(JTable table,int teacherID,String courseCode,String semester){
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        Connection con=OnlineCourse.Conn();
        PreparedStatement ps;
        try{
            ps=con.prepareStatement("select *from  MARKSHEET where SEMESTER='"+semester+"' and TEACHERID='"+teacherID+"' and COURSECODE='"+courseCode+"'");
            ResultSet rs=ps.executeQuery();
            while(rs.next()){
                Object [] row={rs.getString(1),getStudentName(rs.getInt(1)),rs.getString(2),courseCode,rs.getString(7)};
                model.addRow(row);
            }
            con.close();
        }catch(Exception e){
            
        }
    }
    static boolean setResultMarkSheetFromTeacher(int studentId,String batch,String courseCode,int teacherID,double result){
        boolean b=false;
        Connection con=OnlineCourse.Conn();
        PreparedStatement ps;
        try {
            ps=con.prepareStatement("update MARKSHEET set RESULT='"+result+"' where STUDENTID='"+studentId+"' and BATCH='"+batch+"' and TEACHERID='"+teacherID+"' and COURSECODE='"+courseCode+"'");
            if(ps.executeUpdate()>0){
                b=true;
            }
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(courseOfferClass.class.getName()).log(Level.SEVERE, null, ex);
        }
        return b;
    }
    
    static void fillComboBox_Semester(JComboBox combo,int studentID,String batch){
        combo.removeAllItems();
        Connection con=OnlineCourse.Conn();
        PreparedStatement ps; 
        combo.addItem("            ----Select----");
        try {
            ps=con.prepareStatement("select DISTINCT(SEMESTER)from  MARKSHEET where BATCH='"+batch+"' and STUDENTID='"+studentID+"'");
            ResultSet rs=ps.executeQuery();
            while(rs.next()){
                combo.addItem(rs.getString(1));
            }
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(courseOfferClass.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
//    static boolean checkDouble(String string){
//        boolean b=false;
//        try{
//            Double.parseDouble(string);
//            b=true;        
//        }catch(Exception e){    
//
//        }           
//        return b;
//    }
    static void fillShowResultTable(JTable table,int studentID,String batch,String semester){
        double totalHours=0,point=0;
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        Connection con=OnlineCourse.Conn();
        PreparedStatement ps;
        try{
            ps=con.prepareStatement("select *from  MARKSHEET where SEMESTER='"+semester+"' and STUDENTID='"+studentID+"' and BATCH='"+batch+"'");
            ResultSet rs=ps.executeQuery();
            while(rs.next()){
                totalHours+=getCourseHoure(rs.getString(5));
                point=point+(getCourseHoure(rs.getString(5))*rs.getDouble(7));
                Object [] row={rs.getString(5),rs.getString(4),getCourseHoure(rs.getString(5)),rs.getDouble(7)};
                model.addRow(row);
            }
            con.close();
        }catch(Exception e){
            
        }
        watchResult.jLabel3.setText("Your SGPA: "+(point/totalHours));
    }
    static String getTeacherName(int teacherID){
        String name="";
        Connection con=OnlineCourse.Conn();
        PreparedStatement ps;
        try {
            ps=con.prepareStatement("select *from TEACHER where TEACHERID='"+teacherID+"'");
            ResultSet rs=ps.executeQuery();
            if(rs.next()){
                name=rs.getString("FIRSTNAME").concat(" "+rs.getString("LASTNAME"));
            }
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(courseOfferClass.class.getName()).log(Level.SEVERE, null, ex);
        }
        return name;
    }
    static void fillWithdrawTable(JTable table,int studentID,String batch,String semester){
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        Connection con=OnlineCourse.Conn();
        PreparedStatement ps;
        try{
            ps=con.prepareStatement("Select *from MARKSHEET where STUDENTID='"+studentID+"' and BATCH='"+batch+"' and SEMESTER='"+semester+"'");
            ResultSet rs=ps.executeQuery();
            while(rs.next()){
                Object [] row={rs.getString(4),rs.getString(5),getCourseHoure(rs.getString(5)),getTeacherName(rs.getInt(6)),false};
                model.addRow(row);
            }
        }catch(Exception e){
            
        }
    }
    
    static boolean deleteStudentFromMarksheet(int studentId,String batch,String semester,String courseCode){
        Connection con=OnlineCourse.Conn();
        PreparedStatement ps;
        try {
            ps=con.prepareStatement("delete from MARKSHEET where STUDENTID=? and BATCH=? and SEMESTER=? and COURSECODE =?" );
            ps.setInt(1, studentId);
            ps.setString(2, batch);
            ps.setString(3, semester);
            ps.setString(4, courseCode);
            if(ps.executeUpdate()>0){
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(courseOfferClass.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
}