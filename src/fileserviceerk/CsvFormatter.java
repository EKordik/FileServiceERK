/*
 * Emma Kordik
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fileserviceerk;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Implementation of the FormatStrategy for CSV Formatted Files. This class
 * decodes data from the file into a usable format that is returned to the file
 * and encodes records to be written to a CSV file.
 * 
 * @author Emma Kordik
 * @version 1.00
 */
public class CsvFormatter implements FormatStrategy {
    private final String DELIMINATOR;
    private final String BR = "\n";
    private final String QUOTES = "\"";
    
    /**
     * Constructor for the CscFormatter. Must be used as no alternative is given.
     * Constructor request the a deliminator that is used and sets it to a static
     * for the file thus being able to read the file correctly. In theory.
     * @param deliminator - This should be a String and be whatever character 
     * separates the data in the file being read or written to. When in doubt us a comma.
     */
    public CsvFormatter(String deliminator){
        DELIMINATOR = deliminator;
    }
    
    
    /**
     * Transforms all the data from a file into a generic list of LinkedHashMaps
     * with String keys and values. Currently this formatter only supports files
     * with headers. The headers for each column are used as keys in the map.
     * Each LinkedHashMap represents one record of data. This Map is used to maintain
     * the order that the records are in the file.
     * @param data - Data from the file that is being converted into a List of 
     * LinkedHashMaps
     * @return - Returns a generic list of LinkedHashMaps with String keys and 
     * values. This Map is used to maintain the order that the records are in 
     * the file.
     */
    @Override
    public List<LinkedHashMap<String,String>> decodeAll(String data){
        List<LinkedHashMap<String,String>> recordList = new ArrayList<>();
        String lines[] = data.split("\\n");
        String[] header = lines[0].split(DELIMINATOR);
        
        if(header[0].startsWith(QUOTES)){
            for(int i = 0; i<header.length; i++){
                header[i] = header[i].substring(1, header[i].length()-1);
            }
        }
        
        for(int i =1; i<lines.length; i++){
            LinkedHashMap<String, String> record = new LinkedHashMap();
            String[] records = lines[i].split(DELIMINATOR);
            
            //Checks if the record has quotes around data and removes them.
            if(records[0].startsWith(QUOTES)){
                for(int x = 0; x<records.length; x++){
                    records[x] = records[x].substring(1, records[x].length()-1);
                }
            }
            for(int j = 0; j<records.length; j++){
                record.put(header[j], records[j]);
            }
            recordList.add(record);
        }
        
        return recordList;
    }
    
    /**
     * Transforms a List of LinkedHashMaps containing String keys and values 
     * into a String that can be written to a file. This method uses LinkedHashMaps
     * to preserve the order of the records. The keys are used for headers in
     * the CSV file. Each Map is one record.
     * 
     * @param records - This is the information that is going to be written to 
     * the file. The keys are the column headers and the values are the records.
     * One map per line of record.
     * @return - Returns a String representing all of the records in the List 
     * with proper formatting and separation between data. Each value is inclosed
     * in double quotes.
     */
    @Override
    public String encodeAll(List<LinkedHashMap<String,String>> records){
        StringBuilder data = new StringBuilder();
        LinkedHashMap<String,String> tempStorage;
        
        //Adds the record header
        tempStorage = records.get(0);
        Set<String>headers = tempStorage.keySet();
        List<String>colHeaders = new ArrayList<>();
        colHeaders.addAll(headers);
        for(String s: colHeaders){
            data.append(QUOTES);
            data.append(s);
            data.append(QUOTES);
            data.append(DELIMINATOR);
        }
        data.delete(data.length()-1, data.length());
        data.append(BR);
        
        //Adds a line to the data for each record
        for(int i =0; i<records.size(); i++){
            tempStorage = records.get(i);
            Set<String>columns = tempStorage.keySet();
            for(Iterator j = columns.iterator(); j.hasNext();){
                data.append(QUOTES);
                data.append(tempStorage.get(j.next()));
                data.append(QUOTES);
                data.append(DELIMINATOR);
            }
            
            data.delete(data.length()-1, data.length());
            data.append(BR);
        }
        data.delete(data.length()-1, data.length());
        return data.toString();
    }
    
    /**
     * Transforms a single record, which is a LinkedHashMap into a String of data. 
     * LinkedHashMap is used to preserve the order of the record.The values are 
     * formatted properly for a CSV file and returned as a string.
     * 
     * @param newRecord - A single record represented by a LinkedHashMap where 
     * the keys are headers and values the actual information for the record to
     * be formatted.
     * @return - Returns a String formatted with proper separators for CSV files.
     */
    @Override
    public String encodeRecord(LinkedHashMap<String,String> newRecord){
        StringBuilder data = new StringBuilder();
        
        Set<String> columns = newRecord.keySet();        
        for(Iterator i = columns.iterator(); i.hasNext();){
            data.append(QUOTES);
            data.append(newRecord.get(i.next()));
            data.append(QUOTES);
            data.append(DELIMINATOR);
        }
        
        data.delete(data.length()-1,data.length());
        data.append(BR);
        return data.toString();
    }
    
    /**
     * For testing purposes
     * @param args 
     */
    public static void main(String[] args) {
        CsvFormatter format = new CsvFormatter(",");
        String data = "THis,is,a,test\nstring,of,information,end";
        List<LinkedHashMap<String,String>> records = format.decodeAll(data);
        
        for(int i = 0; i<records.size(); i++){
            System.out.println(records.get(i).toString());
        }
        LinkedHashMap<String,String>testRecord = new LinkedHashMap<>();
        testRecord.put("1", "This");
        testRecord.put("3", "Test");
        
        String test = format.encodeAll(records);
        System.out.println(test);
        
        String test2 = format.encodeRecord(testRecord);
        
        System.out.println(test2);
    }
}