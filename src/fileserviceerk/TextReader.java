/*
 * Emma Kordik
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fileserviceerk;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Reads data in from text files. Uses a formatStrategy to format the data 
 * properly and then returns the data to the calling class.
 * 
 * @author Emma Kordik
 * @version 1.00
 */
public class TextReader implements ReaderStrategy {
    private FormatStrategy formatter;
    private final String BR = "\n";
    
    /**
     * Constructor that sets the FormatStrategy when an instance of the class is
     * created.
     * 
     * @param formatter - Accepts a FromatStrategy based off of the FormatStrategy
     * interface.
     * @throws IllegalArgumentException - If no formatter object is found throws
     * an exception back to the calling method.
     */
    public TextReader(FormatStrategy formatter)throws IllegalArgumentException{
        setFormatter(formatter);
    }

    /**
     * Sets a format object based on the FormatStrategy interface. This method
     * can be used if you need to change the formatter after an instance of the 
     * text reader has been created.
     * 
     * @param formatter - Accepts a Format object based off the FormatStrategy
     * interface.
     * @throws IllegalArgumentException - Throws an IllegalArgumentException if 
     * no format object is passed in.
     */
    public void setFormatter(FormatStrategy formatter)throws IllegalArgumentException {
        if(formatter == null){
            throw new IllegalArgumentException();
        }
        this.formatter = formatter;
    }
    
    
    /**
     * Reads all the data in a file and returns it as a List of LinkedHashMaps
     * with String keys and values. LinkedHashMaps are used to preserve the order
     * of the information coming from the file.
     * 
     * @param path - Accepts the path of the file that is to be read.
     * @return - Returns a List of LinkedHashMaps with String keys and values. 
     * LinkedHashMaps are used to preserve the order of the data from the file.
     * @throws IOException - Throws an IOException if an exception is generated
     * when the file is read.
     * @throws IllegalArgumentException - if the file path is null or the length is 
     * zero throws an IllegalArgumentException because the file needs a path
     * to be able to read them in.
     */
    @Override
    public List<LinkedHashMap<String,String>> readAll(String path) throws IOException, IllegalArgumentException{
        if(path == null || path.length() == 0){
            throw new IllegalArgumentException();
        }
        File file = new File(path);
        String data = "";
        
        BufferedReader in = null;
        try{
            in = new BufferedReader(new FileReader(file));
            String line = in.readLine();

            while(line != null){
                data += line + BR;
                line = in.readLine();
            }
        }catch(IOException ioe){
            throw new IOException(ioe);
        }finally{
            try{
                in.close();
            }catch(Exception e){
                throw new IOException(e.getMessage());
            }
        }
        
        return formatter.decodeAll(data);
    }
    
    /**
     * For Testing Purposes
     * @param args 
     */
    public static void main(String[] args) {
        TextReader reader = new TextReader(new CsvFormatter(","));
        List<LinkedHashMap<String,String>> data;
        
        try{
           data = reader.readAll("src" + File.separatorChar + "text" + File.separatorChar + "mydata.csv");
           
           for(int i =0; i<data.size(); i++){
               System.out.println(data.get(i).toString());
           }
        }catch(IOException ioe){
            System.out.println(ioe.getMessage());
        }
        
    }
}
