package site.gr8.mattis.creatingminecraft.core.input;

import org.lwjgl.glfw.GLFWCursorPosCallback;

public class MousePositionCallback extends GLFWCursorPosCallback {

    private static int mouseX;
    private static int mouseY;
    private static int mouseDX;
    private static int mouseDY;

    @Override
    public void invoke(long window, double xpos, double ypos) {
        mouseDX += (int) xpos - mouseX;
        mouseDY += (int) ypos - mouseY;
        mouseX  =  (int) xpos;
        mouseY  =  (int) ypos;

    }

    public static int getMouseDX() {
        return mouseDX | (mouseDX = 0);
    }

    public static int getMouseDY() {
        return mouseDY | (mouseDY = 0);
    }

    public static int getRawX() {
        return mouseX;
    }

    public static int getRawY() {
        return mouseY;
    }
}
