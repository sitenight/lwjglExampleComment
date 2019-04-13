package example.demo07.engine.scene.light;

import example.demo07.engine.gldata.vbo.Meshs;
import example.demo07.engine.scene.*;
import example.demo07.modules.generic.*;

public class PointLight extends Light {

    public PointLight(PointLight light){
        this();
        setColor(light.getColor());
        setIntensity(light.getIntensity());
    }

    public PointLight(){
        super();

        RenderModule debugRenderer = new RenderModule(
                LightOverlayShader.instance(), Meshs.cube
        );

        RenderModule UUIDrenderer = new RenderModule(
                UUIDShader.instance(), Meshs.cube
        );
        addModule(RenderType.TYPE_OVERLAY, debugRenderer);
        addModule(RenderType.TYPE_UUID, UUIDrenderer);
        scale(.2f);
    }
}

