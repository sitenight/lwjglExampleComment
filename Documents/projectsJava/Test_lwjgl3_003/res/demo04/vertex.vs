#version 430

layout (location=0) in vec3 position; //индекс в атрибутах Mesh, позиция
layout (location=1) in vec3 inColour; //индекс в атрибутах Mesh, цвета

out vec3 exColour;

uniform mat4 projectionMatrix;

void main() {
    gl_Position = projectionMatrix * vec4(position, 1.0);
    exColour = inColour;
}