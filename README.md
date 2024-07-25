# GC E-Sports Competition App 2
## Description
This application helps manage competition data effectively using a user-friendly GUI. The app interacts with a SQL relational database to manage teams, matches, and results.

<p align="center">
  <img src="https://github.com/LukeWait/gc-esports-app2/raw/main/src/images/gc-esports-app2-screenshot.png" alt="App Screenshot" width="600">
</p>

## Table of Contents
- [Installation](#installation)
- [Usage](#usage)
- [Development](#development)
- [Testing](#testing)
- [License](#license)
- [Acknowledgments](#acknowledgments)
- [Source Code](#source-code)
- [Dependencies](#dependencies)

## Installation
### Executable
#### Windows
1. Download the latest Windows release from the [releases page](https://github.com/LukeWait/gc-esports-app/releases).
2. Extract the contents to a desired location.
3. Run the `GCEsportsApp2.jar` file.

### From Source
To install and run the application from source:
1. Clone the repository:
    ```sh
    git clone https://github.com/LukeWait/gc-esports-app2.git
    cd gc-esports-app
    ```

2. Install Project Dependencies:
    Ensure JDK 17 is installed. You can download it from [Oracle](https://www.oracle.com/java/technologies/javase-downloads.html).

3. Open with Apache Netbeans or other compatible IDE:

## Usage
The application manages competition data through a SQL database:
- `goldcoast_esports.sql`

### Managing SQL Data
1. Ensure the .sql is imported to an accesible database server (XAMPP).
2. Launch the application.
3. Use the provided options to edit, update, or view the data.

## Development
### Project Structure
```sh
gc-esports-app/
├── nbproject/
├── src/
│   ├── images/
│   ├── data/
│   └── gcesportsapp2/
│       ├── GCEsportsApp2.java
│       ├── GCEsportsApp2.form
│       ├── DbRead.java
│       └── DbWrite.java
├── build.xml
└── manifest.mf
```

## License
This project is licensed under the MIT License. See the LICENSE file for details.

## Acknowledgments
This project was developed as part of an assignment at TAFE Queensland for subject ICTPRG430.

App requirements and boilerplate code provided by Hans Telford.

## Source Code
The source code for this project can be found in the GitHub repository: [https://github.com/LukeWait/gc-esports-app](https://www.github.com/LukeWait/gc-esports-app).

## Dependencies
- JDK 17
