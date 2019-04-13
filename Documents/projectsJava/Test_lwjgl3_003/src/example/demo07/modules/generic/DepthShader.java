package example.demo07.modules.generic;

import example.demo07.engine.scene.ModuleNode;
import example.demo07.engine.system.*;


public class DepthShader extends Shader {

    private static DepthShader instance;
    public static DepthShader instance(){
        if (instance == null)
            instance = new DepthShader();
        return instance;
    }

    private DepthShader(){
        createVertexShader("demo07/shaders/depth/depth_vs.glsl");
        createFragmentShader("demo07/shaders/depth/depth_fs.glsl");
        link();

        addUniform("modelMatrix");
        addUniform("viewMatrix");
        addUniform("projectionMatrix");
    }

    @Override
    public void updateUniforms(ModuleNode node) {
        Camera camera = Core.camera();

        setUniform("projectionMatrix", camera.getProjectionMatrix());
        setUniform("modelMatrix", node.getModelMatrix());
        setUniform("viewMatrix", camera.getViewMatrix());
    }
}

