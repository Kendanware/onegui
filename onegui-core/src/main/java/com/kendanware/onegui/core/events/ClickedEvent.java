package com.kendanware.onegui.core.events;

/**
 * Event that is sent when some component has been clicked
 * 
 * @author Daniel Johansson, Kendanware
 * @author Kenny Colliander Nordin, Kendanware
 * 
 * @since 0.0.1
 */
public interface ClickedEvent extends Event {

    /**
     * Method that is called when a compoent has been clicked
     * 
     * @param id
     *            the id of the component
     */
    public void clicked(String id);
}
