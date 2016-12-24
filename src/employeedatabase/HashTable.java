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
        
        public int searchEmployee(int employeeNum) {
            for (int x = 0;x<buckets[calcBucket(employeeNum)].size();x++){
                    if (buckets[calcBucket(employeeNum)].get(x).getEmployeeNumber() == employeeNum){
                        return x;
                    }
            }
            return -1;
        }
        
        public ArrayList<Integer> broadSearchEmployee(String searchItem,int searchType){
            ArrayList<Integer> matchingEmployees = new ArrayList<Integer>();
            String currentEmpSearchValue = "";
            for (int a = 0; a < buckets.length;a++){
                for (int b = 0; b<buckets[a].size();b++){
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
                    if (currentEmpSearchValue.startsWith(searchItem.replace(" ","").toLowerCase())){
                        matchingEmployees.add(buckets[a].get(b).getEmployeeNumber());
                    }
                }
            }
            return matchingEmployees; 
        }
        
        public EmployeeInfo getEmployee(int employeeNumber){
        if (searchEmployee(employeeNumber) == -1){
            return null;
        }
        else {
            return buckets[calcBucket(employeeNumber)].get(searchEmployee(employeeNumber));
        }
    }

        public void removeEmployee(int employeeNum) {
                System.out.println(calcBucket(employeeNum));
        	if (searchEmployee(employeeNum) != -1){
                        numEmployees--;
                        buckets[calcBucket(employeeNum)].remove(buckets[calcBucket(employeeNum)].get(searchEmployee(employeeNum)));
        	} else {
        		System.out.println("Employee does not exist");
        	}
        }
        
        public void removeAllEmployees (){
            for (int a = 0;a<buckets.length;a++){
                System.out.println("a = " + a);
                int numBucketEmployees = buckets[a].size();
                for (int b = 0; b< numBucketEmployees ;b++){
                System.out.println("b = " + b);
                    removeEmployee(buckets[a].get(0).getEmployeeNumber());
                }
            }
            System.out.println("Deleted all Employees");
        }    
        
        public void displayContents() {
        	for (int a = 0;a<buckets.length;a++){
                    System.out.println("Bucket " + a + ":");
                    for (int b = 0; b< buckets[a].size();b++){
            		System.out.print("Value of slot " + b + ": ");
        		System.out.println(buckets[a].get(b).getFirstName());
                    }
        	System.out.println("");
        	}
        }
        public String[] listEmployees(){
            if (numEmployees == 0){
                return null;
            }
            
            int currentEmployee = 0;
            String[] empList = new String[numEmployees];
            EmployeeInfo[] employeeArray = getAllEmployees();
            for (EmployeeInfo employee: employeeArray) {
                empList[currentEmployee] = employee.getEmployeeString();
                currentEmployee++;
            }
            return empList;
        }
        
        public EmployeeInfo[] getAllEmployees(){
            if (numEmployees == 0){
                return null;
            }
            
            int currentEmployee = 0;
            EmployeeInfo[] employeeArray = new EmployeeInfo[numEmployees];
            for (ArrayList<EmployeeInfo> bucket : buckets) {
                for (int b = 0; b < bucket.size(); b++) {
                    employeeArray[currentEmployee] = bucket.get(b);
                    currentEmployee++;
                }
            }
            return employeeArray;
        }

    void addMouseListener(MouseAdapter mouseAdapter) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
