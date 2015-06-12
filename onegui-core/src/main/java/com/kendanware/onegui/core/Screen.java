package com.kendanware.onegui.core;

/**
 * 
 * @author Daniel Johansson, Kendanware
 * @author Kenny Colliander Nordin, Kendanware
 * 
 * @since 0.0.1
 */

public class Screen extends Container {

    public Screen() {
        super(null);
    }

    public Screen(String id) {
        super(null, id);
    }

    @Override
    protected void checkParentComponent(Container parent) {
    }

    @Override
    protected void registerInParent(Container parent) {
    }

}
