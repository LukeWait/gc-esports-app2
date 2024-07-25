/*******************************************************************************
File name:  GC_EGames_V2_GUI.java
Purpose:    1.  Set up GUI and functionality for the teamsStrArray and competitions
            2.  Constructor method reads in data from goldcoast_esports database and sets up GUI
            3.  Program uses DB_Read and DB_Write classes to connect to database
                and run SQL statements to retrieve/write table data
Author:     Luke Wait
Date:       29-Sept-2022
Version:    2.2
NOTES:      Created jar file for DB_Read and DB_Write classes and imported
            Reintroduced arrays to store sql returned data to minimise the number of sql queries
            and database connections
            Now it only accesses the database when constructor runs and when new data is added

DEVELOPMENT PLAN
01. Read through documentation provided for project                                     ---DONE
02. Set up goldcoast_esports database (goldcoast_esports.sql)                           ---DONE
03. Create and test DB_Read class (in separate project)                                 ---DONE
04. Create and test DB_Write class (in separate project)                                ---DONE
    Create JAR file for both DB_Read and DB_Write classes and import here               ---DONE
05. Set up GUI components (5 tab panels)                                                ---DONE
06. In GUI class:                                                                       ---DONE
        - set up customizations for 3 jTables                                           
        - set up private data                                                           
        - set up GUI constructor method                                                 
        - include resizeTableColumn() methods in GUI class                              
        - include selectionSort() method for 2D Object[][] array                        
        - include other methods ('shell' - with no functional code)                     
        - include all event handler methods ('shell') for jComboBoxes                   
        - include all event handler methods ('shell') for jButtons                      

FUNCTIONALITIES
01. READ FROM DATABASE AND DISPLAY
    Read all rows from goldcoast_esports.competition table and display in: 
        - ecrCompResults_jTable (gameName, team1, team1Points, team2, team2Points)      ---DONE
    Read all rows from goldcoast_esports.event table and display in:
        - ecrEvent_jComboBox (formatted: name (date location))                          ---DONE
        - ancrEvent_jComboBox (name)                                                    ---DONE
    Read all rows from goldcoast_esports.team table and display in:
        - ecrTeam_jComboBox (team)                                                      ---DONE
        - ancrTeam1_jComboBox (team)                                                    ---DONE
        - ancrTeam2_jComboBox (team)                                                    ---DONE
        - uetTeamName_jComboBox (team)                                                  ---DONE
    Read all rows from goldcoast_esports.game table and display in:
        - ancrGame_jComboBox (game)                                                     ---DONE

02. EVENT COMPETITION RESULTS PANEL
    Set up ecrEvent_jComboBox item state changed event handler:
        - when "All events" is selected, the ecrLeaderBoardAll_jTable displays
          each team with total points accumulated for all events
          nothing is displayed in the ecrLeaderBoardSelected_jTable
          and all competition results are shown in ecrCompResults_jTable                ---DONE
        - when a single event is selected, the ecrLeaderBoardSelected_jTable displays
          each team with total points accumulated for the specific event chosen,
          nothing is displayed in the ecrLeaderBoardAll_jTable
          and only the competition results for the selected event are shown in
          ecrCompResults_jTable                                                         ---DONE
        - when an event with no data is selected all three jTables show nothing         ---DONE
        - ecrRecordCount_jTextField shows number of records in ecrCompResults_jTable    ---DONE
    Set up ecrTeam_jComboBox item state changed event handler:
        - filters display in ecrCompResults_jTable by team selected, working in
          conjunction with selected event                                               ---DONE
        - does not affect ecrLeaderBoardAll_jTable or ecrLeaderBoardSelected_jTable     ---DONE
    ecrExportCompResultsCSV_jButton clicked:
        - if there is row data in ecrCompResults_jTable the data is written to external 
          competitionTeamScores.csv file and a pop up displays confirmation             ---DONE
        - if there is no data in ecrCompResults_jTable nothing happens                  ---DONE
    ecrExportLeaderBoardsCSV_jButton clicked:
        - if there is row data in ecrLeaderBoardSelected or ecrLeaderBoardAll the data
          is written to external file eventTeamScores and a pop up displays confirmation---DONE
        - if there is no data in either jTable nothing happens                          ---DONE

03. ADD NEW COMPETITION RESULT PANEL
    ancr_jButton clicked:
        - ancrTeam1_jComboBox and ancrTeam2_jComboBox cannot be the same team           ---DONE
        - ancrTeam1Points_jTextField and ancrTeam2Points_jTextField can only be numeric 
          values: 0, 1 or 2 (cannot be empty)                                           ---DONE
        - The sum of the two point scores must equal 2                                  ---DONE
        - The same team1/team2 combination playing the same game in the same event
          represents an existing record in the database and will produce error          ---DONE  
        - if any validation checks fail display an error pop up with all failed checks  ---DONE
        - if all validation checks pass display a pop up with a yes/no option displaying
          teams to be saved to database                                                 ---DONE
        - when no is clicked the action is cancelled, when yes is clicked the record
          is written to the database (competition table)                                ---DONE
        - update jTables in event competition results panel with new record             ---DONE

04. ADD NEW TEAM PANEL
    ant_jButton clicked:
        - check that antNewTeamName_jTextField data does not already exist              ---DONE
        - check that all jTextFields are not empty                                      ---DONE
        - if any validation checks fail display an error pop up with all failed checks  ---DONE
        - if all validation checks pass display a pop up with a yes/no option displaying
          team name to be saved to database                                             ---DONE
        - when no is clicked the action is cancelled, when yes is clicked the record
          is written to the database (team table)                                       ---DONE
        - update all four team jComboBoxes with new team name                           ---DONE

05. UPDATE EXISTING TEAM PANEL
    Set up uetTeamName_jComboBox item state changed event handler:
        - display team.contact, team.phone and team.email in relevant jTextFields
          according to team selected                                                    ---DONE
    uet_jButton clicked:
        - check that all jTextFields are not empty                                      ---DONE
        - check that jTextFields contain new information/record to update isn't the same---DONE
        - if any validation checks fail display an error pop up with all failed checks  ---DONE
        - if all validation checks pass display a pop up with a yes/no option displaying
          team to be saved to database                                                  ---DONE
        - when no is clicked the action is cancelled, when yes is clicked the record
          is updated on the database (team table)                                       ---DONE

06. ADD NEW EVENT PANEL
    Set up default jTextField values:
        - the current date should be displayed in the format yyyy-MM-dd                 ---DONE
        - a default location string set for location (TAFE Coomera)                     ---DONE
    ane_jButton clicked:
        - check that aneNewEventName_jTextField is unique and not already in database   ---DONE
        - check that all jTextFields are not empty                                      ---DONE
        - check that aneDate_jTextField is formatted to yyyy-MM-dd (10 characters total)
          year, month, day must be numeric, dashes must be used to divide               ---DONE
        - check that aneDate_jTextField month values are between 1 and 12               ---DONE
        - check that aneDate_jTextField date numeric values are appropriate to the
          month entered (eg: 1-29 for Feb(02), 1-31 for Mar(03), etc)                   ---DONE
        - if any validation checks fail display an error pop up with all failed checks  ---DONE
        - if all validation checks pass display a pop up with a yes/no option displaying
          event to be saved to database                                                 ---DONE
        - when no is clicked the action is cancelled, when yes is clicked the record
          is written to the database (event table)                                      ---DONE
        - update the two event jComboBoxes with new event data with proper formatting   ---DONE

TESTING
01. Refer to ICTPRG549_AT2_Test_Examples.docx
*******************************************************************************/

package gcesportsapp2;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.JOptionPane;


public class GCEsportsApp2 extends javax.swing.JFrame 
{
    // private data fields
    // required for customising the three jTables
    private DefaultTableModel ecrCompResultsTableModel;
    private DefaultTableModel ecrLeaderBoardSelectedTableModel;
    private DefaultTableModel ecrLeaderBoardAllTableModel;
    // stores current date and desired format
    private LocalDate now;
    private DateTimeFormatter dtf;
    // database connection and sql processing objects
    private DbRead dbRead;
    private DbWrite dbWrite;
    // stores sql String to query database
    private String sql;
    // stores ecrEvent_jComboBox and ecrTeam_jComboBox selections
    private String chosenEvent;
    private String chosenTeam;
    // acts as a gate for jComboBox events
    private boolean comboBoxStatus;
    // stores returned results from sql queries
    private String[] teamsStrArray;
    private String[] eventsStrArray;
    private Object[][] compResultsArray;
    private Object[][] leaderBoardArray;
    
    
    /***************************************************************************
    Method:     GC_EGames_V2_GUI() constructor method
    Purpose:    creates GC_EGames_V2_GUI jFrame with GUI controls
                and sets up all data for jTable and jComboBoxes
    Inputs:     void
    Outputs:    void
    ***************************************************************************/
    public GCEsportsApp2() 
    {        
        //************ CUSTOMISE TABLE MODELS ************
        // initilise and set up customised table model for ecrCompResults_jTable
        String[] columnNames_Results = new String[] {"Game", "Team 1", "Pt", "Team 2", "Pt"};
        ecrCompResultsTableModel = new DefaultTableModel();
        ecrCompResultsTableModel.setColumnIdentifiers(columnNames_Results);
        
        // initilise and set up customised table model for ecrLeaderBoardSelected_jTable
        String[] columnNames_Selected = new String[] {"Team", "Total points - chosen event"};
        ecrLeaderBoardSelectedTableModel = new DefaultTableModel();
        ecrLeaderBoardSelectedTableModel.setColumnIdentifiers(columnNames_Selected);
        
        // initilise and set up customised table model for ecrLeaderBoardAll_jTable
        String[] columnNames_All = new String[] {"Team", "Total points - all events"};
        ecrLeaderBoardAllTableModel = new DefaultTableModel();
        ecrLeaderBoardAllTableModel.setColumnIdentifiers(columnNames_All);        
        
        
        //************ INITIALISE LOCAL DATE AND FORMAT ************
        // aneDate_jTextField and aneLocation_jTextField are set in initComponents()
        now = LocalDate.now();
        dtf = DateTimeFormatter.ofPattern("uuuu-MM-dd");
        
        
        //************ INITIALISE PRIVATE DATA FIELDS ************
        dbRead = null;
        dbWrite = null;       
        sql = "";
        chosenEvent = "All events";
        chosenTeam = "All teams";
        comboBoxStatus = false;
        teamsStrArray = null;
        eventsStrArray = null;
        compResultsArray = null;
        leaderBoardArray = null;
        
        
        //************ INITIALISE ALL SWING CONTROLS ************
        initComponents();
        
        
        //************ CUSTOMISE TABLE COLUMNS FOR JTABLES ************
        resizeTableColumnsCompResults();
        resizeTableColumnsLeaderBoardSelected();
        resizeTableColumnsLeaderBoardAll();
        
        
        //************ DISPLAY DATA IN JTABLES ************
        initialiseCompResults();        
        initialiseEventLeaderBoards();
        displayCompResults();
        displayEventLeaderBoards();
                
        
        //************ DISPLAY DATA IN JCOMBOBOXES ************
        displayEventJComboBoxes();
        displayTeamJComboBoxes();
        displayGameJComboBox();
        
        
        //************ DISPLAY DATA IN UPDATE EXISTING TEAM JPANEL ************
        displayTeamData();
        
        
        //************ SET COMBOBOXSTATUS TO TRUE ************
        comboBoxStatus = true;
        
    }// end constructor method

    
    //************ DISPLAY AND UPDATE JTABLE METHODS ************
    
