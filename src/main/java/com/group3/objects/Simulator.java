package com.group3.objects;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import javafx.application.Platform;

@ToString
@Getter
@Setter
public class Simulator {
    private Sensor coolantTemp;
    private Sensor coolantLevel;
    private Sensor coreTemp;
    private Sensor powerOutput;
    private Sensor corePressure;
    private Sensor radiationLevel;
    private Sensor controlRodPosition;

    public ControlDevice coolantValve;
    public ControlDevice coolantPump;
    public ControlDevice controlRods;
    public ControlDevice steamRate;
    public ControlDevice corePressureBlowOff;

    public controlIO controlIO;
    public sensorIO sensorIO;

    public ReactorState reactorState;
    public boolean inAutoMode;

    public Simulator() {
        coolantTemp = new Sensor("Coolant Temperature", 50d, 10d, 350d, "Celsius");
        coolantLevel = new Sensor("Coolant Level", 200d, 0d, 350d, "Hectoliters");
        coreTemp = new Sensor("Core Temperature", 300d, 0d, 12000d, "Celsius");
        powerOutput = new Sensor("Power Output", 100d, 0d, 2000d, "MWh");
        corePressure = new Sensor("Core Pressure", 2000d, 0d, 5000d, "kPa");
        radiationLevel = new Sensor("Radiation Level", 0.1d, 0d, 500d, "mSv");
        controlRodPosition = new Sensor("Control Rod Position", 75d, 0d, 100d, "Percent");

        coolantValve = new ControlDevice("Coolant Valve", 0d, 100d, 50d, "Percent");
        coolantPump = new ControlDevice("Coolant Pump", 0d, 100d, 50d, "Percent");
        controlRods = new ControlDevice("Control Rods", 0d, 100d, 75d, "Percent");
        steamRate = new ControlDevice("Steam Rate", 0d, 100d, 10d, "Percent");
        corePressureBlowOff = new ControlDevice("Core Pressure Blow Off Valve", 0d, 100d, 0d, "Percent");

        reactorState = ReactorState.STABLE;
        inAutoMode = true;

        controlIO = new controlIO("controls.txt");
        sensorIO = new sensorIO("sensors.txt");
    }

    public void updateSimulator() {
        List<ControlDevice> controls = new ArrayList<>(5);
        List<Sensor> sensors = new ArrayList<>();

        controls.add(coolantPump);
        controls.add(coolantValve);
        controls.add(controlRods);
        controls.add(steamRate);
        controls.add(corePressureBlowOff);
        controls = controlIO.readControlData(controls);

        coolantPump.setCurrentValue(controls.get(0).getCurrentValue());
        coolantValve.setCurrentValue(controls.get(1).getCurrentValue());
        controlRods.setCurrentValue(controls.get(2).getCurrentValue());
        steamRate.setCurrentValue(controls.get(3).getCurrentValue());
        corePressureBlowOff.setCurrentValue(controls.get(4).getCurrentValue());

        handleControlInput();

        reactorState = checkReactorState();
        if (inAutoMode) {
            runAutoMode();
        }

        sensors.add(coolantTemp);
        sensors.add(coolantLevel);
        sensors.add(coreTemp);
        sensors.add(powerOutput);
        sensors.add(corePressure);
        sensors.add(radiationLevel);
        sensors.add(controlRodPosition);

        sensorIO.writeSensorData(sensors);
    }

    public void runAutoMode() {
        if (reactorState == ReactorState.MELTDOWN) {
            resetToStable();
        }
        switch (reactorState) {
            case MELTDOWN:
                Platform.runLater(() -> {
                    coolantValve.setTargetValue(100.0);   // Fully open coolant valve
                    coolantPump.setTargetValue(100.0);    // Maximize coolant pump
                    controlRods.setTargetValue(0.0);      // Fully insert control rods
                    steamRate.setTargetValue(10.0);       // Reduce steam output
                    corePressureBlowOff.setTargetValue(80.0); // Open blow-off valve to reduce pressure
                });
                break;

            case CRITICAL:
                Platform.runLater(() -> {
                    coolantValve.setTargetValue(80.0);
                    coolantPump.setTargetValue(90.0);
                    controlRods.setTargetValue(25.0);    // Insert rods more
                    steamRate.setTargetValue(15.0);      // Slightly lower steam output
                    corePressureBlowOff.setTargetValue(50.0); // Moderate blow-off pressure
                });
                break;

            case WARNING:
                System.out.println("Warning");
                Platform.runLater(() -> {
                    coolantValve.setTargetValue(60.0);
                    coolantPump.setTargetValue(70.0);
                    controlRods.setTargetValue(50.0);
                    steamRate.setTargetValue(20.0);      // Normal steam output
                    corePressureBlowOff.setTargetValue(30.0); // Minor blow-off
                });
                break;

            case STABLE:
                Platform.runLater(() -> {
                    coolantValve.setTargetValue(50.0);
                    coolantPump.setTargetValue(50.0);
                    controlRods.setTargetValue(75.0);    // Default position
                    steamRate.setTargetValue(30.0);      // Increase steam output slightly
                    corePressureBlowOff.setTargetValue(0.0); // Close blow-off valve
                });
                break;
        }
    }

