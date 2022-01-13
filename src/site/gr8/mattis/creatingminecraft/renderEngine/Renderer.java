package site.gr8.mattis.creatingminecraft.renderEngine;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import site.gr8.mattis.creatingminecraft.core.shader.StaticShader;
import site.gr8.mattis.creatingminecraft.settings.AdditionalSettings;
import site.gr8.mattis.creatingminecraft.settings.Settings;

public class Renderer {

    private static final Settings settings = Settings.get();
    private static final AdditionalSettings additionalSettings = AdditionalSettings.get();

    public void render(RawModel model, StaticShader shader, Camera camera) {

        GL30.glBindVertexArray(model.getVaoID());
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);

        shader.loadTransformationMatrix(camera.calculateProjectionMatrix());
        shader.loadViewMatrix(camera.calculateViewMatrix());

        GL11.glDrawElements(GL11.GL_TRIANGLES, model.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);

        GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
        GL30.glBindVertexArray(0);
    }





}