    /***************************************************************************
    Method:     resizeTableColumnsCompResults()
    Purpose:    resize ecrCompResults_jTable columns 
    Inputs:     void
    Outputs:    void
    ***************************************************************************/
    private void resizeTableColumnsCompResults()
    {
        // Columns: Game, Team 1, Pt, Team 2, Pt
        // (total numeric values must = 1)
        double[] columnWidthPercentage = {0.3f, 0.3f, 0.05f, 0.3f, 0.05f};
        int tableWidth = ecrCompResults_jTable.getWidth();
        TableColumn column;
        TableColumnModel tableColumnModel = ecrCompResults_jTable.getColumnModel();
        int cantCols = tableColumnModel.getColumnCount();
        for (int i = 0; i < cantCols; i++)
        {
            column = tableColumnModel.getColumn(i);
            float pWidth = Math.round(columnWidthPercentage[i] * tableWidth);
            column.setPreferredWidth((int)pWidth);
        }
    }// end resizeTableColumnsCompResults()
      
    /***************************************************************************
    Method:     resizeTableColumnsLeaderBoardSelected()
    Purpose:    resize ecrLeaderBoardSelectedResults_jTable columns
    Inputs:     void
    Outputs:    void
    ***************************************************************************/
    private void resizeTableColumnsLeaderBoardSelected()
    {
        // Columns: Team, Total points - chosen event
        // (total numeric values must = 1)
        double[] columnWidthPercentage = {0.4f, 0.6f};
        int tableWidth = ecrLeaderBoardSelected_jTable.getWidth();
        TableColumn column;
        TableColumnModel tableColumnModel = ecrLeaderBoardSelected_jTable.getColumnModel();
        int cantCols = tableColumnModel.getColumnCount();
        for (int i = 0; i < cantCols; i++)
        {
            column = tableColumnModel.getColumn(i);
            float pWidth = Math.round(columnWidthPercentage[i] * tableWidth);
            column.setPreferredWidth((int)pWidth);
        }
    }// end resizeTableColumnsLeaderBoardSelected()
    
    /***************************************************************************
    Method:     resizeTableColumnsLeaderBoardAll()
    Purpose:    resize ecrLeaderBoardAllResults_jTable columns
    Inputs:     void
    Outputs:    void
    ***************************************************************************/
    private void resizeTableColumnsLeaderBoardAll()
    {
        // Columns: Team, Total points - all events
        // (total numeric values must = 1)
        double[] columnWidthPercentage = {0.4f, 0.6f};
        int tableWidth = ecrLeaderBoardAll_jTable.getWidth();
        TableColumn column;
        TableColumnModel tableColumnModel = ecrLeaderBoardAll_jTable.getColumnModel();
        int cantCols = tableColumnModel.getColumnCount();
        for (int i = 0; i < cantCols; i++)
        {
            column = tableColumnModel.getColumn(i);
            float pWidth = Math.round(columnWidthPercentage[i] * tableWidth);
            column.setPreferredWidth((int)pWidth);
        }
    }// resizeTableColumnsLeaderBoardAll()
           
    /***************************************************************************
    Method:     initialiseCompResults()
    Purpose:    uses sql statements to dynamically retrieve data from database
                sets returned data to compResultsArray
    Inputs:     void
    Outputs:    void
    ***************************************************************************/
    private void initialiseCompResults()
    {
        // sql statement used to retrieve data from database
        // added a join to sort by event date
        sql = "SELECT competition.gameName, competition.team1, competition.team1Points, "
                   + "competition.team2, competition.team2Points, competition.eventName, event.date "
            + "FROM goldcoast_esports.competition "
            + "INNER JOIN goldcoast_esports.event ON competition.eventName=event.name "
            + "ORDER BY date";
        // use dbRead object to read from database with sql statement and qryType
        dbRead = new DbRead(sql, "competition");
        
        // system out and error messaging
        System.out.println("initialiseCompResults():");
        // if reading is successful
        if (dbRead.getErrorMessage().isEmpty())
        {
            System.out.println("Successful read operation from database");
            System.out.println("competition records found: " + dbRead.getRecordCount());
            System.out.println("***************************************");
            // display contents of objDataSet
            if (dbRead.getObjDataSet() != null)
            {
                // assign returned data to compResultsArray
                compResultsArray = dbRead.getObjDataSet();
                
                for (int row = 0; row < dbRead.getObjDataSet().length; row++)
                {
                    for (int col = 0; col < 6; col++)
                    {
                        System.out.print(dbRead.getObjDataSet()[row][col] + " - ");
                    }
                    System.out.println();
                }
                System.out.println("***************************************");
            }
            else
            {
                System.out.println("Returned value is null!");
                System.out.println("***************************************");
            }
            System.out.println();
        }
        // if reading unsuccessful, also show errors in pop up so app user is aware
        else
        {
            System.out.println("***************************************");
            System.out.println("ERRORS:\n" + dbRead.getErrorMessage());
            System.out.println("***************************************\n");
            JOptionPane.showMessageDialog(null, "RETRIEVING COMPETITION RECORDS FROM DATABASE:\n" 
                    + dbRead.getErrorMessage(), "ERRORS DETECTED!", JOptionPane.ERROR_MESSAGE);
        }
    }// end initialiseCompResults()
    
    /***************************************************************************
    Method:     initialiseEventLeaderBoards()
    Purpose:    uses sql statements to dynamically retrieve data from database
                sets returned data to leaderBoardArray
    Inputs:     void
    Outputs:    void
    ***************************************************************************/
    private void initialiseEventLeaderBoards()
    {
        // sql statement used to retrieve data from database
        sql = "SELECT team1 AS name, team1points AS points, eventName "
            + "FROM goldcoast_esports.competition "
            + "UNION ALL "
            + "SELECT team2, team2points, eventName "
            + "FROM goldcoast_esports.competition";
        // use dbRead object to read from database with sql statement and qryType
        dbRead = new DbRead(sql, "leaderBoard");
        
        // system out and error messaging
        System.out.println("initialiseEventLeaderBoards():");
        // if reading is successful
        if (dbRead.getErrorMessage().isEmpty())
        {
            System.out.println("Successful read operation from database"); 
            System.out.println("leaderBoard records found: " + dbRead.getRecordCount());
            System.out.println("***************************************");
            // display contents of objDataSet
            if (dbRead.getObjDataSet() != null)
            {
                // assign returned data to leaderBoardArray
                leaderBoardArray = dbRead.getObjDataSet();
                
                for (int row = 0; row < dbRead.getObjDataSet().length; row++)
                {
                    for (int col = 0; col < 3; col++)
                    {
                        System.out.print(dbRead.getObjDataSet()[row][col] + " - ");
                    }
                    System.out.println();
                }
                System.out.println("***************************************");
            }
            else
            {
                System.out.println("Returned value is null!");
                System.out.println("***************************************");
            }
            System.out.println();
        }
        // if reading unsuccessful, also show errors in pop up so app user is aware
        else
        {
            System.out.println("***************************************");
            System.out.println("ERRORS:\n" + dbRead.getErrorMessage());
            System.out.println("***************************************\n");
            JOptionPane.showMessageDialog(null, "RETRIEVING LEADERBOARD RECORDS FROM DATABASE:\n" 
                    + dbRead.getErrorMessage(), "ERRORS DETECTED!", JOptionPane.ERROR_MESSAGE);
        }
    }// end initialiseEventLeaderBoards()
    
    /***************************************************************************
    Method:     displayCompResults()
    Purpose:    display competition results according to jComboBox selections
                (by event or by team or all)
                uses sql statements to dynamically retrieve data from database
    Inputs:     void
    Outputs:    void
    ***************************************************************************/
    private void displayCompResults()
    {
        
        // remove all existing rows if there are any
        if (ecrCompResultsTableModel.getRowCount() > 0)
        {
            for (int i = ecrCompResultsTableModel.getRowCount() - 1; i > -1; i--)
            {
                ecrCompResultsTableModel.removeRow(i);
            }
        }

        // track number of records displayed
        int recordCount = 0;
        
        if (compResultsArray != null)
        {
            // display all records for chosen event
            if (!chosenEvent.equals("All events") && chosenTeam.equals("All teams"))
            {           
                for (int row = 0; row < compResultsArray.length; row++)
                {               
                    if (compResultsArray[row][5].equals(chosenEvent))
                    {
                        ecrCompResultsTableModel.addRow(compResultsArray[row]);
                        recordCount++;
                    }
                }
            }    
            // display all records for chosen event and chosen team
            else if (!chosenEvent.equals("All events") && !chosenTeam.equals("All teams"))
            {
                for (int row = 0; row < compResultsArray.length; row++)
                {               
                    if (compResultsArray[row][5].equals(chosenEvent) &&
                        (compResultsArray[row][1].equals(chosenTeam) ||
                         compResultsArray[row][3].equals(chosenTeam)))
                    {                       
                        ecrCompResultsTableModel.addRow(compResultsArray[row]);
                        recordCount++;
                    }
                }
            }
            // display all records for chosen team
            else if (chosenEvent.equals("All events") && !chosenTeam.equals("All teams"))
            {
                for (int row = 0; row < compResultsArray.length; row++)
                {               
                    if (compResultsArray[row][1].equals(chosenTeam) ||
                        compResultsArray[row][3].equals(chosenTeam))
                    {                       
                        ecrCompResultsTableModel.addRow(compResultsArray[row]);
                        recordCount++;
                    }
                }
            }
            // display all records
            else
            {
                for (int row = 0; row < compResultsArray.length; row++)
                {
                    ecrCompResultsTableModel.addRow(compResultsArray[row]);
                    recordCount++;
                }
            }

            // update jTable
            ecrCompResultsTableModel.fireTableDataChanged();
        }
        
        // update record count in ecrRecordCount_jTextField
        ecrRecordCount_jTextField.setText(recordCount + " records found");      
    }// end displayCompResults()