    public void handleControlInput() {
        updateHeat(updateControlRods(), updateCoolant(), updateSteamRate());
        updatePressure();
    }

    public ReactorState checkReactorState() {
        if (coreTemp.getValue() > 1100)
            reactorState = ReactorState.MELTDOWN;
        else if (coreTemp.getValue() > 900)
            reactorState = ReactorState.CRITICAL;
        else if (coreTemp.getValue() > 600)
            reactorState = ReactorState.WARNING;
        else
            reactorState = ReactorState.STABLE;

        return reactorState;
    }

    public void resetToStable() {
        coolantTemp = new Sensor("Coolant Temperature", 50d, 10d, 350d, "Celsius");
        coolantLevel = new Sensor("Coolant Level", 200d, 0d, 350d, "Hectoliters");
        coreTemp = new Sensor("Core Temperature", 300d, 0d, 1500d, "Celsius");
        powerOutput = new Sensor("Power Output", 100d, 0d, 2000d, "MWh");
        corePressure = new Sensor("Core Pressure", 2000d, 0d, 5000d, "kPa");
        radiationLevel = new Sensor("Radiation Level", 0.1d, 0d, 500d, "mSv");
        controlRodPosition = new Sensor("Control Rod Position", 75d, 0d, 100d, "Percent");
    }

    public void updateHeat(double heatGenerated, double heatRemovedByCoolant, double heatRemovedBySteam) {
        double netHeatChange = heatGenerated - heatRemovedByCoolant - heatRemovedBySteam;

        coreTemp.setValue(Math.max(Math.min(coreTemp.getValue() + netHeatChange, coreTemp.getMaxValue()), coreTemp.getMinValue()));
        radiationLevel.setValue(coreTemp.getValue() / 14);
        coolantTemp.setValue(Math.min(coolantTemp.getValue() + heatGenerated * 0.4, coolantTemp.getMaxValue()));
    }

    public double updateCoolant() {
        double coolantRemovalFactor = 15d;

        if (coolantLevel.getValue() < coolantLevel.getMaxValue()) {
            double addedCoolant;
            addedCoolant = ((coolantLevel.getMaxValue() * (coolantPump.getCurrentValue() / 100) * (coolantValve.getCurrentValue() / 100)) * 0.7);
            coolantTemp.setValue(coolantTemp.getValue() - (addedCoolant * 0.05));
            coolantLevel.setValue(Math.min((addedCoolant + coolantLevel.getValue()), coolantLevel.getMaxValue()));
        }
        double coolantEfficiency = (coolantLevel.getValue() / coolantLevel.getMaxValue()) *
                (coolantPump.getCurrentValue() / 100) *
                (coolantValve.getCurrentValue() / 100);

        return coolantEfficiency * coolantRemovalFactor;
    }

    public double updateControlRods() {
        double reactivity = 120f;
        controlRodPosition.setValue(controlRods.getCurrentValue());
        return reactivity * (1 - (controlRodPosition.getValue() / 100));
    }

    public double updateSteamRate() {
        double deductedSteam = 0;
        double initialCoolant = coolantLevel.getValue();
        double steamHeatRemovalFactor = 0.8d;

        if (coreTemp.getValue() > 100d && corePressure.getValue() > 1000) {
            deductedSteam = coolantLevel.getValue() * (steamRate.getCurrentValue() / 100);
            coolantLevel.setValue(Math.max(coolantLevel.getValue() - deductedSteam, coolantLevel.getMinValue()));

            if (coolantLevel.getValue() > coolantLevel.getMinValue()) {
                powerOutput.setValue(deductedSteam * 2);
                coolantTemp.setValue(Math.max(coolantTemp.getValue() - (deductedSteam * 0.3), coolantTemp.getMinValue()));
                return deductedSteam * steamHeatRemovalFactor;
            } else {
                powerOutput.setValue(initialCoolant * 2);
                coolantTemp.setValue(Math.max(coolantTemp.getValue() - (initialCoolant * 0.3), coolantTemp.getMinValue()));
                return initialCoolant * steamHeatRemovalFactor;
            }
        } else {
            System.out.println("Reactor not hot enough for steam");
            powerOutput.setValue(0d);
            return 0;
        }
    }

    public void updatePressure() {
        double temperatureEffect = coreTemp.getValue() * 0.4;
        double coolantEffect = (coolantPump.getCurrentValue() / 100) * (coolantValve.getCurrentValue() / 100) * 2;
        double steamEffect = steamRate.getCurrentValue() * 0.8;
        double newPressure = temperatureEffect + coolantEffect - steamEffect;

        corePressure.setValue(Math.min(Math.max(corePressure.getValue() + newPressure, corePressure.getMinValue()), corePressure.getMaxValue()));

        if (corePressureBlowOff.getCurrentValue() > 0) {
            corePressure.setValue(corePressure.getValue() - (corePressure.getValue() * (corePressureBlowOff.getCurrentValue() / 100) * 0.4));
        }
    }

    public void printSensors() {
        System.out.println("Core Temperature: " + coreTemp.getValue());
        System.out.println("Core Pressure: " + corePressure.getValue());
        System.out.println("Current Rod Position: " + controlRodPosition.getValue());
        System.out.println("Power Output: " + powerOutput.getValue());
        System.out.println("Coolant Level: " + coolantLevel.getValue());
    }
}