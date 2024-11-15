package com.group3;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.group3.objects.Sensor;
import com.group3.objects.Simulator;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.collections.FXCollections;
import javafx.scene.control.Slider;

import static javafx.application.Platform.exit;

public class PrimaryController {

    ControlDevice coolantValve = new ControlDevice("Coolant Valve", 0d, 100d, 50d, "Percent");
    ControlDevice coolantPump = new ControlDevice("Coolant Pump", 0d, 100d, 50d, "Percent");
    ControlDevice controlRods = new ControlDevice("Control Rods", 0d, 100d, 75d, "Percent");
    ControlDevice steamRate = new ControlDevice("Steam Rate", 0d, 100d, 10d, "Percent");
    ControlDevice corePressureBlowOff = new ControlDevice("Core Pressure Blow Off Valve", 0d, 100d, 0d, "Percent");
    List<ControlDevice> controls;

    Simulator simulator;

    @FXML
    private Slider coolPump;
    @FXML
    private Slider coolValve;
    @FXML
    private Slider controlRod;
    @FXML
    private Slider steam;
    @FXML
    private Slider pressure;

    @FXML
    private ListView<String> sensorList;

    ObservableList<String> sensorData;

    @FXML
    private Button exit;
    @FXML
    private Button run;

    @FXML
    private void initialize() {
        simulator = new Simulator();
        controls = new ArrayList<>();
    sensorData = FXCollections.observableArrayList("Sensor 1", "Sensor 2");
    sensorList.setItems(sensorData);

    controls.add(coolantValve);
    controls.add(coolantPump);
    controls.add(controlRods);
    controls.add(steamRate);
    controls.add(corePressureBlowOff);

    coolValve.setValue(simulator.coolantValve.getCurrentValue());
    coolPump.setValue(simulator.coolantPump.getCurrentValue());
    controlRod.setValue(simulator.controlRods.getCurrentValue());
    steam.setValue(simulator.steamRate.getCurrentValue());
    pressure.setValue(simulator.corePressureBlowOff.getCurrentValue());
    }

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }
    @FXML
    private void onButtonClicked() throws IOException {
    System.out.println("Button is clicked");
    exit();
    }

    @FXML
    private void onRunButtonClicked() throws IOException {
        SimulateReactor();
    }

    @FXML
    private void SimulateReactor() throws IOException {

        List<Sensor> sensors;


        coolantValve.setCurrentValue(coolValve.getValue());
        coolantPump.setCurrentValue(coolPump.getValue());
        controlRods.setCurrentValue(controlRod.getValue());
        steamRate.setCurrentValue(steam.getValue());
        corePressureBlowOff.setCurrentValue(pressure.getValue());

        sensors = simulator.UpdateSimulator(controls);

        setList(sensors);
    }

    @FXML
    private void setList(List<Sensor> sensors) throws IOException {
        sensorList.getItems().clear();

        for (Sensor sensor : sensors) {
            sensorList.getItems().add(String.format("%.2f", sensor.getValue()));
        }

    }
}
