# Gold Coast E-Sports Competition App 2
## Description
This application helps manage competition data effectively using a user-friendly GUI. The app interacts with a SQL relational database to manage teams, matches, and results.

<p align="center">
  <img src="https://github.com/LukeWait/gc-esports-app2/raw/main/src/images/gc-esports-app2-screenshot.png" alt="App Screenshot" width="600">
</p>

## Table of Contents
- [Installation](#installation)
- [Usage](#usage)
- [Development](#development)
- [License](#license)
- [Acknowledgments](#acknowledgments)
- [Source Code](#source-code)
- [Dependencies](#dependencies)

## Installation
### Prerequisites
Ensure that Java JDK 17 is installed on your system:
- **Windows**: Download and install [Java JDK 17](https://www.oracle.com/java/technologies/javase-jdk17-downloads.html).
- **Linux**: Install Java JDK 17 using your package manager:
  ```sh
  sudo apt-get install openjdk-17-jdk   # For Debian-based systems
  ```

### Set Up MySQL Server
- **Windows**:
  1. Download the MySQL installer from the [MySQL Community Downloads page](https://dev.mysql.com/downloads/mysql/).
  2. Run the installer and follow the prompts to complete the installation.
  3. During installation, you can choose to run the MySQL Server as a Windows service. Otherwise you can start it manually from the Services app or by running:
    ```sh
    net start mysql
    ```

- **Linux**:
  Download the package and run the service:
  ```sh
  sudo apt-get install mysql-server  # For Debian-based systems
  sudo systemctl start mysql
  sudo systemctl status mysql   # Check if the service is running
  ```

#### Import the Database
Once the SQL server is running, you can import the `gc_esports.sql` file to set up the database. Replace `username` (default: `root`) and `path/to/gc_esports.sql` with your MySQL username and the path to the SQL dump file:
```sh
mysql -u username -p < path/to/gc_esports.sql
```

#### Configure the `db_access.config` File
The `db_access.config` file contains the connection string for the database service. It is currently set to use the default MySQL credentials running on localhost/127.0.0.1. Ensure these settings match your database credentials for proper application functionality.:
```sh
dbURL=jdbc:mysql://localhost:3306/gc_esports?serverTimezone=UTC
usrID=root
usrPWD=
```

#### Alternative SQL Server Options
You can opt for a web server solution like **XAMPP** or **WAMP** for ease of use. These solutions come with tools like phpMyAdmin, providing a GUI for importing and managing databases.
- **XAMPP**: [Download XAMPP](https://www.apachefriends.org/index.html)
- **WAMP**: [Download WAMP](http://www.wampserver.com/en/)

### Executable
1. Download the latest release from the [releases page](https://github.com/LukeWait/gc-esports-app2/releases).
2. Extract the contents to a desired location.
3. Run the `gc-esports-app2.jar` file:
   - **Windows**: Simply double-click the `gc-esports-app2.jar` file to run it.
   - **Linux**: Make the `.jar` file executable and run it:
     ```sh
     chmod +x gc-esports-app2.jar
     java -jar gc-esports-app2.jar
     ```

### From Source
To install and run the application from source:
1. Clone the repository:
    ```sh
    git clone https://github.com/LukeWait/gc-esports-app2.git
    cd gc-esports-app2
    ```
2. Open the project directory with Apache NetBeans or another compatible IDE to build and run the application.

## Usage
1. Ensure the gc_esports database is running on an accessible server with the correct access details entered in `db_access.config`.
2. Launch the application.
3. Use the provided functions to interact with the data:
   - Create and view competition and event data.
   - Create, view, and update team data.
   - Note: There is currently no option to delete data.

### Application Functions
- **Display Competition Results**: View results for all competitions, with the option to filter by event or team.
- **Export Competition Results**: Export the displayed competition results as a CSV file.
- **Display Leaderboard**: Show a leaderboard for the entire competition or a single event based on the selected filter.
- **Export Leaderboard**: Export the currently displayed leaderboard as a CSV file.
- **Add New Competition Results**: Input and save results for new competitions.
- **Add New Team**: Register a new team along with their contact information.
- **Update Existing Team**: Modify the details of an already registered team.
- **Add New Event**: Create and save details for a new event within the competition.

## Development
This project was developed using Apache NetBeans, an integrated development environment (IDE) that facilitates Java application development. If using a different IDE, you may need to configure the environment to ensure compatibility with the project.

### Project Structure
```sh
gc-esports-app2/
├── nbproject/                # NetBeans settings
├── data/                     # CSV export files
├── lib/                      # JAR libraries
├── sql/                      # SQL database schema
├── src/
│   ├── images/               # GUI design elements
│   └── gcesportsapp2/        # Project source code
│       ├── GCEsportsApp2.java
│       ├── GCEsportsApp2.form
│       ├── DbRead.java
│       └── DbWrite.java
├── build.xml                 # Build configuration
├── manifest.mf               # Manifest file for the JAR
└── db_access.config          # Database connection details
```

### SQL Database Structure
```
gc_esports Database
└── Tables:
    ├── event
    │   ├── name (PK)
    │   ├── date
    │   └── location
    │
    ├── game
    │   ├── name (PK)
    │   └── type
    │
    ├── team
    │   ├── name (PK)
    │   ├── contact
    │   ├── phone
    │   └── email
    │
    └── competition
        ├── competitionID (PK)
        ├── eventName (FK -> event.name)
        ├── gameName (FK -> game.name)
        ├── team1 (FK -> team.name)
        ├── team2 (FK -> team.name)
        ├── team1Points
        └── team2Points
```

### Creating New Releases
- **Build the Application**: Use your IDE to compile and package the application into a `.jar` executable, which will create a `dist` folder containing the final distributable files.
- **Create `data` Directory**: Create an empty directory called `data` in the `dist` folder for potential CSV exports.
- **Include `db_access.config` File**: Copy the config file into the `dist` folder, as it is required to establish a database connection.
- **Include `gc_access.sql` File**: You may want to include the SQL dump in the `dist` folder so users can re-create the database.

## License
This project is licensed under the MIT License. See the LICENSE file for details.

## Acknowledgments
This project was developed as part of an assignment at TAFE Queensland for subject ICTPRG430.

Project requirements and initial GUI design/codebase provided by Hans Telford.

## Source Code
The source code for this project can be found in the GitHub repository: [https://github.com/LukeWait/gc-esports-app2](https://www.github.com/LukeWait/gc-esports-app2).

## Dependencies
- Java JDK 17
- MySQL Connector/J 8.0.28 (included in project files)
