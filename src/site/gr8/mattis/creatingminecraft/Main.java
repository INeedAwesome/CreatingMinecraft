package site.gr8.mattis.creatingminecraft;

import org.lwjgl.glfw.GLFW;
import site.gr8.mattis.creatingminecraft.core.input.Input;
import site.gr8.mattis.creatingminecraft.core.util.Logger;
import site.gr8.mattis.creatingminecraft.renderEngine.Loader;
import site.gr8.mattis.creatingminecraft.renderEngine.RawModel;
import site.gr8.mattis.creatingminecraft.renderEngine.Renderer;
import site.gr8.mattis.creatingminecraft.settings.Settings;
import site.gr8.mattis.creatingminecraft.window.GLX;
import site.gr8.mattis.creatingminecraft.window.Window;

public class Main {

    private static Logger LOGGER;

    private static long lastTime = System.currentTimeMillis();
    private static int frames = 0;


    public static void main(String[] args) {
        LOGGER = new Logger();
        Settings settings = new Settings();
        GLX.initGLFW();
        Window window = new Window(settings);
        window.createWindow();

        Loader loader = new Loader();
        Renderer renderer = new Renderer();

        float[] vertices = {
                -0.5f, 0.5f, 0f,
                -0.5f, -0.5f, 0f,
                0.5f, -0.5f, 0f,
                0.5f, 0.5f, 0f,
        };
        int[] indices = {
                0, 1, 3,
                3, 1, 2
        };

        RawModel model = loader.loadToVAO(vertices, indices);

        LOGGER.info("Initializing game loop!");
        while (!GLFW.glfwWindowShouldClose(Window.getWindowID())) {
            GLX.prepare();

            renderer.render(model);

            if (Input.isKeyDown(GLFW.GLFW_KEY_F11)) {
                Window.toggleFullscreen(Window.getWindowID());
                LOGGER.info("F11");
            }

            calcFps();
            GLX.flipFrame();
        }
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
