/*
 * Emma Kordik
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fileserviceerk;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

/**
 * This class formats data as a invoice that can be saved to disk or whatever. It
 * is custom designed for my Billing and Expense Service.
 * It accepts a list of LinkedHashMaps with String keys and values. The first 
 * Map should be the header information. This would include Invoice No. Invoice
 * Date, etc. The second map is the address. The next map is a list of Activities. 
 * The third Map is a list of Expense Items. The fourth map is the totals. And the Fifth map
 * is any footer information.
 * 
 * @author Emma Kordik
 * @version 1.00
 */
public class InvoiceFormatter implements FormatStrategy {
    private int index;
    private final String ACTIVITY_KEY;
    private  final String EXPENSE_KEY;

    //Formatting Variables
    private static final String SPACE = " ";
    private static final String BR = "\n";
    private static final String TAB = "\t";
    private static final String COLON = ":";
    
    public InvoiceFormatter(){
        index = 0;
        ACTIVITY_KEY = "Activity";
        EXPENSE_KEY = "Expenses";
    }
    
    public InvoiceFormatter(String activityHeader){
        index =0;
        EXPENSE_KEY = "Expenses";
        ACTIVITY_KEY = activityHeader;
    }
    @Override
    public final List<LinkedHashMap<String, String>> decodeAll(final String data) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public final String encodeAll(List<LinkedHashMap<String, String>> records) throws IllegalArgumentException{
        if(records.size() < 5){
            throw new IllegalArgumentException();
        }
        StringBuilder invoice = new StringBuilder();
        List<String> colHeaders = new ArrayList<>();
        LinkedHashMap<String,String> header = records.get(index);
        index++;

        
        //Code that gets the proper invoice set up.
        Set temp = header.keySet();
        colHeaders.addAll(temp);
        for(int i = 0; i<header.size(); i++){
            invoice.append(colHeaders.get(i)).append(SPACE).append(header.get(colHeaders.get(i)) + BR);
        }
        invoice.append(BR);
        
        //Code for the address block
        LinkedHashMap<String,String> address = records.get(index);
        index++;
        colHeaders.removeAll(temp);//clears the colHeader List for new headers.
        temp = address.keySet();
        colHeaders.addAll(temp);
        for(int i = 0; i<address.size(); i++){
            invoice.append(address.get(colHeaders.get(i))).append(BR);
        }
        invoice.append(BR + BR);
        
        //Formats the Activityies
        LinkedHashMap<String,String> activity = records.get(index);
        index++; //Sets for the next Index
        
        //Formats and displays the activity header
        colHeaders.removeAll(temp);
        temp = activity.keySet();
        colHeaders.addAll(temp);
        
        //Makes sure there are enough columns.
        invoice.append(colHeaders.get(0) + TAB + TAB + TAB);
        for(int i =1; i<colHeaders.size(); i++){          
            invoice.append(colHeaders.get(i)).append(TAB);           
        }
        invoice.append(BR);
        invoice.append("------------------------------------------------------------------------").append(BR);
        
        //Displays the activities.
        while(activity.containsKey(ACTIVITY_KEY)){
            for(int i = 0; i<activity.size(); i++){
                invoice.append(activity.get(colHeaders.get(i))).append(TAB);
            }
            invoice.append(BR);
            
            activity = records.get(index);
            index++;
        }
        index--;
        invoice.append(BR);
        
        //Code for the Expense Block
        LinkedHashMap<String,String> expense = records.get(index);
        index++; //Sets for the next Index
        
        //Formats and displays the Expense header
        colHeaders.removeAll(temp);
        temp = activity.keySet();
        colHeaders.addAll(temp);
        
      
        invoice.append(colHeaders.get(0) + TAB + TAB);
        for(int i =1; i<colHeaders.size(); i++){          
            invoice.append(colHeaders.get(i)).append(TAB);           
        }
        invoice.append(BR);
        invoice.append("------------------------------------------------------------------------").append(BR);
        
        //Displays the line items
        while(expense.containsKey(EXPENSE_KEY)){
            for(int i = 0; i<activity.size(); i++){
                invoice.append(activity.get(colHeaders.get(i))).append(TAB);
            }
            invoice.append(BR);
            
            expense = records.get(index);
            index++;
        }
        index--;
        invoice.append(BR);
        
        //Code for the total's block
        LinkedHashMap<String,String> totals = records.get(index);
        index++;
        invoice.append("--------------------").append(BR);
        colHeaders.removeAll(temp);
        temp = totals.keySet();
        colHeaders.addAll(temp);
        
        for(int i = 0; i<totals.size(); i++){
            invoice.append(colHeaders.get(i)).append(COLON).append(SPACE).append(totals.get(colHeaders.get(i)) + BR);
        }
        
        invoice.append(BR);
        

//        Code for the footer block
        LinkedHashMap<String,String> footer = records.get(index);
         
        colHeaders.removeAll(temp);
        temp = footer.keySet();
        colHeaders.addAll(temp);
        for(int i = 0; i<footer.size(); i++){
            invoice.append(footer.get(colHeaders.get(i)) + BR);
        }        //Code for the footer block
       
        //Returns a string of the completed invoice
        return invoice.toString();
    }
    
