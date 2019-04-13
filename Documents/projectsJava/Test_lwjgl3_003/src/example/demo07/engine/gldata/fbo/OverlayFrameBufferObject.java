package example.demo07.engine.gldata.fbo;

import example.demo07.engine.gldata.tex.TextureObject;
import example.demo07.engine.system.*;
import static org.lwjgl.opengl.GL11.GL_RGBA;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL30.GL_RGBA16F;

public class OverlayFrameBufferObject extends FrameBufferObject {

    TextureObject overlay, depth;

    public OverlayFrameBufferObject(){

        super();
        overlay = new TextureObject(GL_TEXTURE_2D,
                Window.instance().getWidth(), Window.instance().getHeight())
                .allocateImage2D(GL_RGBA16F, GL_RGBA)
                .bilinearFilter();

        depth = new TextureObject(
                GL_TEXTURE_2D, Window.instance().getWidth(), Window.instance().getHeight())
                .allocateDepth()
                .bilinearFilter();

        addAttatchments(overlay, depth);
        checkStatus();
    }

    public void resize(int x, int y){
        overlay.resize(x,y);
        depth.resize(x,y);
    }

    public TextureObject getDepth() {
        return depth;
    }

    public TextureObject getOverlay() {
        return overlay;
    }
    
    
}
