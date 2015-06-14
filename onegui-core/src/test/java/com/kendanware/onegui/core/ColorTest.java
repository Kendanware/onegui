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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

public class ColorTest {

    private static final float RED_1 = 0.1f;
    private static final float GREEN_1 = 0.2f;
    private static final float BLUE_1 = 0.3f;
    private static final float ALPHA_1 = 1.0f;

    private static final float LOW_VALUE = -0.01f;
    private static final float HIGH_VALUE = 1.01f;

    @Test
    public void testColorFloatFloatFloatFloat() {
        final Color color = new Color(ColorTest.RED_1, ColorTest.GREEN_1, ColorTest.BLUE_1, ColorTest.ALPHA_1);

        Assert.assertEquals(ColorTest.RED_1, color.getRed(), 0.001f);
        Assert.assertEquals(ColorTest.GREEN_1, color.getGreen(), 0.001f);
        Assert.assertEquals(ColorTest.BLUE_1, color.getBlue(), 0.001f);
        Assert.assertEquals(ColorTest.ALPHA_1, color.getAlpha(), 0.001f);
    }

    @Test
    public void testColorFloatFloatFloatFloatRanges() {
        try {
            new Color(ColorTest.LOW_VALUE, ColorTest.GREEN_1, ColorTest.BLUE_1, ColorTest.ALPHA_1);
            Assert.fail();
        } catch (final IllegalArgumentException e) {
        }
        try {
            new Color(ColorTest.HIGH_VALUE, ColorTest.GREEN_1, ColorTest.BLUE_1, ColorTest.ALPHA_1);
            Assert.fail();
        } catch (final IllegalArgumentException e) {
        }

        try {
            new Color(ColorTest.RED_1, ColorTest.LOW_VALUE, ColorTest.BLUE_1, ColorTest.ALPHA_1);
            Assert.fail();
        } catch (final IllegalArgumentException e) {
        }
        try {
            new Color(ColorTest.RED_1, ColorTest.HIGH_VALUE, ColorTest.BLUE_1, ColorTest.ALPHA_1);
            Assert.fail();
        } catch (final IllegalArgumentException e) {
        }

        try {
            new Color(ColorTest.RED_1, ColorTest.GREEN_1, ColorTest.LOW_VALUE, ColorTest.ALPHA_1);
            Assert.fail();
        } catch (final IllegalArgumentException e) {
        }
        try {
            new Color(ColorTest.RED_1, ColorTest.GREEN_1, ColorTest.HIGH_VALUE, ColorTest.ALPHA_1);
            Assert.fail();
        } catch (final IllegalArgumentException e) {
        }

        try {
            new Color(ColorTest.RED_1, ColorTest.GREEN_1, ColorTest.BLUE_1, ColorTest.LOW_VALUE);
            Assert.fail();
        } catch (final IllegalArgumentException e) {
        }
        try {
            new Color(ColorTest.RED_1, ColorTest.GREEN_1, ColorTest.BLUE_1, ColorTest.HIGH_VALUE);
            Assert.fail();
        } catch (final IllegalArgumentException e) {
        }
    }

    @Test
    public void testColorString() {
        final Color color = new Color("#1a344Eff");

        Assert.assertEquals(ColorTest.RED_1, color.getRed(), 0.01f);
        Assert.assertEquals(ColorTest.GREEN_1, color.getGreen(), 0.01f);
        Assert.assertEquals(ColorTest.BLUE_1, color.getBlue(), 0.01f);
        Assert.assertEquals(ColorTest.ALPHA_1, color.getAlpha(), 0.01f);

        final Color color2 = new Color("#ffffffff");

        Assert.assertEquals(1.0f, color2.getRed(), 0.01f);
        Assert.assertEquals(1.0f, color2.getGreen(), 0.01f);
        Assert.assertEquals(1.0f, color2.getBlue(), 0.01f);
        Assert.assertEquals(1.0f, color2.getAlpha(), 0.01f);
    }

