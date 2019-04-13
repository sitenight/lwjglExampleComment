package example.demo07.modules.generic;

import example.demo07.engine.scene.ModuleNode;
import example.demo07.engine.system.*;
import org.joml.Vector3f;


public class PlainColorShader extends Shader {

    private static Vector3f color = new Vector3f(1,1,1);

    private static PlainColorShader instance;
    public static PlainColorShader instance(){
        if (instance == null)
            instance = new PlainColorShader();
        return instance;
    }

    public static void setColor(Vector3f _color){
         color = _color;
    }

    public static void setColor(float r, float g, float b){
        setColor(new Vector3f(r, g, b));
    }

    private PlainColorShader(){
        super();
        createVertexShader("demo07/shaders/wireframe/wire_vs.glsl");
        createFragmentShader("demo07/shaders/gui/generic_color_fs.glsl");
        link();

        addUniform("modelMatrix");
        addUniform("viewMatrix");
        addUniform("projectionMatrix");

        addUniform("color");
    }

    @Override
    public void updateUniforms(ModuleNode node) {
        Camera camera = Core.camera();

        setUniform("projectionMatrix", camera.getProjectionMatrix());
        setUniform("modelMatrix", node.getModelMatrix());
        setUniform("viewMatrix", camera.getViewMatrix());
        setUniform("color", color);
    }
}

