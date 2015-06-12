package com.kendanware.onegui.core.events;

/**
 * Event is sent when a component has been hovered, will only be activated once per value.
 * 
 * @author Daniel Johansson, Kendanware
 * @author Kenny Colliander Nordin, Kendanware
 * 
 * @since 0.0.1
 */
public interface HoverEvent extends Event {

    /**
     * Event is sent when a component has been hovered, will only be activated once per value.
     * 
     * @param id
     *            the id of the component
     * @param over
     *            true if hovered, otherwise false
     */
    public void hover(String id, boolean over);
}
