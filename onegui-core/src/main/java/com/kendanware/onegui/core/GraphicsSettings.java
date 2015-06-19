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
package com.kendanware.onegui.core;

import java.awt.Graphics2D;
import java.awt.RenderingHints;

/**
 * Immutable class containing graphics settings.
 *
 * @author Daniel Johansson, Kendanware
 * @author Kenny Colliander Nordin, Kendanware
 *
 * @since 0.0.1
 */
public final class GraphicsSettings {
    private final boolean antialias;

    private final ImageInterpolation imageInterpolation;

    private final AlphaInterpolation alphaInterpolation;

    private final Rendering rendering;

    /**
     * Default is high quality.
     */
    public GraphicsSettings() {
        super();
        this.antialias = true;
        this.imageInterpolation = ImageInterpolation.BICUBIC;
        this.alphaInterpolation = AlphaInterpolation.QUALITY;
        this.rendering = Rendering.QUALITY;
    }

    public GraphicsSettings(boolean antialias, ImageInterpolation imageInterpolation, AlphaInterpolation alphaInterpolation, Rendering rendering) {
        super();
        this.antialias = antialias;

        if (imageInterpolation == null) {
            throw new NullPointerException("ImageInterpolation is null");
        }

        if (alphaInterpolation == null) {
            throw new NullPointerException("AlphaInterpolation is null");
        }

        if (rendering == null) {
            throw new NullPointerException("Rendering is null");
        }

        this.imageInterpolation = imageInterpolation;
        this.alphaInterpolation = alphaInterpolation;
        this.rendering = rendering;
    }

    public enum ImageInterpolation {
        BILINEAR(RenderingHints.VALUE_INTERPOLATION_BILINEAR), BICUBIC(RenderingHints.VALUE_INTERPOLATION_BICUBIC), NEAREST_NEIGHBOR(
                RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);

        ImageInterpolation(final Object value) {

            this.value = value;
        }

        final Object value;
    }

    public enum AlphaInterpolation {
        DEFAULT(RenderingHints.VALUE_ALPHA_INTERPOLATION_DEFAULT), QUALITY(RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY), SPEED(
                RenderingHints.VALUE_ALPHA_INTERPOLATION_SPEED);

        AlphaInterpolation(final Object value) {

            this.value = value;
        }

        final Object value;
    }

    public enum Rendering {
        DEFAULT(RenderingHints.VALUE_RENDER_DEFAULT), QUALITY(RenderingHints.VALUE_RENDER_QUALITY), SPEED(RenderingHints.VALUE_RENDER_SPEED);

        Rendering(final Object value) {
            this.value = value;
        }

        final Object value;
    }

    public boolean isAntialias() {
        return antialias;
    }

    public ImageInterpolation getImageInterpolation() {
        return imageInterpolation;
    }

    public AlphaInterpolation getAlphaInterpolation() {
        return alphaInterpolation;
    }

    public Rendering getRendering() {
        return rendering;
    }

    public void apply(final Graphics2D graphics) {

        if (this.antialias) {
            graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        } else {
            graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
        }

        graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, this.imageInterpolation.value);
        graphics.setRenderingHint(RenderingHints.KEY_RENDERING, this.rendering.value);
        graphics.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, this.alphaInterpolation.value);
    }
}
