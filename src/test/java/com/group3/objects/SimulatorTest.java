package com.group3.objects;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class SimulatorTest {
    Simulator sim;
    List<ControlDevice> controls = new ArrayList<>();

    @BeforeEach
    void setUp() {
        sim = new Simulator();
    }

    @Test
    void constructorInitializesValuesCorrectly() {
        assertThat(sim.getCoolantTemp()).isEqualTo(new Sensor("Coolant Temperature", 50d, 10d, 350d, "Celsius"));
        assertThat(sim.getCoolantLevel()).isEqualTo(new Sensor("Coolant Level", 200d, 0d, 350d, "Hectoliters"));
        assertThat(sim.getCoreTemp()).isEqualTo(new Sensor("Core Temperature", 300d, 0d, 12000d, "Celsius"));
        assertThat(sim.getPowerOutput()).isEqualTo(new Sensor("Power Output", 100d, 0d, 2000d, "MWh"));
        assertThat(sim.getCorePressure()).isEqualTo(new Sensor("Core Pressure", 2000d, 0d, 5000d, "kPa"));
        assertThat(sim.getRadiationLevel()).isEqualTo(new Sensor("Radiation Level", 0.1d, 0d, 500d, "mSv"));
        assertThat(sim.getControlRodPosition()).isEqualTo(new Sensor("Control Rod Position", 75d, 0d, 100d, "Percent"));
    }
    @Test
    void loadControl(){
        controls.add(new ControlDevice("Coolant Valve", 0d, 100d, 50d, "Percent"));
        controls.add(new ControlDevice("Coolant Pump", 0d, 100d, 50d, "Percent"));
        controls.add(new ControlDevice("Control Rods", 0d, 100d, 75d, "Percent"));
        controls.add(new ControlDevice("Steam Rate", 0d, 100d, 10d, "Percent"));
        controls.add(new ControlDevice("Core Pressure Blow Off Valve", 0d, 100d, 0d, "Percent"));
        sim.controlIO.writeControlData(controls);

        sim.controlIO.readControlData(controls);

        assertThat(sim.coolantValve.toString()).isEqualTo(new ControlDevice("Coolant Valve", 0d, 100d, 50d, "Percent").toString());
        assertThat(sim.coolantPump.toString()).isEqualTo(new ControlDevice("Coolant Pump", 0d, 100d, 50d, "Percent").toString());
        assertThat(sim.controlRods.toString()).isEqualTo(new ControlDevice("Control Rods", 0d, 100d, 75d, "Percent").toString());
        assertThat(sim.steamRate.toString()).isEqualTo(new ControlDevice("Steam Rate", 0d, 100d, 10d, "Percent").toString());
        assertThat(sim.corePressureBlowOff.toString()).isEqualTo(new ControlDevice("Core Pressure Blow Off Valve", 0d, 100d, 0d, "Percent").toString());
    }
    @Test
    void applyControls(){
        controls.add(new ControlDevice("Coolant Valve", 0d, 100d, 40d, "Percent"));
        controls.add(new ControlDevice("Coolant Pump", 0d, 100d, 40d, "Percent"));
        controls.add(new ControlDevice("Control Rods", 0d, 100d, 65d, "Percent"));
        controls.add(new ControlDevice("Steam Rate", 0d, 100d, 15d, "Percent"));
        controls.add(new ControlDevice("Core Pressure Blow Off Valve", 0d, 100d, 10d, "Percent"));

        sim.controlIO.writeControlData(controls);
        sim.controlIO.readControlData(controls);
        sim.UpdateSimulator();

        assertThat(sim.getCoolantTemp().getValue()).isBetween(10d, 350d);
        assertThat(sim.getCoolantLevel().getValue()).isBetween(0d, 350d);
        assertThat(sim.getCoreTemp().getValue()).isBetween(0d, 12000d);
        assertThat(sim.getPowerOutput().getValue()).isBetween(0d, 2000d);
        assertThat(sim.getCorePressure().getValue()).isBetween(0d, 5000d);
        assertThat(sim.getCoolantLevel().getValue()).isBetween(0d, 350d);
        assertThat(sim.getRadiationLevel().getValue()).isBetween(0d, 500d);
        assertThat(sim.getControlRodPosition().getValue()).isBetween(0d, 100d);
    }
    @Test
    void simulateMeltdown(){
        sim.controlRods.setCurrentValue(1d);
        sim.steamRate.setCurrentValue(0d);
        sim.coolantPump.setCurrentValue(0d);
        sim.coolantValve.setCurrentValue(0d);

        for(int i = 0; i < 10; i++){
            sim.HandleControlInput();
        }
        sim.PreventMeltdown();
        sim.reactorState = sim.CheckReactorState();

        assertThat(sim.reactorState).isNotEqualTo(ReactorState.MELTDOWN);
    }
    @Test
    void checkState(){
        sim.setCoreTemp(new Sensor("Core Temperature", 1200d, 0d, 12000d, "Celsius"));

        sim.CheckReactorState();

        assertThat(sim.reactorState).isEqualTo(ReactorState.MELTDOWN);
    }
    @Test
    void testUpdateSteamRateMin(){
        sim.steamRate.setCurrentValue(0);

        assertThat(sim.UpdateSteamRate()).isBetween(0d, 2000d);
    }
    @Test
    void testUpdateSteamRateMax(){
        sim.steamRate.setCurrentValue(100);

        assertThat(sim.UpdateSteamRate()).isBetween(0d, 2000d);
    }
    @Test
    void testUpdateCoolantMin(){
        sim.coolantValve.setCurrentValue(0);
        sim.coolantPump.setCurrentValue(0);

        assertThat(sim.getCoolantLevel().getValue()).isBetween(0d, 350d);
    }
    @Test
    void testUpdateCoolantMax(){
        sim.coolantValve.setCurrentValue(100);
        sim.coolantPump.setCurrentValue(100);

        assertThat(sim.getCoolantLevel().getValue()).isBetween(0d, 350d);
    }
    @Test
    void testUpdateControlRodsMin(){
        sim.controlRods.setCurrentValue(0);

        assertThat(sim.UpdateControlRods()).isEqualTo(120);
    }
    @Test
    void testUpdateControlRodsMax(){
        sim.controlRods.setCurrentValue(100);

        assertThat(sim.UpdateControlRods()).isEqualTo(0);
    }
    @Test
    void testUpdatePressureMin(){
        sim.steamRate.setCurrentValue(100);
        sim.getControlRodPosition().setValue(100d);
        sim.getCoreTemp().setValue(0d);
        sim.coolantValve.setCurrentValue(0);
        sim.coolantPump.setCurrentValue(0);
        for (int i = 0; i < 15; i++) {
            sim.UpdatePressure();
        }
        assertThat(sim.getCorePressure().getValue()).isEqualTo(0);
    }
    @Test
    void testUpdatePressureMax(){
        sim.steamRate.setCurrentValue(0);
        sim.getControlRodPosition().setValue(0d);
        sim.getCoreTemp().setValue(1200d);
        sim.coolantValve.setCurrentValue(100);
        sim.coolantPump.setCurrentValue(100);
        for (int i = 0; i < 15; i++) {
            sim.UpdatePressure();
        }
        assertThat(sim.getCorePressure().getValue()).isEqualTo(5000);
    }
}
