//FRAGMENT SHADER
#version 400 core

in vec3 colour;
in vec2 textureCoord;

out vec4 out_Colour;

uniform sampler2D texImage;

void main(void) {

    vec4 textureColor = texture2D(texImage, textureCoord);
    out_Colour = vec4(colour, 1) * textureColor;

}