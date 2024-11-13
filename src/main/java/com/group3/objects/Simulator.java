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
    controlRodPosition = new Sensor("Control Rod Position", 50d, 0d, 100d, "Percent");

    coolantValve = new ControlDevice("Coolant Valve", 0d, 100d, 50d, "Percent");
    coolantPump = new ControlDevice("Coolant Pump", 0d, 100d, 50d, "Percent");
    controlRods = new ControlDevice("Control Rods", 0d, 100d, 50d, "Percent");
    steamRate = new ControlDevice("Steam Rate", 0d, 100d, 50d, "Percent");
    corePressureBlowOff = new ControlDevice("Core Pressure Blow Off Valve", 0d, 100d, 50d, "Percent");

    reactorState = ReactorState.Stable;
    reactorMode = true;
}

public void UpdateSimulator(){

    HandleControlInput();
}
//List<ControlDevice> LoadControls()

private void HandleControlInput(){

}

private void UpdateCoolantValve(){

}

}


