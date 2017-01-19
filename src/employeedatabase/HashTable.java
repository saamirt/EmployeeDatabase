/**********

NAME:                              Aamir Tahir & Varun Ramanathan
STUDENT NUMBER:                    605973 & 505041

ICS4U0-A, Dec 2016

THIS FILE IS PART OF THE PROGRAM:  Employee Database

**********/

package employeedatabase;

import java.awt.event.MouseAdapter;
import java.util.ArrayList;

public class HashTable {
        
        //creates first array list
        private ArrayList<EmployeeInfo>[] buckets;
        
        //variables for number of employees
        private int numEmployees = 0;
        
        // CONSTRUCTOR
        @SuppressWarnings({ "unchecked", "rawtypes" })
        //accepts number of buckets as a parameter
        public HashTable(int howManyBuckets) {
            //initializes the arraylist to the size of the number of buckets chosed
            buckets = new ArrayList[howManyBuckets];
            
            //creates a new arraylist for each bucket to store employees in
            for (int x = 0; x < howManyBuckets; x++) {
                buckets[x] = new ArrayList<EmployeeInfo>();
            }
        }
        
        //calculates bucket of employee
        public int calcBucket(int keyValue) {
                return(keyValue % buckets.length);
        }
        
        public void addEmployee(EmployeeInfo theEmployee) {
            //adds employee
            buckets[calcBucket(theEmployee.getEmployeeNumber())].add(theEmployee);
            //increases number of employees
            numEmployees++;
        }
        
        //searches for employee
        public int searchEmployee(int employeeNum) {
            //cycles through employees
            for (int x = 0;x<buckets[calcBucket(employeeNum)].size();x++){
                    //checks for the employee
                    if (buckets[calcBucket(employeeNum)].get(x).getEmployeeNumber() == employeeNum){
                        return x;
                    }
            }
            //returns -1 when employee isn't found
            return -1;
        }
        //broad search method (for multiple employee search)
        public ArrayList<Integer> broadSearchEmployee(String searchItem,int searchType){
            ArrayList<Integer> matchingEmployees = new ArrayList<Integer>();
            String currentEmpSearchValue = "";
            //cycles through the buckets
            for (int a = 0; a < buckets.length;a++){
                //cycles through employees in each bucket
                for (int b = 0; b<buckets[a].size();b++){
                    //alters search item based on what type of search user picks
                    switch (searchType){
                        case 0:
                            currentEmpSearchValue = String.valueOf(buckets[a].get(b).getEmployeeNumber());
                            break;
                        case 1:
                            currentEmpSearchValue = buckets[a].get(b).getFirstName().toLowerCase() + buckets[a].get(b).getLastName().toLowerCase();
                            break;
                        case 2:
                            currentEmpSearchValue = String.valueOf(buckets[a].get(b).getDeductionsRate());
                            break;
                        case 3:
                            currentEmpSearchValue = String.valueOf(buckets[a].get(b).getPay());
                            break;
                    }
                    //adds matching employees to the arraylist
                    if (currentEmpSearchValue.startsWith(searchItem.replace(" ","").toLowerCase())){
                        matchingEmployees.add(buckets[a].get(b).getEmployeeNumber());
                    }
                }
            }
            return matchingEmployees; 
        }
        
        //gets employee number of an employee
        public EmployeeInfo getEmployee(int employeeNumber){
        //if user doesn't exist, method returns null
        if (searchEmployee(employeeNumber) == -1){
            return null;
        }
        else {
            //returns employee number by searching for the employee
            return buckets[calcBucket(employeeNumber)].get(searchEmployee(employeeNumber));
        }
    }
    
    //remove employee
    public void removeEmployee(int employeeNum) {
            System.out.println(calcBucket(employeeNum));
            //checks if employee exists
            if (searchEmployee(employeeNum) != -1){
                //reduces number of employees
                numEmployees--;
                //removes employee
                buckets[calcBucket(employeeNum)].remove(buckets[calcBucket(employeeNum)].get(searchEmployee(employeeNum)));
            } else {
                //error message if user doesnt exist
                System.out.println("Employee does not exist");
            }
    }
    //removes all employees
    public void removeAllEmployees (){
        //cycles through buckets
        for (int a = 0;a<buckets.length;a++){
            System.out.println("a = " + a);
            //resets number of employees
            int numBucketEmployees = buckets[a].size();
            //cycles through employees
            for (int b = 0; b< numBucketEmployees ;b++){
                System.out.println("b = " + b);
                //removes each employee
                removeEmployee(buckets[a].get(0).getEmployeeNumber());
            }
        }
        System.out.println("Deleted all Employees");
    }    
    //displays contents of hash table
    public void displayContents() {
        //cycles through buckets
        for (int a = 0;a<buckets.length;a++){
            System.out.println("Bucket " + a + ":");
            //cycles through employees
            for (int b = 0; b< buckets[a].size();b++){
                System.out.print("Value of slot " + b + ": ");
                //displays first name
                System.out.println(buckets[a].get(b).getFirstName());
            }
        System.out.println("");
        }
    }
    //lists employees
    public String[] listEmployees(){
        //checks if list is empty
        if (numEmployees == 0){
            return null;
        }
        int currentEmployee = 0;
        //creates a list for employees
        String[] empList = new String[numEmployees];
        EmployeeInfo[] employeeArray = getAllEmployees();
        //cycles through employees
        for (EmployeeInfo employee: employeeArray) {
            //gets employee information string
            empList[currentEmployee] = employee.getEmployeeString();
            //moves to next employee
            currentEmployee++;
        }
        return empList;
    }
    
    //gets all employees
    public EmployeeInfo[] getAllEmployees(){
        //checks if table is empty
        if (numEmployees == 0){
            return null;
        }

        int currentEmployee = 0;
        EmployeeInfo[] employeeArray = new EmployeeInfo[numEmployees];
        //cycles through buckets
        for (ArrayList<EmployeeInfo> bucket : buckets) {
            //cyclese through employees
            for (int b = 0; b < bucket.size(); b++) {
                //gets employee object
                employeeArray[currentEmployee] = bucket.get(b);
                //moves to next employee
                currentEmployee++;
            }
        }
        return employeeArray;
    }
}
