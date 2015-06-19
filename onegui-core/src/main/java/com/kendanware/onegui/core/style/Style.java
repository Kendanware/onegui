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

import com.kendanware.onegui.core.Align;
import com.kendanware.onegui.core.ChildLayout;
import com.kendanware.onegui.core.Color;
import com.kendanware.onegui.core.Dimension;
import com.kendanware.onegui.core.FontSize;
import com.kendanware.onegui.core.FontStyle;
import com.kendanware.onegui.core.VerticalAlign;

/**
 * Immutable object that is containing style information for components
 *
 * @author Daniel Johansson, Kendanware
 * @author Kenny Colliander Nordin, Kendanware
 *
 * @since 0.0.1
 */
public final class Style {

    private final Dimension width;;
    private final Dimension height;

    private final Color color;
    private final String backgroundImage;

    private final Color backgroundColor;

    private final Dimension paddingLeft;
    private final Dimension paddingRight;
    private final Dimension paddingBottom;
    private final Dimension paddingTop;

    private final Dimension marginLeft;
    private final Dimension marginRight;
    private final Dimension marginBottom;
    private final Dimension marginTop;

    private final String font;
    private final FontSize fontSize;
    private final FontStyle fontStyle;

    private final Align align;
    private final VerticalAlign verticalAlign;
    private final ChildLayout childLayout;

