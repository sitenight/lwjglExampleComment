package example.demo06;

import example.demo06.engine.render.Camera;
import example.demo04.math.*;
import example.demo06.engine.*;
import example.demo06.engine.items.Item;

public class DummyGame implements ILogic {
    
    private static final float MOUSE_SENSITIVITY = 0.2f;
    private final Vector3f cameraInc;
    private final Renderer renderer;
    private final Camera camera;
    private Scene scene;
    private Hud hud;
    private static final float CAMERA_POS_STEP = 0.10f;
    private Terrain terrain;
    private float angleInc;
    private float lightAngle;
    private FlowParticleEmitter particleEmitter;
    private Item block, block1;

    public DummyGame() {
        this.cameraInc = new Vector3f();
        this.renderer =  new Renderer();
        this.camera = new Camera();
        angleInc = 0;
        lightAngle = 45;
    }

    @Override
    public void init(Window window) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void input(Window window, MouseInput mouseInput) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update(float interval, MouseInput mouseInput) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void render(Window window) {
        if(hud != null)
            hud.updateSize(window);
        renderer.render(window, camera, scene, hud);
    }

    @Override
    public void cleanup() {
        renderer.cleanup();
        scene.cleanup();
        if(hud != null)
            hud.cleanip();
    }
    
    

}
