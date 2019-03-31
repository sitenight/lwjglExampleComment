package example.demo06.engine.render.shaders;

import java.nio.FloatBuffer;

/**
 * Простой класс заполнителя, используемый с Шейдерной программой
 * @author Medved Sitenight <sitenight00@gmail.com>
 */
public class UniformData {

    /** Расположение униформы, ее указатель */
    private final int uniformLocation;
    
    /** Буфер, содержащий информацию */
    private FloatBuffer buffer;

    /**
     * Конструктор новой Юниформы
     * @param uniformLocation локальная юниформа
     */
    public UniformData(int uniformLocation) {
        this.uniformLocation = uniformLocation;
    }

    // =========================================
    
    /**
     * Получить локальную юниформу
     * @return локальная юниформа
     */
    public int getUniformLocation() {
        return uniformLocation;
    }

    public FloatBuffer getBuffer() {
        return buffer;
    }

    public void setBuffer(FloatBuffer buffer) {
        this.buffer = buffer;
    }
    
    
    
}
