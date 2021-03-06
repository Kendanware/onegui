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
import java.util.ArrayList;
import java.util.List;

import com.kendanware.onegui.core.Component;
import com.kendanware.onegui.core.Container;
import com.kendanware.onegui.core.renderer.ComponentInfo;
import com.kendanware.onegui.core.renderer.ComponentRendererFactory;
import com.kendanware.onegui.core.renderer.OneGuiRenderer;
import com.kendanware.onegui.core.renderer.RenderingState;

/**
 * Renderer for the <code>Container</code> subclass component.
 *
 * @author Daniel Johansson, Kendanware
 * @author Kenny Colliander Nordin, Kendanware
 *
 * @since 0.0.1
 */

public class ContainerRenderer extends DefaultComponentRenderer {

    @Override
    protected void render(OneGuiRenderer oneGuiRenderer, Component component, ComponentInfo componentInfo, float width, float height,
            BufferedImage bufferedImage, Graphics2D graphics) {

        this.drawBackground(component, width, height, graphics);
        this.renderChildren(oneGuiRenderer, component, graphics);
    }

    protected void renderChildren(OneGuiRenderer oneGuiRenderer, Component component, Graphics2D graphics) {
        final List<Component> children = ((Container) component).getChildren();

        for (final Component child : children) {

            final ComponentInfo childRendererInfo = oneGuiRenderer.getComponentInfos().get(child.getId());
            final BufferedImage childImage = ComponentRendererFactory.getRenderHandler(child.getClass()).render(oneGuiRenderer, child,
                    childRendererInfo);

            graphics.setColor(new java.awt.Color(0.0f, 0.0f, 0.0f, 0.1f));
            graphics.drawImage(childImage, Math.round(childRendererInfo.getX()), Math.round(childRendererInfo.getY()), null);
        }
    }

    @Override
    public RenderingState getState(OneGuiRenderer oneGuiRenderer, Component component, ComponentInfo componentInfo) {

        List<RenderingState> renderingStates = new ArrayList<RenderingState>();
        final List<Component> children = ((Container) component).getChildren();

        for (final Component child : children) {

            final ComponentInfo childRendererInfo = oneGuiRenderer.getComponentInfos().get(child.getId());
            final RenderingState state = ComponentRendererFactory.getRenderHandler(child.getClass()).getState(oneGuiRenderer, child,
                    childRendererInfo);

            renderingStates.add(state);
        }

        return new ContainerRenderingState(renderingStates);
    }

    private static class ContainerRenderingState implements RenderingState {
        private final List<RenderingState> renderingStates;

        public ContainerRenderingState(List<RenderingState> renderingStates) {
            super();
            this.renderingStates = renderingStates;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + ((renderingStates == null) ? 0 : renderingStates.hashCode());
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
            ContainerRenderingState other = (ContainerRenderingState) obj;
            if (renderingStates == null) {
                if (other.renderingStates != null)
                    return false;
            } else if (!renderingStates.equals(other.renderingStates))
                return false;
            return true;
        }
    }
}
