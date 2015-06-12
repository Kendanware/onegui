package com.kendanware.onegui.core.control;

import java.util.List;

import com.kendanware.onegui.core.Container;
import com.kendanware.onegui.core.Control;

/**
 * 
 * @author Daniel Johansson, Kendanware
 * @author Kenny Colliander Nordin, Kendanware
 * 
 * @since 0.0.1
 */
public class DropDown<K> extends Control {

    public DropDown(Container parent) {
        super(parent);
    }

    public DropDown(Container parent, String id) {
        super(parent, id);
    }

    public void setOptions(List<Option<K>> options) {
        // TODO: implement

    }

    public List<Option<K>> getOptions() {
        // TODO: implement
        return null;
    }

    public K getSelected() {
        // TODO: implement
        return null;
    }

    public void setSelected(K key) {
        // TODO: implement
    }

}
