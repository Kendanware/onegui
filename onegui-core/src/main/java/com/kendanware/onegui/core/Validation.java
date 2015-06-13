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

import java.util.List;

/**
 * Contains various validation methods.
 *
 * @author Daniel Johansson, Kendanware
 * @author Kenny Colliander Nordin, Kendanware
 *
 * @since 0.0.1
 */
public class Validation {

    /**
     * Check that the id is unique. Scan the tree of components for the id
     *
     * @param id
     *            the id to check
     * @throws IllegalArgumentException
     *             if the id is invalid
     */
    public static void checkId(final Component component, final String id) {

        if ((id == null) || id.trim().isEmpty()) {
            throw new IllegalArgumentException("Invalid id: " + id);
        }

        if (component == null) {
            return;
        }

        Component parent = component;
        while (parent.getParent() != null) {
            parent = parent.getParent();
        }

        Validation.checkIdTraverse(component, id);
    }

    protected static void checkIdTraverse(final Component component, final String id) {

        if (id.equals(component.getId())) {
            throw new IllegalArgumentException("Invalid id; already in use: " + id);
        }

        if (component instanceof Container) {
            final Container container = (Container) component;

            final List<Component> list = container.getChildren();

            for (final Component child : list) {
                Validation.checkId(child, id);
            }
        }
    }
}
