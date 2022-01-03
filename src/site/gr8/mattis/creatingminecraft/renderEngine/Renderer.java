package site.gr8.mattis.creatingminecraft.renderEngine;

import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import site.gr8.mattis.creatingminecraft.core.shader.StaticShader;
import site.gr8.mattis.creatingminecraft.settings.AdditionalSettings;
import site.gr8.mattis.creatingminecraft.settings.Settings;
import site.gr8.mattis.creatingminecraft.window.FrameBufferSizeCallback;

public class Renderer {

    private static Settings settings = Settings.get();
    private static AdditionalSettings additionalSettings = AdditionalSettings.get();

    private static final float FOV = (float) Math.toRadians(Integer.parseInt(settings.getProperty("FOV")));
    private static final float Z_NEAR = Float.parseFloat(additionalSettings.getProperty("Z_NEAR"));
    private static final float Z_FAR = Float.parseFloat(additionalSettings.getProperty("Z_FAR"));

    private Matrix4f projectionMatrix;

    public void render(RawModel model, StaticShader shader) {

        float aspectRatio = (float) FrameBufferSizeCallback.getCurrentWidth() / FrameBufferSizeCallback.getCurrentHeight();
        projectionMatrix = new Matrix4f().perspective(FOV, aspectRatio, Z_NEAR, Z_FAR);

        GL30.glBindVertexArray(model.getVaoID());
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);
        shader.loadTransformationMatrix(projectionMatrix);
        GL11.glDrawElements(GL11.GL_TRIANGLES, model.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
        GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
        GL30.glBindVertexArray(0);
    }


}
