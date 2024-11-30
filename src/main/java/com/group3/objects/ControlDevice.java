package com.group3.objects;

import lombok.ToString;

import java.io.Serializable;

/* Class Name: ControlDevice
 * Class Author: Brooke Cronin
 * Date: November 14, 2024
 * Course: CSCN72030
 *
 * Description: This class represents a control device with attributes such as
 *              device name, activation state, current and target values, and more.
 *              It includes methods for managing and adjusting the device's values and state.
 */
@ToString
public class ControlDevice implements Serializable
{
    private static final long serialVersionUID = 1L;

    private String deviceName;
    private boolean isActive;
    private String lastFeedbackMessage;
    private double currentValue;
    private double targetValue;
    private double minValue;
    private double maxValue;
    private String unit;
    private boolean buttonState;

    /* Method Name: ControlDevice
     * Method Author: Brooke Cronin
     * Description: Default constructor that initializes a ControlDevice with default values.
     * Parameters: N/A
     * Returns: N/A
     */
    public ControlDevice()
    {
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

    /* Method Name: ControlDevice
     * Method Author: Brooke Cronin
     * Description: Constructor that initializes a ControlDevice with a specified device name.
     * Parameters: String deviceName (the name of the controller device)
     * Returns: N/A
     */
    public ControlDevice(String deviceName)
    {
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

    /* Method Name: ControlDevice
     * Method Author: Brooke Cronin
     * Description: Constructor that initializes a ControlDevice with specified name, min/max values, and unit.
     * Parameters: String deviceName (name of the device), double minValue (minimum value),
     *             double maxValue (maximum value), String unit (unit of measurement)
     * Returns: N/A
     */
    public ControlDevice(String deviceName, double minValue, double maxValue, String unit)
    {
        if (minValue > maxValue)
        {
            throw new IllegalArgumentException("Minimum value cannot be greater than the maximum value.");
        }
        if (minValue == maxValue)
        {
            throw new IllegalArgumentException("Minimum and maximum values cannot be equal.");
        }
        this.deviceName = deviceName;
        this.isActive = false;
        this.lastFeedbackMessage = null;
        this.currentValue = minValue;
        this.targetValue = minValue;
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.unit = unit;
        this.buttonState = false;
    }

    /* Method Name: ControlDevice
     * Method Author: Brooke Cronin
     * Description: Constructor that initializes a ControlDevice with specified name, min/max values,
     *              current value, and unit.
     * Parameters: String deviceName (name of the device), double minValue (minimum value),
     *             double maxValue (maximum value), double currentValue (current value), String unit (unit of measurement)
     * Returns: N/A
     */
    public ControlDevice(String deviceName, double minValue, double maxValue, double currentValue, String unit)
    {
        if (minValue > maxValue)
        {
            throw new IllegalArgumentException("Minimum value cannot be greater than the maximum value.");
        }
        if (minValue == maxValue)
        {
            if (currentValue == minValue) {
                throw new IllegalArgumentException("Min, max, and current values cannot all be equal.");
            }
            else {
                throw new IllegalArgumentException("Minimum and maximum values cannot be equal.");
            }
        }
        if (currentValue < minValue)
        {
            throw new IllegalArgumentException("Current value cannot be less than the minimum value.");
        }
        if (currentValue > maxValue)
        {
            throw new IllegalArgumentException("Current value cannot be greater than the maximum value.");
        }
        this.deviceName = deviceName;
        this.isActive = false;
        this.lastFeedbackMessage = null;
        this.currentValue = currentValue;
        this.targetValue = currentValue;
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.unit = unit;
        this.buttonState = false;
    }

    /* Method Name: getDeviceName
     * Method Author: Brooke Cronin
     * Description: Returns the name of the device.
     * Parameters: N/A
     * Returns: String (name of the device)
     */
    public String getDeviceName()
    {
        return this.deviceName;
    }

    /* Method Name: setDeviceName
     * Method Author: Brooke Cronin
     * Description: Sets a new name for the device.
     * Parameters: String deviceName (new name of the device)
     * Returns: void
     */
    public void setDeviceName(String deviceName)
    {
        if (deviceName == null || deviceName.isEmpty())
        {
            throw new IllegalArgumentException("Device name cannot be null or empty.");
        }
        this.deviceName = deviceName;
    }

    /* Method Name: getIsActive
     * Method Author: Brooke Cronin
     * Description: Returns the current activation state of the device.
     * Parameters: N/A
     * Returns: boolean (activation state of the device)
     */
    public boolean getIsActive()
    {
        return this.isActive;
    }

    /* Method Name: setIsActive
     * Method Author: Brooke Cronin
     * Description: Sets the activation state of the device.
     * Parameters: boolean newIsActive (new activation state)
     * Returns: void
     */
    public void setIsActive(boolean newIsActive)
    {
        this.isActive = newIsActive;
    }

    /* Method Name: turnOn
     * Method Author: Brooke Cronin
     * Description: Activates the device by setting isActive to true.
     * Parameters: N/A
     * Returns: void
     */
    public void turnOn()
    {
        this.setIsActive(true);
    }

    /* Method Name: turnOff
     * Method Author: Brooke Cronin
     * Description: Deactivates the device by setting isActive to false.
     * Parameters: N/A
     * Returns: void
     */
    public void turnOff()
    {
        this.setIsActive(false);
    }

    /* Method Name: getLastFeedbackMessage
     * Method Author: Brooke Cronin
     * Description: Returns the last feedback message.
     * Parameters: N/A
     * Returns: String (last feedback message)
     */
    public String getLastFeedbackMessage()
    {
        return this.lastFeedbackMessage;
    }

    /* Method Name: setLastFeedbackMessage
     * Method Author: Brooke Cronin
     * Description: Sets a new feedback message for the device.
     * Parameters: String newLastFeedbackMessage (new feedback message)
     * Returns: void
     */
    public void setLastFeedbackMessage(String newLastFeedbackMessage)
    {
        this.lastFeedbackMessage = newLastFeedbackMessage;
    }

    /* Method Name: getCurrentValue
     * Method Author: Brooke Cronin
     * Description: Returns the current value of the device.
     * Parameters: N/A
     * Returns: double (current value of the device)
     */
    public double getCurrentValue()
    {
        return this.currentValue;
    }

    /* Method Name: setCurrentValue
     * Method Author: Brooke Cronin
     * Description: Sets a new current value for the device if it is within the valid range.
     * Parameters: double newCurrentValue (new current value for the device)
     * Returns: void
     */
    public void setCurrentValue(double newCurrentValue)
    {
        if (newCurrentValue >= this.getMinValue() && newCurrentValue <= this.getMaxValue())
        {
            this.currentValue = newCurrentValue;
            this.generateFeedbackMessage("Current value updated successfully.");
        }
        else if (newCurrentValue < this.getMinValue())
        {
            this.generateFeedbackMessage("Current value cannot be less than the minimum value.");
        }
        else
        {
            this.generateFeedbackMessage("Current value cannot be greater than the maximum value.");
        }
    }

    /* Method Name: getTargetValue
     * Method Author: Brooke Cronin
     * Description: Returns the target value of the device.
     * Parameters: N/A
     * Returns: double (target value of the device)
     */
    public double getTargetValue()
    {
        return this.targetValue;
    }

    /* Method Name: setTargetValue
     * Method Author: Brooke Cronin
     * Description: Sets a new target value for the device if it is within the valid range.
     * Parameters: double newTargetValue (new target value for the device)
     * Returns: void
     */
    public void setTargetValue(double newTargetValue)
    {
        if (newTargetValue >= this.getMinValue() && newTargetValue <= this.getMaxValue())
        {
            this.targetValue = newTargetValue;
            this.generateFeedbackMessage("Target value updated successfully.");
        }
        else if (newTargetValue < this.getMinValue())
        {
            this.generateFeedbackMessage("Target value cannot be less than the minimum value.");
        }
        else
        {
            this.generateFeedbackMessage("Target value cannot be greater than the maximum value.");
        }
    }

    /* Method Name: getMinValue
     * Method Author: Brooke Cronin
     * Description: Returns the minimum value allowed for the device.
     * Parameters: N/A
     * Returns: double (minimum value of the device)
     */
    public double getMinValue()
    {
        return this.minValue;
    }

    /* Method Name: setMinValue
     * Method Author: Brooke Cronin
     * Description: Sets a new minimum value for the device if it is valid and less than or equal to other values.
     * Parameters: double newMinValue (new minimum value for the device)
     * Returns: void
     */
    public void setMinValue(double newMinValue)
    {
        if (newMinValue < this.getMaxValue() && newMinValue <= this.getCurrentValue() && newMinValue <= this.getTargetValue())
        {
            this.minValue = newMinValue;
            this.generateFeedbackMessage("Minimum value updated successfully.");
        }
        else
        {
            this.generateFeedbackMessage("Invalid minimum value. Must be less than the maximum value and less than or equal to the current and target values.");
        }
    }

    /* Method Name: getMaxValue
     * Method Author: Brooke Cronin
     * Description: Returns the maximum value allowed for the device.
     * Parameters: N/A
     * Returns: double (maximum value of the device)
     */
    public double getMaxValue()
    {
        return this.maxValue;
    }

    /* Method Name: setMaxValue
     * Method Author: Brooke Cronin
     * Description: Sets a new maximum value for the device if it is valid and greater than or equal to other values.
     * Parameters: double newMaxValue (new maximum value for the device)
     * Returns: void
     */
    public void setMaxValue(double newMaxValue)
    {
        if (newMaxValue > this.getMinValue() && newMaxValue >= this.getCurrentValue() && newMaxValue >= this.getTargetValue())
        {
            this.maxValue = newMaxValue;
            this.generateFeedbackMessage("Maximum value updated successfully.");
        }
        else
        {
            this.generateFeedbackMessage("Invalid maximum value. Must be greater than the minimum value and greater than or equal to the current and target values.");
        }
    }

    /* Method Name: getUnit
     * Method Author: Brooke Cronin
     * Description: Returns the unit of measurement for the device's values.
     * Parameters: N/A
     * Returns: String (unit of measurement)
     */
    public String getUnit()
    {
        return this.unit;
    }

    /* Method Name: setUnit
     * Method Author: Brooke Cronin
     * Description: Sets a new unit of measurement for the device's values.
     * Parameters: String newUnit (new unit of measurement)
     * Returns: void
     */
    public void setUnit(String newUnit)
    {
        this.unit = newUnit;
        this.generateFeedbackMessage("Unit of measurement updated successfully.");
    }

    /* Method Name: isButtonState
     * Method Author: Brooke Cronin
     * Description: Returns the current state of the button.
     * Parameters: N/A
     * Returns: boolean (current state of the button)
     */
    public boolean isButtonState()
    {
        return this.buttonState;
    }

    /* Method Name: setButtonState
     * Method Author: Brooke Cronin
     * Description: Sets a new state for the button.
     * Parameters: boolean newButtonState (new state for the button)
     * Returns: void
     */
    public void setButtonState(boolean newButtonState)
    {
        this.buttonState = newButtonState;
        this.generateFeedbackMessage("Button state updated successfully.");
    }

    /* Method Name: adjustCurrentValue
     * Method Author: Brooke Cronin
     * Description: Adjusts the current value incrementally towards the target value.
     * Parameters: double increment (amount to adjust the current value by)
     * Returns: void
     */
    public void adjustCurrentValue(double increment)
    {
        if (this.targetValue < this.currentValue)
        {
            this.currentValue -= increment;
            this.generateFeedbackMessage("Successfully decremented current value towards target value.");
        }
        else if (this.targetValue > this.currentValue)
        {
            this.currentValue += increment;
            this.generateFeedbackMessage("Successfully incremented current value towards target value.");
        }
    }

    /* Method Name: generateFeedbackMessage
     * Method Author: Brooke Cronin
     * Description: Sets a feedback message and logs the action for tracking purposes.
     * Parameters: String message (feedback message to be set)
     * Returns: void
     */
    public void generateFeedbackMessage(String message)
    {
        this.setLastFeedbackMessage(message);
        this.logAction(this.lastFeedbackMessage);
    }

    /* Method Name: readFromFile
     * Method Author: Brooke Cronin
     * Description: Reads device configuration or state data from a specified file.
     * Parameters: String fileName (name of the file to read from)
     * Returns: void
     */
    public void readFromFile(String fileName)
    {
        // Placeholder for reading from a file
    }

    /* Method Name: writeToFile
     * Method Author: Brooke Cronin
     * Description: Writes device configuration or state data to a specified file.
     * Parameters: String fileName (name of the file to write to)
     * Returns: void
     */
    public void writeToFile(String fileName)
    {
        // Placeholder for writing to a file
    }

    /* Method Name: logAction
     * Method Author: Brooke Cronin
     * Description: Logs an action for tracking or debugging purposes.
     * Parameters: String action (description of the action taken)
     * Returns: void
     */
    public void logAction(String action)
    {
        // Placeholder for logging actions
    }

    /*
    *
    * @Override
    public String toString() {
        return String.format("ControlDevice{name='%s', min='%.2f', max='%.2f', current='%.2f', unit='%s'}%n",deviceName, minValue, maxValue, currentValue, unit);
    }
    * */

}