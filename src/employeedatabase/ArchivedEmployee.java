/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package employeedatabase;

public class ArchivedEmployee{
    private EmployeeInfo employee1;
    private EmployeeInfo employee2 = null;
    private int actionType;
    
    public ArchivedEmployee(EmployeeInfo employee1,int actionType){
        this.employee1 = employee1;
        this.actionType = actionType;
    }
    
    public ArchivedEmployee(EmployeeInfo employee1,int actionType, EmployeeInfo employee2){
        this.employee1 = employee1;
        this.employee2 = employee2;
        this.actionType = actionType;
    }
    
    public int getActionType(){
        return actionType;
    }
    
    public EmployeeInfo getEmployee1(){
        return employee1;
    }
    
    public EmployeeInfo getEmployee2(){
        return employee2;
    }
}
