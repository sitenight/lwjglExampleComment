package example.demo04;

import example.demo04.engine.Camera;
import example.demo04.engine.IGameLogic;
import example.demo04.engine.MouseInput;
import example.demo04.engine.model.Mesh;
import example.demo04.engine.Renderer;
import example.demo04.engine.Window;
import example.demo04.engine.model.GameItem;
import example.demo04.engine.model.OBJLoader;
import example.demo04.engine.model.Texture;
import example.demo04.math.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.glViewport;

public class DummyGame implements IGameLogic{
    
    private static final float MOUSE_SENSITIVITY = 0.2f;
    private static final float CAMERA_POS_STEP = 0.05f;
    
    /** смещение камеры */
    private Vector3f cameraInc;
    
    private Camera camera;
    
    private final Renderer renderer;
    private GameItem[] gameItems;

    public DummyGame() {
        renderer = new Renderer();
        
        camera = new Camera();
        cameraInc = new Vector3f();
    }

    @Override
    public void init(Window window) throws Exception {
        renderer.init(window);
        
        Mesh mesh = OBJLoader.loadMesh("/demo04/models/cube.obj");
        Texture texture = new Texture("./res/demo04/textures/grassblock.png");
        
        mesh.setTexture(texture);
        
        GameItem gameItem = new GameItem(mesh);
        gameItem.setScale(0.5f);
        gameItem.setPosition(0, 0, -2);
        
        gameItems = new GameItem[] { gameItem };
    }

    @Override
    public void input(Window window, MouseInput mouseInput) {
        cameraInc.set(0, 0, 0);
        if (window.isKeyPressed(GLFW_KEY_W)) {
            cameraInc.z = -1;
        } else if (window.isKeyPressed(GLFW_KEY_S)) {
            cameraInc.z = 1;
        } 
        if (window.isKeyPressed(GLFW_KEY_A)) {
            cameraInc.x = -1;
        } else if (window.isKeyPressed(GLFW_KEY_D)) {
            cameraInc.x = 1;
        } 
        if (window.isKeyPressed(GLFW_KEY_SPACE)) {
            cameraInc.y = -1;
        } else if (window.isKeyPressed(GLFW_KEY_LEFT_CONTROL)) {
            cameraInc.y = 1;
        }
    }

    @Override
    public void update(float interval, MouseInput mouseInput) {
        for (GameItem gameItem : gameItems) {
            // обновляем позицию камеры
           camera.movePosition(
                   cameraInc.x * CAMERA_POS_STEP, 
                   cameraInc.y * CAMERA_POS_STEP, 
                   cameraInc.z * CAMERA_POS_STEP);
            
            // Update camera based on mouse 
            if(mouseInput.isRightButtonPressed()) {
                Vector2f rotVec = mouseInput.getDisplVec();
                camera.moveRotation(
                    rotVec.x * MOUSE_SENSITIVITY,
                    rotVec.y * MOUSE_SENSITIVITY,
                    0);
            }
        }
    }

    @Override
    public void render(Window window) {
        renderer.render(window, camera, gameItems);
    }

    @Override
    public void cleanup() {
        renderer.cleanup();
        for (GameItem gameItem : gameItems) {
            gameItem.getMesh().cleanup();
        }
    }

}

