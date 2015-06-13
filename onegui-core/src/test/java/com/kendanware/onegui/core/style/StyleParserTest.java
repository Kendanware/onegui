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

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.kendanware.onegui.core.Color;
import com.kendanware.onegui.core.Dimension;

public class StyleParserTest extends StyleParser {

    private static final Charset CHARSET = Charset.forName("UTF-8");

    private static final byte[] INVALID_STYLE = "style1 { unknown: 123; }".getBytes(StyleParserTest.CHARSET);

    @Test
    public void testParseStyle() throws FileNotFoundException, IOException {
        try (InputStream inputStream = this.getClass().getResourceAsStream("/example1.ogs")) {
            final Map<String, Style> styles = this.parseStyle(inputStream);

            final Style style1 = styles.get("style1");
            final Style style2 = styles.get("style2");
            final Style style3 = styles.get("style3");

            Assert.assertEquals(new Dimension("100px"), style1.getWidth());
            Assert.assertEquals(new Dimension("300px"), style2.getWidth());
            Assert.assertEquals(new Color("#000000ff"), style1.getColor());
            Assert.assertEquals(new Color("#000000ff"), style2.getColor());
            Assert.assertEquals(new Color("#ffffffff"), style1.getBackgroundColor());
            Assert.assertEquals(new Color("#ffffffff"), style2.getBackgroundColor());
            Assert.assertEquals("resources/image.png", style1.getBackgroundImage());
            Assert.assertNull(style2.getBackgroundImage());
            Assert.assertEquals(new Dimension("10px"), style1.getMarginBottom());
            Assert.assertEquals(new Dimension("20px"), style1.getMarginLeft());
            Assert.assertEquals(new Dimension("30px"), style1.getMarginRight());
            Assert.assertEquals(new Dimension("40px"), style1.getMarginTop());
            Assert.assertEquals(new Dimension("11px"), style1.getPaddingBottom());
            Assert.assertEquals(new Dimension("22px"), style1.getPaddingLeft());
            Assert.assertEquals(new Dimension("33px"), style1.getPaddingRight());
            Assert.assertEquals(new Dimension("44px"), style1.getPaddingTop());
            Assert.assertEquals(new Dimension("10px"), style2.getMarginBottom());
            Assert.assertEquals(new Dimension("20px"), style2.getMarginLeft());
            Assert.assertEquals(new Dimension("30px"), style2.getMarginRight());
            Assert.assertEquals(new Dimension("40px"), style2.getMarginTop());
            Assert.assertEquals(new Dimension("11px"), style2.getPaddingBottom());
            Assert.assertEquals(new Dimension("22px"), style2.getPaddingLeft());
            Assert.assertEquals(new Dimension("33px"), style2.getPaddingRight());
            Assert.assertEquals(new Dimension("44px"), style2.getPaddingTop());

            final Dimension pixel0 = new Dimension("0px");

            Assert.assertEquals(new Dimension("500px"), style3.getWidth());
            Assert.assertEquals(new Color("#ffffffff"), style3.getColor());
            Assert.assertEquals(new Color("#00000000"), style3.getBackgroundColor());
            Assert.assertNull(style3.getBackgroundImage());
            Assert.assertEquals(pixel0, style3.getMarginBottom());
            Assert.assertEquals(pixel0, style3.getMarginLeft());
            Assert.assertEquals(pixel0, style3.getMarginRight());
            Assert.assertEquals(pixel0, style3.getMarginTop());
            Assert.assertEquals(pixel0, style3.getPaddingBottom());
            Assert.assertEquals(pixel0, style3.getPaddingLeft());
            Assert.assertEquals(pixel0, style3.getPaddingRight());
            Assert.assertEquals(pixel0, style3.getPaddingTop());

            Assert.assertNull(styles.get("style4"));
        }
    }

    @Test
    public void testParseStyleInvalid() throws IOException {
        try (InputStream inputStream = new ByteArrayInputStream(StyleParserTest.INVALID_STYLE)) {
            this.parseStyle(inputStream);
            Assert.fail();
        } catch (final IllegalArgumentException e) {
        }
    }

}
