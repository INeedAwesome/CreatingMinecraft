package site.gr8.mattis.creatingminecraft.renderEngine;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import site.gr8.mattis.creatingminecraft.core.shader.StaticShader;
import site.gr8.mattis.creatingminecraft.world.Block;

public class Renderer {

    float i = 0.00f;

    public void render(RawModel model, StaticShader shader, Camera camera, Texture texture) {
        enable(model, texture);
        loadUniforms(shader, camera, texture);
        draw(model);
        disable(texture);
    }

    private void draw(RawModel model) {


        GL15.glBufferSubData(GL15.GL_ARRAY_BUFFER, 0, Block.returnNewVertices(0, 0, 0));
        GL11.glDrawElements(GL11.GL_TRIANGLES, model.getVertexCount() * 1000, GL11.GL_UNSIGNED_INT, 0);


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
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 2);
        texture.bind();
    }

    private void disable(Texture texture) {
        texture.unbind();
        GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
        GL20.glDisableVertexAttribArray(2);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
        GL30.glBindVertexArray(0);
    }
}
