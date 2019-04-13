package example.demo07.modules.generic;

import example.demo07.engine.scene.ModuleNode;
import example.demo07.engine.scene.light.Light;
import example.demo07.engine.system.*;


public class LightOverlayShader extends Shader {

    private static LightOverlayShader instance;
    public static LightOverlayShader instance(){
        if(instance == null)
            instance = new LightOverlayShader();
        return instance;
    }

    private LightOverlayShader(){
        super();
        createVertexShader("demo07/shaders/overlay/overlay_vs.glsl");
        createFragmentShader("demo07/shaders/overlay/overlay_fs.glsl");
        link();

        addUniform("color");
        addUniform("projectionMatrix");
        addUniform("modelMatrix");
        addUniform("viewMatrix");
    }

    @Override
    public void updateUniforms(ModuleNode node){

        Light light = (Light) node;

        Camera camera = Core.camera();
        setUniform("projectionMatrix", camera.getProjectionMatrix());
        setUniform("modelMatrix", light.getModelMatrix());
        setUniform("viewMatrix", camera.getViewMatrix());
        setUniform("color", light.getColor());
    }
}

