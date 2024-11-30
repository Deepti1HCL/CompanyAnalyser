package org.example;

//import static org.junit.jupiter.api.Assertions.*;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import java.io.*;

import static org.junit.Assert.assertTrue;


public class CompanyAnalyzerTest {

    private CompanyAnalyzer analyzer;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @BeforeEach
    void setUp() throws IOException {
        // Set up the test analyzer instance
        analyzer = new CompanyAnalyzer();

        // Prepare a mock employee data CSV file (in-memory)
        String mockCSV = "123,Joe,Doe,60000,\n" +
                "124,Martin,Chekov,45000,121\n" +
                "125,Bob,Ronstad,47000,123\n" +
                "300,Alice,Hasacat,50000,124\n" +
                "121,Alice,Hasacat,50000,120\n" +
                "120,Alice,Hasacat,50000,123\n" +
                "305,Brett,Hardleaf,34000,300";

        // Write this mock data to a temporary file
        File tempFile = File.createTempFile("employees", ".csv");
        BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
        writer.write(mockCSV);
        writer.close();

        // Read the file into the analyzer
        analyzer.readEmployeesFromCSV(tempFile.getAbsolutePath());

        // Redirect system output to capture printed results
        System.setOut(new PrintStream(outContent));
    }

    @Test
    public void testAnalyzeManagersEarningLess() throws IOException {

        setUp();
        // Run analysis
        analyzer.analyzeManagers();

        // Assert that the output includes the manager who earns less than they should
        assertTrue(outContent.toString().contains("Manager Martin Chekov earns 45000.00 less than they should"));
    }

    @Test
    public void testAnalyzeManagersEarningMore() throws IOException {
        setUp();
        // Reset the output content for a clean state
        outContent.reset();

        // Run analysis
        analyzer.analyzeManagers();

        // Assert that the output includes the manager who earns more than they should
        assertTrue(outContent.toString().contains("Manager Alice Hasacat earns 50000.00 less than they should"));
    }

    @Test
    public void testAnalyzeReportingLines() throws IOException {
        setUp();
        // Reset the output content for a clean state
        outContent.reset();

        // Run analysis
        analyzer.analyzeReportingLines();

        // Assert that the output includes the employee with a long reporting line
        assertTrue(outContent.toString().contains("Employee 305 Brett Hardleaf has a reporting line which is too long: 6 managers"));
    }
}