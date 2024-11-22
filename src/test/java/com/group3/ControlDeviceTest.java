package com.group3;

import com.group3.ControlDevice;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class ControlDeviceTest {

    /* Method Name: defaultConstructor_ShouldInitializeWithDefaultValues
     * Method Author: Brooke Cronin
     * Test Case ID: UNIT_CD_001_REQ_003
     * Description: Tests that the default constructor initializes all attributes to their default values (null, 0, or false).
     * Inputs: None
     * Output: A ControlDevice object with default values for all attributes.
     */
    @Test
    void defaultConstructor_ShouldInitializeWithDefaultValues()
    {
        // Arrange (N/A)

        // Act
        ControlDevice device = new ControlDevice();

        // Assert
        assertThat(device.getDeviceName()).isNull();
        assertThat(device.getIsActive()).isFalse();
        assertThat(device.getLastFeedbackMessage()).isNull();
        assertThat(device.getCurrentValue()).isZero();
        assertThat(device.getTargetValue()).isZero();
        assertThat(device.getMinValue()).isZero();
        assertThat(device.getMaxValue()).isZero();
        assertThat(device.getUnit()).isNull();
        assertThat(device.isButtonState()).isFalse();
    }

    /* Method Name: constructor_WithDeviceName_ShouldSetDeviceNameAndDefaultOtherValues
     * Method Author: Brooke Cronin
     * Test Case ID: UNIT_CD_002_REQ_003
     * Description: Verifies that the constructor with the deviceName parameter sets the deviceName and initializes other attributes to their default values.
     * Inputs: deviceName: "Coolant Level" (String - name of the device)
     * Output: A ControlDevice object with deviceName set and other attributes at their default values.
     */
    @Test
    void constructor_WithDeviceName_ShouldSetDeviceNameAndDefaultOtherValues()
    {
        // Arrange
        String expectedDeviceName = "Coolant Level";

        // Act
        ControlDevice device = new ControlDevice(expectedDeviceName);

        // Assert
        assertThat(device.getDeviceName()).isEqualTo(expectedDeviceName);
        assertThat(device.getIsActive()).isFalse();
        assertThat(device.getLastFeedbackMessage()).isNull();
        assertThat(device.getCurrentValue()).isZero();
        assertThat(device.getTargetValue()).isZero();
        assertThat(device.getMinValue()).isZero();
        assertThat(device.getMaxValue()).isZero();
        assertThat(device.getUnit()).isNull();
        assertThat(device.isButtonState()).isFalse();
    }

    /* Method Name: constructor_WithValidMinMaxCurrentAndUnitValues_ShouldInitializeAllFieldsAndRestWithDefaultValues
     * Method Author: Brooke Cronin
     * Test Case ID: UNIT_CD_003_REQ_003
     * Description: Ensures that the constructor initializes deviceName, minValue, maxValue, currentValue, and unit, while other attributes are set to default values.
     * Inputs:
     * - deviceName: "Coolant Level" (String - name of the device)
     * - minValue: 10.0 (double - minimum allowable value)
     * - maxValue: 100.0 (double - maximum allowable value)
     * - currentValue: 50.0 (double - current value of the device)
     * - unit: "litres (L)" (String - unit of measurement)
     * Output: A ControlDevice object with the specified and default attribute values.
     */
    @Test
    void constructor_WithValidMinMaxCurrentAndUnitValues_ShouldInitializeAllFieldsAndRestWithDefaultValues()
    {
        // Arrange
        String expectedDeviceName = "Coolant Level";
        double expectedMinValue = 10.0;
        double expectedMaxValue = 100.0;
        double expectedCurrentValue = 50.0;
        String expectedUnit = "litres (L)";

        // Act
        ControlDevice device = new ControlDevice(expectedDeviceName, expectedMinValue, expectedMaxValue, expectedCurrentValue, expectedUnit);

        // Assert
        assertThat(device.getDeviceName()).isEqualTo(expectedDeviceName);
        assertThat(device.getIsActive()).isFalse();
        assertThat(device.getLastFeedbackMessage()).isNull();
        assertThat(device.getCurrentValue()).isEqualTo(expectedCurrentValue);
        assertThat(device.getTargetValue()).isEqualTo(expectedCurrentValue);
        assertThat(device.getMinValue()).isEqualTo(expectedMinValue);
        assertThat(device.getMaxValue()).isEqualTo(expectedMaxValue);
        assertThat(device.getUnit()).isEqualTo(expectedUnit);
        assertThat(device.isButtonState()).isFalse();
    }

    /* Method Name: constructor_WithCurrentValueEqualToMinValue_ShouldSetCurrentAndTargetValue
     * Method Author: Brooke Cronin
     * Test Case ID: UNIT_CD_004_REQ_003
     * Description: Tests that the constructor sets currentValue and targetValue to minValue when they are equal.
     * Inputs:
     * - deviceName: "Coolant Level" (String - name of the device)
     * - minValue: 10.0 (double - minimum allowable value)
     * - maxValue: 100.0 (double - maximum allowable value)
     * - currentValue: 10.0 (double - current value equal to minValue)
     * - unit: "litres (L)" (String - unit of measurement)
     * Output: A ControlDevice object with currentValue and targetValue set to minValue.
     */
    @Test
    void constructor_WithCurrentValueEqualToMinValue_ShouldSetCurrentAndTargetValue()
    {
        // Arrange
        String expectedDeviceName = "Coolant Level";
        double expectedMinValue = 10.0;
        double expectedMaxValue = 100.0;
        double expectedCurrentValue = 10.0;
        String expectedUnit = "litres (L)";

        // Act
        ControlDevice device = new ControlDevice(expectedDeviceName, expectedMinValue, expectedMaxValue, expectedCurrentValue, expectedUnit);

        // Assert
        assertThat(device.getDeviceName()).isEqualTo(expectedDeviceName);
        assertThat(device.getIsActive()).isFalse();
        assertThat(device.getLastFeedbackMessage()).isNull();
        assertThat(device.getCurrentValue()).isEqualTo(expectedCurrentValue);
        assertThat(device.getTargetValue()).isEqualTo(expectedCurrentValue);
        assertThat(device.getMinValue()).isEqualTo(expectedMinValue);
        assertThat(device.getMaxValue()).isEqualTo(expectedMaxValue);
        assertThat(device.getUnit()).isEqualTo(expectedUnit);
        assertThat(device.isButtonState()).isFalse();
    }

    /* Method Name: constructor_WithCurrentValueEqualToMaxValue_ShouldSetCurrentAndTargetValue
     * Method Author: Brooke Cronin
     * Test Case ID: UNIT_CD_005_REQ_003
     * Description: Verifies that the constructor sets currentValue and targetValue to maxValue when they are equal.
     * Inputs:
     * - deviceName: "Coolant Level" (String - name of the device)
     * - minValue: 10.0 (double - minimum allowable value)
     * - maxValue: 100.0 (double - maximum allowable value)
     * - currentValue: 100.0 (double - current value equal to maxValue)
     * - unit: "litres (L)" (String - unit of measurement)
     * Output: A ControlDevice object with currentValue and targetValue set to maxValue.
     */
    @Test
    void constructor_WithCurrentValueEqualToMaxValue_ShouldSetCurrentAndTargetValue()
    {
        // Arrange
        String expectedDeviceName = "Coolant Level";
        double expectedMinValue = 10.0;
        double expectedMaxValue = 100.0;
        double expectedCurrentValue = 100.0;
        String expectedUnit = "litres (L)";

        // Act
        ControlDevice device = new ControlDevice(expectedDeviceName, expectedMinValue, expectedMaxValue, expectedCurrentValue, expectedUnit);

        // Assert
        assertThat(device.getDeviceName()).isEqualTo(expectedDeviceName);
        assertThat(device.getIsActive()).isFalse();
        assertThat(device.getLastFeedbackMessage()).isNull();
        assertThat(device.getCurrentValue()).isEqualTo(expectedCurrentValue);
        assertThat(device.getTargetValue()).isEqualTo(expectedCurrentValue);
        assertThat(device.getMinValue()).isEqualTo(expectedMinValue);
        assertThat(device.getMaxValue()).isEqualTo(expectedMaxValue);
        assertThat(device.getUnit()).isEqualTo(expectedUnit);
        assertThat(device.isButtonState()).isFalse();
    }

    /* Method Name: constructor_WithCurrentValueBelowMinValue_ShouldThrowIllegalArgumentException
     * Method Author: Brooke Cronin
     * Test Case ID: UNIT_CD_006_REQ_008
     * Description: Ensures that the constructor throws an IllegalArgumentException when currentValue is less than minValue.
     * Inputs:
     * - deviceName: "Coolant Level" (String - name of the device)
     * - minValue: 10.0 (double - minimum allowable value)
     * - maxValue: 100.0 (double - maximum allowable value)
     * - currentValue: 5.0 (double - less than minValue)
     * - unit: "litres (L)" (String - unit of measurement)
     * Output: IllegalArgumentException with an appropriate error message.
     */
    @Test
    void constructor_WithCurrentValueBelowMinValue_ShouldThrowIllegalArgumentException()
    {
        // Arrange
        String expectedDeviceName = "Coolant Level";
        double expectedMinValue = 10.0;
        double expectedMaxValue = 100.0;
        double invalidCurrentValue = 5.0;
        String expectedUnit = "litres (L)";

        // Act & Assert
        assertThatThrownBy(() -> new ControlDevice(expectedDeviceName, expectedMinValue, expectedMaxValue, invalidCurrentValue, expectedUnit))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Current value cannot be less than the minimum value.");
    }

    /* Method Name: constructor_WithCurrentValueAboveMaxValue_ShouldThrowIllegalArgumentException
     * Method Author: Brooke Cronin
     * Test Case ID: UNIT_CD_007_REQ_008
     * Description: Ensures that the constructor throws an IllegalArgumentException when currentValue is greater than maxValue.
     * Inputs:
     * - deviceName: "Coolant Level" (String - name of the device)
     * - minValue: 10.0 (double - minimum allowable value)
     * - maxValue: 100.0 (double - maximum allowable value)
     * - currentValue: 105.0 (double - greater than maxValue)
     * - unit: "litres (L)" (String - unit of measurement)
     * Output: IllegalArgumentException with an appropriate error message.
     */
    @Test
    void constructor_WithCurrentValueAboveMaxValue_ShouldThrowIllegalArgumentException()
    {
        // Arrange
        String expectedDeviceName = "Coolant Level";
        double expectedMinValue = 10.0;
        double expectedMaxValue = 100.0;
        double invalidCurrentValue = 105.0;
        String expectedUnit = "litres (L)";

        // Act & Assert
        assertThatThrownBy(() -> new ControlDevice(expectedDeviceName, expectedMinValue, expectedMaxValue, invalidCurrentValue, expectedUnit))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Current value cannot be greater than the maximum value.");
    }

    /* Method Name: constructor_WithMinValueAboveMaxValue_ShouldThrowIllegalArgumentException
     * Method Author: Brooke Cronin
     * Test Case ID: UNIT_CD_008_REQ_008
     * Description: Verifies that the constructor throws an IllegalArgumentException when minValue is greater than maxValue.
     * Inputs:
     * - deviceName: "Coolant Level" (String - name of the device)
     * - minValue: 100.0 (double - greater than maxValue)
     * - maxValue: 10.0 (double - maximum allowable value)
     * - currentValue: 50.0 (double - valid current value)
     * - unit: "litres (L)" (String - unit of measurement)
     * Output: IllegalArgumentException with an appropriate error message.
     */
    @Test
    void constructor_WithMinValueAboveMaxValue_ShouldThrowIllegalArgumentException()
    {
        // Arrange
        String expectedDeviceName = "Coolant Level";
        double invalidMinValue = 100.0;
        double expectedMaxValue = 10.0;
        double expectedCurrentValue = 50.0;
        String expectedUnit = "litres (L)";

        // Act & Assert
        assertThatThrownBy(() -> new ControlDevice(expectedDeviceName, invalidMinValue, expectedMaxValue, expectedCurrentValue, expectedUnit))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Minimum value cannot be greater than the maximum value.");
    }

    /* Method Name: constructor_WithEqualMinMaxAndCurrentValues_ShouldThrowIllegalArgumentException
     * Method Author: Brooke Cronin
     * Test Case ID: UNIT_CD_009_REQ_008
     * Description: Verifies that the constructor throws an IllegalArgumentException when minValue, maxValue, and currentValue are all equal.
     * Inputs:
     * - deviceName: "Coolant Level" (String - name of the device)
     * - minValue: 50.0 (double - equal to maxValue and currentValue)
     * - maxValue: 50.0 (double - equal to minValue and currentValue)
     * - currentValue: 50.0 (double - equal to minValue and maxValue)
     * - unit: "litres (L)" (String - unit of measurement)
     * Output: IllegalArgumentException with an appropriate error message.
     */
    @Test
    void constructor_WithEqualMinMaxAndCurrentValues_ShouldThrowIllegalArgumentException()
    {
        // Arrange
        String expectedDeviceName = "Coolant Level";
        double invalidMinValue = 50.0;
        double invalidMaxValue = 50.0;
        double invalidCurrentValue = 50.0;
        String expectedUnit = "litres (L)";

        // Act & Assert
        assertThatThrownBy(() -> new ControlDevice(expectedDeviceName, invalidMinValue, invalidMaxValue, invalidCurrentValue, expectedUnit))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Min, max, and current values cannot all be equal.");
    }

    /* Method Name: constructor_WithMinValueAboveMaxValueInThreeParamConstructor_ShouldThrowIllegalArgumentException
     * Method Author: Brooke Cronin
     * Test Case ID: UNIT_CD_010_REQ_008
     * Description: Ensures that the constructor with three parameters throws an IllegalArgumentException when minValue is greater than maxValue.
     * Inputs:
     * - deviceName: "Coolant Level" (String - name of the device)
     * - minValue: 100.0 (double - greater than maxValue)
     * - maxValue: 10.0 (double - maximum allowable value)
     * - unit: "liters (L)" (String - unit of measurement)
     * Output: IllegalArgumentException with an appropriate error message.
     */
    @Test
    void constructor_WithMinValueAboveMaxValueInThreeParamConstructor_ShouldThrowIllegalArgumentException()
    {
        // Arrange
        String deviceName = "Coolant Level";
        double invalidMinValue = 100.0; // Greater than maxValue
        double maxValue = 10.0;
        String unit = "liters (L)";

        // Act & Assert
        assertThatThrownBy(() -> new ControlDevice(deviceName, invalidMinValue, maxValue, unit))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Minimum value cannot be greater than the maximum value.");
    }

    /* Method Name: constructor_WithEqualMinMaxValuesInThreeParamConstructor_ShouldThrowIllegalArgumentException
     * Method Author: Brooke Cronin
     * Test Case ID: UNIT_CD_011_REQ_008
     * Description: Verifies that the three-parameter constructor throws an IllegalArgumentException when minValue and maxValue are equal.
     * Inputs:
     * - deviceName: "Coolant Level" (String - name of the device)
     * - minValue: 100.0 (double - equal to maxValue)
     * - maxValue: 100.0 (double - equal to minValue)
     * - unit: "liters (L)" (String - unit of measurement)
     * Output: IllegalArgumentException with an appropriate error message.
     */
    @Test
    void constructor_WithEqualMinMaxValuesInThreeParamConstructor_ShouldThrowIllegalArgumentException()
    {
        // Arrange
        String deviceName = "Coolant Level";
        double equalValue = 100.0; // minValue and maxValue are the same
        String unit = "liters (L)";

        // Act & Assert
        assertThatThrownBy(() -> new ControlDevice(deviceName, equalValue, equalValue, unit))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Minimum and maximum values cannot be equal.");
    }

    /* Method Name: getDeviceName_WithNonNullDeviceName_ShouldReturnDeviceName
     * Method Author: Brooke Cronin
     * Test Case ID: UNIT_CD_012_REQ_006
     * Description: Tests that the getDeviceName method returns the correct deviceName when it is set to a non-null value.
     * Inputs: None
     * Output: "Coolant Level" (String - deviceName of the ControlDevice object)
     */
    @Test
    void getDeviceName_WithNonNullDeviceName_ShouldReturnDeviceName()
    {
        // Arrange
        String expectedDeviceName = "Coolant Level";
        ControlDevice device = new ControlDevice(expectedDeviceName);

        // Act
        String actualDeviceName = device.getDeviceName();

        // Assert
        assertThat(actualDeviceName).isEqualTo(expectedDeviceName);
    }

    /* Method Name: getDeviceName_WithNullDeviceName_ShouldReturnNull
     * Method Author: Brooke Cronin
     * Test Case ID: UNIT_CD_013_REQ_006
     * Description: Tests that the getDeviceName method returns null when the deviceName is not set.
     * Inputs: None
     * Output: null (String - deviceName is not set)
     */
    @Test
    void getDeviceName_WithNullDeviceName_ShouldReturnNull()
    {
        // Arrange
        ControlDevice device = new ControlDevice();

        // Act
        String actualDeviceName = device.getDeviceName();

        // Assert
        assertThat(actualDeviceName).isNull();
    }

    /* Method Name: setDeviceName_FromNonNullToNewNonNullValue_ShouldUpdateDeviceName
     * Method Author: Brooke Cronin
     * Test Case ID: UNIT_CD_014_REQ_006
     * Description: Ensures that the setDeviceName method updates the deviceName from one non-null value to another non-null value.
     * Inputs: newDeviceName: "Power Output" (String - new deviceName)
     * Output: Updated deviceName: "Power Output" (String)
     */
    @Test
    void setDeviceName_FromNonNullToNewNonNullValue_ShouldUpdateDeviceName()
    {
        // Arrange
        ControlDevice device = new ControlDevice("Coolant Level");
        String newDeviceName = "Power Output";

        // Act
        device.setDeviceName(newDeviceName);

        // Assert
        assertThat(device.getDeviceName()).isEqualTo(newDeviceName);
    }

    /* Method Name: setDeviceName_FromNullToNonNullValue_ShouldUpdateDeviceName
     * Method Author: Brooke Cronin
     * Test Case ID: UNIT_CD_015_REQ_006
     * Description: Verifies that the setDeviceName method updates the deviceName from null to a non-null value.
     * Inputs: newDeviceName: "Power Output" (String - new deviceName)
     * Output: Updated deviceName: "Power Output" (String)
     */
    @Test
    void setDeviceName_FromNullToNonNullValue_ShouldUpdateDeviceName()
    {
        // Arrange
        ControlDevice device = new ControlDevice();
        String newDeviceName = "Power Output";

        // Act
        device.setDeviceName(newDeviceName);

        // Assert
        assertThat(device.getDeviceName()).isEqualTo(newDeviceName);
    }

    /* Method Name: setDeviceName_FromNonNullToNull_ShouldThrowIllegalArgumentException
     * Method Author: Brooke Cronin
     * Test Case ID: UNIT_CD_016_REQ_008
     * Description: Tests that the setDeviceName method throws an IllegalArgumentException when attempting to set the deviceName to null.
     * Inputs: newDeviceName: null (String - invalid deviceName)
     * Output: IllegalArgumentException with an appropriate error message.
     */
    @Test
    void setDeviceName_FromNonNullToNull_ShouldThrowIllegalArgumentException()
    {
        // Arrange
        ControlDevice device = new ControlDevice("Coolant Level");

        // Act & Assert
        assertThatThrownBy(() -> device.setDeviceName(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Device name cannot be null or empty.");
    }

    /* Method Name: setDeviceName_FromNullToNull_ShouldThrowIllegalArgumentException
     * Method Author: Brooke Cronin
     * Test Case ID: UNIT_CD_017_REQ_008
     * Description: Verifies that the setDeviceName method throws an IllegalArgumentException when attempting to set the deviceName to null while it is already null.
     * Inputs: newDeviceName: null (String - invalid deviceName)
     * Output: IllegalArgumentException with an appropriate error message.
     */
    @Test
    void setDeviceName_FromNullToNull_ShouldThrowIllegalArgumentException()
    {
        // Arrange
        ControlDevice device = new ControlDevice();

        // Act & Assert
        assertThatThrownBy(() -> device.setDeviceName(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Device name cannot be null or empty.");
    }

    /* Method Name: getIsActive_WithTrueState_ShouldReturnTrue
     * Method Author: Brooke Cronin
     * Test Case ID: UNIT_CD_018_REQ_006
     * Description: Tests that the getIsActive method returns true when the isActive attribute is set to true.
     * Inputs: None
     * Output: true (boolean - isActive state of the ControlDevice object)
     */
    @Test
    void getIsActive_WithTrueState_ShouldReturnTrue()
    {
        // Arrange
        ControlDevice device = new ControlDevice();
        device.setIsActive(true);

        // Act
        boolean isActive = device.getIsActive();

        // Assert
        assertThat(isActive).isTrue();
    }

    /* Method Name: setIsActive_FromFalseToTrue_ShouldUpdateIsActiveToTrue
     * Method Author: Brooke Cronin
     * Test Case ID: UNIT_CD_019_REQ_005
     * Description: Verifies that the setIsActive method updates the isActive attribute from false to true.
     * Inputs: newIsActive: true (boolean - new isActive state)
     * Output: true (boolean - updated isActive state of the ControlDevice object)
     */
    @Test
    void setIsActive_FromFalseToTrue_ShouldUpdateIsActiveToTrue()
    {
        // Arrange
        ControlDevice device = new ControlDevice();
        assertThat(device.getIsActive()).isFalse(); // Ensure initial state is false

        // Act
        device.setIsActive(true);

        // Assert
        assertThat(device.getIsActive()).isTrue();
    }

    /* Method Name: setIsActive_FromTrueToFalse_ShouldUpdateIsActiveToFalse
     * Method Author: Brooke Cronin
     * Test Case ID: UNIT_CD_020_REQ_005
     * Description: Ensures that the setIsActive method updates the isActive attribute from true to false.
     * Inputs: newIsActive: false (boolean - new isActive state)
     * Output: false (boolean - updated isActive state of the ControlDevice object)
     */
    @Test
    void setIsActive_FromTrueToFalse_ShouldUpdateIsActiveToFalse()
    {
        // Arrange
        ControlDevice device = new ControlDevice();
        device.setIsActive(true); // Ensure initial state is true
        assertThat(device.getIsActive()).isTrue(); // Verify the setup

        // Act
        device.setIsActive(false);

        // Assert
        assertThat(device.getIsActive()).isFalse();
    }

    /* Method Name: getLastFeedbackMessage_WithNonNullFeedbackMessage_ShouldReturnFeedbackMessage
     * Method Author: Brooke Cronin
     * Test Case ID: UNIT_CD_021_REQ_009
     * Description: Tests that the getLastFeedbackMessage method retrieves the lastFeedbackMessage when it is set to a non-null value.
     * Inputs: None
     * Output: "Feedback message 1" (String - lastFeedbackMessage of the ControlDevice object)
     */
    @Test
    void getLastFeedbackMessage_WithNonNullFeedbackMessage_ShouldReturnFeedbackMessage()
    {
        // Arrange
        ControlDevice device = new ControlDevice();
        String expectedMessage = "Feedback message 1";
        device.setLastFeedbackMessage(expectedMessage);

        // Act
        String actualMessage = device.getLastFeedbackMessage();

        // Assert
        assertThat(actualMessage).isEqualTo(expectedMessage);
    }

    /* Method Name: setLastFeedbackMessage_ShouldUpdateFeedbackMessage
     * Method Author: Brooke Cronin
     * Test Case ID: UNIT_CD_022_REQ_009
     * Description: Verifies that the setLastFeedbackMessage method updates the lastFeedbackMessage attribute.
     * Inputs: newLastFeedbackMessage: "Updated feedback message" (String - new feedback message)
     * Output: "Updated feedback message" (String - updated lastFeedbackMessage of the ControlDevice object)
     */
    @Test
    void setLastFeedbackMessage_ShouldUpdateFeedbackMessage()
    {
        // Arrange
        ControlDevice device = new ControlDevice();
        String initialMessage = "Feedback message 1";
        String updatedMessage = "Updated feedback message";

        device.setLastFeedbackMessage(initialMessage);
        assertThat(device.getLastFeedbackMessage()).isEqualTo(initialMessage); // Verify initial state

        // Act
        device.setLastFeedbackMessage(updatedMessage);

        // Assert
        assertThat(device.getLastFeedbackMessage()).isEqualTo(updatedMessage);
    }

    /* Method Name: getCurrentValue_WithInitializedValue_ShouldReturnCurrentValue
     * Method Author: Brooke Cronin
     * Test Case ID: UNIT_CD_023_REQ_006
     * Description: Tests that the getCurrentValue method returns the currentValue when it is set to a valid value.
     * Inputs: None
     * Output: 50.0 (double - currentValue of the ControlDevice object)
     */
    @Test
    void getCurrentValue_WithInitializedValue_ShouldReturnCurrentValue()
    {
        // Arrange
        double expectedCurrentValue = 50.0;
        ControlDevice device = new ControlDevice("Coolant Level", 10.0, 100.0, expectedCurrentValue, "liters");

        // Act
        double actualCurrentValue = device.getCurrentValue();

        // Assert
        assertThat(actualCurrentValue).isEqualTo(expectedCurrentValue);
    }

    /* Method Name: setCurrentValue_WithinRange_ShouldUpdateCurrentValue
     * Method Author: Brooke Cronin
     * Test Case ID: UNIT_CD_024_REQ_006
     * Description: Ensures that the setCurrentValue method updates the currentValue to a new valid value within the minValue and maxValue range.
     * Inputs: newCurrentValue: 70.0 (double - new current value)
     * Output: 70.0 (double - updated currentValue of the ControlDevice object)
     */
    @Test
    void setCurrentValue_WithinRange_ShouldUpdateCurrentValue()
    {
        // Arrange
        double minValue = 10.0;
        double maxValue = 100.0;
        double initialValue = 50.0;
        double newValue = 70.0;
        ControlDevice device = new ControlDevice("Coolant Level", minValue, maxValue, initialValue, "liters");
        assertThat(device.getCurrentValue()).isEqualTo(initialValue); // Verify initial state

        // Act
        device.setCurrentValue(newValue);

        // Assert
        assertThat(device.getCurrentValue()).isEqualTo(newValue);
        assertThat(device.getLastFeedbackMessage()).isEqualTo("Current value updated successfully.");
    }

    /* Method Name: setCurrentValue_BelowMinValue_ShouldNotUpdateCurrentValue
     * Method Author: Brooke Cronin
     * Test Case ID: UNIT_CD_025_REQ_008
     * Description: Verifies that the setCurrentValue method does not update the currentValue when the new value is less than minValue.
     * Inputs: newCurrentValue: 5.0 (double - invalid current value below minValue)
     * Output: 50.0 (double - unchanged currentValue of the ControlDevice object)
     */
    @Test
    void setCurrentValue_BelowMinValue_ShouldNotUpdateCurrentValue()
    {
        // Arrange
        double minValue = 10.0;
        double maxValue = 100.0;
        double initialValue = 50.0;
        double invalidValue = 5.0; // Below minValue
        ControlDevice device = new ControlDevice("Coolant Level", minValue, maxValue, initialValue, "liters");
        assertThat(device.getCurrentValue()).isEqualTo(initialValue); // Verify initial state

        // Act
        device.setCurrentValue(invalidValue);

        // Assert
        assertThat(device.getCurrentValue()).isEqualTo(initialValue); // Value should not change
        assertThat(device.getLastFeedbackMessage()).isEqualTo("Current value cannot be less than the minimum value.");
    }

    /* Method Name: setCurrentValue_AboveMaxValue_ShouldNotUpdateCurrentValue
     * Method Author: Brooke Cronin
     * Test Case ID: UNIT_CD_026_REQ_008
     * Description: Ensures that the setCurrentValue method does not update the currentValue when the new value exceeds maxValue.
     * Inputs: newCurrentValue: 110.0 (double - invalid current value above maxValue)
     * Output: 50.0 (double - unchanged currentValue of the ControlDevice object)
     */
    @Test
    void setCurrentValue_AboveMaxValue_ShouldNotUpdateCurrentValue()
    {
        // Arrange
        double minValue = 10.0;
        double maxValue = 100.0;
        double initialValue = 50.0;
        double invalidValue = 110.0; // Above maxValue
        ControlDevice device = new ControlDevice("Coolant Level", minValue, maxValue, initialValue, "liters");
        assertThat(device.getCurrentValue()).isEqualTo(initialValue); // Verify initial state

        // Act
        device.setCurrentValue(invalidValue);

        // Assert
        assertThat(device.getCurrentValue()).isEqualTo(initialValue); // Value should not change
        assertThat(device.getLastFeedbackMessage()).isEqualTo("Current value cannot be greater than the maximum value.");
    }

    /* Method Name: getTargetValue_WithInitializedValue_ShouldReturnTargetValue
     * Method Author: Brooke Cronin
     * Test Case ID: UNIT_CD_027_REQ_006
     * Description: Tests that the getTargetValue method returns the targetValue when it is set to a valid value.
     * Inputs: None
     * Output: 50.0 (double - targetValue of the ControlDevice object)
     */
    @Test
    void getTargetValue_WithInitializedValue_ShouldReturnTargetValue()
    {
        // Arrange
        double expectedTargetValue = 50.0;
        ControlDevice device = new ControlDevice("Coolant Level", 10.0, 100.0, expectedTargetValue, "liters");

        // Act
        double actualTargetValue = device.getTargetValue();

        // Assert
        assertThat(actualTargetValue).isEqualTo(expectedTargetValue);
    }

    /* Method Name: setTargetValue_WithinRange_ShouldUpdateTargetValue
     * Method Author: Brooke Cronin
     * Test Case ID: UNIT_CD_028_REQ_006
     * Description: Ensures that the setTargetValue method updates the targetValue to a new valid value within the minValue and maxValue range.
     * Inputs: newTargetValue: 70.0 (double - new target value)
     * Output: 70.0 (double - updated targetValue of the ControlDevice object)
     */
    @Test
    void setTargetValue_WithinRange_ShouldUpdateTargetValue()
    {
        // Arrange
        double minValue = 10.0;
        double maxValue = 100.0;
        double initialTargetValue = 50.0;
        double newTargetValue = 70.0;
        ControlDevice device = new ControlDevice("Coolant Level", minValue, maxValue, initialTargetValue, "liters");
        assertThat(device.getTargetValue()).isEqualTo(initialTargetValue); // Verify initial state

        // Act
        device.setTargetValue(newTargetValue);

        // Assert
        assertThat(device.getTargetValue()).isEqualTo(newTargetValue);
        assertThat(device.getLastFeedbackMessage()).isEqualTo("Target value updated successfully.");
    }

    /* Method Name: setTargetValue_BelowMinValue_ShouldNotUpdateTargetValue
     * Method Author: Brooke Cronin
     * Test Case ID: UNIT_CD_029_REQ_008
     * Description: Verifies that the setTargetValue method does not update the targetValue when the new value is less than minValue.
     * Inputs: newTargetValue: 5.0 (double - invalid target value below minValue)
     * Output: 50.0 (double - unchanged targetValue of the ControlDevice object)
     */
    @Test
    void setTargetValue_BelowMinValue_ShouldNotUpdateTargetValue()
    {
        // Arrange
        double minValue = 10.0;
        double maxValue = 100.0;
        double initialTargetValue = 50.0;
        double invalidTargetValue = 5.0; // Below minValue
        ControlDevice device = new ControlDevice("Coolant Level", minValue, maxValue, initialTargetValue, "liters");
        assertThat(device.getTargetValue()).isEqualTo(initialTargetValue); // Verify initial state

        // Act
        device.setTargetValue(invalidTargetValue);

        // Assert
        assertThat(device.getTargetValue()).isEqualTo(initialTargetValue); // Value should not change
        assertThat(device.getLastFeedbackMessage()).isEqualTo("Target value cannot be less than the minimum value.");
    }

    /* Method Name: setTargetValue_AboveMaxValue_ShouldNotUpdateTargetValue
     * Method Author: Brooke Cronin
     * Test Case ID: UNIT_CD_030_REQ_008
     * Description: Ensures that the setTargetValue method does not update the targetValue when the new value exceeds maxValue.
     * Inputs: newTargetValue: 110.0 (double - invalid target value above maxValue)
     * Output: 50.0 (double - unchanged targetValue of the ControlDevice object)
     */
    @Test
    void setTargetValue_AboveMaxValue_ShouldNotUpdateTargetValue()
    {
        // Arrange
        double minValue = 10.0;
        double maxValue = 100.0;
        double initialTargetValue = 50.0;
        double invalidTargetValue = 110.0; // Above maxValue
        ControlDevice device = new ControlDevice("Coolant Level", minValue, maxValue, initialTargetValue, "liters");
        assertThat(device.getTargetValue()).isEqualTo(initialTargetValue); // Verify initial state

        // Act
        device.setTargetValue(invalidTargetValue);

        // Assert
        assertThat(device.getTargetValue()).isEqualTo(initialTargetValue); // Value should not change
        assertThat(device.getLastFeedbackMessage()).isEqualTo("Target value cannot be greater than the maximum value.");
    }

    /* Method Name: getMinValue_WithInitializedValue_ShouldReturnMinValue
     * Method Author: Brooke Cronin
     * Test Case ID: UNIT_CD_031_REQ_006
     * Description: Tests that the getMinValue method returns the minValue when it is set to a valid value.
     * Inputs: None
     * Output: 10.0 (double - minValue of the ControlDevice object)
     */
    @Test
    void getMinValue_WithInitializedValue_ShouldReturnMinValue()
    {
        // Arrange
        double expectedMinValue = 10.0;
        ControlDevice device = new ControlDevice("Coolant Level", expectedMinValue, 100.0, 50.0, "liters");

        // Act
        double actualMinValue = device.getMinValue();

        // Assert
        assertThat(actualMinValue).isEqualTo(expectedMinValue);
    }

    /* Method Name: getMaxValue_WithInitializedValue_ShouldReturnMaxValue
     * Method Author: Brooke Cronin
     * Test Case ID: UNIT_CD_032_REQ_006
     * Description: Tests that the getMaxValue method returns the maxValue when it is set to a valid value.
     * Inputs: None
     * Output: 100.0 (double - maxValue of the ControlDevice object)
     */
    @Test
    void getMaxValue_WithInitializedValue_ShouldReturnMaxValue()
    {
        // Arrange
        double expectedMaxValue = 100.0;
        ControlDevice device = new ControlDevice("Coolant Level", 10.0, expectedMaxValue, 50.0, "liters");

        // Act
        double actualMaxValue = device.getMaxValue();

        // Assert
        assertThat(actualMaxValue).isEqualTo(expectedMaxValue);
    }

    /* Method Name: getUnit_WithInitializedValue_ShouldReturnUnit
     * Method Author: Brooke Cronin
     * Test Case ID: UNIT_CD_033_REQ_006
     * Description: Verifies that the getUnit method retrieves the unit attribute when it is set to a valid value.
     * Inputs: None
     * Output: "liters" (String - unit of the ControlDevice object)
     */
    @Test
    void getUnit_WithInitializedValue_ShouldReturnUnit()
    {
        // Arrange
        String expectedUnit = "liters";
        ControlDevice device = new ControlDevice("Coolant Level", 10.0, 100.0, 50.0, expectedUnit);

        // Act
        String actualUnit = device.getUnit();

        // Assert
        assertThat(actualUnit).isEqualTo(expectedUnit);
    }

    /* Method Name: setUnit_ShouldUpdateUnit
     * Method Author: Brooke Cronin
     * Test Case ID: UNIT_CD_034_REQ_006
     * Description: Ensures that the setUnit method updates the unit attribute to a new valid value.
     * Inputs: newUnit: "cubic meters" (String - new unit)
     * Output: "cubic meters" (String - updated unit of the ControlDevice object)
     */
    @Test
    void setUnit_ShouldUpdateUnit()
    {
        // Arrange
        String initialUnit = "liters";
        String newUnit = "cubic meters";
        ControlDevice device = new ControlDevice("Coolant Level", 10.0, 100.0, 50.0, initialUnit);
        assertThat(device.getUnit()).isEqualTo(initialUnit); // Verify initial state

        // Act
        device.setUnit(newUnit);

        // Assert
        assertThat(device.getUnit()).isEqualTo(newUnit);
        assertThat(device.getLastFeedbackMessage()).isEqualTo("Unit of measurement updated successfully.");
    }

    /* Method Name: isButtonState_WithTrueState_ShouldReturnTrue
     * Method Author: Brooke Cronin
     * Test Case ID: UNIT_CD_035_REQ_006
     * Description: Tests that the isButtonState method returns true when the buttonState attribute is set to true.
     * Inputs: None
     * Output: true (boolean - buttonState of the ControlDevice object)
     */
    @Test
    void isButtonState_WithTrueState_ShouldReturnTrue()
    {
        // Arrange
        ControlDevice device = new ControlDevice();
        device.setButtonState(true); // Set button state to true

        // Act
        boolean buttonState = device.isButtonState();

        // Assert
        assertThat(buttonState).isTrue();
    }

    /* Method Name: setButtonState_FromFalseToTrue_ShouldUpdateButtonState
     * Method Author: Brooke Cronin
     * Test Case ID: UNIT_CD_036_REQ_006
     * Description: Verifies that the setButtonState method updates the buttonState attribute from false to true.
     * Inputs: newButtonState: true (boolean - new button state)
     * Output: true (boolean - updated buttonState of the ControlDevice object)
     */
    @Test
    void setButtonState_FromFalseToTrue_ShouldUpdateButtonState()
    {
        // Arrange
        ControlDevice device = new ControlDevice();
        assertThat(device.isButtonState()).isFalse(); // Verify initial state

        // Act
        device.setButtonState(true);

        // Assert
        assertThat(device.isButtonState()).isTrue();
    }

    /* Method Name: adjustCurrentValue_TowardsHigherTargetValue_ShouldIncrementCurrentValue
     * Method Author: Brooke Cronin
     * Test Case ID: UNIT_CD_037_REQ_007
     * Description: Ensures that the adjustCurrentValue method increments the currentValue towards the targetValue when the targetValue is greater than the currentValue.
     * Inputs: increment: 10.0 (double - value to increment towards targetValue)
     * Output: Updated currentValue: 30.0 (double - incremented currentValue of the ControlDevice object)
     */
    @Test
    void adjustCurrentValue_TowardsHigherTargetValue_ShouldIncrementCurrentValue()
    {
        // Arrange
        double minValue = 0.0;
        double maxValue = 100.0;
        double initialCurrentValue = 20.0;
        double targetValue = 50.0;
        double increment = 10.0;
        ControlDevice device = new ControlDevice("Coolant Level", minValue, maxValue, initialCurrentValue, "liters");
        device.setTargetValue(targetValue);

        // Act
        device.adjustCurrentValue(increment);

        // Assert
        assertThat(device.getCurrentValue()).isEqualTo(initialCurrentValue + increment);
        assertThat(device.getLastFeedbackMessage()).isEqualTo("Successfully incremented current value towards target value.");
    }

    /* Method Name: adjustCurrentValue_TowardsLowerTargetValue_ShouldDecrementCurrentValue
     * Method Author: Brooke Cronin
     * Test Case ID: UNIT_CD_038_REQ_007
     * Description: Ensures that the adjustCurrentValue method decrements the currentValue towards the targetValue when the targetValue is less than the currentValue.
     * Inputs: decrement: 10.0 (double - value to decrement towards targetValue)
     * Output: Updated currentValue: 40.0 (double - decremented currentValue of the ControlDevice object)
     */
    @Test
    void adjustCurrentValue_TowardsLowerTargetValue_ShouldDecrementCurrentValue()
    {
        // Arrange
        double minValue = 0.0;
        double maxValue = 100.0;
        double initialCurrentValue = 50.0;
        double targetValue = 40.0;
        double decrement = 10.0;
        ControlDevice device = new ControlDevice("Coolant Level", minValue, maxValue, initialCurrentValue, "liters");
        device.setTargetValue(targetValue);

        // Act
        device.adjustCurrentValue(-decrement);

        // Assert
        assertThat(device.getCurrentValue()).isEqualTo(initialCurrentValue - decrement);
        assertThat(device.getLastFeedbackMessage()).isEqualTo("Successfully decremented current value towards target value.");
    }
}
