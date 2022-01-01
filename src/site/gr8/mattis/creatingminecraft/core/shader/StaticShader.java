package site.gr8.mattis.creatingminecraft.core.shader;

public class StaticShader extends ShaderProgram {

    private static final String VERTEX_FILE = "resources/shaders/vertexShader.txt";
    private static final String FRAGMENT_FILE = "resources/shaders/fragmentShader.txt";

    public StaticShader() {
        super(VERTEX_FILE, FRAGMENT_FILE);
    }

    @Override
    protected void bindAttributes() {
        super.bindAttribute(0, "position");
    }
}
