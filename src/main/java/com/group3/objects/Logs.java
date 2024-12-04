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

public class Logs {
    private String logFilePath;

    public Logs() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm");
        LocalDateTime timestamp = LocalDateTime.now();
        this.logFilePath = timestamp.format(formatter) + "_logs.txt";
    }

    public void logControl(ControlDevice controlDevice) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime timestamp = LocalDateTime.now();
        String entry = timestamp.format(formatter) + " Control Device Name: " + controlDevice.getDeviceName() + " Control Device Target Value and Unit: " + controlDevice.getTargetValue() + " " + controlDevice.getUnit() + " Control Device Current Value and Unit: " + controlDevice.getCurrentValue() + " " + controlDevice.getUnit();
        writeLogToFile(entry);

    }

    public void logAllSensors(List<Sensor> sensors) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime timestamp = LocalDateTime.now();
        for (Sensor sensor : sensors) {
            String entry = timestamp.format(formatter) + " Sensor Name: " + sensor.getName() + " Sensor Value and Unit: " + sensor.getValue() + " " + sensor.getUnit();
            writeLogToFile(entry);
        }
    }

    private void writeLogToFile(String entry) {

        try (BufferedWriter writeToFile = new BufferedWriter(new FileWriter(logFilePath, true))) {

            writeToFile.write(entry);
            writeToFile.newLine();

        } catch (IOException e) {
            System.err.println("Error writing log entry to file: " + e.getMessage());
        }

    }

}

