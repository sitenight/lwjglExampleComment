package example.demo06.engine.render;

import example.demo04.math.Matrix4f;
import example.demo06.engine.items.Item;
import java.nio.FloatBuffer;
import java.util.List;
import org.lwjgl.BufferUtils;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL31.*;
import static org.lwjgl.opengl.GL33.*;

/**
 * Более эффективная {@link Mesh}, которая не поддерживает анимацию.
 * <p> Использует преимущества методов OpenGL 
 * <tt> glDraw * Instanced </tt> для повышения производительности. </p>
 * @author user
 */
public class InstancedMesh extends Mesh {

    /** Размер float в байтах */
    private static final int FLOAT_SIZE_BYTES = 4;
    
    /** Размер Vector4f в байтах */
    private static final int VECTOR4F_SIZE_BYTES = 4 * FLOAT_SIZE_BYTES;
    
    /** Размер Matrix4f */
    private static final int MATRIX_SIZE_FLOATS = 4 * 4;
    
    /** Размер Matrix4f в байтах */
    private static final int MATRIX_SIZE_BYTES = MATRIX_SIZE_FLOATS * FLOAT_SIZE_BYTES;
    
    /** Размер экземпляра (bytes) в байтах */
    private static final int INSTANCE_SIZE_BYTES = MATRIX_SIZE_BYTES * 2 + FLOAT_SIZE_BYTES * 2;
    
    /** Размер экземпляра (floats) в байтах */
    private static final int INSTANCE_SIZE_FLOATS = MATRIX_SIZE_FLOATS * 2 + 2;
    
    /** Количество экземпляров */
    private final int numInstances;
    
    /** Данные экземпляра VBO */
    private final int instanceDataVBO;
    
    /** Буфер данных */
    private final FloatBuffer instanceDataBuffer;

    public InstancedMesh(float[] positions, float[] textCoords, float[] normals, int[] indices, int numInstances) {
        super(positions, textCoords, normals, indices, 
                createEmptyIntArray(MAX_WEIGHT * positions.length / 3, 0), 
                createEmptyFloatArray(MAX_WEIGHT * positions.length / 3, 0)
        );
        this.numInstances = numInstances;
        
        glBindVertexArray(vaoId);
        
        // Модель матрицы вида
        this.instanceDataVBO = glGenBuffers();
        vboIdList.add(instanceDataVBO);
        this.instanceDataBuffer = BufferUtils.createFloatBuffer(numInstances * INSTANCE_SIZE_FLOATS);
        glBindBuffer(GL_ARRAY_BUFFER, instanceDataVBO);
        int start = 5;
        int strideStart = 0;
        for (int i = 0; i < 4; i++) {
            glVertexAttribPointer(start, 4, GL_FLOAT, false, INSTANCE_SIZE_BYTES, strideStart);
            glVertexAttribDivisor(start, 1);
            start++;
            strideStart += VECTOR4F_SIZE_BYTES;
        }
        
        // Световая матрицы вида
        for (int i = 0; i < 4; i++) {
            glVertexAttribPointer(start, 4, GL_FLOAT, false, INSTANCE_SIZE_BYTES, strideStart);
            glVertexAttribDivisor(start, 1);
            start++;
            strideStart += VECTOR4F_SIZE_BYTES;
        }
        
        // Texture offsets
        glVertexAttribPointer(start, 2, GL_FLOAT, false, INSTANCE_SIZE_BYTES, strideStart);
        glVertexAttribDivisor(start, 1);

        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindVertexArray(0);
    }
    
    @Override
    protected void initRender() {
        super.initRender();
        
        int start = 5;
        int numElements = 4 * 2 + 1;
        for (int i = 0; i < numElements; i++) {
            glEnableVertexAttribArray(start + i);
        }
    }
    
    @Override
    protected void endRender() {
        int start = 5;
        int numElements = 4 * 2 + 1;
        for (int i = 0; i < numElements; i++) {
            glDisableVertexAttribArray(start + i);
        }
        
        super.endRender();
    }
    
    /**
     * Визуализирует список экземпляров элементов
     * @param items объекты
     * @param transformation трансформация
     * @param viewMatrix матрица вида
     * @param lightViewMatrix матрица света
     */
    public void renderListInstanced(List<Item> items, Transformation transformation, Matrix4f viewMatrix, Matrix4f lightViewMatrix){
        renderListInstanced(items, false, transformation, viewMatrix, lightViewMatrix);
    }
    
    public void renderListInstanced(List<Item> items, boolean billBoard, Transformation transformation, Matrix4f viewMatrix, Matrix4f lightViewMatrix){
        initRender();

        int chunkSize = numInstances;
        int length = items.size();
        for(int i = 0; i < length; i += chunkSize){
            int end = Math.min(length, i + chunkSize);
            List<Item> subList = items.subList(i, end);
            renderChunkInstanced(subList, billBoard, transformation, viewMatrix, lightViewMatrix);
        }

        endRender();
    }

    /**
     * Отрисовывает кусок/часть предметов
     * @param items объекты
     * @param billBoard Если предметы являются частицами
     * @param transformation трансформация
     * @param viewMatrix матрица вида
     * @param lightViewMatrix матрица света
     */
    private void renderChunkInstanced(List<Item> items, boolean billBoard, Transformation transformation, Matrix4f viewMatrix, Matrix4f lightViewMatrix){
        this.instanceDataBuffer.clear();

        int i = 0;

        Texture text = getMaterial().getTexture();
        for(Item gameItem : items){
            Matrix4f modelMatrix = transformation.buildModelMatrix(gameItem);
            if(viewMatrix != null){
                if(billBoard){
                    viewMatrix.transpose3x3(modelMatrix);
                }
                Matrix4f modelViewMatrix = transformation.buildModelViewMatrix(modelMatrix, viewMatrix);
                modelViewMatrix.get(INSTANCE_SIZE_FLOATS * i, instanceDataBuffer);
            }
            if(lightViewMatrix != null){
                Matrix4f modelLightViewMatrix = transformation.buildModelLightViewMatrix(modelMatrix, lightViewMatrix);
                modelLightViewMatrix.get(INSTANCE_SIZE_FLOATS * i + MATRIX_SIZE_FLOATS, this.instanceDataBuffer);
            }
            if(text != null){
                int col = gameItem.getTextPos() % text.getNumCols();
                int row = gameItem.getTextPos() / text.getNumCols();
                float textXOffset = (float) col / text.getNumCols();
                float textYOffset = (float) row / text.getNumRows();
                int buffPos = INSTANCE_SIZE_FLOATS * i + MATRIX_SIZE_FLOATS * 2;
                this.instanceDataBuffer.put(buffPos, textXOffset);
                this.instanceDataBuffer.put(buffPos + 1, textYOffset);
            }

            i++;
        }

        glBindBuffer(GL_ARRAY_BUFFER, instanceDataVBO);
        glBufferData(GL_ARRAY_BUFFER, instanceDataBuffer, GL_DYNAMIC_DRAW);

        glDrawElementsInstanced(
                GL_TRIANGLES, getVertexCount(), GL_UNSIGNED_INT, 0, items.size());

        glBindBuffer(GL_ARRAY_BUFFER, 0);
    }
}

