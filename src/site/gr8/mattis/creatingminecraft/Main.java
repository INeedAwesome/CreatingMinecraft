package site.gr8.mattis.creatingminecraft;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;
import site.gr8.mattis.creatingminecraft.core.audio.AudioMaster;
import site.gr8.mattis.creatingminecraft.core.audio.Source;
import site.gr8.mattis.creatingminecraft.core.input.Input;
import site.gr8.mattis.creatingminecraft.core.logger.Logger;
import site.gr8.mattis.creatingminecraft.core.shader.StaticShader;
import site.gr8.mattis.creatingminecraft.core.util.GLX;
import site.gr8.mattis.creatingminecraft.renderEngine.Loader;
import site.gr8.mattis.creatingminecraft.renderEngine.RawModel;
import site.gr8.mattis.creatingminecraft.renderEngine.Renderer;
import site.gr8.mattis.creatingminecraft.settings.AdditionalSettings;
import site.gr8.mattis.creatingminecraft.settings.Settings;
import site.gr8.mattis.creatingminecraft.window.Window;

import java.util.ArrayList;
import java.util.List;

public class Main {

    private static final Logger LOGGER = Logger.get();
    private static final Settings settings = Settings.get();
    private static final AdditionalSettings additionalSettings = AdditionalSettings.get();

    private static List<Integer> sounds = new ArrayList<>();

    private static float x = -16, y = 0, z = 0;

    public static void main(String[] args) {
        settings.init();
        GLX.initGLFW();

        AudioMaster.init();
        AudioMaster.setListenerData(0, 0, 2);

        Window window = Window.get();
        window.createWindow();
        additionalSettings.init();

        int sound = AudioMaster.loadSound("resources/new.ogg");
        int sound2 = AudioMaster.loadSound("resources/filewav.ogg");
        sounds.add(sound);
        sounds.add(sound2);
        LOGGER.warn("If you hear a screeching in the sound contact the creator @ http://mattis.gr8.site");
        Source source = new Source();
        source.setPosition(0, 0, 0);

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
                0.5f, 0.0f, 0.0f,
                1.0f, 0.5f, 1.0f,
                0.9f, 0.16f, 0.5f,
                0.2f, 0.5f, 0.5f,
        };
        RawModel model = loader.loadToVAO(vertices, colours, indices);

        boolean wireFrame = false;

        double frame_cap = 1.0 / Double.parseDouble(settings.getProperty("fps"));
        double time = (double) System.nanoTime() / (double) 1_000_000_000L;
        double unprocessed = 0;


        LOGGER.info("Initializing game loop!");
        while (!GLX.shouldClose()) {
            boolean canRender = false;
            double time_2 = (double) System.nanoTime() / (double) 1_000_000_000L;
            unprocessed += (time_2 - time);
            time = time_2;
            while (unprocessed >= frame_cap) { // key presses
                unprocessed -= frame_cap;
                canRender = true;
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
                if (Input.isKeyPressed(GLFW.GLFW_KEY_1))
                    source.play(sound2);
                if (Input.isKeyPressed(GLFW.GLFW_KEY_2))
                    source.play(sound);
                if (Input.isKeyPressed(GLFW.GLFW_KEY_SPACE)) {
                    if (source.isPlaying())
                        source.pause();
                    else source.continuePlaying();
                }
            }

            if (canRender) { // rendering stuff
                source.setPosition(x += 0.03f, y, z);
                LOGGER.info(x);
                GLX.prepare();
                shader.start();
                renderer.render(model, shader);
                shader.stop();
                GLX.flipFrame();
            }
        }

        AudioMaster.cleanUp();
        shader.cleanUp();
        loader.cleanUp();
        Window.closeDisplay();
    }
}