/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rohith.assignment2;
import com.google.common.collect.Multimap ;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.google.common.util.concurrent.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import javax.ws.rs.Produces;
//import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class GradeBook {

    public static HashMap<String,Assignment> assignments = new HashMap();
    public static HashMap<String,Student> students = new HashMap();
    
    public static Table<Student, Assignment, Integer> table = HashBasedTable.create();

    public static String tableToStringConvert() {
        String output = "";
        System.out.println("Inside tableToStringCovert");
        for( String s : students.keySet() ){
            output = output.concat("<Student>" + s + "</Student>\n");
            System.out.print("<Student>" + s + "</Student>");
            for( String a : assignments.keySet() ){ 
                output = output.concat("<Assignment>" + a + "</Assignment>" + "<Score>"
                + table.get(students.get(s), assignments.get(a)) + "</Score>\n");
                System.out.print("<Assignment>" + a + "</Assignment>" + "<Score>"
                + table.get(students.get(s), assignments.get(a)) + "</Score>\n");
            }
        }
        return output;
    }
    
    Multimap x1;
    public static String toStringAssignments(){
 
       String stringAssignments = "<html><h4>Below are the Assignments added to the Grade Book</h4><Assignments><br>" ;//= "<html>";
       
       for(String key : assignments.keySet()){
           stringAssignments = stringAssignments.concat("<Assignment>".concat(assignments.get(key).assignmentName ).concat("</Assignment><br>"));
       }
       
       stringAssignments.concat("</Assignments></html>");
       return stringAssignments;
    }

   
   public static String toStringStudents(){
 
       String stringStudents = "<html><h4>Below are the Students added to the Grade Book</h4><Students><br>";
       
       for(String key : students.keySet()){
           stringStudents = stringStudents.concat("<Student>" + students.get(key).studentName + " </Student><br>");
       }
       
       stringStudents.concat("</Students></html>");
       return stringStudents;
    }
   
   @Produces Set<Service> dummyServices() {
  return new HashSet<>();
}
}