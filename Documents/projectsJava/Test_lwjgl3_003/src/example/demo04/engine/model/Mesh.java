package example.demo04.engine.model;

import example.demo04.math.Vector3f;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.GL_FLOAT;
import org.lwjgl.opengl.GL13;
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
    
    private static final Vector3f DEFAULT_COLOUR = new Vector3f(1.0f, 1.0f, 1.0f);
    
    /** Объект вершинного массива - содержит списки атрибутов: положениеб цветб текстура и т.д.*/
    private final int vaoId;
    
    /** Список буффера вершин - содержит индексы вершин */
    private final List<Integer> vboIdList;
    
    /** Количество вершин */
    private final int vertexCount;
    
    private Texture texture;
    
    private Vector3f colour;

    /**
     * Конструктор объекта
     * @param positions массив координат вершин
     * @param colours содержит цвет для каждой координаты
     * @param indices массив индексов вершин
     */
    public Mesh(float[] positions, float[] textCoords, float[] normal, int[] indices) {
        FloatBuffer posBuffer = null;
        FloatBuffer textCoordsBuffer = null;
        FloatBuffer vecNormalsBuffer = null;
        IntBuffer indicesBuffer = null;
        try {
            colour = DEFAULT_COLOUR;
            vertexCount = indices.length;
            vboIdList = new ArrayList<>();

            vaoId = GL30.glGenVertexArrays(); // выделяем идентификатор в OpenGL
            GL30.glBindVertexArray(vaoId); // Активируем буффера вершин
            
            // VBO Буфер позиций
            int vboId = GL15.glGenBuffers(); // выделяем идентификатор буфера позиций в OpenGL
            vboIdList.add(vboId);
            // Cоздание буфера в свободной памяти, чтобы он был доступен библиотеке OpenGL
            posBuffer = MemoryUtil.memAllocFloat(positions.length);             
            // Сохраняем данные вершин (с помощью метода put)
            // Сбрасываем положение буфера до позиции 0 с помощью метода flip 
            posBuffer.put(positions).flip();
            GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboId);  // Активируем вершинный массив
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

            // VBO Буфер текстурных координат
            vboId = GL15.glGenBuffers(); 
            vboIdList.add(vboId);
            textCoordsBuffer = MemoryUtil.memAllocFloat(textCoords.length);             
            textCoordsBuffer.put(textCoords).flip();
            GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboId);  
            GL15.glBufferData(GL15.GL_ARRAY_BUFFER, textCoordsBuffer, GL15.GL_STATIC_DRAW); 
            // индекс = 1 (для передачи Шейдеру)
            GL20.glVertexAttribPointer(1, 2, GL_FLOAT, false, 0, 0);
            
            // VBO Буфер нормалей
            vboId = GL15.glGenBuffers(); 
            vboIdList.add(vboId);
            vecNormalsBuffer = MemoryUtil.memAllocFloat(normal.length);             
            vecNormalsBuffer.put(normal).flip();
            GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboId);  
            GL15.glBufferData(GL15.GL_ARRAY_BUFFER, vecNormalsBuffer, GL15.GL_STATIC_DRAW); 
            // индекс = 1 (для передачи Шейдеру)
            GL20.glVertexAttribPointer(2, 3, GL_FLOAT, false, 0, 0);
            
            // VBO Буфер индексов позиций
            vboId = GL15.glGenBuffers(); 
            vboIdList.add(vboId);
            indicesBuffer = MemoryUtil.memAllocInt(indices.length);             
            indicesBuffer.put(indices).flip();
            GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboId);  
            GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, GL15.GL_STATIC_DRAW);             
            
            // отвязываем буффера вершин (VBO)
            GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);

            // отвязываем вершинный массив (VAO)
            GL30.glBindVertexArray(0);
        } finally { // освобождаем память
            if(posBuffer != null) 
                MemoryUtil.memFree(posBuffer);
            if(textCoordsBuffer != null) 
                MemoryUtil.memFree(textCoordsBuffer);
            if(vecNormalsBuffer != null) 
                MemoryUtil.memFree(vecNormalsBuffer);
            if(indicesBuffer != null) 
                MemoryUtil.memFree(indicesBuffer);
        }
    }
    
    /**
     * Рисуем сетку модели
     */
    public void render() {
        if(isTexture()) {
            // Активируем первый банк текстур
            GL13.glActiveTexture(GL13.GL_TEXTURE0);
            // присоединяем текстуру
            glBindTexture(GL_TEXTURE_2D, texture.getId());
        }
       
        // связываем VAO
       GL30.glBindVertexArray(getVaoId()); 
       // атрибуты передаем шейдеру и указали в Mesh glVertexAttribPointer
       GL20.glEnableVertexAttribArray(0);
       GL20.glEnableVertexAttribArray(1);
       GL20.glEnableVertexAttribArray(2);
       
       // ============= Рисуем вершины Mesh
       // параметры метода:
       // 1) Режим - Определяет примитивы для рендеринга
       // 2) Количество - количество отображаемых элементов
       // 3) Тип - тип данных индексов(Int)
       // 4) Индекс - задает смещение, применяемое к данным индексов для начала рендеринга
       glDrawElements(GL_TRIANGLES, getVertexCount(), GL_UNSIGNED_INT, 0); 
       
       //восстанавливаем состояние
       GL20.glDisableVertexAttribArray(0); 
       GL20.glDisableVertexAttribArray(1); 
       GL20.glDisableVertexAttribArray(2); 
       GL30.glBindVertexArray(0);
       glBindTexture(GL_TEXTURE_2D, 0);
    }
    
    public void cleanup() {
        GL20.glDisableVertexAttribArray(0);
        
        // Delete the VBO
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0); // отвязываем буффера вершин (VBO)
        for (int vboId : vboIdList) {
            GL15.glDeleteBuffers(vboId);
        }
        
        // Delete the textures
        if(isTexture()) 
            texture.cleanup();
        
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

    public boolean isTexture() {
        return this.texture != null;
    }

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public Vector3f getColour() {
        return colour;
    }

    public void setColour(Vector3f colour) {
        this.colour = colour;
    }
    
    
}

