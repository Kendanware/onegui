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

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.kendanware.onegui.core.Screen;
import com.kendanware.onegui.core.container.Panel;
import com.kendanware.onegui.core.control.Button;
import com.kendanware.onegui.core.control.Label;
import com.kendanware.onegui.core.renderer.component.ButtonRenderer;
import com.kendanware.onegui.core.renderer.component.LabelRenderer;
import com.kendanware.onegui.core.renderer.component.PanelRenderer;
import com.kendanware.onegui.core.renderer.component.ScreenRenderer;

/**
 * Contains references to all available <code>ComponentRenderer</code>s.
 *
 * @author Daniel Johansson, Kendanware
 * @author Kenny Colliander Nordin, Kendanware
 *
 * @since 0.0.1
 */
public class ComponentRendererFactory {

    private static final Map<Class<?>, ComponentRenderer> HANDLERS = new ConcurrentHashMap<>();

    static {
        ComponentRendererFactory.setRenderHandler(Screen.class, new ScreenRenderer());
        ComponentRendererFactory.setRenderHandler(Panel.class, new PanelRenderer());
        ComponentRendererFactory.setRenderHandler(Label.class, new LabelRenderer());
        ComponentRendererFactory.setRenderHandler(Button.class, new ButtonRenderer());
    }

    public static ComponentRenderer getRenderHandler(final Class<?> clazz) {

        final ComponentRenderer componentRenderer = HANDLERS.get(clazz);

        if (componentRenderer != null) {
            return componentRenderer;
        }

        throw new IllegalStateException("No renderer found for " + clazz.getName());
    }

    public static void setRenderHandler(final Class<?> clazz, ComponentRenderer componentRenderer) {
        HANDLERS.put(clazz, componentRenderer);
    }
}
