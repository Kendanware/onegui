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
 * Contains style property constants
 *
 * @author Daniel Johansson, Kendanware
 * @author Kenny Colliander Nordin, Kendanware
 *
 * @since 0.0.1
 */
public class StyleProperty {

    /** Container height, fixed or relative to parent. Type: {@link Dimension} */
    public static final String HEIGHT = "height";

    /** Container width, fixed or relative to parent. Type: {@link Dimension} */
    public static final String WIDTH = "width";

    /** Margin top to parent container. Type: {@link Dimension} */
    public static final String MARGIN_TOP = "marginTop";

    /** Margin bottom to parent container. Type: {@link Dimension} */
    public static final String MARGIN_BOTTOM = "marginBottom";

    /** Margin right to parent container. Type: {@link Dimension} */
    public static final String MARGIN_RIGHT = "marginRight";

    /** Margin left to parent container. Type: {@link Dimension} */
    public static final String MARGIN_LEFT = "marginLeft";

    /** Padding top to in container. Type: {@link Dimension} */
    public static final String PADDING_TOP = "paddingTop";

    /** Padding bottom to in container. Type: {@link Dimension} */
    public static final String PADDING_BOTTOM = "paddingBottom";

    /** Padding right to in container. Type: {@link Dimension} */
    public static final String PADDING_RIGHT = "paddingRight";

    /** Padding left to in container. Type: {@link Dimension} */
    public static final String PADDING_LEFT = "paddingLeft";

    /** Background image, specify <code>none</code> to reset. Type: <code>String</code> */
    public static final String BACKGROUND_IMAGE = "backgroundImage";

    /** Background color in container. Type: {@link Color} */
    public static final String BACKGROUND_COLOR = "backgroundColor";

    /** Foreground color in container. Type: {@link Color} */
    public static final String COLOR = "color";
}
