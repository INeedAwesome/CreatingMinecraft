package site.gr8.mattis.creatingminecraft.core.shader;

import org.joml.Matrix4f;

public class StaticShader extends ShaderProgram {

    private static final String VERTEX_FILE = "resources/shaders/vertexShader.txt";
    private static final String FRAGMENT_FILE = "resources/shaders/fragmentShader.txt";

    private int projectionMatrix;

    public StaticShader() {
        super(VERTEX_FILE, FRAGMENT_FILE);
    }

    @Override
    protected void getAllUniformLocations() {
        projectionMatrix = super.getUniformLocation("projectionMatrix");
    }

    @Override
    protected void bindAttributes() {
        super.bindAttribute(0, "position");
        super.bindAttribute(1, "color");
    }

    public void loadTransformationMatrix(Matrix4f matrix) {
        super.loadMatrix(projectionMatrix, matrix);
    }

}
