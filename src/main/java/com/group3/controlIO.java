package com.group3;

import com.group3.ControlDevice;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class controlIO{

    private String controlFilePath;

    // Constructor to initialize file path

    public controlIO(String controlFilePath) {

        if(controlFilePath == null || controlFilePath.isEmpty()) {

            throw new IllegalArgumentException("Control file path cannot be null or empty.");

        }
        this.controlFilePath = controlFilePath;

    }

    // Method to write control data to file

    public void writeControlData(List<ControlDevice> controls){

        if(controls == null || controls.isEmpty()) {

            System.err.println("Controls are null or empty.");
            return;

        }

        for (ControlDevice control : controls) {

            try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(controlFilePath))) {

                oos.writeObject(controls);
                System.out.println("Control devices saved to file");

            } catch (IOException e) {

                System.err.println("Error saving devices: " + e.getMessage());

            }

        }

    }

    // reading control data from file

    public List<ControlDevice> readControlData() {

        List<ControlDevice> controls = new ArrayList<>();

        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(controlFilePath))) {

            return (List<ControlDevice>) ois.readObject();

        } catch (FileNotFoundException e){

            System.err.println("File not found: " + controlFilePath);

        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading control devices: " + e.getMessage());
        }

        return new ArrayList<>();
    }
    
}
