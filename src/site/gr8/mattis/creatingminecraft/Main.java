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

import java.util.ArrayList;
import java.util.List;

public class Main {

    private static final Logger LOGGER = Logger.get();
    private static final Settings settings = Settings.get();
    private static final AdditionalSettings additionalSettings = AdditionalSettings.get();

    private static Source soundBuffer;
    private static StaticShader shader;
    private static Loader loader;

    private static int sound;

    private static boolean wireFrame = false;
    private static boolean mouseBound = true;
    private static boolean running = true;
    private static boolean usingInternalServer;

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

        List<Vector3f> verts = new ArrayList<>();
        verts.add(new Vector3f(-0.5f, 0.5f, 0.5f));
        verts.add(new Vector3f(-0.5f, -0.5f, 0.5f));
        verts.add(new Vector3f(0.5f, -0.5f, 0.5f));
        verts.add(new Vector3f(0.5f, 0.5f, 0.5f));
        verts.add(new Vector3f(-0.5f, 0.5f, -0.5f));
        verts.add(new Vector3f(0.5f, 0.5f, -0.5f));
        verts.add(new Vector3f(-0.5f, -0.5f, -0.5f)); // testing

        Block block = new Block();
        float[] vertices = block.vertices;
        int[]   indices =  block.indices;
        float[] colours =  block.colours;
        float[] uvs =      block.uvs;
        RawModel model = loader.loadToVAO(vertices, colours, indices, uvs, verts);


        Camera camera = new Camera();
        camera.setPosition(new Vector3f(0, 0, 1));

        Texture texture = new Texture();
        texture.genTexture("resources/textures/blocks/grass_block_side.png");

        double frame_cap = 1.0 / Double.parseDouble(settings.getProperty("fps"));
        double time = (double) System.nanoTime() / (double) 1_000_000_000L;
        double unprocessed = 0;

        window.showWindow();
        GLFW.glfwSetInputMode(Window.getWindowID(), GLFW.GLFW_CURSOR, GLFW.GLFW_CURSOR_DISABLED);

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
                renderer.render(model, shader, camera, texture, verts);
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