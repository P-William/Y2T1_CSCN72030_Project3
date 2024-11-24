package com.group3.objects;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class sensorIO {

    private String sensorFilePath;

    // constructor for file path
    public sensorIO(String sensorFilePath) {

        if(sensorFilePath == null || sensorFilePath.isEmpty()) {

            throw new IllegalArgumentException("Sensor file path cannot be null or empty.");

        }
        this.sensorFilePath = sensorFilePath;
    }

    // writing sensor data to a file/pass to simulator class

    public void writeSensorData (List<Sensor> sensorData){

       if(sensorData == null || sensorData.isEmpty()) {

           System.err.println("List is null or empty.");
           return;

       }

       try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(sensorFilePath))) {

           oos.writeObject(sensorData);
           System.out.println("Sensors successfully saved to " + sensorFilePath);

       } catch (IOException e) {

           System.err.println("Error saving sensors: " + e.getMessage());

       }

    }

    // reading sensor data from file

    public List<Sensor> readSensorData() {

       List<Sensor> sensors = new ArrayList<>();

       try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(sensorFilePath))) {

           Object objSensor = ois.readObject();

           if(objSensor instanceof List<?>) {

               List<?> loadedList = (List<?>) objSensor;
               for (Object item : loadedList) {

                   if(item instanceof Sensor) {

                       sensors.add((Sensor) item);

                   } else {

                       System.err.println("Invalid object in sensor list: " + item);

                   }

               }

           } else {

               System.err.println("Invalid data format in sensor file.");

           }

       } catch(FileNotFoundException e) {

           System.err.println("File not found: " + sensorFilePath);

       } catch (IOException | ClassNotFoundException e) {

           System.err.println("Error loading the sensors: " + e.getMessage());

       }

       return sensors;

    }

    private void validateSensorAttributes(Sensor sensor) {

        if(sensor.getValue() < sensor.getMinValue() || sensor.getValue() > sensor.getMaxValue()) {

            throw new IllegalArgumentException("Sensor value is out of range.");

        }
        if(sensor.getName() == null || sensor.getName().isEmpty()) {

            throw new IllegalArgumentException("Sensor name is invalid.");

        }
        if(sensor.getUnit() == null || sensor.getUnit().isEmpty()) {

            throw new IllegalArgumentException("Sensor unit is invalid.");

        }

    }

    // Validates the sensor data list

    public List<Sensor> validateSensors(List<Sensor> sensors) {

        if(sensors == null || sensors.isEmpty()) {

            System.err.println("Sensor list is null or empty..... proceeding with default values.");
            return new ArrayList<>();

        }

        List<Sensor> validatedSensors = new ArrayList<>();

        for(Sensor sensor : sensors) {

            if(sensor != null) {
                try {

                    validateSensorAttributes(sensor);
                    validatedSensors.add(sensor);

                } catch (IllegalArgumentException e) {

                    System.err.println("Validation failed for sensor: " + e.getMessage());

                }

            } else {

                System.err.println("Found null sensor.....skipping.");

            }

        }

        return validatedSensors;

    }

    /*
    * public static void main(String[] args) {
        String sensorFilePath = "sensors_test.dat";

        List<Sensor> sensors = new ArrayList<>();
        sensors.add(new Sensor("Temperature", 200.0, 50.0, 250.0, "C"));
        sensors.add(new Sensor("Pressure", 100.0, 20.0, 150.0, "PSI"));

        sensorIO sensorIO = new sensorIO(sensorFilePath);

        sensorIO.writeSensorData(sensors);

        List<Sensor> loadedSensors = sensorIO.readSensorData();


        System.out.println("Loaded Sensors:");

        for (Sensor sensor : loadedSensors) {

            System.out.println(sensor);

        }
    }
    * */

}

