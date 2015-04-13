/*
 * Emma Kordik
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fileserviceerk;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Industry ready Text Writer that writes data to text files. This class can 
 * either write a completely new file or append records into an existing file.
 * 
 * @author Emma Kordik
 * @version 1.00
 */
public class TextWriter implements WriterStrategy {
    private FormatStrategy format;
    private String data;
    
    
    /**
     * Constructor that sets a FormatStrategy and sets it for use by the class.
     * @param format - Accepts a format object that implements the FormatStrategy
     * interface.
     * @throws IllegalArgumentException - If the FormatStrategy is null an 
     * exception is thrown to the calling method.
     */
    public TextWriter(FormatStrategy format)throws IllegalArgumentException{
        setFormat(format);
    }

    /**
     * Sets the format strategy to be used for formatting data that is to be 
     * written. This class should be used if you need to change the formatter
     * being used after the TextWriter has been instantiated.
     * 
     * @param format - Accepts a format objected based on the formatStrategy interface.
     * @throws IllegalArgumentException - If the FormatStrategy based in is null
     * this exception is thrown to the calling method.
     */
    public void setFormat(FormatStrategy format) throws IllegalArgumentException {
        if(format == null){
            throw new IllegalArgumentException();
        }
        this.format = format;
    }
    
    
    /**
     * Writes a new file containing the data passed in as a List of LinkedHashMaps
     * with String keys and values. LinkedHashMaps are used to preserve the
     * order of the records. 
     * @param path - A String path to where the file should be written.
     * @param files - A list of LinkedHashMaps with String keys and values. The
     * maps are each a record where the values are the columns. LinkedHashMaps are
     * used to preserve the order of the records.
     * @throws Exception - Throws IOException if something goes wrong with writing
     * to the file and IllegalArgumentExcpetion if the path or files passed in are
     * not correct.
     */
    @Override
    public void writeNewFile(String path, List<LinkedHashMap<String,String>> files) throws Exception{
        if(path == null || path.length() == 0 || files == null || files.size() == 0){
            throw new IllegalArgumentException();
        } 

           
        data = format.encodeAll(files);
        boolean append = false;
        
        File file = new File(path);
        PrintWriter output = null;
        
        try{        
            output = new PrintWriter(new BufferedWriter(new FileWriter(file, append)));
            
            output.println(data);
            
        }catch(IOException ioe){
            throw new IOException(ioe);
        }finally{
            try{
                output.close();
            }catch(Exception e){
                throw new Exception(e);
            }
        }
    }
    
    /**
     * Writes a single record into an existing file. If no file exist where the
     * path points to it will write a new file. 
     * @param path - String path of where the file to be appended is.
     * @param record - A LinkedHashMap with String keys and values to be written
     * into the file
     * @throws Exception - Throws IOExceptions for file writing issues and an
     * IllegalArgumentException if the path or record are null or have a size of
     * zero.
     */
    @Override
    public void writeRecordtoFile(String path, LinkedHashMap<String,String> record) throws Exception{
        if(path == null|| path.length() == 0||record == null || record.size() == 0){
            throw new IllegalArgumentException();
        }
        boolean append = true;
        data = format.encodeRecord(record);
        
        File file = new File(path);
        PrintWriter output = null; 
        try{
            output = new PrintWriter(new BufferedWriter(new FileWriter(file, append)));
        
            output.println(data);
        }catch(IOException ioe){
            throw new IOException(ioe);
        }finally{
            try{
                output.close();
            }catch(Exception e){
                throw new Exception(e);
            }
        }
    }
    
}
