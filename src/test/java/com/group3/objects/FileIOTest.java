package com.group3.objects;

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.assertj.core.api.Assertions.*;

public class FileIOTest {

    @Test
    void sensorIOInitializesCorrectly() {

        String filePath = "sensorTest.txt";

        sensorIO sensorIO = new sensorIO(filePath);

        assertThat(sensorIO).isNotNull();
        assertThat(sensorIO.equals(filePath));
    }

    @Test
    void logsInitializesCorrectly() {

        String filePath = "testLogs.txt";

        Logs operatorLogs = new Logs(filePath);

        assertThat(operatorLogs.getLogEntries().isEmpty());
        assertThat(operatorLogs.getLogFilePath()).isEqualTo(filePath);

    }

    @Test
    void controlIOInitializesCorrectly() {

        String filePath = "controlTest.txt";

        controlIO controlIO = new controlIO(filePath);

        assertThat(controlIO).isNotNull();
        assertThat(controlIO.equals(filePath));

    }

    @Test
    void verifySensorDataWriteAndRead() {

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
    void verifyControlDataWriteAndRead() {

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
    void verifyLogEntryFormat() {

        String filePath = "operatorTest.txt";
        Logs operatorLogs = new Logs(filePath);

        List<String> sensorData = List.of("\"Temperature: 300°C\", \"Pressure: 1000 psi\", \"Coolant Flow: 45%\", \"Radiation Level: Low\"");

        operatorLogs.logOperatorAction("Startup", sensorData);
        List<String> logEntries = operatorLogs.getLogEntries();

        assertThat(logEntries).isNotEmpty();
        String logEntry = logEntries.get(0);

        assertThat(logEntry).contains("System Action: Startup");
        assertThat(logEntry).contains("Temperature: 300°C");
        assertThat(logEntry).contains("Pressure: 1000 psi");
        assertThat(logEntry).contains("Coolant Flow: 45%");
        assertThat(logEntry).contains("Radiation Level: Low");

    }

}