    /***************************************************************************
    Method:     displayEventLeaderBoards()
    Purpose:    display event leader boards according to event jComboBox selection
                uses sql statements to dynamically retrieve data from database
    Inputs:     void
    Outputs:    void
    ***************************************************************************/
    private void displayEventLeaderBoards()
    {       
        // remove all existing rows from both leader board jTables
        if (ecrLeaderBoardSelectedTableModel.getRowCount() > 0)
        {
            for (int i = ecrLeaderBoardSelectedTableModel.getRowCount() - 1; i > -1; i--)
            {
                ecrLeaderBoardSelectedTableModel.removeRow(i);
            }
        }
        if (ecrLeaderBoardAllTableModel.getRowCount() > 0)
        {
            for (int i = ecrLeaderBoardAllTableModel.getRowCount() - 1; i > -1; i--)
            {
                ecrLeaderBoardAllTableModel.removeRow(i);
            }
        }
        
        if (leaderBoardArray != null)
        {
            // ArrayList of Objects (team names)
            ArrayList<Object> uniqueTeamNamesList = new ArrayList<>();
            // get unique names for chosen event
            if (!chosenEvent.equals("All events"))
            {
                for (int row = 0; row < leaderBoardArray.length; row++)
                {
                    // if event name in leaderBoardArray matches chosenEvent
                    if (leaderBoardArray[row][2].equals(chosenEvent))
                    {
                        Object teamNameObj = leaderBoardArray[row][0];
                        if (!uniqueTeamNamesList.contains(teamNameObj))
                        {
                            uniqueTeamNamesList.add(teamNameObj);
                        }
                    }
                }
            }
            // get unique names for all events
            else
            {
                for (int row = 0; row < leaderBoardArray.length; row++)
                {
                    Object teamNameObj = leaderBoardArray[row][0];
                    if (!uniqueTeamNamesList.contains(teamNameObj))
                    {
                        uniqueTeamNamesList.add(teamNameObj);
                    }
                }
            }

            // ArrayList of integers (for each unique team - initialise to 0)
            ArrayList<Integer> totalPointsList = new ArrayList<>();
            for (int i = 0; i < uniqueTeamNamesList.size(); i++)
            {
                totalPointsList.add(0);
            }

            // next, loop through leaderBoardArray to add points for each unique team
            for ( int i = 0; i < uniqueTeamNamesList.size(); i++)
            {
                // accumulate points for chosen event
                if (!chosenEvent.equals("All events"))
                {
                    // loop through the leaderBoardArray to retrieve string[] values
                    for (int row = 0; row < leaderBoardArray.length; row++)
                    {
                        // if event name in leaderBoardArray matches chosenEvent
                        if (leaderBoardArray[row][2].equals(chosenEvent))
                        {
                            String teamName = leaderBoardArray[row][0].toString();
                            Integer points = Integer.valueOf(leaderBoardArray[row][1].toString());
                            if (uniqueTeamNamesList.get(i).equals(teamName))
                            {
                                // add points
                                totalPointsList.set(i, totalPointsList.get(i) + points);
                            }   
                        }
                    }
                }
                // accumulate points for all events
                else
                {
                    // loop through the leaderBoardArray to retrieve string[] values
                    for (int row = 0; row < leaderBoardArray.length; row++)
                    {
                        String teamName = leaderBoardArray[row][0].toString();
                        Integer points = Integer.valueOf(leaderBoardArray[row][1].toString());
                        if (uniqueTeamNamesList.get(i).equals(teamName))
                        {
                            // add points
                            totalPointsList.set(i, totalPointsList.get(i) + points);
                        }        
                    }
                }
            }

            // create 2D object for jTables from unique names and total points
            Object[][] leaderBoardDisplay = new Object[uniqueTeamNamesList.size()][];
            for (int i = 0; i < leaderBoardDisplay.length; i++)
            {
                Object[] rowData = new Object[2];
                rowData[0] = uniqueTeamNamesList.get(i);
                rowData[1] = totalPointsList.get(i);
                leaderBoardDisplay[i] = rowData;
            }           

            // sort 2D array in descending order
            selectionSort(leaderBoardDisplay);

            // display leader board for chosen event
            if (!chosenEvent.equals("All events"))
            {
                for (int row = 0; row < leaderBoardDisplay.length; row++)
                {
                    ecrLeaderBoardSelectedTableModel.addRow(leaderBoardDisplay[row]);
                }
                // update
                ecrLeaderBoardSelectedTableModel.fireTableDataChanged();
            }
            // display leader board for all events
            else
            {
                for (int row = 0; row < leaderBoardDisplay.length; row++)
                {
                    ecrLeaderBoardAllTableModel.addRow(leaderBoardDisplay[row]);
                }
                // update
                ecrLeaderBoardAllTableModel.fireTableDataChanged();
            }
        }
    }// end displayEventLeaderBoards()
    
    /***************************************************************************
    Method:     selectionSort()
    Purpose:    sorts 2D array by using temporary array
    Inputs:     Object[][]
    Outputs:    Object[][]
    ***************************************************************************/
    private Object[][] selectionSort(Object[][] array)
    {
        for (int i = 0; i < array.length - 1; i++)
        {
            for (int j = i + 1; j < array.length; j++)
            {
                int nbr1 = Integer.parseInt(array[j][1].toString());
                int nbr2 = Integer.parseInt(array[i][1].toString());
                if (nbr1 > nbr2)
                {
                    Object[] temp = array[j];
                    array[j] = array[i];
                    array[i] = temp;
                }
            }
        }     
        return array;
    }// end selectionSort() 
    
    
    //************ DISPLAY AND UPDATE JCOMBOBOX METHODS ************
    
    /***************************************************************************
    Method:     displayEventJComboBoxes()
    Purpose:    display event data in ecrEvent_jComboBox and ancrEvent_jComboBox
                uses sql statements to dynamically retrieve data from database
                initialises eventsStrArray
    Inputs:     void
    Outputs:    void
    ***************************************************************************/
    private void displayEventJComboBoxes()
    {
        // initialise ecrEvent_jComboBox & ancrEvent_jComboBox
        ecrEvent_jComboBox.removeAllItems();
        ecrEvent_jComboBox.addItem("All events");
        ancrEvent_jComboBox.removeAllItems();
            
        // sql statement used to retrieve data from database
        sql = "SELECT name, date, location "
            + "FROM goldcoast_esports.event "
            + "ORDER BY date;";
        // use dbRead object to read from database with sql statement and qryType
        dbRead = new DbRead(sql, "event");
        
        // system out and error messaging
        System.out.println("displayEventJComboBoxes():");
        // if reading is successful
        if (dbRead.getErrorMessage().isEmpty())
        {          
            System.out.println("Successful read operation from database"); 
            System.out.println("event records found: " + dbRead.getRecordCount());
            System.out.println("***************************************");
            // display contents of stringCSVData
            if (dbRead.getStringCSVData() != null)
            {
                for (int i = 0; i < dbRead.getStringCSVData().length; i++)
                {
                    System.out.println(dbRead.getStringCSVData()[i]);
                }
                System.out.println("***************************************");
            } 
            else
            {
                System.out.println("Returned value is null!");
                System.out.println("***************************************");
            }
            System.out.println();
        }
        // if reading unsuccessful, also show errors in pop up so app user is aware
        else
        {
            System.out.println("***************************************");
            System.out.println("ERRORS:\n" + dbRead.getErrorMessage());
            System.out.println("***************************************\n");
            JOptionPane.showMessageDialog(null, "RETRIEVING EVENT RECORDS FROM DATABASE:\n" 
                    + dbRead.getErrorMessage(), "ERRORS DETECTED!", JOptionPane.ERROR_MESSAGE);
        }
        
        // if sql qry reads successfully and returns records
        if (dbRead.getRecordCount() > 0)
        {
            // if stringCSVData has data / qry type has populated variable
            if (dbRead.getStringCSVData() != null)
            {
                // assign returned team data to eventsStrArray
                eventsStrArray = dbRead.getStringCSVData();
                
                // loop for all records of events in database
                for (int i = 0; i < dbRead.getStringCSVData().length; i++)
                {   
                    // DOTA 2 Battle Royale,21-Jan-2022,TAFE Coomera
                    // split stringCSVData by delimiter and assign to eventRow
                    String[] eventRow = dbRead.getStringCSVData()[i].split(",");
                    
                    // construct String for display using array elements
                    String formattedEventData = eventRow[0] + " (" + eventRow[1] + " " + eventRow[2] + ")";

                    // add formattedEventData to jComboBoxes
                    ecrEvent_jComboBox.addItem(formattedEventData);
                    ancrEvent_jComboBox.addItem(eventRow[0]);
                }
            } 
        }
    }// end displayEventJComboBoxes() 
    
    /***************************************************************************
    Method:     displayTeamJComboBoxes()
    Purpose:    display team data in ecrTeam_jComboBox, ancrTeam1_jComboBox, 
                ancrTeam2_jComboBox and uetTeamName_jComboBox
                uses sql statements to dynamically retrieve data from database
                initialises teamsStrArray
    Inputs:     void
    Outputs:    void
    ***************************************************************************/
    private void displayTeamJComboBoxes()
    {
        // initialise team jComboBoxes
        ecrTeam_jComboBox.removeAllItems();
        ecrTeam_jComboBox.addItem("All teams");
        ancrTeam1_jComboBox.removeAllItems();
        ancrTeam2_jComboBox.removeAllItems();
        uetTeamName_jComboBox.removeAllItems();
        
        // sql statement used to retrieve data from database
        sql = "SELECT name, contact, phone, email "
            + "FROM goldcoast_esports.team "
            + "ORDER BY name;";
        // use dbRead object to read from database with sql statement and qryType
        dbRead = new DbRead(sql, "team");
        
        // system out and error messaging
        System.out.println("displayTeamJComboBoxes():");
        // if reading is successful
        if (dbRead.getErrorMessage().isEmpty())
        {       
            System.out.println("Successful read operation from database"); 
            System.out.println("team records found: " + dbRead.getRecordCount());
            System.out.println("***************************************");
            // display contents of stringCSVData
            if (dbRead.getStringCSVData() != null)
            {
                for (int i = 0; i < dbRead.getStringCSVData().length; i++)
                {
                    System.out.println(dbRead.getStringCSVData()[i]);
                }
                System.out.println("***************************************");
            }
            else
            {
                System.out.println("Returned value is null!");
                System.out.println("***************************************");
            }
            System.out.println();
        }
        // if reading unsuccessful, also show errors in pop up so app user is aware
        else
        {
            System.out.println("***************************************");
            System.out.println("ERRORS:\n" + dbRead.getErrorMessage());
            System.out.println("***************************************\n");
            JOptionPane.showMessageDialog(null, "RETRIEVING TEAM RECORDS FROM DATABASE:\n" 
                    + dbRead.getErrorMessage(), "ERRORS DETECTED!", JOptionPane.ERROR_MESSAGE);
        }
        
        // if sql qry reads successfully and returns records
        if (dbRead.getRecordCount() > 0)
        {
            // if stringCSVData has data / qry type has populated variable
            if (dbRead.getStringCSVData() != null)
            {
                // assign returned team data to teamsStrArray
                teamsStrArray = dbRead.getStringCSVData();
                
                // loop for all records of teams in database
                for (int i = 0; i < dbRead.getStringCSVData().length; i++)
                {              
                    // BioHazards,Zheng Lee,0418999888,zhenglee99@geemail.com
                    // split stringCSVData by delimiter and assign to teamRow
                    String[] teamRow = dbRead.getStringCSVData()[i].split(",");

                    // add just the team names to jComboBoxes (index 0 of teamString)
                    ecrTeam_jComboBox.addItem(teamRow[0]);
                    ancrTeam1_jComboBox.addItem(teamRow[0]);     
                    ancrTeam2_jComboBox.addItem(teamRow[0]);
                    uetTeamName_jComboBox.addItem(teamRow[0]);
                }
            }
        }
    }// end displayTeamJComboBoxes()
    
    /***************************************************************************
    Method:     displayGameJComboBox()
    Purpose:    display game data in ancrGame_jComboBox 
                uses sql statements to dynamically retrieve data from database
    Inputs:     void
    Outputs:    void
    ***************************************************************************/
    private void displayGameJComboBox()
    {
        // initialise ancrGame_jComboBox 
        ancrGame_jComboBox.removeAllItems();
        
        // sql statement used to retrieve data from database
        sql = "SELECT name "
            + "FROM goldcoast_esports.game "
            + "ORDER BY name;";
        // use dbRead object to read from database with sql statement and qryType
        dbRead = new DbRead(sql, "game");
        
        // system out and error messaging
        System.out.println("displayGameJComboBox():");
        // if reading is successful
        if (dbRead.getErrorMessage().isEmpty())
        {           
            System.out.println("Successful read operation from database"); 
            System.out.println("game records found: " + dbRead.getRecordCount());
            System.out.println("***************************************");
            // display contents of stringCSVData
            if (dbRead.getStringCSVData() != null)
            {
                for (int i = 0; i < dbRead.getStringCSVData().length; i++)
                {
                    System.out.println(dbRead.getStringCSVData()[i]);
                }
                System.out.println("***************************************");
            }  
            else
            {
                System.out.println("Returned value is null!");
                System.out.println("***************************************");
            }
            System.out.println();
        }
        // if reading unsuccessful, show errors in pop up so app user is aware
        else
        {
            System.out.println("***************************************");
            System.out.println("ERRORS:\n" + dbRead.getErrorMessage());
            System.out.println("***************************************\n");
            JOptionPane.showMessageDialog(null, "RETRIEVING GAME RECORDS FROM DATABASE:\n" 
                    + dbRead.getErrorMessage(), "ERRORS DETECTED!", JOptionPane.ERROR_MESSAGE);
        }
        
        // if sql qry reads successfully and returns records
        if (dbRead.getRecordCount() > 0)
        {
            // if stringCSVData has data / qry type has populated variable
            if (dbRead.getStringCSVData() != null)
            {
                // loop for all records of games in database
                for (int i = 0; i < dbRead.getStringCSVData().length; i++)
                {              
                    // add games to ancrGame_jComboBox
                    ancrGame_jComboBox.addItem(dbRead.getStringCSVData()[i]);     
                }
            }
        }
    }// end displayGameJComboBox()
    
    
    //************ JCOMBOBOX EVENT METHODS ************
    
