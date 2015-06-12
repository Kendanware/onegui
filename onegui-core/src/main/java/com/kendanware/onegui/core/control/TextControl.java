package com.kendanware.onegui.core.control;

import com.kendanware.onegui.core.Container;
import com.kendanware.onegui.core.Control;

/**
 * 
 * @author Daniel Johansson, Kendanware
 * @author Kenny Colliander Nordin, Kendanware
 * 
 * @since 0.0.1
 */
public class TextControl extends Control {
    public TextControl(Container parent) {
        super(parent);
    }

    public TextControl(Container parent, String id) {
        super(parent, id);
    }

    private String text;

    public String getText() {
        return this.text;
    }

    public void setText(final String text) {
        this.text = text;
    }
}