    /**
     *
     * @param width
     *            the width
     * @param height
     *            the height
     * @param color
     *            the color
     * @param backgroundImage
     *            the background image
     * @param backgroundColor
     *            the background color
     * @param paddingLeft
     * @param paddingRight
     * @param paddingBottom
     * @param paddingTop
     * @param marginLeft
     * @param marginRight
     * @param marginBottom
     * @param marginTop
     * @param align
     * @param font
     * @param fontSize
     * @param fontStyle
     */
    public Style(Dimension width, Dimension height, Color color, String backgroundImage, Color backgroundColor, Dimension paddingLeft,
            Dimension paddingRight, Dimension paddingBottom, Dimension paddingTop, Dimension marginLeft, Dimension marginRight,
            Dimension marginBottom, Dimension marginTop, ChildLayout childLayout, Align align, VerticalAlign verticalAlign, String font,
            FontSize fontSize, FontStyle fontStyle) {
        super();
        this.width = width;
        this.height = height;
        this.color = color;
        this.backgroundImage = backgroundImage;
        this.backgroundColor = backgroundColor;
        this.paddingLeft = paddingLeft;
        this.paddingRight = paddingRight;
        this.paddingBottom = paddingBottom;
        this.paddingTop = paddingTop;
        this.marginLeft = marginLeft;
        this.marginRight = marginRight;
        this.marginBottom = marginBottom;
        this.marginTop = marginTop;
        this.childLayout = childLayout;
        this.align = align;
        this.verticalAlign = verticalAlign;
        this.font = font;
        this.fontSize = fontSize;
        this.fontStyle = fontStyle;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((align == null) ? 0 : align.hashCode());
        result = prime * result + ((backgroundColor == null) ? 0 : backgroundColor.hashCode());
        result = prime * result + ((backgroundImage == null) ? 0 : backgroundImage.hashCode());
        result = prime * result + ((childLayout == null) ? 0 : childLayout.hashCode());
        result = prime * result + ((color == null) ? 0 : color.hashCode());
        result = prime * result + ((font == null) ? 0 : font.hashCode());
        result = prime * result + ((fontSize == null) ? 0 : fontSize.hashCode());
        result = prime * result + ((fontStyle == null) ? 0 : fontStyle.hashCode());
        result = prime * result + ((height == null) ? 0 : height.hashCode());
        result = prime * result + ((marginBottom == null) ? 0 : marginBottom.hashCode());
        result = prime * result + ((marginLeft == null) ? 0 : marginLeft.hashCode());
        result = prime * result + ((marginRight == null) ? 0 : marginRight.hashCode());
        result = prime * result + ((marginTop == null) ? 0 : marginTop.hashCode());
        result = prime * result + ((paddingBottom == null) ? 0 : paddingBottom.hashCode());
        result = prime * result + ((paddingLeft == null) ? 0 : paddingLeft.hashCode());
        result = prime * result + ((paddingRight == null) ? 0 : paddingRight.hashCode());
        result = prime * result + ((paddingTop == null) ? 0 : paddingTop.hashCode());
        result = prime * result + ((verticalAlign == null) ? 0 : verticalAlign.hashCode());
        result = prime * result + ((width == null) ? 0 : width.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Style other = (Style) obj;
        if (align != other.align)
            return false;
        if (backgroundColor == null) {
            if (other.backgroundColor != null)
                return false;
        } else if (!backgroundColor.equals(other.backgroundColor))
            return false;
        if (backgroundImage == null) {
            if (other.backgroundImage != null)
                return false;
        } else if (!backgroundImage.equals(other.backgroundImage))
            return false;
        if (childLayout != other.childLayout)
            return false;
        if (color == null) {
            if (other.color != null)
                return false;
        } else if (!color.equals(other.color))
            return false;
        if (font == null) {
            if (other.font != null)
                return false;
        } else if (!font.equals(other.font))
            return false;
        if (fontSize == null) {
            if (other.fontSize != null)
                return false;
        } else if (!fontSize.equals(other.fontSize))
            return false;
        if (fontStyle != other.fontStyle)
            return false;
        if (height == null) {
            if (other.height != null)
                return false;
        } else if (!height.equals(other.height))
            return false;
        if (marginBottom == null) {
            if (other.marginBottom != null)
                return false;
        } else if (!marginBottom.equals(other.marginBottom))
            return false;
        if (marginLeft == null) {
            if (other.marginLeft != null)
                return false;
        } else if (!marginLeft.equals(other.marginLeft))
            return false;
        if (marginRight == null) {
            if (other.marginRight != null)
                return false;
        } else if (!marginRight.equals(other.marginRight))
            return false;
        if (marginTop == null) {
            if (other.marginTop != null)
                return false;
        } else if (!marginTop.equals(other.marginTop))
            return false;
        if (paddingBottom == null) {
            if (other.paddingBottom != null)
                return false;
        } else if (!paddingBottom.equals(other.paddingBottom))
            return false;
        if (paddingLeft == null) {
            if (other.paddingLeft != null)
                return false;
        } else if (!paddingLeft.equals(other.paddingLeft))
            return false;
        if (paddingRight == null) {
            if (other.paddingRight != null)
                return false;
        } else if (!paddingRight.equals(other.paddingRight))
            return false;
        if (paddingTop == null) {
            if (other.paddingTop != null)
                return false;
        } else if (!paddingTop.equals(other.paddingTop))
            return false;
        if (verticalAlign != other.verticalAlign)
            return false;
        if (width == null) {
            if (other.width != null)
                return false;
        } else if (!width.equals(other.width))
            return false;
        return true;
    }

    public String getBackgroundImage() {
        return this.backgroundImage;
    }

    public Color getBackgroundColor() {
        return this.backgroundColor;
    }

    public Color getColor() {
        return this.color;
    }

    public Dimension getPaddingLeft() {
        return this.paddingLeft;
    }

    public Dimension getPaddingRight() {
        return this.paddingRight;
    }

    public Dimension getPaddingBottom() {
        return this.paddingBottom;
    }

    public Dimension getPaddingTop() {
        return this.paddingTop;
    }

    public Dimension getMarginLeft() {
        return this.marginLeft;
    }

    public Dimension getMarginRight() {
        return this.marginRight;
    }

    public Dimension getMarginBottom() {
        return this.marginBottom;
    }

    public Dimension getMarginTop() {
        return this.marginTop;
    }

    public Dimension getWidth() {
        return this.width;
    }

    public Dimension getHeight() {
        return this.height;
    }

    public String getFont() {
        return font;
    }

    public FontSize getFontSize() {
        return fontSize;
    }

    public FontStyle getFontStyle() {
        return fontStyle;
    }

    public Align getAlign() {
        return align;
    }

    public VerticalAlign getVerticalAlign() {
        return verticalAlign;
    }

    public ChildLayout getChildLayout() {
        return childLayout;
    }

    @Override
    public String toString() {
        return "Style [width=" + width + ", height=" + height + ", color=" + color + ", backgroundImage=" + backgroundImage + ", backgroundColor="
                + backgroundColor + ", paddingLeft=" + paddingLeft + ", paddingRight=" + paddingRight + ", paddingBottom=" + paddingBottom
                + ", paddingTop=" + paddingTop + ", marginLeft=" + marginLeft + ", marginRight=" + marginRight + ", marginBottom=" + marginBottom
                + ", marginTop=" + marginTop + ", font=" + font + ", fontSize=" + fontSize + ", fontStyle=" + fontStyle + ", align=" + align
                + ", verticalAlign=" + verticalAlign + ", childLayout=" + childLayout + "]";
    }

}
