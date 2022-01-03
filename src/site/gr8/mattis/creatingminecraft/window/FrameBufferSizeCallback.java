package site.gr8.mattis.creatingminecraft.window;

import org.lwjgl.glfw.GLFWFramebufferSizeCallback;
import org.lwjgl.opengl.GL11;

public class FrameBufferSizeCallback extends GLFWFramebufferSizeCallback {

    private static int CURRENT_WIDTH;
    private static int CURRENT_HEIGHT;

    @Override
    public void invoke(long window, int width, int height) {
        FrameBufferSizeCallback.CURRENT_WIDTH = width;
        FrameBufferSizeCallback.CURRENT_HEIGHT = height;
        GL11.glViewport(0, 0, width, height);
    }

    public static int getCurrentWidth() {
        if (CURRENT_WIDTH == 0)
            return Window.isFullscreen() ? Window.MAX_WIDTH : Window.WIDTH;
        return CURRENT_WIDTH;
    }

    public static int getCurrentHeight() {
        if (CURRENT_HEIGHT == 0)
            return Window.isFullscreen() ? Window.MAX_HEIGHT : Window.HEIGHT;
        return CURRENT_HEIGHT;
    }
}
