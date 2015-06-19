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

import org.junit.Assert;
import org.junit.Test;

public class DimensionTypeTest {

    @Test
    public void testDetectType() {
        Assert.assertEquals(DimensionType.PERCENT, DimensionType.detectType("1.0%"));
        Assert.assertEquals(DimensionType.PERCENT_HEIGHT, DimensionType.detectType("1.0%h"));
        Assert.assertEquals(DimensionType.PERCENT_WIDTH, DimensionType.detectType("1.0%w"));
        Assert.assertEquals(DimensionType.PIXEL, DimensionType.detectType("1.0px"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDetectTypeNull() {
        DimensionType.detectType(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDetectTypeEmpty() {
        DimensionType.detectType("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDetectTypeNoSuffix() {
        DimensionType.detectType("1.0");
    }

    @Test
    public void testGetSize() {
        Assert.assertEquals(1.0f, DimensionType.PERCENT.parseSize("1.0%"), 0.001f);
        Assert.assertEquals(1.0f, DimensionType.PERCENT_HEIGHT.parseSize("1.0%h"), 0.001f);
        Assert.assertEquals(1.0f, DimensionType.PERCENT_WIDTH.parseSize("1.0%w"), 0.001f);
        Assert.assertEquals(1.0f, DimensionType.PIXEL.parseSize("1.0px"), 0.001f);

        for (final DimensionType dimensionType : DimensionType.values()) {
            try {
                dimensionType.parseSize(null);
                Assert.fail(dimensionType.name());
            } catch (final IllegalArgumentException e) {
            }

            try {
                dimensionType.parseSize("");
                Assert.fail(dimensionType.name());
            } catch (final IllegalArgumentException e) {
            }

            try {
                dimensionType.parseSize("1.0");
                Assert.fail(dimensionType.name());
            } catch (final IllegalArgumentException e) {
            }

            try {
                dimensionType.parseSize("abc");
                Assert.fail(dimensionType.name());
            } catch (final IllegalArgumentException e) {
            }

            try {
                dimensionType.parseSize("a" + dimensionType.getSuffix());
                Assert.fail(dimensionType.name());
            } catch (final IllegalArgumentException e) {
            }
        }
    }
}
