package example.demo04.engine.gui;

import example.demo04.engine.model.Texture;

public class Font {

    public Texture texture;

    public Font(String path) throws Exception {
        texture = new Texture(path);
    }
    
    
}
