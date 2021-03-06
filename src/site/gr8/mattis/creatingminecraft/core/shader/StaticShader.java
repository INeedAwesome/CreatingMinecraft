package site.gr8.mattis.creatingminecraft.core.shader;

import org.joml.Matrix4f;
import org.lwjgl.opengl.GL20;

public class StaticShader extends ShaderProgram {

    private static final String VERTEX_FILE = "resources/shaders/vertexShader.glsl";
    private static final String FRAGMENT_FILE = "resources/shaders/fragmentShader.glsl";

    private int location_projectionMatrix;
    private int location_viewMatrix;
    private int location_texture;

    public StaticShader() {
        super(VERTEX_FILE, FRAGMENT_FILE);
    }

    @Override
    protected void getAllUniformLocations() {
        location_projectionMatrix = super.getUniformLocation("projectionMatrix");
        location_viewMatrix = super.getUniformLocation("viewMatrix");
        location_texture = super.getUniformLocation("texImage");
    }

    @Override
    protected void bindAttributes() {
        super.bindAttribute(0, "position");
        super.bindAttribute(1, "color");
        super.bindAttribute(2, "texcoord");
    }

    public void loadUniform(String location, Matrix4f matrix) {
        int uloc = super.getUniformLocation(location);
        loadMatrix(uloc, matrix);
    }

    public void loadUniform(String location, int val) {
        int uloc = super.getUniformLocation(location);
        GL20.glUniform1i(uloc, val);
    }

    public void loadUniform(String location, boolean val) {
        int uloc = super.getUniformLocation(location);
        GL20.glUniform1i(uloc, val ? 1 : 0);
    }

    public void loadUniform(String location, float val) {
        int uloc = super.getUniformLocation(location);
        GL20.glUniform1f(uloc, val);
    }

}
