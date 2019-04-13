package example.demo07.modules.post.ssao;


import example.demo07.engine.gldata.tex.TextureObject;
import example.demo07.engine.system.*;
import static org.lwjgl.opengl.GL11.GL_RGBA;
import static org.lwjgl.opengl.GL15.GL_READ_ONLY;
import static org.lwjgl.opengl.GL15.GL_WRITE_ONLY;
import static org.lwjgl.opengl.GL30.GL_RGBA32F;

public class SSAOBlurShader extends Shader {

    private TextureObject targetTexture;

    public SSAOBlurShader(){

        targetTexture = new TextureObject(
                Window.instance().getWidth(),
                Window.instance().getHeight())
                .allocateImage2D(GL_RGBA32F, GL_RGBA)
                .nofilter();

        createComputeShader("demo07/shaders/ssao/ssao_blur_comp.glsl");
        link();

        addUniform("resX");
        addUniform("resY");
    }

    public void compute(TextureObject ssao){

        bind();
        setUniform("resX", Window.instance().getWidth());
        setUniform("resY", Window.instance().getHeight());

        bindImage(0, ssao.getId(), GL_READ_ONLY, GL_RGBA32F);
        bindImage(1, targetTexture.getId(), GL_WRITE_ONLY, GL_RGBA32F);

        compute(16,16);
        unbind();
    }

    public TextureObject getTargetTexture() {
        return targetTexture;
    }
    
    
}

