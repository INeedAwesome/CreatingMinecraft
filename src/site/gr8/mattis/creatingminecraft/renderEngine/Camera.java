package site.gr8.mattis.creatingminecraft.renderEngine;

import org.joml.Math;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;
import site.gr8.mattis.creatingminecraft.core.input.Input;
import site.gr8.mattis.creatingminecraft.core.logger.Logger;
import site.gr8.mattis.creatingminecraft.settings.AdditionalSettings;
import site.gr8.mattis.creatingminecraft.settings.Settings;
import site.gr8.mattis.creatingminecraft.window.FrameBufferSizeCallback;

public class Camera {

    Logger LOGGER = Logger.get();
    Settings settings = Settings.get();
    AdditionalSettings ad = AdditionalSettings.get();

    private Vector3f position;
    private float pitch;
    private float yaw;
    private float roll;

    private final float FOV = Math.toRadians(Integer.parseInt(settings.getProperty("fov")));
    private final float Z_NEAR = Float.parseFloat(ad.getProperty("Z_NEAR"));
    private final float Z_FAR = Float.parseFloat(ad.getProperty("Z_FAR"));
    private final float sensitivity = Float.parseFloat(settings.getProperty("sensitivity"));
    float playerSpeed = 5f;

    public void move(float delta) {
        updateMouse();

        if (Input.isKeyDown(GLFW.GLFW_KEY_W))
            moveForwardOrBack(true, delta);
        if (Input.isKeyDown(GLFW.GLFW_KEY_D))
            moveRightOrLeft(false, delta);
        if (Input.isKeyDown(GLFW.GLFW_KEY_A))
            moveRightOrLeft(true, delta);
        if (Input.isKeyDown(GLFW.GLFW_KEY_S))
            moveForwardOrBack(false, delta);
        if (Input.isKeyDown(GLFW.GLFW_KEY_Q))
            position.y -= playerSpeed * delta;
        if (Input.isKeyDown(GLFW.GLFW_KEY_E))
            position.y += playerSpeed * delta;
    }

    public void updateMouse() {
        int dx = Input.getMouseDX();
        int dy = Input.getMouseDY();
        pitch += dy * sensitivity;
        yaw += dx * sensitivity;

        if (pitch > 89.0f)
            pitch = 89.0f;
        if (pitch < -89.0f)
            pitch = -89.0f;

        if (yaw > 360.0f)
            yaw = 0.0f;
        if (yaw < 0)
            yaw = 360.0f;
    }

    public void moveForwardOrBack(boolean forward, float delta) {
        float x = Math.sin(Math.toRadians(getYaw())) * playerSpeed * delta;
        float z = Math.cos(Math.toRadians(getYaw())) * playerSpeed * delta;
        if (forward) {
            position.z -= z;
            position.x += x;
        } else {
            position.z += z;
            position.x -= x;
        }
    }

    public void moveRightOrLeft(boolean right, float delta) {
        float z = Math.sin(Math.toRadians(getYaw())) * playerSpeed * delta;
        float x = Math.cos(Math.toRadians(getYaw())) * playerSpeed * delta;
        if (right) {
            position.z -= z;
            position.x -= x;
        } else {
            position.z += z;
            position.x += x;
        }
    }

    public Matrix4f calculateProjectionMatrix() {
        float aspectRatio = (float) FrameBufferSizeCallback.getCurrentWidth() / FrameBufferSizeCallback.getCurrentHeight();
        return new Matrix4f().perspective(FOV, aspectRatio, Z_NEAR, Z_FAR);
    }

    public Matrix4f calculateViewMatrix() {
        Matrix4f viewMatrix = new Matrix4f();
        viewMatrix.identity();
        viewMatrix.rotate(Math.toRadians(getPitch()), new Vector3f(1, 0, 0), viewMatrix);
        viewMatrix.rotate(Math.toRadians(getYaw()),   new Vector3f(0, 1, 0), viewMatrix);
        viewMatrix.rotate(Math.toRadians(getRoll()),  new Vector3f(0, 0, 1), viewMatrix);
        Vector3f cameraPos = getPosition();
        Vector3f negativeCameraPos = new Vector3f(-cameraPos.x, -cameraPos.y, -cameraPos.z);
        viewMatrix.translate(negativeCameraPos, viewMatrix);
        return viewMatrix;
    }

    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public float getPitch() {
        return pitch;
    }

    public float getYaw() {
        return yaw;
    }

    public float getRoll() {
        return roll;
    }
}
