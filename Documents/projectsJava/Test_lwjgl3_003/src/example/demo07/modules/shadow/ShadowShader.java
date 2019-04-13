package example.demo07.modules.shadow;

import example.demo07.engine.scene.ModuleNode;
import example.demo07.engine.scene.light.LightManager;
import example.demo07.engine.system.Shader;

public class ShadowShader extends Shader {

    private static ShadowShader instance;
    public static ShadowShader instance(){
        if(instance == null)
            instance = new ShadowShader();
        return instance;
    }

    private ShadowShader(){
        createVertexShader("demo07/shaders/shadow/shadow_vs.glsl");
        createFragmentShader("demo07/shaders/shadow/shadow_fs.glsl");
        link();

        addUniform("lightSpaceMatrix");
        addUniform("modelMatrix");
    }

    @Override
    public void updateUniforms(ModuleNode parent) {
        setUniform("lightSpaceMatrix", LightManager.getSun().getLightSpaceMatrix());
        setUniform("modelMatrix", parent.getModelMatrix());
    }
}

