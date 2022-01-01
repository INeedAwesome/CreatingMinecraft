package site.gr8.mattis.creatingminecraft.window;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;
import site.gr8.mattis.creatingminecraft.core.util.Logger;

public class GLX {

    private static Logger LOGGER = new Logger();

    public static void flipFrame() {
        GLFW.glfwPollEvents();
        GLFW.glfwSwapBuffers(Window.getWindowID());
    }

    public static void prepare() {
        GL11.glClearColor(255.0f / 250.0f, 119.0f / 250.0f, 100.0f / 255.0f, 1f);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
    }

    public static void initGLFW() {
        LOGGER.info("Initializing GLFW!");
        if (!GLFW.glfwInit()) {
            LOGGER.error("GLFW error, GLFW version: " + GLFW.glfwGetVersionString());
            throw new IllegalStateException("GLFW error during init");
        }
    }

}
