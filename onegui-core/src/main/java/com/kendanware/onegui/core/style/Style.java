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

import com.kendanware.onegui.core.Color;
import com.kendanware.onegui.core.Dimension;

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
     */
    public Style(final Dimension width, final Dimension height, final Color color, final String backgroundImage, final Color backgroundColor,
            final Dimension paddingLeft, final Dimension paddingRight, final Dimension paddingBottom, final Dimension paddingTop,
            final Dimension marginLeft, final Dimension marginRight, final Dimension marginBottom, final Dimension marginTop) {
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
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = (prime * result) + ((this.backgroundColor == null) ? 0 : this.backgroundColor.hashCode());
        result = (prime * result) + ((this.backgroundImage == null) ? 0 : this.backgroundImage.hashCode());
        result = (prime * result) + ((this.color == null) ? 0 : this.color.hashCode());
        result = (prime * result) + ((this.height == null) ? 0 : this.height.hashCode());
        result = (prime * result) + ((this.marginBottom == null) ? 0 : this.marginBottom.hashCode());
        result = (prime * result) + ((this.marginLeft == null) ? 0 : this.marginLeft.hashCode());
        result = (prime * result) + ((this.marginRight == null) ? 0 : this.marginRight.hashCode());
        result = (prime * result) + ((this.marginTop == null) ? 0 : this.marginTop.hashCode());
        result = (prime * result) + ((this.paddingBottom == null) ? 0 : this.paddingBottom.hashCode());
        result = (prime * result) + ((this.paddingLeft == null) ? 0 : this.paddingLeft.hashCode());
        result = (prime * result) + ((this.paddingRight == null) ? 0 : this.paddingRight.hashCode());
        result = (prime * result) + ((this.paddingTop == null) ? 0 : this.paddingTop.hashCode());
        result = (prime * result) + ((this.width == null) ? 0 : this.width.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        final Style other = (Style) obj;
        if (this.backgroundColor == null) {
            if (other.backgroundColor != null) {
                return false;
            }
        } else if (!this.backgroundColor.equals(other.backgroundColor)) {
            return false;
        }
        if (this.backgroundImage == null) {
            if (other.backgroundImage != null) {
                return false;
            }
        } else if (!this.backgroundImage.equals(other.backgroundImage)) {
            return false;
        }
        if (this.color == null) {
            if (other.color != null) {
                return false;
            }
        } else if (!this.color.equals(other.color)) {
            return false;
        }
        if (this.height == null) {
            if (other.height != null) {
                return false;
            }
        } else if (!this.height.equals(other.height)) {
            return false;
        }
        if (this.marginBottom == null) {
            if (other.marginBottom != null) {
                return false;
            }
        } else if (!this.marginBottom.equals(other.marginBottom)) {
            return false;
        }
        if (this.marginLeft == null) {
            if (other.marginLeft != null) {
                return false;
            }
        } else if (!this.marginLeft.equals(other.marginLeft)) {
            return false;
        }
        if (this.marginRight == null) {
            if (other.marginRight != null) {
                return false;
            }
        } else if (!this.marginRight.equals(other.marginRight)) {
            return false;
        }
        if (this.marginTop == null) {
            if (other.marginTop != null) {
                return false;
            }
        } else if (!this.marginTop.equals(other.marginTop)) {
            return false;
        }
        if (this.paddingBottom == null) {
            if (other.paddingBottom != null) {
                return false;
            }
        } else if (!this.paddingBottom.equals(other.paddingBottom)) {
            return false;
        }
        if (this.paddingLeft == null) {
            if (other.paddingLeft != null) {
                return false;
            }
        } else if (!this.paddingLeft.equals(other.paddingLeft)) {
            return false;
        }
        if (this.paddingRight == null) {
            if (other.paddingRight != null) {
                return false;
            }
        } else if (!this.paddingRight.equals(other.paddingRight)) {
            return false;
        }
        if (this.paddingTop == null) {
            if (other.paddingTop != null) {
                return false;
            }
        } else if (!this.paddingTop.equals(other.paddingTop)) {
            return false;
        }
        if (this.width == null) {
            if (other.width != null) {
                return false;
            }
        } else if (!this.width.equals(other.width)) {
            return false;
        }
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

    @Override
    public String toString() {
        return "Style [width=" + this.width + ", height=" + this.height + ", backgroundImage=" + this.backgroundImage + ", backgroundColor="
                + this.backgroundColor + ", color=" + this.color + ", paddingLeft=" + this.paddingLeft + ", paddingRight=" + this.paddingRight
                + ", paddingBottom=" + this.paddingBottom + ", paddingTop=" + this.paddingTop + ", marginLeft=" + this.marginLeft + ", marginRight="
                + this.marginRight + ", marginBottom=" + this.marginBottom + ", marginTop=" + this.marginTop + "]";
    }

}
