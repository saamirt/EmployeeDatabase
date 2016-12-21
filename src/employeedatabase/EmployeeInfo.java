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
public class EmployeeInfo
{

    // Attributes

    private int employeeNumber;
    private String firstName;
    private String lastName;
    private int sex;
    private double pay;
    private double deductionsRate;
    private int workLocation;
    private boolean isFullTime;


	// Constructor to assign default values to attributes.
    public EmployeeInfo ()
    {
        employeeNumber = 777777;
        firstName = "Bugs";
        lastName = "Bunny";
        sex = 1;
        pay = 0;
        deductionsRate = 0;
        workLocation = 0;
        isFullTime = true;
    }


    // Constructor to assign passed values to attributes.
    public EmployeeInfo (boolean isFullTime, int employeeNumber, String firstName, String lastName,int sex, double pay, double deductionsRate,int workLocation)
    {
        this.employeeNumber = employeeNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.pay = pay;  	 	
        this.deductionsRate=deductionsRate;
        this.workLocation = workLocation;
        this.isFullTime = isFullTime;
    }


    public String getFirstName() {
		return firstName;
	}


	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	public String getLastName() {
		return lastName;
	}


	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	// Getter method for employeeNumber attribute.
    public int getEmployeeNumber ()
    {
        return (employeeNumber);
    }


    // Setter method for employeeNumber attributes.
    public void setEmployeeNumber (int employeeNumber)
    {
        if (employeeNumber < 0)
        {
            return;
        }
        else
        {
            this.employeeNumber = employeeNumber;
        }
    }


    public int getSex() {
		return sex;
	}


	public void setSex(int sex) {
		this.sex = sex;
	}


	public double getPay() {
		return pay*(1-deductionsRate);
	}

	public void setpay(double pay) {
		this.pay = pay;
	}
	public double getDeductionsRate() {
		return deductionsRate;
	}


	public void setDeductionsRate(double deductionsRate) {
		this.deductionsRate = deductionsRate;
	}


	public int getWorkLocation() {
		return workLocation;
	}


	public void setWorkLocation(int workLocation) {
		this.workLocation = workLocation;
	}
        
        public String getEmployeeString(){
            return isFullTime + ";" +
                   employeeNumber + ";" +
                   firstName + ";" +
                   lastName + ";" +
                   sex + ";" +
                   pay + ";" +
                   deductionsRate + ";" +
                   workLocation;
        }

    
} // public class EmployeeInfo
