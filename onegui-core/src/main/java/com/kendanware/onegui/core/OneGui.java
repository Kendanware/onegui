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
