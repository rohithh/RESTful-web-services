/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rohith.messages.assignment_2_server;


import com.rohith.assignment2.Assignment;
import com.rohith.assignment2.GradeBook;
import com.rohith.assignment2.GradeBooksList;
import com.rohith.assignment2.Student;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.StringTokenizer;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * REST Web Service
 *
 * @author Rohith
 */
@Path("/GradeBooks")
public class GradeBookResource {
    static GradeBook gradeBook;                                                                                                    
    static int x;
    @Context
    private UriInfo context;

    /** 
     * Creates a new instance of GradeBookResource
     */
    public GradeBookResource() {
    }

    /** 
     * Retrieves representation of an instance of com.rohith.messages.assignment_2_server.GradeBookResource
     * @return an instance of java.lang.String
     */
    
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Response getMethod(){
        
        print("Inside getMethod()");
        String output = GradeBook.tableToStringConvert();
        return Response.status(200).entity(output).build();
        /*return "<html><body bgcolor = \"000000\"><h4><font color = \"FFFFFF\">Welcome to the student gradebook page, please go to the appropriate uri to access "
                + "the required "
                + "student resources</h4></body></html>";*/
    }
    
    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public Response postMethod(String input) {
        print("Inside POST 1");
        String [] inputs = new String[2];
        print("input = " + input);
        print("input.indexOf(\"|\") = " + input.indexOf("|"));
        inputs[0] = input.substring(0, input.indexOf("|"));
        inputs[1] = input.substring(input.indexOf("|")+1, input.length());
        print("inputs[0] = "  + inputs[0]);
        print("inputs[1] = " + inputs[1]);
        //print(inputs);
        if(Integer.parseInt(inputs[0]) == 1){
            if(GradeBook.assignments.containsKey(inputs[1])){
                return Response.status(409).entity("<html><h1>Resource already exists</h1></html>").build();
            }
            GradeBook.assignments.put(inputs[1],new Assignment(inputs[1]));
            
            return Response.status(201).entity( "Resource created" ).build();
        }
        else if(Integer.parseInt(inputs[0]) == 2){
            if(GradeBook.students.containsKey(inputs[1])){                
                return Response.status(409).entity("<html><h1>Resource already exists</h1></html>").build();
            }
           GradeBook.students.put(inputs[1],new Student(inputs[1]));
           
           return Response.status(201).entity(  "Resource created" ).build();
       
        } 
        else {
            return Response.status(404).entity("Error : Invalid choice").build();
        }
        //return Response.status(201).entity("Success").build();
    }
    
    @GET
    @Path("/CSE564/Assignments")
    @Produces(MediaType.TEXT_HTML)
    public String getMethod3(){
        //return GradeBook.toStringAssignments();
      //  return "hey";
        return GradeBook.toStringAssignments();
    }
    
    @GET
    @Path("/CSE564/Students")
    //@Produces(MediaType.APPLICATION_XML)
    public String getMethod4(){
        //return GradeBook.toStringAssignments();
      //  return "hey";
        return GradeBook.toStringStudents();
    }
    
    @GET
    @Path("/CSE564/Assignments/{assignmentName}")
    @Produces(MediaType.TEXT_PLAIN)
    public String getMethod(@PathParam("assignmentName") String assignmentName){      
        return gradeBook.assignments.get(assignmentName).assignmentName;
    }
    
    
    @GET
    @Path("/CSE564/Students/{studentName}")
    @Produces(MediaType.TEXT_PLAIN)
    public String getMethod2(@PathParam("studentName") String studentName){      
        return gradeBook.students.get(studentName).studentName;
    }
    
