package example.demo06.engine.render;

import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL30;
import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryStack;

/**
 * Объект, представляющий текстуру
 * @author Medved Sitenight <sitenight00@gmail.com>
 */
public class Texture {

    /** ID текстуры */
    private final int id;
    
    /** Ширина */
    private final int width;
    
    /** Высота */
    private final int height;
    
    /** Количество рядов */
    private int numRows = 1;
    
    /** Количество строк */
    private int numCols = 1;

    /**
     * Создание пустой текстуры
     * @param width ширина текстуры
     * @param height высота текстуры
     * @param pixelFormat Определяет формат данных пикселей (GL_RGBA и т. д.)
     * @throws Exception Если изображение не может быть связано
     */
    public Texture(int width, int height, int pixelFormat) throws Exception {
        this.id = glGenTextures();
        this.width = width;
        this.height = height;
        glBindTexture(GL_TEXTURE_2D, this.id);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_DEPTH_COMPONENT, this.width, this.height, 0, pixelFormat, GL_FLOAT, (ByteBuffer) null);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);
    }
    
    public Texture(String fileName) throws Exception {
        ByteBuffer image;
        
        try(MemoryStack stack = MemoryStack.stackPush()) {
            IntBuffer comp = stack.mallocInt(1);
            IntBuffer w = stack.mallocInt(1);
            IntBuffer h = stack.mallocInt(1);
            
            image = STBImage.stbi_load(fileName, w, h, comp, 4);
            if(image == null)
                throw new Exception("Не могу загрузить " + fileName);
            this.width = w.get();
            this.height = h.get();
        }
        
        // Генерируем новый идентификатор текстуры
        this.id = glGenTextures();
        // связываем с 2Д текстурой
        glBindTexture(GL_TEXTURE_2D, this.id);
        
        // Сообщаем OpenGL, как распаковать наши RGBA-байты, 
        // поскольку каждый компонент имеет размер в один байт
        glPixelStorei(GL_UNPACK_ALIGNMENT, 1);
        
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
        
        // говорим о том, что когда пиксель рисуется без прямой связи один к 
        // одному с координатой текстуры, он выбирает ближайшую точку текстуры
        //glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        //glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
        
        // Загрузка текстурных данных
        // glTextImage2DМетод имеет следующие параметры:
        // 1) target - тип текстуры
        // 2) level -  номер уровня детализации. Уровень 0 - это базовый 
        //      уровень изображения. Уровень n - это n-е уменьшенное изображение mipmap
        // 3) internal format - количество цветовых компонентов в текстуре
        // 4) width - ширина текстуры
        // 5) height - высота текстуры 
        // 6) border 
        // 7) format - формат данных пикселей
        // 8) type - тип данных данных пикселей. Для этого мы используем неподписанные байты
        // 9) data - уфер, в котором хранятся наши данные
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, 
                this.width, this.height, 0, 
                GL_RGBA, GL_UNSIGNED_BYTE, image);
        
        // генерируем набор изображений с уменьшенным разрешением (mipmap)
        // ти изображения с более низким разрешением будут использоваться 
        // автоматически при масштабировании нашего объекта
        GL30.glGenerateMipmap(GL_TEXTURE_2D);
        
        STBImage.stbi_image_free(image);
    }
    
    /**
     * Конструктор текстур
     * @param fileName путь и имя к файлу текстур
     * @param numCols Количество строк
     * @param numRows Количество рядов
     * @throws Exception Если изображение не может быть загружено
     */
    public Texture(String fileName, int numCols, int numRows) throws Exception {
        this(fileName);
        this.numCols = numCols;
        this.numRows = numRows;
    }
    
    /**
     * Присоединяем текстуру
     */
    public void bind() {
        glBindTexture(GL_TEXTURE_2D, id);
    }
    
    /**
     * Отсоединяем текстуру
     */
    public void unbind() {
        glBindTexture(GL_TEXTURE_2D, 0);
    }
    
    /**
     * Удаляем текстуру
     */
    public void cleanup() {
        glDeleteTextures(id);
    }
    
    // =======================================

    /**
     * Получение свойства идентификатора текстуры
     * @return идентификатор текстуры
     */
    public int getId() {
        return id;
    }
    
    /**
     * Получение свойства количество строк
     * @return значение количество строк
     */
    public int getNumCols() {
        return numCols;
    }

    /**
     * Получение свойства количество рядов
     * @return значение количество рядов
     */
    public int getNumRows() {
        return numRows;
    }

    /**
     * Получение свойства ширины текстуры
     * @return ширина текстуры
     */
    public int getWidth() {
        return width;
    }

    /**
     * Получение свойства высоты текстуры
     * @return высота текстуры
     */
    public int getHeight() {
        return height;
    }
    
    
    
}
