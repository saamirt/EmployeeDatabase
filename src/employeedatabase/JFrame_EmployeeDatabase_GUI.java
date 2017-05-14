/** ********
 *
 * NAME:                              Aamir Tahir & Varun Ramanathan
 * STUDENT NUMBER:                    605973        505041
 *
 * ICS4U0-A, Dec 2016
 *
 * THIS FILE IS PART OF THE PROGRAM:  Employee Database
 *
 ********* */
package employeedatabase;

//imports
import java.awt.Desktop;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

//class creation
public class JFrame_EmployeeDatabase_GUI extends javax.swing.JFrame {

    /**
     * Creates new form JFrame_EmployeeDatabase_GUI
     */
    //setting constants for file formatting
    private final String DELIMITER = ";";
    private final String FILE_EXTENSION = "dutton";
    private final String FILE_EXTENSION_EXPANDED = "Database Universal Text Traversing Operators Network";
    
    private final String USER_GUIDE_FILE_NAME = "UserGuide.docx";
    
    //setting other constants
    private final String TITLE = "Employee Database Program";
    private final String SEARCH_BAR_DEFAULT_TEXT = "enter search query ...";
    private final String NEW_FILE_TITLE = "Untitled";
    private final String ADD_EMPLOYEE_TITLE = "Add an Employee";
    private final String MODIFY_EMPLOYEE_TITLE = "Modify an Employee";
    private final String UNSAVED_CHANGES_PROMPT = "You have unsaved data";
    private final String CONFIRM_SAVE_PROMPT = "Would you like to save your database?";
    private final String OVERWRITE_SAVE_PROMPT = "Do you want to overwrite the existing file?";
    private final String CONFIRM_DELETE_PROMPT = "Are you sure you want to delete the following employee";
    
    private final String SEARCH_ERROR_EMPTY_DATABASE = "The database is empty";
    private final String SEARCH_ERROR_EMPTY_SEARCH = "Please enter something into the search bar";
    private final String SEARCH_ERROR_INVALID = "Invalid Search type";
    private final String SEARCH_ERROR_NO_MATCHING = "No employee matches your search";
    
    private final String ADD_ERROR_FIRST_NAME = "You need to enter a first name";
    private final String ADD_ERROR_LAST_NAME = "You need to enter a last name";
    private final String ADD_ERROR_EMPLOYEE_NUMBER = "Employee number must be a positive integer";
    private final String ADD_ERROR_CONTAINS_DELIMITER = "The employee name cannot contain '" + DELIMITER + "'";
    
    private final String LOAD_ERROR_INVALID_FILE = "This file was invalid and could not be read\nPlease check that the file is not incompatible, broken or using an outdated formatting";
    private final String LOAD_ERROR_INVALID_EMPLOYEES = "This file contains one or more invalid employees\nIf you click 'yes', the file will be overwritten with only the valid employees";
    
    private final String SELECTION_ERROR_INVALID = "Invalid selection";
    private final String SELECTION_ERROR_SINGLE_ONLY = "You must have a single employee selected from the table";
    private final String SELECTION_ERROR_ATLEAST_ONE = "You must have at least one employee selected from the table";
    
    private final int DELETE_CONFIRM_EMPLOYEES_TO_DISPLAY = 15;
    
    private final String[] WORK_LOCATION = new String[]{"New York","Mississauga","Chicago","Paris","Dubai"};
    
    //declaring variables for global application usage
    private File activeFile;
    private HashTable menuHashTable;
    private LinkedList<ArchivedEmployee> undoEmployees;
    private LinkedList<ArchivedEmployee> redoEmployees;
    private Boolean wereChangesMade;
    private Boolean firstSearch = true;

