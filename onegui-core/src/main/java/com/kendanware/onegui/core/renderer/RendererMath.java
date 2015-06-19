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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kendanware.onegui.core.Align;
import com.kendanware.onegui.core.ChildLayout;
import com.kendanware.onegui.core.Color;
import com.kendanware.onegui.core.Component;
import com.kendanware.onegui.core.Container;
import com.kendanware.onegui.core.Dimension;
import com.kendanware.onegui.core.DimensionType;
import com.kendanware.onegui.core.FontSize;
import com.kendanware.onegui.core.FontSizeType;
import com.kendanware.onegui.core.VerticalAlign;
import com.kendanware.onegui.core.style.Style;

/**
 * Math functions for rendering.
 *
 * @author Daniel Johansson, Kendanware
 * @author Kenny Colliander Nordin, Kendanware
 *
 * @since 0.0.1
 */
public class RendererMath {
    private static final Logger LOG = LoggerFactory.getLogger(OneGuiRenderer.class);

    /**
     * Get the width of a container based on parent's drawable space
     * 
     * @param style
     *            the container
     * @param parentWidth
     *            the parent width in pixels, after padding has been applied
     * @param parentHeight
     *            the parent height in pixels, after padding has been applied
     * @return the width in pixels
     */
    public static float getWidth(final Style style, final float parentWidth, final float parentHeight) {
        return getWidth(style, parentWidth, parentHeight, true);
    }

    /**
     * Get the width of a container based on parent's drawable space
     * 
     * @param style
     *            the container
     * @param parentWidth
     *            the parent width in pixels, after padding has been applied
     * @param parentHeight
     *            the parent height in pixels, after padding has been applied
     * @param allowHeightBased
     *            true if %h suffix should be allowed, otherwise false
     * @return the width in pixels
     */
    public static float getWidth(final Style style, final float parentWidth, final float parentHeight, final boolean allowHeightBased) {

        if (style == null) {
            throw new NullPointerException("Style is null");
        }

        if (style.getWidth().getType() == DimensionType.PIXEL) {
            return parentWidth < style.getWidth().getSize() ? parentWidth : style.getWidth().getSize();
        }

        if (style.getWidth().getType() == DimensionType.PERCENT) {
            return parentWidth * (style.getWidth().getSize() / 100.0f);
        }

        if (style.getWidth().getType() == DimensionType.PERCENT_HEIGHT) {

            if (!allowHeightBased) {
                throw new IllegalArgumentException("Dimension relative to height is not allowed");
            }

            final float height = getHeight(style, parentWidth, parentHeight, false);

            return height * (style.getWidth().getSize() / 100.0f);
        }

        if (style.getWidth().getType() == DimensionType.PERCENT_WIDTH) {
            throw new IllegalArgumentException("Dimension relative to width is not allowed");
        }

        throw new IllegalArgumentException("Unknown dimension: " + style.getWidth());
    }

    /**
     * Get the height of a container based on parent's drawable space
     * 
     * @param style
     *            the container
     * @param parentWidth
     *            the parent width in pixels, after padding has been applied
     * @param parentHeight
     *            the parent height in pixels, after padding has been applied
     * @return the height in pixels
     */
    public static float getHeight(final Style style, final float parentWidth, final float parentHeight) {
        return getHeight(style, parentWidth, parentHeight, true);
    }

    /**
     * Get the height of a container based on parent's drawable space
     * 
     * @param style
     *            the container
     * @param parentWidth
     *            the parent width in pixels, after padding has been applied
     * @param parentHeight
     *            the parent height in pixels, after padding has been applied
     * @param allowWidthBased
     *            true if %w suffix should be allowed, otherwise false
     * @return the height in pixels
     */
    public static float getHeight(final Style style, final float parentWidth, final float parentHeight, final boolean allowWidthBased) {

        if (style == null) {
            throw new NullPointerException("Style is null");
        }

        if (style.getHeight().getType() == DimensionType.PIXEL) {
            return parentHeight < style.getHeight().getSize() ? parentHeight : style.getHeight().getSize();
        }

        if (style.getHeight().getType() == DimensionType.PERCENT) {
            return parentHeight * (style.getHeight().getSize() / 100.0f);
        }

        if (style.getHeight().getType() == DimensionType.PERCENT_WIDTH) {

            if (!allowWidthBased) {
                throw new IllegalArgumentException("Dimension relative to width is not allowed");
            }

            final float width = getWidth(style, parentWidth, parentHeight, false);

            return width * (style.getHeight().getSize() / 100.0f);
        }

        if (style.getHeight().getType() == DimensionType.PERCENT_HEIGHT) {
            throw new IllegalArgumentException("Dimension relative to height is not allowed");
        }

        throw new IllegalArgumentException("Unknown dimension: " + style.getWidth());
    }

