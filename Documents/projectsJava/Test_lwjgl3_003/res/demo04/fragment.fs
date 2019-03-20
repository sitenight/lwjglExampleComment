#version 430

in vec3 exColour;

out vec4 fragColor;

void main() {
    //fragColor = vec4(exColour, 1.0);
    fragColor = vec4(0.5, 0.5, 0.0, 1.0);
}