package com.kendanware.onegui.core.events;

/**
 * Event is sent when a component has gained or lost focus, will only be activated once per value.
 * 
 * @author Daniel Johansson, Kendanware
 * @author Kenny Colliander Nordin, Kendanware
 * 
 * @since 0.0.1
 */
public interface FocusEvent extends Event {
    /**
     * Event is sent when a component has gained or lost focus, will only be activated once per value.
     * 
     * @param id
     *            the id of the component
     * @param focus
     *            true if focus, otherwise false
     */
    public void focus(String id, boolean focus);
}
