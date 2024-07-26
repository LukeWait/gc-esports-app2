/*******************************************************************************
File name: DbWrite.java
Description:    1.  Make a connection to the gc_esports database
                        - a connection string(s) is read in from an external config file
                          containing the database URL, user id and password
                        - academic exercise only, not recommended security practice
                2.  Execute a write-only SQL query to insert or update a record
Version: 1.2.0
Author: ┬  ┬ ┬┬┌─┌─┐┬ ┬┌─┐╦╔╦╗
        │  │ │├┴┐├┤ │││├─┤║ ║
        ┴─┘└─┘┴ ┴└─┘└┴┘┴ ┴╩ ╩
Date: September 27, 2022
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
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DbWrite 
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
    private String errorMessage;
    
    
    /***************************************************************************
    Method:     DbWrite() constructor method
    Purpose:    initialise private data fields
                connect to database
                run SQL write statements (UPDATE, INSERT, DELETE)
    Inputs:     String sql (SQL statement)
    Outputs:    none
    ***************************************************************************/
    public DbWrite (String sql)
    {
        // initilise private data fields
        // error message string is used to store any exception messages
        errorMessage = "";
     
        // read external config file (app.config) which contains 3 lines of text
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
            stmt = conn.createStatement();
            // executeUpdate(sql) used for INSERT INTO, UPDATE and DELETE FROM statements
            stmt.executeUpdate(sql);
            // close the connection
            conn.close();
        }
        // catch SQL Exceptions
        catch (SQLException sqlE)
        {
            errorMessage += sqlE.getMessage() + "\n";
        }
        // catch other exception
        catch (Exception e)
        {
            errorMessage += e.getMessage() + "\n";
        }
    }// end constructor method
    
    
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
