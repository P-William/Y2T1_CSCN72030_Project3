package com.group3;

public class ControlDevice {
    private String deviceName;
    private boolean isActive;
    private String lastFeedbackMessage;
    private double currentValue;
    private double targetValue;
    private double minValue;
    private double maxValue;
    private String unit;
    private boolean buttonState;

    /*
     * Method Name: ControlDevice
     * Description: Default constructor that initializes a ControlDevice with default values.
     *              It sets all numeric values (current, target, min, max) to 0,
     *              boolean values (isActive, buttonState) to false,
     *              and String values (deviceName, lastFeedbackMessage, unit) to null.
     * Parameters: None
     * Returns: N/A (constructor)
     */
    public ControlDevice() {
        this.deviceName = null;
        this.isActive = false;
        this.lastFeedbackMessage = null;
        this.currentValue = 0;
        this.targetValue = 0;
        this.minValue = 0;
        this.maxValue = 0;
        this.unit = null;
        this.buttonState = false;
    }

    /*
     * Method Name: ControlDevice
     * Description: Constructor that initializes a ControlDevice with a specified device name.
     *              It sets the initial state of the device to inactive, with no feedback message,
     *              and all numeric values (current, target, min, max) to 0. The unit is set to null
     *              and the button state to false.
     * Parameters: String deviceName (the name of the controller device to be created)
     * Returns: N/A (constructor)
     */
    public ControlDevice(String deviceName) {
        this.deviceName = deviceName;
        this.isActive = false;
        this.lastFeedbackMessage = null;
        this.currentValue = 0;
        this.targetValue = 0;
        this.minValue = 0;
        this.maxValue = 0;
        this.unit = null;
        this.buttonState = false;
    }

    /*
     * Method Name: ControlDevice
     * Description: Constructor that initializes a ControlDevice with specified device name, minimum and maximum values, and unit.
     *              It sets the initial state of the device to inactive, with no feedback message, and current and target values at 0.
     * Parameters: String deviceName, double minValue, double maxValue, String unit
     * Returns: N/A (constructor)
     */
    public ControlDevice(String deviceName, double minValue, double maxValue, String unit) {
        this.deviceName = deviceName;
        this.isActive = false;
        this.lastFeedbackMessage = null;
        this.currentValue = 0;
        this.targetValue = 0;
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.unit = unit;
        this.buttonState = false;
    }

    /*
     * Method Name: getDeviceName
     * Description: Returns the name of the device.
     * Parameters: None
     * Returns: String (name of the device)
     */
    public String getDeviceName() {
        return deviceName;
    }

    /*
     * Method Name: setDeviceName
     * Description: Sets a new name for the device.
     * Parameters: String deviceName (the new name of the device)
     * Returns: void
     */
    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    /*
     * Method Name: getIsActive
     * Description: Returns the current activation state of the device.
     * Parameters: None
     * Returns: boolean (activation state of the device)
     */
    public boolean getIsActive() {
        return isActive;
    }

    /*
     * Method Name: setActive
     * Description: Sets the activation state of the device.
     * Parameters: boolean newIsActive (the new activation state of the device)
     * Returns: void
     */
    public void setActive(boolean newIsActive) {
        this.isActive = newIsActive;
    }

    /*
     * Method Name: turnOn
     * Description: Activates the device by setting isActive to true.
     * Parameters: None
     * Returns: void
     */
    public void turnOn() {
        this.isActive = true;
    }

    /*
     * Method Name: turnOff
     * Description: Deactivates the device by setting isActive to false.
     * Parameters: None
     * Returns: void
     */
    public void turnOff() {
        this.isActive = false;
    }

    /*
     * Method Name: getLastFeedbackMessage
     * Description: Returns the last feedback message of the device.
     * Parameters: None
     * Returns: String (last feedback message)
     */
    public String getLastFeedbackMessage() {
        return lastFeedbackMessage;
    }

    /*
     * Method Name: setLastFeedbackMessage
     * Description: Sets a new feedback message for the device.
     * Parameters: String newLastFeedbackMessage (the new feedback message)
     * Returns: void
     */
    public void setLastFeedbackMessage(String newLastFeedbackMessage) {
        this.lastFeedbackMessage = newLastFeedbackMessage;
    }

    /*
     * Method Name: getCurrentValue
     * Description: Returns the current value of the device.
     * Parameters: None
     * Returns: double (current value)
     */
    public double getCurrentValue() {
        return currentValue;
    }

    /*
     * Method Name: setCurrentValue
     * Description: Sets a new current value for the device.
     * Parameters: double newCurrentValue (the new current value)
     * Returns: void
     */
    public void setCurrentValue(double newCurrentValue) {
        if (newCurrentValue >= this.getMinValue() && newCurrentValue <= this.getMaxValue()) {
            this.currentValue = newCurrentValue;
            this.generateFeedbackMessage("Current value updated successfully.");
        }
        else if (newCurrentValue < this.getMinValue()) {
            this.generateFeedbackMessage("Current value cannot be less than the minimum value.");
        }
        else {
            this.generateFeedbackMessage("Current value cannot be greater than the maximum value.");
        }
    }

