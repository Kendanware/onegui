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
package com.kendanware.onegui.core;

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

    private final String id;

    private Style style;

    private final Container parent;

    private final OneGui oneGui;

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
        Validation.checkId(parent, id);

        this.parent = parent;
        this.id = id;

        this.registerInParent(parent);

        if (parent != null) {
            final Screen screen = this.getScreen(parent);

            this.oneGui = screen.getOneGui();
        } else {
            throw new IllegalStateException("OneGui is null");
        }
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
    Component(final OneGui oneGui, final Container parent, final String id) {
        this.checkParentComponent(parent);
        Validation.checkId(parent, id);

        this.parent = parent;
        this.id = id;

        this.registerInParent(parent);

        if (oneGui != null) {
            this.oneGui = oneGui;
        } else if (parent != null) {
            final Screen screen = this.getScreen(parent);

            this.oneGui = screen.getOneGui();
        } else {
            throw new IllegalStateException("OneGui is null");
        }
    }

    protected Screen getScreen(final Container parent) {

        Component newParent = parent;
        while (newParent.getParent() != null) {
            newParent = newParent.getParent();
        }

        if (!(newParent instanceof Screen)) {
            throw new IllegalArgumentException("No parent instance of Screen found");
        }
        return (Screen) newParent;
    }

    protected void checkParentComponent(final Container parent) {
        if (parent == null) {
            throw new IllegalArgumentException("Parent Container is null");
        }
    }

    public OneGui getOneGui() {
        return oneGui;
    }

    public Container getParent() {
        return this.parent;
    }

    protected void registerInParent(final Container parent) {
        parent.addComponent(this);
    }

    /**
     * @return the id
     */
    public String getId() {
        return this.id;
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
            this.getOneGui().execute(() -> {
                listener.clicked(this.getId());
            });
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
                this.getOneGui().execute(() -> {
                    listener.focus(this.getId(), focus);
                });
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
                this.getOneGui().execute(() -> {
                    listener.hover(this.getId(), over);
                });
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
        this.clickedEventListeners.addListener(event);
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

    @Override
    public String toString() {
        return "Component [id=" + id + ", class=" + this.getClass().getSimpleName() + "]";
    }

}
