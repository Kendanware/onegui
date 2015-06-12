package com.kendanware.onegui.core.control;

/**
 * 
 * @author Daniel Johansson, Kendanware
 * @author Kenny Colliander Nordin, Kendanware
 * 
 * @since 0.0.1
 */
public enum ButtonState {
    DEFAULT(0), PRESSED(1), HOVER(2), DISABLED(3);

    ButtonState(int index) {
        this.index = index;
    }

    private final int index;

    public int getIndex() {
        return index;
    }
}