    /***************************************************************************
    Method:     displayTeamData()
    Purpose:    populates jTextFields of updateExisitingTeam_jPanel according to
                uetTeamName_jComboBox selection, using the teamsStrArray
    Inputs:     void
    Outputs:    void
    ***************************************************************************/
    private void displayTeamData()
    {
        // check that index isn't out of bounds
        if (uetTeamName_jComboBox.getSelectedIndex() >= 0)
        {
            // loop teamsStrArray data to get matching team
            for (int i = 0; i < teamsStrArray.length; i++)
            {
                // split teamsStrArray by delimiter and assign to teamString array
                String[] teamString = teamsStrArray[i].split(",");
                
                // teamsStrArray team matches jComboBox selection
                if (teamString[0].equals(uetTeamName_jComboBox.getSelectedItem()))
                {
                    // set name, phone and email according to index
                    // BioHazards,Zheng Lee,0418999888,zhenglee99@geemail.com
                    uetContactName_jTextField.setText(teamString[1]);
                    uetPhoneNumber_jTextField.setText(teamString[2]);
                    uetEmailAddress_jTextField.setText(teamString[3]);
                }
            }  
        }
    }// end displayTeamData()
    
    /***************************************************************************
    Method:     selectEventJTables()
    Purpose:    filters ecrCompResults_jTable by according to ecrEvent_jComboBox selection
    Inputs:     void
    Outputs:    void
    ***************************************************************************/
    private void selectEventJTables()
    { 
        // change chosenEvent when index not out of bounds
        if (ecrEvent_jComboBox.getSelectedIndex() >= 0)
        {
            // split string to get just the event name without the date and location
            String[] eventString = ecrEvent_jComboBox.getSelectedItem().toString().split("\\ \\(");
            chosenEvent = eventString[0];

            // call filtered results
            displayCompResults();
            displayEventLeaderBoards();
        } 
    }// end selectEventJTables()
    
    /***************************************************************************
    Method:     selectTeamJTables()
    Purpose:    filters ecrCompResults_jTable by according to ecrTeam_jComboBox selection
    Inputs:     void
    Outputs:    void
    ***************************************************************************/
    private void selectTeamJTables()
    {
        // set chosenTeam when index is not out of bounds
        if (ecrTeam_jComboBox.getSelectedIndex() >= 0)
        {
            chosenTeam = ecrTeam_jComboBox.getSelectedItem().toString();   
            
            // display filtered results
            displayCompResults();
        }
    }// end selectTeamJTables()
    
    
    //************ JBUTTON EVENT METHODS ************
      
    /***************************************************************************
    Method:     writeCompetitionCSV()
    Purpose:    writes contents of ecrCompResults_jTable to external competitionTeamScores.csv
    Inputs:     void
    Outputs:    void
    ***************************************************************************/
    private void writeCompetitionCSV()
    {
        // String to add any failed checks to error pop up
        String errorMsg = "";
        
        // only write to csv if there is data in ecrCompResults_jTable
        if (ecrCompResultsTableModel.getRowCount() > 0)
        { 
            try
            {
                // create writing objects designate the external file to write to
                FileOutputStream fos = new FileOutputStream("competitionsTeamScores.csv", false);
                OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");               
                BufferedWriter bw = new BufferedWriter(osw);

                // String to store formatted row data from ecrCompResultsTableModel
                String line;
                // loop through the 2D array by using row index
                for (int i = 0; i < ecrCompResultsTableModel.getRowCount(); i++)
                {
                    // add column values to String with formatting
                    line = ecrCompResultsTableModel.getValueAt(i, 0).toString() + ",";
                    line += ecrCompResultsTableModel.getValueAt(i, 1).toString() + ",";
                    line += ecrCompResultsTableModel.getValueAt(i, 2).toString() + ",";
                    line += ecrCompResultsTableModel.getValueAt(i, 3).toString() + ",";
                    line += ecrCompResultsTableModel.getValueAt(i, 4).toString();

                    // write formatted String to csv, then start a new line                   
                    bw.write(line);
                    bw.newLine();
                }
                // close the bufferedWriter object
                bw.close(); 
                
                // display pop up displaying that the data was written to the external competitionTeamScore.csv
                JOptionPane.showMessageDialog(null, "CSV data successfully written to file: "
                        + "competitionTeamScores.csv", "CSV Data Exported!",
                        JOptionPane.INFORMATION_MESSAGE);
            }
            // exceptions for try block
            catch (IOException ioe)
            {
                errorMsg += ioe.getMessage();                          
            }
        }
        // if there is no data displayed in jTable
        else
        {
            errorMsg += "No data displayed in Competition Results!";
        }
        
        // show any errors in pop up
        if (!errorMsg.isEmpty())
        {
            JOptionPane.showMessageDialog(null, "PROBLEM WRITING TO: competitionTeamScores.csv\n"
                                        + errorMsg, "ERRORS DETECTED!", JOptionPane.ERROR_MESSAGE);
        }
    }// end writeCompetitionCSV()

    /***************************************************************************
    Method:     writeEventCSV()
    Purpose:    writes contents of ecrLeaderBoardSelected_jTable or ecrLeaderBoardAll_jTable
                to eventTeamScores.csv depending on which table has contents displayed
    Inputs:     void
    Outputs:    void
    ***************************************************************************/
    private void writeEventCSV()
    {
        // String to add any failed checks to error pop up
        String errorMsg = "";
        
        // write from ecrLeaderBoardSelected_jTable if there is data displayed
        if (ecrLeaderBoardSelectedTableModel.getRowCount() > 0)
        {
            try
            {
                // create objects and designate the external file to write to
                FileOutputStream fos = new FileOutputStream("eventTeamScores.csv", false);
                OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");                
                BufferedWriter bw = new BufferedWriter(osw);
                
                // String to store formatted row data from jTables
                String line;
                
                // loop through the 2D array by using row index
                for (int i = 0; i < ecrLeaderBoardSelectedTableModel.getRowCount(); i++)
                {
                    // add column values to String with formatting
                    line = ecrLeaderBoardSelectedTableModel.getValueAt(i, 0).toString() + ",";
                    line += ecrLeaderBoardSelectedTableModel.getValueAt(i, 1).toString();

                    // write formatted String to csv, then start a new line                   
                    bw.write(line);
                    bw.newLine();
                }
                
                // close the bufferedWriter object
                bw.close();
                
                // display pop up displaying that the data was written to the external eventTeamScore.csv
                JOptionPane.showMessageDialog(null, "CSV data successfully written to file: "
                        + "eventTeamScores.csv", "CSV Data Exported!",
                        JOptionPane.INFORMATION_MESSAGE);
            }
            // exceptions for try block
            catch (IOException ioe)
            {
                errorMsg += ioe.getMessage();                          
            }
        }
        // write from ecrLeaderBoardAll_jTable if there is data displayed
        else if (ecrLeaderBoardAllTableModel.getRowCount() > 0)
        {
            try
            {
                // create objects and designate the external file to write to
                FileOutputStream fos = new FileOutputStream("allTeamScores.csv", false);
                OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");                
                BufferedWriter bw = new BufferedWriter(osw);
                
                // String to store formatted row data from jTables
                String line;
                
                // loop through the 2D array by using row index
                for (int i = 0; i < ecrLeaderBoardAllTableModel.getRowCount(); i++)
                {
                    // add column values to String with formatting
                    line = ecrLeaderBoardAllTableModel.getValueAt(i, 0).toString() + ",";
                    line += ecrLeaderBoardAllTableModel.getValueAt(i, 1).toString();

                    // write formatted String to csv, then start a new line                   
                    bw.write(line);
                    bw.newLine();
                }
                
                // close the bufferedWriter object
                bw.close();
                
                // display pop up displaying that the data was written to the external eventTeamScore.csv
                JOptionPane.showMessageDialog(null, "CSV data successfully written to file: "
                        + "allTeamScores.csv", "CSV Data Exported!",
                        JOptionPane.INFORMATION_MESSAGE);
            }
            // exceptions for try block
            catch (IOException ioe)
            {
                errorMsg += ioe.getMessage();                          
            }
        }
        // if there is no data displayed in either jTable
        else
        {
            errorMsg += "No data displayed in Event Leader Boards!";
        }
        
        // show any errors in pop up
        if (!errorMsg.isEmpty())
        {
            JOptionPane.showMessageDialog(null, "PROBLEM WRITING TO: eventTeamScores.csv or allTeamScores.csv\n"
                                        + errorMsg, "ERRORS DETECTED!", JOptionPane.ERROR_MESSAGE);
        }
    }//end writeEventCSV()
    
    /***************************************************************************
    Method:     addNewCompResults()
    Purpose:    add row to competition table of goldcoast_esports database with validated field data
                uses sql statements to dynamically write data to database with dbWrite
    Inputs:     void
    Outputs:    void
    ***************************************************************************/
    private void addNewCompResults()
    {
        // call method to validate all field data
        if (validateNewCompResults())
        {
            // display save update for this team pop up and provide yes/no option to save
            int yesNo = JOptionPane.showConfirmDialog(null, "You are about to save a new competition result for: "
                                + ancrTeam1_jComboBox.getSelectedItem() + " and " + ancrTeam2_jComboBox.getSelectedItem()
                                + "\nDo you wish to continue?", "NEW COMPETITION RESULT", JOptionPane.YES_NO_OPTION);
            
            // if yes is selected
            if (yesNo == JOptionPane.YES_OPTION)
            {
                // variable for competitionID to be written
                int newCompID = compResultsArray.length + 1;
                
                // write field data to database with sql
                sql = "INSERT INTO goldcoast_esports.competition "
                    + "VALUES ('" + newCompID + "', '" 
                                  + ancrEvent_jComboBox.getSelectedItem() + "', '"
                                  + ancrGame_jComboBox.getSelectedItem() + "', '"
                                  + ancrTeam1_jComboBox.getSelectedItem() + "', '"
                                  + ancrTeam2_jComboBox.getSelectedItem() + "', '"
                                  + ancrTeam1Points_jTextField.getText() + "', '"
                                  + ancrTeam2Points_jTextField.getText() + "')";
                // use dbWrite object to write to database with sql statement
                dbWrite = new DbWrite(sql);

                // system out and error messaging
                System.out.println("addNewCompResults():");
                System.out.println("***************************************");
                // if writing is successful
                if (dbWrite.getErrorMessage().isEmpty())
                {
                    System.out.println("Successful write operation to goldcoast_esports.competition");
                    System.out.println("***************************************\n");

                    // update compResultsArray, leaderBoardArray and jTables in eventCompResults_jPanel
                    initialiseCompResults();
                    initialiseEventLeaderBoards();
                    displayCompResults();
                    displayEventLeaderBoards(); 
                }
                // if writing unsuccessful, show errors in pop up so app user is aware
                else
                {
                    System.out.println("ERRORS:\n" + dbWrite.getErrorMessage());
                    System.out.println("***************************************\n");
                    JOptionPane.showMessageDialog(null, "WRITING COMPETITION RECORDS TO DATABASE:\n" 
                            + dbWrite.getErrorMessage(), "ERRORS DETECTED!", JOptionPane.ERROR_MESSAGE);
                }
            }    
        } 
    }// end addNewCompResults()
    
