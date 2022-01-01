package site.gr8.mattis.creatingminecraft.core.shader;

import org.lwjgl.opengl.GL20;

public class Shader {

    private int vertexShader;
    private int fragmentShader;

    static CharSequence vertexShaderSource = "";

    public void create() {
        vertexShader = GL20.glCreateShader(GL20.GL_VERTEX_SHADER);
        GL20.glShaderSource(vertexShader, vertexShaderSource);
    }
}
