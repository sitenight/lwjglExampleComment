package example.demo02.modules.skydome;

import example.demo02.core.buffers.MeshVBO;
import example.demo02.core.configs.CCW;
import example.demo02.core.model.Mesh;
import example.demo02.core.renderer.RenderInfo;
import example.demo02.core.renderer.Renderer;
import example.demo02.core.scene.GameObject;
import example.demo02.core.utils.Constants;
import example.demo02.core.utils.objloader.OBJLoader;

public class Skydome extends GameObject{
	
	public Skydome()
	{
		getWorldTransform().setScaling(Constants.ZFAR*0.5f, Constants.ZFAR*0.5f, Constants.ZFAR*0.5f);
		Mesh mesh = new OBJLoader().load("./res/demo02/models/dome", "dome.obj", null)[0].getMesh();
		MeshVBO meshBuffer = new MeshVBO();
		meshBuffer.allocate(mesh);
		Renderer renderer = new Renderer();
		renderer.setVbo(meshBuffer);
		renderer.setRenderInfo(new RenderInfo(new CCW(), AtmosphereShader.getInstance()));
		addComponent(Constants.RENDERER_COMPONENT, renderer);
	}
}