    /**
     * Convert color to AWT Color
     * 
     * @param color
     *            the color
     * @return
     */
    public static java.awt.Color toColor(final Color color) {
        return new java.awt.Color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
    }

    public static int getFontHeight(final float screenHeight, final FontSize fontSize) {

        if (fontSize.getType() == FontSizeType.PIXEL) {
            return Math.round(fontSize.getSize());
        }

        // Calculate height from 2% of screen resolution
        return Math.round(fontSize.getSize() * 0.02f * screenHeight);
    }

    public static void calculateChildren(final Map<String, ComponentInfo> componentInfos, Container container) {

        if (container.getStyle() == null) {
            final Style style = container.getOneGui().getStyles().get(container.getId());
            container.setStyle(style);
        }

        if (container.getStyle().getChildLayout() == ChildLayout.LEFT || container.getStyle().getChildLayout() == ChildLayout.RIGHT) {
            calculateChildrenHorizontal(componentInfos, container);
        } else if (container.getStyle().getChildLayout() == ChildLayout.UP || container.getStyle().getChildLayout() == ChildLayout.DOWN) {
            calculateChildrenVertical(componentInfos, container);
        } else {
            // TODO: fix center
        }

    }

    static void calculateChildrenHorizontal(final Map<String, ComponentInfo> componentInfos, final Container container) {

        try {
            final ComponentInfo parentRendererInfo = componentInfos.get(container.getId());

            final ChildLayout childLayout = container.getStyle().getChildLayout();
            final VerticalAlign verticalAlign = container.getStyle().getVerticalAlign();

            final float parentWidth = getParentWidth(parentRendererInfo.getWidth(), parentRendererInfo.getHeight(), container.getStyle());
            final float parentHeight = getParentHeight(parentRendererInfo.getWidth(), parentRendererInfo.getHeight(), container.getStyle());
            final float paddingLeft = getMarginPaddingWidth(container.getStyle(), container.getStyle().getPaddingLeft(), parentWidth, parentHeight);
            final float paddingRight = getMarginPaddingWidth(container.getStyle(), container.getStyle().getPaddingRight(), parentWidth, parentHeight);
            final float paddingTop = getMarginPaddingHeight(container.getStyle(), container.getStyle().getPaddingTop(), parentWidth, parentHeight);
            final float paddingBottom = getMarginPaddingHeight(container.getStyle(), container.getStyle().getPaddingBottom(), parentWidth,
                    parentHeight);
            float position = childLayout == ChildLayout.RIGHT ? paddingLeft : parentRendererInfo.getWidth() - paddingRight;

            for (Component component : container.getChildren()) {
                try {
                    if (component.getStyle() == null) {
                        final Style style = container.getOneGui().getStyles().get(component.getId());
                        component.setStyle(style);
                    }

                    float width = RendererMath.getWidth(component.getStyle(), parentWidth, parentHeight);
                    float height = RendererMath.getHeight(component.getStyle(), parentWidth, parentHeight);

                    final float marginLeft = getMarginPaddingWidth(component.getStyle(), component.getStyle().getMarginLeft(), width, height);
                    final float marginRight = getMarginPaddingWidth(component.getStyle(), component.getStyle().getMarginRight(), width, height);

                    float y;
                    if (verticalAlign == VerticalAlign.TOP) {
                        final float marginTop = getMarginPaddingWidth(component.getStyle(), component.getStyle().getMarginTop(), width, height);
                        y = paddingTop + marginTop;
                    } else if (verticalAlign == VerticalAlign.BOTTOM) {
                        final float marginBottom = getMarginPaddingWidth(component.getStyle(), component.getStyle().getMarginBottom(), width,
                                parentHeight);
                        y = parentRendererInfo.getHeight() - height - paddingBottom - marginBottom;
                    } else {
                        y = parentRendererInfo.getHeight() / 2 - height / 2f;
                    }

                    if (childLayout == ChildLayout.LEFT) {
                        // Go left
                        position -= (width + marginRight);
                    } else {
                        // Go right
                        position += marginLeft;
                    }

                    ComponentInfo componentInfo = new ComponentInfo(position, y, width, height, true); // TODO: fix visible
                    componentInfos.put(component.getId(), componentInfo);

                    if (component instanceof Container) {
                        calculateChildren(componentInfos, (Container) component);
                    }

                    if (childLayout == ChildLayout.RIGHT) {
                        // Go right
                        position += width + marginRight;
                    } else {
                        // Go left
                        position -= marginLeft;
                    }

                } catch (RuntimeException e) {
                    LOG.error("Unknown error occurred for {}", component, e);
                    throw e;
                }
            }

        } catch (RuntimeException e) {
            LOG.error("Unknown error occurred for {}", container, e);
            throw e;
        }
    }

