package site.gr8.mattis.creatingminecraft.core.shader;

import org.joml.Matrix4f;

public class StaticShader extends ShaderProgram {

    private static final String VERTEX_FILE = "resources/shaders/vertexShader.txt";
    private static final String FRAGMENT_FILE = "resources/shaders/fragmentShader.txt";

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

    public void loadTransformationMatrix(Matrix4f matrix) {
        super.loadMatrix(location_projectionMatrix, matrix);
    }

    public void loadViewMatrix(Matrix4f viewMatrix) {
        super.loadMatrix(location_viewMatrix, viewMatrix);
    }

    public void loadTexture(int slot) {
        super.loadTexture(location_texture, slot);
    }

}
