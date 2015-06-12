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
        return type;
    }

    /**
     * @return the size, the type is defined by {@link #getType()}
     */
    public float getSize() {
        return size;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((type == null) ? 0 : type.hashCode());
        result = prime * result + Float.floatToIntBits(size);
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
        Dimension other = (Dimension) obj;
        if (type != other.type)
            return false;
        if (Float.floatToIntBits(size) != Float.floatToIntBits(other.size))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Dimension [type=" + type + ", size=" + size + "]";
    }
}