    static void calculateChildrenVertical(final Map<String, ComponentInfo> componentInfos, Container container) {

        try {
            final ComponentInfo parentRendererInfo = componentInfos.get(container.getId());

            final float parentWidth = getParentWidth(parentRendererInfo.getWidth(), parentRendererInfo.getHeight(), container.getStyle());
            final float parentHeight = getParentHeight(parentRendererInfo.getWidth(), parentRendererInfo.getHeight(), container.getStyle());
            final float paddingLeft = getMarginPaddingWidth(container.getStyle(), container.getStyle().getPaddingLeft(), parentWidth, parentHeight);
            final float paddingRight = getMarginPaddingWidth(container.getStyle(), container.getStyle().getPaddingRight(), parentWidth, parentHeight);
            final float paddingTop = getMarginPaddingHeight(container.getStyle(), container.getStyle().getPaddingTop(), parentWidth, parentHeight);
            final float paddingBottom = getMarginPaddingHeight(container.getStyle(), container.getStyle().getPaddingBottom(), parentWidth,
                    parentHeight);

            final ChildLayout childLayout = container.getStyle().getChildLayout();
            final Align align = container.getStyle().getAlign();

            float position = childLayout == ChildLayout.DOWN ? paddingTop : parentRendererInfo.getHeight() - paddingBottom;

            for (final Component component : container.getChildren()) {
                try {
                    if (component.getStyle() == null) {
                        final Style style = container.getOneGui().getStyles().get(component.getId());
                        component.setStyle(style);
                    }

                    float width = RendererMath.getWidth(component.getStyle(), parentWidth, parentHeight);
                    float height = RendererMath.getHeight(component.getStyle(), parentWidth, parentHeight);

                    final float marginTop = getMarginPaddingWidth(component.getStyle(), component.getStyle().getMarginTop(), width, height);
                    final float marginBottom = getMarginPaddingWidth(component.getStyle(), component.getStyle().getMarginBottom(), width, height);

                    float x;
                    if (align == Align.LEFT) {
                        final float marginLeft = getMarginPaddingWidth(component.getStyle(), component.getStyle().getMarginLeft(), width, height);
                        x = paddingLeft + marginLeft;
                    } else if (align == Align.RIGHT) {
                        final float marginRight = getMarginPaddingWidth(component.getStyle(), component.getStyle().getMarginRight(), width, height);
                        x = parentRendererInfo.getWidth() - width - paddingRight - marginRight;
                    } else {
                        x = parentRendererInfo.getWidth() / 2 - width / 2f;
                    }

                    if (childLayout == ChildLayout.UP) {
                        position -= height - marginBottom;
                    } else {
                        position += marginTop;
                    }

                    ComponentInfo componentInfo = new ComponentInfo(x, position, width, height, true); // TODO: fix visible
                    componentInfos.put(component.getId(), componentInfo);

                    if (component instanceof Container) {
                        calculateChildren(componentInfos, (Container) component);
                    }

                    if (childLayout == ChildLayout.DOWN) {
                        position += height + marginBottom;
                    } else {
                        position -= marginTop;
                    }

                } catch (RuntimeException e) {
                    LOG.error("Unknown error occurred for {}", component, e);
                    throw e;
                }
            }

        } catch (RuntimeException e) {
            LOG.error("Unknown error occurred for {}", container, e);
            throw e;
        }
    }

