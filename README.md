# The cumulative vesting schedule

## Introduction

Carta helps companies manage and understand ownership through a capitalization table (cap table), which lists
shareholders, their shares, purchase prices, and ownership percentages. Maintaining an accurate cap table is
challenging, and many are often inaccurate. When companies grant equity awards to employees, these shares typically vest
over time, encouraging employees to stay longer. A vesting schedule outlines the total equity vested over time.

## Objective

Create a command-line program that reads a file of vesting events and outputs a vesting schedule to stdout.
The program will do the following:
    
1) Accept a positional filename argument to read event data
2) Accept a second positional target date argument to calculate the total number of shares vested on or before that date

An automated tool will validate the correctness of the submission, so strict adherence to the specification is necessary.

## Solution

Project Info

     Java (23 SDK) program developed in IntelliJ
     Sample vestingSample.csv is used for testing the application

The following 2 classes were created for this program using Java
    
    VestingSchedule: Main class that performs the operations and has the code logic
    VestingInfo: Object that holds all the properties for the Vesting Information

Thought Process

- We need to have a way to parse the file efficiently in terms of memory so I selected BufferedReader which uses
    stream processing and can handle multiple threads in case of multi-threading (Future use case)
- Create an Object class for VestingInfo and use a HashMap which is an efficient, fast data structure to store the 
      parsed Objects from the CSV
- Use 3 seperate methods for the following: Processing the files before or on the dates into the HashMap, calculate
  the vesting shares and outputting the vesting info based on the following order (employeeId and awardId)
- For error handling I had made sure to add a method to exit the program and log the exceptions and give the detailed 
error reason so it can be investigated

How to run program: 
- In intelliJ, import the Java project and pass program arguments: 

        vestingSample.csv 2021-01-01 OR
-  In terminal, download any latest java jdk and add as a path in your environment variables bin folder and cd to the root location for the project inside source folder and run the following (Windows Instructions, for different OS the slashes should only differ):
        
        javac *.java (compiles all the java files)
        java VestingSchedule ../filename date precision(optional)
        java VestingSchedule ../vestingSample.csv 2021-01-01
        java VestingSchedule ../vestingSample.csv 2021-01-01 4




## Future implementation
Based on the size, frequency and time allocated towards the program being used we can make the following optimizations:
- Create a list of files with its corresponding parameters that can be processed in parallel using multi-threading 
    buffered stream 
- Instead of System.out.printf, use a Logger that can level different levels (INFO, SEVERE) so we can prioritize and better organize   
the output
- Add more validations for user input incorrectness and accessibility 
- Add JUnit tests and integration tests
- Seperation for folder structure for object and main method in different file
- Use a repository source tool and CI/CD pipeline for cross collaboration and efficency for code changes
- Transform the application to Spring Boot to have a server running for provide faster code execution and allow multi user support
- Build a UI component to provide a better experience for user 
  - 1 page application with 2 box panels (input and output)
  - Clear, upload file and submit button
  
