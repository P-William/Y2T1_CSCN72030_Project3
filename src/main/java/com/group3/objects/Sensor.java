package com.group3.objects;

import lombok.NonNull;
import lombok.ToString;

import java.io.Serializable;

@ToString
public class Sensor implements Serializable {
    private final String name;
    private double value;
    private final double minValue;
    private final double maxValue;
    private final String unit;

    private static final String RANGE_ERROR = "Value %s is out of range %s - %s";

    public Sensor(
        @NonNull String name,
        @NonNull Double value,
        @NonNull Double minValue,
        @NonNull Double maxValue,
        @NonNull String unit
    ) {
        if (value <= minValue || value >= maxValue) {
            throw new IllegalArgumentException(RANGE_ERROR.formatted(value, minValue, maxValue));
        }
        this.name = name;
        this.value = value;
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.unit = unit;
    }

    public String getName() {
        return name;
    }

    public double getValue() {
        return value;
    }

    public double getMinValue() {
        return minValue;
    }

    public double getMaxValue() {
        return maxValue;
    }

    public String getUnit() {
        return unit;
    }

    public void setValue(@NonNull Double value) {
        if (value <= minValue || value >= maxValue) {
            throw new IllegalArgumentException(RANGE_ERROR.formatted(value, minValue, maxValue));
        }
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Sensor sensor = (Sensor) o;
        return Double.compare(value, sensor.value) == 0 &&
            Double.compare(minValue, sensor.minValue) == 0 &&
            Double.compare(maxValue, sensor.maxValue) == 0 &&
            name.equals(sensor.name) &&
            unit.equals(sensor.unit);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + Double.hashCode(value);
        result = 31 * result + Double.hashCode(minValue);
        result = 31 * result + Double.hashCode(maxValue);
        result = 31 * result + unit.hashCode();
        return result;
    }
}
