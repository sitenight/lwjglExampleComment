package example.demo02.modules.terrain;

import example.demo02.core.kernel.Camera;
import example.demo02.core.scene.GameObject;
import example.demo02.core.shaders.Shader;
import example.demo02.core.utils.ResourceLoader;

public class TerrainShader extends Shader{

private static TerrainShader instance = null;
	
	public static TerrainShader getInstance() 
	{
	    if(instance == null) 
	    {
	    	instance = new TerrainShader();
	    }
	      return instance;
	}
	
	protected TerrainShader()
	{
		super();

		addVertexShader(ResourceLoader.loadShader("shaders/terrain/Terrain_VS.glsl"));
		addTessellationControlShader(ResourceLoader.loadShader("shaders/terrain/Terrain_TC.glsl"));
		addTessellationEvaluationShader(ResourceLoader.loadShader("shaders/terrain/Terrain_TE.glsl"));
		addGeometryShader(ResourceLoader.loadShader("shaders/terrain/Terrain_GS.glsl"));
		addFragmentShader(ResourceLoader.loadShader("shaders/terrain/Terrain_FS.glsl"));
		compileShader();
	
		addUniform("localMatrix");
		addUniform("worldMatrix");

		addUniform("m_ViewProjection");
	}
	
	public void updateUniforms(GameObject object)
	{	
		setUniform("m_ViewProjection", Camera.getInstance().getViewProjectionMatrix());
		
		setUniform("localMatrix", object.getLocalTransform().getWorldMatrix());
		setUniform("worldMatrix", object.getWorldTransform().getWorldMatrix());
	}
}
