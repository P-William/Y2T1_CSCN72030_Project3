package com.group3;

import com.group3.ControlDevice;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class controlIO{

    private final String controlFilePath;

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
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(controlFilePath))) {

            oos.writeObject(controls);
            System.out.println("Control devices saved to file");

        } catch (IOException e) {

            System.err.println("Error saving devices: " + e.getMessage());

        }

    }

    // reading control data from file

    public List<ControlDevice> readControlData() {

        List<ControlDevice> controls = new ArrayList<>();

            try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(controlFilePath))) {

                controls = (List<ControlDevice>) ois.readObject();

            } catch (FileNotFoundException e){

                System.err.println("File not found: " + controlFilePath);

            } catch (IOException | ClassNotFoundException e) {
                System.err.println("Error loading control devices: " + e.getMessage());
            }

        return controls;
    }

    /*
    *   public static void main(String[] args) {

        controlIO controlIO = new controlIO("control_test.dat");

        List<ControlDevice> controlList = new ArrayList<>();
        controlList.add(new ControlDevice("Valve", 1.00,2.00, 3.00,"KW"));
        controlList.add(new ControlDevice("Sensor", 40.00, 100.00, 55.00, "PSI"));

        System.out.println("Writing control data...");
        controlIO.writeControlData(controlList);

        System.out.println("Reading control data...");
        List<ControlDevice> loadedControls = controlIO.readControlData();

        System.out.println("Loaded Controls:");
        *
        for (ControlDevice control : loadedControls) {
        *
            System.out.println(control);
            *
        }
    }
    * */

}
