package site.gr8.mattis.creatingminecraft.renderEngine;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import site.gr8.mattis.creatingminecraft.core.shader.StaticShader;

public class Renderer {

    public void render(RawModel model, StaticShader shader, Camera camera, Texture texture) {

        enable(model, texture);
        loadUniforms(shader, camera, texture);
        draw(model);
        disable(texture);
    }

    private void draw(RawModel model) {
        GL11.glDrawElements(GL11.GL_TRIANGLES, model.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
    }

    private void loadUniforms(StaticShader shader, Camera camera, Texture texture) {
        shader.loadTransformationMatrix(camera.calculateProjectionMatrix());
        shader.loadViewMatrix(camera.calculateViewMatrix());
        shader.loadTexture(texture.getTextureID());
    }

    private void enable(RawModel model, Texture texture) {
        GL30.glBindVertexArray(model.getVaoID());
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);
        GL20.glEnableVertexAttribArray(2);
        texture.bind();
    }

    private void disable(Texture texture) {
        texture.unbind();
        GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
        GL20.glDisableVertexAttribArray(2);
        GL30.glBindVertexArray(0);
    }

}
