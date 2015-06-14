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
 * Immutable object that stores a size and dimension type.
 *
 * @author Daniel Johansson, Kendanware
 * @author Kenny Colliander Nordin, Kendanware
 *
 * @since 0.0.1
 */
public final class Dimension {

    public static final Dimension ZERO = new Dimension(DimensionType.PIXEL, 0);

    private final DimensionType type;

    private final float size;

    /**
     * Constructor
     *
     * @param type
     *            the type
     * @param size
     *            the size
     */
    public Dimension(final DimensionType type, final float size) {
        super();

        if (type == null) {
            throw new IllegalArgumentException("DimensionType is null");
        }
        this.type = type;
        this.size = size;
    }

    /**
     * Construct a dimension based on a string representation.
     *
     * @param stringValue
     *            the string value with dimension type suffix
     */
    public Dimension(final String stringValue) {
        super();
        this.type = DimensionType.detectType(stringValue);
        this.size = this.type.parseSize(stringValue);
    }

    /**
     * @return the dimesion type
     */
    public DimensionType getType() {
        return this.type;
    }

    /**
     * @return the size, the type is defined by {@link #getType()}
     */
    public float getSize() {
        return this.size;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = (prime * result) + ((this.type == null) ? 0 : this.type.hashCode());
        result = (prime * result) + Float.floatToIntBits(this.size);
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        final Dimension other = (Dimension) obj;
        if (this.type != other.type) {
            return false;
        }
        if (Float.floatToIntBits(this.size) != Float.floatToIntBits(other.size)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Dimension [type=" + this.type + ", size=" + this.size + "]";
    }
}
