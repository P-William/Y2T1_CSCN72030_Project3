package com.group3.objects;

import com.group3.ControlDevice;

public class Simulator {
private Sensor coolantTemp;
private Sensor coolantLevel;
private Sensor coreTemp;
private Sensor powerOutput;
private Sensor corePressure;
private Sensor radiationLevel;
private Sensor controlRodPosition;

private ControlDevice coolantValve;
private ControlDevice coolantPump;
private ControlDevice controlRods;
private ControlDevice steamRate;
private ControlDevice corePressureBlowOff;

private ReactorState reactorState;
private boolean reactorMode;

public Simulator() {
    coolantTemp = new Sensor("Coolant Temperature", 50d, 10d, 350d, "Celsius");
    coolantLevel = new Sensor("Coolant Level", 200d, 0d, 350d, "Hectoliters");
    coreTemp = new Sensor("Core Temperature", 300d, 0d, 1200d, "Celsius");
    powerOutput = new Sensor("Power Output", 500d, 0d, 2000d, "MWh");
    corePressure = new Sensor("Core Pressure", 2000d, 0d, 5000d, "kPa");
    radiationLevel = new Sensor("Radiation Level", 0.1d, 0d, 500d, "mSv");
    controlRodPosition = new Sensor("Control Rod Position", 75d, 0d, 100d, "Percent");

    coolantValve = new ControlDevice("Coolant Valve", 0d, 100d, 50d, "Percent");
    coolantPump = new ControlDevice("Coolant Pump", 0d, 100d, 50d, "Percent");
    controlRods = new ControlDevice("Control Rods", 0d, 100d, 75d, "Percent");
    steamRate = new ControlDevice("Steam Rate", 0d, 100d, 50d, "Percent");
    corePressureBlowOff = new ControlDevice("Core Pressure Blow Off Valve", 0d, 100d, 50d, "Percent");

    reactorState = ReactorState.Stable;
    reactorMode = true;
}

public void UpdateSimulator(){

    HandleControlInput();

    if (reactorMode && reactorState == ReactorState.Critical){
        PreventMeltdown();
    }
}
//List<ControlDevice> LoadControls()

private void HandleControlInput(){

    UpdateControlRods();

    UpdateSteamRate();
}

private void PreventMeltdown(){

}

private void UpdateCoolantValve(){

}

private void UpdateControlRods(){
    // controlRodPosition is proportionate to controlRods so auto apply new value
    controlRodPosition.setValue(controlRods.getCurrentValue());

    //before coolant reductions core temp is directly controlled by control rods
    //core temp ranges 0 to 1200 or 12 times the 0 to 100 scale of the control rods
    coreTemp.setValue(controlRods.getCurrentValue() * 12);
}

private void UpdateSteamRate(){
    //100 is temp because pressure shenanigans
    if (coreTemp.getValue() > 100){
        //deduct coolant level by the percentage of steam flow rate?
        coolantLevel.setValue(coolantLevel.getValue() * (steamRate.getCurrentValue() / 100));
    }
}
}


