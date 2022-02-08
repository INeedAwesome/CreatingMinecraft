package site.gr8.mattis.creatingminecraft.world;

public class Block {

    int ID;
    int state;
    boolean emitLight;
    private float bottomShade = 0.6f;

    public int[] indices = {
            0, 1, 3, 3, 1, 2,// Front face
            4, 0, 3, 5, 4, 3,// Top Face
            3, 2, 7, 5, 3, 7,// Right face
            6, 1, 0, 6, 0, 4,// Left face
            2, 1, 6, 2, 6, 7,// Bottom face
            7, 6, 4, 7, 4, 5,// Back face
    };


    public float[] colours = { // rgb values
            1.0f, 1.0f, 1.0f,
            bottomShade, bottomShade, bottomShade,
            bottomShade, bottomShade, bottomShade,
            1.0f, 1.0f, 1.0f,

            1.0f, 1.0f, 1.0f,
            1.0f, 1.0f, 1.0f,
            bottomShade, bottomShade, bottomShade,
            bottomShade, bottomShade, bottomShade
    };

    public float[] uvs = {
            0.5f, 0,
            0.5f, 0.5f,
            0, 0.5f,
            0, 0,

            0, 0,
            0.5f, 0,
            0, 0.5f,
            0.5f, 0.5f,
//
//            1, 1,
//            1, 0,
//            0, 1,
//            1, 1,
//
//            1, 0,
//            0, 0.5f,
//            1, 0.5f,
//            0, 0.5f,
//
//            1, 1,
//            1f, 0.5f,
//            0.5f, 1f,
//            0.5f, 0.5f,
//
//            1, 1,
//            0.5f, 1,
//            0.5f, 1,
//            0.5f, 0.5f,
    };

//    public static float[] vertices = {
//            -0.5f,  0.5f,  0.5f,   // VO   // forward top left
//            -0.5f, -0.5f,  0.5f,  // V1   // forward bottom left
//             0.5f, -0.5f,  0.5f,   // V2   // forward bottom right
//             0.5f,  0.5f,  0.5f,    // V3   // forward top right
//            -0.5f,  0.5f, -0.5f,  // V4   // back top left
//             0.5f,  0.5f, -0.5f,   // V5   // back top right
//            -0.5f, -0.5f, -0.5f, // V6   // back bottom left
//             0.5f, -0.5f, -0.5f,  // V7   // back bottom right
//    };

//    public static float[] getVertices() {
//        return vertices;
//    }

    public static float[] returnNewVertices(float x, float y, float z) {
        return new float[] {
                x - 0.5f, y + 0.5f, z + 0.5f,// VO   // forward top left
                x - 0.5f, y - 0.5f, z + 0.5f,// V1   // forward bottom left
                x + 0.5f, y - 0.5f, z + 0.5f,// V2   // forward bottom right
                x + 0.5f, y + 0.5f, z + 0.5f,// V3   // forward top right
                x - 0.5f, y + 0.5f, z - 0.5f,// V4   // back top left
                x + 0.5f, y + 0.5f, z - 0.5f,// V5   // back top right
                x - 0.5f, y - 0.5f, z - 0.5f,// V6   // back bottom left
                x + 0.5f, y - 0.5f, z - 0.5f,// V7   // back bottom right
        };
    }
}
