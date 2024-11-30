package com.group3;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.group3.objects.ControlDevice;
import com.group3.objects.Logs;
import com.group3.objects.Sensor;
import com.group3.objects.Simulator;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import lombok.NonNull;

public class PrimaryController {

    private ControlDevice coolantValve = new ControlDevice("Coolant Valve", 0d, 100d, 50d, "Percent");
    private ControlDevice coolantPump = new ControlDevice("Coolant Pump", 0d, 100d, 50d, "Percent");
    private ControlDevice controlRods = new ControlDevice("Control Rods", 0d, 100d, 75d, "Percent");
    private ControlDevice steamRate = new ControlDevice("Steam Rate", 0d, 100d, 10d, "Percent");
    private ControlDevice corePressureBlowOff = new ControlDevice("Core Pressure Blow Off Valve", 0d, 100d, 0d, "Percent");

    private List<ControlDevice> controls;
    private List<Sensor> sensors;

    private Timeline timeline;

    private Simulator simulator;

    @FXML
    private ImageView imageViewAIImage;

    @FXML
    private Slider coolantValveGui;
    @FXML
    private Label LblCoolantValveTargetValue;
    @FXML
    private Label LblCoolantValveCurrentValue;
    @FXML
    private Slider coolantPumpGui;
    @FXML
    private Label LblCoolantPumpTargetValue;
    @FXML
    private Label LblCoolantPumpCurrentValue;
    @FXML
    private Slider controlRodGui;
    @FXML
    private Label LblControlRodsTargetValue;
    @FXML
    private Label LblControlRodsCurrentValue;
    @FXML
    private Slider steamLevelGui;
    @FXML
    private Label LblSteamOutputTargetValue;
    @FXML
    private Label LblSteamOutputCurrentValue;
    @FXML
    public Slider pressureValveGui;
    @FXML
    private Label LblPressureValveTargetValue;
    @FXML
    private Label LblPressureValveCurrentValue;
    @FXML
    private Label state;
    @FXML
    private ListView<String> sensorList;

    ObservableList<String> sensorData;
    private Logs logs;

    @FXML
    private ToggleButton pause;
    private Image pauseImage;
    private Image resumeImage;