    /*
     * Method Name: getTargetValue
     * Description: Returns the target value of the device.
     * Parameters: None
     * Returns: double (target value)
     */
    public double getTargetValue() {
        return targetValue;
    }

    /*
     * Method Name: setTargetValue
     * Description: Sets a new target value for the device.
     * Parameters: double newTargetValue (the new target value)
     * Returns: void
     */
    public void setTargetValue(double newTargetValue) {
        if (newTargetValue >= this.getMinValue() && newTargetValue <= this.getMaxValue()) {
            this.targetValue = newTargetValue;
            this.generateFeedbackMessage("Target value updated successfully.");
        } else if (newTargetValue < this.getMinValue()) {
            this.generateFeedbackMessage("Target value cannot be less than the minimum value.");
        }
        else {
            this.generateFeedbackMessage("Target value cannot be greater than the maximum value.");
        }
    }

    /*
     * Method Name: getMinValue
     * Description: Returns the minimum value of the device.
     * Parameters: None
     * Returns: double (minimum value)
     */
    public double getMinValue() {
        return minValue;
    }

    /*
     * Method Name: setMinValue
     * Description: Sets a new minimum value for the device.
     * Parameters: double newMinValue (the new minimum value)
     * Returns: void
     */
    public void setMinValue(double newMinValue) {
        if (newMinValue < this.getMaxValue()) {
            this.maxValue = newMinValue;
            this.generateFeedbackMessage("Minimum value updated successfully.");
        }
        else {
            this.generateFeedbackMessage("Invalid minimum value. Must be less than or equal to the current maximum value.");
        }
    }

    /*
     * Method Name: getMaxValue
     * Description: Returns the maximum value of the device.
     * Parameters: None
     * Returns: double (maximum value)
     */
    public double getMaxValue() {
        return maxValue;
    }

    /*
     * Method Name: setMaxValue
     * Description: Sets a new maximum value for the device.
     * Parameters: double newMaxValue (the new maximum value)
     * Returns: void
     */
    public void setMaxValue(double newMaxValue) {
        if (newMaxValue > this.getMinValue()) {
            this.maxValue = newMaxValue;
            this.generateFeedbackMessage("Maximum value updated successfully.");
        }
        else {
            this.generateFeedbackMessage("Invalid maximum value. Must be greater than or equal to the current minimum value.");
        }
    }

    /*
     * Method Name: getUnit
     * Description: Returns the unit of measurement for the device's values.
     * Parameters: None
     * Returns: String (unit of measurement)
     */
    public String getUnit() {
        return unit;
    }

    /*
     * Method Name: setUnit
     * Description: Sets a new unit of measurement for the device's values.
     * Parameters: String newUnit (the new unit of measurement)
     * Returns: void
     */
    public void setUnit(String newUnit) {
        this.unit = newUnit;
        this.generateFeedbackMessage("Unit of measurement updated successfully.");
    }

    /*
     * Method Name: isButtonState
     * Description: Returns the state of the button.
     * Parameters: None
     * Returns: boolean (state of the button)
     */
    public boolean isButtonState() {
        return buttonState;
    }

    /*
     * Method Name: setButtonState
     * Description: Sets the state of the button.
     * Parameters: boolean newButtonState (the new state of the button)
     * Returns: void
     */
    public void setButtonState(boolean newButtonState) {
        this.buttonState = newButtonState;
        this.generateFeedbackMessage("Button state updated successfully.");
    }

    /*
     * Method Name: adjustCurrentValue
     * Description: Adjusts the current value of the device incrementally towards the target value.
     *              If the target is lower than the current value, it decreases by increment.
     *              If the target is higher, it increases by increment.
     * Parameters: double increment (amount to adjust the current value by)
     * Returns: void
     */
    public void adjustCurrentValue(double increment) {
        if (this.targetValue < this.currentValue) {
            this.targetValue += increment;
            this.generateFeedbackMessage("Successfully incremented current value towards target value.");
        } else if (this.targetValue > this.currentValue) {
            this.targetValue -= increment;
            this.generateFeedbackMessage("Successfully decremented current value towards target value.");
        }
    }

    /*
     * Method Name: generateFeedbackMessage
     * Description: Generates a feedback message based on the given message. Updates lastFeedbackMessage accordingly.
     * Parameters: String message (message to be logged)
     * Returns: void
     */
    public void generateFeedbackMessage(String message) {
        this.setLastFeedbackMessage(message);
        this.logAction(this.lastFeedbackMessage);
    }

    /*
     * Method Name: readFromFile
     * Description: Reads device configuration or state data from a specified file.
     * Parameters: String fileName (name of the file to read from)
     * Returns: void
     */
    public void readFromFile(String fileName) {
        // Implementation for reading from file goes here by Stephan
    }

    /*
     * Method Name: writeToFile
     * Description: Writes device configuration or state data to a specified file.
     * Parameters: String fileName (name of the file to write to)
     * Returns: void
     */
    public void writeToFile(String fileName) {
        // Implementation for writing to file goes here by Stephan
    }

    /*
     * Method Name: logAction
     * Description: Logs an action taken by the device for tracking or debugging purposes.
     * Parameters: String action (description of the action taken)
     * Returns: void
     */
    public void logAction(String action) {
        // Implementation for logging actions goes here by Stephan
    }
}
