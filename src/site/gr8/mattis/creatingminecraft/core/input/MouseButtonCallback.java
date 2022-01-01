package site.gr8.mattis.creatingminecraft.core.input;

import org.lwjgl.glfw.GLFWMouseButtonCallback;

import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

public class MouseButtonCallback extends GLFWMouseButtonCallback {

    public static boolean[] buttons = new boolean[16];

    @Override
    public void invoke(long window, int button, int action, int mods) {
        buttons[button] = action != GLFW_RELEASE;
    }

    public static boolean isButtonDown(int keycode) {
        return buttons[keycode];
    }
}
