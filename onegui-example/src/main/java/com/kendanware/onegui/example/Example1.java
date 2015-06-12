package com.kendanware.onegui.example;

import com.kendanware.onegui.core.OneGui;
import com.kendanware.onegui.core.Screen;
import com.kendanware.onegui.core.container.Panel;
import com.kendanware.onegui.core.control.Button;

public class Example1 {

    public static void main(String[] args) {

        OneGui oneGui = new OneGui();

        Screen screen = new Screen();

        Panel leftPanel = new Panel(screen, "panelLeft");

        Button button = new Button(leftPanel, "startNewGame");

        button.addClickedEventListener(id -> {
            System.out.println("Clicked: " + id);
        });

        Panel rightPanel = new Panel(screen, "panelRight");

        Button button2 = new Button(rightPanel, "startNewGame");

        button.click();

    }

}
