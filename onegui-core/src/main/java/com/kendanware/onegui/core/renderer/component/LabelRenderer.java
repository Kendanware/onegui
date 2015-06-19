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

import static com.kendanware.onegui.core.renderer.RendererMath.getMarginPaddingHeight;
import static com.kendanware.onegui.core.renderer.RendererMath.getMarginPaddingWidth;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import com.kendanware.onegui.core.Align;
import com.kendanware.onegui.core.Component;
import com.kendanware.onegui.core.VerticalAlign;
import com.kendanware.onegui.core.control.Label;
import com.kendanware.onegui.core.renderer.ComponentInfo;
import com.kendanware.onegui.core.renderer.OneGuiRenderer;
import com.kendanware.onegui.core.renderer.RendererMath;
import com.kendanware.onegui.core.renderer.RenderingState;
import com.kendanware.onegui.core.style.Style;

/**
 * Renderer for the <code>Label</code> component.
 *
 * @author Daniel Johansson, Kendanware
 * @author Kenny Colliander Nordin, Kendanware
 *
 * @since 0.0.1
 */
public class LabelRenderer extends DefaultComponentRenderer {

    @Override
    protected void render(OneGuiRenderer oneGuiRenderer, Component component, ComponentInfo componentInfo, float width, float height,
            BufferedImage bufferedImage, Graphics2D graphics) {
        final Style style = component.getStyle();
        final Label label = (Label) component;

        this.drawBackground(component, width, height, graphics);

        graphics.setColor(RendererMath.toColor(style.getColor()));

        final int fontHeight = RendererMath.getFontHeight(oneGuiRenderer.getHeight(), style.getFontSize());

        final Font font = component.getOneGui().getAssetHolder().getFont(style.getFont(), fontHeight, style.getFontStyle());

        graphics.setFont(font);

        oneGuiRenderer.getGraphicsSettings().apply(graphics);

        final int textWidth = graphics.getFontMetrics().stringWidth(label.getText());
        final int textHeight = graphics.getFontMetrics().getHeight();

        final float x;
        final float y;

        final float paddingLeft = getMarginPaddingWidth(component.getStyle(), component.getStyle().getPaddingLeft(), width, height);
        final float paddingRight = getMarginPaddingWidth(component.getStyle(), component.getStyle().getPaddingRight(), width, height);
        final float paddingTop = getMarginPaddingHeight(component.getStyle(), component.getStyle().getPaddingTop(), width, height);
        final float paddingBottom = getMarginPaddingHeight(component.getStyle(), component.getStyle().getPaddingBottom(), width, height);

        if (style.getAlign() == Align.LEFT) {
            // Left to right
            x = paddingLeft;
        } else if (style.getAlign() == Align.RIGHT) {
            // Right to left
            x = width - paddingRight - textWidth;
        } else {
            x = (width / 2) - (textWidth / 2) + (paddingLeft - paddingRight);
        }

        if (style.getVerticalAlign() == VerticalAlign.TOP) {
            // Top to bottom
            y = paddingTop + fontHeight;
        } else if (style.getVerticalAlign() == VerticalAlign.BOTTOM) {
            // Right to left
            y = height - (paddingBottom - (fontHeight - textHeight));
        } else {
            y = (height / 2) + (textHeight / 4) + (paddingTop - paddingBottom);
        }

        graphics.drawString(label.getText(), x, y);
    }

    @Override
    public RenderingState getState(OneGuiRenderer oneGuiRenderer, Component component, ComponentInfo componentInfo) {
        Label label = (Label) component;
        return new LabelRenderingState(label.getText());
    }

    private static class LabelRenderingState implements RenderingState {
        private final String text;

        public LabelRenderingState(String text) {
            super();
            this.text = text;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + ((text == null) ? 0 : text.hashCode());
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            LabelRenderingState other = (LabelRenderingState) obj;
            if (text == null) {
                if (other.text != null)
                    return false;
            } else if (!text.equals(other.text))
                return false;
            return true;
        }
    }

}
