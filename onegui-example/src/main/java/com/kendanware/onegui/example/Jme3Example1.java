package com.kendanware.onegui.example;

import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import com.jme3.system.AppSettings;
import com.jme3.system.JmeContext;
import com.jme3.texture.Image;
import com.jme3.texture.Texture2D;
import com.jme3.texture.plugins.AWTLoader;
import com.jme3.ui.Picture;
import com.jme3.util.BufferUtils;
import com.kendanware.onegui.core.OneGui;
import com.kendanware.onegui.core.Screen;
import com.kendanware.onegui.core.container.Panel;
import com.kendanware.onegui.core.control.Button;
import com.kendanware.onegui.core.control.Label;
import com.kendanware.onegui.core.renderer.OneGuiRenderer;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.concurrent.TimeUnit;

/**
 * @author Daniel Johansson
 * @since 2015-06-19
 */
public class Jme3Example1 extends SimpleApplication {

    private OneGui oneGui;
    private OneGuiRenderer oneGuiRenderer;
    private Image image;
    private ByteBuffer byteBuffer;
    private Label label;
    private Texture2D texture;
    private long lastUpdated;

    @Override
    public void simpleInitApp() {
        initGui();
        flyCam.setMoveSpeed(5.0f);

        final Geometry geometry = new Geometry("", new Box(0.5f, 0.5f, 0.5f));
        geometry.setMaterial(new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md"));
        geometry.getMaterial().setColor("Color", ColorRGBA.White);
        geometry.getMaterial().setTexture("ColorMap", texture);

        rootNode.attachChild(geometry);
    }

    private void initGui() {
        oneGui = new OneGui();

        try (InputStream inputStream = Example1.class.getResourceAsStream("/example1.ogs")) {
            oneGui.addStyles(inputStream);
        } catch (Exception e) {

        }

        final Screen screen = new Screen(oneGui, "screen");
        Panel leftPanel = new Panel(screen, "panelLeft");
        Button startNewGameButton = new Button(leftPanel, "startNewGame");

        startNewGameButton.addClickedEventListener(id -> {
            System.out.println("Clicked: " + id);
        });

        Button showSettingsButton = new Button(leftPanel, "showSettings");

        Panel centerspace = new Panel(screen, "panelCenterSpace");

        Panel rightPanel = new Panel(screen, "panelRight");

        label = new Label(rightPanel, "label1");
        label.setText("SettingslåäöÅÄÖqhjKp");
        Label settingsLabel2 = new Label(rightPanel, "label2");
        settingsLabel2.setText("SettingslåäöÅÄÖqhjKp");
        Label settingsLabel3 = new Label(rightPanel, "label3");
        settingsLabel3.setText("SettingslåäöÅÄÖqhjKp");

        Button button2 = new Button(rightPanel, "saveSettings");

        startNewGameButton.click();

        oneGuiRenderer = new OneGuiRenderer(screen, 1280, 720);
        oneGuiRenderer.update(1);

        final BufferedImage bufferedImage = oneGuiRenderer.generateImage();
        image = new AWTLoader().load(bufferedImage, true);

        final byte[] pixels = ((DataBufferByte) bufferedImage.getRaster().getDataBuffer()).getData();
        flipImage(pixels, (int) oneGuiRenderer.getWidth(), (int) oneGuiRenderer.getHeight(), bufferedImage.getColorModel().getPixelSize());
        byteBuffer = BufferUtils.createByteBuffer(pixels.length);
        byteBuffer.put(pixels);
        byteBuffer.flip();

        texture = new Texture2D(image);
        texture.setAnisotropicFilter(2);
        final Picture picture = new Picture("HUD Picture");
        picture.setWidth(settings.getWidth());
        picture.setHeight(settings.getHeight());
        picture.setPosition(0, 0);
        picture.setTexture(assetManager, texture, true);

        guiNode.attachChild(picture);
    }

    @Override
    public void simpleUpdate(float tpf) {
        super.simpleUpdate(tpf);
        oneGuiRenderer.update(tpf);

        if (timer.getTime() - lastUpdated < TimeUnit.MILLISECONDS.toNanos(40)) {
            return;
        }

        final BufferedImage bufferedImage = oneGuiRenderer.generateImage();
        final byte[] pixels = ((DataBufferByte) bufferedImage.getRaster().getDataBuffer()).getData();
        flipImage(pixels, (int) oneGuiRenderer.getWidth(), (int) oneGuiRenderer.getHeight(), bufferedImage.getColorModel().getPixelSize());
        byteBuffer.clear();
        byteBuffer.put(pixels);
        byteBuffer.flip();
        image.setData(byteBuffer);
        lastUpdated = timer.getTime();

        label.setText(System.currentTimeMillis() + "");
    }

    @Override
    public void stop() {
        oneGui.shutdown();
        super.stop();
    }

    public static void main(final String... arguments) {
        final AppSettings settings = new AppSettings(true);
        settings.setResolution(1280, 720);

        final Jme3Example1 example = new Jme3Example1();
        example.setSettings(settings);
        example.start(JmeContext.Type.Display);
    }

    private void flipImage(byte[] img, int width, int height, int bpp) {
        int scSz = (width * bpp) / 8;
        byte[] sln = new byte[scSz];
        int y2 = 0;
        for (int y1 = 0; y1 < height / 2; y1++) {
            y2 = height - y1 - 1;
            System.arraycopy(img, y1 * scSz, sln, 0, scSz);
            System.arraycopy(img, y2 * scSz, img, y1 * scSz, scSz);
            System.arraycopy(sln, 0, img, y2 * scSz, scSz);
        }
    }
}
