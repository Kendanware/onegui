package com.kendanware.onegui.core;

import static com.kendanware.onegui.core.Validation.checkId;

import java.util.Collections;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

import com.kendanware.onegui.core.events.ClickedEvent;
import com.kendanware.onegui.core.events.Event;
import com.kendanware.onegui.core.events.FocusEvent;
import com.kendanware.onegui.core.events.HoverEvent;
import com.kendanware.onegui.core.style.Style;

/**
 * The Component is the base object that all other controls inherit from.
 * 
 * @author Daniel Johansson, Kendanware
 * @author Kenny Colliander Nordin, Kendanware
 * 
 * @since 0.0.1
 */
public class Component {

    private String id;

    private Style style;

    private final Container parent;

    private final AtomicBoolean focus = new AtomicBoolean(false);
    private final AtomicBoolean hover = new AtomicBoolean(false);
    private final RegisteredEventListeners<ClickedEvent> clickedEventListeners = new RegisteredEventListeners<>();
    private final RegisteredEventListeners<FocusEvent> focusEventListeners = new RegisteredEventListeners<>();
    private final RegisteredEventListeners<HoverEvent> mouseHoverEventListeners = new RegisteredEventListeners<>();

    /**
     * Constructor
     * 
     * @param id
     *            the id
     */

    public Component(final Container parent) {
        this(parent, UUID.randomUUID().toString());
    }

    /**
     * Constructor
     * 
     * @param parent
     *            the parent
     * @param id
     *            the id
     * 
     * @throws IllegalArgumentException
     *             if the id is invalid or not unique or if parent container is null
     */
    public Component(final Container parent, final String id) {
        this.checkParentComponent(parent);
        checkId(parent, id);

        this.parent = parent;
        this.id = id;

        this.registerInParent(parent);
    }

    protected void checkParentComponent(Container parent) {
        if (parent == null) {
            throw new IllegalArgumentException("Parent Container is null");
        }
    }

    public Container getParent() {
        return parent;
    }

    protected void registerInParent(Container parent) {
        parent.addComponent(this);
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param style
     *            the style to set
     */
    public void setStyle(final Style style) {
        this.style = style;
    }

    /**
     * @return the style or null
     */
    public Style getStyle() {
        return this.style;
    }

    /**
     * Method is called when the control has been clicked
     */
    public void click() {
        for (final ClickedEvent listener : this.clickedEventListeners.getListeners()) {
            listener.clicked(this.getId()); // TODO: run async
        }
    }

    /**
     * @return true if the control has focus, otherwise false
     */
    public boolean hasFocus() {
        return this.focus.get();
    }

    /**
     * @param focus
     *            set true if the control has focus, otherwise false
     */
    public void setFocus(final boolean focus) {
        // Only fire event once per change
        if (this.focus.getAndSet(focus) != focus) {
            for (final FocusEvent listener : this.focusEventListeners.getListeners()) {
                listener.focus(this.getId(), focus); // TODO: run async
            }
        }
    }

    /**
     * @param over
     *            set true if the mouse hover over, otherwise false
     */
    public void hover(final boolean over) {
        // Only fire event once per change
        if (this.hover.getAndSet(over) != over) {
            for (final HoverEvent listener : this.mouseHoverEventListeners.getListeners()) {
                listener.hover(this.getId(), over); // TODO: run async
            }
        }
    }

    /**
     * Add event listener to the control
     * 
     * @param event
     *            the event listener to add
     */
    public void addClickedEventListener(final ClickedEvent event) {
        this.clickedEventListeners.addListener((ClickedEvent) event);
    }

    /**
     * Add event listener to the control
     * 
     * @param event
     *            the event listener to add
     */
    public void addFocusEventListener(final FocusEvent event) {
        this.focusEventListeners.addListener(event);
    }

    /**
     * Add event listener to the control
     * 
     * @param event
     *            the event listener to add
     */
    public void addHoverEventListener(final HoverEvent event) {
        this.mouseHoverEventListeners.addListener(event);
    }

    /**
     * Remove event listener from the control
     * 
     * @param event
     *            the event listener to remove
     */
    public void removeClickedEventListener(final ClickedEvent event) {
        if (event == null) {
            throw new NullPointerException("Parameter event is null");
        }

        this.clickedEventListeners.removeListener(event);
    }

    /**
     * Remove event listener from the control
     * 
     * @param event
     *            the event listener to remove
     */
    public void removeFocusEventListener(final FocusEvent event) {
        if (event == null) {
            throw new NullPointerException("Parameter event is null");
        }

        this.focusEventListeners.removeListener(event);
    }

    /**
     * Remove event listener from the control
     * 
     * @param event
     *            the event listener to remove
     */
    public void removeHoverEventListener(final HoverEvent event) {
        if (event == null) {
            throw new NullPointerException("Parameter event is null");
        }

        this.mouseHoverEventListeners.removeListener(event);
    }

    /**
     * Contains list of events listeners that should be called for a specific event
     *
     * @param <X>
     *            the event type
     */
    protected static class RegisteredEventListeners<X extends Event> {

        private final Set<X> listeners = Collections.newSetFromMap(new ConcurrentHashMap<X, Boolean>());

        public void addListener(final X listener) {
            this.listeners.add(listener);
        }

        public void removeListener(final X listener) {
            this.listeners.remove(listener);
        }

        public Set<X> getListeners() {
            return Collections.unmodifiableSet(this.listeners);
        }
    }
}
