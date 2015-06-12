package com.kendanware.onegui.core.container;

import com.kendanware.onegui.core.Container;

/**
 * The <code>Panel</code> is used for presentation areas
 * 
 * @author Daniel Johansson, Kendanware
 * @author Kenny Colliander Nordin, Kendanware
 * 
 * @since 0.0.1
 */
public class Panel extends Container {

    public Panel(Container parent) {
        super(parent);
    }

    public Panel(Container parent, String id) {
        super(parent, id);
    }

}
