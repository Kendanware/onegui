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
package com.kendanware.onegui.core.renderer;

import java.awt.image.BufferedImage;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.kendanware.onegui.core.GraphicsSettings;
import com.kendanware.onegui.core.Screen;

/**
 * Holder class connected to screen. Used for storing global rendering information.
 *
 * @author Daniel Johansson, Kendanware
 * @author Kenny Colliander Nordin, Kendanware
 *
 * @since 0.0.1
 */
public class OneGuiRenderer {

    private final Screen screen;

    private final float width;

    private final float height;

    private final Map<String, ComponentInfo> componentInfos = new ConcurrentHashMap<>();

    private final Map<String, BufferedImage> renderedImages = new ConcurrentHashMap<>();

    private final GraphicsSettings graphicsSettings;

    public OneGuiRenderer(final Screen screen, final float width, final float height) {
        super();
        this.screen = screen;
        this.width = width;
        this.height = height;
        this.graphicsSettings = new GraphicsSettings();
    }

    public OneGuiRenderer(final Screen screen, final float width, final float height, final GraphicsSettings graphicsSettings) {
        super();
        this.screen = screen;
        this.width = width;
        this.height = height;
        this.graphicsSettings = graphicsSettings;
    }

    public Screen getScreen() {
        return screen;
    }

    public void update(final float tpf) {

        final float screenWidth = this.width;
        final float screenHeight = this.height;

        final ComponentInfo componentInfo = new ComponentInfo(0.0f, 0.0f, screenWidth, screenHeight, true);

        this.componentInfos.put(this.screen.getId(), componentInfo);
        RendererMath.calculateChildren(this.componentInfos, this.screen);
    }

    public BufferedImage generateImage() {
        return ComponentRendererFactory.getRenderHandler(this.screen.getClass()).render(this, this.screen,
                this.componentInfos.get(this.screen.getId()));
    }

    public Map<String, ComponentInfo> getComponentInfos() {
        return componentInfos;
    }

    public Map<String, BufferedImage> getRenderedImages() {
        return renderedImages;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public GraphicsSettings getGraphicsSettings() {
        return graphicsSettings;
    }

}
