#version 430

in vec2 outTexCoord;

out vec4 fragColor;

uniform sampler2D texture_sampler;
uniform vec3 colour; // основной цвет
uniform int useColour; // флаг использования текстуры

void main() {
    if(useColour == 1) { 
        // если нет текстуры, то выводим цвет
        fragColor = vec4(colour, 1);
    } else { 
        // если есть текстура, то её отображаем
        fragColor = texture(texture_sampler, outTexCoord);
    }
    
}