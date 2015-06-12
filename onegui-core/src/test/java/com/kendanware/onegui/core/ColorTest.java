package com.kendanware.onegui.core;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import com.kendanware.onegui.core.Color;

public class ColorTest {

    private static final float RED_1 = 0.1f;
    private static final float GREEN_1 = 0.2f;
    private static final float BLUE_1 = 0.3f;
    private static final float ALPHA_1 = 1.0f;

    private static final float LOW_VALUE = -0.01f;
    private static final float HIGH_VALUE = 1.01f;

    @Test
    public void testColorFloatFloatFloatFloat() {
        Color color = new Color(RED_1, GREEN_1, BLUE_1, ALPHA_1);

        assertEquals(RED_1, color.getRed(), 0.001f);
        assertEquals(GREEN_1, color.getGreen(), 0.001f);
        assertEquals(BLUE_1, color.getBlue(), 0.001f);
        assertEquals(ALPHA_1, color.getAlpha(), 0.001f);
    }

    @Test
    public void testColorFloatFloatFloatFloatRanges() {
        try {
            new Color(LOW_VALUE, GREEN_1, BLUE_1, ALPHA_1);
            fail();
        } catch (IllegalArgumentException e) {
        }
        try {
            new Color(HIGH_VALUE, GREEN_1, BLUE_1, ALPHA_1);
            fail();
        } catch (IllegalArgumentException e) {
        }

        try {
            new Color(RED_1, LOW_VALUE, BLUE_1, ALPHA_1);
            fail();
        } catch (IllegalArgumentException e) {
        }
        try {
            new Color(RED_1, HIGH_VALUE, BLUE_1, ALPHA_1);
            fail();
        } catch (IllegalArgumentException e) {
        }

        try {
            new Color(RED_1, GREEN_1, LOW_VALUE, ALPHA_1);
            fail();
        } catch (IllegalArgumentException e) {
        }
        try {
            new Color(RED_1, GREEN_1, HIGH_VALUE, ALPHA_1);
            fail();
        } catch (IllegalArgumentException e) {
        }

        try {
            new Color(RED_1, GREEN_1, BLUE_1, LOW_VALUE);
            fail();
        } catch (IllegalArgumentException e) {
        }
        try {
            new Color(RED_1, GREEN_1, BLUE_1, HIGH_VALUE);
            fail();
        } catch (IllegalArgumentException e) {
        }
    }

    @Test
    public void testColorString() {
        Color color = new Color("#1a344Eff");

        assertEquals(RED_1, color.getRed(), 0.01f);
        assertEquals(GREEN_1, color.getGreen(), 0.01f);
        assertEquals(BLUE_1, color.getBlue(), 0.01f);
        assertEquals(ALPHA_1, color.getAlpha(), 0.01f);

        Color color2 = new Color("#ffffffff");

        assertEquals(1.0f, color2.getRed(), 0.01f);
        assertEquals(1.0f, color2.getGreen(), 0.01f);
        assertEquals(1.0f, color2.getBlue(), 0.01f);
        assertEquals(1.0f, color2.getAlpha(), 0.01f);
    }

    @Test
    public void testColorStringRanges() {
        try {
            new Color("1a344Eff");
            fail();
        } catch (IllegalArgumentException e) {
        }

        try {
            new Color("#1a344Efh");
            fail();
        } catch (IllegalArgumentException e) {
        }

        try {
            new Color("#1a344E");
            fail();
        } catch (IllegalArgumentException e) {
        }
        try {
            new Color(null);
            fail();
        } catch (IllegalArgumentException e) {
        }

        try {
            new Color("");
            fail();
        } catch (IllegalArgumentException e) {
        }
    }

    @Test
    public void testSerializable() throws Exception {

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);

        Color color = new Color(0.0f, 0.5f, 1.0f, 0.8f);

        objectOutputStream.writeObject(color);
        objectOutputStream.close();

        ObjectInputStream objectInputStream = new ObjectInputStream(new ByteArrayInputStream(byteArrayOutputStream.toByteArray()));

        Color copy = (Color) objectInputStream.readObject();

        assertEquals(color, copy);
        objectInputStream.close();
    }

    @Test
    public void testEqualsAndHash() {
        Color color = new Color(0.0f, 0.5f, 1.0f, 0.8f);
        Color colorSimilar = new Color(0.0f, 0.5f, 1.0f, 0.8f);
        Color color1 = new Color(0.9f, 0.5f, 1.0f, 0.8f);
        Color color2 = new Color(0.0f, 0.6f, 1.0f, 0.8f);
        Color color3 = new Color(0.0f, 0.5f, 0.9f, 0.8f);
        Color color4 = new Color(0.0f, 0.5f, 1.0f, 0.9f);

        assertTrue(color.equals(colorSimilar));
        assertFalse(color.equals(color1));
        assertFalse(color.equals(color2));
        assertFalse(color.equals(color3));
        assertFalse(color.equals(color4));

        Set<Color> set = new HashSet<Color>();

        set.add(color);
        set.add(color1);
        set.add(color2);
        set.add(color3);
        set.add(color4);

        assertEquals(5, set.size());

        set.add(colorSimilar);
        assertEquals(5, set.size());
    }

    @Test
    public void testClone() throws Exception {
        Color color = new Color(0.0f, 0.5f, 1.0f, 0.8f);
        Color colorSimilar = ((Color) color.clone());

        assertSame(color, colorSimilar);
    }

}
