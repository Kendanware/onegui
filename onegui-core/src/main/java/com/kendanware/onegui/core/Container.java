package com.kendanware.onegui.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Base class for containers, for example windows and panels.
 * 
 * @author Daniel Johansson, Kendanware
 * @author Kenny Colliander Nordin, Kendanware
 * 
 * @since 0.0.1
 */
public class Container extends Component {
    private final AtomicReference<List<Component>> components = new AtomicReference<List<Component>>(Arrays.asList());

    private final Lock componentsLock = new ReentrantLock();

    public Container(Container parent) {
        super(parent);
    }

    public Container(Container parent, String id) {
        super(parent, id);
    }

    public void addComponent(final Component component) {

        this.componentsLock.lock();
        try {
            final List<Component> components = this.components.get();

            if (!components.contains(component)) {
                final List<Component> newComponents = new ArrayList<Component>(components);
                newComponents.add(component);
                this.components.set(Collections.unmodifiableList(newComponents));
            }
        } finally {
            this.componentsLock.unlock();
        }
    }

    public void removeComponent(Component component) {
        this.componentsLock.lock();
        try {
            final List<Component> components = this.components.get();

            if (components.contains(component)) {
                final List<Component> newComponents = new ArrayList<Component>(components);
                newComponents.remove(component);
                this.components.set(Collections.unmodifiableList(newComponents));
            } else {
                for (Component current : components) {
                    if (current instanceof Container) {
                        ((Container) current).removeComponent(component);
                    }
                }
            }
        } finally {
            this.componentsLock.unlock();
        }
    }

    public List<Component> getChildren() {
        return this.components.get();
    }

}
