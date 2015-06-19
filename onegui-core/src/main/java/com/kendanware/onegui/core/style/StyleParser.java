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
package com.kendanware.onegui.core.style;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kendanware.onegui.core.Align;
import com.kendanware.onegui.core.ChildLayout;
import com.kendanware.onegui.core.Color;
import com.kendanware.onegui.core.Dimension;
import com.kendanware.onegui.core.FontSize;
import com.kendanware.onegui.core.FontStyle;
import com.kendanware.onegui.core.VerticalAlign;

/**
 * Parse onegui style files
 *
 * @author Daniel Johansson, Kendanware
 * @author Kenny Colliander Nordin, Kendanware
 *
 * @since 0.0.1
 */
public class StyleParser {

    private static final Logger LOG = LoggerFactory.getLogger(StyleParser.class);

    private static final Dimension DIMESION_100_PERCENT = new Dimension("100%");

    private static final Set<String> AVAILABLE_PROPERTIES = Collections.unmodifiableSet(new HashSet<>(Arrays.asList(StyleProperty.HEIGHT,
            StyleProperty.WIDTH, StyleProperty.MARGIN_BOTTOM, StyleProperty.MARGIN_LEFT, StyleProperty.MARGIN_RIGHT, StyleProperty.MARGIN_TOP,
            StyleProperty.PADDING_BOTTOM, StyleProperty.PADDING_LEFT, StyleProperty.PADDING_RIGHT, StyleProperty.PADDING_TOP,
            StyleProperty.BACKGROUND_IMAGE, StyleProperty.BACKGROUND_COLOR, StyleProperty.COLOR, StyleProperty.FONT, StyleProperty.FONT_SIZE,
            StyleProperty.FONT_STYLE, StyleProperty.ALIGN, StyleProperty.VERTICAL_ALIGN, StyleProperty.CHILD_LAYOUT)));

    /**
     * Different parsing states
     */
    protected enum State {
        STYLE_NAME, PROPERTY, VALUE, COMMENT
    }

    /**
     * Parse a onegui style file (.ogs)
     *
     * @param inputStream
     *            the stream to parse using UTF-8 encoding, note that the stream needs to be closed.
     * @return a map with all compiled styles
     * @throws IOException
     */
    public Map<String, Style> parseStyle(final InputStream inputStream) throws IOException {
        return this.parseStyle(new InputStreamReader(inputStream, "UTF-8"));
    }

