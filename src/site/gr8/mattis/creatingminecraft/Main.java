package site.gr8.mattis.creatingminecraft;

import org.joml.Vector3f;
import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;
import org.lwjgl.stb.STBEasyFont;
import site.gr8.mattis.creatingminecraft.core.audio.AudioMaster;
import site.gr8.mattis.creatingminecraft.core.audio.Source;
import site.gr8.mattis.creatingminecraft.core.input.Input;
import site.gr8.mattis.creatingminecraft.core.logger.Logger;
import site.gr8.mattis.creatingminecraft.core.shader.StaticShader;
import site.gr8.mattis.creatingminecraft.core.util.GLX;
import site.gr8.mattis.creatingminecraft.core.util.IOUtil;
import site.gr8.mattis.creatingminecraft.renderEngine.*;
import site.gr8.mattis.creatingminecraft.settings.AdditionalSettings;
import site.gr8.mattis.creatingminecraft.settings.Settings;
import site.gr8.mattis.creatingminecraft.window.Window;

import java.io.IOException;
import java.nio.ByteBuffer;

public class Main {

    private static final Logger LOGGER = Logger.get();
    private static final Settings settings = Settings.get();
    private static final AdditionalSettings additionalSettings = AdditionalSettings.get();

    private static Source soundBuffer;

    private static int sound;
    private static int quads;

    private static boolean wireFrame = false;
    private static boolean mouseBound = true;
    private static boolean running = true;

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

        Loader loader = new Loader();
        Renderer renderer = new Renderer();
        StaticShader shader = new StaticShader();

        float[] vertices = {
                -0.5f, 0.5f, 0.5f,// VO
                -0.5f, -0.5f, 0.5f,// V1
                0.5f, -0.5f, 0.5f,// V2
                0.5f, 0.5f, 0.5f,// V3
                -0.5f, 0.5f, -0.5f,// V4
                0.5f, 0.5f, -0.5f,// V5
                -0.5f, -0.5f, -0.5f,// V6
                0.5f, -0.5f, -0.5f,// V7
        };
        int[] indices = {
                0, 1, 3, 3, 1, 2,// Front face
                4, 0, 3, 5, 4, 3,// Top Face
                3, 2, 7, 5, 3, 7, // Right face
                6, 1, 0, 6, 0, 4,// Left face
                2, 1, 6, 2, 6, 7,// Bottom face
                7, 6, 4, 7, 4, 5,// Back face
        };
        float[] colours = new float[]{ // rgb values
                0.5f, 1.0f, 0.0f,
                1.0f, 0.5f, 1.0f,
                0.9f, 0.16f, 0.5f,
                0.2f, 0.5f, 0.5f,

                0.5f, 0.7f, 0.5f,
                1.0f, 0.2f, 1.0f,
                1.9f, 0.56f, 0.5f,
                0.5f, 1.0f, 0.5f,
        };
        float[] uvs = new float[] {
                1, 0,
                1, 1,
                0, 1,
                0, 0
        };
        RawModel model = loader.loadToVAO(vertices, colours, indices, uvs);

        Camera camera = new Camera();
        camera.setPosition(new Vector3f(0, 0, 1));

        Texture texture = new Texture();
        texture.genTexture("resources/textures/blocks/grass_block_side.png");

        double frame_cap = 1.0 / Double.parseDouble(settings.getProperty("fps"));
        double time = (double) System.nanoTime() / (double) 1_000_000_000L;
        double unprocessed = 0;

        GLFW.glfwSetInputMode(Window.getWindowID(), GLFW.GLFW_CURSOR, GLFW.GLFW_CURSOR_DISABLED);

        try {
            ByteBuffer source = IOUtil.ioResourceToByteBuffer("resources/fonts/testing.txt", 4 * 1024);
            String text = "Hello World!"; // = MemoryUtil.memUTF8(source).replaceAll("\t", "    ");
            ByteBuffer charBuffer = BufferUtils.createByteBuffer(text.length() * 270);
            quads = STBEasyFont.stb_easy_font_print(10, 10, text, null, charBuffer);
            LOGGER.info(text.length() * 270);
            LOGGER.info(charBuffer);

        } catch (IOException e) {   e.printStackTrace();    }

        LOGGER.info("Initializing game loop!");
        while (!GLX.shouldClose() && running) {
            boolean canRender = false;
            double time_2 = (double) System.nanoTime() / (double) 1_000_000_000L;
            unprocessed += (time_2 - time);
            time = time_2;
            while (unprocessed >= frame_cap) { // key presses
                unprocessed -= frame_cap;
                canRender = true;
                handleInput();
                camera.move();
                AudioMaster.setListenerPosition(camera.getPosition());
                AudioMaster.setOrientation(camera.getPitch(), camera.getYaw(), camera.getRoll());
            }

            if (canRender) { // rendering stuff
                GLX.prepare();
                shader.start();
                renderer.render(model, shader, camera, texture);
                GL11.glDrawArrays(GL11.GL_QUADS, 0, quads * 4);
                shader.stop();
                GLX.flipFrame();
            }
        }

        AudioMaster.cleanUp();
        shader.cleanUp();
        loader.cleanUp();
        Window.closeDisplay();
    }

    public static void handleInput() {

        if (Input.isKeyPressed(GLFW.GLFW_KEY_F11))
            Window.toggleFullscreen();
        if (Input.isKeyPressed(GLFW.GLFW_KEY_F1))
            if (!wireFrame) {
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
        if (Input.isKeyPressed(GLFW.GLFW_KEY_ESCAPE))
            running = false;
        if (Input.isKeyPressed(GLFW.GLFW_KEY_9)) {
            soundBuffer.play(sound);
        }
    }
}