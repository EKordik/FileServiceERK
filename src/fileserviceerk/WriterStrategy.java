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
 * Writer Strategy Interface. This is so that writer objects can be made based
 * off of this class and work together in the DIP pattern. Writes either a new 
 * file with lots of records or appends a single record into an existing file.
 * 
 * @author Emma Kordik
 * @version 1.00
 */
public interface WriterStrategy {

    /**
     * Writes a completely new file containing the information in the List of LinkedHashMaps.
     * Each map should have a String key and a String value. 
     * @param path - Path of the file that is to be written to.
     * @param files - List of LinkedHashMaps that contain String keys and String values. This holds the records that will be written
     * @throws Exception 
     */
    public abstract void writeNewFile(String path, List<LinkedHashMap<String, String>> files) throws Exception;

    /**
     * Appends a single record to the end of a file. This method adds one file 
     * at a time to the designated file.
     * @param path - The path of the file that is to be appended.
     * @param record - A LinkedHashMap with a String key and String value to be appended
     * @throws Exception 
     */
    public abstract void writeRecordtoFile(String path, LinkedHashMap<String, String> record) throws Exception;
    
}
