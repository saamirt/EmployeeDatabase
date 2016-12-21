/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package employeedatabase;

/**
 *
 * @author Aamir
 */

public class FullTimeEmployee extends EmployeeInfo {
	
	public FullTimeEmployee() {
        super();
	}

	public FullTimeEmployee(boolean isFullTime, int employeeNumber, String firstName, String lastName, int sex, double salary,double deductionsRate,int workLocation) {
		super(isFullTime, employeeNumber, firstName, lastName, sex, salary,deductionsRate,workLocation);
	}

}
