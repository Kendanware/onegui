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

    public Button(final Container parent) {
        super(parent);
    }

    public Button(final Container parent, final String id) {
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

    public void setButtonState(final ButtonState buttonState) {
        this.buttonState = buttonState;
    }

}
