package com.kendanware.onegui.core;

import java.io.Serializable;

/**
 * Immutable color object containing values of RGBA as <code>float</code>s. Valid value ranges are between 0.0f and 1.0f.
 * 
 * @author Daniel Johansson, Kendanware
 * @author Kenny Colliander Nordin, Kendanware
 * 
 * @since 0.0.1
 */
public final class Color implements Serializable, Cloneable {

    private static final long serialVersionUID = -1794208483502392803L;

    private static final float MIN_VALUE = 0.0f;

    private static final float MAX_VALUE = 1.0f;

    private final float red;
    private final float green;
    private final float blue;
    private final float alpha;

    /**
     * Constructs a color
     */
    public Color() {
        super();

        this.red = MIN_VALUE;
        this.green = MIN_VALUE;
        this.blue = MIN_VALUE;
        this.alpha = MAX_VALUE;
    }

    /**
     * Constructs a color
     * 
     * @param red
     *            the red color between 0.0f and 1.0f
     * @param green
     *            the green color between 0.0f and 1.0f
     * @param blue
     *            the blue color between 0.0f and 1.0f
     * @param alpha
     *            the alpha color between 0.0f and 1.0f
     * 
     * @throws IllegalArgumentException
     *             if any value is out of range
     */
    public Color(final float red, final float green, final float blue, final float alpha) {
        super();

        this.checkRange(red, "red");
        this.checkRange(green, "green");
        this.checkRange(blue, "blue");
        this.checkRange(alpha, "alpha");

        this.red = red;
        this.green = green;
        this.blue = blue;
        this.alpha = alpha;
    }

    private void checkRange(final float value, final String color) {
        if (value < MIN_VALUE || value > MAX_VALUE) {
            throw new IllegalArgumentException("Value must be between 0.0f and 1.0f for " + color + "; " + value);
        }
    }

    /**
     * Constructs a color
     * 
     * @param color
     *            the color in hex. Format including hash <code>#RRGGBBAA</code> where <code>RR</code> is red, <code>GG</code> is green,
     *            <code>BB</code> is blue and <code>AA</code> is alpha. <br/>
     *            Example: #ff00007f (red with half alpha)
     * @throws IllegalArgumentException
     *             if the format of the submitted color is invalid
     */
    public Color(final String color) {

        if (color != null && color.matches("\\#[0-9a-fA-F]{8}")) {
            final long parsed = Long.decode(color).intValue();

            this.red = ((float) (parsed >> 24 & 255)) / 255f;
            this.green = ((float) (parsed >> 16 & 255)) / 255f;
            this.blue = ((float) (parsed >> 8 & 255)) / 255f;
            this.alpha = ((float) (parsed & 255)) / 255f;

        } else {
            throw new IllegalArgumentException("Invalid color format: " + color);
        }
    }

    /**
     * @return the red value in range from 0.0f to 1.0f
     */
    public float getRed() {
        return red;
    }

    /**
     * @return the green value in range from 0.0f to 1.0f
     */
    public float getGreen() {
        return green;
    }

    /**
     * @return the blue value in range from 0.0f to 1.0f
     */
    public float getBlue() {
        return blue;
    }

    /**
     * @return the alpha value in range from 0.0f to 1.0f
     */
    public float getAlpha() {
        return alpha;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Float.floatToIntBits(alpha);
        result = prime * result + Float.floatToIntBits(blue);
        result = prime * result + Float.floatToIntBits(green);
        result = prime * result + Float.floatToIntBits(red);
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Color other = (Color) obj;
        if (Float.floatToIntBits(alpha) != Float.floatToIntBits(other.alpha))
            return false;
        if (Float.floatToIntBits(blue) != Float.floatToIntBits(other.blue))
            return false;
        if (Float.floatToIntBits(green) != Float.floatToIntBits(other.green))
            return false;
        if (Float.floatToIntBits(red) != Float.floatToIntBits(other.red))
            return false;
        return true;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return this;
    }

    @Override
    public String toString() {
        return "Color [red=" + this.red + ", green=" + this.green + ", blue=" + this.blue + ", alpha=" + this.alpha + "]";
    }
}
