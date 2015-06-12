package com.kendanware.onegui.core;

/**
 * Describes all available dimesion types.
 * 
 * @author Daniel Johansson, Kendanware
 * @author Kenny Colliander Nordin, Kendanware
 * 
 * @since 0.0.1
 */
public enum DimensionType {
    PIXEL("px", -32768f, 32767f), PERCENT("%", 0.0f, 100.0f), PERCENT_WIDTH("%w", 0.0f, 100.0f), PERCENT_HEIGHT("%h", 0.0f, 100.0f);

    /**
     * @param suffix
     *            the suffix
     * @param minValue
     *            the min value
     * @param maxValue
     *            the max value
     */
    DimensionType(final String suffix, final float minValue, final float maxValue) {
        this.suffix = suffix;
        this.suffixLength = suffix.length();
        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    private final String suffix;

    private final int suffixLength;

    private float minValue;

    private float maxValue;

    /**
     * @return the suffix related to the dimension
     */
    public String getSuffix() {
        return suffix;
    }

    /**
     * Detect a dimension type depending on suffix
     * 
     * @param stringValue
     *            the string to detect
     * @return the type
     * @throws IllegalArgumentException
     *             if <code>stringValue</code> is null or if no dimension could be matched.
     */
    public static DimensionType detectType(final String stringValue) {

        if (stringValue == null) {
            throw new IllegalArgumentException("Invalid dimension: null");
        }

        for (DimensionType type : DimensionType.values()) {
            if (stringValue.endsWith(type.getSuffix())) {
                return type;
            }
        }

        throw new IllegalArgumentException("Invalid dimension: " + stringValue);
    }

    /**
     * Parse a size from a string value.
     * 
     * @param stringValue
     *            the string value
     * @return the size
     * @throws IllegalArgumentException
     *             if <code>stringValue</code> is null, or if no dimension could be matched, or size is out of range.
     */
    public float parseSize(final String stringValue) {

        if (stringValue == null) {
            throw new IllegalArgumentException("Invalid dimension: null");
        }

        if (!stringValue.endsWith(getSuffix())) {
            throw new IllegalArgumentException("Invalid dimension; suffix doesn't match: " + stringValue);
        }

        final int stringValueLength = stringValue.length();

        if (stringValueLength < this.suffixLength) {
            throw new IllegalArgumentException("Invalid dimension: " + stringValue);
        }

        final float value = Float.valueOf(stringValue.substring(0, stringValueLength - this.suffixLength));

        if (value < this.minValue || value > this.maxValue) {
            throw new IllegalArgumentException("Invalid dimension; must be a value between " + minValue + " and " + maxValue + ": " + stringValue);
        }

        return value;
    }
}
