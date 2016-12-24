/**********

NAME:                              Aamir Tahir & Varun Ramanathan
STUDENT NUMBER:                    605973 & 505041

ICS4U0-A, Dec 2016

THIS FILE IS PART OF THE PROGRAM:  Employee Database

**********/

package employeedatabase;

public class PartTimeEmployee extends EmployeeInfo {
    //new attributes
    private int hoursPerWeek;
    private int weeksPerYear;
    
    //default values
    public PartTimeEmployee() {
    super();
    hoursPerWeek = 0;
    weeksPerYear = 0;
    }
    
    //constructor for part time employee 
    public PartTimeEmployee(int empNo, String firstName, String lName, int sex, double wage,double deductionsRate, int workLocation, int hoursPerWeek, int weeksPerYear) {
        super(empNo, firstName, lName, sex, wage,deductionsRate,workLocation);
        this.hoursPerWeek=hoursPerWeek;
        this.weeksPerYear=weeksPerYear;

    }
    
    @Override
    //getter for pay
    public double getPay(){
            return super.getPay()*hoursPerWeek*weeksPerYear;
    }
    //setter for hours per week
    public void setHoursPerWeek(int hoursPerWeek){
            this.hoursPerWeek=hoursPerWeek;
    }
    //setter for weeks per year
    public void setWeeksPerYear(int weeksPerYear){
            this.weeksPerYear=weeksPerYear;
    }

    @Override
    //gets the employee string
    public String getEmployeeString(){
        return super.getEmployeeString() + ";" +
               hoursPerWeek + ";" +
               weeksPerYear;
    }
    
    //getter for hours per week
    public int getHoursPerWeek() {
        return hoursPerWeek;
    }
    
    //getter for weeks per year
    public int getWeeksPerYear() {
        return weeksPerYear;
    }
}
