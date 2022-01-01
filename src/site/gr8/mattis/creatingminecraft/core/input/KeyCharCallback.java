package site.gr8.mattis.creatingminecraft.core.input;

import org.lwjgl.glfw.GLFWCharCallback;

public class KeyCharCallback extends GLFWCharCallback {


    private static int keyChar;

    @Override
    public void invoke(long window, int codepoint) {
        keyChar = codepoint;
    }

    public static int getKeyChar() {
        return keyChar;
    }
}