    @Override
    public String encodeRecord(LinkedHashMap<String, String> record) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


    
    /**
     * For testing
     */
//    public static void main(String[] args) {
//        LinkedHashMap<String,String> test = new LinkedHashMap<>();
//        LinkedHashMap<String,String> address = new LinkedHashMap<>();
//        LinkedHashMap<String,String> activity = new LinkedHashMap<>();
//        LinkedHashMap<String,String> activity2 = new LinkedHashMap<>();
//        LinkedHashMap<String,String> expense = new LinkedHashMap<>();
//        LinkedHashMap<String,String>totals = new LinkedHashMap<>();
//
//        List<LinkedHashMap<String,String>> test2 = new ArrayList();
//       LinkedHashMap<String,String> footer = new LinkedHashMap<>();
//        
//        test.put("Invoice No:", "1008");
//        test.put("Invoice Date:", "1/15/2015");
//        test.put("Invoice Period:", "February");
//        
//        address.put("Company:", "Big Buiding Blocks");
//        address.put("Contact:", "John Smith");
//        address.put("Street:", "1999 This Way Dr.");
//        address.put("CityStateZip:", "Somewhere, OK 57575");
//      
//        activity.put("Activity", "Meetings & Conference Calls");
//        activity.put("Hours", "5.00");
//        activity.put("SubTotal", "250.00");
//        activity.put("Discount", "0.00");
//        activity.put("Total", "250.00");
//        
//        activity2.put("Activity", "Programming & Design");
//        activity2.put("Hours", "10.00");
//        activity2.put("SubTotal", "1000.00");
//        activity2.put("Discount", "0.00");
//        activity2.put("Total", "1000.00");
//        
//        expense.put("Expenses", "Postage");
//        expense.put("Cost", "0.45");
//        expense.put("Qty", "2");
//        expense.put("Tax", "0.05");
//        expense.put("Subtotal", "0.95");
//        expense.put("Markup", "0.15");
//        expense.put("Total", "1.10");
//        
//        totals.put("Subtotal", "15.00");
//        totals.put("Tax", "54");
//        totals.put("Discount", "000");
//        totals.put("Total", "54.00");
//        
//        footer.put("footer", "Pay Earlier for a discount");
//        
//        test2.add(test);
//        test2.add(address);
//        test2.add(activity);
//        test2.add(activity2);
//        test2.add(expense);
//        test2.add(totals);
//        test2.add(footer);
//        
//        FormatStrategy invoice = new InvoiceFormatter();
//        
//        System.out.println(invoice.encodeAll(test2));
//    }
}