    /***************************************************************************
    Method:     addNewTeam()
    Purpose:    add row to team table of goldcoast_esports database with validated field data
                uses sql statements to dynamically write data to database with dbWrite
    Inputs:     void
    Outputs:    void
    ***************************************************************************/
    private void addNewTeam()
    {
        // call method to validate all field data
        if (validateNewTeam())
        {
            // display save update for this team pop up and provide yes/no option to save
            int yesNo = JOptionPane.showConfirmDialog(null, "You are about to save a new team for: "
                                + antNewTeamName_jTextField.getText() + "\nDo you wish to continue?", 
                                  "ADD NEW TEAM", JOptionPane.YES_NO_OPTION);
            
            // if yes is selected
            if (yesNo == JOptionPane.YES_OPTION)
            {
                // write field data to database with sql
                sql = "INSERT INTO goldcoast_esports.team "
                    + "VALUES ('" + antNewTeamName_jTextField.getText() + "', '"
                                  + antContactName_jTextField.getText() + "', '"
                                  + antPhoneNumber_jTextField.getText() + "', '"
                                  + antEmailAddress_jTextField.getText() + "')";
                // use dbWrite object to write to database with sql statement
                dbWrite = new DbWrite(sql);
                
                // system out and error messaging
                System.out.println("addNewTeam():");
                System.out.println("***************************************");
                // if writing is successful
                if (dbWrite.getErrorMessage().isEmpty())
                {
                    System.out.println("Successful write operation to goldcoast_esports.team");
                    System.out.println("***************************************\n");                   
                    
                    // gate jComboBox events while updating contents of team jComboBoxees
                    comboBoxStatus = false;
                    // temp store of team jComboBox selections
                    String ecrTeamTemp = ecrTeam_jComboBox.getSelectedItem().toString();
                    String ancrTeam1Temp = ancrTeam1_jComboBox.getSelectedItem().toString();
                    String ancrTeam2Temp = ancrTeam2_jComboBox.getSelectedItem().toString();
                    String uetTeamNameTemp = uetTeamName_jComboBox.getSelectedItem().toString();
                    // update the four team jComboBoxes with new sql data
                    // also updates teamsStrArray
                    displayTeamJComboBoxes();
                    // set team jComboBox selections to what they were before update
                    ecrTeam_jComboBox.setSelectedItem(ecrTeamTemp);
                    ancrTeam1_jComboBox.setSelectedItem(ancrTeam1Temp);
                    ancrTeam2_jComboBox.setSelectedItem(ancrTeam2Temp);
                    uetTeamName_jComboBox.setSelectedItem(uetTeamNameTemp);
                    // re-enable jComboBox events
                    comboBoxStatus = true; 
                }
                // if writing unsuccessful, show errors in pop up so app user is aware
                else
                {
                    System.out.println("ERRORS:\n" + dbWrite.getErrorMessage());
                    System.out.println("***************************************\n");
                    JOptionPane.showMessageDialog(null, "WRITING TEAM RECORDS TO DATABASE:\n" 
                            + dbWrite.getErrorMessage(), "ERRORS DETECTED!", JOptionPane.ERROR_MESSAGE);
                }
            }    
        } 
    }// end addNewTeam()
    
    /***************************************************************************
    Method:     updateExistingTeam()
    Purpose:    update team table of goldcoast_esports database with validated field data
                uses sql statements to dynamically update data on database with dbWrite
    Inputs:     void
    Outputs:    void
    ***************************************************************************/
    private void updateExistingTeam()
    {
        // call method to validate all field data
        if (validateUpdateExistingTeam())
        {
            // display save update for this team pop up and provide yes/no option to save
            int yesNo = JOptionPane.showConfirmDialog(null, "You are about to update the team details for: "
                                + uetTeamName_jComboBox.getSelectedItem() + "\nDo you wish to continue?", 
                                  "UPDATE EXISTING TEAM", JOptionPane.YES_NO_OPTION);
            
            // if yes is selected
            if (yesNo == JOptionPane.YES_OPTION)
            {               
                // update database with field data using sql
                sql = "UPDATE goldcoast_esports.team "
                    + "SET contact = '" + uetContactName_jTextField.getText() + "', "
                        + "phone = '" + uetPhoneNumber_jTextField.getText() + "', "
                        + "email = '" + uetEmailAddress_jTextField.getText() + "' "
                    + "WHERE name = '" + uetTeamName_jComboBox.getSelectedItem() + "'";
                // use dbWrite object to write to database with sql statement
                dbWrite = new DbWrite(sql);
                
                // system out and error messaging
                System.out.println("updateExistingTeam():");
                System.out.println("***************************************");
                // if writing is successful
                if (dbWrite.getErrorMessage().isEmpty())
                {
                    System.out.println("Succesful write operation to goldcoast_esports.team");
                    System.out.println("***************************************\n");
                    
                    // update teamsStrArray with new data
                    for (int i = 0; i < teamsStrArray.length; i++)
                    {
                        // split teamsStrArray by delimiter and assign to teamString array
                        String[] teamString = teamsStrArray[i].split(",");
                        // BioHazards,Zheng Lee,0418999888,zhenglee99@geemail.com
                        // if team name matches
                        if (teamString[0].equals(uetTeamName_jComboBox.getSelectedItem()))
                        {
                            // format and assign sting to replace current indexed string
                            teamsStrArray[i] = uetTeamName_jComboBox.getSelectedItem() + ","
                                             + uetContactName_jTextField.getText() + ","
                                             + uetPhoneNumber_jTextField.getText() + ","
                                             + uetEmailAddress_jTextField.getText();
                        }
                    }   
                }
                // if writing unsuccessful, show errors in pop up so app user is aware
                else
                {
                    System.out.println("ERRORS:\n" + dbWrite.getErrorMessage());
                    System.out.println("***************************************\n");
                    JOptionPane.showMessageDialog(null, "UPDATING TEAM RECORDS ON DATABASE:\n" 
                            + dbWrite.getErrorMessage(), "ERRORS DETECTED!", JOptionPane.ERROR_MESSAGE);
                }   
                
            }    
        } 
    }// end updateExisitingTeam()
    
    /***************************************************************************
    Method:     addNewEvent()
    Purpose:    add row to event table of goldcoast_esports database with validated field data
                uses sql statements to dynamically write data to database with dbWrite
    Inputs:     void
    Outputs:    void
    ***************************************************************************/
    private void addNewEvent()
    {
        // call method to validate all field data
        if (validateNewEvent())
        {
            // display save update for this team pop up and provide yes/no option to save
            int yesNo = JOptionPane.showConfirmDialog(null, "You are about to add a new event for: "
                                + aneNewEventName_jTextField.getText() + "\nDo you wish to continue?", 
                                  "ADD NEW EVENT", JOptionPane.YES_NO_OPTION);
            
            // if yes is selected
            if (yesNo == JOptionPane.YES_OPTION)
            {
                // write field data to database with sql
                sql = "INSERT INTO goldcoast_esports.event "
                    + "VALUES ('" + aneNewEventName_jTextField.getText() + "', '"
                                  + aneDate_jTextField.getText() + "', '"
                                  + aneLocation_jTextField.getText() + "')";
                // use dbWrite object to write to database with sql statement
                dbWrite = new DbWrite(sql);
                
                // system out and error messaging
                System.out.println("addNewEvent():");
                System.out.println("***************************************");
                // if writing is successful
                if (dbWrite.getErrorMessage().isEmpty())
                {
                    System.out.println("Succesful write operation to goldcoast_esports.event");
                    System.out.println("***************************************\n");
                    
                    // gate jComboBox events while updating contents of event jComboBoxees
                    comboBoxStatus = false;
                    // temp store of event jComboBox selections
                    String ecrEventTemp = ecrEvent_jComboBox.getSelectedItem().toString();
                    String ancrEventTemp = ancrEvent_jComboBox.getSelectedItem().toString();
                    // update the two event jComboBoxes with new sql data
                    // also updates eventsStrArray
                    displayEventJComboBoxes();
                    // set event jComboBox selections to what they were before update
                    ecrEvent_jComboBox.setSelectedItem(ecrEventTemp);
                    ancrEvent_jComboBox.setSelectedItem(ancrEventTemp);
                    // re-enable jComboBox events
                    comboBoxStatus = true;
                }
                // if writing unsuccessful, show errors in pop up so app user is aware
                else
                {
                    System.out.println("ERRORS:\n" + dbWrite.getErrorMessage());
                    System.out.println("***************************************\n");
                    JOptionPane.showMessageDialog(null, "WRITING EVENT RECORDS TO DATABASE:\n" 
                            + dbWrite.getErrorMessage(), "ERRORS DETECTED!", JOptionPane.ERROR_MESSAGE);
                }   
            }    
        } 
    }// end addNewEvent()
    
    
    //************ VALIDATION METHODS ************
    
    /***************************************************************************
    Method:     validateNewCompResults()
    Purpose:    validates all fields on addNewCompResults_jPanel
                checks that jComboBoxes aren't empty
                checks that team1 and team2 are different
                checks that points fields are between 0-2 and the sum of both is 2
                checks that record doesn't already exist
    Inputs:     void
    Outputs:    boolean (validNewCompResults)
    ***************************************************************************/
    private boolean validateNewCompResults()
    {
        // set data fields as variables
        String event = ancrEvent_jComboBox.getSelectedItem().toString();
        String game = ancrGame_jComboBox.getSelectedItem().toString();
        String team1 = ancrTeam1_jComboBox.getSelectedItem().toString();
        String team2 = ancrTeam2_jComboBox.getSelectedItem().toString();
        String team1PointsStr = ancrTeam1Points_jTextField.getText();
        String team2PointsStr = ancrTeam2Points_jTextField.getText();

        // String to add any failed checks to error pop up
        String errorMsg = "";
        // boolean to validate if any empty fields exist
        boolean validNewCompResults = true;
        
        // check that records exist and jComboBoxes aren't empty
        if (event.isEmpty())
        {
            errorMsg += "- no events on record!\n";
            validNewCompResults = false;
        }
        if (game.isEmpty())
        {
            errorMsg += "- no games on record!\n";
            validNewCompResults = false;
        }
        if (team1.isEmpty() ||
            team2.isEmpty())
        {
            errorMsg += "- no teams on record!\n";
            validNewCompResults = false;
        }
        
        // check that team 1 and team 2 are not the same
        if (team1.equals(team2))
        {
            errorMsg += "- both team 1 and team 2 must be different teams\n";
            validNewCompResults = false;
        }
        
        // check that points jTextFields are an integer 0-2 and the sum of both is 2
        try
        { 
            // convert text fields to numeric values
            int team1Points = Integer.parseInt(team1PointsStr);
            int team2Points = Integer.parseInt(team2PointsStr);
            
            // added a check for each requirement so pop up displays all errors
            // and requirements for valid entry
            if ((team1Points < 0 || team1Points > 2) ||
                (team2Points < 0 || team2Points > 2))   
            {
                errorMsg += "- team points must be 0, 1 or 2\n";
                validNewCompResults = false;
            }
            if (team1Points + team2Points != 2)
            {
                errorMsg += "- team 1 and team 2 points must add to 2\n";
                validNewCompResults = false;
            }
        }
        // catch attempt to parseInt anything but an integer
        catch (NumberFormatException nfe)
        {
            errorMsg += "- team points must be numeric and 0, 1 or 2\n";
            validNewCompResults = false;
        }
        catch (Exception e)
        {
            errorMsg += "- team points must be numeric and 0, 1 or 2\n";
            validNewCompResults = false;
        }
       
        // if a record exists
        for (int row = 0; row < compResultsArray.length; row++)
        {
            // compResultsArray records matches jComboBoxes
            if (compResultsArray[row][5].equals(event) &&
                compResultsArray[row][0].equals(game) &&
                ((compResultsArray[row][1].equals(team1) && compResultsArray[row][3].equals(team2)) ||
                 (compResultsArray[row][3].equals(team1) && compResultsArray[row][1].equals(team2))))   
            {
                errorMsg += "- existing record in database (same teams playing in same event and same game)\n";
                 validNewCompResults = false;
            }
        }
        
        // display errorMsg String in pop up only if there is an error
        if(validNewCompResults == false)
        {
            JOptionPane.showMessageDialog(null, "ERROR(S) DETECTED:\n" + errorMsg, "ERRORS DETECTED!",
                                          JOptionPane.ERROR_MESSAGE);
        }
        
        // return true if no errors detected
        return validNewCompResults;
    }// end validateNewCompResults()
    
