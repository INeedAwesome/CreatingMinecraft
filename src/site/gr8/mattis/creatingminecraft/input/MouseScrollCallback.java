package site.gr8.mattis.creatingminecraft.input;

import org.lwjgl.glfw.GLFWScrollCallback;

public class MouseScrollCallback extends GLFWScrollCallback {

    private static double yoffset;

    @Override
    public void invoke(long window, double xoffset, double yoffset) {
        MouseScrollCallback.yoffset = yoffset;
        // the x offset will always be zero.
    }

    public static double getYoffset() {
        return yoffset;
    }
}
