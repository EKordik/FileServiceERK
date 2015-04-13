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
 * ReaderStrategy for use in the DIP design Pattern so that file readers can
 * be created that will work together. Reads all of the records in a file.
 * 
 * @author Emma Kordik
 * @version 1.00
 */
public interface ReaderStrategy {

    /**
     * Reads all of the records in a file and outputs them in a useable format.
     * Depending on the formatter used will depend on whether the key is the header
     * for a column or if it is just an automatically assigned identifier.
     * The value of each map is the data in a column for that record. Each 
     * LinkedHashMap represents one record.
     * @param path - Path of the file being written to
     * @return - Returns a List of Maps each map represents a record in the file. 
     * @throws IOException 
     */
    public abstract List<LinkedHashMap<String, String>> readAll(String path) throws IOException;
    
}
