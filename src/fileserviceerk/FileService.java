/*
 * Emma Kordik
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fileserviceerk;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;

/**
 *
 * @author emmakordik
 */
public class FileService {
    private ReaderStrategy reader;
    private WriterStrategy writer;
    
    public FileService(ReaderStrategy reader, WriterStrategy writer){
        this.reader = reader;
        this.writer = writer;
        
    }
    
    public FileService(){
        
    }


    public void setReader(ReaderStrategy reader) {
        this.reader = reader;
    }

    public void setWriter(WriterStrategy writer) {
        this.writer = writer;
    }
    
    
    public List<LinkedHashMap<String,String>> readAllLines(String path) throws IOException{
        return reader.readAll(path);
    }
    
    public void writeNewFile(String path, List<LinkedHashMap<String,String>> newData) throws Exception{
        writer.writeNewFile(path, newData);

    }
    
    public void writeNewRecord(String path, LinkedHashMap<String,String> newRecord) throws Exception{
        writer.writeRecordtoFile(path, newRecord);
    }
    
}
