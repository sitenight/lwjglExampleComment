package example.demo07.modules.generic;

import example.demo07.engine.event.Picking;
import example.demo07.engine.scene.ModuleNode;
import example.demo07.engine.system.*;

public class UUIDShader extends Shader {

    private static UUIDShader instance;
    public static UUIDShader instance(){
        if (instance == null)
            instance = new UUIDShader();
        return instance;
    }

    private UUIDShader(){
        createVertexShader("demo07/shaders/picking/UUID_vs.glsl");
        createFragmentShader("demo07/shaders/picking/UUID_fs.glsl");
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
        setUniform("color", Picking.getUUIDColor(node.getUUID()));
    }
}