    /**
     * Parse a onegui style file (.ogs)
     *
     * @param inputStreamReader
     *            the stream to parse, note that the stream needs to be closed.
     * @return a map with all compiled styles
     * @throws IOException
     */
    public Map<String, Style> parseStyle(final InputStreamReader inputStreamReader) throws IOException {

        if (inputStreamReader == null) {
            throw new IllegalArgumentException("Parameter inputStream is null");
        }

        final Map<String, Style> styles = new ConcurrentHashMap<>();

        final Map<String, Map<String, String>> rawData = new HashMap<>();

        final List<String> currentStyles = new ArrayList<String>();
        StringBuilder buffer = new StringBuilder();

        State state = State.STYLE_NAME;
        String property = null;

        int i;
        int lineNumber = 1;
        int columnNumber = 0;
        boolean lastCharWasSlash = false;
        State lastState = null;

        while ((i = inputStreamReader.read()) != -1) {
            columnNumber++;

            if (i == '/') {
                if (lastCharWasSlash) {
                    lastState = state;
                    state = State.COMMENT;
                    lastCharWasSlash = false;
                } else {
                    lastCharWasSlash = true;
                }
                continue;
            } else if (lastCharWasSlash) {
                buffer.append("/");
                lastCharWasSlash = false;
            }

            if (state == State.COMMENT) {
                if (i == '\n') {
                    state = lastState;
                    lineNumber++;
                    columnNumber = 0;
                }
                continue;
            }

            if (i == ':') { // end of style property name

                if (state != State.PROPERTY) {
                    throw new IllegalArgumentException("Misplaced ':' at line " + lineNumber + " and column " + columnNumber);
                }

                property = this.getProperty(buffer, lineNumber, columnNumber);
                buffer = new StringBuilder();
                state = State.VALUE;

            } else if (i == ';') { // end of style value

                if (state != State.VALUE) {
                    throw new IllegalArgumentException("Misplaced ';' at line " + lineNumber + " and column " + columnNumber);
                }

                final String value = this.getValue(buffer, lineNumber, columnNumber);

                this.addStyleProperty(currentStyles, rawData, property, value, lineNumber, columnNumber);

                property = null;
                buffer = new StringBuilder();
                state = State.PROPERTY;
            } else if (i == '{') { // start of block definition

                this.addStyleId(currentStyles, buffer);
                buffer = new StringBuilder();
                state = State.PROPERTY;

            } else if (i == '}') { // end of block definition

                if (state != State.PROPERTY) {
                    throw new IllegalArgumentException("Misplaced '}' at line " + lineNumber + " and column " + columnNumber);
                }

                property = null;
                currentStyles.clear();
                buffer = new StringBuilder();
                state = State.STYLE_NAME;

            } else if (i == ',') { // separate style property name

                if (state != State.STYLE_NAME) {
                    throw new IllegalArgumentException("Misplaced ',' at line " + lineNumber + " and column " + columnNumber);
                }

                this.addStyleId(currentStyles, buffer);
                buffer = new StringBuilder();

            } else if (i == '\t') { // tab
                buffer.append(" ");
            } else if (i == '\r') { // new line
                buffer.append(" ");
            } else if (i == '\n') { // new line
                buffer.append(" ");
                lineNumber++;
                columnNumber = 0;
            } else {
                buffer.append((char) i);
            }
        }

        // Produce style objects from raw data
        for (final Map.Entry<String, Map<String, String>> entry : rawData.entrySet()) {
            styles.put(entry.getKey(), this.getStyle(entry.getValue()));
        }

        return Collections.unmodifiableMap(styles);
    }

    private void addStyleProperty(final List<String> currentStyles, final Map<String, Map<String, String>> data, final String property,
            final String value, final int lineNumber, final int columnNumber) {
        if (currentStyles.isEmpty()) {
            throw new IllegalArgumentException("No style name found at line " + lineNumber + " and column " + columnNumber);
        }

        if (!StyleParser.AVAILABLE_PROPERTIES.contains(property)) {
            throw new IllegalArgumentException("Invalid property name \"" + property + "\" at line " + lineNumber + " and column " + columnNumber);
        }

        for (final String styleName : currentStyles) {
            Map<String, String> properties = data.get(styleName);
            if (properties == null) {
                properties = new HashMap<>();
                data.put(styleName, properties);
            }

            StyleParser.LOG.trace("Add style property {} and value {} for style name {}", property, value, styleName);
            properties.put(property, value);
        }
    }

    protected void addStyleId(final List<String> currentStyles, final StringBuilder buffer) {
        final String name = buffer.toString().trim();

        if (!name.isEmpty()) {
            currentStyles.add(name);
        }
    }

    protected String getProperty(final StringBuilder buffer, final int lineNumber, final int columnNumber) {
        final String property = buffer.toString().trim();

        if (property.isEmpty()) {
            throw new IllegalArgumentException("No property name found at line " + lineNumber + " and column " + columnNumber);
        }

        return property;
    }

    protected String getValue(final StringBuilder buffer, final int lineNumber, final int columnNumber) {
        final String value = buffer.toString().trim();

        if (value.isEmpty()) {
            throw new IllegalArgumentException("No property value found at line " + lineNumber + " and column " + columnNumber);
        }

        return value;
    }

