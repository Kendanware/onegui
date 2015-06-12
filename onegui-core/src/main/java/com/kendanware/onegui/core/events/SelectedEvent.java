package com.kendanware.onegui.core.events;

import com.kendanware.onegui.core.control.DropDown;

/**
 * Event is sent when an entry in a <code>DropDown</code> has been selected
 * 
 * @author Daniel Johansson, Kendanware
 * @author Kenny Colliander Nordin, Kendanware
 * 
 * @since 0.0.1
 */
public interface SelectedEvent<K> extends Event {
    /**
     * Event is sent when an entry in a {@link DropDown} has been selected
     * 
     * @param id
     *            the id of the component
     * @param key
     *            the selected key
     */
    public void selected(String id, K key);
}