    @Test
    public void testColorStringRanges() {
        try {
            new Color("1a344Eff");
            Assert.fail();
        } catch (final IllegalArgumentException e) {
        }

        try {
            new Color("#1a344Efh");
            Assert.fail();
        } catch (final IllegalArgumentException e) {
        }

        try {
            new Color("#1a344E");
            Assert.fail();
        } catch (final IllegalArgumentException e) {
        }
        try {
            new Color(null);
            Assert.fail();
        } catch (final IllegalArgumentException e) {
        }

        try {
            new Color("");
            Assert.fail();
        } catch (final IllegalArgumentException e) {
        }
    }

    @Test
    public void testSerializable() throws Exception {

        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        final ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);

        final Color color = new Color(0.0f, 0.5f, 1.0f, 0.8f);

        objectOutputStream.writeObject(color);
        objectOutputStream.close();

        final ObjectInputStream objectInputStream = new ObjectInputStream(new ByteArrayInputStream(byteArrayOutputStream.toByteArray()));

        final Color copy = (Color) objectInputStream.readObject();

        Assert.assertEquals(color, copy);
        objectInputStream.close();
    }

    @Test
    public void testEqualsAndHash() {
        final Color color = new Color(0.0f, 0.5f, 1.0f, 0.8f);
        final Color colorSimilar = new Color(0.0f, 0.5f, 1.0f, 0.8f);
        final Color color1 = new Color(0.9f, 0.5f, 1.0f, 0.8f);
        final Color color2 = new Color(0.0f, 0.6f, 1.0f, 0.8f);
        final Color color3 = new Color(0.0f, 0.5f, 0.9f, 0.8f);
        final Color color4 = new Color(0.0f, 0.5f, 1.0f, 0.9f);

        Assert.assertTrue(color.equals(colorSimilar));
        Assert.assertFalse(color.equals(color1));
        Assert.assertFalse(color.equals(color2));
        Assert.assertFalse(color.equals(color3));
        Assert.assertFalse(color.equals(color4));

        final Set<Color> set = new HashSet<Color>();

        set.add(color);
        set.add(color1);
        set.add(color2);
        set.add(color3);
        set.add(color4);

        Assert.assertEquals(5, set.size());

        set.add(colorSimilar);
        Assert.assertEquals(5, set.size());
    }

    @Test
    public void testClone() throws Exception {
        final Color color = new Color(0.0f, 0.5f, 1.0f, 0.8f);
        final Color colorSimilar = ((Color) color.clone());

        Assert.assertSame(color, colorSimilar);
    }

    @Test
    public void testStaticColors() {

        Assert.assertEquals(Color.AQUA, new Color("aqua"));
        Assert.assertEquals(Color.BLACK, new Color("black"));
        Assert.assertEquals(Color.BLUE, new Color("blue"));
        Assert.assertEquals(Color.FUCHSIA, new Color("fuchsia"));
        Assert.assertEquals(Color.GRAY, new Color("gray"));
        Assert.assertEquals(Color.GREEN, new Color("green"));
        Assert.assertEquals(Color.LIME, new Color("lime"));
        Assert.assertEquals(Color.MAROON, new Color("maroon"));
        Assert.assertEquals(Color.NAVY, new Color("navy"));
        Assert.assertEquals(Color.OLIVE, new Color("olive"));
        Assert.assertEquals(Color.PURPLE, new Color("purple"));
        Assert.assertEquals(Color.RED, new Color("red"));
        Assert.assertEquals(Color.SILVER, new Color("silver"));
        Assert.assertEquals(Color.TEAL, new Color("teal"));
        Assert.assertEquals(Color.TRANSPARENT, new Color("transparent"));
        Assert.assertEquals(Color.WHITE, new Color("white"));
        Assert.assertEquals(Color.YELLOW, new Color("yellow"));
    }

}
