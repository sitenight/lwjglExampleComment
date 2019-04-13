package example.demo07.engine.gui.element;

import example.demo07.engine.gldata.vbo.Meshs;
import example.demo07.engine.gui.*;
import example.demo07.engine.system.Shader;
import org.joml.Vector3f;
import static org.lwjgl.opengl.GL11.GL_FILL;
import static org.lwjgl.opengl.GL11.GL_FRONT;
import static org.lwjgl.opengl.GL11.glPolygonMode;

public class Panel extends Element {

    private static PanelShader instance;
    private static PanelShader instance(){
        if (instance == null)
            instance = new PanelShader();
        return instance;
    }

    private Vector3f color;

    public Panel() {
        super();
        relativeBox = new Box(0,0,1,1);
        absoluteBox = new Box(0,0,1,1);
        color = new Vector3f(0,1,0);
    }

    @Override
    public void render(){

        glPolygonMode(GL_FRONT, GL_FILL);
        instance().bind();
        instance().updateUniforms(this);
        Meshs.posquad.render();
        instance().unbind();

        super.render();

    }

    @Override
    public void handle(){
        super.handle();
    }

    @Override
    public void cleanup(){
        super.cleanup();
    }

    private static class PanelShader extends Shader {

        public PanelShader(){
            super();

            createVertexShader("demo07/shaders/gui/panel_vs.glsl");
            createFragmentShader("demo07/shaders/gui/generic_color_fs.glsl");
            link();

            addUniform("box.x");
            addUniform("box.y");
            addUniform("box.width");
            addUniform("box.height");

            addUniform("color");
        }

        public void updateUniforms(Panel e){

            Box box = e.getAbsoluteBox();

            setUniform("box.x", box.getX());
            setUniform("box.y", box.getY());
            setUniform("box.width", box.getWidth());
            setUniform("box.height", box.getHeight());

            setUniform("color", e.getColor());

        }
    }

    public Vector3f getColor() {
        return color;
    }

    public void setColor(Vector3f color) {
        this.color = color;
    }
    
    
}

