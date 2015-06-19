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
package com.kendanware.onegui.core.assets;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.kendanware.onegui.core.FontStyle;

/**
 * Font factory that keep instances of created fonts.
 *
 * @author Daniel Johansson, Kendanware
 * @author Kenny Colliander Nordin, Kendanware
 *
 * @since 0.0.1
 */
public class FontResourceFactory {

    private final Map<String, Font> baseFonts = new ConcurrentHashMap<>();

    private final Map<FontResourceKey, Font> fonts = new ConcurrentHashMap<>();

    /**
     * Get a specific font
     * 
     * @param resource
     *            the resource name. Must be a TrueType font.
     * @param size
     *            the size in pixels
     * @param style
     *            the font style
     * @return the font reference
     */
    public Font getFont(final String resource, final int size, final FontStyle style) {

        final FontResourceKey key = new FontResourceKey(resource, size, style);
        final Font cached = this.fonts.get(key);

        if (cached != null) {
            return cached;
        }

        Font baseFont = this.baseFonts.get(resource);

        if (baseFont == null) {
            try {
                baseFont = this.createFont(resource);
            } catch (IOException | FontFormatException e) {
                throw new RuntimeException("Failed to load font: " + resource, e);
            }
        }

        final Font derivedFont = baseFont.deriveFont(style.getStyle(), size);

        this.fonts.put(key, derivedFont);

        return derivedFont;
    }

    /**
     * Create a new font from resource
     * 
     * @param resource
     *            the resource path
     * @return the font reference
     * @throws IOException
     *             if no reference is found
     * @throws FontFormatException
     *             if the font format is invalid
     */
    protected Font createFont(final String resource) throws IOException, FontFormatException {

        try (final InputStream inputStream = this.getClass().getResourceAsStream(resource)) {
            return Font.createFont(Font.TRUETYPE_FONT, inputStream);
        }
    }
}
