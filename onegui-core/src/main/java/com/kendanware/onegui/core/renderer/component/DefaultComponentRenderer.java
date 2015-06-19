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
package com.kendanware.onegui.core.renderer.component;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import com.kendanware.onegui.core.Color;
import com.kendanware.onegui.core.Component;
import com.kendanware.onegui.core.renderer.ComponentInfo;
import com.kendanware.onegui.core.renderer.ComponentRenderer;
import com.kendanware.onegui.core.renderer.OneGuiRenderer;
import com.kendanware.onegui.core.renderer.RendererMath;
import com.kendanware.onegui.core.renderer.RenderingState;
import com.kendanware.onegui.core.style.Style;

/**
 * The default implementaton of a <code>ComponentRenderer</code>. Draw background color or image, and traverse children if the component is an
 * instance of <code>Container</code>.
 *
 * @author Daniel Johansson, Kendanware
 * @author Kenny Colliander Nordin, Kendanware
 *
 * @since 0.0.1
 */
public abstract class DefaultComponentRenderer implements ComponentRenderer {

    @Override
    public BufferedImage render(final OneGuiRenderer oneGuiRenderer, Component component, ComponentInfo componentInfo) {

        final RenderingState renderingState = getState(oneGuiRenderer, component, componentInfo);

        if (renderingState.equals(oneGuiRenderer.getLastState().get(component.getId()))) {
            final BufferedImage cachedImage = oneGuiRenderer.getRenderedImages().get(component.getId());

            if (cachedImage != null) {
                return cachedImage;
            }
        }

        final int width = Math.round(componentInfo.getWidth());
        final int height = Math.round(componentInfo.getHeight());

        final BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        final Graphics2D graphics = bufferedImage.createGraphics();
        oneGuiRenderer.getGraphicsSettings().apply(graphics);

        this.render(oneGuiRenderer, component, componentInfo, width, height, bufferedImage, graphics);

        graphics.dispose();
        oneGuiRenderer.getRenderedImages().put(component.getId(), bufferedImage);
        renderingState.equals(oneGuiRenderer.getLastState().put(component.getId(), renderingState));
        return bufferedImage;
    }

    protected void drawBackground(final Component component, final float width, final float height, final Graphics2D graphics) {
        final Style style = component.getStyle();
        final Color backgroundColor = style.getBackgroundColor();

        if (style.getBackgroundImage() != null) {
            try {
                final BufferedImage backgroundImage = component.getOneGui().getAssetHolder()
                        .getImage(style.getBackgroundImage(), Math.round(width), Math.round(height));
                graphics.drawImage(backgroundImage, 0, 0, RendererMath.toColor(backgroundColor), null);
            } catch (IOException e) {
                throw new RuntimeException("Failed to load " + style.getBackgroundImage(), e);
            }
        } else {
            graphics.setColor(RendererMath.toColor(backgroundColor));
            graphics.fillRect(0, 0, Math.round(width), Math.round(height));
        }
    }

    protected abstract void render(OneGuiRenderer oneGuiRenderer, Component component, ComponentInfo componentInfo, float width, float height,
            BufferedImage bufferedImage, Graphics2D graphics);

}
