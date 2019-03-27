package example.demo04.engine;

import static org.lwjgl.glfw.GLFW.*;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

/**
 * GLFW — это библиотека для работы с окном, через OpenGL. Всё, что
 * связанно с окнами, работает при помощи библиотеки GLFW.
 * 
 * Этот класс позволяет параметризовать его характеристики
 * также предоставляет метод обнаружения нажатия клавиш
 * @author user
 */
public class Window {

    /** Указатель/идентификатор окна */
    private long windowHandle;
    
    /** Наименование окна */
    private final String title;
    
    /** Размеры окна */
    private int width, height;
    
    /** Изменяемый ли размер окна */
    private boolean resized;
    
     /** v-sync, мы не будем отправлять изображение в графический процессор, 
     * пока оно отображается на экране. Когда мы включаем v-sync, 
     * мы синхронизируемся с частотой обновления видеокарты, 
     * что в итоге приведет к постоянной частоте кадров. */
    private boolean vSync;

    /**
     * Конструктор окна
     * @param title Наименование окна
     * @param width ширина окна в пикселах
     * @param height высота окна в пикселах
     * @param vSync хотим ли мы использовать v-Sync или нет
     */
    public Window(String title, int width, int height, boolean vSync) {
        this.title = title;
        this.width = width;
        this.height = height;
        this.vSync = vSync; // хотим ли мы использовать vsync или нет
        resized = false;
    }
    
    public void init() {
        // Установка обратного вызова ошибки. 
        // Реализация по умолчанию выведет сообщение об ошибке в System.err.
        GLFWErrorCallback.createPrint(System.err).set();
        
        // Инициализировать GLFW. Большинство функций GLFW не будут работать до этого.
        if(!glfwInit()) {
            throw new IllegalStateException("Невозможно инициализировать GLFW");
        }
        
        // Настройка нашего окна
        glfwDefaultWindowHints(); // по желанию, текущее окно подсказки уже по умолчанию
        glfwWindowHint(GLFW_VISIBLE, GL_FALSE); 
        glfwWindowHint(GLFW_RESIZABLE, GL_TRUE);
                
        // Это заставит программу использовать максимально возможную 
        // версию OpenGL между 3.2 и 4.1. Если эти строки не включены, 
        // используется устаревшая версия OpenGL
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 4);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
        glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE);
                
        // Создание окна
        windowHandle = glfwCreateWindow(width, height, title, NULL, NULL);
        if(windowHandle == NULL) {
            throw new RuntimeException("Не удалось создать окно GLFW");
        }
        
        // Изменение ширины, высоты кадрового буфера в экранных координатах
        glfwSetFramebufferSizeCallback(windowHandle, (window, width, height) -> {
            this.width = width;
            this.height = height;
            this.setResized(true);
        });
        
        // Настройка ключа обратного вызова. Он будет вызываться при 
        // каждом нажатии, повторении или отпускании клавиши.
        glfwSetKeyCallback(windowHandle, (window, key, scancode, action, mods) -> {
            if(key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE)
                glfwSetWindowShouldClose(window, true); // Мы обнаружим это в цикле рендеринга
        });
        
        // Получить разрешение основного монитора
        GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        
        // Центр окна
        glfwSetWindowPos(
            windowHandle,
            (vidmode.width() - width) / 2,
            (vidmode.height() - height) / 2
        );
        
        glfwMakeContextCurrent(windowHandle);
        
        if(isvSync()){
            // Включить v-sync
            glfwSwapInterval(1);
        }
        
        // показать окно
        glfwShowWindow(windowHandle);
        
        // Set OpenGL context 
        GL.createCapabilities();
        
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        
        // Включаем глубинное тестирование
        // Пиксели, которые находятся далеко, должны быть нарисованы перед 
        // пикселями, которые находятся ближе
        glEnable(GL_DEPTH_TEST); 
    }
    
    public void update() {
        glfwSwapBuffers(windowHandle);
        glfwPollEvents();
    }
    
    public boolean windowShouldClose() {
        return glfwWindowShouldClose(windowHandle);
    }
    
    public void setClearColor(float r, float g, float b, float alpha) {
        glClearColor(r, g, b, alpha);
    }
    
    // ================== input =================
    
    /**
     * Обнаружения нажатия клавиш
     * @param keyCode Код клавиши
     * @return Нажати ли клавиша или нет
     */
    public boolean isKeyPressed(int keyCode) {
        return glfwGetKey(windowHandle, keyCode) == GLFW_PRESS;
    }
    
    // ============= getter & setter ===========

    public void setResized(boolean resized) {
        this.resized = resized;
    }

    public boolean isResized() {
        return resized;
    }

    public boolean isvSync() {
        return vSync;
    }

    public void setvSync(boolean vSync) {
        this.vSync = vSync;
    }
    
    public String getTitle() {
        return title;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public long getWindowHandle() {
        return windowHandle;
    }
    
    
}

