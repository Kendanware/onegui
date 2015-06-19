package com.kendanware.onegui.example;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import javax.imageio.ImageIO;

import com.kendanware.onegui.core.Align;
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
