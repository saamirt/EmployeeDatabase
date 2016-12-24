/**********

NAME:                              Aamir Tahir & Varun Ramanathan
STUDENT NUMBER:                    605973 & 505041

ICS4U0-A, Dec 2016

THIS FILE IS PART OF THE PROGRAM:  Employee Database

**********/

package employeedatabase;

public class FullTimeEmployee extends EmployeeInfo {
	
        //full time employee class is basically the same as employeeinfo class
        //takes employee info constructor
	public FullTimeEmployee(int employeeNumber, String firstName, String lastName, int sex, double salary,double deductionsRate,int workLocation) {
		super(employeeNumber, firstName, lastName, sex, salary,deductionsRate,workLocation);
	}

}
