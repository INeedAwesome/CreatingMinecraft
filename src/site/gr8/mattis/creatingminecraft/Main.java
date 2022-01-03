package site.gr8.mattis.creatingminecraft;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;
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

    private static final Logger LOGGER = Logger.get();
    private static final Settings settings = Settings.get();
    private static final AdditionalSettings additionalSettings = AdditionalSettings.get();

    public static void main(String[] args) {
        settings.init();
        GLX.initGLFW();
        Window window = Window.get();
        window.createWindow();
        additionalSettings.init();

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
                    Window.toggleFullscreen(Window.getWindowID());
                if (Input.isKeyPressed(GLFW.GLFW_KEY_F1))
                    if (!wireFrame) {
                        wireFrame = true;
                        GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_LINE);
                    } else {
                        wireFrame = false;
                        GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_FILL);
                    }
            }

            if (canRender) { // rendering stuff
                GLX.prepare();
                shader.start();
                renderer.render(model);
                shader.stop();
                GLX.flipFrame();
            }
        }

        shader.cleanUp();
        loader.cleanUp();
        Window.closeDisplay();
    }
}