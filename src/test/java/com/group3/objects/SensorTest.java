package com.group3.objects;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class SensorTest {

    @Test
    void constructorInitializesFieldsCorrectly() {
        Sensor sensor = new Sensor("Core Temperature", 25.0, 0.0, 100.0, "Celsius");

        assertThat(sensor.getName()).isEqualTo("Core Temperature");
        assertThat(sensor.getValue()).isEqualTo(25.0);
        assertThat(sensor.getMinValue()).isEqualTo(0.0);
        assertThat(sensor.getMaxValue()).isEqualTo(100.0);
        assertThat(sensor.getUnit()).isEqualTo("Celsius");
    }

    @Test
    void setValueWithinRangeUpdatesValue() {
        Sensor sensor = new Sensor("Core Temperature", 25.0, 0.0, 100.0, "Celsius");
        sensor.setValue(50.0);
        assertThat(sensor.getValue()).isEqualTo(50.0);
    }

    @Test
    void setValueBelowMinThrowsException() {
        Sensor sensor = new Sensor("Core Temperature", 25.0, 0.0, 100.0, "Celsius");

        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> sensor.setValue(-1.0))
            .withMessageContaining("Value is out of range");
    }

    @Test
    void setValueAboveMaxThrowsException() {
        Sensor sensor = new Sensor("Core Temperature", 25.0, 0.0, 100.0, "Celsius");
        assertThatThrownBy(() -> sensor.setValue(101.0))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("Value is out of range");
    }

    @Test
    void equalsReturnsTrueForEqualObjects() {
        Sensor sensor1 = new Sensor("Core Temperature", 25.0, 0.0, 100.0, "Celsius");
        Sensor sensor2 = new Sensor("Core Temperature", 25.0, 0.0, 100.0, "Celsius");
        assertThat(sensor1).isEqualTo(sensor2);
    }

    @Test
    void equalsReturnsFalseForDifferentObjects() {
        Sensor sensor1 = new Sensor("Core Temperature", 25.0, 0.0, 100.0, "Celsius");
        Sensor sensor2 = new Sensor("Humidity", 25.0, 0.0, 100.0, "Percent");
        assertThat(sensor1).isNotEqualTo(sensor2);
    }

    @Test
    void hashCodeReturnsSameValueForEqualObjects() {
        Sensor sensor1 = new Sensor("Core Temperature", 25.0, 0.0, 100.0, "Celsius");
        Sensor sensor2 = new Sensor("Core Temperature", 25.0, 0.0, 100.0, "Celsius");
        assertThat(sensor1).hasSameHashCodeAs(sensor2);
    }

    @Test
    void hashCodeReturnsDifferentValueForDifferentObjects() {
        Sensor sensor1 = new Sensor("Core Temperature", 25.0, 0.0, 100.0, "Celsius");
        Sensor sensor2 = new Sensor("Humidity", 25.0, 0.0, 100.0, "Percent");
        assertThat(sensor1).doesNotHaveSameHashCodeAs(sensor2);
    }
}
