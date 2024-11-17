package com.group3;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class sensorIO {

    private String sensorFilePath;

    // constructor for file path
    public sensorIO(String sensorFilePath) {

        if(sensorFilePath == null || sensorFilePath.isEmpty()) {

            System.err.println("Sensor file path cannot be null.");

        }

        this.sensorFilePath = sensorFilePath;

    }

    // writing sensor data to a file/pass to simulator class

    public void writeSensorData (List<String> sensorData){

        if(sensorData == null || sensorData.isEmpty()) {

            System.err.println("Sensor data is null or empty..... Skipping file writing.");
            return;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(sensorFilePath, true))){

            for(String data : sensorData) {

                writer.write(data);
                writer.newLine();

            }
            System.out.println("Sensor data written to file");

        } catch (IOException e) {

            System.err.println("Error writing sensor data to file: " + e.getMessage());

        }

    }

    // reading sensor data from file

    public List<String> readSensorData() {

        List<String> sensorData = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(sensorFilePath))) {

            String line;
            while ((line = reader.readLine()) != null) {

                sensorData.add(line);

            }

        } catch (IOException e){

            System.err.println("Error reading sensor data from file: " + e.getMessage());

        }

        return sensorData;

    }

    // simulator will access this function to read sensor data

    public void simulatorSensorInput() {

        List<String> sensorData = readSensorData();
        System.out.println("Passing to simulator......");

    }

}

