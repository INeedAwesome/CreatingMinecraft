package site.gr8.mattis.creatingminecraft.core.input;

public class Input {

    public static boolean isKeyDown(int keycode) {
        return KeyboardInputCallback.isKeyDown(keycode);
    } // key held

    public static double isScrolling() {
        return MouseScrollCallback.getYoffset();
    }

    public static boolean isMBDown(int keycode) {
        return MouseButtonCallback.isButtonDown(keycode);
    }

    public static int getMouseDX() {
        return MousePositionCallback.getMouseDX();
    }

    public static int getMouseDY() {
        return MousePositionCallback.getMouseDY();
    }

    public static int getMouseDXRaw() {
        return MousePositionCallback.getRawX();
    }

    public static int getMouseDYRaw() {
        return MousePositionCallback.getRawY();
    }

}
