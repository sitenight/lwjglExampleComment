package sbtm.engine.render;

import example.demo01.DemoUtils;
import java.io.File;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.file.Paths;
import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.opengl.GL30;
import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryStack;

public class Texture {

    private final int id;
    private int width;
    private int height;

    public Texture(int id) {
        this.id = id;
    }

    public Texture(String file) throws Exception {
        id = loadTexture(file);
    }
    
    private int loadTexture(String file) throws Exception {
        ByteBuffer imageData = DemoUtils.ioResourceToByteBuffer(file, 1024);
        ByteBuffer decodedImage = null;
        
        // загрузка текстуры
        try(MemoryStack stack = MemoryStack.stackPush()) {
            IntBuffer w = stack.mallocInt(1);
            IntBuffer h = stack.mallocInt(1);
            IntBuffer components = stack.mallocInt(1);
            
            // Декодировать текстурное изображение в байтовый буфер
            decodedImage = STBImage.stbi_load_from_memory(imageData, w, h, components, 4);
            if(decodedImage == null) {
                throw new Exception("Картинка [" + file + "] не загруженно: " + STBImage.stbi_failure_reason());
            }
            
            this.width = w.get();
            this.height = h.get();
        }
        // создаем пустой объект текстуры
        int texturedId = glGenTextures();
        
        // в target GL_TEXTURE_2D помещаем наш объект текстуры, передав туда его id
        // выбирает указанную текстуру как активную для наложения ее на объекты
        glBindTexture(GL_TEXTURE_2D, texturedId);
            
        glPixelStorei(GL_UNPACK_ALIGNMENT, 1);
        // Методом glTexParameteri мы можем задать параметры объекта текстуры
        // GL_TEXTURE_MIN_FILTER  - какой режим фильтрации будет применен при сжатии изображения
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR); 
        // GL_TEXTURE_MAG_FILTER - какой режим фильтрации будет применен при растягивании изображения
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
           
        // Методом texImage2D мы передаем bitmap в объект текстуры.
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, 
                GL_RGBA, GL_UNSIGNED_BYTE, decodedImage);
        
        GL30.glGenerateMipmap(GL_TEXTURE_2D);
        glBindTexture(GL_TEXTURE_2D, 0);        
        
        STBImage.stbi_image_free(decodedImage);
        
        
        return texturedId;
    }
    
    /**
     * Привязать эту текстуру к текущему активному слоту текстуры
     */
    public void use() {
        glBindTexture(GL_TEXTURE_2D, id);
    }
}

