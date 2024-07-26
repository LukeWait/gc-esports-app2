/*******************************************************************************
File name: DbRead.java
Description:    1.  Make a connection to the gc_esports database
                        - a connection string(s) is read in from an external config file
                          containing the database URL, user id and password
                        - this is an academic exercise only, not recommended security practice!
                2.  Execute a read-only SQL query to retrieve a result set of row values from database
                3.  Result set is then read and values set up in relevant variables that are returned 
                    from the database and called from the GUI application
Version: 1.2.0
Author: ┬  ┬ ┬┬┌─┌─┐┬ ┬┌─┐╦╔╦╗
        │  │ │├┴┐├┤ │││├─┤║ ║
        ┴─┘└─┘┴ ┴└─┘└┴┘┴ ┴╩ ╩
Date: September 29, 2022
License: MIT License

Dependencies:
Java JDK 17
MySQL Connector/J 8.0.28

GitHub Repository: https://github.com/LukeWait/gc-esports-app2
*******************************************************************************/
package gcesportsapp2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class DbRead 
{
    // private data fields
    // database login details (read from db_access.config)
    private String dbURL;
    private String usrID;
    private String usrPWD;   
    // SQL operations
    private Connection conn;
    private Statement stmt;
    // returned data
    private ResultSet rs;
    private int recordCount;
    private String errorMessage;
    private Object [][] objDataSet;
    private String [] stringCSVData;
    
    
    /***************************************************************************
    Method:     DbRead() constructor method
    Purpose:    initialise private data fields
                connect to database
                run SQL read statements
                collate resulting data sets into required data structures
    Inputs:     String sql (SQL statement)
                String qryType (determines type of SQL and desired data output)
    Outputs:    none
    ***************************************************************************/
    public DbRead (String sql, String qryType)
    {
        // initilise private data fields
        // number of records in the record set when the SQL statement is executed
        recordCount = 0;
        // error message string is used to store any exception messages
        errorMessage = "";
        // set both arrays to null (can only initialise an array if the size is known)
        objDataSet = null;
        stringCSVData = null;     
        
        try
        {
            // file io buffered reader (used to read external file)
            BufferedReader br = new BufferedReader(new FileReader("db_access.config"));
            // get first line from external file
            String line = br.readLine();
            // set line counter (first line read in)
            int lineCounter = 1;
            
            // loop while the line value is NOT NULL
            while (line != null)
            {
                switch(lineCounter)
                {
                    // read and set dbURL
                    case 1:
                        dbURL = line.substring(6, line.length());
                        break;
                    // read and set usrID
                    case 2:
                        usrID = line.substring(6, line.length());
                        break;
                    // read and set usrPWD
                    case 3:
                        usrPWD = line.substring(7, line.length());
                        break; 
                    default:
                        break;
                }
                // read further lines
                line = br.readLine();
                // increment line counter
                lineCounter++;
            }
            
            /*
            // display read in data from the external app.config file
            System.out.println("dbURL = " + dbURL);
            System.out.println("usrID = " + usrID);
            System.out.println("usrPWD = " + usrPWD);
            */
            
            // close file io object after while loop
            br.close();
        }
        // catch IO exceptions
        catch (IOException ioe)
        {
            errorMessage += ioe.getMessage() + "\n";
        } 
        // catch other exceptions
        catch (Exception e)
        {
            errorMessage += e.getMessage() + "\n";
        }
            
        try
        {
            // make connection to database
            conn = DriverManager.getConnection(dbURL, usrID, usrPWD);
            // set up connection statement (type of result set that gets returned from SQL)
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                                        ResultSet.CONCUR_UPDATABLE);
            // assign result set to the execution (return) of the SQL statement
            rs = stmt.executeQuery(sql);
            
            // get record count
            if (rs != null)
            {
                rs.beforeFirst();
                rs.last();
                recordCount = rs.getRow();
            }       
            
            // display attributes of each record in a while loop
            if (recordCount > 0)
            {
                int counter = 0;
                objDataSet = new Object[recordCount][];
                stringCSVData = new String[recordCount];
                
                // start at beginning of record set
                rs.beforeFirst();
                // loop through each record set and display attributes
                while (rs.next())
                {
                    // action depending on qryType
                    switch (qryType) 
                    {
                        // get all competitions data except competitionID
                        case "competition":
                            // create object array for each row
                            Object [] compResults = new Object[6];
                            compResults[0] = rs.getString("gameName");
                            compResults[1] = rs.getString("team1");
                            compResults[2] = rs.getInt("team1Points");
                            compResults[3] = rs.getString("team2");
                            compResults[4] = rs.getInt("team2Points");
                            compResults[5] = rs.getString("eventName");
                            // add the object array to the objDataSet (2D array)
                            objDataSet[counter] = compResults;
                            counter++;
                            break;

                        // get select data from competitions for leader board jTables
                        case "leaderBoard":
                            // create object array for each row
                            Object [] leaderBoard = new Object[3];
                            leaderBoard[0] = rs.getString("name");
                            leaderBoard[1] = rs.getInt("points");
                            leaderBoard[2] = rs.getString("eventName");
                            // add the object array to the objDataSet (2D array)
                            objDataSet[counter] = leaderBoard;
                            counter++;
                            break;
 
                        // get all events and put them into a string [] array    
                        case "event":
                            stringCSVData[counter] = rs.getString("name") + ","
                                    + formatDateToString(rs.getString("date")) + ","
                                    + rs.getString("location");
                            counter++;
                            break;
                            
                        // get all teams and put them into a string [] array              
                        case "team":
                            stringCSVData[counter] = rs.getString("name") + ","
                                    + rs.getString("contact") + ","
                                    + rs.getString("phone") + ","
                                    + rs.getString("email");
                            counter++;
                            break;
                            
                        // get all games and put them into a string [] array
                        case "game":
                            stringCSVData[counter] = rs.getString("name");
                            counter++;
                            break;
                            
                        default:
                            break;
                    }  
                }//end while loop
                
                // close the connection
                conn.close();
            }    
        }
        // catch SQL Exceptions
        catch (SQLException sqlE)
        {
            errorMessage += sqlE.getMessage();
        }
        // catch other exceptions
        catch (Exception e)
        {
            errorMessage += e.getMessage();
        }
    }// end constructor method
    
    
    /***************************************************************************
    Method:     getRecordCount()
    Purpose:    returns number of records (rows) from the SQL query
    Inputs:     void
    Outputs:    integer (recordCount)
    ***************************************************************************/
    public int getRecordCount()
    {
        return recordCount;
    }
    
    /***************************************************************************
    Method:     getErrorMessage()
    Purpose:    returns String containing error messages
    Inputs:     void
    Outputs:    String (errorMessage)
    ***************************************************************************/
    public String getErrorMessage()
    {
        return errorMessage;
    } 
    
    /***************************************************************************
    Method:     getObjDataSet()
    Purpose:    used primarily for setting up jTables with row data
    Inputs:     void
    Outputs:    Object[][] (objDataSet)
    ***************************************************************************/
    public Object[][] getObjDataSet()
    {
        return objDataSet;
    }

    /***************************************************************************
    Method:     getStringCSVData()
    Purpose:    returns string array for populating jComboBoxes
    Inputs:     void
    Outputs:    String[] (stringCSVData)
    ***************************************************************************/
    public String[] getStringCSVData()
    {
        return stringCSVData;
    }

    /***************************************************************************
    Method:     formatDateToString()
    Purpose:    returns String version fo a formatted date
    Inputs:     formatted date e.g "2022-03-01"
    Outputs:    String version e.g "01-Mar-2022"
    ***************************************************************************/
    public static String formatDateToString(String inputDateString)
    {
        // create date string version
        String formattedDateStr;
        String day = inputDateString.substring(8, 10);
        String year = inputDateString.substring(0, 4);
        String month = "Jan";
        String monthNbr = inputDateString.substring(5, 7);
        switch (monthNbr)
        {
            case "02":
                month = "Feb";
                break;
            case "03":
                month = "Mar";
                break;
            case "04":
                month = "Apr";
                break;
            case "05":
                month = "May";
                break;
            case "06":
                month = "Jun";
                break;
            case "07":
                month = "Jul";
                break;
            case "08":
                month = "Aug";
                break;
            case "09":
                month = "Sep";
                break;
            case "10":
                month = "Oct";
                break;
            case "11":
                month = "Nov";
                break;
            case "12":
                month = "Dec";
                break;
            default:
                break;
        }
        
        formattedDateStr = day + "-" + month + "-" + year;
        
        return formattedDateStr;
    }
    
    /***************************************************************************
    Method:     toString() --- override from Object class toString() method
    Purpose:    returns String version of database URL, USR ID and USR PWD
    Inputs:     void
    Outputs:    String
    ***************************************************************************/
    @Override
    public String toString()
    {
        return "Database URL = " + dbURL + " USR ID = " + usrID + " USR PWD = " + usrPWD;
    } 
    
}