  /*  
    @GET
    @Path("{a}/{b}/{c}")    //  TODO Edit this
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_XML)
    public Response getMethod(@PathParam("a") String gradeBookNameParam, @PathParam("b") String studentNameParam,
            @PathParam("c") String assignmentNameParam) {
        print("Inside GET method");
        int score = 0;
        if(checkCorrectnessGet(gradeBookNameParam,studentNameParam,assignmentNameParam)){
            for(GradeBook g : GradeBooksList.gradeBooksList){
            if(g.gradeBookName.equalsIgnoreCase(gradeBookNameParam) && g.studentName.equalsIgnoreCase(studentNameParam)
                    && g.assignmentName.equalsIgnoreCase(assignmentNameParam)){
                //score = g.assignmentScore;
                            return Response.status(200).entity(g).build();

            }
        }
            return Response.status(200).entity("<html><h1>"+score+"</h1></html>").build();
        }
        else {
            return Response.status(404).entity("<html><h1>Resource not found</h1></html>").build();            
        }
      }
    */
    @POST
    @Path("/CSE564/Assignments/{assignmentName}/Students/{studentName}")    //  TODO Edit this
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_HTML)
    public Response postMethod (@PathParam("studentName") String studentNameParam,
            @PathParam("assignmentName") String assignmentNameParam, String input) {
        //TODO return proper representation object String inputs[] = input.split("|");
        print("Inside POST method");
        String gradeBookNameParam = "CSE564";
        print("Params are : " + gradeBookNameParam + " " + studentNameParam + " " + assignmentNameParam);
        StringTokenizer st = new StringTokenizer(input,"|");
        String gradeBookName, studentName, assignmentName, assignmentScore;
        gradeBookName = st.nextToken();
        studentName = st.nextToken();
        assignmentName = st.nextToken();
        assignmentScore = st.nextToken();
        print("ass score  = " + assignmentScore);
        int score = Integer.parseInt(assignmentScore);
        
        if(GradeBook.assignments.containsKey(assignmentName) && GradeBook.students.containsKey(studentName)){
          try{
              if(GradeBook.table.get(GradeBook.students.get(studentName),GradeBook.assignments.get(assignmentName)) == 0){
              int s = GradeBook.table.get(GradeBook.students.get(studentName),GradeBook.assignments.get(assignmentName));
               print("score = " + s); 
              GradeBook.table.put(GradeBook.students.get(studentName), GradeBook.assignments.get(assignmentName) , score);
                print("\nPrinting table ... ");
                return Response.status(201).entity("<html><h1>Resource created</h1></html>").build();
            }
          
              else if(GradeBook.table.get(GradeBook.students.get(studentName),GradeBook.assignments.get(assignmentName)) != 0){
                return Response.status(409).entity("<html><h1>Resource already exists</h1></html>").build();
              }
          }
          catch(Exception e){
               
            GradeBook.table.put(GradeBook.students.get(studentName), GradeBook.assignments.get(assignmentName) , score);
           int s = GradeBook.table.get(GradeBook.students.get(studentName),GradeBook.assignments.get(assignmentName));
               print("score = " + s);  
          
            print("\nPrinting table ... ");
            return Response.status(201).entity("<html><h1>Resource created</h1></html>").build();
        
          } 
            }  
        else if(!(GradeBook.assignments.containsKey(assignmentName) && GradeBook.students.containsKey(studentName))){
            return Response.status(404).entity("Resource not found, please create student/assignment first").build();
        }
        
        else {
            return Response.status(400).entity("Oops something went wrong").build();
        }
        return Response.status(400).entity("Oops something went wrong").build();
        
    }
    
    @GET
    @Path("/CSE564/Assignments/{assignmentName}/Students/{studentName}")
   // @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public Response getMethod5(@PathParam("assignmentName") String assignmentName, @PathParam("studentName")
                        String studentName){
        print("Inside getMethod5");
        
        if(GradeBook.assignments.containsKey(assignmentName) && GradeBook.students.containsKey(studentName)){
            print("marks = " + GradeBook.table.get(GradeBook.students.get(studentName), GradeBook.assignments.get(assignmentName)) );
            return Response.status(200).entity("<Student>" + studentName + "</Student>" + "<Assignment>" + assignmentName 
                + "</Assignment>" + "<Score>" + 
                GradeBook.table.get(GradeBook.students.get(studentName), GradeBook.assignments.get(assignmentName)) + "</Score>").build();
        }
        else {
            return Response.status(404).entity("<html><h1>Resource not found</h1></html>").build();
        }
    }
    
    
    @PUT
    @Path("/CSE564/Assignments/{assignmentName}/Students/{studentName}")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_HTML)
    public Response putMethod(@PathParam("b") String studentNameParam,
            @PathParam("c") String assignmentNameParam, String input) {
        //TODO return proper representation object String inputs[] = input.split("|");
        print("Inside PUT method");
        String gradeBookNameParam = "CSE564";
        print("Params are : " + gradeBookNameParam + " " + studentNameParam + " " + assignmentNameParam);
        StringTokenizer st = new StringTokenizer(input,"|");
        String gradeBookName, studentName, assignmentName, assignmentScore;
        gradeBookName = st.nextToken();
        studentName = st.nextToken();
        assignmentName = st.nextToken();
        assignmentScore = st.nextToken();
        print("ass score  = " + assignmentScore);
        int score = Integer.parseInt(assignmentScore);
        
        if(GradeBook.assignments.containsKey(assignmentName) && GradeBook.students.containsKey(studentName)){
            GradeBook.table.put(GradeBook.students.get(studentName), GradeBook.assignments.get(assignmentName), score);
            return Response.status(200).entity("<html><h1>Resource updated</h1></html>").build();
        }
        else{
            return Response.status(404).entity("<html><h1>Resource not found</h1></html>").build();
        }       
    }
    
    @DELETE
    @Path("/CSE564/Assignments/{assignmentName}/Students/{studentName}")
    @Produces(MediaType.TEXT_HTML)
