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
 * File Service class can read data from a file and return formatted data or
 * write data to a file. The File Service class is a high level class that has
 * a weak dependency to a ReaderStrategy for reading and WriterStrategy for 
 * writing. This class can read all the data in a file and either write a new
 * file or add a record to an existing file.
 * 
 * @author Emma Kordik
 * @version 1.00
 */
public class FileService {
    private ReaderStrategy reader;
    private WriterStrategy writer;
    
    /**
     * Constructor sets the reader and writer strategies if you are using
     * both. A reader and writer strategy must be passed in for the File Service
     * Class to work.
     * @param reader - A Read object based off of the ReaderStrategy interface.
     * @param writer - A writer object based off of the WriterStrategy interface. 
     * @throws IllegalArgumentException - if no ReaderStrategy or WriterStrategy 
     * is passed in throws an IllegalArgumentException back to the calling class.
     */
    public FileService(ReaderStrategy reader, WriterStrategy writer) throws IllegalArgumentException{
        this.reader = reader;
        this.writer = writer;
        
    }
    
    /**
     * Empty constructor that can be used if you want to set the Reader or Writer
     * later on or only set one at a time. If you are using this constructor
     * you will need to set a reader or writer before you can read or write to files.
     */
    public FileService(){
        
    }


    /**
     * Sets the Reader strategy being used by the file service class. This method 
     * can be used if a reader strategy was not set upon creating an instance of 
     * the service or if the reader needs to change. 
     * 
     * @param reader - A reader strategy based on the ReaderStrategy interface to 
     * properly read files.
     * @throws IllegalArgumentException - if no reader object is passed in it will
     * throw a IllegalArgumentException.
     */
    public void setReader(ReaderStrategy reader) throws IllegalArgumentException{
        if(reader == null){
            throw new IllegalArgumentException();
        }
        this.reader = reader;
    }
    
    
    /**
     * Sets the writer strategy being used by the file service class. THis method 
     * must be used if you are writing to a file and did not declare a writer for
     * use when you created an instance of the File Service Class.
     * 
     * @param writer - Accepts a writer strategy object that is based on the
     * WriterStrategy interface.
     * @throws IllegalArgumentException - If no writer object is passed in it will
     * throw a IllegalArgumentException back to the calling class.
     */
    public void setWriter(WriterStrategy writer) throws IllegalArgumentException{
        if(writer == null){
            throw new IllegalArgumentException();
        }
        this.writer = writer;
    }
    
    /**
     * Reads all the lines from a file and returns them as a List of LinkedHashMaps
     * with String keys and values. LinkedHashMap is used to preserve the order
     * of the data in the records. Each map represents one record.
     * 
     * @param path - String representing the path to the file that is going to be read
     * @return - Returns a list of LinkedHashMap with String keys and values. 
     * LinkedHashMaps are used to preserve the order of records in the files.
     * @throws IOException - Exception if something goes wrong with reading the file.
     * @throws IllegalArgumentException - If the path is null or zero characters
     * in length an IllegalArgumentException will be thrown.
     */
    public List<LinkedHashMap<String,String>> readAllLines(String path) throws IOException, IllegalArgumentException{
        if(path == null || path.length() == 0){
            throw new IllegalArgumentException();
        }
        return reader.readAll(path);
    }
    
    /**
     * Writes a new file containing the data that is passed in through the List 
     * of LinkedHashMaps with String keys and values. LinkedHashMaps are used to 
     * preserve data. Each map representing one record.
     * @param path - String representing the path to where the file should be written.
     * @param newData - A List of LinkedHashMaps with String keys and values of
     * the data that is to be written to the file. 
     * @throws Exception - Throws IOExeption of file writing problems and IllegalArgumentExceptions
     * if an incorrect path or data is passed in.
     */
    public void writeNewFile(String path, List<LinkedHashMap<String,String>> newData) throws Exception{
        if(path == null || path.length() == 0 || newData == null || newData.size() == 0){
            throw new IllegalArgumentException();
        }
        writer.writeNewFile(path, newData);

    }
    
    /**
     * Writes a single record onto the end of a file. If the file is not found it
     * will write a new file.
     * @param path - A String representing the path to file that is to be appended.
     * @param newRecord - A LinkedHashMap with String keys and values. A LinkedHashMap
     * is used to preserve the order.
     * @throws Exception - IOExceptions are thrown if something goes wrong with 
     * writing to the file and an IllegalArgumentException is thrown if the
     * path or newRecord is null or the size/length is zero.
     */
    public void writeNewRecord(String path, LinkedHashMap<String,String> newRecord) throws Exception{
        if(path == null || path.length() == 0 || newRecord == null || newRecord.size() == 0){
            throw new IllegalArgumentException();
        }
        writer.writeRecordtoFile(path, newRecord);
    }
    
}
