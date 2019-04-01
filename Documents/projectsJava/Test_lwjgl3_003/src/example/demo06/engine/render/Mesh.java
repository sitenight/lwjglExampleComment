package example.demo06.engine.render;

import example.demo06.engine.*;
import example.demo06.engine.items.Item;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import org.lwjgl.BufferUtils;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

/**
 * Класс, представляющий сетку. <p> Сетка - это объект в пространстве, представленный позициями вершин и текстурой </p>
 * <p> Вы можете загрузить <tt> Mesh </tt> либо используя {@link engine.loaders.obj.OBJLoader # loadMesh (String)}
 * или {@link engine.loaders.md5.MD5Loader # process (MD5Model, MD5AnimModel, Vector3f)} </p>
 * @author Medved Sitenight <sitenight00@gmail.com>
 */
public class Mesh {

    /** Максимальное количество весов */
    public static final int MAX_WEIGHT = 4;
    
    /** Объект вершинного массива - содержит списки атрибутов: положениеб цвет, текстура и т.д.*/
    protected final int vaoId;
    
    /** Список буффера вершин - содержит индексы вершин */
    protected final List<Integer> vboIdList;
    
    /** Массив позиций. Сделано для помощи при столкновениях */
    private float[] positions;
    
    /** Количество вершин */
    private final int vertexCount;
    
    /** Материал модели */
    private Material material;

    /**
     * Конструктор объекта
     * @param positions массив координат вершин
     * @param textCoords массив текстурных координат
     * @param normals массив нормалей
     * @param indices массив индексов вершин
     * @param jointIndices Массив, содержащий индексы суставов
     * @param weights Массив, содержащий веса
     */
    public Mesh(float[] positions, float[] textCoords, float[] normals, int[] indices,
            int[] jointIndices, float[] weights) {
        vertexCount = indices.length;
        vboIdList = new ArrayList<>();

        vaoId = glGenVertexArrays(); // выделяем идентификатор в OpenGL
        glBindVertexArray(vaoId); // Активируем буффера вершин
            
        // VBO Буфер позиций
        int vboId = glGenBuffers(); // выделяем идентификатор буфера позиций в OpenGL
        vboIdList.add(vboId);
        // Cоздание буфера в свободной памяти, чтобы он был доступен библиотеке OpenGL
        FloatBuffer posBuffer = BufferUtils.createFloatBuffer(positions.length);             
        // Сохраняем данные вершин (с помощью метода put)
        // Сбрасываем положение буфера до позиции 0 с помощью метода flip 
        posBuffer.put(positions).flip();
        glBindBuffer(GL_ARRAY_BUFFER, vboId);  // Активируем вершинный массив
        glBufferData(GL_ARRAY_BUFFER, posBuffer, GL_STATIC_DRAW); // помещаем в него данные
        // Определяем структуру данных и сохраняем в  в одном из списков атрибутов
        // параметры:
        // 1) Индекс - место, где шейдер ожидает данные
        // 2) Размер - кол-ва каомпонентов на атрибут(от 1 до 4)
        // 3) Тип - тип каждого компонента в массиве
        // 4) Нормализация - должны ли значения быть нормализированными
        // 5) Шаг - задает смещение байта между последовательными аттрибутами
        // 6) Смещение - задает смещение первого компонента в бучере
        glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);

        // VBO Буфер текстурных координат
        vboId = glGenBuffers(); 
        vboIdList.add(vboId);
        FloatBuffer textCoordsBuffer = BufferUtils.createFloatBuffer(textCoords.length);             
        textCoordsBuffer.put(textCoords).flip();
        glBindBuffer(GL_ARRAY_BUFFER, vboId);  
        glBufferData(GL_ARRAY_BUFFER, textCoordsBuffer, GL_STATIC_DRAW); 
        // индекс = 1 (для передачи Шейдеру)
        glVertexAttribPointer(1, 2, GL_FLOAT, false, 0, 0);
            
