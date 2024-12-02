package com.group3.objects;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import static org.assertj.core.api.Assertions.*;

public class FileIOTest {

    @Test
    void sensorIOInitializesCorrectlyFLR2() {

        String filePath = "testSensors.txt";

        sensorIO sensorIO = new sensorIO(filePath);

        assertThat(sensorIO).isNotNull();

        assertThat(sensorIO.equals(filePath));

    }

    @Test
    void logsInitializesCorrectlyFLR3() {

        Logs logs = new Logs();

        assertThat(logs).isNotNull();
        assertThat(logs.getLogFilePath()).matches("\\d{4}-\\d{2}-\\d{2}_\\d{2}-\\d{2}_logs.txt");

    }

    @Test
    void controlIOInitializesCorrectlyFLR2() {

        String filePath = "controlTest.txt";

        controlIO controlIO = new controlIO(filePath);

        assertThat(controlIO).isNotNull();
        assertThat(controlIO.equals(filePath));

    }

    @Test
    void verifySensorDataWriteAndReadFLR1FLR3() {

        String filePath = "sensorTest.txt";
        sensorIO sensorIO = new sensorIO(filePath);
        List<Sensor> sensors = List.of(new Sensor("Temperature", 200.0, 50.0, 250.0, "C"));

        sensorIO.writeSensorData(sensors);
        List<Sensor> readSensors = sensorIO.readSensorData();

        assertThat(readSensors).isNotEmpty();
        assertThat(readSensors.get(0).getName()).isEqualTo("Temperature");
        assertThat(readSensors.get(0).getValue()).isEqualTo(200.0);

    }

    @Test
    void verifyControlDataWriteAndReadFLR1FLR3() {

        String filePath = "controlTest.txt";
        controlIO controlIO = new controlIO(filePath);

        List<ControlDevice> controlDevices = List.of(new ControlDevice("Valve", 0.0, 100.0, "PSI"));

        controlIO.writeControlData(controlDevices);

        List<ControlDevice> readDevices = controlIO.readControlData(controlDevices);

        assertThat(readDevices).isNotEmpty();
        assertThat(readDevices.get(0).getDeviceName()).isEqualTo("Valve");
        assertThat(readDevices.get(0).getMinValue()).isEqualTo(0.0);
        assertThat(readDevices.get(0).getMaxValue()).isEqualTo(100.0);
        assertThat(readDevices.get(0).getUnit()).isEqualTo("PSI");

    }

    @Test
    void verifyLogEntryFormatFLR3() {

       Logs logs = new Logs();

       ControlDevice controlDevice = new ControlDevice("Pump", 0.0, 100,"Litres");

       logs.logControl(controlDevice);

       File logFile = new File(logs.getLogFilePath());
       assertThat(logFile).exists();

       try (BufferedReader reader = new BufferedReader(new FileReader(logFile))) {

           String logEntry = reader.readLine();

           assertThat(logEntry).isNotNull();
           assertThat(logEntry).matches("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}.*");
           assertThat(logEntry).contains("Control Device Name: ");
           assertThat(logEntry).contains("Control Device Target Value and Unit: ");
           assertThat(logEntry).contains("Control Device Current Value and Unit: ");

       } catch (IOException e) {

           System.err.println("ERROR: Test failed: " + e.getMessage());

       }

    }

}
