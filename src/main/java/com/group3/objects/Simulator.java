package com.group3.objects;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

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
public boolean reactorMode;

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
    reactorMode = true;

    controlIO = new controlIO("controls.txt");
    sensorIO = new sensorIO("sensors.txt");
}

public void UpdateSimulator() {

    List<ControlDevice> controls = new ArrayList<>(5);
    List<Sensor> sensors = new ArrayList<Sensor>();

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

    HandleControlInput();

    reactorState =  CheckReactorState();
    if (reactorMode && reactorState == ReactorState.MELTDOWN) {
        PreventMeltdown();
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

public void HandleControlInput() {

    UpdateHeat(UpdateControlRods(), UpdateCoolant(), UpdateSteamRate());

    UpdatePressure();

}
public ReactorState CheckReactorState(){
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

public void PreventMeltdown(){//this is just to make it work for now
    coolantTemp = new Sensor("Coolant Temperature", 50d, 10d, 350d, "Celsius");
    coolantLevel = new Sensor("Coolant Level", 200d, 0d, 350d, "Hectoliters");
    coreTemp = new Sensor("Core Temperature", 300d, 0d, 1500d, "Celsius");
    powerOutput = new Sensor("Power Output", 100d, 0d, 2000d, "MWh");
    corePressure = new Sensor("Core Pressure", 2000d, 0d, 5000d, "kPa");
    radiationLevel = new Sensor("Radiation Level", 0.1d, 0d, 500d, "mSv");
    controlRodPosition = new Sensor("Control Rod Position", 75d, 0d, 100d, "Percent");
}

public void UpdateHeat(double heatGenerated, double heatRemovedByCoolant, double heatRemovedBySteam){

    double netHeatChange = heatGenerated - heatRemovedByCoolant - heatRemovedBySteam;

    coreTemp.setValue(Math.max(Math.min(coreTemp.getValue() + netHeatChange, coreTemp.getMaxValue()), coreTemp.getMinValue()));
    radiationLevel.setValue(coreTemp.getValue() / 14);
    coolantTemp.setValue(Math.min(coolantTemp.getValue() + heatGenerated * 0.4, coolantTemp.getMaxValue()));
}

public double UpdateCoolant(){
    double coolantRemovalFactor = 15d;

if (coolantLevel.getValue() < coolantLevel.getMaxValue()) {
    double addedCoolant;

    //add to coolant using pump rate and flow rate percentages
    addedCoolant = ((coolantLevel.getMaxValue() * ((coolantPump.getCurrentValue() / 100)) * (coolantValve.getCurrentValue() / 100)) * 0.7);
    coolantTemp.setValue(coolantTemp.getValue() - (addedCoolant * 0.05));
    //either set the coolant value to be the maximum or whatever the calculated value is (should prevent coolant level being over max)
    coolantLevel.setValue(Math.min((addedCoolant + coolantLevel.getValue()), coolantLevel.getMaxValue()));
}
    double coolantEfficiency = (coolantLevel.getValue() / coolantLevel.getMaxValue()) *
            (coolantPump.getCurrentValue() / 100) *
            (coolantValve.getCurrentValue() / 100);

    return coolantEfficiency * coolantRemovalFactor;
}

public double UpdateControlRods(){
    double reactivity = 120f;
    // controlRodPosition is proportionate to controlRods so auto apply new value
    controlRodPosition.setValue(controlRods.getCurrentValue());

    //before coolant reductions core temp is directly controlled by control rods
    //core temp ranges 0 to 1200 or 12 times the 0 to 100 scale of the control rods
    return reactivity * (1 - (controlRodPosition.getValue() / 100));
}

public double UpdateSteamRate(){
    double deductedSteam = 0;
    double initialCoolant = coolantLevel.getValue();
    double steamHeatRemovalFactor = 0.8d;

    //100 is temp because pressure shenanigans
    if (coreTemp.getValue() > 100d && corePressure.getValue() > 1000){

        //deduct coolant level by the percentage of steam flow rate?
        deductedSteam = coolantLevel.getValue() * (steamRate.getCurrentValue() / 100);
        //if the result would be less then the min just set it to the min to avoid exception
        coolantLevel.setValue(Math.max(coolantLevel.getValue() - deductedSteam, coolantLevel.getMinValue()));

        //if the resulting coolant is above 0 then power is equivalent to the steam deducted
        if (coolantLevel.getValue() > coolantLevel.getMinValue()){
            powerOutput.setValue(deductedSteam * 2);
            // for every hecto liter of coolant removed add 4 degrees to core temp
            coolantTemp.setValue(Math.max(coolantTemp.getValue() - (deductedSteam * 0.3), coolantTemp.getMinValue()));
            return deductedSteam * steamHeatRemovalFactor;

        } else {//otherwise we only deducted what was already there
            powerOutput.setValue(initialCoolant * 2);
            coolantTemp.setValue(Math.max(coolantTemp.getValue() - (initialCoolant * 0.3), coolantTemp.getMinValue()));
            return initialCoolant * steamHeatRemovalFactor;
        }
    }
    else {
        System.out.println("Reactor not hot enough for steam");
        powerOutput.setValue(0d);
        return 0;
    }
}

public void UpdatePressure(){
    // Pressure increases with temperature
    double temperatureEffect = coreTemp.getValue() * 0.4;

    // Pressure increases inversely with coolant level
    double coolantEffect = (coolantPump.getCurrentValue()/ 100) * (coolantValve.getCurrentValue() / 100) * 2;

    // Pressure decreases with steam rate
    double steamEffect = steamRate.getCurrentValue() * 0.8;

    // Total pressure change
    double newPressure = temperatureEffect + coolantEffect - steamEffect;

    corePressure.setValue(Math.min(Math.max(corePressure.getValue() + newPressure, corePressure.getMinValue()), corePressure.getMaxValue()));

    if (corePressureBlowOff.getCurrentValue() > 0){
        //might need to switch to subtract from max value so we are subtracting what percent we want from the max
        corePressure.setValue(corePressure.getValue() - (corePressure.getValue() * (corePressureBlowOff.getCurrentValue() / 100) * 0.4));
    }
}

public void PrintSensors(){
    System.out.println("Core Temperature: " + coreTemp.getValue());
    System.out.println("Core Pressure: " + corePressure.getValue());
    System.out.println("Current Rod Position: " + controlRodPosition.getValue());
    System.out.println("Power Output: " + powerOutput.getValue());
    System.out.println("Coolant Level: " + coolantLevel.getValue());
}
}


