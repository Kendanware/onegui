package com.kendanware.onegui.core;

import static org.junit.Assert.*;

import org.junit.Test;

public class DimensionTest {

    @Test
    public void test() {
        assertEquals(new Dimension(DimensionType.PERCENT, 1.0f), new Dimension("1.0%"));
        assertEquals(new Dimension(DimensionType.PERCENT_HEIGHT, 1.0f), new Dimension("1.0%h"));
        assertEquals(new Dimension(DimensionType.PERCENT_WIDTH, 1.0f), new Dimension("1.0%w"));
        assertEquals(new Dimension(DimensionType.PIXEL, 1.0f), new Dimension("1.0px"));

        Dimension dimension = new Dimension(DimensionType.PIXEL, 1.0f);

        assertEquals(1.0f, dimension.getSize(), 0.001f);
        assertEquals(DimensionType.PIXEL, dimension.getType());
    }
}
