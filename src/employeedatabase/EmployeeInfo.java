/** ********
 *
 * NAME:                              Aamir Tahir & Varun Ramanathan
 * STUDENT NUMBER:                    605973 & 505041
 *
 * ICS4U0-A, Dec 2016
 *
 * THIS FILE IS PART OF THE PROGRAM:  Employee Database
 *
 ********* */
package employeedatabase;

public class EmployeeInfo {

    //Employee Attributes
    private int employeeNumber;
    private String firstName;
    private String lastName;
    private int sex;
    private double pay;
    private double deductionsRate;
    private int workLocation;

    // Constructor to assign default values to attributes.
    public EmployeeInfo() {
        employeeNumber = 777777;
        firstName = "Bugs";
        lastName = "Bunny";
        sex = 1;
        pay = 0;
        deductionsRate = 0;
        workLocation = 0;
    }

    // Constructor to assign passed values to attributes.
    public EmployeeInfo(int employeeNumber, String firstName, String lastName, int sex, double pay, double deductionsRate, int workLocation) {
        this.employeeNumber = employeeNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.pay = pay;
        this.deductionsRate = deductionsRate;
        this.workLocation = workLocation;
    }

    //getter for first name
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    //getter for last name
    public String getLastName() {
        return lastName;
    }

    //setter for last name
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    //getter for employeeNumber
    public int getEmployeeNumber() {
        return (employeeNumber);
    }

    //Setter for employeeNumber
    public void setEmployeeNumber(int employeeNumber) {
        //makes sure that employee number is never set to below 0
        if (employeeNumber >= 0) {
            this.employeeNumber = employeeNumber;
        } else {
            System.out.println("Invalid Employee Number");
        }
    }

    //getter for sex
    public int getSex() {
        return sex;
    }

    //setter for sex
    public void setSex(int sex) {
        this.sex = sex;
    }

    //getter for pay
    public double getPay() {
        return pay * (1 - deductionsRate);
    }

    //setter for pay
    public void setpay(double pay) {
        this.pay = pay;
    }

    //getter for deductions rate
    public double getDeductionsRate() {
        return deductionsRate;
    }

    //setter for deductions rate
    public void setDeductionsRate(double deductionsRate) {
        this.deductionsRate = deductionsRate;
    }

    //getter for work location
    public int getWorkLocation() {
        return workLocation;
    }

    //setter for work location
    public void setWorkLocation(int workLocation) {
        this.workLocation = workLocation;
    }

    //compiles all attributes into a string (for file writing)
    public String getEmployeeString() {
        return employeeNumber + ";"
                + firstName + ";"
                + lastName + ";"
                + sex + ";"
                + pay + ";"
                + deductionsRate + ";"
                + workLocation;
    }

} // public class EmployeeInfo
