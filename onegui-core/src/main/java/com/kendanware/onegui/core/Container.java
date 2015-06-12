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
