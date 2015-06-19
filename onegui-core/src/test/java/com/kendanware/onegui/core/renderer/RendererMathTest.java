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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.kendanware.onegui.core.style.Style;
import com.kendanware.onegui.core.style.StyleParser;

public class RendererMathTest extends RendererMath {

    private Map<String, Style> styles;

    @Before
    public void before() throws IOException {
        try (InputStream inputStream = this.getClass().getResourceAsStream("/screen-renderer-test1.ogs")) {
            StyleParser styleParser = new StyleParser();
            this.styles = styleParser.parseStyle(inputStream);
        }
    }

    @Test
    public void testGetMaxWidth() {
        Style stylePercent = this.styles.get("panelPercent");
        assertNotNull(stylePercent);
        assertEquals(40.0f, getWidth(stylePercent, 200f, 100f), 0.01f);

        Style stylePixel = this.styles.get("panelPixel");
        assertNotNull(stylePixel);
        assertEquals(20.0f, getWidth(stylePixel, 200f, 100f), 0.01f);

        Style stylePercentWidth = this.styles.get("panelWidthPercent");
        assertNotNull(stylePercentWidth);
        assertEquals(16.0f, getWidth(stylePercentWidth, 200f, 100f), 0.01f);
    }

    @Test
    public void testGetMaxHeight() {
        Style style = this.styles.get("panelPercent");
        assertNotNull(style);
        assertEquals(80.0f, getHeight(style, 200f, 100f), 0.01f);

        Style stylePixel = this.styles.get("panelPixel");
        assertNotNull(stylePixel);
        assertEquals(90.0f, getHeight(stylePixel, 200f, 100f), 0.01f);

        Style stylePercentHeight = this.styles.get("panelHeightPercent");
        assertNotNull(stylePercentHeight);
        assertEquals(32.0f, getHeight(stylePercentHeight, 200f, 100f), 0.01f);
    }

    @Test
    public void testGetMarginWidthPercent() {
        Style style = this.styles.get("panelMarginPercent");
        assertNotNull(style);
        assertEquals(20.0f, getMarginPaddingWidth(style, style.getMarginLeft(), 200f, 100f, true), 0.01f);
        assertEquals(40.0f, getMarginPaddingWidth(style, style.getMarginRight(), 200f, 100f, true), 0.01f);
    }

    @Test
    public void testGetMarginWidthPercentWidth() {
        Style style = this.styles.get("panelMarginWidthPercent");
        assertNotNull(style);
        assertEquals(8.0f, getMarginPaddingWidth(style, style.getMarginLeft(), 200f, 100f, true), 0.01f);
        assertEquals(16.0f, getMarginPaddingWidth(style, style.getMarginRight(), 200f, 100f, true), 0.01f);
    }

    @Test
    public void testGetMarginWidthPixel() {
        Style style = this.styles.get("panelMarginPixel");
        assertNotNull(style);
        assertEquals(10.0f, getMarginPaddingWidth(style, style.getMarginLeft(), 200f, 100f, true), 0.01f);
        assertEquals(20.0f, getMarginPaddingWidth(style, style.getMarginRight(), 200f, 100f, true), 0.01f);
    }
}
