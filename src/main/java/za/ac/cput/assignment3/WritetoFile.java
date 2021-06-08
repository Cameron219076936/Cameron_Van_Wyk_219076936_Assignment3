
/**
 *
 * @author Cameron Van Wyk_219076936
 */
package za.ac.cput.assignment3;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.stream.Collectors;

public class WritetoFile {
       private ObjectInputStream input;
    
  ArrayList<Customer> customer=  new ArrayList<Customer>();
  ArrayList<Supplier> supplier= new ArrayList<Supplier>();
  
  //open file 
  public void openFile(){
      try{
          input = new ObjectInputStream(new FileInputStream("stakeholder.ser"));
          System.out.println("***Serialized is file opened for reading***");
      }
      catch (IOException ioe){
          System.out.println("Cannot open serialized file: " +ioe.getMessage());
      }
  }
  public void closeFile(){
  try{
      input.close( );
  }
  catch (IOException ioe){
      System.out.println("Cannot open serialized file " + ioe.getMessage());
     }    
  }
  
public void readFile(){
    try{
        Object obj=null;
        while(!(obj= input.readObject()).equals(null)){
            if(obj instanceof Customer ){
                customer.add((Customer) obj );
                System.out.println("Insert Customer: "+ ((Customer) obj).getFirstName());
            }
            if (obj instanceof Supplier){
                supplier.add((Supplier) obj);
                System.out.println("Insert Supplier: " + ((Supplier) obj) .getName());
            }
            
        }
        System.out.println("Read File Finish");
    
    }catch (IOException ioe){
        System.out.println("Error");
    }catch (ClassNotFoundException cnfe){
        System.out.println("Class not found");
    }finally{
        closeFile();
    }
  }

public void sortCust(){
    Collections.sort(customer,(c1, c2) -> {
        return c1.getStHolderId().compareTo(c2.getStHolderId());
    });
}
public void sortSuppl(){
    Collections.sort(supplier, (c1 , c2) ->{
        return c1.getName().compareTo(c2.getName());
    });
}
private int getAgeFormat(String date ){
    LocalDate d1= LocalDate.parse(date, DateTimeFormatter.ISO_DATE);
    LocalDate d2= LocalDate.now();
    return Period.between(d1, d2).getYears();
}
public String formatBirthDate(Customer cust){
    DateFormat dateF =new SimpleDateFormat("dd MMM yyy");
    try{
        Date date =new SimpleDateFormat("yyyy-MM-dd").parse(cust.getDateOfBirth());
        return dateF.format(date);
    }catch (ParseException e){
        System.out.println("Error"+ e.getMessage());
        
    }
    return null;
}
//Customer 
public void writeToCustomerFile(){
 try{
     FileWriter fileWriter= new FileWriter("CustomerOutput.txt");
     fileWriter.write("==============================CUSTOMERS ===================================\n");
     fileWriter.write(String.format("%-10s\t%-10s\t%-10s\t%-15s\t%-10s\n", "ID", "Name", "Surname", "Date of birth", "Age"));
     fileWriter.write("===========================================================================\n");
     for (Customer cust :customer){
          String output = String.format("%-10s\t%-10s\t%-10s\t%-15s\t%-10s", 
                  cust.getStHolderId(), 
                  cust.getFirstName(), 
                  cust.getSurName(), 
                  formatBirthDate(cust), 
                  getAgeFormat(cust.
                  getDateOfBirth()));
                 fileWriter.write(output + "\n");
         }
     fileWriter.write("\nNumber of customers who can rent: " +customer.stream().filter(Customer::getCanRent).collect(Collectors.toList()).size() + "\n");
     fileWriter.write("\nNumber of customers who cannot rent: " + customer.stream().filter(c -> !c.getCanRent()).collect(Collectors.toList()).size());
     fileWriter.close();
 }   catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            System.out.println("Error writing to file");  
 
}
}
//Supplier
 public void writeToSupplierFile(){
   try {
            FileWriter fileWriter = new FileWriter("SupplierOutput.txt");
            fileWriter.write("========================== SUPPLIERS  ============================\n");
            fileWriter.write(String.format("%-15s\t%-15s\t%-15s\t%-15s\n", "ID", "Name", "Prod Type", "Description"));
            fileWriter.write("==================================================================\n");
            for (Supplier supp : supplier) {
                String output = String.format("%-15s\t%-20s\t%-15s\t%-15s", 
                        supp.getStHolderId(), 
                        supp.getName(),
                        supp.getProductType(),
                        supp.getProductDescription());
                fileWriter.write(output + "\n");
            }
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error: " + e.getMessage());
            System.out.println("Error writing to file");
        }
       
 }
}
