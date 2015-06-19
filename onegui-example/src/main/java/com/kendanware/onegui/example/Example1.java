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
package com.kendanware.onegui.example;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import javax.imageio.ImageIO;

import com.kendanware.onegui.core.OneGui;
import com.kendanware.onegui.core.Screen;
import com.kendanware.onegui.core.container.Panel;
import com.kendanware.onegui.core.control.Button;
import com.kendanware.onegui.core.control.Label;
import com.kendanware.onegui.core.renderer.ComponentInfo;
import com.kendanware.onegui.core.renderer.OneGuiRenderer;
import com.kendanware.onegui.core.style.Style;

public class Example1 {

    public static void main(String[] args) throws IOException {

        OneGui oneGui = new OneGui();
        try {
            try (InputStream inputStream = Example1.class.getResourceAsStream("/example1.ogs")) {
                oneGui.addStyles(inputStream);
            }

            for (Map.Entry<String, Style> entry : oneGui.getStyles().entrySet()) {
                System.out.println(entry.getKey() + " " + entry.getValue());
            }

            Screen screen = new Screen(oneGui, "screen");
            Panel leftPanel = new Panel(screen, "panelLeft");
            Button startNewGameButton = new Button(leftPanel, "startNewGame");

            startNewGameButton.addClickedEventListener(id -> {
                System.out.println("Clicked: " + id);
            });

            Button showSettingsButton = new Button(leftPanel, "showSettings");

            Panel centerspace = new Panel(screen, "panelCenterSpace");

            Panel rightPanel = new Panel(screen, "panelRight");

            Label settingsLabel = new Label(rightPanel, "label1");
            settingsLabel.setText("SettingslåäöÅÄÖqhjKp");
            Label settingsLabel2 = new Label(rightPanel, "label2");
            settingsLabel2.setText("SettingslåäöÅÄÖqhjKp");
            Label settingsLabel3 = new Label(rightPanel, "label3");
            settingsLabel3.setText("SettingslåäöÅÄÖqhjKp");

            Button button2 = new Button(rightPanel, "saveSettings");

            startNewGameButton.click();

            OneGuiRenderer oneGuiRenderer = new OneGuiRenderer(screen, 800, 600);

            oneGuiRenderer.update(1);

            Map<String, ComponentInfo> map = oneGuiRenderer.getComponentInfos();

            for (Map.Entry<String, ComponentInfo> entry : map.entrySet()) {
                System.out.println(entry.getKey() + " " + entry.getValue());
            }

            BufferedImage bufferedImage = oneGuiRenderer.generateImage();

            ImageIO.write(bufferedImage, "PNG", new File("target/rendered.png"));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            oneGui.shutdown();
        }
    }
}
