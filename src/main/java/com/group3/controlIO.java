package com.group3;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class controlIO{

    private String controlFilePath;

    // Constructor to initialize file path

    public controlIO(String controlFilePath) {

        if(controlFilePath == null || controlFilePath.isEmpty()) {

            System.err.println("Control file path cannot be null or empty.");

        }
        this.controlFilePath = controlFilePath;

    }

    // Method to write control data to file

    public void writeControlData(List<String> controlData){

        if (controlData == null || controlData.isEmpty()) {

            System.err.println("Control data is empty.....skipping write.");
            return;

        }

        List<String> validatedData = validateControlData(controlData);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(controlFilePath, true))) {

            for(String data : validatedData) {

                writer.write(data);
                writer.newLine();

            }
            System.out.println("Successfully written to file.");

        } catch (IOException e) {

            System.err.println("Error writing control data to file " + e.getMessage());

        }

    }

    // reading control data from file

    public List<String> readControlData() {

        List<String> controlData = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(controlFilePath))) {

            String line;
            while ((line = reader.readLine()) != null){

                controlData.add(line);

            }

        } catch (IOException e) {

            System.err.println("Error reading data from file: " + e.getMessage());

        }

        return controlData;

    }

    // Validating control data

    private List<String> validateControlData(List<String> controlData) {

        if (controlData == null || controlData.isEmpty()) {

            System.err.println("Control data is null or empty..... Proceeding with default values.");
            return new ArrayList<>(); // Maintaining continuity with empty list

        }
        // Filter null entries
        return controlData.stream().filter(Objects::nonNull).toList();

    }

    // Passing control data to the simulator

    public void simulatorControlInput() {

        List<String> controlData = readControlData();
        System.out.println("Passing control data to simulator.");

    }
    
}