    static float getParentWidth(final float parentWidth, final float parentHeight, Style parentStyle) {
        final float paddingLeft = getMarginPaddingWidth(parentStyle, parentStyle.getPaddingLeft(), parentWidth, parentHeight);
        final float paddingRight = getMarginPaddingWidth(parentStyle, parentStyle.getPaddingRight(), parentWidth, parentHeight);

        return parentWidth - (paddingLeft + paddingRight);
    }

    static float getParentHeight(final float parentWidth, final float parentHeight, Style parentStyle) {
        final float paddingTop = getMarginPaddingHeight(parentStyle, parentStyle.getPaddingTop(), parentWidth, parentHeight);
        final float paddingBottom = getMarginPaddingHeight(parentStyle, parentStyle.getPaddingBottom(), parentWidth, parentHeight);

        return parentHeight - (paddingTop + paddingBottom);
    }

    public static float getMarginPaddingWidth(final Style style, final Dimension dimension, final float parentWidth, final float parentHeight) {
        return getMarginPaddingWidth(style, dimension, parentWidth, parentHeight, true);
    }

    public static float getMarginPaddingWidth(final Style style, final Dimension dimension, final float parentWidth, final float parentHeight,
            final boolean allowHeightBased) {

        if (dimension == null) {
            throw new NullPointerException("Dimension is null");
        }

        if (dimension.getType() == DimensionType.PIXEL) {
            return parentWidth < dimension.getSize() ? parentWidth : dimension.getSize();
        }

        if (dimension.getType() == DimensionType.PERCENT) {
            return parentWidth * (dimension.getSize() / 100.0f);
        }

        if (dimension.getType() == DimensionType.PERCENT_HEIGHT) {

            if (!allowHeightBased) {
                throw new IllegalArgumentException("Dimension relative to height is not allowed");
            }

            final float height = getHeight(style, parentWidth, parentHeight, false);

            return height * (dimension.getSize() / 100.0f);
        }

        if (dimension.getType() == DimensionType.PERCENT_WIDTH) {
            throw new IllegalArgumentException("Dimension relative to width is not allowed");
        }

        throw new IllegalArgumentException("Unknown dimension: " + dimension);
    }

    public static float getMarginPaddingHeight(final Style style, final Dimension dimension, final float parentWidth, final float parentHeight) {
        return getMarginPaddingHeight(style, dimension, parentWidth, parentHeight, true);
    }

    public static float getMarginPaddingHeight(final Style style, final Dimension dimension, final float parentWidth, final float parentHeight,
            final boolean allowWidthBased) {

        if (dimension == null) {
            throw new NullPointerException("Dimension is null");
        }

        if (dimension.getType() == DimensionType.PIXEL) {
            return parentHeight < dimension.getSize() ? parentHeight : dimension.getSize();
        }

        if (dimension.getType() == DimensionType.PERCENT) {
            return parentHeight * (dimension.getSize() / 100.0f);
        }

        if (dimension.getType() == DimensionType.PERCENT_WIDTH) {

            if (!allowWidthBased) {
                throw new IllegalArgumentException("Dimension relative to width is not allowed");
            }

            final float width = getWidth(style, parentWidth, parentHeight, false);

            return width * (dimension.getSize() / 100.0f);
        }

        if (style.getHeight().getType() == DimensionType.PERCENT_HEIGHT) {
            throw new IllegalArgumentException("Dimension relative to height is not allowed");
        }

        throw new IllegalArgumentException("Unknown dimension: " + style.getWidth());
    }

}
