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

import java.util.MissingResourceException;
import java.util.ResourceBundle;

import com.kendanware.onegui.core.Container;
import com.kendanware.onegui.core.Control;

/**
 * The Label component is used for printing a text. The text may be set directly or via a <code>ResourceBundle</code>
 *
 * @author Daniel Johansson, Kendanware
 * @author Kenny Colliander Nordin, Kendanware
 *
 * @since 0.0.1
 */
public class Label extends Control {

    private static final String DEFAULT_TEXT = "";

    private String text = DEFAULT_TEXT;

    /**
     * Construct a new label with a generated id
     * 
     * @param parent
     *            the parent container
     */
    public Label(final Container parent) {
        super(parent);
    }

    /**
     * Construct a new label
     * 
     * @param parent
     *            the parent container
     * @param id
     *            the id
     */
    public Label(final Container parent, final String id) {
        super(parent, id);
    }

    /**
     * Construct a new label
     * 
     * @param parent
     *            the parent container
     * @param id
     *            the id
     * @param text
     *            the text
     */
    public Label(final Container parent, final String id, final String text) {
        super(parent, id);
        this.setText(text);
    }

    /**
     * Construct a new label
     * 
     * @param parent
     *            the parent container
     * @param id
     *            the id
     * @param baseName
     *            the baseName for a {@link ResourceBundle}
     * @param the
     *            key the key <code>ResourceBundle</code>
     * @throws MissingResourceException
     *             if no resource could be found
     */
    public Label(final Container parent, final String id, final String baseName, final String key) {
        super(parent, id);
        this.setText(baseName, key);
    }

    /**
     * Construct a new label
     * 
     * @param parent
     *            the parent container
     * @param id
     *            the id
     * @param resourceBundle
     *            the <code>ResourceBundle</code>
     * @param the
     *            key the key for the <code>ResourceBundle</code>
     * @throws MissingResourceException
     *             if no resource could be found
     */
    public Label(final Container parent, final String id, final ResourceBundle resourceBundle, final String key) {
        super(parent, id);
        this.setText(resourceBundle, key);
    }

    /**
     * Set a text to be displayed
     * 
     * @param text
     *            the text
     */
    public void setText(final String text) {

        if (text == null) {
            throw new NullPointerException("Parameter text is null");
        }

        this.text = text;
    }

    /**
     * Set a text to be displayed using a <code>ResourceBundle</code>
     * 
     * @param baseName
     *            the base name for the resource
     * @param key
     *            the key
     * @throws MissingResourceException
     *             if no resource could be found
     */
    public void setText(final String baseName, final String key) {
        final ResourceBundle resourceBundle = ResourceBundle.getBundle(baseName);

        this.setText(resourceBundle, key);
    }

    /**
     * Set a text to be displayed using a <code>ResourceBundle</code>
     * 
     * @param resourceBundle
     *            the resource bundle
     * @param key
     *            the key
     * @throws MissingResourceException
     *             if no resource could be found
     */
    public void setText(final ResourceBundle resourceBundle, final String key) {

        if (resourceBundle == null) {
            throw new NullPointerException("ResourceBundle is null");
        }

        this.setText(resourceBundle.getString(key));
    }

    /**
     * @return the text
     */
    public String getText() {
        return this.text;
    }
}