        // VBO Буфер нормалей
        vboId = glGenBuffers(); 
        vboIdList.add(vboId);
        FloatBuffer vecNormalsBuffer = BufferUtils.createFloatBuffer(normals.length);             
        vecNormalsBuffer.put(normals).flip();
        glBindBuffer(GL_ARRAY_BUFFER, vboId);  
        glBufferData(GL_ARRAY_BUFFER, vecNormalsBuffer, GL_STATIC_DRAW); 
        // индекс = 2 (для передачи Шейдеру)
        glVertexAttribPointer(2, 3, GL_FLOAT, false, 0, 0);
        
        // Веса
        vboId = glGenBuffers(); 
        vboIdList.add(vboId);
        FloatBuffer weightsBuffer = BufferUtils.createFloatBuffer(weights.length);             
        weightsBuffer.put(weights).flip();
        glBindBuffer(GL_ARRAY_BUFFER, vboId);  
        glBufferData(GL_ARRAY_BUFFER, weightsBuffer, GL_STATIC_DRAW); 
        // индекс = 3 (для передачи Шейдеру)
        glVertexAttribPointer(3, 4, GL_FLOAT, false, 0, 0);
            
        // Совместные индексы
        vboId = glGenBuffers(); 
        vboIdList.add(vboId);
        IntBuffer jointIndicesBuffer = BufferUtils.createIntBuffer(jointIndices.length);             
        jointIndicesBuffer.put(jointIndices).flip();
        glBindBuffer(GL_ARRAY_BUFFER, vboId);  
        glBufferData(GL_ARRAY_BUFFER, jointIndicesBuffer, GL_STATIC_DRAW); 
        // индекс = 4 (для передачи Шейдеру)
        glVertexAttribPointer(4, 4, GL_FLOAT, false, 0, 0);
            
