package site.gr8.mattis.creatingminecraft;

import org.lwjgl.glfw.GLFW;
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

public class Main {

    private static Logger LOGGER = new Logger();
    private static Settings settings = new Settings();
    public static AdditionalSettings additionalSettings = new AdditionalSettings();

    private static long lastTime = System.currentTimeMillis();
    private static int frames = 0;


    public static void main(String[] args) {
        settings.init();
        additionalSettings.init();
        GLX.initGLFW();
        Window window = new Window(settings);
        window.createWindow();

        Loader loader = new Loader();
        Renderer renderer = new Renderer();
        StaticShader shader = new StaticShader();

        float[] vertices = {
                -0.5f, 0.5f, 0,
                -0.5f, -0.5f, 0,
                0.5f, -0.5f, 0,
                0.5f, 0.5f, 0
        };
        int[] indices = { //  0, 1, 1, 2, 2, 3, 3, 4, 4, 5, 5, 6, 6, 7, 7, 8, 8, 9, 9, 0
                0, 1, 3,
                3, 1, 2
        };
        RawModel model = loader.loadToVAO(vertices, indices);

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
                    Window.toggleFullscreen(Window.getWindowID());
            }

            if (canRender) { // rendering stuff
                GLX.prepare();
                shader.start();

                renderer.render(model);

                shader.stop();
                calcFps();
                GLX.flipFrame();
            }
        }

        shader.cleanUp();
        loader.cleanUp();
        Window.closeDisplay();
    }

    private static void calcFps() {
        frames++;
        while (System.currentTimeMillis() >= lastTime + 1000L) {
            LOGGER.info(frames + " fps ");
            lastTime += 1000L;
            frames = 0;
        }
    }

}

    /*
                -0.4f, 0.2f, 0,
                -0.15f, 0.2f, 0,
                0f, 0.6f, 0,
                0.15f, 0.2f, 0,
                0.4f, 0.2f, 0,
                0.15f, -0.15f, 0,
                0.2f, -0.6f, 0,
                0f, -0.3f, 0,
                -0.2f, -0.6f, 0,
                -0.15f, -0.15f, 0,
                                        */
