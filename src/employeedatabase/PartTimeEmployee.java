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

public class PartTimeEmployee extends EmployeeInfo {
    private int hoursPerWeek;
    private int weeksPerYear;

    public PartTimeEmployee() {
    super();
    hoursPerWeek = 0;
    weeksPerYear = 0;
    }

    public PartTimeEmployee(boolean isFullTime, int empNo, String firstName, String lName, int sex, double wage,double deductionsRate, int workLocation, int hoursPerWeek, int weeksPerYear) {
            super(isFullTime, empNo, firstName, lName, sex, wage,deductionsRate,workLocation);
            this.hoursPerWeek=hoursPerWeek;
            this.weeksPerYear=weeksPerYear;

    }

    @Override
    public double getPay(){
            return super.getPay()*hoursPerWeek*weeksPerYear;
    }
    public void setHoursPerWeek(int hoursPerWeek){
            this.hoursPerWeek=hoursPerWeek;
    }
    public void setWeeksPerYear(int weeksPerYear){
            this.weeksPerYear=weeksPerYear;
    }

    @Override
    public String getEmployeeString(){
        return super.getEmployeeString() + ";" +
               hoursPerWeek + ";" +
               weeksPerYear;
    }

    public int getHoursPerWeek() {
        return hoursPerWeek;
    }

    public int getWeeksPerYear() {
        return weeksPerYear;
    }
}
