# BIG COMPANY Organizational Structure Analysis

This project is designed to analyze the organizational structure of BIG COMPANY by processing employee data from a CSV file. The company wants to ensure that the salaries of managers align with company policies, and they are also concerned with the length of reporting lines between employees and the CEO. This program checks the following:

1. **Manager Salary Compliance**:
   - Managers should earn between 20% to 50% more than the average salary of their direct subordinates.
   - The program reports managers who earn less than they should, as well as those who earn more than they should.

2. **Reporting Line Length**:
   - The program identifies employees with more than 4 managers between them and the CEO, which results in a reporting line considered too long.

## Tech Stack

- **Java SE**:  Java SE 17 version.
- **JUnit**: Junit 4.13.+ version.
- **Maven**: Maven version is 3+.
- **CSV Input**: The program reads employee data from a CSV file.

## Project Setup

### 1. Clone the Repository

To get started, clone the repository to your local machine:


git clone <repository-url>
cd <project-directory>
### 2. Maven Setup
Ensure Maven is installed on your system. If not, follow the installation instructions on Maven's official site.

### 3. Build the Project
Once you've cloned the repository and set up Maven, run the following command to build the project:

bash

mvn clean install
### 4. Running the Program
To run the application, execute the following command:


Copy code
mvn exec:java -Dexec.mainClass="org.example.CompanyAnalyzer"
This command will read the employee data from the input CSV file, process it, and output the analysis results to the console.

### 5. Testing
To run the unit tests for the application:

bash
Copy code
mvn test
This will execute all unit tests written using JUnit.

CSV File Format
The program expects the input CSV file to have the following format:

**Id   FirstName   LastName   Salary   ManagerId**

123      Joe         Doe      60000

124	   Martin	  Chekov      45000	     123

125	   Bob	    Ronstad	    47000	    123

300	   Alice	    Hasacat	    50000	    124

305	  Brett	    Hardleaf	34000	       300


Id: A unique identifier for the employee.
FirstName: Employee's first name.
LastName: Employee's last name.
Salary: Employee's salary.
ManagerId: The ID of the employee's manager (empty for the CEO).

Program Output
The program will output the following types of reports:

Managers who earn less than they should:

List of managers and the amount by which their salary is less than the required amount.
Managers who earn more than they should:

List of managers and the amount by which their salary exceeds the maximum allowed.
Employees with too long a reporting line:

List of employees who have more than 4 managers between them and the CEO, along with the length of their reporting line.
Assumptions
The program assumes that the CSV file is well-formed and adheres to the format described above.
It assumes that the CEO has no manager and their ManagerId field is empty.
The program calculates average salaries for direct subordinates only (i.e., it does not consider indirect subordinates).
The program considers only the direct reporting chain between employees and the CEO.
How the Program Works
Data Reading: The program reads the CSV file and creates a list of employees, where each employee has a unique ID, salary, and potentially a manager.
Manager Compliance Check: For each manager, it calculates the average salary of their direct subordinates and checks whether their salary is within the allowed range (20% to 50% more).
Reporting Line Check: The program checks the reporting line length for each employee by tracing their manager chain up to the CEO. If there are more than 4 managers in the chain, the employee is flagged.
Output: After processing the data, the program outputs a report with the results.
Example Output


Managers who earn less than they should:
Manager <Firstname>, <lastName>, earns <Salary> less than they should by <less Amount>

Managers who earn more than they should:
Manager <Firstname>, <lastName>, earns <Salary> more than they should by <exceed Amount>

Employees with too long a reporting line:
Employee <ID>, <Firstname>, <lastName> has a reporting line which is too long : <length>
