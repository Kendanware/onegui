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
 * Define a font size. Supported types are pixel and relative to screen. 1 relative unit equals 2% of screen height.
 *
 * @author Daniel Johansson, Kendanware
 * @author Kenny Colliander Nordin, Kendanware
 *
 * @since 0.0.1
 */
public final class FontSize {

    public static final FontSize DEFAULT_SIZE = new FontSize(1.0f, FontSizeType.RELATIVE);

    private final float size;

    private final FontSizeType type;

    /**
     * Constructor
     * 
     * @param size
     *            the size depending on type
     * @param type
     *            the type
     */
    public FontSize(final float size, final FontSizeType type) {
        super();

        if (size <= 0.0f) {
            throw new IllegalArgumentException("Negative size is not allowed: " + size);
        }

        this.size = size;
        this.type = type;
    }

    /**
     * Constructor
     * 
     * @param stringValue
     *            the string to parse
     */
    public FontSize(final String stringValue) {
        super();

        this.type = FontSizeType.detectType(stringValue);
        this.size = this.type.parseValue(stringValue);

        if (this.size <= 0.0f) {
            throw new IllegalArgumentException("Negative size is not allowed: " + size);
        }

    }

    /**
     * @return the size depending on <code>getType</code>
     */
    public float getSize() {
        return size;
    }

    /**
     * @return the type
     */
    public FontSizeType getType() {
        return type;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Float.floatToIntBits(size);
        result = prime * result + ((type == null) ? 0 : type.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        FontSize other = (FontSize) obj;
        if (Float.floatToIntBits(size) != Float.floatToIntBits(other.size))
            return false;
        if (type != other.type)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "FontSize [size=" + size + ", type=" + type + "]";
    }
}
