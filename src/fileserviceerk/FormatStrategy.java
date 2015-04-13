/*
 * Emma Kordik
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fileserviceerk;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * FormatStrategy interface to build format strategy objects from. Designed for
 * use in a file service program but could be implemented elsewhere. Encodes
 * and decodes files with proper formatting for use.
 * 
 * @author Emma Kordik
 * @version 1.00
 */
public interface FormatStrategy {

    /**
     * Transforms data from the raw data into a List of LinkedHashMaps of String 
     * keys and String values. LinkedHashMaps are used to preserve the order
     * of the records. The raw data should be inputed as a String.
     * @param data - Raw data from a file or other source that is in need of formatting.
     * @return - Returns a List of LinkedHashMaps containing String keys and String
     * Values. Each Map is one record. LinkedHashMaps are used to preserve the 
     * order of the information inside the record.
     */
    public abstract List<LinkedHashMap<String, String>> decodeAll(String data);

    /**
     * Transforms data from a List of LinkedHashMaps with String keys and String 
     * values into a String of data ready to be written to an appropriate file.
     * LinkedHashMaps are used to preserve the order of the records.
     * @param records - A List of LinkedHashMaps containing the information to
     * be written to the file.
     * @return - Returns a String of data formatted for the appropriate file type.
     */
    public abstract String encodeAll(List<LinkedHashMap<String, String>> records);

    /**
     * Transforms a single record from a LinkedHashMap with String keys and 
     * String values into a String of data ready to be written to a file.
     * LinkedHashMap is used to preserve the order of the record.
     * @param record - A Single record in a LinkedHashMap format with a String
     * key and String value. 
     * @return - Returns a String of data formatted according to the proper
     * format.
     */
    public abstract String encodeRecord(LinkedHashMap<String, String> record);
    
}
