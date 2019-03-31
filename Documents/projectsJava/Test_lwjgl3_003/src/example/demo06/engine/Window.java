package example.demo06.engine;

import example.demo06.engine.render.Color4f;
import org.lwjgl.glfw.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.glfw.GLFW.*;
import org.lwjgl.opengl.GL;
import static org.lwjgl.system.MemoryUtil.NULL;

/**
 * Объект, представляющий текущее окно
 * @author Medved Sitenight <sitenight00@gmail.com>
 */
public class Window {

    /** Название окна */
    private final String title;
    
    /** размеры окна */
    private int width, height;
    
    /** идентификатор окна */
    private long windowHandle;
    
    /** обработка ошибок */
    private GLFWErrorCallback errorCallback;
    
    /** The key callback. */
    private GLFWKeyCallback keyCallback;
    
    /** The window resize callback */
    private GLFWWindowSizeCallback windowSizeCallback;
    
    /** было ли окно изменено или нет*/
    private boolean resized;
    
    /** Должно ли это окно использовать вертикальную синхронизацию */
    private boolean vSync;
    
    /** опции окна */
    private WindowOptions options;

    public Window(String title, int width, int height, 
            boolean vSync, WindowOptions options) {
        this.title = title;
        this.width = width;
        this.height = height;
        this.resized = false;
        this.vSync = vSync;
        this.options = options;
    }
    
    /**
     * Инициализирует контекст GL, устанавливает обратные вызовы, 
     * определяет подсказки и отображает окно
     */
    public void init() {
        // Настройка обратного вызова ошибки. Реализация по умолчанию выведет 
        // сообщение об ошибке в System.err
        glfwSetErrorCallback(errorCallback = GLFWErrorCallback.createPrint(System.err));
        
        if(!glfwInit()) 
            throw new IllegalStateException("Не могу инициализировать GLFW");
        
        glfwDefaultWindowHints(); 
        glfwWindowHint(GLFW_VISIBLE, GL_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GL_TRUE);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2);
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
        glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE);
        
        // Создание окна
        windowHandle = glfwCreateWindow(width, height, title, NULL, NULL);
        if(windowHandle == NULL)
            throw new RuntimeException("Ошибка создания GLFW окна");
        
        // Настройка изменение размеров окна обратного доступа
        glfwSetWindowSizeCallback(windowHandle, windowSizeCallback = new GLFWWindowSizeCallback(){
            @Override
            public void invoke(long window, int width, int height) {
                Window.this.width = width;
                Window.this.height = height;
                Window.this.setResized(true);
            }
        });
        
        // Получить разрешение основного монитора
        GLFWVidMode vidMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        // Центер нашего окна
        glfwSetWindowPos(windowHandle,
                (vidMode.width() - width) / 2, 
                (vidMode.height() - height) / 2
        );
        
        // устанавливаем текущим контекстом наше окно
        glfwMakeContextCurrent(windowHandle);
        
        if(isvSync())
            glfwSwapInterval(1); // включаем вертикальную синхронизацию
        
        // делаем окно видимым 
        glfwShowWindow(windowHandle);
        
        GL.createCapabilities();
        
        // Очистка экрана
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        glEnable(GL_DEPTH_TEST);
        if(options.showTriangles)
            glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
        
        // Поддержка прозрачности
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        
        if(options.cullFace) {
            glEnable(GL_CULL_FACE);
            glCullFace(GL_BACK);
        }
    }
    
    public void update() {
        glfwSwapBuffers(windowHandle);
        glfwPollEvents();
    }
    
    /**
     * Устанавливает цвет фона
     * @param color  цвет
     */
    public void setClearColor(Color4f color) {
        glClearColor(color.r, color.g, color.b, color.a);
    }
    
    /**
     * Закрыто ли окно
     * @return закрыто или нет окно
     */
    public boolean windowShouldClose() {
        return glfwWindowShouldClose(windowHandle);
    }
    
    /**
     * Возвращает TRUE если клавиша нажата
     * @param keyCode код клавиши
     * @return статус клавиши
     */
    public boolean isKeyPressed(int keyCode) {
        return glfwGetKey(windowHandle, keyCode) == GLFW_PRESS;
    }
    
    /**
     * Возвращает TRUE если клавиша отнажата
     * @param keyCode код клавиши
     * @return статус клавиши
     */
    public boolean isKeyReleased(int keyCode) {
        return glfwGetKey(windowHandle, keyCode) == GLFW_RELEASE;
    }
    // ======================================

    /**
     * Возвращает ID окна
     * @return ID окна этого окна
     */
    public long getWindowHandle() {
        return windowHandle;
    }

    /**
     * Возвращает наименование окна
     * @return Наименование окна
     */
    public String getTitle() {
        return title;
    }

    /**
     * Возвращает ширину окна
     * @return ширина окна
     */
    public int getWidth() {
        return width;
    }

    /**
     * Возвращает высоту окна
     * @return высота окна
     */
    public int getHeight() {
        return height;
    }

    /**
     * Возвращает изменен ли был размер окна
     * @return True если изменен
     */
    public boolean isResized() {
        return resized;
    }

    /**
     * Устанавливает переменную изменения размера окна
     * @param resized размер окна, новое состояние
     */
    public void setResized(boolean resized) {
        this.resized = resized;
    }

    /**
     * Включена или нет вертикальная синхронизация
     * @return {@link #vSync}
     */
    public boolean isvSync() {
        return vSync;
    }

    /**
     * Установка вертикальной синхронизации
     * @param vSync новый статус
     */
    public void setvSync(boolean vSync) {
        this.vSync = vSync;
    }
    
    
    
}
