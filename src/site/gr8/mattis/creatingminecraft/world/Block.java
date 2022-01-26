package site.gr8.mattis.creatingminecraft.world;

public class Block {

    int ID;
    int state;
    boolean emitLight;

    public float[] vertices = {
            -0.5f, 0.5f, 0.5f,// VO
            -0.5f, -0.5f, 0.5f,// V1
            0.5f, -0.5f, 0.5f,// V2
            0.5f, 0.5f, 0.5f,// V3
            -0.5f, 0.5f, -0.5f,// V4
            0.5f, 0.5f, -0.5f,// V5
            -0.5f, -0.5f, -0.5f,// V6
            0.5f, -0.5f, -0.5f,// V7
    };

    public int[] indices = {
            0, 1, 3, 3, 1, 2,// Front face
            4, 0, 3, 5, 4, 3,// Top Face
            3, 2, 7, 5, 3, 7, // Right face
            6, 1, 0, 6, 0, 4,// Left face
            2, 1, 6, 2, 6, 7,// Bottom face
            7, 6, 4, 7, 4, 5,// Back face
    };

    public float[] colours = { // rgb values
            1.0f, 1.0f, 1.0f,
            1.0f, 1.0f, 1.0f,
            1.0f, 1.0f, 1.0f,
            1.0f, 1.0f, 1.0f,

            1.0f, 1.0f, 1.0f,
            1.0f, 1.0f, 1.0f,
            1.0f, 1.0f, 1.0f,
            1.0f, 1.0f, 1.0f,
    };

    public float[] uvs = {
            1, 0,
            1, 1,
            0, 1,
            0, 0,

            0, 0,
            1, 0,
            0, 1,
            1, 1,

            1, 0,
            1, 1,
            0, 1,
            1, 1,

            1, 0,
            0, 1,
            1, 1,
            0, 0,

            0, 0,
            1, 0,
            1, 0,
            0, 1,

            1, 1,
            1, 1,
            0, 0,
            0, 1,
    };

    public void generateBlockTop(int offset) {

    }


}
