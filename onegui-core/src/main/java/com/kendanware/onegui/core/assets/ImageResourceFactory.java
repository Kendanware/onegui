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

import com.kendanware.onegui.core.GraphicsSettings;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Load and cache images
 *
 * @author Daniel Johansson, Kendanware
 * @author Kenny Colliander Nordin, Kendanware
 *
 * @since 0.0.1
 */
public class ImageResourceFactory {

    // TODO: implement least recently used cache
    private final Map<ImageResourceKey, BufferedImage> images = new ConcurrentHashMap<>();

    private final GraphicsSettings graphicsSettings;

    public ImageResourceFactory(final GraphicsSettings graphicsSettings) {
        super();
        if (graphicsSettings == null) {
            throw new NullPointerException("GraphicsSettings is null");
        }

        this.graphicsSettings = graphicsSettings;
    }

    /**
     * Get an image of a specific size
     * 
     * @param resource
     *            the resource
     * @param width
     *            the width
     * @param height
     *            the height
     * @return the image
     * @throws IOException
     */
    public BufferedImage getImage(final String resource, final int width, final int height) throws IOException {

        final ImageResourceKey key = new ImageResourceKey(resource, width, height);
        final BufferedImage cached = this.images.get(key);

        if (cached != null) {
            return cached;
        }

        final BufferedImage bufferedImage = loadImage(resource);

        if (bufferedImage.getWidth() == width && bufferedImage.getHeight() == height) {
            this.images.put(key, bufferedImage);

            return bufferedImage;
        }

        final BufferedImage resizedImage = resizeImage(bufferedImage, width, height);

        this.images.put(key, resizedImage);

        return resizedImage;
    }

    /**
     * Get a cloned image of a specific size
     * 
     * @param resource
     *            the resource
     * @param width
     *            the width
     * @param height
     *            the height
     * @param copy
     *            true if the resource should be
     * @return the cloned image
     * @throws IOException
     */
    public BufferedImage getImageAsClone(final String resource, final int width, final int height) throws IOException {
        return this.clone(this.getImage(resource, width, height));
    }

    /**
     * Load an image from resource
     * 
     * @param resource
     *            the resource
     * @return the image
     * @throws IOException
     */
    BufferedImage loadImage(final String resource) throws IOException {
        try (final InputStream inputStream = ImageResourceFactory.class.getResourceAsStream(resource)) {
            return ImageIO.read(inputStream);
        }
    }

    /**
     * Resize a loaded image
     * 
     * @param bufferedImage
     *            the original
     * @param width
     *            the new width
     * @param height
     *            the new height
     * @return the resized image
     * @throws IOException
     * @throws IllegalArgumentException
     */
    BufferedImage resizeImage(final BufferedImage bufferedImage, final int width, final int height) throws IOException, IllegalArgumentException {

        final BufferedImage resizedImage = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
        final Graphics2D graphics2D = resizedImage.createGraphics();

        try {
            this.graphicsSettings.apply(graphics2D);

            graphics2D.drawImage(bufferedImage, 0, 0, width, height, null);

        } finally {
            graphics2D.dispose();
        }
        return resizedImage;
    }

    /**
     * Clone a buffered image
     * 
     * @param bufferedImage
     *            the image to clone
     * @return the clone
     */
    BufferedImage clone(final BufferedImage bufferedImage) {
        final ColorModel colorModel = bufferedImage.getColorModel();
        final WritableRaster raster = bufferedImage.copyData(bufferedImage.getRaster().createCompatibleWritableRaster());
        return new BufferedImage(colorModel, raster, colorModel.isAlphaPremultiplied(), null);
    }
}
