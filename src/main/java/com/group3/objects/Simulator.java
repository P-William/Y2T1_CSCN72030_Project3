package com.group3.objects;

import com.group3.ControlDevice;

import java.util.ArrayList;
import java.util.List;
import java.util.function.IntPredicate;

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

private ReactorState reactorState;
private boolean reactorMode;

public Simulator() {
    coolantTemp = new Sensor("Coolant Temperature", 50d, 10d, 350d, "Celsius");
    coolantLevel = new Sensor("Coolant Level", 200d, 0d, 350d, "Hectoliters");
    coreTemp = new Sensor("Core Temperature", 300d, 0d, 12000d, "Celsius");
    powerOutput = new Sensor("Power Output", 500d, 0d, 2000d, "MWh");
    corePressure = new Sensor("Core Pressure", 2000d, 0d, 5000d, "kPa");
    radiationLevel = new Sensor("Radiation Level", 0.1d, 0d, 500d, "mSv");
    controlRodPosition = new Sensor("Control Rod Position", 75d, 0d, 100d, "Percent");

    coolantValve = new ControlDevice("Coolant Valve", 0d, 100d, 50d, "Percent");
    coolantPump = new ControlDevice("Coolant Pump", 0d, 100d, 50d, "Percent");
    controlRods = new ControlDevice("Control Rods", 0d, 100d, 75d, "Percent");
    steamRate = new ControlDevice("Steam Rate", 0d, 100d, 10d, "Percent");
    corePressureBlowOff = new ControlDevice("Core Pressure Blow Off Valve", 0d, 100d, 0d, "Percent");

    reactorState = ReactorState.Stable;
    reactorMode = true;
}

public List<Sensor> UpdateSimulator(List<ControlDevice> controls){
    List<Sensor> sensors = new ArrayList<Sensor>();

    coolantPump.setCurrentValue(controls.get(0).getCurrentValue());
    coolantValve.setCurrentValue(controls.get(1).getCurrentValue());
    controlRods.setCurrentValue(controls.get(2).getCurrentValue());
    steamRate.setCurrentValue(controls.get(3).getCurrentValue());
    corePressureBlowOff.setCurrentValue(controls.get(4).getCurrentValue());

    HandleControlInput();

    CheckReactorState();
    if (reactorMode && reactorState == ReactorState.Critical){
        PreventMeltdown();
    }

    sensors.add(coolantTemp);
    sensors.add(coolantLevel);
    sensors.add(coreTemp);
    sensors.add(powerOutput);
    sensors.add(corePressure);
    sensors.add(radiationLevel);
    sensors.add(controlRodPosition);

    return sensors;
}
//List<ControlDevice> LoadControls()

private void HandleControlInput() {
    // set reactors new core temp
    UpdateControlRods();



    //update the new amount of coolant coming in
    UpdateCoolant();

// update the amount of coolant turning to power and increase core temp
    UpdateSteamRate();

}
public ReactorState CheckReactorState(){
    if (coreTemp.getValue() > 1100)
        reactorState = ReactorState.Meltdown;
    else if (coreTemp.getValue() > 900)
        reactorState = ReactorState.Critical;
    else if (coreTemp.getValue() > 600)
        reactorState = ReactorState.Warning;
    else
        reactorState = ReactorState.Stable;

    return reactorState;
}

private void PreventMeltdown(){

}

private void UpdateCoolant(){
if (coolantLevel.getValue() < coolantLevel.getMaxValue()){
    double temp = 0d;

    //add to coolant using pump rate and flow rate percentages
    temp += (coolantLevel.getMaxValue() * ((coolantPump.getCurrentValue() / 100)) * (coolantValve.getCurrentValue() / 100));

    //either set the coolant value to be the maximum or whatever the calculated value is (should prevent coolant level being over max)
    coolantLevel.setValue(Math.min((temp + coolantLevel.getValue()), coolantLevel.getMaxValue() - 1));

    //System.out.println("Core Temp Before: " + coreTemp.getValue());
    coreTemp.setValue( Math.max((Math.min(coreTemp.getValue() - (temp * 4), coreTemp.getMaxValue() -1)), coreTemp.getMinValue()+ 1));
    //System.out.println("Core Temp After: " + coreTemp.getValue());
}
}

private void UpdateControlRods(){
    // controlRodPosition is proportionate to controlRods so auto apply new value
    controlRodPosition.setValue(controlRods.getCurrentValue());

    //before coolant reductions core temp is directly controlled by control rods
    //core temp ranges 0 to 1200 or 12 times the 0 to 100 scale of the control rods
    coreTemp.setValue(controlRods.getCurrentValue() * 12);
}

private void UpdateSteamRate(){
    double deductedSteam = 0;
    double initialCoolant = coolantLevel.getValue();
    //100 is temp because pressure shenanigans
    if (coreTemp.getValue() > 100d){

        //deduct coolant level by the percentage of steam flow rate?
        deductedSteam = coolantLevel.getValue() * (steamRate.getCurrentValue() / 100);
        //if the result would be less then the min just set it to the min to avoid exception
        coolantLevel.setValue(Math.max(coolantLevel.getValue() - deductedSteam, coolantLevel.getMinValue()));

        //if the resulting coolant is above 0 then power is equivalent to the steam deducted
        if (coolantLevel.getValue() > coolantLevel.getMinValue()){
            powerOutput.setValue(deductedSteam);
            // for every hectoliter of coolant removed add 4 degrees to core temp
            coreTemp.setValue(coreTemp.getValue() + (deductedSteam));
        } else {//otherwise we only deducted what was already there
            powerOutput.setValue(initialCoolant);
            coreTemp.setValue(coreTemp.getValue() + (initialCoolant * 4));
        }
    }
    else {
        System.out.println("Reactor not hot enough for steam");
        powerOutput.setValue(1d);
    }
}

private void UpdatePressure(){
    if (corePressureBlowOff.getCurrentValue() > 0){
        //might need to switch to subtract from max value so we are subtracting what percent we want from the max
        corePressure.setValue(corePressure.getValue() - (corePressure.getValue() * (corePressureBlowOff.getCurrentValue() / 100)));
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


