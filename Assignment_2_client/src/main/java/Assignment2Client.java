/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Rohith
 */

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class Assignment2Client {

    public static ClientResponse serverResponse ;
    public static String resourceRepresentation;
    public static String resourceUri;
    public static void clientGet(String gradeBookName, String studentName, String assignmentName){
        
        //  Boiler plate code taken from MKYONG
    print("Inside clientGet()");
    Client client = Client.create();
    WebResource webResource = client.resource("http://localhost:8080/Assignment_2_server/webresources/GradeBooks"//);
                           + "/CSE564"   + "/Assignments/" + assignmentName + "/Students/" + studentName);
    ClientResponse response = webResource.accept(MediaType.TEXT_PLAIN).get(ClientResponse.class);
    
    
    String output = response.getEntity(String.class);
    serverResponse = response;
    System.out.println(output);
    resourceRepresentation = output;
    //resourceRepresentation = output;
    resourceUri = "http://localhost:8080/Assignment_2_server/webresources/GradeBooks"//);
                           + "/CSE564"   + "/Assignments/" + assignmentName + "/Students/" + studentName ;
    //resourceRepresentation = output.substring(output.indexOf("<assignmentScore>"), output.indexOf("</assignmentScore>")) + "</assignmentScore>";
    System.out.println("Output from Server ... \n");
    System.out.println(output);
   
    }
    
    public static void getAllDetails() {
        print("Inside getAllDetails()");
        Client client = Client.create();       
        WebResource webResource = client.resource("http://localhost:8080/Assignment_2_server/webresources/GradeBooks");
        ClientResponse response = webResource.accept(MediaType.TEXT_PLAIN).get(ClientResponse.class);
        String output = response.getEntity(String.class);
        serverResponse = response;
        resourceRepresentation = output;
    //print(output);
        resourceUri = "http://localhost:8080/Assignment_2_server/webresources/GradeBooks";
       // System.out.println("Output from Server ... \n");
        //System.out.println(output);
         
    }
    
    public static void clientPost(String fieldName, int fieldChoice){
        print("Inside clientPost(String,int)");
        String assignmentName, studentName;
        Client client = new Client();
        ClientResponse response = null;
        WebResource webResource = client.resource("http://localhost:8080/Assignment_2_server/webresources/GradeBooks");
        resourceUri = "http://localhost:8080/Assignment_2_server/webresources/GradeBooks/CSE564/";
        if(fieldChoice == 1){
            assignmentName = fieldName;
            print(fieldChoice + "|" + assignmentName);
            response = webResource.type(MediaType.TEXT_PLAIN).post(ClientResponse.class, Integer.toString(fieldChoice) + "|" + assignmentName);
            //      response = webResource.type(MediaType.TEXT_PLAIN).post(ClientResponse.class, "Rohith test");
            //resourceUri = response.getEntity(String.class);
            resourceUri = resourceUri.concat("Assignments/" + assignmentName);
        }
        else if(fieldChoice == 2){
            studentName = fieldName;
            response = webResource.type(MediaType.TEXT_PLAIN).post(ClientResponse.class, Integer.toString(fieldChoice)  + "|" + studentName);
            resourceUri = resourceUri.concat("Students/" + studentName);
        }
        
        serverResponse = response;
        if(response.getStatus() == 201){
            resourceRepresentation = "<html><h1>Resource created</h1></html>";
        }
        resourceRepresentation = response.getEntity(String.class);
        print("Output from server ... \n");
        print("clientPost output = " + resourceRepresentation);
    }
    
    /*public static void clientGet(){
        print("Inside clientGet()");
        Client client = new Client();
        WebResource webResource = client.resource(resourceUri)
        Client response = 
    }*/
    
    public static void clientPost(String gradeBookName, String studentName, String assignmentName, int assignmentScore) {
        Client client = new Client ();
        print("Inside clientPost()");
        String assignmentScoreStr = Integer.toString(assignmentScore);
        String input = gradeBookName + "|" + studentName + "|" + assignmentName + "|" + assignmentScoreStr;
         
        System.out.println("input = " + input);
        WebResource webResource = client.resource("http://localhost:8080/Assignment_2_server/webresources/GradeBooks/"   
                            + "CSE564" + "/Assignments/" + assignmentName + "/Students/" + studentName);
        ClientResponse response = webResource.type(MediaType.TEXT_PLAIN).post(ClientResponse.class,input);
        serverResponse = response;
      
        resourceUri = "http://localhost:8080/Assignment_2_server/webresources/GradeBooks/"   
                            + "CSE564" + "/Assignments/" + assignmentName + "/Students/" + studentName;
        resourceRepresentation = response.getEntity(String.class);
        System.out.println("Output from server ... \n");
        
        System.out.println("clientPost output = " + resourceRepresentation);
        
    }
    
    public static void clientUpdate(String gradeBookName, String studentName, String assignmentName, int assignmentScore){
        Client client = new Client ();
        String assignmentScoreStr = Integer.toString(assignmentScore);
        String input = gradeBookName + "|" + studentName + "|" + assignmentName + "|" + assignmentScoreStr;
         
        System.out.println("input = " + input);
        WebResource webResource = client.resource("http://localhost:8080/Assignment_2_server/webresources/GradeBooks"//);
                           + "/CSE564" +  "/Assignments/" + assignmentName + "/Students/" + studentName);
        ClientResponse response = webResource.type(MediaType.TEXT_PLAIN).put(ClientResponse.class,input);
        serverResponse = response;       
        resourceRepresentation = response.getEntity(String.class);
        resourceUri = "http://localhost:8080/Assignment_2_server/webresources/GradeBooks"//);
                           + "/CSE564" +  "/Assignments/" + assignmentName + "/Students/" + studentName;
        System.out.println("Output from server ... \n");
        
        System.out.println("clientPost output = " + resourceRepresentation);
    }
    
    
    public static void clientDelete(String gradeBookName, String studentName, String assignmentName){
        
        //  Boiler plate code taken from MKYONG
    print("Inside clientDelete");
    Client client = Client.create();
    print("assignmentName =  " + assignmentName);
    print("studentName = " + studentName);
    WebResource webResource = client.resource("http://localhost:8080/Assignment_2_server/webresources/GradeBooks"//);
                           + "/CSE564" +  "/Assignments/" + assignmentName + "/Students/" + studentName);
    ClientResponse response = webResource.type(MediaType.TEXT_PLAIN).delete(ClientResponse.class);

    String output = response.getEntity(String.class);
    serverResponse = response;
    resourceUri = "http://localhost:8080/Assignment_2_server/webresources/GradeBooks"//);
                           + "/CSE564" +  "/Assignments/" + assignmentName + "/Students/" + studentName;
    resourceRepresentation = output;
    System.out.println("Output from Server ... \n");
    System.out.println(output);
   
    }

    public static void clientColumnDelete(String fieldName, int optionSelected){
        Client client = Client.create();
        print("Inside clientColumnDelete");
        ClientResponse response = null;     
        resourceUri = "http://localhost:8080/Assignment_2_server/webresources"
                    + "/GradeBooks/";
        if(optionSelected == 3){    //  Delete assignment is selected
            WebResource webResource = client.resource("http://localhost:8080/Assignment_2_server/webresources"
                    + "/GradeBooks/Assignments/" + fieldName);
            response = webResource.type(MediaType.TEXT_PLAIN).delete(ClientResponse.class);
            resourceUri = resourceUri.concat("Assignments/" + fieldName);
        }
        else if(optionSelected == 4){
            WebResource webResource = client.resource("http://localhost:8080/Assignment_2_server/webresources"
                    + "/GradeBooks/Students/" + fieldName);
            response = webResource.type(MediaType.TEXT_PLAIN).delete(ClientResponse.class);
             resourceUri = resourceUri.concat("Students/" + fieldName);
      }
        else {
            print("Incorrect option selected");
        }
 
        String output = response.getEntity(String.class);
        serverResponse = response;
        resourceRepresentation = output;
        System.out.println("Output from Server ... \n");
        System.out.println(output);
        
    }
   
    
    public static void main(String[] args) {
    }

    private static void print(String string) {
        System.out.println(string);
    }

    
}

