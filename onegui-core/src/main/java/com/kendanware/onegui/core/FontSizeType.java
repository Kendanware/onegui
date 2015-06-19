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
 * Supported font size types.
 *
 * @author Daniel Johansson, Kendanware
 * @author Kenny Colliander Nordin, Kendanware
 *
 * @since 0.0.1
 */
public enum FontSizeType {
    /** Pixel */
    PIXEL("px"),

    /** Relative, 1.0 equals 2% of screen height */
    RELATIVE("");

    FontSizeType(final String suffix) {
        this.suffix = suffix;
        this.suffixLength = suffix.length();
    }

    private final String suffix;

    private final int suffixLength;

    public String getSuffix() {
        return suffix;
    }

    static FontSizeType detectType(final String stringValue) {
        if (stringValue == null) {
            throw new IllegalArgumentException("Invalid FontSizeType: null");
        }

        if (stringValue.endsWith(PIXEL.getSuffix())) {
            return PIXEL;
        }

        return RELATIVE;
    }

    float parseValue(final String stringValue) {
        if (!stringValue.endsWith(this.getSuffix())) {
            throw new IllegalArgumentException("Invalid FontSizeType; suffix doesn't match: " + stringValue);
        }

        final int stringValueLength = stringValue.length();

        if (stringValueLength < this.suffixLength) {
            throw new IllegalArgumentException("Invalid FontSizeType: " + stringValue);
        }

        final float value = Float.valueOf(stringValue.substring(0, stringValueLength - this.suffixLength));

        return value;
    }
}
