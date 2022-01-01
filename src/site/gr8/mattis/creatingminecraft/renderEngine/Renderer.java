package site.gr8.mattis.creatingminecraft.renderEngine;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import site.gr8.mattis.creatingminecraft.window.GLX;

public class Renderer {

    public void prepare() {
        GLX.prepare();
    }

    public void render(RawModel model) {
        GL30.glBindVertexArray(model.getVaoID());
        GL20.glEnableVertexAttribArray(0);
        GL11.glDrawElements(GL11.GL_LINES, model.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
        GL20.glDisableVertexAttribArray(0);
        GL30.glBindVertexArray(0);
    }


}
