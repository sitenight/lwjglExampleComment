package example.demo07.modules.pbr;


import example.demo07.engine.gldata.vbo.Mesh3D;
import example.demo07.engine.scene.ModuleNode;
import example.demo07.engine.scene.RenderModule;
import example.demo07.engine.scene.RenderType;
import example.demo07.modules.generic.DepthShader;
import example.demo07.modules.generic.PlainColorShader;
import example.demo07.modules.generic.UUIDShader;
import example.demo07.modules.shadow.ShadowShader;
import java.util.ArrayList;

public class PBRModel extends ModuleNode {

    Mesh3D mesh;
    PBRMaterial material;

    public PBRModel(Mesh3D mesh, PBRMaterial material){

        super();

        this.mesh = mesh;
        this.material = material;

        RenderModule scenerenderer = new RenderModule(
                PBRShader.instance(), mesh
        );

        RenderModule shadowrenderer = new RenderModule(
                ShadowShader.instance(), mesh
        );

        RenderModule wireframerenderer = new RenderModule(
                PlainColorShader.instance(), mesh
        );

        RenderModule UUIDrenderer = new RenderModule(
                UUIDShader.instance(), mesh
        );

        RenderModule depthrenderer = new RenderModule(
                DepthShader.instance(), mesh
        );

        addModule(RenderType.TYPE_SCENE, scenerenderer);
        addModule(RenderType.TYPE_SHADOW, shadowrenderer);
        addModule(RenderType.TYPE_WIREFRAME, wireframerenderer);
        addModule(RenderType.TYPE_UUID, UUIDrenderer);
        addModule(RenderType.TYPE_DEPTH, depthrenderer);

    }

    public PBRModel(ArrayList<Mesh3D> meshs, PBRMaterial material){
        super();
        for(Mesh3D mesh: meshs){
            PBRModel model = new PBRModel(mesh, material);
            addChild(model);
        }
    }

    public Mesh3D getMesh() {
        return mesh;
    }

    public PBRMaterial getMaterial() {
        return material;
    }
    
    
}

