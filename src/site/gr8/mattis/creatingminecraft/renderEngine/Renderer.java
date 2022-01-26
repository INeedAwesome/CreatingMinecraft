package site.gr8.mattis.creatingminecraft.renderEngine;

import org.joml.Vector3f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import site.gr8.mattis.creatingminecraft.core.shader.StaticShader;

import java.util.List;

public class Renderer {

    public void render(RawModel model, StaticShader shader, Camera camera, Texture texture, List<Vector3f> verts) {
        enable(model, texture);
        loadUniforms(shader, camera, texture);
        draw(model, verts);
        disable(texture);
    }

    private void draw(RawModel model, List<Vector3f> verts) {
        GL11.glDrawElements(GL11.GL_TRIANGLES, verts.size() * 6, GL11.GL_UNSIGNED_INT, 0);
    }

    private void loadUniforms(StaticShader shader, Camera camera, Texture texture) {
        shader.loadUniform("projectionMatrix", camera.calculateProjectionMatrix());
        shader.loadUniform("viewMatrix", camera.calculateViewMatrix());
        shader.loadUniform("texImage", texture.getTextureID());
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
