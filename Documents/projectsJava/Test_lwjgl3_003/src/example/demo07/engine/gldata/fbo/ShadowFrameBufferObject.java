package example.demo07.engine.gldata.fbo;

import example.demo07.engine.gldata.tex.TextureObject;
import example.demo07.engine.system.Config;
import java.nio.IntBuffer;
import org.lwjgl.BufferUtils;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.GL_COLOR_ATTACHMENT0;

public class ShadowFrameBufferObject extends FrameBufferObject {

    private TextureObject depth;

    public ShadowFrameBufferObject(){
        super();
        depth = new TextureObject(
                GL_TEXTURE_2D, Config.instance().getShadowBufferWidth(),
                Config.instance().getShadowBufferHeight())
                .allocateDepth()
                .wrap()
                .nofilter();

        IntBuffer drawBuffers = BufferUtils.createIntBuffer(1);
        drawBuffers.put(GL_COLOR_ATTACHMENT0);
        drawBuffers.flip();

        bind();
        createDepthTextureAttatchment(depth.getId());
        glDrawBuffer(GL_NONE);
        glReadBuffer(GL_NONE);
        setDrawBuffer(drawBuffers);
        checkStatus();
        unbind();
    }

    @Override
    public void resize(int x, int y){ }

    public TextureObject getDepth() {
        return depth;
    }
    
    
}

