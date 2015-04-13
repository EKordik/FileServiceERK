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
 *
 * @author emmakordik
 */
public class TextReader implements ReaderStrategy {
    private FormatStrategy formatter;
    private final String BR = "\n";
    
    public TextReader(FormatStrategy formatter){
        this.formatter = formatter;
    }
    
    @Override
    public List<LinkedHashMap<String,String>> readAll(String path) throws IOException{
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
