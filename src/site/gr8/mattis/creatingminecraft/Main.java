/*
 * MIT License
 *
 * Copyright (c) 2022 Mattis Kjellerås
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package site.gr8.mattis.creatingminecraft;

import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;
import site.gr8.mattis.creatingminecraft.core.audio.AudioMaster;
import site.gr8.mattis.creatingminecraft.core.audio.Source;
import site.gr8.mattis.creatingminecraft.core.input.Input;
import site.gr8.mattis.creatingminecraft.core.logger.Logger;
import site.gr8.mattis.creatingminecraft.core.shader.StaticShader;
import site.gr8.mattis.creatingminecraft.core.util.GLX;
import site.gr8.mattis.creatingminecraft.renderEngine.*;
import site.gr8.mattis.creatingminecraft.settings.AdditionalSettings;
import site.gr8.mattis.creatingminecraft.settings.Settings;
import site.gr8.mattis.creatingminecraft.window.Window;
import site.gr8.mattis.creatingminecraft.world.Block;

public class Main {

    private static final Logger LOGGER = Logger.get();
    private static final Settings settings = Settings.get();
    private static final AdditionalSettings additionalSettings = AdditionalSettings.get();

    private static Source soundBuffer;
    private static StaticShader shader;
    private static Loader loader;

    private static boolean wireFrame = false;
    private static boolean mouseBound = true;
    private static boolean running = true;
    private static int sound;

    public static void main(String[] args) {
        settings.init();
        GLX.initGLFW();

        AudioMaster.init();
        AudioMaster.setListenerPosition(0, 0, 2);

        Window window = Window.get();
        window.createWindow();

        additionalSettings.init();

        soundBuffer = new Source();
        soundBuffer.setPosition(0, 0, 0);
        sound = AudioMaster.loadSound("resources/sounds/records/pigstep.ogg");

        loader = new Loader();
        Renderer renderer = new Renderer();
        shader = new StaticShader();

        Block block = new Block();
        int[] indices = block.indices;
        float[] colours = block.colours;
        float[] uvs = block.uvs;
        float[] ve = Block.returnNewVertices(1, 1, 1);
        RawModel model = loader.loadToVAO(ve, colours, indices, uvs);

        Camera camera = new Camera();
        camera.setPosition(new Vector3f(0, 0, 1));

        Texture texture = new Texture();
        texture.genTexture("resources/textures/blocks/blocks.png");

        window.showWindow();
        GLFW.glfwSetInputMode(Window.getWindowID(), GLFW.GLFW_CURSOR, GLFW.GLFW_CURSOR_DISABLED);

        double frame_cap = 1.0 / Double.parseDouble(settings.getProperty("fps"));
        double time = (double) System.nanoTime() / (double) 1_000_000_000L;
        double unprocessed = 0;

        LOGGER.info("Initializing game loop!");
        while (!GLX.shouldClose() && running) {
            float delta = (float) frame_cap;
            boolean canRender = false;
            double time_2 = (double) System.nanoTime() / (double) 1_000_000_000L;
            unprocessed += (time_2 - time);
            time = time_2;
            while (unprocessed >= frame_cap) { // key presses / moving
                unprocessed -= frame_cap;
                canRender = true;
                handleInput();
                camera.move(delta);
                AudioMaster.setListenerPosition(camera.getPosition());
                AudioMaster.setOrientation(camera.getPitch(), camera.getYaw(), camera.getRoll());
            }

            if (canRender) { // rendering stuff
                GLX.prepare();
                shader.start();
                renderer.render(model, shader, camera, texture);
                //LOGGER.info((Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1024  + "KB"); // / 1024 + "MB");
                shader.stop();
                GLX.flipFrame();
            }
        }

        cleanUp();
    }

    public static void cleanUp() {
        AudioMaster.cleanUp();
        shader.cleanUp();
        loader.cleanUp();
        Window.closeDisplay();
    }

    public static void handleInput() {
        if (Input.isKeyPressed(GLFW.GLFW_KEY_F11)) Window.toggleFullscreen();
        if (Input.isKeyPressed(GLFW.GLFW_KEY_F1)) if (!wireFrame) {
            wireFrame = true;
            GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_LINE);
        } else {
            wireFrame = false;
            GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_FILL);
        }
        if (Input.isKeyPressed(GLFW.GLFW_KEY_LEFT_CONTROL)) {
            if (mouseBound) {
                GLFW.glfwSetInputMode(Window.getWindowID(), GLFW.GLFW_CURSOR, GLFW.GLFW_CURSOR_NORMAL);
                mouseBound = false;
            } else {
                GLFW.glfwSetInputMode(Window.getWindowID(), GLFW.GLFW_CURSOR, GLFW.GLFW_CURSOR_DISABLED);
                mouseBound = true;
            }
        }
        if (Input.isKeyPressed(GLFW.GLFW_KEY_ESCAPE)) running = false;
        if (Input.isKeyPressed(GLFW.GLFW_KEY_9)) {
            soundBuffer.play(sound);
        }
    }
}