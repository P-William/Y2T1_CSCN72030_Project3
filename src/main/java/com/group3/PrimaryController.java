package com.group3;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.group3.objects.*;
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
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import lombok.NonNull;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.effect.DropShadow;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ButtonBar;
import java.util.Optional;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

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

    private boolean criticalDialogShown = false;

    private MediaPlayer backgroundMusicPlayer;

    @FXML
    private Pane pane;
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
    private ToggleButton mode;
    @FXML
    private ListView<String> sensorList;

    ObservableList<String> sensorData;



    private Logs logs;
    private List<Double> prevTargetValues = new ArrayList<>();

    @FXML
    private ToggleButton pause;
    private Image pauseImage;
    private Image resumeImage;

    public Circle stateLightIndicator = new Circle(10);
    public DropShadow glow = new DropShadow();


    @FXML
    private void initialize() {
        initializeBackgroundMusic();

        // Create a shape to simulate the LED
        stateLightIndicator.setLayoutX(710); // Same X position
        stateLightIndicator.setLayoutY(255); // Same Y position

        // Add a DropShadow effect for the LED
        glow.setRadius(10);
        glow.setSpread(0.4);
        stateLightIndicator.setEffect(glow);

        pane.getChildren().add(stateLightIndicator);

        Image imageReactor = new Image(Objects.requireNonNull(getClass().getResourceAsStream("reactor_image.png")));
        imageViewAIImage.setImage(imageReactor);
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

        logs = new Logs();
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

        for(ControlDevice controlDevice : controls) {
            prevTargetValues.add(controlDevice.getTargetValue());
        }


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
                logData();
                if (simulator.reactorState == ReactorState.STABLE) {
                    criticalDialogShown = false;
                    state.setText("Reactor Sate: Stable");
                    state.setStyle("-fx-text-fill: #4B4B4B; -fx-font-size: 12px;");
                    Image imageReactorStable = new Image(Objects.requireNonNull(getClass().getResourceAsStream("reactor_image_stable.png")));
                    imageViewAIImage.setImage(imageReactorStable);
                    imageViewAIImage.setFitWidth(250);  // Set the desired width
                    imageViewAIImage.setFitHeight(250); // Set the desired height
                    imageViewAIImage.setPreserveRatio(true);
                    stateLightIndicator.setFill(Color.web("#03BE03")); // Green LED color
                    glow.setColor(Color.web("#0AC40A")); // Glow color matching the LED
                }
                else if (simulator.reactorState == ReactorState.WARNING)
                {
                    criticalDialogShown = false;
                    state.setText("Reactor Sate: Warning");
                    state.setStyle("-fx-text-fill: #4B4B4B; -fx-font-size: 12px;");
                    Image imageReactorWarning = new Image(Objects.requireNonNull(getClass().getResourceAsStream("reactor_image_warning.png")));
                    imageViewAIImage.setImage(imageReactorWarning);
                    stateLightIndicator.setFill(Color.web("#CCD402")); // Yellow LED color
                    glow.setColor(Color.web("#E2EB04")); // Glow color matching the LED
                }
                else if (simulator.reactorState == ReactorState.CRITICAL)
                {
                    state.setText("Reactor Sate: CRITICAL");
                    state.setStyle("-fx-text-fill: #4B4B4B; -fx-font-size: 12px;");
                    Image imageReactorCritical = new Image(Objects.requireNonNull(getClass().getResourceAsStream("reactor_image_critical.png")));
                    imageViewAIImage.setImage(imageReactorCritical);
                    stateLightIndicator.setFill(Color.web("#EF7D03")); // Orange LED color
                    glow.setColor(Color.web("#DB7202")); // Glow color matching the LED

                    // Show the critical dialog only if not already shown
                    if (!criticalDialogShown) {
                        criticalDialogShown = true;
                        timeline.stop();
                        Platform.runLater(this::showCriticalDialog);
                    }
                }
                else {
                    criticalDialogShown = false;
                    state.setText("Reactor State: MELTDOWN");
                    state.setStyle("-fx-text-fill: #4B4B4B; -fx-font-size: 12px;");
                    Image imageReactorMeltdown = new Image(Objects.requireNonNull(getClass().getResourceAsStream("reactor_image_meltdown.png")));
                    imageViewAIImage.setImage(imageReactorMeltdown);
                    stateLightIndicator.setFill(Color.web("#FF0000")); // Red LED color
                    glow.setColor(Color.web("#D30202")); // Glow color matching the LED

                    // Play meltdown sound effect
                    playMeltdownSound();
                }

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
                if (backgroundMusicPlayer.getStatus() == MediaPlayer.Status.PAUSED) {
                    backgroundMusicPlayer.play(); // Resume the music
                }
                ImageView newImageView = new ImageView(pauseImage);
                newImageView.setFitWidth(16); // Set image width
                newImageView.setFitHeight(16); // Set image height

                pause.setGraphic(newImageView);
                changeIfSlidersAreEnabled(true);

            } else {
                System.out.println("Pausing Reactor");
                timeline.pause();
                if (backgroundMusicPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
                    backgroundMusicPlayer.pause(); // Pause the music
                }
                ImageView newImageView = new ImageView(resumeImage);
                newImageView.setFitWidth(16); // Set image width
                newImageView.setFitHeight(16); // Set image height
                pause.setGraphic(newImageView);
                changeIfSlidersAreEnabled(false);
            }
        });

        mode.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                System.out.println("Auto Mode is on");
                mode.setText("Auto Mode");
                changeIfSlidersAreEnabled(false);

            } else {
                System.out.println("Manual Mode is on");
                mode.setText("Manual Mode");
                changeIfSlidersAreEnabled(true);
            }
        });
    }

    private void SimulateReactor() throws IOException {
        double increment = 1d;
        simulator.setReactorMode(mode.isSelected());
        coolantValve.setTargetValue(coolantValveGui.getValue());
        LblCoolantValveTargetValue.setText(coolantValve.getTargetValue() + " " + coolantValve.getUnit());
        LblCoolantValveCurrentValue.setText(coolantValve.getCurrentValue() + " " + coolantValve.getUnit());
        coolantPump.setTargetValue(coolantPumpGui.getValue());
        LblCoolantPumpTargetValue.setText(coolantPump.getTargetValue() + " " + coolantPump.getUnit());
        LblCoolantPumpCurrentValue.setText(coolantPump.getCurrentValue() + " " + coolantPump.getUnit());
        controlRods.setTargetValue(controlRodGui.getValue());
        LblControlRodsTargetValue.setText(controlRods.getTargetValue() + " " + controlRods.getUnit());
        LblControlRodsCurrentValue.setText(controlRods.getCurrentValue() + " " + controlRods.getUnit());
        steamRate.setTargetValue(steamLevelGui.getValue());
        LblSteamOutputTargetValue.setText(steamRate.getTargetValue() + " " + steamRate.getUnit());
        LblSteamOutputCurrentValue.setText(steamRate.getCurrentValue() + " " + steamRate.getUnit());
        corePressureBlowOff.setTargetValue(pressureValveGui.getValue());
        LblPressureValveTargetValue.setText(corePressureBlowOff.getTargetValue() + " " + corePressureBlowOff.getUnit());
        LblPressureValveCurrentValue.setText(corePressureBlowOff.getCurrentValue() + " " + corePressureBlowOff.getUnit());

        for(ControlDevice controlDevice : controls) {

            controlDevice.adjustCurrentValue(increment);
        }

        simulator.controlIO.writeControlData(controls);
        simulator.UpdateSimulator();
        sensors = simulator.sensorIO.readSensorData();

        Platform.runLater(() -> {

        });

        setList(sensors);
    }

    public void logData()
    {
        for(Sensor sensor : sensors) {
            sensorData.add(sensor.toString());
        }

        for(int i = 0; i < controls.size(); i++) {
            if(controls.get(i).getTargetValue() != prevTargetValues.get(i)) {
                logs.logControl(controls.get(i));
                prevTargetValues.set(i, controls.get(i).getTargetValue());
            }
        }

        logs.logAllSensors(sensors);
    }

    @FXML
    private void setList(@NonNull List<Sensor> sensors) throws IOException {
        sensorList.getItems().clear();

        for (Sensor sensor : sensors) {
            sensorList.getItems().add(String.format("%.2f", sensor.getValue()));
        }
    }

    private void changeIfSlidersAreEnabled(boolean isEnabled)
    {
        coolantValveGui.setDisable(!isEnabled);
        coolantPumpGui.setDisable(!isEnabled);
        controlRodGui.setDisable(!isEnabled);
        steamLevelGui.setDisable(!isEnabled);
        pressureValveGui.setDisable(!isEnabled);
    }

    private void showCriticalDialog() {
        // Create the alert dialog
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Reactor Critical Warning");
        alert.setHeaderText("The reactor has entered CRITICAL mode!");
        alert.setContentText("Please choose an action:");

        // Create custom buttons
        ButtonType buttonAutoMode = new ButtonType("Switch to Auto Mode");
        ButtonType buttonFixYourself = new ButtonType("Continue in Manual Mode");
        ButtonType buttonCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

        // Add the buttons to the alert
        alert.getButtonTypes().setAll(buttonAutoMode, buttonFixYourself, buttonCancel);

        // Show the dialog and wait for user input
        Optional<ButtonType> result = alert.showAndWait();

        // Handle the user's choice
        if (result.isPresent()) {
            if (result.get() == buttonAutoMode) {
                System.out.println("Switching to Auto Mode...");
                mode.setText("Auto Mode");
                changeIfSlidersAreEnabled(false);
                mode.setSelected(true);
                timeline.play();
            } else if (result.get() == buttonFixYourself) {
                System.out.println("Continue with user control...");
                timeline.play();
            } else {
                System.out.println("No action taken.");
            }
        }
    }

    private void initializeBackgroundMusic() {
        try {
            Media backgroundMusic = new Media(Objects.requireNonNull(getClass().getResource("background_music.wav")).toExternalForm());
            backgroundMusicPlayer = new MediaPlayer(backgroundMusic);
            backgroundMusicPlayer.setCycleCount(MediaPlayer.INDEFINITE); // Loop the music
            backgroundMusicPlayer.setVolume(0.5); // Set volume (0.0 to 1.0)
            backgroundMusicPlayer.play(); // Start playing
        } catch (Exception e) {
            System.err.println("Error loading background music: " + e.getMessage());
        }
    }

    private void playMeltdownSound() {
        try {
            Media meltdownSound = new Media(Objects.requireNonNull(getClass().getResource("meltdown_sound.wav")).toExternalForm());
            MediaPlayer meltdownPlayer = new MediaPlayer(meltdownSound);
            meltdownPlayer.setVolume(1.0); // Full volume
            meltdownPlayer.play(); // Play the sound
        } catch (Exception e) {
            System.err.println("Error playing meltdown sound: " + e.getMessage());
        }
    }


}
