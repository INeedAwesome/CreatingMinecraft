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

    private static final Logger LOGGER = Logger.get();

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

    private static final float PI = 3.141592653589f;

    public void updateMouse() {
        int dx = Input.getMouseDX();
        int dy = Input.getMouseDY();
        pitch += dy * sensitivity;
        yaw += dx * sensitivity;

        if (pitch > 89.0f)
            pitch =  89.0f;
        if (pitch < -89.0f)
            pitch = -89.0f;

        if (yaw > 360.0f)
            yaw = 0.0f;
        if (yaw < 0)
            yaw = 360.0f;

        LOGGER.info(pitch + "   " + yaw);
    }

    public void move() {
        updateMouse();
        float playerSpeed = 0.05f;
        if (Input.isKeyDown(GLFW.GLFW_KEY_W))
            position.z -= playerSpeed;
        if (Input.isKeyDown(GLFW.GLFW_KEY_D))
            position.x += playerSpeed;
        if (Input.isKeyDown(GLFW.GLFW_KEY_A))
            position.x -= playerSpeed;
        if (Input.isKeyDown(GLFW.GLFW_KEY_S))
            position.z += playerSpeed;
        if (Input.isKeyDown(GLFW.GLFW_KEY_Q))
            position.y -= playerSpeed;
        if (Input.isKeyDown(GLFW.GLFW_KEY_E))
            position.y += playerSpeed;
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
