/*
 * Copyright (c) 2015 Kendanware
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * 3. Neither the name of onegui, Kendanware nor the names of its
 *    contributors may be used to endorse or promote products derived from
 *    this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
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
        return this.suffix;
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

        for (final DimensionType type : DimensionType.values()) {
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

        if (!stringValue.endsWith(this.getSuffix())) {
            throw new IllegalArgumentException("Invalid dimension; suffix doesn't match: " + stringValue);
        }

        final int stringValueLength = stringValue.length();

        if (stringValueLength < this.suffixLength) {
            throw new IllegalArgumentException("Invalid dimension: " + stringValue);
        }

        final float value = Float.valueOf(stringValue.substring(0, stringValueLength - this.suffixLength));

        if ((value < this.minValue) || (value > this.maxValue)) {
            throw new IllegalArgumentException("Invalid dimension; must be a value between " + this.minValue + " and " + this.maxValue + ": "
                    + stringValue);
        }

        return value;
    }
}
