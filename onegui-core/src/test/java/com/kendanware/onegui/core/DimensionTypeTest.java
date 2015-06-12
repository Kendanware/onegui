package com.kendanware.onegui.core;

import static org.junit.Assert.*;

import org.junit.Test;

public class DimensionTypeTest {

    @Test
    public void testDetectType() {
        assertEquals(DimensionType.PERCENT, DimensionType.detectType("1.0%"));
        assertEquals(DimensionType.PERCENT_HEIGHT, DimensionType.detectType("1.0%h"));
        assertEquals(DimensionType.PERCENT_WIDTH, DimensionType.detectType("1.0%w"));
        assertEquals(DimensionType.PIXEL, DimensionType.detectType("1.0px"));

        try {
            DimensionType.detectType(null);
            fail();
        } catch (IllegalArgumentException e) {
        }

        try {
            DimensionType.detectType("");
            fail();
        } catch (IllegalArgumentException e) {
        }

        try {
            DimensionType.detectType("1.0");
            fail();
        } catch (IllegalArgumentException e) {
        }
    }

    @Test
    public void testGetSize() {
        assertEquals(1.0f, DimensionType.PERCENT.parseSize("1.0%"), 0.001f);
        assertEquals(1.0f, DimensionType.PERCENT_HEIGHT.parseSize("1.0%h"), 0.001f);
        assertEquals(1.0f, DimensionType.PERCENT_WIDTH.parseSize("1.0%w"), 0.001f);
        assertEquals(1.0f, DimensionType.PIXEL.parseSize("1.0px"), 0.001f);

        for (DimensionType dimensionType : DimensionType.values()) {
            try {
                dimensionType.parseSize(null);
                fail(dimensionType.name());
            } catch (IllegalArgumentException e) {
            }

            try {
                dimensionType.parseSize("");
                fail(dimensionType.name());
            } catch (IllegalArgumentException e) {
            }

            try {
                dimensionType.parseSize("1.0");
                fail(dimensionType.name());
            } catch (IllegalArgumentException e) {
            }

            try {
                dimensionType.parseSize("abc");
                fail(dimensionType.name());
            } catch (IllegalArgumentException e) {
            }

            try {
                dimensionType.parseSize("a" + dimensionType.getSuffix());
                fail(dimensionType.name());
            } catch (IllegalArgumentException e) {
            }
        }

    }
}