    /***************************************************************************
    Method:     validateNewTeam()
    Purpose:    validates all fields on addNewTeam_jPanel
                checks that fields aren't empty
                checks that team doesn't already exist
    Inputs:     void
    Outputs:    boolean (validNewTeam)
    ***************************************************************************/
    private boolean validateNewTeam()
    {
        // set data fields as variables
        String newTeamName = antNewTeamName_jTextField.getText();
        String contactName = antContactName_jTextField.getText();
        String phoneNumber = antPhoneNumber_jTextField.getText();
        String emailAddress = antEmailAddress_jTextField.getText();
        
        // String to add any failed checks to error pop up
        String errorMsg = "";
        // boolean to validate if any empty fields exist
        boolean validNewTeam = true;

        // check that jTextFields aren't empty
        if (newTeamName.isEmpty())
        {
            errorMsg += "- team name required\n";
            validNewTeam = false;
        }
        if (contactName.isEmpty())
        {
            errorMsg += "- contact person's name required\n";
            validNewTeam = false;
        }
        if (phoneNumber.isEmpty())
        {
            errorMsg += "- contact phone number required\n";
            validNewTeam = false;
        }
        if (emailAddress.isEmpty())
        {
            errorMsg += "- contact email address required\n";
            validNewTeam = false;
        }
        
        // if a record exists
        for (int i = 0; i < teamsStrArray.length; i++)
        {
            // split teamsStrArray by delimiter and assign to teamString array
            String[] teamString = teamsStrArray[i].split(",");

            // teamsStrArray team matches newTeamName
            if (teamString[0].equals(newTeamName))
            {
                errorMsg += "- existing record in database (same team name)\n";
                validNewTeam = false;
            }
        }       
        
        // display errorMsg String in pop up only if there is an error
        if(!errorMsg.isEmpty())
        {
            JOptionPane.showMessageDialog(null, "ERROR(S) DETECTED:\n" + errorMsg, "ERRORS DETECTED!",
                                          JOptionPane.ERROR_MESSAGE);
        }
        
        // return true if no errors detected
        return validNewTeam;
    }//end validateNewTeam()
    
    /***************************************************************************
    Method:     validateUpdateExistingTeam()
    Purpose:    validates all fields on addUpdateExistingTeam_jPanel
                checks that fields aren't empty
                checks that record doesn't already exist
    Inputs:     void
    Outputs:    boolean (validUpdateExistingTeam)
    ***************************************************************************/
    private boolean validateUpdateExistingTeam()
    {
        // set data fields as variables
        String teamName = uetTeamName_jComboBox.getSelectedItem().toString();
        String contactName = uetContactName_jTextField.getText();
        String phoneNumber = uetPhoneNumber_jTextField.getText();
        String emailAddress = uetEmailAddress_jTextField.getText();
        
        // String to add any failed checks to error pop up
        String errorMsg = "";
        // boolean to validate if any empty fields exist
        boolean validUpdateExistingTeam = true;

        // check that jTextFields and jCobmoBox aren't empty
        if (teamName.isEmpty())
        {
            errorMsg += "- no teams to update!\n";
            validUpdateExistingTeam = false;
        }
        if (contactName.isEmpty())
        {
            errorMsg += "- contact person's name required\n";
            validUpdateExistingTeam = false;
        }
        if (phoneNumber.isEmpty())
        {
            errorMsg += "- contact phone number required\n";
            validUpdateExistingTeam = false;
        }
        if (emailAddress.isEmpty())
        {
            errorMsg += "- contact email address required\n";
            validUpdateExistingTeam = false;
        }
        
        // if a record exists
        for (int i = 0; i < teamsStrArray.length; i++)
        {
            // BioHazards,Zheng Lee,0418999888,zhenglee99@geemail.com
            // split teamsStrArray by delimiter and assign to teamString array
            String[] teamString = teamsStrArray[i].split(",");

            // teamsStrArray team matches jTextFields
            if (teamString[0].equals(teamName) &&
                teamString[1].equals(contactName) &&
                teamString[2].equals(phoneNumber) &&
                teamString[3].equals(emailAddress))
            {
                errorMsg += "- no new data to record!\n";
                validUpdateExistingTeam = false;
            }
        }
        
        // display errorMsg String in pop up only if there is an error
        if(!errorMsg.isEmpty())
        {
            JOptionPane.showMessageDialog(null, "ERROR(S) DETECTED:\n" + errorMsg, "ERRORS DETECTED!",
                                          JOptionPane.ERROR_MESSAGE);
        }
        
        // return true if no errors detected
        return validUpdateExistingTeam;
    }//end validateUpdateExistingTeam()
    
