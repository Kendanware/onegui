package com.kendanware.onegui.core;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.kendanware.onegui.core.style.Style;
import com.kendanware.onegui.core.style.StyleParser;

/**
 * 
 * @author Daniel Johansson, Kendanware
 * @author Kenny Colliander Nordin, Kendanware
 * 
 * @since 0.0.1
 */
public class OneGui {
    private final Map<String, Screen> screens = new ConcurrentHashMap<>();

    private final Lock screensLock = new ReentrantLock();

    private final Map<String, Style> styles = new ConcurrentHashMap<>();

    private Screen current;

    public void addScreen(final Screen screen) {
        this.screensLock.lock();
        try {
            if (this.screens.containsKey(screen.getId()) || this.screens.values().contains(screen)) {
                throw new IllegalArgumentException("Screen is already added");
            }

            this.screens.put(screen.getId(), screen);
        } finally {
            this.screensLock.unlock();
        }
    }

    public void removeScreen(final Screen screen) {
        this.screensLock.lock();
        try {
            this.screens.remove(screen.getId());
        } finally {
            this.screensLock.unlock();
        }
    }

    public void addStyles(final InputStream inputStream) throws IOException {

        try {
            final StyleParser styleParser = new StyleParser();
            this.styles.putAll(styleParser.parseStyle(inputStream));
        } finally {
            inputStream.close();
        }
    }

}
