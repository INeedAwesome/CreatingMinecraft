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
import site.gr8.mattis.creatingminecraft.renderEngine.Camera;
import site.gr8.mattis.creatingminecraft.renderEngine.Loader;
import site.gr8.mattis.creatingminecraft.renderEngine.RawModel;
import site.gr8.mattis.creatingminecraft.renderEngine.Renderer;
import site.gr8.mattis.creatingminecraft.settings.AdditionalSettings;
import site.gr8.mattis.creatingminecraft.settings.Settings;
import site.gr8.mattis.creatingminecraft.window.Window;

import java.util.Random;

public class Main {

    private static final Logger LOGGER = Logger.get();
    private static final Settings settings = Settings.get();
    private static final AdditionalSettings additionalSettings = AdditionalSettings.get();

    private static Source soundBuffer;
    private static Source musicBuffer;

    private static int sound;
    private static int sound2;
    private static int sound3;
    private static int pigstep;
    private static int grassWalk;
    private static int comforting_memories;
    private static boolean wireFrame = false;
    private static boolean mouseBound = true;

    private static boolean running = true;

    public static void main(String[] args) {
        settings.init();
        GLX.initGLFW();

        AudioMaster.init();
        AudioMaster.setListenerData(0, 0, 2);

        Window window = Window.get();
        window.createWindow();
        additionalSettings.init();

        sound = AudioMaster.loadSound("resources/new.ogg");
        sound2 = AudioMaster.loadSound("resources/filewav.ogg");
        sound3 = AudioMaster.loadSound("resources/Minecraft.ogg");
        pigstep = AudioMaster.loadSound("resources/pigstep.ogg");
        grassWalk = AudioMaster.loadSound("resources/grass.ogg");
        comforting_memories = AudioMaster.loadSound("resources/comforting_memories.ogg");
        soundBuffer = new Source();
        musicBuffer = new Source();
        soundBuffer.setPosition(0, 0, 0);

        Loader loader = new Loader();
        Renderer renderer = new Renderer();
        StaticShader shader = new StaticShader();

        float[] vertices = {
                -0.5f, 0.5f, -1.05f,
                -0.5f, -0.5f, -1.05f,
                0.5f, -0.5f, -1.05f,
                0.5f, 0.5f, -1.05f
        };
        int[] indices = { //  0, 1, 1, 2, 2, 3, 3, 4, 4, 5, 5, 6, 6, 7, 7, 8, 8, 9, 9, 0
                0, 1, 3,
                3, 1, 2
        };
        float[] colours = new float[]{ // rgb values
                0.5f, 1.0f, 0.0f,
                1.0f, 0.5f, 1.0f,
                0.9f, 0.16f, 0.5f,
                0.2f, 0.5f, 0.5f,
        };
        RawModel model = loader.loadToVAO(vertices, colours, indices);

        Camera camera = new Camera();
        camera.setPosition(new Vector3f(0, 0, 3));

        double frame_cap = 1.0 / Double.parseDouble(settings.getProperty("fps"));
        double time = (double) System.nanoTime() / (double) 1_000_000_000L;
        double unprocessed = 0;

        GLFW.glfwSetInputMode(Window.getWindowID(), GLFW.GLFW_CURSOR, GLFW.GLFW_CURSOR_DISABLED);

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
            }

            if (canRender) { // rendering stuff
                GLX.prepare();
                shader.start();
                renderer.render(model, shader, camera);
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
        if (Input.isKeyPressed(GLFW.GLFW_KEY_1))
            soundBuffer.play(sound);
        if (Input.isKeyPressed(GLFW.GLFW_KEY_2))
            soundBuffer.play(sound2);
        if (Input.isKeyPressed(GLFW.GLFW_KEY_3))
            soundBuffer.play(sound3);
        if (Input.isKeyPressed(GLFW.GLFW_KEY_4))
            musicBuffer.play(pigstep);
        if (Input.isKeyPressed(GLFW.GLFW_KEY_5))
            soundBuffer.play(grassWalk, new Random().nextFloat(0.8f, 1.1f));
        if (Input.isKeyPressed(GLFW.GLFW_KEY_6))
            soundBuffer.play(comforting_memories);
        if (Input.isKeyPressed(GLFW.GLFW_KEY_SPACE)) {
            if (soundBuffer.isPlaying())
                soundBuffer.pause();
            else soundBuffer.continuePlaying();

        }
    }
}