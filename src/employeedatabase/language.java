/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package employeedatabase;

/**
 *
 * @author Ghazala
 */
public enum language {
    //setting constants for file formatting
    DELIMITER(","),
    FILE_EXTENSION("dutton"),
    FILE_EXTENSION_EXPANDED("Database Universal Text Traversing Operators Network"),
    
    USER_GUIDE_FILE_NAME("UserGuide.docx"),
    
    //setting other constants
    TITLE("Employee Database Program"),
    SEARCH_BAR_DEFAULT_TEXT("enter search query ..."),
    NEW_FILE_TITLE("Untitled"),
    ADD_EMPLOYEE_TITLE("Add an Employee"),
    MODIFY_EMPLOYEE_TITLE("Modify an Employee"),
    UNSAVED_CHANGES_PROMPT("You have unsaved data"),
    CONFIRM_SAVE_PROMPT("Would you like to save your database?"),
    OVERWRITE_SAVE_PROMPT("Do you want to overwrite the existing file?"),
    CONFIRM_DELETE_PROMPT("Are you sure you want to delete the following employee"),
    
    SEARCH_ERROR_EMPTY_DATABASE("The database is empty"),
    SEARCH_ERROR_EMPTY_SEARCH("Please enter something into the search bar"),
    SEARCH_ERROR_INVALID("Invalid Search type"),
    SEARCH_ERROR_NO_MATCHING("No employee matches your search"),
    
    ADD_ERROR_FIRST_NAME("You need to enter a first name"),
    ADD_ERROR_LAST_NAME("You need to enter a last name"),
    ADD_ERROR_EMPLOYEE_NUMBER("Employee number must be a positive integer"),
    ADD_ERROR_CONTAINS_DELIMITER("The employee name cannot contain '" + DELIMITER + "'"),
    
    LOAD_ERROR_INVALID_FILE("This file was invalid and could not be read\nPlease check that the file is not incompatible, broken or using an outdated formatting"),
    LOAD_ERROR_INVALID_EMPLOYEES("This file contains one or more invalid employees\nIf you click 'yes', the file will be overwritten with only the valid employees"),
    
    SELECTION_ERROR_INVALID("Invalid selection"),
    SELECTION_ERROR_SINGLE_ONLY("You must have a single employee selected from the table"),
    SELECTION_ERROR_ATLEAST_ONE("You must have at least one employee selected from the table");
    
    private final String text;
    language (String text){
        this.text = text;
    }
    public String getValue(){
        return text;
    }
}
