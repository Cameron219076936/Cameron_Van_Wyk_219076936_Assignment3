
package za.ac.cput.assignment3;

/**
 *
 * @author aCameron Van Wyk 219076936
 */
public class RunFile {
    //Main Method
 public static void main(String[] args) {
     WritetoFile run=new WritetoFile();
     run.openFile();
     run.readFile();
     run.sortCust();
     run.writeToCustomerFile();
     run.sortSuppl();
     run.writeToSupplierFile();
 }
    
}
