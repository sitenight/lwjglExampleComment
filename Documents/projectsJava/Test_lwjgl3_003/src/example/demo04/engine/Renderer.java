package example.demo04.engine;

import example.demo04.engine.model.GameItem;
import example.demo04.engine.model.Mesh;
import example.demo04.math.Matrix4f;
import java.nio.FloatBuffer;
import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.system.MemoryUtil;

/**
 * Будет обрабатывать нашу логику рендеринга
 * @author user
 */
public class Renderer {
    
    /** шейдерная программа */
    private ShaderProgram shaderProgram;
    
    /** Определяет поле зрения/Угол обзора */
    private static final float FOV = 60.0f;
    //private static final float FOV = (float) Math.toRadians(60.0f);
    
    /** Расстояние от точки зрения до ближней отсекающей рамки */
    private static final float Z_NEAR = 0.01f;
    
    /** Расстояние от точки зрения до дальней отсекающей рамки */
    private static final float Z_FAR = 1000.f;
    
    private final Transformation transformation;

    public Renderer() {
        this.transformation = new Transformation();
    }
    
    public void init(Window window) throws Exception {
        // создаем шейдерную программу
        shaderProgram = new ShaderProgram();
        shaderProgram.createVertexShader(ShaderProgram.loadResource("/demo04/vertex.vs"));
        shaderProgram.createFragmentShader(ShaderProgram.loadResource("/demo04/fragment.fs"));
        shaderProgram.link();
        
        // создаем униформу для мировой и проекционной матрицы
        shaderProgram.createUniform("projectionMatrix");
        shaderProgram.createUniform("worldMatrix");
        
        window.setClearColor(0.0f, 0.0f, 0.0f, 0.0f);
    }
    
    /**
     * Очистка экрана с настроенным четким цветом
     * Должен вызываться либо в начеле метода рендеринга, либо в конце
     */
    public void clear() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    }
    
   public void render(Window window, GameItem[] gameItems) {
       clear();
       
       if(window.isResized()) {
           glViewport(0, 0, window.getWidth(), window.getHeight());
           window.setResized(false);
       }
       
       shaderProgram.bind(); // привязываем программу шейдеров
              
       //Обновляем матрицу проекции
       Matrix4f projectionMatrix  = transformation.getProjectionMatrix(FOV, window.getWidth(), window.getHeight(), Z_NEAR, Z_FAR);
       shaderProgram.setUniform("projectionMatrix", projectionMatrix);
       
       // Рендеринг каждого игрового элемента
       for (GameItem gameItem : gameItems) {
           // Устанавливаем мировую матрицу для этого элемента
           Matrix4f worldMatrix = transformation.getWorldMatrix(
                   gameItem.getPosition(), 
                   gameItem.getRotation(), 
                   gameItem.getScale()
           );
           shaderProgram.setUniform("worldMatrix", worldMatrix); //помещаем матрицу в шейдер
           //Рендеринг Сетки для этого игрового предмета
           gameItem.getMesh().render();
       }
       
       shaderProgram.unbind(); // отвязываем программу
   }
   
   public void cleanup() {
        if(shaderProgram != null)
           shaderProgram.cleanup();
   }
}

