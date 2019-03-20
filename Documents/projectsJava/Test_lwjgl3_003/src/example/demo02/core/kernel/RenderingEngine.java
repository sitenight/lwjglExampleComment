package example.demo02.core.kernel;

import example.demo02.core.kernel.Window;
import example.demo02.modules.skydome.Skydome;
import example.demo02.modules.terrain.Terrain;
import example.demo02.core.configs.Default;
import example.demo02.core.kernel.Camera;

/**
 * 
 * @author oreon3D
 * The RenderingEngine manages the render calls of all 3D entities
 * with shadow rendering and post processing effects
 *
 */
public class RenderingEngine {
	
	private Window window;
	
	private Skydome skydome;
	private Terrain terrain;
	
	public RenderingEngine()
	{
		window = Window.getInstance();
		skydome = new Skydome();
		terrain = new Terrain();
	}
	
	public void init()
	{
		window.init();
		terrain.init("./res/demo02/settings/terrain_settings.txt");
	}

	public void render()
	{	
		Camera.getInstance().update();
		
		Default.clearScreen();
		
		skydome.render();
		
		terrain.updateQuadtree();
		
		terrain.render();
		
		// draw into OpenGL window
		window.render();
	}
	
	public void update(){
	}
	
	public void shutdown(){}
}
