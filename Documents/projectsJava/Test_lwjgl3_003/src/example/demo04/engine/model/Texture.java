package example.demo04.engine.model;

import example.demo04.engine.Image;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.opengl.GL30;
import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryStack;

public class Texture {

    private final int id;

    public Texture(int id) {
        this.id = id;
    }
    
    public Texture(String fileName) throws Exception {
        this(loadTexture(fileName));
    }
    
    /**
     * Присоединяем текстуру
     */
    public void bind() {
        glBindTexture(GL_TEXTURE_2D, id);
    }
    
    /**
     * Удаляем текстуру
     */
    public void cleanup() {
        glDeleteTextures(id);
    }
    
    /**
     * Загрузка текстуры
     * @param fileName путь и имя файла текстуры
     * @return идентификатор загруженой текстуры
     * @throws Exception 
     */
    private static int loadTexture(String fileName) throws Exception {
        ByteBuffer image;
        int width, height;
        
        try(MemoryStack stack = MemoryStack.stackPush()) {
            IntBuffer comp = stack.mallocInt(1);
            IntBuffer w = stack.mallocInt(1);
            IntBuffer h = stack.mallocInt(1);
            
            image = STBImage.stbi_load(fileName, w, h, comp, 4);
            if(image == null)
                throw new Exception("Не могу загрузить " + fileName);
            width = w.get();
            height = h.get();
        }
        
        // Генерируем новый идентификатор текстуры
        int textureId = glGenTextures();
        // связываем с 2Д текстурой
        glBindTexture(GL_TEXTURE_2D, textureId);
        
        // Сообщаем OpenGL, как распаковать наши RGBA-байты, 
        // поскольку каждый компонент имеет размер в один байт
        glPixelStorei(GL_UNPACK_ALIGNMENT, 1);
        
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
                width, height, 0, 
                GL_RGBA, GL_UNSIGNED_BYTE, image);
        
        // генерируем набор изображений с уменьшенным разрешением (mipmap)
        // ти изображения с более низким разрешением будут использоваться 
        // автоматически при масштабировании нашего объекта
        GL30.glGenerateMipmap(GL_TEXTURE_2D);
        
        STBImage.stbi_image_free(image);
        
        return textureId;
    }
    
    // ============ getter & setter ============

    public int getId() {
        return id;
    }
    
    
}