        // VBO Буфер индексов позиций
        vboId = glGenBuffers(); 
        vboIdList.add(vboId);
        IntBuffer indicesBuffer = BufferUtils.createIntBuffer(indices.length);             
        indicesBuffer.put(indices).flip();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, vboId);  
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, GL_STATIC_DRAW);             
            
        // отвязываем буффера вершин (VBO)
        glBindBuffer(GL_ARRAY_BUFFER, 0);

        // отвязываем вершинный массив (VAO)
        glBindVertexArray(0);
    }
    
    public Mesh(float[] positions, float[] textCoords, float[] normals, int[] indices) {
        this(positions, textCoords, normals, indices, 
                createEmptyIntArray(MAX_WEIGHT * positions.length / 3, 0),
                createEmptyFloatArray(MAX_WEIGHT * positions.length / 3, 0));
    }
    
    /**
     * Создание пустого Целочисленного массива INT
     * @param length размер массива
     * @param defaultValue значение по умолчанию
     * @return новый пустой массив
     */
    protected static int[] createEmptyIntArray(int length, int defaultValue) {
        int[] result = new int[length];
        Arrays.fill(result, defaultValue);
        return result;
    }
    
    /**
     * Создание пустого массива FLOAT
     * @param length размер массива
     * @param defaultValue значение по умолчанию
     * @return новый пустой массив
     */
    protected static float[] createEmptyFloatArray(int length, float defaultValue) {
        float[] result = new float[length];
        Arrays.fill(result, defaultValue);
        return result;
    }
    
    /**
     * Инициализируем операции рендеринга/визуализации
     */
    protected void initRender() {
        Texture texture = material.getTexture();
        if(material.isTextured()) {
            // Активируем первый банк текстур
            glActiveTexture(GL_TEXTURE0);
            // присоединяем текстуру
            glBindTexture(GL_TEXTURE_2D, texture.getId());
        }
        Texture normalMap = material.getNormalMap();
        if(material.isNormalMap()) {
            // Активируем второй банк текстур
            glActiveTexture(GL_TEXTURE1);
            // присоединяем текстуру
            glBindTexture(GL_TEXTURE_2D, normalMap.getId());
        }
       
        // связываем VAO
       glBindVertexArray(getVaoId()); 
       // атрибуты передаем шейдеру и указали в Mesh glVertexAttribPointer
       glEnableVertexAttribArray(0);
       glEnableVertexAttribArray(1);
       glEnableVertexAttribArray(2);
       glEnableVertexAttribArray(3);
       glEnableVertexAttribArray(4);
    }
    
    /**
     * Завершает операции рендеринга/визуализации
     * <p> Восстанавливает состояние OpenGL. </p>
     */
    protected void endRender() {
       glDisableVertexAttribArray(0); 
       glDisableVertexAttribArray(1); 
       glDisableVertexAttribArray(2); 
       glDisableVertexAttribArray(3); 
       glDisableVertexAttribArray(4); 
       glBindVertexArray(0);
       
       glBindTexture(GL_TEXTURE_2D, 0);
    }
    
    /**
     * Рисуем сетку модели
     */
    public void render() {
       initRender();
       
       // ============= Рисуем вершины Mesh
       // параметры метода:
       // 1) Режим - Определяет примитивы для рендеринга
       // 2) Количество - количество отображаемых элементов
       // 3) Тип - тип данных индексов(Int)
       // 4) Индекс - задает смещение, применяемое к данным индексов для начала рендеринга
       glDrawElements(GL_TRIANGLES, getVertexCount(), GL_UNSIGNED_INT, 0); 
       
       endRender();
    }
    
    /**
     * Рендерит/визуализирует список игровых элементов.
     * <p> Параметр <tt> consumer </tt> должен быть лямбда-выражением с любым действием, которое должно 
     * произойти до фактического рендеринга. </p>
     * @param items Элементы которые будут отображены
     * @param consumer Действие которое необходимо выполнить перед рендерингом
     */
    public void renderList(List<Item> items, Consumer<Item> consumer) {
       initRender(); 
       
       for (Item item : items) {
           // Установка данных, необходимых для элемента
           consumer.accept(item);
           // Рендер игрового элемента
           glDrawElements(GL_TRIANGLES, getVertexCount(), GL_UNSIGNED_INT, 0); 
       }
       
       endRender();
    }
    
    /**
     * Очистка ресурсов
     */
    public void cleanup() {
        glDisableVertexAttribArray(0);
        
        // Удаление VBO
        glBindBuffer(GL_ARRAY_BUFFER, 0); // отвязываем буффера вершин (VBO)
        for (int vboId : vboIdList) {
            glDeleteBuffers(vboId);
        }
        
        // Удаление текстур
        Texture texture = material.getTexture();
        if(material.isTextured()) 
            texture.cleanup();
        Texture normalMap = material.getNormalMap();
        if(material.isNormalMap()) 
            normalMap.cleanup();
        
        // Удаление VAO
        glBindVertexArray(0);
        glDeleteVertexArrays(vaoId);
    }
    
    /**
     * Удаление буферов
     */
    public void delereBuffers() {
        glDisableVertexAttribArray(0);
        
        // Удаление VBO
        glBindBuffer(GL_ARRAY_BUFFER, 0); // отвязываем буффера вершин (VBO)
        for (int vboId : vboIdList) {
            glDeleteBuffers(vboId);
        }
        
        // Удаление VAO
        glBindVertexArray(0);
        glDeleteVertexArrays(vaoId);
    }
    
    // ============ getter & setter =============

    /**
     * Получение объекта вершинного массива
     * @return Объект вершинного массива
     */
    public int getVaoId() {
        return vaoId;
    }

    /**
     * Получение количества вершин
     * @return Количество вершин
     */
    public int getVertexCount() {
        return vertexCount;
    }

    /**
     * Получение материала объекта
     * @return материал объекта
     */
    public Material getMaterial() {
        return material;
    }

    /**
     * Установка материала объекту
     * @param material материал объекта
     */
    public void setMaterial(Material material) {
        this.material = material;
    }

    /**
     * Получение Массива позиций
     * @return Массив позиций
     */
    public float[] getPositions() {
        return positions;
    }

    
}
