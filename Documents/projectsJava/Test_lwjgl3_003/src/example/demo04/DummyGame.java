package example.demo04;

import example.demo04.engine.IGameLogic;
import example.demo04.engine.model.Mesh;
import example.demo04.engine.Renderer;
import example.demo04.engine.Window;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.glViewport;

public class DummyGame implements IGameLogic{
    
    private int direction = 0;
    private float color = 0.0f;
    private final Renderer renderer;
    private Mesh mesh;

    public DummyGame() {
        renderer = new Renderer();
    }

    @Override
    public void init(Window window) throws Exception {
        renderer.init(window);
        
        
        // массив вершин модели
        float[] vertices = new float[] {
            -0.5f,  0.5f, -2.5f,
            -0.5f, -0.5f, -2.5f,
             0.5f, -0.5f, -2.5f,
             0.5f,  0.5f, -2.5f
        };
        int[] indices = new int[] {
            0, 1, 3, 3, 1, 2
        };
        float[] colours = new float[] {
             0.5f,  0.0f, 0.0f,
             0.5f,  0.5f, 0.0f,
             0.0f,  0.0f, 0.5f,
             0.0f,  0.5f, 0.5f
        };
        mesh = new Mesh(vertices, colours, indices);
    }

    @Override
    public void input(Window window) {
        if(window.isKeyPressed(GLFW_KEY_UP)) {
            direction = 1;
        } else if(window.isKeyPressed(GLFW_KEY_DOWN)) {
            direction = -1;
        } else {
            direction = 0;
        }
    }

    @Override
    public void update(float interval) {
        color += direction * 0.01f;
        if(color > 1) {
            color = 1.0f;
        } else if(color < 0) {
            color = 0.0f;
        }
    }

    @Override
    public void render(Window window) {
        window.setClearColor(color, color, color, 0.0f);
        renderer.render(window, mesh);
    }

    @Override
    public void cleanup() {
        renderer.cleanup();
        mesh.cleanup();
    }

}

