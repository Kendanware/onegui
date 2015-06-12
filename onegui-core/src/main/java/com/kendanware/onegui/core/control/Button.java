package com.kendanware.onegui.core.control;

import com.kendanware.onegui.core.Container;
import com.kendanware.onegui.core.Control;
import com.kendanware.onegui.core.style.Style;

/**
 * 
 * @author Daniel Johansson, Kendanware
 * @author Kenny Colliander Nordin, Kendanware
 * 
 * @since 0.0.1
 */
public class Button extends Control {

    private final Style[] styles = new Style[ButtonState.values().length];

    private ButtonState buttonState;

    public Button(Container parent) {
        super(parent);
    }

    public Button(Container parent, String id) {
        super(parent, id);
    }

    public void setStyle(final ButtonState buttonState, final Style style) {

        if (buttonState == null) {
            throw new IllegalArgumentException("Parameter buttonState is null");
        }

        this.styles[buttonState.getIndex()] = style;
    }

    public Style getStyle(final ButtonState buttonState) {
        if (buttonState == null) {
            throw new IllegalArgumentException("Parameter buttonState is null");
        }

        return this.styles[buttonState.getIndex()];
    }

    public String getImage(final ButtonState buttonState) {
        if (buttonState == null) {
            throw new IllegalArgumentException("Parameter buttonState is null");
        }
        // TODO: implement
        return null;
    }

    public ButtonState getState() {
        return this.buttonState;
    }

    public void setButtonState(ButtonState buttonState) {
        this.buttonState = buttonState;
    }

}
