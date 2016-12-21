package employeedatabase;

//imports
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
    //declaring variables for database organization
    String DELIMITER = ";";
    String FORMAT = "DUTTON";
    String FORMATEXPANDED = "Database Universal Text Traversing Operators Network";

    //declaring variables for global application usage
    File activeFile;
    HashTable menuHashTable;
    Boolean wereChangesMade;

    //class constructor
    public JFrame_EmployeeDatabase_GUI() {
        //initializes gui elements
        initComponents();

        //sets up application with initial properties
        changesMade(false);
        JFrame_EmployeeDatabase_GUI.super.setTitle("Employee Database Program - Untitled." + FORMAT);
        EmployeeTable.getTableHeader().setReorderingAllowed(false);
        fileChooser.setFileFilter(new FileNameExtensionFilter(FORMATEXPANDED + " (*." + FORMAT + ")", FORMAT));

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
                if (searchBar.getText().toLowerCase().replace(" ", "").equals("Enter Search Query ...") || searchBar.getText().replace(" ", "").equals("")) {
                    refreshTable(menuHashTable);
                }
                //displays the new table
                refreshTable(searchTable);
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
            public void keyPressed(KeyEvent ke){
                //checks if the key pressed is the 'delete' key (DEL)
                if (ke.getExtendedKeyCode() == 127){
                    //calls the remove method
                    jButton_RemoveActionPerformed(null);
                }
                //checks if the key pressed is an 'E' and whether it had a ctrl modifier (CTRL+E)
                if (ke.getExtendedKeyCode() == KeyEvent.VK_E && ke.getModifiersEx() == 128){
                    //edits employee that was selected
                    jButton_EditActionPerformed(null);
                }
                //checks if the key pressed is an 'R' and whether it had a ctrl modifier (CTRL+E)
                if (ke.getExtendedKeyCode() == KeyEvent.VK_R && ke.getModifiersEx() == 128){
                    //edits employee that was selected
                    refreshTable(menuHashTable);
                }
                
            }
        });
    }

    //method for reading a file
    private String readFile(String filePath) throws FileNotFoundException, IOException {

        //creates new variables for file reading and compiling the text together
        String fileLine;
        String compiledText = "";

        //reads a line and adds it to the 'compiledText' variable
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath))) {
            //makes sure the line is not null (while also storing the text in a variable)
            while ((fileLine = bufferedReader.readLine()) != null) {
                compiledText += fileLine + "\n";
            }
        }

        //returns the final string
        return (compiledText);
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

        employeeFormPanel.setBounds(new java.awt.Rectangle(0, 0, 450, 430));
        employeeFormPanel.setMinimumSize(new java.awt.Dimension(450, 430));
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

        workLocationField.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Mississauga", "Chicago", "New York" }));
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
        getContentPane().setLayout(new java.awt.GridLayout(1, 0));

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
                .add(searchBar)
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
                .add(jButton_Add, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 189, Short.MAX_VALUE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jButton_Edit, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 191, Short.MAX_VALUE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jButton_Remove, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 191, Short.MAX_VALUE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jButton_Refresh, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 191, Short.MAX_VALUE)
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
                .add(jScrollPane_EmployeeTable, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 473, Short.MAX_VALUE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(jPanel_BottomButtons, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        getContentPane().add(mainInterface);

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

        jMenu_File1.setText("Save Database");

        JMenuItem_Save.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        JMenuItem_Save.setText("Save Database");
        JMenuItem_Save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JMenuItem_SaveActionPerformed(evt);
            }
        });
        jMenu_File1.add(JMenuItem_Save);

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

        setJMenuBar(TopMenu);

        setSize(new java.awt.Dimension(816, 639));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void JMenuItem_NewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JMenuItem_NewActionPerformed

        //checks if changes were made in order to confirm saving the database
        if (!wereChangesMade) {
            return;

            //prompts user and, depending on the answer, saves the database
        } else if (wereChangesMade && YesNoPrompt("", "Would you like to save your database?") == JOptionPane.YES_OPTION) {
            JMenuItem_SaveActionPerformed(evt);
        }

        //clears and resets the application
        removeAllEmployees();
        JFrame_EmployeeDatabase_GUI.super.setTitle("Employee Database Program - UNTITLED DATABASE");
        activeFile = null;
        changesMade(false);
    }//GEN-LAST:event_JMenuItem_NewActionPerformed

    private void JMenuItem_LoadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JMenuItem_LoadActionPerformed
        //prompt user to select a file to load
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            try {
                File selectedFile = fileChooser.getSelectedFile();
                //prints out the file for debugging purposes
                System.out.println(selectedFile.getAbsolutePath());

                //reads the file for 
                String fileText = readFile(selectedFile.getAbsolutePath());
                String[] fileTextLines = fileText.split("\n");

                //clear the database
                removeAllEmployees();

                for (String fileTextLine : fileTextLines) {
                    System.out.println("> " + fileTextLine);
                    //splits line into attributes
                    String[] employeeAttributes = fileTextLine.split(DELIMITER);

                    // Adds employee (part or full time is based on number of attributes)
                    switch (employeeAttributes.length) {
                        case 8:
                            System.out.println("full time employee added");
                            menuHashTable.addEmployee(new FullTimeEmployee(
                                    Boolean.parseBoolean(employeeAttributes[0]),
                                    Integer.parseInt(employeeAttributes[1]),
                                    employeeAttributes[2],
                                    employeeAttributes[3],
                                    Integer.parseInt(employeeAttributes[4]),
                                    Float.parseFloat(employeeAttributes[5]),
                                    Float.parseFloat(employeeAttributes[6]),
                                    Integer.parseInt(employeeAttributes[7])
                            ));
                            break;
                        case 10:
                            System.out.println("part time employee added");
                            menuHashTable.addEmployee(new PartTimeEmployee(
                                    Boolean.parseBoolean(employeeAttributes[0]),
                                    Integer.parseInt(employeeAttributes[1]),
                                    employeeAttributes[2],
                                    employeeAttributes[3],
                                    Integer.parseInt(employeeAttributes[4]),
                                    Float.parseFloat(employeeAttributes[5]),
                                    Float.parseFloat(employeeAttributes[6]),
                                    Integer.parseInt(employeeAttributes[7]),
                                    Integer.parseInt(employeeAttributes[8]),
                                    Integer.parseInt(employeeAttributes[9])
                            ));
                            break;
                        default:
                            System.out.println("Invalid Employee");
                            break;
                    }
                }
                //sets application title and current file to the loaded file
                JFrame_EmployeeDatabase_GUI.super.setTitle("Employee Database Program - " + selectedFile.getName());
                activeFile = selectedFile;
                //catches any exceptions to keep the application running
            } catch (IOException ex) {
                Logger.getLogger(JFrame_EmployeeDatabase_GUI.class.getName()).log(Level.SEVERE, null, ex);
            }
            //handles an invalid file selection
        } else {
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
                    System.out.println("Saved Employee!");
                }
            } catch (FileNotFoundException ex) {
                Logger.getLogger(JFrame_EmployeeDatabase_GUI.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                //closes the printwriter
                writer.close();
            }
            //for debugging purposes
            System.out.println("Saved Database!");
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
            if (!selectedFile.getAbsolutePath().endsWith("." + FORMAT.toLowerCase())) {
                //adds a format at the end if not included
                selectedFile = new File(selectedFile.getAbsolutePath() + "." + FORMAT);
            }

            //checks if a valid selection was made, and if file exists, it prompts to overwrite
            if (!selectedFile.exists() || selectedFile.exists() && YesNoPrompt("", "Do you want to overwrite the existing file?") == JOptionPane.YES_OPTION) {
                //sets selected file as the active file of the database
                activeFile = selectedFile;
                //saves the database (calls save method)
                JMenuItem_SaveActionPerformed(evt);
                //sets application 
                JFrame_EmployeeDatabase_GUI.super.setTitle("Employee Database Program - " + selectedFile.getName());
            }
            //handles invalid selection
        } else {
            System.out.println("No valid file selected");
        }
    }//GEN-LAST:event_JMenuItem_SaveAsActionPerformed

    private void searchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchButtonActionPerformed
        HashTable searchTable = new HashTable(2);
        for (int employee : menuHashTable.broadSearchEmployee(searchBar.getText(), searchType.getSelectedIndex())) {
            searchTable.addEmployee(menuHashTable.getEmployee(employee));
        }
        refreshTable(searchTable);
        if (menuHashTable.getAllEmployees() == null) {
            JOptionPane.showMessageDialog(new JFrame(), "The database is empty");
        } else if (searchBar.getText().toLowerCase().replace(" ", "").equals("Enter Search Query ...")) {
            refreshTable(menuHashTable);
        } else if (searchBar.getText().replace(" ", "").equals("")) {
            JOptionPane.showMessageDialog(new JFrame(), "Please enter something into the search bar");
            refreshTable(menuHashTable);
        } else if (searchType.getSelectedIndex() != 1 && !isNumerical(searchBar.getText())) {
            JOptionPane.showMessageDialog(new JFrame(), "Invalid Search type");
        } else if (searchTable.getAllEmployees() == null) {
            JOptionPane.showMessageDialog(new JFrame(), "No employee matches your search");
        }
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
        employeeFormPanel.setTitle("Add an Employee");

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

        //removes the current employee if user is modifying (in order to avoid conflicts)
        if (employeeFormPanel.getTitle().equals("Modify an Employee")) {
            menuHashTable.removeEmployee(objToInt(employeeNumberField.getValue()));
        }

        //checks for whether employee already exists
        try {
            if (menuHashTable.searchEmployee((Integer) employeeNumberField.getValue()) != -1) {
                //adds to error string
                error += "Employee " + employeeNumberField.getValue() + " already exists!\n";
            }
        } catch (Exception e) {
        }

        //checks for a blank first name
        if (firstNameField.getText().equals("")) {
            //adds to error string
            error += "You need to enter a first name!\n";
        }

        //checks for a blank last name
        if (lastNameField.getText().equals("")) {
            //adds to error string
            error += "You need to enter a last name!\n";
        }

        //checks if employee number is negative
        if (objToInt(employeeNumberField.getValue()) < 0) {
            //adds to error string
            error += "Employee number must be a positive integer!\n";
        }

        //checks for a delimiter in the text fields
        if (firstNameField.getText().contains(DELIMITER) || lastNameField.getText().contains(DELIMITER)) {
            //adds to error string
            error += "The employee name cannot contain '" + DELIMITER + "'!";
        }

        //checks if error free
        if (error.length() == 0) {
            //checks which employee type panel was currently selected
            if (employeeTypePanel.getSelectedComponent() == fullTimeEmployeePanel) {

                //adds a full time employee
                menuHashTable.addEmployee(new FullTimeEmployee(
                        true,
                        objToInt(employeeNumberField.getValue()),
                        firstNameField.getText(),
                        lastNameField.getText(),
                        sexField.getSelectedIndex(),
                        objToDouble(annualSalaryField.getValue()),
                        objToDouble(deductionsRateField.getValue()),
                        workLocationField.getSelectedIndex()
                ));
            } else if (employeeTypePanel.getSelectedComponent() == partTimeEmployeePanel) {

                //adds a part time employee
                menuHashTable.addEmployee(new PartTimeEmployee(
                        false,
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
            }

            //updates the table and hides the popup
            changesMade(true);
            employeeFormPanel.setVisible(false);
            refreshTable(menuHashTable);
        } //handles errors and prompts with all errors
        else {
            JOptionPane.showMessageDialog(new JFrame(), error, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_saveButtonActionPerformed

    private void sexFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sexFieldActionPerformed
        //UNUSED METHOD
    }//GEN-LAST:event_sexFieldActionPerformed

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        //hides the popup
        employeeFormPanel.setVisible(false);
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
                employeeFormPanel.setTitle("Modify an Employee");

                //disables the employee number field (because that would constitute making a new employee)
                employeeNumberField.setEnabled(false);

                //displays popup
                employeeFormPanel.setVisible(true);

                //handles any invalid selection
            } catch (Exception e) {
                JOptionPane.showMessageDialog(new JFrame(), "invalid selecton");
            }
            //handles multiple employee selection
        } else {
            JOptionPane.showMessageDialog(new JFrame(), "You must have a single employee selected from the table!");
        }
    }//GEN-LAST:event_jButton_EditActionPerformed

    private void JMenuItem_QuitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JMenuItem_QuitActionPerformed
        //sets a default value in case changes were not made
        int saveChange = 1;

        //checks if changes were made and if user wants to save (only prompts if changes were made)
        if (wereChangesMade && (saveChange = YesNoPrompt("You have unsaved data", "Do you want to save your changes before exiting?")) == JOptionPane.YES_OPTION) {
            //calls save method
            saveButtonActionPerformed(evt);
        } else if (saveChange == JOptionPane.CLOSED_OPTION) {
            //exits method before it closes application
            return;
        }
        //exits 
        System.exit(0);
    }//GEN-LAST:event_JMenuItem_QuitActionPerformed

    private void jButton_RemoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_RemoveActionPerformed
        //checks to see if any employees are selected in the table
        if (EmployeeTable.getSelectedRowCount() > 0) {
            //iterates through employees selected
            for (int a : EmployeeTable.getSelectedRows()) {
                //removes employee selected
                menuHashTable.removeEmployee(objToInt(EmployeeTable.getValueAt(a, 2)));
            }
            //updates table
            refreshTable(menuHashTable);
        } else {
            JOptionPane.showMessageDialog(new JFrame(), "You must have at least one employee selected from the table");
        }
        //updates application for changes
        changesMade(true);

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
        System.out.println(evt.getKeyChar());
    }//GEN-LAST:event_formKeyPressed

    private void jMenu_FileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenu_FileActionPerformed
        //USELESS METHOD
    }//GEN-LAST:event_jMenu_FileActionPerformed

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
    private javax.swing.JMenu jMenu_File;
    private javax.swing.JMenu jMenu_File1;
    private javax.swing.JPanel jPanel_BottomButtons;
    private javax.swing.JPanel jPanel_TopButtons;
    private javax.swing.JScrollPane jScrollPane_EmployeeTable;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JPopupMenu.Separator jSeparator3;
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

                    //parses work location attribute to fill cells with locations
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
                    }

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
