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
import java.awt.image.BufferedImage;
import java.io.IOException;

import com.kendanware.onegui.core.FontStyle;
import com.kendanware.onegui.core.GraphicsSettings;

/**
 * Contains method for retrieving assets like fonts, images, etc.
 *
 * @author Daniel Johansson, Kendanware
 * @author Kenny Colliander Nordin, Kendanware
 *
 * @since 0.0.1
 */
public class AssetHolder {

    private final FontResourceFactory fontResourceFactory;

    private final ImageResourceFactory imageResourceFactory;

    /**
     * Constructor, should be used in most cases
     */
    public AssetHolder(final GraphicsSettings graphicsSettings) {
        this(new FontResourceFactory(), new ImageResourceFactory(graphicsSettings));
    }

    /**
     * Alternative constructor for specifying specific factories.
     * 
     * @param fontResourceFactory
     * @param imageResourceFactory
     */
    public AssetHolder(final FontResourceFactory fontResourceFactory, final ImageResourceFactory imageResourceFactory) {
        super();
        this.fontResourceFactory = fontResourceFactory;
        this.imageResourceFactory = imageResourceFactory;
    }

    /**
     * Get a font in a specific size and style
     * 
     * @param resource
     *            the resource path to the .TTF
     * @param size
     *            the size in pixels
     * @param style
     *            the style
     * @return the font
     */
    public Font getFont(final String resource, final int size, final FontStyle style) {
        return this.fontResourceFactory.getFont(resource, size, style);
    }

    /**
     * Get a font in a specific size in normal/regular format
     * 
     * @param resource
     *            the resource path to the .TTF
     * @param size
     *            the size in pixels
     * @return the font
     */
    public Font getFont(final String resource, final int size) {
        return this.fontResourceFactory.getFont(resource, size, FontStyle.NORMAL);
    }

    /**
     * Get an image from resource in a specific size
     * 
     * @param resource
     *            the resource path
     * @param width
     *            the width
     * @param height
     *            the height
     * @return the image
     * @throws IOException
     *             if no image was found
     */
    public BufferedImage getImage(final String resource, final int width, final int height) throws IOException {
        return this.imageResourceFactory.getImage(resource, width, height);
    }

}
