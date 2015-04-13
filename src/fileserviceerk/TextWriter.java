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
 *
 * @author emmakordik
 */
public class TextWriter implements WriterStrategy {
    private FormatStrategy format;
    private String data;
    
    public TextWriter(FormatStrategy format){
        this.format = format;
    }
    
    @Override
    public void writeNewFile(String path, List<LinkedHashMap<String,String>> files) throws Exception{
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
    
    @Override
    public void writeRecordtoFile(String path, LinkedHashMap<String,String> record) throws Exception{
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