    protected Style getStyle(final Map<String, String> properties) {

        return new Style(this.getDimension(properties, StyleProperty.WIDTH, StyleParser.DIMESION_100_PERCENT), // width
                this.getDimension(properties, StyleProperty.HEIGHT, StyleParser.DIMESION_100_PERCENT), // height
                this.getColor(properties, StyleProperty.COLOR, Color.WHITE), // color
                this.getBackgroundImage(properties), // background image
                this.getColor(properties, StyleProperty.BACKGROUND_COLOR, Color.TRANSPARENT), // background color
                this.getDimension(properties, StyleProperty.PADDING_LEFT, Dimension.ZERO), // padding left
                this.getDimension(properties, StyleProperty.PADDING_RIGHT, Dimension.ZERO), // padding right
                this.getDimension(properties, StyleProperty.PADDING_BOTTOM, Dimension.ZERO), // padding bottom
                this.getDimension(properties, StyleProperty.PADDING_TOP, Dimension.ZERO), // padding top
                this.getDimension(properties, StyleProperty.MARGIN_LEFT, Dimension.ZERO), // margin left
                this.getDimension(properties, StyleProperty.MARGIN_RIGHT, Dimension.ZERO), // margin right
                this.getDimension(properties, StyleProperty.MARGIN_BOTTOM, Dimension.ZERO), // margin bottom
                this.getDimension(properties, StyleProperty.MARGIN_TOP, Dimension.ZERO), // margin top
                this.getChildLayout(properties), // child layout
                this.getAlign(properties), // align
                this.getVerticalAlign(properties), // vertical align
                this.getFont(properties), // font
                this.getFontSize(properties), // font size
                this.getFontStyle(properties) // font style
        );
    }

    protected Color getColor(final Map<String, String> properties, final String property, final Color defaultValue) {
        final String value = properties.get(property);
        if ((value == null) || value.isEmpty()) {
            return defaultValue;
        }

        return new Color(value);
    }

    protected Dimension getDimension(final Map<String, String> properties, final String property, final Dimension defaultValue) {

        final String value = properties.get(property);
        if ((value == null) || value.isEmpty()) {
            return defaultValue;
        }

        return new Dimension(value);
    }

    protected String getBackgroundImage(final Map<String, String> properties) {
        final String value = properties.get(StyleProperty.BACKGROUND_IMAGE);

        if ((value == null) || value.isEmpty()) {
            return null;
        }

        if ("none".equals(value) || "null".equals(value)) {
            return null;
        }

        if (value.startsWith("\"") && value.endsWith("\"")) {
            if (value.length() > 3) {
                return value.substring(1, value.length() - 1);
            }
        }
        return null;
    }

    protected String getFont(final Map<String, String> properties) {
        final String value = properties.get(StyleProperty.FONT);

        if ((value == null) || value.isEmpty()) {
            return null;
        }

        if (value.startsWith("\"") && value.endsWith("\"")) {
            if (value.length() > 3) {
                return value.substring(1, value.length() - 1);
            }
        }
        return null;
    }

    protected FontSize getFontSize(final Map<String, String> properties) {

        final String value = properties.get(StyleProperty.FONT_SIZE);
        if ((value == null) || value.isEmpty()) {
            return FontSize.DEFAULT_SIZE;
        }

        return new FontSize(value);
    }

    protected FontStyle getFontStyle(final Map<String, String> properties) {

        final String value = properties.get(StyleProperty.FONT_STYLE);
        if ((value == null) || value.isEmpty()) {
            return FontStyle.NORMAL;
        }

        return FontStyle.valueOf(value.toUpperCase(Locale.US));
    }

    protected Align getAlign(final Map<String, String> properties) {

        final String value = properties.get(StyleProperty.ALIGN);
        if ((value == null) || value.isEmpty()) {
            return Align.LEFT;
        }

        return Align.valueOf(value.toUpperCase(Locale.US));
    }

    protected VerticalAlign getVerticalAlign(final Map<String, String> properties) {

        final String value = properties.get(StyleProperty.VERTICAL_ALIGN);
        if ((value == null) || value.isEmpty()) {
            return VerticalAlign.TOP;
        }

        return VerticalAlign.valueOf(value.toUpperCase(Locale.US));
    }

    protected ChildLayout getChildLayout(final Map<String, String> properties) {

        final String value = properties.get(StyleProperty.CHILD_LAYOUT);
        if ((value == null) || value.isEmpty()) {
            return ChildLayout.RIGHT;
        }

        return ChildLayout.valueOf(value.toUpperCase(Locale.US));
    }
}
