package site.gr8.mattis.creatingminecraft.window;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import site.gr8.mattis.creatingminecraft.core.input.KeyboardInputCallback;
import site.gr8.mattis.creatingminecraft.core.input.MouseButtonCallback;
import site.gr8.mattis.creatingminecraft.core.input.MousePositionCallback;
import site.gr8.mattis.creatingminecraft.core.input.MouseScrollCallback;
import site.gr8.mattis.creatingminecraft.core.logger.Logger;
import site.gr8.mattis.creatingminecraft.settings.Settings;


public class Window {

    private static final Logger LOGGER = Logger.get();
    private static final Settings settings = Settings.get();

    private static long windowID;
    public static int WIDTH = 1280;
    public static int HEIGHT = 720;
    public static int MAX_WIDTH = 1920;
    public static int MAX_HEIGHT = 1080;
    public static String TITLE = "Shit knas v" + settings.getProperty("app.version");

    private static boolean fullscreen = false;
    private int initialisations;

    private static Window window = null;

    public Window() {
    }

    public static Window get() {
        if (Window.window == null) {
            Window.window = new Window();
        }
        return Window.window;
    }

    public static long getWindowID() {
        return windowID;
    }

    public void createWindow() {
        initialisations++;
        if (initialisations >= 2) {
            LOGGER.warn("Tried to initialize window twice(or more)! ");
            return;
        }

        LOGGER.info("Creating window!");
        setWindowHints();
        boolean isFullscreen = Boolean.parseBoolean(settings.getProperty("fullscreen"));
        fullscreen = isFullscreen;

        windowID = GLFW.glfwCreateWindow(
                isFullscreen ? MAX_WIDTH : WIDTH,
                isFullscreen ? MAX_HEIGHT : HEIGHT,
                TITLE,
                isFullscreen ? GLFW.glfwGetPrimaryMonitor() : 0,
                0);

        if (!nullCheck())
            return;
        centerWindow();
        GLFW.glfwMakeContextCurrent(windowID);
        GL.createCapabilities();
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glEnable(GL11.GL_BLEND);
        initializeCallbacks(windowID);
        GLFW.glfwShowWindow(windowID);

        if (Boolean.parseBoolean(settings.getProperty("vsync")))
            GLFW.glfwSwapInterval(1);
        else
            GLFW.glfwSwapInterval(0);

    }

    private static void centerWindow() {
        GLFWVidMode vidMode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
        assert vidMode != null;
        GLFW.glfwSetWindowPos(windowID, (vidMode.width() - WIDTH) / 2, (vidMode.height() - HEIGHT) / 2);
    }

    public static void closeDisplay() {
        GLFW.glfwTerminate();
        LOGGER.info("Terminating!");
    }

    private static void setWindowHints() {
        GLFW.glfwDefaultWindowHints();
        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MAJOR, 3);
        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MINOR, 2);
        GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_PROFILE, GLFW.GLFW_OPENGL_CORE_PROFILE);
        GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_FORWARD_COMPAT, GL11.GL_TRUE);
    }

    private static void initializeCallbacks(long windowID) {
        GLFW.glfwSetKeyCallback(windowID, new KeyboardInputCallback());
        GLFW.glfwSetCursorPosCallback(windowID, new MousePositionCallback());
        GLFW.glfwSetMouseButtonCallback(windowID, new MouseButtonCallback());
        GLFW.glfwSetScrollCallback(windowID, new MouseScrollCallback());
        GLFW.glfwSetFramebufferSizeCallback(windowID, new FrameBufferSizeCallback());
    }

    private static boolean nullCheck() {
        if (windowID == 0) {
            LOGGER.error("Failed to create window.");
            GLFW.glfwTerminate();
            return false;
        }
        return true;
    }

    public static void toggleFullscreen() {
        if (fullscreen) {
            GLFW.glfwSetWindowMonitor(windowID, 0, 1, 30, 1280, 720, 60);
            centerWindow();
            fullscreen = false;
        }
        else {
            GLFW.glfwSetWindowMonitor(windowID, GLFW.glfwGetPrimaryMonitor(), 0, 0, MAX_WIDTH, MAX_HEIGHT, 165);
            GLFW.glfwSwapInterval(1);
            fullscreen = true;

        }
    }

    public static boolean isFullscreen() {
        return fullscreen;
    }
}
