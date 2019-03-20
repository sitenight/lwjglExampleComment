package example.demo04.engine.model;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import static org.lwjgl.opengl.GL11.GL_FLOAT;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.system.MemoryUtil;

/**
 * Принимая в качестве ввода массив позиций, создает объекты 
 * VBO (Объект буффера вершин - содержит вершины) и 
 * VAO (бъект вершинного массива - содержит списки атрибутов: положениеб цветб текстура и т.д.), 
 * необходимые для загрузки модели в графическую карту
 * @author user
 */
public class Mesh {

    /** Объект буффера вершин - содержит вершины */
    private final  int positionVboId;
    
    /** Объект буффера вершин - содержит индексы вершин */
    private final  int indicesVboId;
    
    /** Объект буффера вершин - содержит цвета вершин */
    private final  int colourVboId;
    
    /** Объект вершинного массива - содержит списки атрибутов: положениеб цветб текстура и т.д.*/
    private final int vaoId;
    
    private final int vertexCount;

    /**
     * 
     * @param positions массив координат вершин
     * @param colours содержит цвет для каждой координаты
     * @param indices массив индексов вершин
     */
    public Mesh(float[] positions, float[] colours, int[] indices) {
        FloatBuffer posBuffer = null;
        FloatBuffer colourBuffer = null;
        IntBuffer indicesBuffer = null;
        try {
            vertexCount = indices.length;

            vaoId = GL30.glGenVertexArrays(); // выделяем идентификатор в OpenGL
            GL30.glBindVertexArray(vaoId); // Активируем буффера вершин
            
            // VBO Буфер позиций
            positionVboId = GL15.glGenBuffers(); // выделяем идентификатор буфера позиций в OpenGL
            // Cоздание буфера в свободной памяти, чтобы он был доступен библиотеке OpenGL
            posBuffer = MemoryUtil.memAllocFloat(positions.length);             
            // Сохраняем данные вершин (с помощью метода put)
            // Сбрасываем положение буфера до позиции 0 с помощью метода flip 
            posBuffer.put(positions).flip();
            GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, positionVboId);  // Активируем вершинный массив
            GL15.glBufferData(GL15.GL_ARRAY_BUFFER, posBuffer, GL15.GL_STATIC_DRAW); // помещаем в него данные
            // Определяем структуру данных и сохраняем в  в одном из списков атрибутов
            // параметры:
            // 1) Индекс - место, где шейдер ожидает данные
            // 2) Размер - кол-ва каомпонентов на атрибут(от 1 до 4)
            // 3) Тип - тип каждого компонента в массиве
            // 4) Нормализация - должны ли значения быть нормализированными
            // 5) Шаг - задает смещение байта между последовательными аттрибутами
            // 6) Смещение - задает смещение первого компонента в бучере
            GL20.glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);

            // VBO Буфер индексов позиций
            indicesVboId = GL15.glGenBuffers(); 
            indicesBuffer = MemoryUtil.memAllocInt(indices.length);             
            indicesBuffer.put(indices).flip();
            GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, indicesVboId);  
            GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, GL15.GL_STATIC_DRAW); 
            GL20.glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
            
            // VBO Буфер цветов позиций
            colourVboId = GL15.glGenBuffers(); 
            colourBuffer = MemoryUtil.memAllocFloat(colours.length);             
            colourBuffer.put(colours).flip();
            GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, colourVboId);  
            GL15.glBufferData(GL15.GL_ARRAY_BUFFER, colourBuffer, GL15.GL_STATIC_DRAW); 
            // индекс = 1 для Шейдера
            GL20.glVertexAttribPointer(1, 3, GL_FLOAT, false, 0, 0);
            
            
            // отвязываем буффера вершин (VBO)
            GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);

            // отвязываем вершинный массив (VAO)
            GL30.glBindVertexArray(0);
        } finally { // освобождаем память
            if(posBuffer != null) 
                MemoryUtil.memFree(posBuffer);
            if(indicesBuffer != null) 
                MemoryUtil.memFree(indicesBuffer);
            if(colourBuffer != null) 
                MemoryUtil.memFree(colourBuffer);
        }
    }
    
    public void cleanup() {
        GL20.glDisableVertexAttribArray(0);
        
        // Delete the VBO
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0); // отвязываем буффера вершин (VBO)
        GL15.glDeleteBuffers(positionVboId);
        GL15.glDeleteBuffers(indicesVboId);
        GL15.glDeleteBuffers(colourVboId);
        
        // Delete the VAO
        GL30.glBindVertexArray(0);
        GL30.glDeleteVertexArrays(vaoId);
    }
    
    // ============ getter & setter =============

    public int getVaoId() {
        return vaoId;
    }

    public int getVertexCount() {
        return vertexCount;
    }
    
}

