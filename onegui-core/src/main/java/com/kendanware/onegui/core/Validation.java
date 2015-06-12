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

        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("Invalid id: " + id);
        }

        if (component == null) {
            return;
        }

        Component parent = component;
        while (parent.getParent() != null) {
            parent = parent.getParent();
        }

        checkIdTraverse(component, id);
    }

    protected static void checkIdTraverse(final Component component, final String id) {

        if (id.equals(component.getId())) {
            throw new IllegalArgumentException("Invalid id; already in use: " + id);
        }

        if (component instanceof Container) {
            final Container container = (Container) component;

            final List<Component> list = container.getChildren();

            for (final Component child : list) {
                checkId(child, id);
            }
        }
    }
}
