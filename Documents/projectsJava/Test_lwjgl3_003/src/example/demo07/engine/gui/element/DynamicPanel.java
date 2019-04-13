package example.demo07.engine.gui.element;

import example.demo07.engine.gldata.tex.TextureObject;
import example.demo07.engine.gldata.vbo.Meshs;
import example.demo07.engine.gui.Box;
import example.demo07.engine.system.Shader;
import java.util.ArrayList;

import static org.lwjgl.opengl.GL11.*;

public class DynamicPanel extends Element {

    private TextureObject screenTexture;
    private DynamicPanelShader fsShader;

    public DynamicPanel() {
        super();
        fsShader = new DynamicPanelShader();
        relativeBox = new Box(0,0,1,1);
        absoluteBox = new Box(0,0,1,1);
    }

    @Override
    public void render(){

        glPolygonMode(GL_FRONT, GL_FILL);
        fsShader.bind();
        fsShader.updateUniforms(this);
        Meshs.posquad.render();
        fsShader.unbind();

    }

    @Override
    public void handle(){
        super.handle();
    }

    @Override
    public void cleanup(){
        super.cleanup();
        fsShader.cleanup();
    }

    private class DynamicPanelShader extends Shader {

        public DynamicPanelShader(){
            super();

            createVertexShader("demo07/shaders/gui/panel_vs.glsl");
            createFragmentShader("demo07/shaders/dynamicpanel/dynamicpanel_fs.glsl");
            link();

            addUniform("texture");
            addUniform("box.x");
            addUniform("box.y");
            addUniform("box.width");
            addUniform("box.height");

            addUniform("isDepth");
        }

        public void updateUniforms(DynamicPanel e){

            activeTexture(e.screenTexture, 0);
            setUniform("texture", 0);

            Box box = e.getAbsoluteBox();

            setUniform("box.x", box.getX());
            setUniform("box.y", box.getY());
            setUniform("box.width", box.getWidth());
            setUniform("box.height", box.getHeight());

            setUniform("isDepth", e.getScreenTexture().isDepth() ? 1 : 0);

        }
    }

    public TextureObject getScreenTexture() {
        return screenTexture;
    }

    public void setScreenTexture(TextureObject screenTexture) {
        this.screenTexture = screenTexture;
    }

    public void setChanged(boolean changed) {
        this.changed = changed;
    }
    
    

}