    //class constructor
    public JFrame_EmployeeDatabase_GUI() {
        undoEmployees = new LinkedList<>();
        redoEmployees = new LinkedList<>();
        //initializes gui elements
        initComponents();
        //sets up application with initial properties
        changesMade(false);
        JFrame_EmployeeDatabase_GUI.super.setTitle(TITLE + " - " + NEW_FILE_TITLE + "." + FILE_EXTENSION);
        EmployeeTable.getTableHeader().setReorderingAllowed(false);
        fileChooser.setFileFilter(new FileNameExtensionFilter(FILE_EXTENSION_EXPANDED + " (*." + FILE_EXTENSION + ")", FILE_EXTENSION));

        //creates main hashtable
        menuHashTable = new HashTable(2);

        //commented out setting to allow only single selection of table
        //EmployeeTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        //event listener for instant search feature
        searchBar.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void changedUpdate(DocumentEvent e) {
                action();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                action();
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                action();
            }

            //method for any event pertaining to search bar
            private void action() {
                //creates a new table
                HashTable searchTable = new HashTable(2);

                //searches for any employees fitting search (beginning with search) and adds to the new table
                for (int employee : menuHashTable.broadSearchEmployee(searchBar.getText(), searchType.getSelectedIndex())) {
                    searchTable.addEmployee(menuHashTable.getEmployee(employee));
                }
                //displays the new table
                refreshTable(searchTable);
                if (searchBar.getText().toLowerCase().equals(SEARCH_BAR_DEFAULT_TEXT) && firstSearch == true || searchBar.getText().replace(" ", "").equals("")) {
                    refreshTable(menuHashTable);
                } else {
                    firstSearch = false;
                }
            }
        });

        //listener for mouse click (for double click) on table
        EmployeeTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent me) {
                //checks if first click is by the left mouse button, then for a double click, and if the second click is by the left button
                if (me.getButton() == MouseEvent.BUTTON1) {
                    if (me.getClickCount() == 2 && me.getButton() == MouseEvent.BUTTON1) {
                        //performs editing action
                        jButton_EditActionPerformed(null);
                    }
                }
            }
        });

        //adds a keyevent listener for hotkeys
        EmployeeTable.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent ke) {
                //checks if the key pressed is the 'delete' key (DEL)
                if (ke.getExtendedKeyCode() == 127) {
                    //calls the remove method
                    jButton_RemoveActionPerformed(null);
                }
                //checks if the key pressed is an 'E' and whether it had a ctrl modifier (CTRL+E)
                if (ke.getExtendedKeyCode() == KeyEvent.VK_E && ke.getModifiersEx() == 128) {
                    //edits employee that was selected
                    jButton_EditActionPerformed(null);
                }
                //checks if the key pressed is an 'R' and whether it had a ctrl modifier (CTRL+E)
                if (ke.getExtendedKeyCode() == KeyEvent.VK_R && ke.getModifiersEx() == 128) {
                    //edits employee that was selected
                    refreshTable(menuHashTable);
                }

            }
        });
    }

    //method for reading a file
    private String[] readFile(String filePath) throws FileNotFoundException, IOException {

        //creates new variables for file reading and compiling the text together
        String fileLine;
        ArrayList<String> compiledText = new ArrayList();
        //reads a line and adds it to the 'compiledText' variable
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath))) {
            //makes sure the line is not null (while also storing the text in a variable)
            while ((fileLine = bufferedReader.readLine()) != null) {
                compiledText.add(fileLine);
            }
        }

        //returns the final string
        return compiledText.toArray(new String[compiledText.size()]);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        fileChooser = new javax.swing.JFileChooser();
        employeeFormPanel = new javax.swing.JDialog();
        MainPanel = new javax.swing.JPanel();
        BasicInfoPanel = new javax.swing.JPanel();
        firstNameLabel = new javax.swing.JLabel();
        firstNameField = new javax.swing.JTextField();
        employeeNumberLabel = new javax.swing.JLabel();
        employeeNumberField = new javax.swing.JSpinner();
        deductionsRateLabel = new javax.swing.JLabel();
        sexLabel = new javax.swing.JLabel();
        lastNameLabel = new javax.swing.JLabel();
        lastNameField = new javax.swing.JTextField();
        workLocationLabel = new javax.swing.JLabel();
        sexField = new javax.swing.JComboBox<>();
        SpinnerNumberModel deductionsRateModel = new SpinnerNumberModel(0.00, 0.00, 1.00, 0.01);
        deductionsRateField = new javax.swing.JSpinner(deductionsRateModel);
        workLocationField = new javax.swing.JComboBox<>();
        employeeTypePanel = new javax.swing.JTabbedPane();
        fullTimeEmployeePanel = new javax.swing.JPanel();
        annualSalaryLabel = new javax.swing.JLabel();
        annualSalaryField = new javax.swing.JSpinner();
        partTimeEmployeePanel = new javax.swing.JPanel();
        hourlyWageLabel = new javax.swing.JLabel();
        hoursPerWeekLabel = new javax.swing.JLabel();
        weeksPerYearLabel = new javax.swing.JLabel();
        hourlyWageField = new javax.swing.JSpinner();
        hoursPerWeekField = new javax.swing.JSpinner();
        weeksPerYearField = new javax.swing.JSpinner();
        saveButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();
        mainInterface = new javax.swing.JPanel();
        jPanel_TopButtons = new javax.swing.JPanel();
        searchBar = new javax.swing.JTextField();
        searchButton = new javax.swing.JButton();
        searchType = new javax.swing.JComboBox<>();
        jScrollPane_EmployeeTable = new javax.swing.JScrollPane();
        EmployeeTable = new javax.swing.JTable();
        jPanel_BottomButtons = new javax.swing.JPanel();
        jButton_Add = new javax.swing.JButton();
        jButton_Edit = new javax.swing.JButton();
        jButton_Remove = new javax.swing.JButton();
        jButton_Refresh = new javax.swing.JButton();
        TopMenu = new javax.swing.JMenuBar();
        jMenu_File = new javax.swing.JMenu();
        JMenuItem_New = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        JMenuItem_Load = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        jMenu_File1 = new javax.swing.JMenu();
        JMenuItem_Save = new javax.swing.JMenuItem();
        JMenuItem_SaveAs = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JPopupMenu.Separator();
        JMenuItem_Quit = new javax.swing.JMenuItem();
        jMenu_Help = new javax.swing.JMenu();
        jMenuItem_Guide = new javax.swing.JMenuItem();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem_Add = new javax.swing.JMenuItem();
        jMenuItem_Edit = new javax.swing.JMenuItem();
        jMenuItem_Remove = new javax.swing.JMenuItem();
        jSeparator4 = new javax.swing.JPopupMenu.Separator();
        jMenuItem_Undo = new javax.swing.JMenuItem();
        jMenuItem_Redo = new javax.swing.JMenuItem();

        employeeFormPanel.setBounds(new java.awt.Rectangle(0, 0, 450, 430));
        employeeFormPanel.setLocation((Toolkit.getDefaultToolkit().getScreenSize().width - employeeFormPanel.getWidth()) / 2,(Toolkit.getDefaultToolkit().getScreenSize().height - employeeFormPanel.getHeight()) / 2);
        employeeFormPanel.setMaximumSize(new java.awt.Dimension(450, 425));
        employeeFormPanel.setMinimumSize(new java.awt.Dimension(450, 425));
        employeeFormPanel.setName("employeeFormPanel"); // NOI18N
        employeeFormPanel.setResizable(false);
        employeeFormPanel.setType(java.awt.Window.Type.POPUP);

        BasicInfoPanel.setMaximumSize(new java.awt.Dimension(430, 264));
        BasicInfoPanel.setMinimumSize(new java.awt.Dimension(430, 264));
        BasicInfoPanel.setPreferredSize(new java.awt.Dimension(430, 264));

        firstNameLabel.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        firstNameLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        firstNameLabel.setText("First Name");

        employeeNumberLabel.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        employeeNumberLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        employeeNumberLabel.setText("Employee Number");

        employeeNumberField.setModel(new javax.swing.SpinnerNumberModel(0, 0, null, 1));
        employeeNumberField.setEditor(new javax.swing.JSpinner.NumberEditor(employeeNumberField, "######"));

        deductionsRateLabel.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        deductionsRateLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        deductionsRateLabel.setText("Deductions Rate");

        sexLabel.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        sexLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        sexLabel.setText("Sex");

        lastNameLabel.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lastNameLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lastNameLabel.setText("Last Name");

        workLocationLabel.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        workLocationLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        workLocationLabel.setText("Work Location");

        sexField.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "male", "female", "other" }));
        sexField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sexFieldActionPerformed(evt);
            }
        });

        deductionsRateField.setModel(new javax.swing.SpinnerNumberModel(0, 0, null, 1));

        workLocationField.setModel(new javax.swing.DefaultComboBoxModel<>(WORK_LOCATION));
        workLocationField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                workLocationFieldActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout BasicInfoPanelLayout = new org.jdesktop.layout.GroupLayout(BasicInfoPanel);
        BasicInfoPanel.setLayout(BasicInfoPanelLayout);
        BasicInfoPanelLayout.setHorizontalGroup(
            BasicInfoPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(BasicInfoPanelLayout.createSequentialGroup()
                .add(BasicInfoPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING, false)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, firstNameField)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, firstNameLabel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, employeeNumberLabel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 211, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, deductionsRateLabel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 211, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, sexLabel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 211, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, employeeNumberField)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, deductionsRateField)
                    .add(sexField, 0, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .add(BasicInfoPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                    .add(lastNameLabel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 211, Short.MAX_VALUE)
                    .add(workLocationLabel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 211, Short.MAX_VALUE)
                    .add(lastNameField)
                    .add(workLocationField, 0, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        BasicInfoPanelLayout.setVerticalGroup(
            BasicInfoPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(BasicInfoPanelLayout.createSequentialGroup()
                .add(BasicInfoPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(BasicInfoPanelLayout.createSequentialGroup()
                        .add(BasicInfoPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(BasicInfoPanelLayout.createSequentialGroup()
                                .add(firstNameLabel)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(firstNameField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                            .add(BasicInfoPanelLayout.createSequentialGroup()
                                .add(lastNameLabel)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(lastNameField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(employeeNumberLabel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 22, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(26, 26, 26))
                    .add(BasicInfoPanelLayout.createSequentialGroup()
                        .add(workLocationLabel)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(BasicInfoPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(employeeNumberField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(workLocationField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))))
                .add(deductionsRateLabel)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(deductionsRateField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(sexLabel)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(sexField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        employeeTypePanel.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        employeeTypePanel.setMaximumSize(new java.awt.Dimension(430, 264));
        employeeTypePanel.setMinimumSize(new java.awt.Dimension(430, 264));
        employeeTypePanel.setPreferredSize(new java.awt.Dimension(430, 264));

        fullTimeEmployeePanel.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        fullTimeEmployeePanel.setMaximumSize(new java.awt.Dimension(425, 110));
        fullTimeEmployeePanel.setMinimumSize(new java.awt.Dimension(425, 110));
        fullTimeEmployeePanel.setPreferredSize(new java.awt.Dimension(425, 110));

        annualSalaryLabel.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        annualSalaryLabel.setText("Annual Salary");

        annualSalaryField.setModel(new javax.swing.SpinnerNumberModel(20000.0d, 5.0d, null, 1000d));
        annualSalaryField.setModel(new javax.swing.SpinnerNumberModel(0, 0, null, 1));

        org.jdesktop.layout.GroupLayout fullTimeEmployeePanelLayout = new org.jdesktop.layout.GroupLayout(fullTimeEmployeePanel);
        fullTimeEmployeePanel.setLayout(fullTimeEmployeePanelLayout);
        fullTimeEmployeePanelLayout.setHorizontalGroup(
            fullTimeEmployeePanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(fullTimeEmployeePanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(fullTimeEmployeePanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                    .add(annualSalaryLabel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 203, Short.MAX_VALUE)
                    .add(annualSalaryField))
                .addContainerGap(212, Short.MAX_VALUE))
        );
        fullTimeEmployeePanelLayout.setVerticalGroup(
            fullTimeEmployeePanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(fullTimeEmployeePanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(annualSalaryLabel)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(annualSalaryField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        employeeTypePanel.addTab("Full Time Employee", fullTimeEmployeePanel);

        partTimeEmployeePanel.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N

        hourlyWageLabel.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        hourlyWageLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        hourlyWageLabel.setText("Hourly Wage");

        hoursPerWeekLabel.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        hoursPerWeekLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        hoursPerWeekLabel.setText("Hours Worked per Week");

        weeksPerYearLabel.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        weeksPerYearLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        weeksPerYearLabel.setText("Weeks Worked per Year");

        hourlyWageField.setModel(new javax.swing.SpinnerNumberModel(10.0d, 5.0d, null, 0.05d));
        hourlyWageField.setModel(new javax.swing.SpinnerNumberModel(0, 0, null, 1));

        hoursPerWeekField.setModel(new javax.swing.SpinnerNumberModel(0, 0, 168, 1));

        weeksPerYearField.setModel(new javax.swing.SpinnerNumberModel(0, 0, 52, 1));

        org.jdesktop.layout.GroupLayout partTimeEmployeePanelLayout = new org.jdesktop.layout.GroupLayout(partTimeEmployeePanel);
        partTimeEmployeePanel.setLayout(partTimeEmployeePanelLayout);
        partTimeEmployeePanelLayout.setHorizontalGroup(
            partTimeEmployeePanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(partTimeEmployeePanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(partTimeEmployeePanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING, false)
                    .add(hoursPerWeekField)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, hourlyWageField)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, hoursPerWeekLabel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, hourlyWageLabel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(partTimeEmployeePanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(weeksPerYearLabel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 199, Short.MAX_VALUE)
                    .add(weeksPerYearField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 199, Short.MAX_VALUE))
                .addContainerGap())
        );
        partTimeEmployeePanelLayout.setVerticalGroup(
            partTimeEmployeePanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(partTimeEmployeePanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(partTimeEmployeePanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(partTimeEmployeePanelLayout.createSequentialGroup()
                        .add(21, 21, 21)
                        .add(partTimeEmployeePanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(hourlyWageField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(weeksPerYearField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                    .add(partTimeEmployeePanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                        .add(hourlyWageLabel)
                        .add(weeksPerYearLabel)))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(hoursPerWeekLabel)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(hoursPerWeekField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        employeeTypePanel.addTab("Part Time Employee", partTimeEmployeePanel);

        org.jdesktop.layout.GroupLayout MainPanelLayout = new org.jdesktop.layout.GroupLayout(MainPanel);
        MainPanel.setLayout(MainPanelLayout);
        MainPanelLayout.setHorizontalGroup(
            MainPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(employeeTypePanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 430, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
            .add(BasicInfoPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        MainPanelLayout.setVerticalGroup(
            MainPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, MainPanelLayout.createSequentialGroup()
                .add(0, 0, 0)
                .add(BasicInfoPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 203, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(employeeTypePanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 140, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
        );

        saveButton.setText("Save");
        saveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveButtonActionPerformed(evt);
            }
        });

        cancelButton.setText("Cancel");
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout employeeFormPanelLayout = new org.jdesktop.layout.GroupLayout(employeeFormPanel.getContentPane());
        employeeFormPanel.getContentPane().setLayout(employeeFormPanelLayout);
        employeeFormPanelLayout.setHorizontalGroup(
            employeeFormPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(employeeFormPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(employeeFormPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(MainPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(employeeFormPanelLayout.createSequentialGroup()
                        .add(saveButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 210, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .add(cancelButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 210, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        employeeFormPanelLayout.setVerticalGroup(
            employeeFormPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(employeeFormPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(MainPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(employeeFormPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(saveButton)
                    .add(cancelButton))
                .add(36, 36, 36))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(800, 600));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                formKeyPressed(evt);
            }
        });

        mainInterface.setMinimumSize(new java.awt.Dimension(800, 600));
        mainInterface.setPreferredSize(new java.awt.Dimension(800, 600));

        searchBar.setText("Enter Search Query ...");
        searchBar.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                searchBarFocusGained(evt);
            }
        });
        searchBar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchBarActionPerformed(evt);
            }
        });
        searchBar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                searchBarKeyTyped(evt);
            }
        });

        searchButton.setText("Search");
        searchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchButtonActionPerformed(evt);
            }
        });

        searchType.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Employee Number", "Name", "Deductions Rate", "Annual Pay" }));
        searchType.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchTypeActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout jPanel_TopButtonsLayout = new org.jdesktop.layout.GroupLayout(jPanel_TopButtons);
        jPanel_TopButtons.setLayout(jPanel_TopButtonsLayout);
        jPanel_TopButtonsLayout.setHorizontalGroup(
            jPanel_TopButtonsLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel_TopButtonsLayout.createSequentialGroup()
                .add(searchBar, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 592, Short.MAX_VALUE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(searchType, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(searchButton))
        );
        jPanel_TopButtonsLayout.setVerticalGroup(
            jPanel_TopButtonsLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel_TopButtonsLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                .add(searchBar, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 23, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(searchButton)
                .add(searchType, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 23, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
        );

        EmployeeTable.setAutoCreateRowSorter(true);
        EmployeeTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "First Name", "Last Name", "Employee Number", "Sex", "Work Location", "Employee Type", "Deductions Rate", "Annual Pay"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Float.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        EmployeeTable.setToolTipText("");
        EmployeeTable.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        EmployeeTable.setMinimumSize(new java.awt.Dimension(40, 0));
        EmployeeTable.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                EmployeeTableFocusLost(evt);
            }
        });
        jScrollPane_EmployeeTable.setViewportView(EmployeeTable);

        JMenuItem_Save.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.CTRL_MASK));
        jButton_Add.setText("Add");
        jButton_Add.setMaximumSize(new java.awt.Dimension(71, 23));
        jButton_Add.setMinimumSize(new java.awt.Dimension(10, 23));
        jButton_Add.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_AddActionPerformed(evt);
            }
        });

        jButton_Edit.setText("Edit");
        jButton_Edit.setMaximumSize(new java.awt.Dimension(71, 23));
        jButton_Edit.setMinimumSize(new java.awt.Dimension(10, 23));
        jButton_Edit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_EditActionPerformed(evt);
            }
        });

        jButton_Remove.setText("Remove");
        jButton_Remove.setMinimumSize(new java.awt.Dimension(10, 23));
        jButton_Remove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_RemoveActionPerformed(evt);
            }
        });

        jButton_Refresh.setText("Refresh");
        jButton_Refresh.setMinimumSize(new java.awt.Dimension(10, 23));
        jButton_Refresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_RefreshActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout jPanel_BottomButtonsLayout = new org.jdesktop.layout.GroupLayout(jPanel_BottomButtons);
        jPanel_BottomButtons.setLayout(jPanel_BottomButtonsLayout);
        jPanel_BottomButtonsLayout.setHorizontalGroup(
            jPanel_BottomButtonsLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel_BottomButtonsLayout.createSequentialGroup()
                .add(jButton_Add, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 191, Short.MAX_VALUE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jButton_Edit, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 191, Short.MAX_VALUE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jButton_Remove, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 190, Short.MAX_VALUE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jButton_Refresh, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 190, Short.MAX_VALUE)
                .add(0, 0, 0))
        );
        jPanel_BottomButtonsLayout.setVerticalGroup(
            jPanel_BottomButtonsLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel_BottomButtonsLayout.createSequentialGroup()
                .add(jPanel_BottomButtonsLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, jButton_Refresh, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, jButton_Remove, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(jButton_Edit, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(jPanel_BottomButtonsLayout.createSequentialGroup()
                        .add(0, 0, Short.MAX_VALUE)
                        .add(jButton_Add, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 45, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                .add(0, 0, 0))
        );

        org.jdesktop.layout.GroupLayout mainInterfaceLayout = new org.jdesktop.layout.GroupLayout(mainInterface);
        mainInterface.setLayout(mainInterfaceLayout);
        mainInterfaceLayout.setHorizontalGroup(
            mainInterfaceLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(mainInterfaceLayout.createSequentialGroup()
                .add(10, 10, 10)
                .add(mainInterfaceLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel_TopButtons, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(jScrollPane_EmployeeTable, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 780, Short.MAX_VALUE)
                    .add(jPanel_BottomButtons, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .add(10, 10, 10))
        );
        mainInterfaceLayout.setVerticalGroup(
            mainInterfaceLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(mainInterfaceLayout.createSequentialGroup()
                .add(10, 10, 10)
                .add(jPanel_TopButtons, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(6, 6, 6)
                .add(jScrollPane_EmployeeTable)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(jPanel_BottomButtons, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        getContentPane().add(mainInterface, java.awt.BorderLayout.CENTER);

        jMenu_File.setText("File");
        jMenu_File.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenu_FileActionPerformed(evt);
            }
        });

        JMenuItem_New.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_MASK));
        JMenuItem_New.setText("New Database");
        JMenuItem_New.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JMenuItem_NewActionPerformed(evt);
            }
        });
        jMenu_File.add(JMenuItem_New);
        jMenu_File.add(jSeparator1);

        JMenuItem_Load.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_L, java.awt.event.InputEvent.CTRL_MASK));
        JMenuItem_Load.setText("Load Database");
        JMenuItem_Load.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JMenuItem_LoadActionPerformed(evt);
            }
        });
        jMenu_File.add(JMenuItem_Load);
        jMenu_File.add(jSeparator2);

        jMenu_File1.setText("Save Database ...");

        JMenuItem_Save.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        JMenuItem_Save.setText("Save Database");
        JMenuItem_Save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JMenuItem_SaveActionPerformed(evt);
            }
        });
        jMenu_File1.add(JMenuItem_Save);

        JMenuItem_SaveAs.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        JMenuItem_SaveAs.setText("Save Database As");
        JMenuItem_SaveAs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JMenuItem_SaveAsActionPerformed(evt);
            }
        });
        jMenu_File1.add(JMenuItem_SaveAs);

        jMenu_File.add(jMenu_File1);
        jMenu_File.add(jSeparator3);

        JMenuItem_Quit.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Q, java.awt.event.InputEvent.CTRL_MASK));
        JMenuItem_Quit.setText("Quit");
        JMenuItem_Quit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JMenuItem_QuitActionPerformed(evt);
            }
        });
        jMenu_File.add(JMenuItem_Quit);

        TopMenu.add(jMenu_File);

        jMenu_Help.setText("Help");

        jMenuItem_Guide.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F1, 0));
        jMenuItem_Guide.setText("Open User Guide");
        jMenuItem_Guide.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem_GuideActionPerformed(evt);
            }
        });
        jMenu_Help.add(jMenuItem_Guide);

        TopMenu.add(jMenu_Help);

        jMenu1.setText("Edit");

        jMenuItem_Add.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem_Add.setText("Add");
        jMenuItem_Add.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem_AddActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem_Add);

        jMenuItem_Edit.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem_Edit.setText("Edit");
        jMenuItem_Edit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem_EditActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem_Edit);

        jMenuItem_Remove.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_DELETE, 0));
        jMenuItem_Remove.setText("Remove");
        jMenuItem_Remove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem_RemoveActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem_Remove);
        jMenu1.add(jSeparator4);

        jMenuItem_Undo.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Z, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem_Undo.setText("Undo");
        jMenuItem_Undo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem_UndoActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem_Undo);

        jMenuItem_Redo.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Y, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem_Redo.setText("Redo");
        jMenuItem_Redo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem_RedoActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem_Redo);

        TopMenu.add(jMenu1);

        setJMenuBar(TopMenu);

        setSize(new java.awt.Dimension(816, 639));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void JMenuItem_NewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JMenuItem_NewActionPerformed

        //checks if changes were made in order to confirm saving the database
        if (wereChangesMade && YesNoPrompt("", CONFIRM_SAVE_PROMPT) == JOptionPane.YES_OPTION) {
            JMenuItem_SaveActionPerformed(evt);
        }

        //clears and resets the application
        removeAllEmployees();
        JFrame_EmployeeDatabase_GUI.super.setTitle(TITLE + " - " + NEW_FILE_TITLE + "." + FILE_EXTENSION);
        activeFile = null;
        changesMade(false);
    }//GEN-LAST:event_JMenuItem_NewActionPerformed

    private void JMenuItem_LoadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JMenuItem_LoadActionPerformed
    boolean wereAnyInvalidEmployees = false;    
    //prompt user to select a file to load
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            try {
                File selectedFile = fileChooser.getSelectedFile();
                //prints out the file for debugging purposes
                System.out.println(selectedFile.getAbsolutePath());

                //reads the file for 
                String[] fileTextLines = readFile(selectedFile.getAbsolutePath());

                //clear the database
                removeAllEmployees();

                for (String fileTextLine : fileTextLines) {
                    System.out.println("Line: " + fileTextLine);
                    //splits line into attributes
                    String[] employeeAttributes = fileTextLine.split(DELIMITER);

                    // Adds employee (part or full time is based on number of attributes)
                    switch (employeeAttributes.length) {
                        case 7:
                            //for debugging purposes
                            System.out.println("Full time employee added");
                            //adds employee
                            menuHashTable.addEmployee(new FullTimeEmployee(
                                    Integer.parseInt(employeeAttributes[0]),
                                    employeeAttributes[1],
                                    employeeAttributes[2],
                                    Integer.parseInt(employeeAttributes[3]),
                                    Float.parseFloat(employeeAttributes[4]),
                                    Float.parseFloat(employeeAttributes[5]),
                                    Integer.parseInt(employeeAttributes[6])
                            ));
                            
                            //adds to archived employee table
                            undoEmployees.add(new ArchivedEmployee(new FullTimeEmployee(
                                    Integer.parseInt(employeeAttributes[0]),
                                    employeeAttributes[1],
                                    employeeAttributes[2],
                                    Integer.parseInt(employeeAttributes[3]),
                                    Float.parseFloat(employeeAttributes[4]),
                                    Float.parseFloat(employeeAttributes[5]),
                                    Integer.parseInt(employeeAttributes[6])
                            ),1));
                            
                            break;
                        case 9:
                            //for debugging purposes
                            System.out.println("Part time employee added");
                            //adds employee
                            menuHashTable.addEmployee(new PartTimeEmployee(
                                    Integer.parseInt(employeeAttributes[0]),
                                    employeeAttributes[1],
                                    employeeAttributes[2],
                                    Integer.parseInt(employeeAttributes[3]),
                                    Float.parseFloat(employeeAttributes[4]),
                                    Float.parseFloat(employeeAttributes[5]),
                                    Integer.parseInt(employeeAttributes[6]),
                                    Integer.parseInt(employeeAttributes[7]),
                                    Integer.parseInt(employeeAttributes[8])
                            ));
                            
                            //adds to archived employee table
                            undoEmployees.add(new ArchivedEmployee(new PartTimeEmployee(
                                    Integer.parseInt(employeeAttributes[0]),
                                    employeeAttributes[1],
                                    employeeAttributes[2],
                                    Integer.parseInt(employeeAttributes[3]),
                                    Float.parseFloat(employeeAttributes[4]),
                                    Float.parseFloat(employeeAttributes[5]),
                                    Integer.parseInt(employeeAttributes[6]),
                                    Integer.parseInt(employeeAttributes[7]),
                                    Integer.parseInt(employeeAttributes[8])
                            ),1));
                            
                            break;
                        default:
                            //for debugging purposes
                            System.out.println("Invalid employee");
                            wereAnyInvalidEmployees = true;
                            break;
                    }
                }

                //sets application title and current file to the loaded file
                JFrame_EmployeeDatabase_GUI.super.setTitle(TITLE + " - " + selectedFile.getName());
                activeFile = selectedFile;
                if (menuHashTable.getAllEmployees() == null){
                    JOptionPane.showMessageDialog(new JFrame(), LOAD_ERROR_INVALID_FILE);
                } else if (wereAnyInvalidEmployees){
                    if (YesNoPrompt("Delete Invalid Employees?", LOAD_ERROR_INVALID_EMPLOYEES) == JOptionPane.YES_OPTION){
                        JMenuItem_SaveActionPerformed(evt);
                    }
                }
                //catches any exceptions to keep the application running
            } catch (IOException ex) {
                Logger.getLogger(JFrame_EmployeeDatabase_GUI.class.getName()).log(Level.SEVERE, null, ex);
            }
            //handles an invalid file selection
        } else {
            //for debugging purposes
            System.out.println("No valid file selected");
        }
        //refreshes table
        refreshTable(menuHashTable);
    }//GEN-LAST:event_JMenuItem_LoadActionPerformed

    private void JMenuItem_SaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JMenuItem_SaveActionPerformed
        //checks if file can be saved to a current file or must be saved using a file browser
        if (activeFile == null) {
            JMenuItem_SaveAsActionPerformed(evt);
            //for debugging purposes
            System.out.println("Saving As instead");
        } else {
            //sets printwriter with a null default state
            PrintWriter writer = null;
            try {
                //uses the 'listEmployees' method to gather a string for each employee's attributes in an array
                String[] employeeList = menuHashTable.listEmployees();

                //sets the write location
                writer = new PrintWriter(activeFile);

                //writes into the file for each non-null employee in the array
                if (employeeList != null) {
                    for (String employeeList1 : employeeList) {
                        writer.println(employeeList1);
                    }
                    //for debugging purposes
                    System.out.println("Saved employee successfully");
                }
            } catch (FileNotFoundException ex) {
                Logger.getLogger(JFrame_EmployeeDatabase_GUI.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                //closes the printwriter
                writer.close();
            }
            //for debugging purposes
            System.out.println("Saved database successfully");
        }
        //calls method to show that there are no unsaved changes
        changesMade(false);
    }//GEN-LAST:event_JMenuItem_SaveActionPerformed

    private void JMenuItem_SaveAsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JMenuItem_SaveAsActionPerformed
        //prompts user with a file browser and checks user selection
        if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            //gets selected file
            File selectedFile = fileChooser.getSelectedFile();

            //makes sure that file is saved in a valid format
            if (!selectedFile.getAbsolutePath().endsWith("." + FILE_EXTENSION.toLowerCase())) {
                //adds a format at the end if not included
                selectedFile = new File(selectedFile.getAbsolutePath() + "." + FILE_EXTENSION);
            }

            //checks if a valid selection was made, and if file exists, it prompts to overwrite
            if (!selectedFile.exists() || selectedFile.exists() && YesNoPrompt("", OVERWRITE_SAVE_PROMPT) == JOptionPane.YES_OPTION) {
                //sets selected file as the active file of the database
                activeFile = selectedFile;
                //saves the database (calls save method)
                JMenuItem_SaveActionPerformed(evt);
                //sets application 
                JFrame_EmployeeDatabase_GUI.super.setTitle(TITLE + " - " + selectedFile.getName());
            }
            //handles invalid selection
        } else {
            System.out.println("No valid file selected");
        }
    }//GEN-LAST:event_JMenuItem_SaveAsActionPerformed

    private void searchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchButtonActionPerformed
        HashTable searchTable = new HashTable(2);
        //searches for all matching employees
        for (int employee : menuHashTable.broadSearchEmployee(searchBar.getText(), searchType.getSelectedIndex())) {
            searchTable.addEmployee(menuHashTable.getEmployee(employee));
        }
        //updates table
        refreshTable(searchTable);
        //checks for errors
        if (searchBar.getText().toLowerCase().equals(SEARCH_BAR_DEFAULT_TEXT) && firstSearch == true && firstSearch == true) {
            refreshTable(menuHashTable);
            return;
        } else if (menuHashTable.getAllEmployees() == null) {
            JOptionPane.showMessageDialog(new JFrame(), SEARCH_ERROR_EMPTY_DATABASE);
        } else if (searchBar.getText().replace(" ", "").equals("")) {
            JOptionPane.showMessageDialog(new JFrame(), SEARCH_ERROR_EMPTY_SEARCH);
            refreshTable(menuHashTable);
        } else if (searchType.getSelectedIndex() != 1 && !isNumerical(searchBar.getText())) {
            JOptionPane.showMessageDialog(new JFrame(), SEARCH_ERROR_INVALID);
        } else if (searchTable.getAllEmployees() == null) {
            JOptionPane.showMessageDialog(new JFrame(), SEARCH_ERROR_NO_MATCHING);
        }
        firstSearch = false;
    }//GEN-LAST:event_searchButtonActionPerformed

    private void searchBarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchBarActionPerformed
        //calls search button method
        searchButtonActionPerformed(evt);
    }//GEN-LAST:event_searchBarActionPerformed

    private void searchBarFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_searchBarFocusGained
        //allows user to select all text when search bar is selected (to make it easier to search)
        searchBar.selectAll();
    }//GEN-LAST:event_searchBarFocusGained

    private void jButton_RefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_RefreshActionPerformed
        //calls refresh table method
        refreshTable(menuHashTable);
    }//GEN-LAST:event_jButton_RefreshActionPerformed

    private void jButton_AddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_AddActionPerformed
        //enables number field for when user modifies an employee before adding
        employeeNumberField.setEnabled(true);

        //sets the title of the panel (because the same panel is used for modifying and editing)
        employeeFormPanel.setTitle(ADD_EMPLOYEE_TITLE);

        //clears fields
        firstNameField.setText("");
        lastNameField.setText("");

        //runs through numbers to find the lowest available employee number
        int x = 0;
        while (menuHashTable.getEmployee(x) != null) {
            x++;
        }
        //sets employee number to this available number
        employeeNumberField.setValue(x);

        //makes popup visible
        employeeFormPanel.setVisible(true);
    }//GEN-LAST:event_jButton_AddActionPerformed

    private void EmployeeTableFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_EmployeeTableFocusLost
        //UNUSED METHOD
    }//GEN-LAST:event_EmployeeTableFocusLost

    private void saveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveButtonActionPerformed
        //creates an error string to add on to
        String error = "";

        //checks for whether employee already exists
        try {
            if (menuHashTable.searchEmployee((Integer) employeeNumberField.getValue()) != -1) {
                //adds to error string
                error += "Employee " + employeeNumberField.getValue() + " already exists\n";
            }
        } catch (Exception e) {
        }

        //checks for a blank first name
        if (firstNameField.getText().equals("")) {
            //adds to error string
            error += ADD_ERROR_FIRST_NAME + "\n";
        }

        //checks for a blank last name
        if (lastNameField.getText().equals("")) {
            //adds to error string
            error += ADD_ERROR_LAST_NAME + "\n";
        }

        //checks if employee number is negative
        if (objToInt(employeeNumberField.getValue()) < 0) {
            //adds to error string
            error += ADD_ERROR_EMPLOYEE_NUMBER + "\n";
        }

        //checks for a delimiter in the text fields
        if (firstNameField.getText().contains(DELIMITER) || lastNameField.getText().contains(DELIMITER)) {
            //adds to error string
            error += ADD_ERROR_CONTAINS_DELIMITER;
        }

        //checks if error free
        if (error.length() == 0) {
            
            //removes the current employee if user is modifying (in order to avoid conflicts)
            if (employeeFormPanel.getTitle().equals(MODIFY_EMPLOYEE_TITLE)) {

                menuHashTable.removeEmployee(undoEmployees.getLast().getEmployee1().getEmployeeNumber());
            }
        
            //checks which employee type panel was currently selected
            if (employeeTypePanel.getSelectedComponent() == fullTimeEmployeePanel) {

                //adds a full time employee
                menuHashTable.addEmployee(new FullTimeEmployee(
                        objToInt(employeeNumberField.getValue()),
                        firstNameField.getText(),
                        lastNameField.getText(),
                        sexField.getSelectedIndex(),
                        objToDouble(annualSalaryField.getValue()),
                        objToDouble(deductionsRateField.getValue()),
                        workLocationField.getSelectedIndex()
                ));
                if (employeeFormPanel.getTitle().equals(MODIFY_EMPLOYEE_TITLE)) {
                    //adds to archived employee table
                    undoEmployees.add(new ArchivedEmployee(undoEmployees.getLast().getEmployee1(),3,new FullTimeEmployee(
                            objToInt(employeeNumberField.getValue()),
                            firstNameField.getText(),
                            lastNameField.getText(),
                            sexField.getSelectedIndex(),
                            objToDouble(annualSalaryField.getValue()),
                            objToDouble(deductionsRateField.getValue()),
                            workLocationField.getSelectedIndex()
                    )));
                    undoEmployees.remove(undoEmployees.size()-2);
                } else{
                    //adds to archived employee table
                    undoEmployees.add(new ArchivedEmployee(new FullTimeEmployee(
                            objToInt(employeeNumberField.getValue()),
                            firstNameField.getText(),
                            lastNameField.getText(),
                            sexField.getSelectedIndex(),
                            objToDouble(annualSalaryField.getValue()),
                            objToDouble(deductionsRateField.getValue()),
                            workLocationField.getSelectedIndex()
                    ),1));
                }
            } else if (employeeTypePanel.getSelectedComponent() == partTimeEmployeePanel) {

                //adds a part time employee
                menuHashTable.addEmployee(new PartTimeEmployee(
                        objToInt(employeeNumberField.getValue()),
                        firstNameField.getText(),
                        lastNameField.getText(),
                        sexField.getSelectedIndex(),
                        objToDouble(hourlyWageField.getValue()),
                        objToDouble(deductionsRateField.getValue()),
                        workLocationField.getSelectedIndex(),
                        objToInt(hoursPerWeekField.getValue()),
                        objToInt(weeksPerYearField.getValue())
                ));
                
                if (employeeFormPanel.getTitle().equals(MODIFY_EMPLOYEE_TITLE)) {
                    //adds to archived employee table
                    undoEmployees.add(new ArchivedEmployee(undoEmployees.getLast().getEmployee1(),3,new PartTimeEmployee(
                        objToInt(employeeNumberField.getValue()),
                        firstNameField.getText(),
                        lastNameField.getText(),
                        sexField.getSelectedIndex(),
                        objToDouble(hourlyWageField.getValue()),
                        objToDouble(deductionsRateField.getValue()),
                        workLocationField.getSelectedIndex(),
                        objToInt(hoursPerWeekField.getValue()),
                        objToInt(weeksPerYearField.getValue())
                )));
                    undoEmployees.remove(undoEmployees.size()-2);
                } else{
                    //adds to archived employee table
                    undoEmployees.add(new ArchivedEmployee(new PartTimeEmployee(
                        objToInt(employeeNumberField.getValue()),
                        firstNameField.getText(),
                        lastNameField.getText(),
                        sexField.getSelectedIndex(),
                        objToDouble(hourlyWageField.getValue()),
                        objToDouble(deductionsRateField.getValue()),
                        workLocationField.getSelectedIndex(),
                        objToInt(hoursPerWeekField.getValue()),
                        objToInt(weeksPerYearField.getValue())
                ),1));
                }
            }

            //updates the table and hides the popup
            changesMade(true);
            employeeFormPanel.setVisible(false);
            refreshTable(menuHashTable);
        } //handles errors and prompts with all errors
        else {
            JOptionPane.showMessageDialog(new JFrame(), error, "Error", JOptionPane.ERROR_MESSAGE);
        }
                                             
        if (!undoEmployees.isEmpty()){
            System.out.println("Archived List:");
            for (ArchivedEmployee a : undoEmployees){
                System.out.println("name: " + a.getEmployee1().getFirstName() + ", type: " + a.getActionType());
            }
        }
    }//GEN-LAST:event_saveButtonActionPerformed

    private void sexFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sexFieldActionPerformed
        //UNUSED METHOD
    }//GEN-LAST:event_sexFieldActionPerformed

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        //hides the popup
        employeeFormPanel.setVisible(false);
        if (employeeFormPanel.getTitle().equals(MODIFY_EMPLOYEE_TITLE)) {
            undoEmployees.removeLast();
        }
    }//GEN-LAST:event_cancelButtonActionPerformed

    private void workLocationFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_workLocationFieldActionPerformed
        //UNUSED METHOD
    }//GEN-LAST:event_workLocationFieldActionPerformed

    private void jButton_EditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_EditActionPerformed
        //checks if only one employee is selected
        if (EmployeeTable.getSelectedRowCount() == 1) {

            try {
                //finds the selected employee
                EmployeeInfo employeeToEdit = menuHashTable.getEmployee(objToInt(EmployeeTable.getValueAt(EmployeeTable.getSelectedRow(), 2)));

                undoEmployees.add(new ArchivedEmployee(employeeToEdit,3));
                
                //sets fields of popup to selected employee's attributes
                firstNameField.setText(employeeToEdit.getFirstName());
                lastNameField.setText(employeeToEdit.getLastName());
                employeeNumberField.setValue(employeeToEdit.getEmployeeNumber());
                deductionsRateField.setValue(employeeToEdit.getDeductionsRate());
                workLocationField.setSelectedIndex(employeeToEdit.getWorkLocation());

                //sets fields according to whether employee is part time or full time
                if (employeeToEdit instanceof PartTimeEmployee) {
                    PartTimeEmployee PTEEmployeeToEdit = (PartTimeEmployee) employeeToEdit;
                    //calculates backwards to fine hourly wage
                    hourlyWageField.setValue(PTEEmployeeToEdit.getPay() / ((1 - PTEEmployeeToEdit.getDeductionsRate()) * (PTEEmployeeToEdit.getHoursPerWeek() * PTEEmployeeToEdit.getWeeksPerYear())));
                    hoursPerWeekField.setValue(PTEEmployeeToEdit.getHoursPerWeek());
                    weeksPerYearField.setValue(PTEEmployeeToEdit.getWeeksPerYear());
                    employeeTypePanel.setSelectedComponent(partTimeEmployeePanel);
                } else if (employeeToEdit instanceof FullTimeEmployee) {
                    FullTimeEmployee FTEEmployeeToEdit = (FullTimeEmployee) employeeToEdit;
                    //calculates backwards to find annual salary
                    annualSalaryField.setValue(FTEEmployeeToEdit.getPay() / (1 - FTEEmployeeToEdit.getDeductionsRate()));
                    employeeTypePanel.setSelectedComponent(fullTimeEmployeePanel);
                }

                //updates the title of the popup
                employeeFormPanel.setTitle(MODIFY_EMPLOYEE_TITLE);

                //disables the employee number field (because that would constitute making a new employee)
                //employeeNumberField.setEnabled(false);

                //displays popup
                employeeFormPanel.setVisible(true);

                //handles any invalid selection
            } catch (Exception e) {
                JOptionPane.showMessageDialog(new JFrame(), SELECTION_ERROR_INVALID);
            }
            //handles multiple employee selection
        } else {
            JOptionPane.showMessageDialog(new JFrame(), SELECTION_ERROR_SINGLE_ONLY);
        }
                                             
        if (!undoEmployees.isEmpty()){
            System.out.println("Archived List:");
            for (ArchivedEmployee a : undoEmployees){
                System.out.println("name: " + a.getEmployee1().getFirstName() + ", type: " + a.getActionType());
            }
        }
    }//GEN-LAST:event_jButton_EditActionPerformed

    private void JMenuItem_QuitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JMenuItem_QuitActionPerformed
        //sets a default value in case changes were not made
        int saveChange = 1;

        //checks if changes were made and if user wants to save (only prompts if changes were made)
        if (wereChangesMade && (saveChange = YesNoPrompt(UNSAVED_CHANGES_PROMPT, CONFIRM_SAVE_PROMPT)) == JOptionPane.YES_OPTION) {
            //calls save method
            JMenuItem_SaveActionPerformed(evt);
        } else if (saveChange == JOptionPane.CLOSED_OPTION) {
            //exits method before it closes application
            return;
        }
        //exits 
        System.exit(0);
    }//GEN-LAST:event_JMenuItem_QuitActionPerformed

    private void jButton_RemoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_RemoveActionPerformed
        //makes sure atleast one person is selected to delete
        if (EmployeeTable.getSelectedRowCount() > 0) {
            //new list for employees that are selected to be removed
            ArrayList<Integer> employeesToRemove = new ArrayList<>(EmployeeTable.getSelectedRowCount());

            //string to display when confirming the deletion of the selected employee(s)
            String confirmText = CONFIRM_DELETE_PROMPT;
            
            //makes employee plural if multiple employees are selected
            if (EmployeeTable.getSelectedRowCount() > 1) {
                confirmText += "s";
            }
            confirmText += "?\n";

            //iterates through employees selected
            for (int a : EmployeeTable.getSelectedRows()) {
                //adds employee to list
                employeesToRemove.add(objToInt(EmployeeTable.getValueAt(a, 2)));
                if (confirmText.split("\n").length < DELETE_CONFIRM_EMPLOYEES_TO_DISPLAY+1){
                //adding employee to confirm message
                confirmText += ((String) EmployeeTable.getValueAt(a, 0)) + " " + ((String) EmployeeTable.getValueAt(a, 1)) + "\n";
                }
            }
            if (EmployeeTable.getSelectedRowCount()-DELETE_CONFIRM_EMPLOYEES_TO_DISPLAY > 0){
                //adds message for how many other employees are also to be deleted
                confirmText += "(and " + (EmployeeTable.getRowCount()-10) + " more ...)";
            }
            //confirms with user before deleting
            if (YesNoPrompt("Confirm Deletion", confirmText) == JOptionPane.YES_OPTION) {
                for (int b : employeesToRemove) {
                    //moves employee to deletedEmployees
                    undoEmployees.add(new ArchivedEmployee(menuHashTable.getEmployee(b),2));
                    //removes employee selected from main table
                    menuHashTable.removeEmployee(b);
                }
                //updates table
                refreshTable(menuHashTable);
                //updates application for changes
                changesMade(true);
            }
            //handles invalid selection
        } else {
            JOptionPane.showMessageDialog(new JFrame(), SELECTION_ERROR_ATLEAST_ONE);
        }
                                             
        if (!undoEmployees.isEmpty()){
            System.out.println("Archived List:");
            for (ArchivedEmployee a : undoEmployees){
                System.out.println("name: " + a.getEmployee1().getFirstName() + ", type: " + a.getActionType());
            }
        }
    }//GEN-LAST:event_jButton_RemoveActionPerformed

    private void searchTypeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchTypeActionPerformed
        //calls search method
        searchButtonActionPerformed(evt);
    }//GEN-LAST:event_searchTypeActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        //calls quitting method
        JMenuItem_QuitActionPerformed(null);
    }//GEN-LAST:event_formWindowClosing


    private void searchBarKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchBarKeyTyped
        //USELESS METHOD
    }//GEN-LAST:event_searchBarKeyTyped

    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed

    }//GEN-LAST:event_formKeyPressed

    private void jMenu_FileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenu_FileActionPerformed
        //USELESS METHOD
    }//GEN-LAST:event_jMenu_FileActionPerformed

    private void jMenuItem_GuideActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem_GuideActionPerformed
        //creates a new file for the user guide
        File file = new File(USER_GUIDE_FILE_NAME);
        try {
            //opens the guide
            Desktop.getDesktop().open(file);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(new JFrame(), "User Guide is missing!");
            Logger.getLogger(JFrame_EmployeeDatabase_GUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jMenuItem_GuideActionPerformed

    private void jMenuItem_UndoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem_UndoActionPerformed
        if (!undoEmployees.isEmpty()){
            System.out.println("Before Undo Archived List:");
            for (ArchivedEmployee a : undoEmployees){
                System.out.println("name: " + a.getEmployee1().getFirstName() + ", type: " + a.getActionType());
            }
            switch(undoEmployees.getLast().getActionType()){
                case 1:
                    menuHashTable.removeEmployee(undoEmployees.getLast().getEmployee1().getEmployeeNumber());
                    break;
                case 2:
                    menuHashTable.addEmployee(undoEmployees.getLast().getEmployee1());
                    break;
                case 3:
                    if (undoEmployees.getLast().getEmployee2() != null){
                        menuHashTable.removeEmployee(undoEmployees.getLast().getEmployee2().getEmployeeNumber());
                        menuHashTable.addEmployee(undoEmployees.getLast().getEmployee1());
                    }
                    break;
                default:
                    System.out.println("Invalid Case");
                    break;
            }
            redoEmployees.add(undoEmployees.getLast());
            undoEmployees.removeLast();
            changesMade(true);
            refreshTable(menuHashTable);
            
            System.out.println("After Undo Archived List:");
            for (ArchivedEmployee a : undoEmployees){
                System.out.println("name: " + a.getEmployee1().getFirstName() + ", type: " + a.getActionType());
            }
        }
    }//GEN-LAST:event_jMenuItem_UndoActionPerformed

    private void jMenuItem_RedoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem_RedoActionPerformed
        if (!redoEmployees.isEmpty()){
            System.out.println("Before Redo Archived List:");
            for (ArchivedEmployee a : redoEmployees){
                System.out.println("name: " + a.getEmployee1().getFirstName() + ", type: " + a.getActionType());
            }
            switch(redoEmployees.getLast().getActionType()){
                case 1:
                    menuHashTable.addEmployee(redoEmployees.getLast().getEmployee1());
                    break;
                case 2:
                    menuHashTable.removeEmployee(redoEmployees.getLast().getEmployee1().getEmployeeNumber());
                    break;
                case 3:
                    if (redoEmployees.getLast().getEmployee1() != null){
                        menuHashTable.removeEmployee(redoEmployees.getLast().getEmployee1().getEmployeeNumber());
                        menuHashTable.addEmployee(redoEmployees.getLast().getEmployee2());
                    }
                    break;
                default:
                    System.out.println("Invalid Case");
                    break;
            }
            undoEmployees.add(redoEmployees.getLast());
            redoEmployees.removeLast();
            changesMade(true);
            refreshTable(menuHashTable);
            
            System.out.println("After Redo Archived List:");
            for (ArchivedEmployee a : redoEmployees){
                System.out.println("name: " + a.getEmployee1().getFirstName() + ", type: " + a.getActionType());
            }
        }
    }//GEN-LAST:event_jMenuItem_RedoActionPerformed

    private void jMenuItem_AddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem_AddActionPerformed
        jButton_AddActionPerformed(evt);
    }//GEN-LAST:event_jMenuItem_AddActionPerformed

    private void jMenuItem_EditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem_EditActionPerformed
        jButton_EditActionPerformed(evt);
    }//GEN-LAST:event_jMenuItem_EditActionPerformed

    private void jMenuItem_RemoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem_RemoveActionPerformed
        jButton_RemoveActionPerformed(evt);
    }//GEN-LAST:event_jMenuItem_RemoveActionPerformed

    //main method initializes the application and sets the look and feel
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */

        try {
            //sets look and feel
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
            //catches exception
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JFrame_EmployeeDatabase_GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new JFrame_EmployeeDatabase_GUI().setVisible(true);
            }
        });
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel BasicInfoPanel;
    private javax.swing.JTable EmployeeTable;
    private javax.swing.JMenuItem JMenuItem_Load;
    private javax.swing.JMenuItem JMenuItem_New;
    private javax.swing.JMenuItem JMenuItem_Quit;
    private javax.swing.JMenuItem JMenuItem_Save;
    private javax.swing.JMenuItem JMenuItem_SaveAs;
    private javax.swing.JPanel MainPanel;
    private javax.swing.JMenuBar TopMenu;
    private javax.swing.JSpinner annualSalaryField;
    private javax.swing.JLabel annualSalaryLabel;
    private javax.swing.JButton cancelButton;
    private javax.swing.JSpinner deductionsRateField;
    private javax.swing.JLabel deductionsRateLabel;
    private javax.swing.JDialog employeeFormPanel;
    private javax.swing.JSpinner employeeNumberField;
    private javax.swing.JLabel employeeNumberLabel;
    private javax.swing.JTabbedPane employeeTypePanel;
    private javax.swing.JFileChooser fileChooser;
    private javax.swing.JTextField firstNameField;
    private javax.swing.JLabel firstNameLabel;
    private javax.swing.JPanel fullTimeEmployeePanel;
    private javax.swing.JSpinner hourlyWageField;
    private javax.swing.JLabel hourlyWageLabel;
    private javax.swing.JSpinner hoursPerWeekField;
    private javax.swing.JLabel hoursPerWeekLabel;
    private javax.swing.JButton jButton_Add;
    private javax.swing.JButton jButton_Edit;
    private javax.swing.JButton jButton_Refresh;
    private javax.swing.JButton jButton_Remove;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuItem jMenuItem_Add;
    private javax.swing.JMenuItem jMenuItem_Edit;
    private javax.swing.JMenuItem jMenuItem_Guide;
    private javax.swing.JMenuItem jMenuItem_Redo;
    private javax.swing.JMenuItem jMenuItem_Remove;
    private javax.swing.JMenuItem jMenuItem_Undo;
    private javax.swing.JMenu jMenu_File;
    private javax.swing.JMenu jMenu_File1;
    private javax.swing.JMenu jMenu_Help;
    private javax.swing.JPanel jPanel_BottomButtons;
    private javax.swing.JPanel jPanel_TopButtons;
    private javax.swing.JScrollPane jScrollPane_EmployeeTable;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JPopupMenu.Separator jSeparator3;
    private javax.swing.JPopupMenu.Separator jSeparator4;
    private javax.swing.JTextField lastNameField;
    private javax.swing.JLabel lastNameLabel;
    private javax.swing.JPanel mainInterface;
    private javax.swing.JPanel partTimeEmployeePanel;
    private javax.swing.JButton saveButton;
    private javax.swing.JTextField searchBar;
    private javax.swing.JButton searchButton;
    private javax.swing.JComboBox<String> searchType;
    private javax.swing.JComboBox<String> sexField;
    private javax.swing.JLabel sexLabel;
    private javax.swing.JSpinner weeksPerYearField;
    private javax.swing.JLabel weeksPerYearLabel;
    private javax.swing.JComboBox<String> workLocationField;
    private javax.swing.JLabel workLocationLabel;
    // End of variables declaration//GEN-END:variables

    private int YesNoPrompt(String title, String question) {
        //prompts user with a yes/no confirm prompt
        return JOptionPane.showConfirmDialog(new JFrame(), question, title, JOptionPane.YES_NO_OPTION);

    }

    private void changesMade(boolean wereAnyChangesMade) {
        //updates changes variable
        wereChangesMade = wereAnyChangesMade;

        //checks if the application has an asterisk and whether it should (asterisk means unsaved changes exist)
        if (JFrame_EmployeeDatabase_GUI.super.getTitle().endsWith("*") && !wereAnyChangesMade) {
            //takes away asterisk
            JFrame_EmployeeDatabase_GUI.super.setTitle(JFrame_EmployeeDatabase_GUI.super.getTitle().substring(0, JFrame_EmployeeDatabase_GUI.super.getTitle().length() - 1));
        } else if (!(JFrame_EmployeeDatabase_GUI.super.getTitle().endsWith("*")) && wereAnyChangesMade) {
            //adds an astersik
            JFrame_EmployeeDatabase_GUI.super.setTitle(JFrame_EmployeeDatabase_GUI.super.getTitle() + "*");
        }
    }

    private void removeAllEmployees() {
        //clears table
        menuHashTable.removeAllEmployees();

        //for debugging purposes
        System.out.println("Deleted all employees");

        //updates table
        refreshTable(menuHashTable);
    }

    private void refreshTable(HashTable menuHashTable) {
        //hides the table during changes
        EmployeeTable.setVisible(false);

        //creates a model for the employee table
        DefaultTableModel employeeTableModel = (DefaultTableModel) EmployeeTable.getModel();

        //clears the table
        employeeTableModel.setRowCount(0);

        //gets an array of employees
        EmployeeInfo employees[] = menuHashTable.getAllEmployees();

        //checks if there are employees
        if (employees != null) {

            //allots rows for all employees
            employeeTableModel.setRowCount(employees.length);

            //iterates through employees
            for (int a = 0; a < employees.length; a++) {
                //checks if employee is null
                if (employees[a] != null) {
                    //fills cells with employee attributes
                    employeeTableModel.setValueAt(employees[a].getFirstName(), a, 0);
                    employeeTableModel.setValueAt(employees[a].getLastName(), a, 1);
                    employeeTableModel.setValueAt(employees[a].getEmployeeNumber(), a, 2);

                    //parses sex attribute to fill cells with gender terms
                    switch (employees[a].getSex()) {
                        case 1:
                            employeeTableModel.setValueAt("Female", a, 3);
                            break;
                        case 0:
                            employeeTableModel.setValueAt("Male", a, 3);
                            break;
                        default:
                            employeeTableModel.setValueAt("Other", a, 3);
                            break;
                    }
                    
                    employeeTableModel.setValueAt("-", a, 4);
                    for (int locationNumber = 0;locationNumber <= WORK_LOCATION.length; locationNumber++){
                        if(employees[a].getWorkLocation() == locationNumber){
                            employeeTableModel.setValueAt(WORK_LOCATION[locationNumber], a, 4);
                            break;
                        }
                    }
                    /*//parses work location attribute to fill cells with locations
                    switch (employees[a].getWorkLocation()) {
                        case 0:
                            employeeTableModel.setValueAt("Mississauga", a, 4);
                            break;
                        case 1:
                            employeeTableModel.setValueAt("Chicago", a, 4);
                            break;
                        case 2:
                            employeeTableModel.setValueAt("New York", a, 4);
                            break;
                        default:
                            employeeTableModel.setValueAt("-", a, 4);
                            break;
                    }*/

                    //checks if employee is a full time employee and fills cells accordingly
                    if (employees[a] instanceof FullTimeEmployee) {
                        employeeTableModel.setValueAt("Full-Time", a, 5);
                    } else if (employees[a] instanceof PartTimeEmployee) {
                        employeeTableModel.setValueAt("Part-Time", a, 5);
                    } else {
                        employeeTableModel.setValueAt("-", a, 5);
                    }

                    //fills cell for deductions rate
                    employeeTableModel.setValueAt(employees[a].getDeductionsRate(), a, 6);

                    //checks if pay is valid
                    if (employees[a].getPay() >= 0) {
                        //creates a decimal format to display 2 decimal digits
                        DecimalFormat decimalFormat = new DecimalFormat();
                        decimalFormat.setMaximumFractionDigits(2);
                        decimalFormat.setMinimumFractionDigits(2);

                        //fills cell
                        employeeTableModel.setValueAt(decimalFormat.format(employees[a].getPay()), a, 7);
                    } else {
                        //fills cell with 0 for any invalid pay
                        employeeTableModel.setValueAt(0, a, 7);
                    }

                    //updates table
                    employeeTableModel.fireTableDataChanged();
                }
            }
        }
        //repaints the table
        EmployeeTable.repaint();

        //makes table visible again
        EmployeeTable.setVisible(true);
    }

    private double objToDouble(Object theObject) {
        //tries to cast to integer
        if (theObject instanceof Integer) {
            return (Integer) theObject;
        } //tries to cast as a double
        else if (theObject instanceof Double) {
            return (Double) theObject;
        } else {
            System.out.println("Invalid object in objToDouble method");
            return Double.MIN_VALUE;
        }
    }

    private boolean isNumerical(String value) {
        //attempts to parse as double or integer
        try {
            Double.parseDouble(value);
        } catch (NumberFormatException e) {
            try {
                Integer.parseInt(value);
            } catch (NumberFormatException e2) {
                //returns false if not an integer or double
                return false;
            }
        }
        //returns true on default
        return true;
    }

    private int objToInt(Object theObject) {
        //checks if object is an integer
        if (theObject instanceof Integer) {
            return (Integer) theObject;
        } //checks if the object is a double
        else if (theObject instanceof Double) {
            return ((int) Math.round((Double) theObject));
        } //retuns minimum value by default
        else {
            return Integer.MIN_VALUE;
        }
    }
}
