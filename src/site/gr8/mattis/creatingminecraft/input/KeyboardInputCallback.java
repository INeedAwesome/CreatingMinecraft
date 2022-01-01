package site.gr8.mattis.creatingminecraft.input;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWKeyCallback;


public class KeyboardInputCallback extends GLFWKeyCallback {

    public static boolean[] keys = new boolean[65536];

    @Override
    public void invoke(long window, int key, int scancode, int action, int mods) {
        keys[key] = action != GLFW.GLFW_RELEASE;
    }

    public static boolean isKeyDown(int keycode) {
        return keys[keycode];
    }
}
