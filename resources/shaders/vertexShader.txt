//VERTEX SHADER
#version 400 core

in vec3 position;
in vec3 color;
in vec2 texcoord;

out vec3 colour;
out vec2 textureCoord;

uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;

void main(void) {

    colour = color;
    textureCoord = texcoord;
    gl_Position = projectionMatrix * viewMatrix * vec4(position, 1);

}