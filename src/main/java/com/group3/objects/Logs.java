package com.group3.objects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor

public class Logs {

    private String logFilePath;
    private List<LogEntry> logEntries;

    public Logs(String logFilePath) {

        List<LogEntry> logEntries = new ArrayList<>();
        this.logFilePath = logFilePath;
        this.logEntries = new ArrayList<>();
    }

    private record LogEntry(String actionType, LocalDateTime timestamp, List<String> sensorData) {

        @Override
        public String toString() {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formattedActionType = (actionType == null || actionType.isEmpty()) ? "N/A" : actionType;
            String formattedSensorData = (sensorData == null || sensorData.isEmpty()) ? "N/A" : String.join(", ", sensorData);
            return timestamp.format(formatter) + " | System Action: " + formattedActionType + " | Sensor Data: " + formattedSensorData;
        }
    }

    public void logOperatorAction(String actionType, List<String> sensorData) {

        LocalDateTime timestamp = LocalDateTime.now();
        List<String> newSensorData = (sensorData == null) ? new ArrayList<>() : sensorData;
        LogEntry newEntry = new LogEntry(actionType, timestamp, newSensorData);

        logEntries.add(newEntry);
        writeLogToFile(newEntry);

    }

    private void writeLogToFile(LogEntry entry) {

        try (BufferedWriter writeToFile = new BufferedWriter(new FileWriter(logFilePath, true))) {

            writeToFile.write(entry.toString());
            writeToFile.newLine();

        } catch (IOException e) {
            System.err.println("Error writing log entry to file: " + e.getMessage());
        }

    }

    public List<String> getLogEntries() {

        List<String> formattedEntries = new ArrayList<>();

        for (LogEntry entry : logEntries){

            formattedEntries.add(entry.toString());

        }

        return formattedEntries;

    }

    public void closeLogs() {

        logEntries.clear(); // clear memory
        System.out.println("Logs saved to file, system closing......");

    }

    /* // Testing logs -- main
    *  public static void main(String[] args) {
        Logs operatorLogs = new Logs("operator_logs.txt");

        List<String> sensorData1 = List.of("Temperature: 300°C", "Pressure: 1000 psi", "Coolant Flow: 45%", "Radiation Level: Low");
        operatorLogs.logOperatorAction("Startup", sensorData1);

        // Print all logs to console for verification
        List<String> allLogs = operatorLogs.getLogEntries();
        allLogs.forEach(System.out::println);

        // Close the logger to clear resources
        operatorLogs.closeLogs();
    }
    */

}