public Response deleteMethod(@PathParam("studentName") String studentName,
            @PathParam("assignmentName") String assignmentName) {
        //TODO return proper representation object String inputs[] = input.split("|");
        print("Inside deleteMethod()");
        String gradeBookNameParam = "CSE564";
        print("Params are : " + gradeBookNameParam + " " + studentName + " " + assignmentName);
        if(GradeBook.assignments.containsKey(assignmentName) && GradeBook.students.containsKey(studentName)){
            GradeBook.table.put(GradeBook.students.get(studentName), GradeBook.assignments.get(assignmentName), 0);
            return Response.status(200).entity("<html><h1>Resource deleted</h1></html>").build();
        }
        else{
            return Response.status(409).entity("<html><h1>Resource not found</h1></html>").build();
        }
        
}       
    
    @DELETE
    @Consumes(MediaType.TEXT_PLAIN)
    @Path("/Assignments/{assignmentName}")
    @Produces(MediaType.TEXT_PLAIN)
    public Response deleteMethod(@PathParam("assignmentName") String assignmentName){
        
        boolean toDelete = true;
        if(GradeBook.assignments.containsKey(assignmentName)) {
            GradeBook.assignments.remove(assignmentName);
            for( String studentName : GradeBook.students.keySet()) {
                if(GradeBook.table.contains(studentName,assignmentName)) {
                    GradeBook.table.remove(GradeBook.students.get(studentName), GradeBook.assignments.get(assignmentName));
                }
            }
                
        }
        else{
            return Response.status(404).entity("Resource not found").build();
        }
        return Response.status(200).entity("Resource Deleted").build();
    }
    
    @DELETE
    @Consumes(MediaType.TEXT_PLAIN)
    @Path("/Students/{studentName}")
    @Produces(MediaType.TEXT_PLAIN)
    public Response deleteMethod2(@PathParam("studentName") String studentName){
        boolean toDelete = true;
        if(GradeBook.students.containsKey(studentName)) {
            GradeBook.students.remove(studentName);
            for( String student : GradeBook.students.keySet()) {
                if(student.equals(studentName)){
                 GradeBook.table.row(GradeBook.students.get(student)).clear();                  
                }
            }
                
        }
        else{
            return Response.status(404).entity("Resource not found").build();
        }
        return Response.status(200).entity("Resource Deleted").build();
        
    }
    
       
    public static void print(String string){
        System.out.println(string);
    }
    
    public static void print(int Int){
        System.out.println(Int);
    }
    public static void print(String [] strings){
        System.out.println(strings);
    }
    public static void exit(){
        System.exit(0);
    }

   /* private boolean checkCorrectnessPost(GradeBook gradeBook){
        print("Inside checkCorrectnessPost");
        for(GradeBook g : GradeBooksList.gradeBooksList){
            if(g.assignmentName.equalsIgnoreCase(gradeBook.assignmentName)&& g.gradeBookName.equalsIgnoreCase(gradeBook.gradeBookName)
                    && g.studentName.equalsIgnoreCase(gradeBook.studentName)){
            print("return false");
            return false;
            }     
        }
        print("return true");
        return true;
    }

    private boolean checkCorrectnessGet(String gradeBookNameParam, String studentNameParam, String assignmentNameParam) {
        print("Inside checkCorrectnessGet");
        print( gradeBookNameParam + " " + studentNameParam + " " + assignmentNameParam);
        print("Printing contents of gradeBooksList ...");
        
        for(GradeBook g : GradeBooksList.gradeBooksList){
            print( g.gradeBookName + " " + g.studentName + " " + g.assignmentName );
            print(" " + g.assignmentScore);
            if(g.gradeBookName.equalsIgnoreCase(gradeBookNameParam) && g.studentName.equalsIgnoreCase(studentNameParam)
                    && g.assignmentName.equalsIgnoreCase(assignmentNameParam)){
                print("return true");
                return true;
            }
        }
        print("return false");
        return false;
    }*/

    
    
    
    
    
    
        
}

    /*
         TODO First add functionality to support multiple post requests to create multiple resources 
         TODO Check if resource exists before POSTing
         TODO checkCorrectnessGet()
         TODO Check if all fields have been entered when submit is pressed
         TODO Create a UI to see all resources
    */