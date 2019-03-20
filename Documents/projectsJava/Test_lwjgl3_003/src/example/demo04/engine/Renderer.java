package example.demo04.engine;

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
    
    /** Матрица проекции/перспектива */
    private Matrix4f projectionMatrix;
    
    public void init(Window window) throws Exception {
        // создаем шейдерную программу
        shaderProgram = new ShaderProgram();
        shaderProgram.createVertexShader(ShaderProgram.loadResource("/demo04/vertex.vs"));
        shaderProgram.createFragmentShader(ShaderProgram.loadResource("/demo04/fragment.fs"));
        shaderProgram.link();
        
        // создаем матрицу проекции
        projectionMatrix = new Matrix4f().projection(FOV, window.getWidth(), window.getHeight(), Z_NEAR, Z_FAR);
        //projectionMatrix = new org.joml.Matrix4f().perspective(FOV, aspectRatio, Z_NEAR, Z_FAR);
        shaderProgram.createUniform("projectionMatrix");
    }
    
    /**
     * Очистка экрана с настроенным четким цветом
     * Должен вызываться либо в начеле метода рендеринга, либо в конце
     */
    public void clear() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    }
    
   public void render(Window window, Mesh mesh) {
       clear();
       
       if(window.isResized()) {
           glViewport(0, 0, window.getWidth(), window.getHeight());
           window.setResized(false);
       }
       
       shaderProgram.bind(); // привязываем программу шейдеров
       shaderProgram.setUniform("projectionMatrix", projectionMatrix);
       
       // связываем VAO
       GL30.glBindVertexArray(mesh.getVaoId()); 
       // атрибуты передаем шейдеру и указали в Mesh glVertexAttribPointer
       GL20.glEnableVertexAttribArray(0);
       GL20.glEnableVertexAttribArray(1);
       
       // ============= Рисуем вершины Mesh
       // параметры метода:
       // 1) Режим - Определяет примитивы для рендеринга
       // 2) Количество - количество отображаемых элементов
       // 3) Тип - тип данных индексов(Int)
       // 4) Индекс - задает смещение, применяемое к данным индексов для начала рендеринга
       glDrawElements(GL_TRIANGLES, mesh.getVertexCount(), GL_UNSIGNED_INT, 0); 
       
       //восстанавливаем состояние
       GL20.glDisableVertexAttribArray(0); 
       GL20.glDisableVertexAttribArray(1); 
       GL30.glBindVertexArray(0);
       
       shaderProgram.unbind(); // отвязываем программу
   }
   
   public void cleanup() {
        if(shaderProgram != null)
           shaderProgram.cleanup();
   }
}

