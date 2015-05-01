/*
 * Emma Kordik
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fileserviceerk;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author emmakordik
 */
public class StartUp {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
         final AbstractApplicationContext ctx = new ClassPathXmlApplicationContext(
			       new String[] {"applicationContext.xml"});
        
       FileService file = (FileService)ctx.getBean("fileService");
       List<LinkedHashMap<String,String>> data = new ArrayList<>();
       
       try{
            data = file.readAllLines("src" + File.separatorChar + "text" + File.separatorChar + "mydata.csv");
       
            for(int i =0; i<data.size(); i++){
                System.out.println(data.get(i).toString());
            }
                   
       }catch(IOException ioe){
           System.out.println(ioe.getMessage());
       }catch(IllegalArgumentException iae){
           System.out.println(iae.getMessage());
       }
       
       //Writing to a file
        try{
            file.writeNewFile("src" + File.separatorChar + "text" + File.separatorChar + "test.csv", data);
            System.out.println("Files Written to Disk");
       }catch(Exception e){
           System.out.println(e.getMessage());
       }
        
       LinkedHashMap<String,String> newRecord = new LinkedHashMap<>();
       newRecord.put("firstName", "George");
       newRecord.put("lastName", "Whitfield");
       newRecord.put("age", "58");
       
       try{
           file.writeNewRecord("src" + File.separatorChar + "text" + File.separatorChar + "test.csv", newRecord);
           System.out.println("Record Added to File");
       }catch(Exception e){
           System.out.println(e.getMessage());
       }
    }
    
    
}
