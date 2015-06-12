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
package com.kendanware.onegui.core.style;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Map;

import org.junit.Test;

import com.kendanware.onegui.core.Color;
import com.kendanware.onegui.core.Dimension;

public class StyleParserTest extends StyleParser {

    private static final Charset CHARSET = Charset.forName("UTF-8");

    private static final byte[] INVALID_STYLE = "style1 { unknown: 123; }".getBytes(CHARSET);

    @Test
    public void testParseStyle() throws FileNotFoundException, IOException {
        try (InputStream inputStream = this.getClass().getResourceAsStream("/example1.ogs")) {
            final Map<String, Style> styles = parseStyle(inputStream);

            Style style1 = styles.get("style1");
            Style style2 = styles.get("style2");
            Style style3 = styles.get("style3");

            assertEquals(new Dimension("100px"), style1.getWidth());
            assertEquals(new Dimension("300px"), style2.getWidth());
            assertEquals(new Color("#000000ff"), style1.getColor());
            assertEquals(new Color("#000000ff"), style2.getColor());
            assertEquals(new Color("#ffffffff"), style1.getBackgroundColor());
            assertEquals(new Color("#ffffffff"), style2.getBackgroundColor());
            assertEquals("resources/image.png", style1.getBackgroundImage());
            assertNull(style2.getBackgroundImage());
            assertEquals(new Dimension("10px"), style1.getMarginBottom());
            assertEquals(new Dimension("20px"), style1.getMarginLeft());
            assertEquals(new Dimension("30px"), style1.getMarginRight());
            assertEquals(new Dimension("40px"), style1.getMarginTop());
            assertEquals(new Dimension("11px"), style1.getPaddingBottom());
            assertEquals(new Dimension("22px"), style1.getPaddingLeft());
            assertEquals(new Dimension("33px"), style1.getPaddingRight());
            assertEquals(new Dimension("44px"), style1.getPaddingTop());
            assertEquals(new Dimension("10px"), style2.getMarginBottom());
            assertEquals(new Dimension("20px"), style2.getMarginLeft());
            assertEquals(new Dimension("30px"), style2.getMarginRight());
            assertEquals(new Dimension("40px"), style2.getMarginTop());
            assertEquals(new Dimension("11px"), style2.getPaddingBottom());
            assertEquals(new Dimension("22px"), style2.getPaddingLeft());
            assertEquals(new Dimension("33px"), style2.getPaddingRight());
            assertEquals(new Dimension("44px"), style2.getPaddingTop());

            Dimension pixel0 = new Dimension("0px");

            assertEquals(new Dimension("500px"), style3.getWidth());
            assertEquals(new Color("#ffffffff"), style3.getColor());
            assertEquals(new Color("#00000000"), style3.getBackgroundColor());
            assertNull(style3.getBackgroundImage());
            assertEquals(pixel0, style3.getMarginBottom());
            assertEquals(pixel0, style3.getMarginLeft());
            assertEquals(pixel0, style3.getMarginRight());
            assertEquals(pixel0, style3.getMarginTop());
            assertEquals(pixel0, style3.getPaddingBottom());
            assertEquals(pixel0, style3.getPaddingLeft());
            assertEquals(pixel0, style3.getPaddingRight());
            assertEquals(pixel0, style3.getPaddingTop());

            assertNull(styles.get("style4"));
        }
    }

    @Test
    public void testParseStyleInvalid() throws IOException {
        try (InputStream inputStream = new ByteArrayInputStream(INVALID_STYLE)) {
            parseStyle(inputStream);
            fail();
        } catch (IllegalArgumentException e) {
        }
    }

}
