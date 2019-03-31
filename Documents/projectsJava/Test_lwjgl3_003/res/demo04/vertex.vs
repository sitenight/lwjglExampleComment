#version 430

layout (location=0) in vec3 position; //индекс в атрибутах Mesh, позиция
layout (location=1) in vec2 texCoord; //индекс в атрибутах Mesh, координаты текстуры
layout (location=2) in vec3 vertexNormal;

out vec2 outTexCoord;

uniform mat4 modelViewMatrix;
uniform mat4 projectionMatrix;

void main() {
    gl_Position = projectionMatrix * modelViewMatrix * vec4(position, 1.0);
    outTexCoord = texCoord;
}