    @FXML
    private void initialize() {
        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("reactor_image.png")));
        imageViewAIImage.setImage(image);
        // Set the button size
        pause.setMinSize(32, 32);  // Minimum size
        pause.setPrefSize(32, 32); // Preferred size
        pause.setMaxSize(32, 32);  // Maximum size
        pauseImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("pause.png")));
        resumeImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("resume.png")));
        ImageView imageView = new ImageView(pauseImage);
        imageView.setFitWidth(16); // Set image width
        imageView.setFitHeight(16); // Set image height
        pause.setGraphic(imageView);

        // Center the image in the button
        pause.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        pause.setAlignment(Pos.CENTER); // Center the graphic within the button

        logs = new Logs("logs.txt");
        simulator = new Simulator();
        controls = new ArrayList<>();
        sensors = new ArrayList<>();
        sensorData = FXCollections.observableArrayList();
        sensorList.setItems(sensorData);

        controls.add(coolantValve);
        controls.add(coolantPump);
        controls.add(controlRods);
        controls.add(steamRate);
        controls.add(corePressureBlowOff);

        coolantValveGui.setValue(simulator.coolantValve.getCurrentValue());
        coolantValveGui.valueProperty().addListener((observable, oldValue, newValue) -> {
            coolantValveGui.setValue(Math.round(newValue.doubleValue()));
        });

        coolantPumpGui.setValue(simulator.coolantPump.getCurrentValue());
        coolantPumpGui.valueProperty().addListener((observable, oldValue, newValue) -> {
            coolantPumpGui.setValue(Math.round(newValue.doubleValue()));
        });

        controlRodGui.setValue(simulator.controlRods.getCurrentValue());
        controlRodGui.valueProperty().addListener((observable, oldValue, newValue) -> {
            controlRodGui.setValue(Math.round(newValue.doubleValue()));
        });

        steamLevelGui.setValue(simulator.steamRate.getCurrentValue());
        steamLevelGui.valueProperty().addListener((observable, oldValue, newValue) -> {
            steamLevelGui.setValue(Math.round(newValue.doubleValue()));
        });

        pressureValveGui.setValue(simulator.corePressureBlowOff.getCurrentValue());
        pressureValveGui.valueProperty().addListener((observable, oldValue, newValue) -> {
            pressureValveGui.setValue(Math.round(newValue.doubleValue()));
        });

       //this is the thread that is run every 0.25 seconds
        timeline = new Timeline(new KeyFrame(Duration.seconds(2), event -> {
            try {
                SimulateReactor();
                state.setText("Reactor Sate: "+String.valueOf(simulator.reactorState).toLowerCase());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);//set the timeline to loop forever/until the window is closed

        timeline.play();
        pause.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                System.out.println("Resuming Reactor");
                timeline.play();
                ImageView newImageView = new ImageView(pauseImage);
                newImageView.setFitWidth(16); // Set image width
                newImageView.setFitHeight(16); // Set image height
                pause.setGraphic(newImageView);

            } else {
                System.out.println("Pausing Reactor");
                timeline.pause();
                ImageView newImageView = new ImageView(resumeImage);
                newImageView.setFitWidth(16); // Set image width
                newImageView.setFitHeight(16); // Set image height
                pause.setGraphic(newImageView);
            }
        });
    }

    private void SimulateReactor() throws IOException {
        double increment = 1d;
        coolantValve.setTargetValue(coolantValveGui.getValue());
        LblCoolantValveTargetValue.setText(Double.toString(coolantValve.getTargetValue()) + " " + coolantValve.getUnit());
        LblCoolantValveCurrentValue.setText(Double.toString(coolantValve.getCurrentValue()) + " " + coolantValve.getUnit());
        coolantPump.setTargetValue(coolantPumpGui.getValue());
        LblCoolantPumpTargetValue.setText(Double.toString(coolantPump.getTargetValue()) + " " + coolantPump.getUnit());
        LblCoolantPumpCurrentValue.setText(Double.toString(coolantPump.getCurrentValue()) + " " + coolantPump.getUnit());
        controlRods.setTargetValue(controlRodGui.getValue());
        LblControlRodsTargetValue.setText(Double.toString(controlRods.getTargetValue()) + " " + controlRods.getUnit());
        LblControlRodsCurrentValue.setText(Double.toString(controlRods.getCurrentValue()) + " " + controlRods.getUnit());
        steamRate.setTargetValue(steamLevelGui.getValue());
        LblSteamOutputTargetValue.setText(Double.toString(steamRate.getTargetValue()) + " " + steamRate.getUnit());
        LblSteamOutputCurrentValue.setText(Double.toString(steamRate.getCurrentValue()) + " " + steamRate.getUnit());
        corePressureBlowOff.setTargetValue(pressureValveGui.getValue());
        LblPressureValveTargetValue.setText(Double.toString(corePressureBlowOff.getTargetValue()) + " " + corePressureBlowOff.getUnit());
        LblPressureValveCurrentValue.setText(Double.toString(corePressureBlowOff.getCurrentValue()) + " " + corePressureBlowOff.getUnit());

        for(ControlDevice controlDevice : controls) {

            controlDevice.adjustCurrentValue(increment);
        }

        simulator.controlIO.writeControlData(controls);
        simulator.UpdateSimulator();
        sensors = simulator.sensorIO.readSensorData();

        List<String> sensorData = new ArrayList<>();

        for(Sensor sensor : sensors) {
            sensorData.add(sensor.toString());
        }

        for(ControlDevice controlDevice : controls) {
            logs.logOperatorAction(controlDevice.toString(), sensorData);
        }

        Platform.runLater(() -> {

        });

        setList(sensors);
    }

    @FXML
    private void setList(@NonNull List<Sensor> sensors) throws IOException {
        sensorList.getItems().clear();

        for (Sensor sensor : sensors) {
            sensorList.getItems().add(String.format("%.2f", sensor.getValue()));
        }
    }


}
