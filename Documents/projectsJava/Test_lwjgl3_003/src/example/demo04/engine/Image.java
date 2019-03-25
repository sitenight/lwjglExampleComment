package example.demo04.engine;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryStack;

public class Image {

    private ByteBuffer image;
    private int width, height;

    public Image(ByteBuffer image, int width, int height) {
        this.image = image;
        this.width = width;
        this.height = height;
    }
    
    public static Image loadImage(String path) throws Exception {
        ByteBuffer image;
        int width, height;
        
        try(MemoryStack stack = MemoryStack.stackPush()) {
            IntBuffer comp = stack.mallocInt(1);
            IntBuffer w = stack.mallocInt(1);
            IntBuffer h = stack.mallocInt(1);
            
            image = STBImage.stbi_load(path, w, h, comp, 4);
            if(image == null)
                throw new Exception("Не могу загрузить " + path);
            width = w.get();
            height = h.get();
        }
        
        return new Image(image, width, height);
    }
    
    // =============== getter & setter =======

    public ByteBuffer getImage() {
        return image;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
    
    
}