    /***************************************************************************
    Method:     validateNewEvent()
    Purpose:    validates all fields on addNewEvent_jPanel
                checks that fields aren't empty
                checks that event doesn't already exist
                checks aneDate_jTextField conforms to standards
    Inputs:     void
    Outputs:    boolean (validNewEvent)
    ***************************************************************************/
    private boolean validateNewEvent()
    {
        // set data fields as variables
        String newEventName = aneNewEventName_jTextField.getText();
        String date = aneDate_jTextField.getText();
        String location = aneLocation_jTextField.getText();
        
        // String to add any failed checks to error pop up
        String errorMsg = "";
        // boolean to validate if any empty fields exist
        boolean validNewEvent = true;

        // check that jTextFields are not empty
        if (newEventName.isEmpty())
        {
            errorMsg += "- event name required\n";
            validNewEvent = false;
        }
        if (date.isEmpty())
        {
            errorMsg += "- date required\n";
            validNewEvent = false;
        }
        if (location.isEmpty())
        {
            errorMsg += "- location required\n";
            validNewEvent = false;
        }
        
        // if a record exists
        for (int i = 0; i < eventsStrArray.length; i++)
        {
            // split eventsStrArray by delimiter and assign to eventString array
            String[] eventString = eventsStrArray[i].split(",");

            // eventsStrArray event name matches newEventName
            if (eventString[0].equals(newEventName))
            {
                errorMsg += "- existing record in database (same event name)\n";
                validNewEvent = false;
            }
        }
        
        // check that aneDate_jTextField.getText() is 10 chars in length
        if (date.length() != 10)
        {
            errorMsg += "- event date must be 10 chars - format: yyyy-mm-dd\n";
            validNewEvent = false;
        }
        // check that date is properly formatted yyyy-mm-dd
        else
        {
            try 
            {
                // attempts to parse jTextField with format yyyy-mm-dd
                // checks that month is 1 - 12, day is 1 - 31
                // ResolverStyle class utilized to check that max value for days
                // matches calender months, set to STRICT to return error in event
                LocalDate validDate = LocalDate.parse(date, dtf.withResolverStyle(ResolverStyle.STRICT));
            }
            // if parse fails then the string does not conform to the format
            catch (DateTimeParseException exp) 
            {
                errorMsg += "- event date must be in format: yyyy-mm-dd "
                          + "(year, month and day must be numeric)\n";
                validNewEvent = false;
            }
        }
        
        // display errorMsg String in pop up only if there is an error
        if(!errorMsg.isEmpty())
        {
            JOptionPane.showMessageDialog(null, "ERROR(S) DETECTED:\n" + errorMsg, "ERRORS DETECTED!",
                                          JOptionPane.ERROR_MESSAGE);
        }
        
        // return true if no errors detected
        return validNewEvent;
    }//end validateNewEvent()
      
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        header_jPanel = new javax.swing.JPanel();
        image_jLabel = new javax.swing.JLabel();
        body_jPanel = new javax.swing.JPanel();
        body_jTabbedPane = new javax.swing.JTabbedPane();
        eventCompResults_jPanel = new javax.swing.JPanel();
        ecrEvent_jLabel = new javax.swing.JLabel();
        ecrEvent_jComboBox = new javax.swing.JComboBox<>();
        ecrTeam_jLabel = new javax.swing.JLabel();
        ecrTeam_jComboBox = new javax.swing.JComboBox<>();
        ecrCompResults_jLabel = new javax.swing.JLabel();
        ecrCompResults_jScrollPane = new javax.swing.JScrollPane();
        ecrCompResults_jTable = new javax.swing.JTable();
        ecrExportCompResultsCSV_jButton = new javax.swing.JButton();
        ecrEventLeaderBoards_jLabel = new javax.swing.JLabel();
        ecrLeaderBoardSelected_jScrollPane = new javax.swing.JScrollPane();
        ecrLeaderBoardSelected_jTable = new javax.swing.JTable();
        ecrLeaderBoardAll_jScrollPane = new javax.swing.JScrollPane();
        ecrLeaderBoardAll_jTable = new javax.swing.JTable();
        ecrExportLeaderBoardsCSV_jButton = new javax.swing.JButton();
        ecrRecordCount_jTextField = new javax.swing.JTextField();
        addNewCompResults_jPanel = new javax.swing.JPanel();
        ancrEvent_jLabel = new javax.swing.JLabel();
        ancrEvent_jComboBox = new javax.swing.JComboBox<>();
        ancrGame_jLabel = new javax.swing.JLabel();
        ancrGame_jComboBox = new javax.swing.JComboBox<>();
        ancrTeam1_jLabel = new javax.swing.JLabel();
        ancrTeam1_jComboBox = new javax.swing.JComboBox<>();
        ancrTeam2_jLabel = new javax.swing.JLabel();
        ancrTeam2_jComboBox = new javax.swing.JComboBox<>();
        ancrTeam1Points_jLabel = new javax.swing.JLabel();
        ancrTeam1Points_jTextField = new javax.swing.JTextField();
        ancrTeam2Points_jLabel = new javax.swing.JLabel();
        ancrTeam2Points_jTextField = new javax.swing.JTextField();
        ancr_jButton = new javax.swing.JButton();
        addNewTeam_jPanel = new javax.swing.JPanel();
        antNewTeamName_jLabel = new javax.swing.JLabel();
        antNewTeamName_jTextField = new javax.swing.JTextField();
        antContactName_jLabel = new javax.swing.JLabel();
        antContactName_jTextField = new javax.swing.JTextField();
        antPhoneNumber_jLabel = new javax.swing.JLabel();
        antPhoneNumber_jTextField = new javax.swing.JTextField();
        antEmailAddress_jLabel = new javax.swing.JLabel();
        antEmailAddress_jTextField = new javax.swing.JTextField();
        ant_jButton = new javax.swing.JButton();
        updateExisitingTeam_jPanel = new javax.swing.JPanel();
        uetTeamName_jLabel = new javax.swing.JLabel();
        uetTeamName_jComboBox = new javax.swing.JComboBox<>();
        uetContactName_jLabel = new javax.swing.JLabel();
        uetContactName_jTextField = new javax.swing.JTextField();
        uetPhoneNumber_jLabel = new javax.swing.JLabel();
        uetPhoneNumber_jTextField = new javax.swing.JTextField();
        uetEmailAddress_jLabel = new javax.swing.JLabel();
        uetEmailAddress_jTextField = new javax.swing.JTextField();
        uet_jButton = new javax.swing.JButton();
        addNewEvent_jPanel = new javax.swing.JPanel();
        aneNewEventName_jLabel = new javax.swing.JLabel();
        aneNewEventName_jTextField = new javax.swing.JTextField();
        aneDate_jLabel = new javax.swing.JLabel();
        aneDate_jTextField = new javax.swing.JTextField();
        aneLocation_jLabel = new javax.swing.JLabel();
        aneLocation_jTextField = new javax.swing.JTextField();
        ane_jButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));
        setLocation(new java.awt.Point(800, 400));
        setResizable(false);
        setSize(new java.awt.Dimension(800, 600));

        header_jPanel.setBackground(new java.awt.Color(255, 255, 255));

        image_jLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/gc-esports2-header.jpg"))); // NOI18N

        javax.swing.GroupLayout header_jPanelLayout = new javax.swing.GroupLayout(header_jPanel);
        header_jPanel.setLayout(header_jPanelLayout);
        header_jPanelLayout.setHorizontalGroup(
            header_jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, header_jPanelLayout.createSequentialGroup()
                .addComponent(image_jLabel)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        header_jPanelLayout.setVerticalGroup(
            header_jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(header_jPanelLayout.createSequentialGroup()
                .addComponent(image_jLabel)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        body_jPanel.setBackground(new java.awt.Color(255, 255, 255));
        body_jPanel.setPreferredSize(new java.awt.Dimension(800, 500));

        body_jTabbedPane.setBackground(new java.awt.Color(255, 255, 255));

        eventCompResults_jPanel.setBackground(new java.awt.Color(255, 255, 255));

        ecrEvent_jLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        ecrEvent_jLabel.setText("Event:");

        ecrEvent_jComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ecrEvent_jComboBoxActionPerformed(evt);
            }
        });

        ecrTeam_jLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        ecrTeam_jLabel.setText("Team:");

        ecrTeam_jComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ecrTeam_jComboBoxActionPerformed(evt);
            }
        });

        ecrCompResults_jLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        ecrCompResults_jLabel.setText("Competition Results:");

        ecrCompResults_jTable.setModel(ecrCompResultsTableModel);
        ecrCompResults_jScrollPane.setViewportView(ecrCompResults_jTable);

        ecrExportCompResultsCSV_jButton.setText("Export competition results as CSV file");
        ecrExportCompResultsCSV_jButton.setToolTipText("");
        ecrExportCompResultsCSV_jButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ecrExportCompResultsCSV_jButtonActionPerformed(evt);
            }
        });

        ecrEventLeaderBoards_jLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        ecrEventLeaderBoards_jLabel.setText("Event Leader Boards:");

        ecrLeaderBoardSelected_jTable.setModel(ecrLeaderBoardSelectedTableModel);
        ecrLeaderBoardSelected_jScrollPane.setViewportView(ecrLeaderBoardSelected_jTable);

        ecrLeaderBoardAll_jTable.setModel(ecrLeaderBoardAllTableModel);
        ecrLeaderBoardAll_jScrollPane.setViewportView(ecrLeaderBoardAll_jTable);

        ecrExportLeaderBoardsCSV_jButton.setText("Export leader boards as CSV files");
        ecrExportLeaderBoardsCSV_jButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ecrExportLeaderBoardsCSV_jButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout eventCompResults_jPanelLayout = new javax.swing.GroupLayout(eventCompResults_jPanel);
        eventCompResults_jPanel.setLayout(eventCompResults_jPanelLayout);
        eventCompResults_jPanelLayout.setHorizontalGroup(
            eventCompResults_jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(eventCompResults_jPanelLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(eventCompResults_jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(eventCompResults_jPanelLayout.createSequentialGroup()
                        .addGroup(eventCompResults_jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(ecrEvent_jLabel)
                            .addComponent(ecrTeam_jLabel))
                        .addGap(33, 33, 33)
                        .addGroup(eventCompResults_jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(ecrEvent_jComboBox, 0, 420, Short.MAX_VALUE)
                            .addComponent(ecrTeam_jComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(eventCompResults_jPanelLayout.createSequentialGroup()
                        .addGroup(eventCompResults_jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(ecrCompResults_jLabel)
                            .addGroup(eventCompResults_jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(eventCompResults_jPanelLayout.createSequentialGroup()
                                    .addComponent(ecrRecordCount_jTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(ecrExportCompResultsCSV_jButton, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(ecrCompResults_jScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 438, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                        .addGroup(eventCompResults_jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(eventCompResults_jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(ecrExportLeaderBoardsCSV_jButton, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(eventCompResults_jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(ecrLeaderBoardAll_jScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(ecrLeaderBoardSelected_jScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(ecrEventLeaderBoards_jLabel))
                        .addContainerGap(20, Short.MAX_VALUE))))
        );
        eventCompResults_jPanelLayout.setVerticalGroup(
            eventCompResults_jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(eventCompResults_jPanelLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(eventCompResults_jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ecrEvent_jLabel)
                    .addComponent(ecrEvent_jComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(eventCompResults_jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ecrTeam_jLabel)
                    .addComponent(ecrTeam_jComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 17, Short.MAX_VALUE)
                .addGroup(eventCompResults_jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ecrCompResults_jLabel)
                    .addComponent(ecrEventLeaderBoards_jLabel))
                .addGap(6, 6, 6)
                .addGroup(eventCompResults_jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(eventCompResults_jPanelLayout.createSequentialGroup()
                        .addComponent(ecrLeaderBoardSelected_jScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(ecrLeaderBoardAll_jScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(ecrCompResults_jScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(eventCompResults_jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ecrRecordCount_jTextField, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, eventCompResults_jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(ecrExportCompResultsCSV_jButton, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(ecrExportLeaderBoardsCSV_jButton, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(20, 20, 20))
        );

        body_jTabbedPane.addTab("Event competition results", eventCompResults_jPanel);
        eventCompResults_jPanel.getAccessibleContext().setAccessibleName("");

        addNewCompResults_jPanel.setBackground(new java.awt.Color(255, 255, 255));

        ancrEvent_jLabel.setText("Event:");

        ancrGame_jLabel.setText("Game:");

        ancrTeam1_jLabel.setText("Team 1:");

        ancrTeam2_jLabel.setText("Team 2:");

        ancrTeam1Points_jLabel.setText("Team 1 Points:");

        ancrTeam2Points_jLabel.setText("Team 2 Points:");

        ancr_jButton.setText("ADD NEW COMPETITION RESULT");
        ancr_jButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ancr_jButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout addNewCompResults_jPanelLayout = new javax.swing.GroupLayout(addNewCompResults_jPanel);
        addNewCompResults_jPanel.setLayout(addNewCompResults_jPanelLayout);
        addNewCompResults_jPanelLayout.setHorizontalGroup(
            addNewCompResults_jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(addNewCompResults_jPanelLayout.createSequentialGroup()
                .addGap(141, 141, 141)
                .addGroup(addNewCompResults_jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(ancr_jButton)
                    .addGroup(addNewCompResults_jPanelLayout.createSequentialGroup()
                        .addGroup(addNewCompResults_jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(ancrEvent_jLabel)
                            .addComponent(ancrGame_jLabel)
                            .addComponent(ancrTeam1_jLabel)
                            .addComponent(ancrTeam2_jLabel))
                        .addGap(18, 18, 18)
                        .addGroup(addNewCompResults_jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(ancrGame_jComboBox, 0, 320, Short.MAX_VALUE)
                            .addComponent(ancrTeam1_jComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(ancrTeam2_jComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(ancrEvent_jComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(addNewCompResults_jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(addNewCompResults_jPanelLayout.createSequentialGroup()
                                .addComponent(ancrTeam2Points_jLabel)
                                .addGap(18, 18, 18)
                                .addComponent(ancrTeam2Points_jTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(addNewCompResults_jPanelLayout.createSequentialGroup()
                                .addComponent(ancrTeam1Points_jLabel)
                                .addGap(18, 18, 18)
                                .addComponent(ancrTeam1Points_jTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(97, Short.MAX_VALUE))
        );
        addNewCompResults_jPanelLayout.setVerticalGroup(
            addNewCompResults_jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(addNewCompResults_jPanelLayout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(addNewCompResults_jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ancrEvent_jComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ancrEvent_jLabel))
                .addGap(18, 18, 18)
                .addGroup(addNewCompResults_jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ancrGame_jComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ancrGame_jLabel))
                .addGap(18, 18, 18)
                .addGroup(addNewCompResults_jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ancrTeam1_jComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ancrTeam1_jLabel)
                    .addComponent(ancrTeam1Points_jLabel)
                    .addComponent(ancrTeam1Points_jTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(addNewCompResults_jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ancrTeam2_jComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ancrTeam2Points_jLabel)
                    .addComponent(ancrTeam2_jLabel)
                    .addComponent(ancrTeam2Points_jTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(ancr_jButton, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50))
        );

        body_jTabbedPane.addTab("Add new competition results", addNewCompResults_jPanel);

        addNewTeam_jPanel.setBackground(new java.awt.Color(255, 255, 255));

        antNewTeamName_jLabel.setText("New Team Name:");

        antContactName_jLabel.setText("Contact Name:");

        antPhoneNumber_jLabel.setText("Phone Number:");

        antEmailAddress_jLabel.setText("Email Address:");

        ant_jButton.setText("ADD NEW TEAM");
        ant_jButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ant_jButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout addNewTeam_jPanelLayout = new javax.swing.GroupLayout(addNewTeam_jPanel);
        addNewTeam_jPanel.setLayout(addNewTeam_jPanelLayout);
        addNewTeam_jPanelLayout.setHorizontalGroup(
            addNewTeam_jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(addNewTeam_jPanelLayout.createSequentialGroup()
                .addGap(88, 88, 88)
                .addGroup(addNewTeam_jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(ant_jButton, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(addNewTeam_jPanelLayout.createSequentialGroup()
                        .addGroup(addNewTeam_jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(antNewTeamName_jLabel)
                            .addComponent(antContactName_jLabel)
                            .addComponent(antPhoneNumber_jLabel)
                            .addComponent(antEmailAddress_jLabel))
                        .addGap(18, 18, 18)
                        .addGroup(addNewTeam_jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(antPhoneNumber_jTextField, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(antContactName_jTextField)
                            .addComponent(antNewTeamName_jTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 320, Short.MAX_VALUE)
                            .addComponent(antEmailAddress_jTextField))))
                .addContainerGap(280, Short.MAX_VALUE))
        );
        addNewTeam_jPanelLayout.setVerticalGroup(
            addNewTeam_jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(addNewTeam_jPanelLayout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(addNewTeam_jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(antNewTeamName_jTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(antNewTeamName_jLabel))
                .addGap(18, 18, 18)
                .addGroup(addNewTeam_jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(antContactName_jTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(antContactName_jLabel))
                .addGap(18, 18, 18)
                .addGroup(addNewTeam_jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(antPhoneNumber_jTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(antPhoneNumber_jLabel))
                .addGap(18, 18, 18)
                .addGroup(addNewTeam_jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(antEmailAddress_jTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(antEmailAddress_jLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(ant_jButton, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50))
        );

        body_jTabbedPane.addTab("Add new team", addNewTeam_jPanel);

        updateExisitingTeam_jPanel.setBackground(new java.awt.Color(255, 255, 255));

        uetTeamName_jLabel.setText("Team Name:");

        uetTeamName_jComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                uetTeamName_jComboBoxActionPerformed(evt);
            }
        });

        uetContactName_jLabel.setText("Contact Name:");

        uetPhoneNumber_jLabel.setText("Phone Number:");

        uetEmailAddress_jLabel.setText("Email Address:");

        uet_jButton.setText("UPDATE EXISTING TEAM");
        uet_jButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                uet_jButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout updateExisitingTeam_jPanelLayout = new javax.swing.GroupLayout(updateExisitingTeam_jPanel);
        updateExisitingTeam_jPanel.setLayout(updateExisitingTeam_jPanelLayout);
        updateExisitingTeam_jPanelLayout.setHorizontalGroup(
            updateExisitingTeam_jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(updateExisitingTeam_jPanelLayout.createSequentialGroup()
                .addGap(98, 98, 98)
                .addGroup(updateExisitingTeam_jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(uet_jButton)
                    .addGroup(updateExisitingTeam_jPanelLayout.createSequentialGroup()
                        .addGroup(updateExisitingTeam_jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(uetTeamName_jLabel)
                            .addComponent(uetContactName_jLabel)
                            .addComponent(uetPhoneNumber_jLabel)
                            .addComponent(uetEmailAddress_jLabel))
                        .addGap(18, 18, 18)
                        .addGroup(updateExisitingTeam_jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(uetPhoneNumber_jTextField, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(uetContactName_jTextField, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(uetEmailAddress_jTextField, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(uetTeamName_jComboBox, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(280, Short.MAX_VALUE))
        );
        updateExisitingTeam_jPanelLayout.setVerticalGroup(
            updateExisitingTeam_jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, updateExisitingTeam_jPanelLayout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(updateExisitingTeam_jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(uetTeamName_jComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(uetTeamName_jLabel))
                .addGap(18, 18, 18)
                .addGroup(updateExisitingTeam_jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(uetContactName_jTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(uetContactName_jLabel))
                .addGap(18, 18, 18)
                .addGroup(updateExisitingTeam_jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(uetPhoneNumber_jTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(uetPhoneNumber_jLabel))
                .addGap(18, 18, 18)
                .addGroup(updateExisitingTeam_jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(uetEmailAddress_jTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(uetEmailAddress_jLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(uet_jButton, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50))
        );

        body_jTabbedPane.addTab("Update existing team", updateExisitingTeam_jPanel);

        addNewEvent_jPanel.setBackground(new java.awt.Color(255, 255, 255));

        aneNewEventName_jLabel.setText("New Event Name:");

        aneDate_jLabel.setText("Date:");

        aneDate_jTextField.setText(now.format(dtf));

        aneLocation_jLabel.setText("Location:");

        aneLocation_jTextField.setText("TAFE Coomera");

        ane_jButton.setText("ADD NEW EVENT");
        ane_jButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ane_jButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout addNewEvent_jPanelLayout = new javax.swing.GroupLayout(addNewEvent_jPanel);
        addNewEvent_jPanel.setLayout(addNewEvent_jPanelLayout);
        addNewEvent_jPanelLayout.setHorizontalGroup(
            addNewEvent_jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(addNewEvent_jPanelLayout.createSequentialGroup()
                .addGap(88, 88, 88)
                .addGroup(addNewEvent_jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(ane_jButton, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(addNewEvent_jPanelLayout.createSequentialGroup()
                        .addGroup(addNewEvent_jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(aneNewEventName_jLabel)
                            .addComponent(aneDate_jLabel)
                            .addComponent(aneLocation_jLabel))
                        .addGap(18, 18, 18)
                        .addGroup(addNewEvent_jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(aneNewEventName_jTextField)
                            .addComponent(aneDate_jTextField)
                            .addComponent(aneLocation_jTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 320, Short.MAX_VALUE))))
                .addContainerGap(280, Short.MAX_VALUE))
        );
        addNewEvent_jPanelLayout.setVerticalGroup(
            addNewEvent_jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(addNewEvent_jPanelLayout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(addNewEvent_jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(aneNewEventName_jTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(aneNewEventName_jLabel))
                .addGap(18, 18, 18)
                .addGroup(addNewEvent_jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(aneDate_jTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(aneDate_jLabel))
                .addGap(18, 18, 18)
                .addGroup(addNewEvent_jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(aneLocation_jTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(aneLocation_jLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(ane_jButton, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50))
        );

        body_jTabbedPane.addTab("Add new event", addNewEvent_jPanel);

        javax.swing.GroupLayout body_jPanelLayout = new javax.swing.GroupLayout(body_jPanel);
        body_jPanel.setLayout(body_jPanelLayout);
        body_jPanelLayout.setHorizontalGroup(
            body_jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(body_jTabbedPane, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );
        body_jPanelLayout.setVerticalGroup(
            body_jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(body_jTabbedPane, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );

        body_jTabbedPane.getAccessibleContext().setAccessibleName("");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(header_jPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(body_jPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(header_jPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(body_jPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 484, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    private void ecrExportCompResultsCSV_jButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ecrExportCompResultsCSV_jButtonActionPerformed
        // write to competition.csv
        writeCompetitionCSV();
    }//GEN-LAST:event_ecrExportCompResultsCSV_jButtonActionPerformed

    private void ecrExportLeaderBoardsCSV_jButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ecrExportLeaderBoardsCSV_jButtonActionPerformed
        // write to event.csv
        writeEventCSV();
    }//GEN-LAST:event_ecrExportLeaderBoardsCSV_jButtonActionPerformed

    private void ancr_jButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ancr_jButtonActionPerformed
        // save new competition result to goldcoast_esports database
        addNewCompResults();
    }//GEN-LAST:event_ancr_jButtonActionPerformed

    private void ant_jButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ant_jButtonActionPerformed
        // save new team to goldcoast_esports database
        addNewTeam();
    }//GEN-LAST:event_ant_jButtonActionPerformed

    private void uet_jButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_uet_jButtonActionPerformed
        // update existing team on goldcoast_esports database
        updateExistingTeam();
    }//GEN-LAST:event_uet_jButtonActionPerformed

    private void ane_jButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ane_jButtonActionPerformed
        // save new event to goldcoast_esports database
        addNewEvent();
    }//GEN-LAST:event_ane_jButtonActionPerformed

    private void uetTeamName_jComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_uetTeamName_jComboBoxActionPerformed
        // populate jTextFields with team data from goldcoast_esports database
        // gate event when comboBoxStatus is false
        if (comboBoxStatus)
        {
            displayTeamData();
        }
    }//GEN-LAST:event_uetTeamName_jComboBoxActionPerformed

    private void ecrEvent_jComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ecrEvent_jComboBoxActionPerformed
        // populate jTables with competition data from goldcoast_esports database
        // gate event when comboBoxStatus is false
        if (comboBoxStatus)
        {
            selectEventJTables();
        }
    }//GEN-LAST:event_ecrEvent_jComboBoxActionPerformed

    private void ecrTeam_jComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ecrTeam_jComboBoxActionPerformed
        // populate jTables with competition data from goldcoast_esports database
        // gate event when comboBoxStatus is false
        if (comboBoxStatus)
        {
            selectTeamJTables();
        }
    }//GEN-LAST:event_ecrTeam_jComboBoxActionPerformed

    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(GCEsportsApp2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GCEsportsApp2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GCEsportsApp2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GCEsportsApp2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GCEsportsApp2().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel addNewCompResults_jPanel;
    private javax.swing.JPanel addNewEvent_jPanel;
    private javax.swing.JPanel addNewTeam_jPanel;
    private javax.swing.JComboBox<String> ancrEvent_jComboBox;
    private javax.swing.JLabel ancrEvent_jLabel;
    private javax.swing.JComboBox<String> ancrGame_jComboBox;
    private javax.swing.JLabel ancrGame_jLabel;
    private javax.swing.JLabel ancrTeam1Points_jLabel;
    private javax.swing.JTextField ancrTeam1Points_jTextField;
    private javax.swing.JComboBox<String> ancrTeam1_jComboBox;
    private javax.swing.JLabel ancrTeam1_jLabel;
    private javax.swing.JLabel ancrTeam2Points_jLabel;
    private javax.swing.JTextField ancrTeam2Points_jTextField;
    private javax.swing.JComboBox<String> ancrTeam2_jComboBox;
    private javax.swing.JLabel ancrTeam2_jLabel;
    private javax.swing.JButton ancr_jButton;
    private javax.swing.JLabel aneDate_jLabel;
    private javax.swing.JTextField aneDate_jTextField;
    private javax.swing.JLabel aneLocation_jLabel;
    private javax.swing.JTextField aneLocation_jTextField;
    private javax.swing.JLabel aneNewEventName_jLabel;
    private javax.swing.JTextField aneNewEventName_jTextField;
    private javax.swing.JButton ane_jButton;
    private javax.swing.JLabel antContactName_jLabel;
    private javax.swing.JTextField antContactName_jTextField;
    private javax.swing.JLabel antEmailAddress_jLabel;
    private javax.swing.JTextField antEmailAddress_jTextField;
    private javax.swing.JLabel antNewTeamName_jLabel;
    private javax.swing.JTextField antNewTeamName_jTextField;
    private javax.swing.JLabel antPhoneNumber_jLabel;
    private javax.swing.JTextField antPhoneNumber_jTextField;
    private javax.swing.JButton ant_jButton;
    private javax.swing.JPanel body_jPanel;
    private javax.swing.JTabbedPane body_jTabbedPane;
    private javax.swing.JLabel ecrCompResults_jLabel;
    private javax.swing.JScrollPane ecrCompResults_jScrollPane;
    private javax.swing.JTable ecrCompResults_jTable;
    private javax.swing.JLabel ecrEventLeaderBoards_jLabel;
    private javax.swing.JComboBox<String> ecrEvent_jComboBox;
    private javax.swing.JLabel ecrEvent_jLabel;
    private javax.swing.JButton ecrExportCompResultsCSV_jButton;
    private javax.swing.JButton ecrExportLeaderBoardsCSV_jButton;
    private javax.swing.JScrollPane ecrLeaderBoardAll_jScrollPane;
    private javax.swing.JTable ecrLeaderBoardAll_jTable;
    private javax.swing.JScrollPane ecrLeaderBoardSelected_jScrollPane;
    private javax.swing.JTable ecrLeaderBoardSelected_jTable;
    private javax.swing.JTextField ecrRecordCount_jTextField;
    private javax.swing.JComboBox<String> ecrTeam_jComboBox;
    private javax.swing.JLabel ecrTeam_jLabel;
    private javax.swing.JPanel eventCompResults_jPanel;
    private javax.swing.JPanel header_jPanel;
    private javax.swing.JLabel image_jLabel;
    private javax.swing.JLabel uetContactName_jLabel;
    private javax.swing.JTextField uetContactName_jTextField;
    private javax.swing.JLabel uetEmailAddress_jLabel;
    private javax.swing.JTextField uetEmailAddress_jTextField;
    private javax.swing.JLabel uetPhoneNumber_jLabel;
    private javax.swing.JTextField uetPhoneNumber_jTextField;
    private javax.swing.JComboBox<String> uetTeamName_jComboBox;
    private javax.swing.JLabel uetTeamName_jLabel;
    private javax.swing.JButton uet_jButton;
    private javax.swing.JPanel updateExisitingTeam_jPanel;
    // End of variables declaration//GEN-END:variables
}
